#ifndef LIBORBIT_NEURAL_NETWORK_SPECIES_H
#define LIBORBIT_NEURAL_NETWORK_SPECIES_H

#include <vector>
#include "organism.h"

namespace liborbit::neural_network::evolution::neat {
    class species final {
    public:
        uint_fast32_t generation;
        std::vector<organism> organisms;
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_SPECIES_H
