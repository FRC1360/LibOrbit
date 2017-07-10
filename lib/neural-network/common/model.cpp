#include "model.h"
#include <cmath>

using namespace liborbit::neural_network::common;

node::node(size_t id) : id(id) {
    output = NAN;
}

connection::connection(node *source, node *destination, double weight) : source(source), destination(destination), weight(weight) {}

network::network(node *nodes, size_t node_count, connection *connections, size_t connection_count) : nodes(nodes), node_count(node_count), connections(connections), connection_count(connection_count) {}
