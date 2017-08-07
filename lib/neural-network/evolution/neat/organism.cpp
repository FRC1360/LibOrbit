#include "organism.h"

namespace liborbit::neural_network::evolution::neat {
    organism::organism(const genome &genotype) : genotype(genotype), phenotype(genotype.generate_network()) {}
}
