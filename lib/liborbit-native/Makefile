CC=gcc
CXX=g++
RM=rm -f
CFLAGS=-g -O3 -fPIC -std=c11
CXXFLAGS=-g -O3 -fPIC -std=gnu++11 "-I$(JAVA_HOME)/include" "-I$(JAVA_HOME)/include/linux" -Ideps/include
LDFLAGS=-shared -O3

JNI_SRC:=$(wildcard src/jni/*.cpp)
MAIN_SRC:=$(wildcard src/main/*.cpp)
ASM_SRC:=$(wildcard src/asm/*.S)

JNI_OBJ:=$(addprefix obj/jni/,$(notdir $(JNI_SRC:.cpp=.o)))
MAIN_OBJ:=$(addprefix obj/main/,$(notdir $(MAIN_SRC:.cpp=.o)))
ASM_OBJ:=$(addprefix obj/asm/,$(notdir $(ASM_SRC:.S=.o)))

DEPS:=$(wildcard deps/*.a)

.PHONY: all clean deps ;
all: bin/liborbit-native.so

clean:
	$(RM) bin/liborbit-native.so $(wildcard obj/jni/*.o) $(wildcard obj/main/*.o) $(wildcard obj/asm/*.o)

obj/jni/%.o: src/jni/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

obj/main/%.o: src/main/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

obj/asm/%.o: src/asm/%.S
	$(CC) $(CFLAGS) -c $< -o $@

deps:
	$(MAKE) -C deps all

bin/liborbit-native.so: deps $(JNI_OBJ) $(MAIN_OBJ) $(ASM_OBJ) $(DEPS)
	$(CXX) $(LDFLAGS) $(JNI_OBJ) $(MAIN_OBJ) $(ASM_OBJ) $(DEPS) -o $@
