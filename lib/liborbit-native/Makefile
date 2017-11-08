# Copyright 2017 Oakville Community FIRST Robotics
#
# This file is part of LibOrbit.
#
# LibOrbit is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# LibOrbit is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
#
# Contributions:
#
# Nicholas Mertin (2017-08-29) - Created file
# Nicholas Mertin (2017-08-30) - Fixed compilation issues by adding -fPIC flag and enabling GNU extensions for C++
# Nicholas Mertin (2017-09-04) - Fixed filename extension typo

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
	mkdir -p obj/jni
	$(CXX) $(CXXFLAGS) -c $< -o $@

obj/main/%.o: src/main/%.cpp
	mkdir -p obj/main
	$(CXX) $(CXXFLAGS) -c $< -o $@

obj/asm/%.o: src/asm/%.S
	mkdir -p obj/asm
	$(CC) $(CFLAGS) -c $< -o $@

deps:
	$(MAKE) -C deps all

bin/liborbit-native.so: deps $(JNI_OBJ) $(MAIN_OBJ) $(ASM_OBJ) $(DEPS)
	mkdir -p bin
	$(CXX) $(LDFLAGS) $(JNI_OBJ) $(MAIN_OBJ) $(ASM_OBJ) $(DEPS) -o $@
