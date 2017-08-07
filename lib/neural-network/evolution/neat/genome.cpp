#include "genome.h"
#include <cstddef>

namespace liborbit::neural_network::evolution::neat {
    genome::genome(const std::vector<gene> &genes, const std::vector<uint_fast32_t> &inputs, const std::vector<uint_fast32_t> &outputs) : genes(genes), inputs(inputs), outputs(outputs) {}
}
