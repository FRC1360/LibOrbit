#ifndef LIBORBIT_NEURAL_NETWORK_SERIALIZATION_H
#define LIBORBIT_NEURAL_NETWORK_SERIALIZATION_H

#include <iostream>
#include "model.h"
#include "utilities.h"

namespace liborbit::neural_network::common {
    void write(std::ostream &out, network &network);

    network *read(std::istream &in);
}

#endif //LIBORBIT_NEURAL_NETWORK_SERIALIZATION_H
