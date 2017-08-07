#include "evaluation.h"
#include <cstdint>

namespace liborbit::neural_network::evaluation {
    void evaluate(common::network *network, double *inputs, double *outputs) {
        for (uint_fast32_t i = 0; i < network->node_count; ++i) {
            network->nodes[i].output = 0;
        }
        for (uint_fast32_t i = 0; i < network->input_count; ++i) {
            network->inputs[i]->output = inputs[i];
        }
        for (uint_fast32_t i = 0; i < network->connection_count; ++i) {
            common::connection &conn = network->connections[network->evaluation_order[i]];
            conn.destination->output += conn.source->output * conn.weight;
        }
        for (uint_fast32_t i = 0; i < network->output_count; ++i) {
            outputs[i] = network->outputs[i]->output;
        }
    }
}
