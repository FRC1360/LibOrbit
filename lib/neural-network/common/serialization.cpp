#include "serialization.h"

namespace liborbit::neural_network::common {
    void write(std::ostream &out, network &network) {
        out + network.node_count + network.connection_count + network.input_count + network.output_count;
        for (__uint64_t i = 0; i < network.connection_count; ++i) {
            out + network.connections[i].source->id + network.connections[i].destination->id + network.connections[i].weight;
        }
        for (__uint64_t i = 0; i < network.input_count; ++i) {
            out + network.inputs[i]->id;
        }
        for (__uint64_t i = 0; i < network.output_count; ++i) {
            out + network.outputs[i]->id;
        }
    }

    network *read(std::istream &in) {
        __uint64_t node_count, connection_count, input_count, output_count;
        in - node_count - connection_count - input_count - output_count;
        node *nodes = new node[node_count];
        for (__uint64_t i = 0; i < node_count; ++i) {
            nodes[i].id = i;
        }
        connection *connections = new connection[connection_count];
        for (__uint64_t i = 0; i < connection_count; ++i) {
            __uint64_t source, destination;
            double weight;
            in - source - destination - weight;
            connections[i].source = &nodes[source];
            connections[i].destination = &nodes[destination];
            connections[i].weight = weight;
        }
        node **inputs = new node *[input_count];
        for (__uint64_t i = 0; i < input_count; ++i) {
            __uint64_t id;
            in - id;
            inputs[i] = &nodes[id];
        }
        node **outputs = new node *[output_count];
        for (__uint64_t i = 0; i < output_count; ++i) {
            __uint64_t id;
            in - id;
            outputs[i] = &nodes[id];
        }
        return new network(nodes, node_count, connections, connection_count, inputs, input_count, outputs, output_count);
    }
}
