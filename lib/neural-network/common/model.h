#ifndef LIBORBIT_MODEL_H
#define LIBORBIT_MODEL_H

#include <cstdio>

namespace liborbit::neural_network::common {
    class node final {
    public:
        __uint64_t id;
        double output;

        node();

        node(__uint64_t id);
    };

    class connection final {
    public:
        connection();

        node *source;
        node *destination;
        double weight;

        connection(node *source, node *destination, double weight);

        bool operator==(const connection &rhs) const;

        bool operator!=(const connection &rhs) const;
    };

    class network final {
    public:
        node *nodes;
        __uint64_t node_count;
        connection *connections;
        __uint64_t connection_count;
        node **inputs;
        __uint64_t input_count;
        node **outputs;
        __uint64_t output_count;
        __uint64_t *evaluation_order;

        network(node *nodes, __uint64_t node_count, connection *connections, __uint64_t connection_count, node **inputs, __uint64_t input_count, node **outputs, __uint64_t output_count);

        void generate_evaluation_order();
    };
}

#endif //LIBORBIT_MODEL_H
