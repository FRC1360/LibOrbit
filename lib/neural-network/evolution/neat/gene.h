#ifndef LIBORBIT_NEURAL_NETWORK_GENE_H
#define LIBORBIT_NEURAL_NETWORK_GENE_H

#include <cstdint>

namespace liborbit::neural_network::evolution::neat {
    class gene final {
    public:
        uint_fast32_t in;
        uint_fast32_t out;
        float weight;
        bool enabled;
        uint_fast32_t id;

        gene(uint_fast32_t in, uint_fast32_t out, float weight, bool enabled, uint_fast32_t id);
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_GENE_H
