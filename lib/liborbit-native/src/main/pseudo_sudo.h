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

        ssize_t read(char *buffer, size_t buffer_size);
        ssize_t write(const char *buffer, size_t buffer_size);

        virtual ~pseudo_sudo_process();
    };
}
