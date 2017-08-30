#include <iostream>
#include <sys/types.h>
#include <ext/stdio_filebuf.h>

namespace liborbit {
    class pseudo_sudo_process final {
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

        pseudo_sudo_process(temp_config config);

    public:
        pseudo_sudo_process(const char *path, char *const argv[]);

        pseudo_sudo_process(pseudo_sudo_process const&) = delete;
        pseudo_sudo_process &operator=(pseudo_sudo_process const&) = delete;

        ~pseudo_sudo_process();
    };
}
