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
