#ifndef LIBORBIT_NEURAL_NETWORK_GENERATION_H
#define LIBORBIT_NEURAL_NETWORK_GENERATION_H

#include <vector>
#include "species.h"

namespace liborbit::neural_network::evolution::neat {
    class generation final {
    public:
        std::vector<species> organism_species;
        uint_fast32_t number;

        generation(uint_fast32_t number);
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_GENERATION_H
