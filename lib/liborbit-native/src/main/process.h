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
