#include "model.h"
#include <cmath>
#include "utilities.h"

namespace liborbit::neural_network::common {
    node::node(__uint64_t id) : node() {
        this->id = id;
    }

    node::node() : output(NAN) {}

    connection::connection(node *source, node *destination, double weight) : source(source), destination(destination), weight(weight) {}

    connection::connection() : source(NULL), destination(NULL) {}

    bool connection::operator==(const connection &rhs) const {
        return source == rhs.source && destination == rhs.destination;
    }

    bool connection::operator!=(const connection &rhs) const {
        return !(rhs == *this);
    }

    network::network(node *nodes, __uint64_t node_count, connection *connections, __uint64_t connection_count, node **inputs, __uint64_t input_count, node **outputs, __uint64_t output_count) : nodes(nodes), node_count(node_count), connections(connections), connection_count(connection_count), inputs(inputs), input_count(input_count), outputs(outputs), output_count(output_count), evaluation_order(NULL), evaluation_order_count(0) {}

    void network::generate_evaluation_order() {
        directed_acyclic_graph<node *, connection, &connection::source, &connection::destination> graph;
        graph.values.reserve(node_count - input_count);
        for (__uint64_t i = 0; i < node_count; ++i) {
            for (__uint64_t j = 0; j < input_count; ++j) {
                if (inputs[j] == &nodes[i]) {
                    goto input;
                }
            }
            graph.values.push_back(&nodes[i]);
            input: continue;
        }
        std::copy_if(connections, connections + connection_count, graph.connections.begin(), [this] (const connection &current) {
            for (__uint64_t i = 0; i < input_count; ++i) {
                if (inputs[i] == current.source) {
                    return false;
                }
            }
            return true;
        });
        if (!graph.sort()) {
            throw "Neural network has directed cycles!";
        }
        if (evaluation_order) {
            delete[] evaluation_order;
        }
        evaluation_order = new node *[graph.values.size()];
        std::copy(graph.values.begin(), graph.values.end(), evaluation_order);
    }
}
