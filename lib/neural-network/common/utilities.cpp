#include <cmath>
#include "utilities.h"

namespace liborbit::neural_network::common {
    double sigmoid(double value) {
        return 1 / (1 + exp(value));
    }
}
