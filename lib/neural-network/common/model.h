#ifndef LIBORBIT_MODEL_H
#define LIBORBIT_MODEL_H

#include <cstdio>

namespace liborbit::neural_network::common {
    class node final {
    public:
        size_t id;
        double output;

        node(size_t id);
    };

    class connection final {
    public:
        node *source;
        node *destination;
        double weight;

        connection(node *source, node *destination, double weight);
    };

    class network final {
    public:
        node *nodes;
        size_t node_count;
        connection *connections;
        size_t connection_count;

        network(node *nodes, size_t node_count, connection *connections, size_t connection_count);
    };
}

#endif //LIBORBIT_MODEL_H
