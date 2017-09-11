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
