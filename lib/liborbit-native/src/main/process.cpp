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

#include "process.h"
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <ext/stdio_filebuf.h>
#include <signal.h>

namespace liborbit {
    process::temp_config process::configure(const char *path, char *const argv[]) {
        int sockets[2];
        socketpair(AF_INET, SOCK_STREAM, 0, &sockets[0]);
        pid_t child = fork();
        if (child < 0) {
            throw "Fork error";
        } else if (child > 0) {
            close(sockets[1]);
            return {child, new __gnu_cxx::stdio_filebuf<char>(sockets[0], std::ios::in | std::ios::out | std::ios::binary)};
        } else {
            close(sockets[0]);
            dup2(sockets[1], 0);
            close(sockets[1]);
            dup2(0, 1);
            dup2(0, 2);
            execvp(path, argv);
        }
    }

    process::process(temp_config config) : pid(config.pid), proc_in(config.buf), proc_out_err(config.buf) {}

    process::process(const char *path, char *const argv[]) : process(configure(path, argv)) {}

    process::~process() {
        kill(pid, SIGTERM);
    }
}
