#include "gene.h"

namespace liborbit::neural_network::evolution::neat {
    gene::gene(uint_fast32_t in, uint_fast32_t out, float weight, bool enabled, uint_fast32_t id) : in(in), out(out), weight(weight), enabled(enabled), id(id) {}
}
