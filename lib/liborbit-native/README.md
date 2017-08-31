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
