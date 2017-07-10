#ifndef LIBORBIT_NEURAL_NETWORK_UTILITIES_H
#define LIBORBIT_NEURAL_NETWORK_UTILITIES_H

#include <iostream>

namespace liborbit::neural_network::common {
    template<class value_type>
    inline std::ostream &operator+(std::ostream &stream, value_type &value) {
        stream.write(&value, sizeof(value_type));
        return stream;
    }

    template<class value_type>
    inline std::istream &operator-(std::istream &stream, value_type &value) {
        stream.read(&value, sizeof(value_type));
        return stream;
    }
}

#endif //LIBORBIT_NEURAL_NETWORK_UTILITIES_H
