#ifndef LIBORBIT_NEURAL_NETWORK_UTILITIES_H
#define LIBORBIT_NEURAL_NETWORK_UTILITIES_H

#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
#include <stack>
#include <functional>

namespace liborbit::neural_network::common {
    double sigmoid(double value);

    template<class value_type>
    inline std::ostream &operator+(std::ostream &stream, value_type &value) {
        stream.write((const char *) &value, sizeof(value_type));
        return stream;
    }

    template<class value_type>
    inline std::istream &operator-(std::istream &stream, value_type &value) {
        stream.read((char *) &value, sizeof(value_type));
        return stream;
    }

    template<class value_type>
    class directed_acyclic_graph_default_connection {
    public:
        value_type source;
        value_type destination;

        directed_acyclic_graph_default_connection(value_type source, value_type destination) : source(source), destination(destination) {}

        bool operator==(const directed_acyclic_graph_default_connection &rhs) const {
            return source == rhs.source && destination == rhs.destination;
        }

        bool operator!=(const directed_acyclic_graph_default_connection &rhs) const {
            return !(rhs == *this);
        }
    };

    template<class value_type, class connection_type = directed_acyclic_graph_default_connection<value_type>, value_type connection_type::*source = &directed_acyclic_graph_default_connection<value_type>::source, value_type connection_type::*destination = &directed_acyclic_graph_default_connection<value_type>::destination>
    class directed_acyclic_graph {
    public:
        std::vector<value_type> values;
        std::vector<connection_type> connections;

        bool sort() {
            std::vector<value_type> output;
            output.reserve(values.size());
            std::stack<value_type> no_incoming;
            std::list<connection_type> unused_connections;
            std::copy(connections.begin(), connections.end(), unused_connections.begin());
            for (auto value : values) {
                for (auto &conn : connections) {
                    if (conn.*destination == value) {
                        goto incoming;
                    }
                }
                no_incoming.push(value);
                incoming: continue;
            }
            while (!no_incoming.empty()) {
                auto node = no_incoming.top();
                no_incoming.pop();
                output.push_back(node);
                for (auto target : values) {
                    for (auto &conn : unused_connections) {
                        if (conn.*source == node && conn.*destination == target) {
                            unused_connections.remove(conn);
                            goto valid;
                        }
                    }
                    continue;
                    valid:
                    for (auto &conn : unused_connections) {
                        if (conn.*source == node && conn.*destination == target) {
                            goto others;
                        }
                    }
                    no_incoming.push(target);
                    others: continue;
                }
            }
            if (unused_connections.empty()) {
                values.swap(output);
                return true;
            }
            return false;
        }
    };

    template<class T>
    class container final {
    private:
        T value;
        std::function<void(T &)> handler;

    public:
        container(T value, const std::function<void(T &)> &handler) : value(value), handler(handler) {}

        T &getValue() const {
            return value;
        }

        virtual ~container() {
            handler(value);
        }
    };
}

#endif //LIBORBIT_NEURAL_NETWORK_UTILITIES_H
