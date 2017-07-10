#ifndef LIBORBIT_SERIALIZATION_H
#define LIBORBIT_SERIALIZATION_H

#include <iostream>
#include "model.h"
#include "utilities.h"

namespace liborbit::neural_network::common {
    void write(std::ostream &out, network &network);

    network *read(std::istream &in);
}

#endif //LIBORBIT_SERIALIZATION_H
