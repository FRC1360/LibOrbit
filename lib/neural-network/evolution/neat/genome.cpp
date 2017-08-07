#include "genome.h"
#include <algorithm>

namespace liborbit::neural_network::evolution::neat {
    genome::genome(const std::vector<gene> &genes, const std::vector<uint_fast32_t> &inputs, const std::vector<uint_fast32_t> &outputs) : genes(genes), inputs(inputs), outputs(outputs) {}

    common::network genome::generate_network() const {
        uint_fast32_t max_node_id = 0;
        for (auto &gene : genes) {
            if (gene.enabled) {
                if (gene.in > max_node_id) {
                    max_node_id = gene.in;
                }
                if (gene.out > max_node_id) {
                    max_node_id = gene.out;
                }
            }
        }
        for (auto in : inputs) {
            if (in > max_node_id) {
                max_node_id = in;
            }
        }
        for (auto out : outputs) {
            if (out > max_node_id) {
                max_node_id = out;
            }
        }
        common::node *nodes = new common::node[max_node_id + 1];
        for (uint_fast32_t i = 0; i <= max_node_id; ++i) {
            nodes[i].id = i;
        }
        std::vector<common::connection> connections;
        for (auto &gene : genes) {
            if (gene.enabled) {
                connections.push_back(common::connection(&nodes[gene.in], &nodes[gene.out], gene.weight));
            }
        }
        common::connection *connections_ptr = new common::connection[connections.size()];
        std::copy(connections.begin(), connections.end(), connections_ptr);
        common::node **inputs_ptr = new common::node *[inputs.size()];
        std::transform(inputs.begin(), inputs.end(), inputs_ptr, [nodes] (uint_fast32_t i) {
            return nodes + i;
        });
        common::node **outputs_ptr = new common::node *[outputs.size()];
        std::transform(outputs.begin(), outputs.end(), outputs_ptr, [nodes] (uint_fast32_t i) {
            return nodes + i;
        });
        return common::network(nodes, max_node_id + 1, connections_ptr, connections.size(), inputs_ptr, inputs.size(), outputs_ptr, outputs.size());
    }
}
