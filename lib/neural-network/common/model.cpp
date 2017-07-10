#include "model.h"
#include <cmath>

namespace liborbit::neural_network::common {
    node::node(__uint64_t id) : node(), id(id) {}

    node::node() : output(NAN) {}

    connection::connection(node *source, node *destination, double weight) : source(source), destination(destination), weight(weight) {}

    connection::connection() : source(NULL), destination(NULL) {}

    network::network(node *nodes, __uint64_t node_count, connection *connections, __uint64_t connection_count, node **inputs, __uint64_t input_count, node **outputs, __uint64_t output_count) : nodes(nodes), node_count(node_count), connections(connections), connection_count(connection_count), inputs(inputs), input_count(input_count), outputs(outputs), output_count(output_count) {}
}
