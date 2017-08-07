#include "model.h"
#include <cmath>
#include "utilities.h"

namespace liborbit::neural_network::common {
    node::node(uint_fast32_t id) : node() {
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

    network::network(node *nodes, uint_fast32_t node_count, connection *connections, uint_fast32_t connection_count, node **inputs, uint_fast32_t input_count, node **outputs, uint_fast32_t output_count) : nodes(nodes), node_count(node_count), connections(connections), connection_count(connection_count), inputs(inputs), input_count(input_count), outputs(outputs), output_count(output_count), evaluation_order(NULL) {}

    void network::generate_evaluation_order() {
        directed_acyclic_graph<uint_fast32_t> graph;
        graph.values.reserve(node_count - input_count);
        for (uint_fast32_t i = 0; i < connection_count; ++i) {
            graph.values.push_back(i);
        }
        for (uint_fast32_t i = 0; i < connection_count; ++i) {
            for (uint_fast32_t j = i + 1; j < connection_count; ++j) {
                if (connections[i].source == connections[j].destination) {
                    graph.connections.push_back({j, i});
                }
                else if(connections[j].source == connections[i].destination) {
                    graph.connections.push_back({i, j});
                }
            }
        }
        if (!graph.sort()) {
            throw "Neural network has directed cycles!";
        }
        if (evaluation_order) {
            delete[] evaluation_order;
        }
        evaluation_order = new uint_fast32_t[graph.values.size()];
        std::copy(graph.values.begin(), graph.values.end(), evaluation_order);
    }
}
