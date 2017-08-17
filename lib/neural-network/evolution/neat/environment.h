#ifndef LIBORBIT_NEURAL_NETWORK_ENVIRONMENT_H
#define LIBORBIT_NEURAL_NETWORK_ENVIRONMENT_H

#include "generation.h"

namespace liborbit::neural_network::evolution::neat {
    class environment final {
    public:
        generation current_gen;
        const char *root_directory;

        environment(const char *root_directory);
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_ENVIRONMENT_H
