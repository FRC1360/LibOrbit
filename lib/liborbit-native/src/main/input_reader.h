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
* Nicholas Mertin (2017-09-11) - Restructured main loop
*/

#include <functional>
#include <string>
#include <linux/input.h>
#include "pseudo_sudo.h"

namespace liborbit {
    class input_reader final {
    private:
        pseudo_sudo_process process;
        std::function<void(input_event)> handler;
        std::function<void(std::string)> error_handler;
        
        input_reader(const char *command, std::function<void(input_event)> handler, std::function<void(std::string)> error_handler, void *);

        void handle_status(int code);
        void handle_signal(const char *signal);

    public:
        input_reader(const char *path, std::function<void(input_event)> handler, std::function<void(std::string)> error_handler);

        void run_loop(std::function<bool()> work);
    };
}
