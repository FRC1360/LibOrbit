#ifndef LIBORBIT_NEURAL_NETWORK_GENOME_H
#define LIBORBIT_NEURAL_NETWORK_GENOME_H

#include <cstdint>
#include <vector>
#include "gene.h"
#include "../../common/model.h"

namespace liborbit::neural_network::evolution::neat {
    class genome final {
    public:
        std::vector<gene> genes;
        std::vector<uint_fast32_t> inputs;
        std::vector<uint_fast32_t> outputs;

        genome(const std::vector<gene> &genes, const std::vector<uint_fast32_t> &inputs, const std::vector<uint_fast32_t> &outputs);

        common::network generate_network() const;
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_GENOME_H
