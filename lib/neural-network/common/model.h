#ifndef LIBORBIT_NEURAL_NETWORK_MODEL_H
#define LIBORBIT_NEURAL_NETWORK_MODEL_H

#include <cstdio>
#include <cstdint>

namespace liborbit::neural_network::common {
    class node final {
    public:
        uint_fast32_t id;
        float output;

        node();

        node(uint_fast32_t id);
    };

    class connection final {
    public:
        connection();

        node *source;
        node *destination;
        float weight;

        connection(node *source, node *destination, float weight);

        bool operator==(const connection &rhs) const;

        bool operator!=(const connection &rhs) const;
    };

    class network final {
    public:
        node *nodes;
        uint_fast32_t node_count;
        connection *connections;
        uint_fast32_t connection_count;
        node **inputs;
        uint_fast32_t input_count;
        node **outputs;
        uint_fast32_t output_count;
        uint_fast32_t *evaluation_order;

        network(node *nodes, uint_fast32_t node_count, connection *connections, uint_fast32_t connection_count, node **inputs, uint_fast32_t input_count, node **outputs, uint_fast32_t output_count);

        void generate_evaluation_order();
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_MODEL_H
