#ifndef LIBORBIT_NEURAL_NETWORK_EVALUATION_H
#define LIBORBIT_NEURAL_NETWORK_EVALUATION_H

#include "../common/model.h"

namespace liborbit::neural_network::evaluation {
    void evaluate(common::network *network, double *inputs, double *outputs);
}

#endif //LIBORBIT_NEURAL_NETWORK_EVALUATION_H
