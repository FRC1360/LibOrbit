#ifndef LIBORBIT_NEURAL_NETWORK_ORGANISM_H
#define LIBORBIT_NEURAL_NETWORK_ORGANISM_H

#include "genome.h"
#include "../../common/model.h"

namespace liborbit::neural_network::evolution::neat {
    class organism final {
    public:
        enum origin_t : uint_fast32_t {
            GEN_ZERO,
            MUTATION,
            CROSSBREED
        };

        genome genotype;
        common::network phenotype;
        origin_t origin;
        uint_fast32_t parent1_species;
        uint_fast32_t parent1_index;
        uint_fast32_t parent2_species;
        uint_fast32_t parent2_index;

        organism(const genome &genotype);
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_ORGANISM_H
