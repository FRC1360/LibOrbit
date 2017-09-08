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
