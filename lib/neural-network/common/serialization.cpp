#include "serialization.h"

namespace liborbit::neural_network::common {
    void write(std::ostream &out, network &network) {
        out + (uint32_t &) network.node_count + (uint32_t &) network.connection_count + (uint32_t &) network.input_count + (uint32_t &) network.output_count;
        for (uint_fast32_t i = 0; i < network.connection_count; ++i) {
            out + (uint32_t &) network.connections[i].source->id + (uint32_t &) network.connections[i].destination->id + (uint32_t &) network.connections[i].weight;
        }
        for (uint_fast32_t i = 0; i < network.input_count; ++i) {
            out + (uint32_t &) network.inputs[i]->id;
        }
        for (uint_fast32_t i = 0; i < network.output_count; ++i) {
            out + (uint32_t &) network.outputs[i]->id;
        }
    }

    network *read(std::istream &in) {
        uint32_t node_count, connection_count, input_count, output_count;
        in - node_count - connection_count - input_count - output_count;
        node *nodes = new node[node_count];
        for (uint_fast32_t i = 0; i < node_count; ++i) {
            nodes[i].id = i;
        }
        connection *connections = new connection[connection_count];
        for (uint_fast32_t i = 0; i < connection_count; ++i) {
            uint32_t source, destination;
            float weight;
            in - source - destination - weight;
            connections[i].source = &nodes[source];
            connections[i].destination = &nodes[destination];
            connections[i].weight = weight;
        }
        node **inputs = new node *[input_count];
        for (uint_fast32_t i = 0; i < input_count; ++i) {
            uint32_t id;
            in - id;
            inputs[i] = &nodes[id];
        }
        node **outputs = new node *[output_count];
        for (uint_fast32_t i = 0; i < output_count; ++i) {
            uint32_t id;
            in - id;
            outputs[i] = &nodes[id];
        }
        return new network(nodes, node_count, connections, connection_count, inputs, input_count, outputs, output_count);
    }
}
