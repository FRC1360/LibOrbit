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

#include "input_reader.h"
#include <cstring>
#include <sstream>

static const char *make_command(const char *path) {
    char *command = new char[strlen(path) + 6];
    sprintf(command, "cat \"%s\"", path);
    return command;
}

namespace liborbit {
    input_reader::input_reader(const char *command, std::function<void(input_event)> handler, std::function<void(std::string)> error_handler, void *) : process(command, [this] (int status) { handle_status(status); }, [this] (char *signal) { handle_signal(signal); }), handler(handler), error_handler(error_handler) {
        delete[] command;
    }

    input_reader::input_reader(const char *path, std::function<void(input_event)> handler, std::function<void(std::string)> error_handler) : input_reader(make_command(path), handler, error_handler, nullptr) {}

    void input_reader::handle_status(int status) {
        if (status) {
            std::ostringstream error;
            error << "Status code: " << status;
            error_handler(error.str());
        }
    }

    void input_reader::handle_signal(const char *signal) {
        std::ostringstream error;
        error << "Signal: " << signal;
        error_handler(error.str());
    }

    void input_reader::run_loop(std::function<bool()> work) {
        while (work()) {
            input_event event;
        }
    }
}
