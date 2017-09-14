# LibOrbit Native

This library contains all parts of LibOrbit which consist of native (i.e. C, C++, Assembly, etc.) code that needs to be executed on the RoboRIO. This file describes the structure of this library, along with a list of compilation units included, and their purpose.

## Structure

All compilation units (i.e. source files that are not header files) grouped into one of three categories by their content:

| Category       | Source Directory | Object Directory |
|----------------|------------------|------------------|
| JNI Connectors | `src/jni`        | `obj/jni`        |
| Main Code      | `src/main`       | `obj/main`       |
| Assembly       | `src/asm`        | `obj/asm`        |

'Source Directory' refers to the location of original, (mostly) human-written source code files. 'Object directory' refers to the location of object files, which are an intermediate step in the compilation of native libraries. For more information, see [Compilation Process](#compilation-process) for more information.

## JNI Connectors

This category of compilation units contains C++ code that interacts directly with Java code; all accessible functions should be implementations of Java `native` methods. Header files (`.h`) should be generated using the [`javah`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javah.html) utility, and source files (`.cpp`) should be created with matching names. Code in this category should not directly implement any non-trivial functionality; such functionality should be implemented in the [Main Code](#main-code) category, and should simply be referenced from here.

A description of each module in this category follows.

### ca._1360.liborbit.util.jni.OrbitInputEventsReader

This module provides asynchronous reading of event objects from Linux input character device files (usually located in `/dev/input`). As each event is read, the `handleInternal` Java function is called with the appropriate information.

## Main Code

This category contains C++ code that implements the core functionality requiring native code. Code here should be written, within reason, as if it were to be consumed by a native C++ program (as opposed to a Java program); this means that it should not be directly dependent on the JNI or on any code in `src/jni`. Source code should be in `.cpp` files with matching `.h` header files.

A description of each module in this category follows.

### container

This module provides a simple reference-counting value wrapper that executes custom code when the last reference is destroyed; it takes advantage of C++ destructors to provide this functionality. It is designed to be used as a utility by other parts of the main code.

### pseudo_sudo

This module implements functionality based on that of the `sudo` command present on many Unix-like systems; namely, it allows a command to be run with escalated permissions. However, due to the not-api-friendly nature of priviledge escalation software available on the RoboRIO, the best available method is to use the secure shell protocol (SSH) to connect to the RoboRIO as the Admin user. This is done using libssh2.

### process

This module provides the ability to create child processes, and to interact with the standard input, output and error streams. This is done using the `fork` and `execve` syscalls.

### input_reader

This module uses `pseudo_sudo` to provide continuous reading of event objects from Linux input character device files (usually located in `/dev/input`), calling a custom callback with each event object.

## Assembly

This category contains code written in ARM assembly to implement specific high-performance sections of code that would be too inefficient if implemented in standard C++. Code here should be written using the standard A32 calling convention, as if it were to be used by standard C code. Source code should be in `.S` files, with matching `.h` header files compatible with C and C++.

There are not yet any modules in this category.

## Compilation Instructions

Compilation should be performed with GCC and GNU Binutils targeted at ARM Linux. This can either be done natively, or with a cross-compiler; however, a cross-compiler will require additional setup, and has not been tested. If a prefix is required for `gcc` and `g++` commands, it should be put in the `PREFIX` environment variable.

Additionally, development binaries for one of the following cryptography libraries must be installed on the local system; this is required for compiling libssh2:

- OpenSSL
- Libgcrypt
- WinCNG
- mbedTLS

To compile the library, open a POSIX-compatible shell (such as `bash`) in this directory, and run `make`. If the command is not found, then you should download GNU Make from your Linux distribution's repositories, or from [GNU Savannah](http://savannah.gnu.org/projects/make).

This command ensures that all dependencies are up to date, configured, and compiled, and then compiles the main liborbit-native code. it will show any warnings and errors, and if the compilation is succesful, create the final library in `bin/liborbit-native.so`.
