cmake_minimum_required(VERSION 3.6)
project(liborbit-neural-network)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14")

set(SOURCE_COMMON common/model.cpp common/model.h common/serialization.cpp common/serialization.h common/utilities.cpp common/utilities.h)

set(SOURCE_EVALUATION evaluation/evaluation.cpp evaluation/evaluation.h)

set(SOURCE_NEAT evolution/neat/genome.cpp evolution/neat/genome.h evolution/neat/gene.cpp evolution/neat/gene.h evolution/neat/organism.cpp evolution/neat/organism.h evolution/neat/species.cpp evolution/neat/species.h evolution/neat/generation.cpp evolution/neat/generation.h evolution/neat/environment.cpp evolution/neat/environment.h)

add_executable(liborbit-neural-network-train ${SOURCE_COMMON} ${SOURCE_EVALUATION} ${SOURCE_NEAT})
