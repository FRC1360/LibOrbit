cmake_minimum_required(VERSION 3.6)
project(liborbit-neural-network)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14")

set(SOURCE_COMMON common/model.cpp common/model.h)

add_executable(liborbit-neural-network-train ${SOURCE_COMMON})
