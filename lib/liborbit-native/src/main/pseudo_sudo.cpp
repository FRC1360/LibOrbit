#include "pseudo_sudo.h"
#include <string>
#include <arpa/inet.h>

// From the libssh2 examples
static int waitsocket(int socket_fd, LIBSSH2_SESSION *session)
{
    struct timeval timeout;
    int rc;
    fd_set fd;
    fd_set *writefd = NULL;
    fd_set *readfd = NULL;
    int dir;
 
    timeout.tv_sec = 10;
    timeout.tv_usec = 0;
 
    FD_ZERO(&fd);
 
    FD_SET(socket_fd, &fd);
 
    /* now make sure we wait in the correct direction */ 
    dir = libssh2_session_block_directions(session);

 
    if(dir & LIBSSH2_SESSION_BLOCK_INBOUND)
        readfd = &fd;
 
    if(dir & LIBSSH2_SESSION_BLOCK_OUTBOUND)
        writefd = &fd;
 
    rc = select(socket_fd + 1, readfd, writefd, NULL, &timeout);
 
    return rc;
}

namespace liborbit {
    pseudo_sudo_process::pseudo_sudo_process(const char *command) {
        int rc = libssh2_init(0);
        if (rc) {
            char error[64];
            sprintf(error, "Failed to initialize libssh2 (%d)!", rc);
            throw std::string(error);
        }
        in_addr_t addr = htons(0x7F000001);
        int sock = socket(AF_INET, SOCK_STREAM, 0);
        sockaddr_in sin;
        sin.sin_family = AF_INET;
        sin.sin_port = htons(22);
        sin.sin_addr.s_addr = addr;
        if (connect(sock, (sockaddr *)&sin, sizeof(sockaddr_in))) {
            throw std::string("Failed to connect!");
        }
        session = libssh2_session_init();
        if (!session) {
            throw std::string("Failed to initialize libssh2 session!");
        }
        libssh2_session_set_blocking(session, 0);
        while ((rc = libssh2_session_handshake(session, sock)) == LIBSSH2_ERROR_EAGAIN);
        if (rc) {
            char error[64];
            sprintf(error, "Failed to initialize libssh2 (%d)!", rc);
            throw std::string(error);
        }
        while ((rc = libssh2_userauth_password(session, "Admin", "")) == LIBSSH2_ERROR_EAGAIN);
        if (rc) {
            char error[64];
            sprintf(error, "Failed to authenticate (%d)!", rc);
            throw std::string(error);
        }
        while ((channel = libssh2_channel_open_session(session)) == NULL && libssh2_session_last_error(session, NULL, NULL, 0) == LIBSSH2_ERROR_EAGAIN) {
            waitsocket(sock, session);
        }
        if (channel == NULL) {
            throw std::string("Failed to open channel!");
        }
        while ((rc = libssh2_channel_exec(channel, command)) == LIBSSH2_ERROR_EAGAIN) {
            waitsocket(sock, session);
        }
        if (rc) {
            char error[64];
            sprintf(error, "Failed to execute command (%d)!", rc);
        }
    }

    ssize_t pseudo_sudo_process::read(char *buffer, size_t buffer_size) {
        return libssh2_channel_read(channel, buffer, buffer_size);
    }

    ssize_t pseudo_sudo_process::write(const char *buffer, size_t buffer_size) {
        return libssh2_channel_write(channel, buffer, buffer_size);
    }
}
