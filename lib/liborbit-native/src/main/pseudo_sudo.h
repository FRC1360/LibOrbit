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

#include <libssh2/libssh2.h>
#include <functional>

namespace liborbit {
    class pseudo_sudo_process {
    private:
        int sock;
        LIBSSH2_SESSION *session;
        LIBSSH2_CHANNEL *channel;
        std::function<void(int)> exit_status_handler;
        std::function<void(char *)> exit_signal_handler;

    public:
        pseudo_sudo_process(const char *command, std::function<void(int)> exit_status_handler, std::function<void(char *)> exit_signal_handler);

        pseudo_sudo_process(const pseudo_sudo_process &other) = delete;
        pseudo_sudo_process &operator=(const pseudo_sudo_process &other) = delete;
        
        ssize_t read(char *buffer, size_t buffer_size);
        ssize_t write(const char *buffer, size_t buffer_size);

        virtual ~pseudo_sudo_process();
    };
}
