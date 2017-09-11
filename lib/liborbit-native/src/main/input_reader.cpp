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
