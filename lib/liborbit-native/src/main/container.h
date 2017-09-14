/*
* Copyright 2017 Oakville Community FIRST Robotics
*
* This file is part of LibOrbit.
*
* LibOrbit is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* LibOrbit is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
*
* Contributions:
*
* Nicholas Mertin (2017-09-08) - Created file
*/

#include <functional>
#include <cstdint>

namespace liborbit {
    template<class T>
    class container final {
    private:
        T value;
        uint64_t *refcount;
        std::function<void (T &)> handler;

    public:
        container(T value, std::function<void (T *)> handler) : value(value), refcount(new uint64_t(1)), handler(handler) {}

        container(container<T> &other) : value(other.value), refcount(other.refcount), handler(other.handler) {
            ++*refcount;
        }

        container<T> &operator=(const container &other) {
            this->~container();
            new(this) container(other);
            return *this;
        }

        T &getValue() const {
            return value;
        }

        ~container() {
            if (!--*refcount) {
                delete refcount;
                handler(value);
            }
        }
    };
}
