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
* Nicholas Mertin (2017-08-30) - Created file
* Nicholas Mertin (2017-09-01) - Renamed from `pseudo_sudo` to `process`
*/

#include <iostream>
#include <sys/types.h>
#include <ext/stdio_filebuf.h>

namespace liborbit {
    class process {
    public:
        const std::ostream proc_in;
        const std::istream proc_out_err;

    private:
        const pid_t pid;

        struct temp_config {
            pid_t pid;
            __gnu_cxx::stdio_filebuf<char> *buf;
        };

        static temp_config configure(const char *path, char *const argv[]);

        process(temp_config config);

    public:
        process(const char *path, char *const argv[]);

        process(process const&) = delete;
        process &operator=(process const&) = delete;

        virtual ~process();
    };
}
