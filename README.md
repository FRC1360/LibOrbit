# LibOrbit

An abstraction layer for FRC programming, developed by Team 1360 Orbit Robotics.

## Features Overview

LibOrbit includes the following feature libraries:

* Miscellaneous programming utilities
* Functional programming utilities
* Pipeline system
* Log controller
* Autonomous controller
* Generalized state machine implementation
* Abstracted I/O APIs to remove hardware dependency
* I/O emulation
* Network API server

## Miscellaneous Programming Utilities

This section includes all APIs that do not belong in any other section. They largely consist of short procedures that would be otherwise repeated.

### Container

Glorified variable to get around Java's final requirement for closure (lambda or anonymous inner class) captures.

### Directed Acyclic Graph

[Wikipedia](https://en.wikipedia.org/wiki/Directed_acyclic_graph) has a good definition of this container data structure. This implementation allows for changes to applied in bulk, rolling back changes if any change results in an error, or if topological sorting shows that the final result has a directed cycle. [Khan's algorithm](https://en.wikipedia.org/wiki/Topological_sorting#Kahn.27s_algorithm) is used for topological sorting.

### Multi-Channel Stream

This utility splits a single duplex connection (represented by an `InputStream` and an `OutputStream` in Java) into up to 256 separate sub-connections (each also represented by an `InputStream` and an `OutputStream`). Data transmission is split into frames which start with the channel number (a single byte), followed by the data length (two bytes in big-endian order), and finally the data. This allows for up to 64 KiB to be transmitted in a single frame.

### Java 8 Stream Generation

Three factory functions were created for the generation of Java 8 streams: from an iterator, from a supplier of `Optional` values, and from a series of existing streams (multiple-term concatenation).

### Reading From a Stream

A function was created to read a set number of bytes from an `InputStream` into an array, retrying if only some data could be read.

### Try to Stop a Thread in an I/O Operation

A function was created to attempt to stop a thread, and if it fails, throws an `InterruptedIOException` with the given message.

## Functional Programming Utilities

This section contains tools to work with Java 8 functional interfaces.

### Exceptional Versions of Functional Interfaces

A version of each standard functional interface was created that has an additional type parameter for exception type; its application function contains a `throws` declaration for this type. This allows for methods that could result in a checked exception to be passed around like any other (albeit the consuming code must support these types). This was particularly useful while working with stream I/O.

### Function Combination and Transformation

A set of transformations was created for working with functional interfaces:

* Exception wrapping methods: generate a non-exceptional functional interface from an exceptional one by providing a function to wrap the throwable in an unchecked exception
* Exception handling methods: generate a non-exceptional functional interface from an exceptional one by providing a consumer to handle the throwable that occurs
* Specialization methods: specify the value for first, second, both, or only parameter(s) of a functional interface; exceptional versions are suffixed with -Ex
* Supplier specialization methods: specify a supplier to produce the first, second, both, or only parameter(s) of a functional interface; exceptional versions are suffixed with -Ex
* Source supplying methods: produce a single-parameter functional interface from a dual-parameter one by providing a function to map each parameter; exceptional versions are suffixed with -Ex
* Combining methods: produce a single result-less functional interface from multiple that executes them in order; exceptional versions are suffixed with -Ex
* Composing methods: produce a single function from multiple that executes them in order, using the result of each as the parameter for the next; all values must be the same type due to Java generics limitations; exceptional versions are suffixed with -Ex
* All/any methods: produce a single predicate-type functional interface from multiple that requires that all or any to approve the parameter(s); exceptional versions are suffixed with -Ex
* Conditional methods: produce a result-less functional interface from an equivalent that executes it only if the given boolean supplier or predicate-type functional interface approves; exceptional versions are suffixed with -Ex
