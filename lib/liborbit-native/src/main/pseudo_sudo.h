#include <libssh2/libssh2.h>

namespace liborbit {
    class pseudo_sudo_process {
    private:
        LIBSSH2_SESSION *session;
        LIBSSH2_CHANNEL *channel;

    public:
        explicit pseudo_sudo_process(const char *command);

        ssize_t read(char *buffer, size_t buffer_size);
        ssize_t write(const char *buffer, size_t buffer_size);
    };
}
