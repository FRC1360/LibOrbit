#include "environment.h"
#include "../../common/utilities.h"
#include <cstdio>
#include <dirent.h>
#include <cstring>
#include <cmath>
#include <map>

namespace liborbit::neural_network::evolution::neat {
    static organism load_org(const char *org_file) {
        throw "Not implemented";
        //TODO: Load organism genome
    }

    static organism load_org(const char *gen_directory, uint_fast32_t id) {
        common::container<char *> path(new char[strlen(gen_directory) + (size_t) ceil(log10f(id)) + 4], operator delete[]);
        sprintf(path.getValue(), "%s/%u", gen_directory, (unsigned) id);
        return load_org(path.getValue());
    }

    static organism load_org2(const char *root_directory, uint_fast32_t gen, uint_fast32_t id) {
        common::container<char *> path(new char[strlen(root_directory) + (size_t) ceil(log10f(gen)) + (size_t) ceil(log10(id)) + 9], operator delete[]);
        sprintf(path.getValue(), "%s/gen_%u/%u", root_directory, (unsigned) gen, (unsigned) id);
        return load_org(path.getValue());
    }

    static generation load_gen(const char *gen_directory, uint_fast32_t id) {
        common::container<DIR *> d(opendir(gen_directory), closedir);
        if (!d.getValue()) {
            throw "Could not open generation directory";
        }
        dirent *dir;
        generation result(id);
        std::map<uint_fast32_t, organism> organisms;
        while ((dir = readdir(d.getValue()))) {
            unsigned org_id;
            if (dir->d_type == 'f' && sscanf(dir->d_name, "org_%u", &org_id) != EOF) {
            }
        }
    }

    static generation load_gen2(const char *root_directory, uint_fast32_t id) {
        common::container<char *> path(new char[strlen(root_directory) + (size_t) ceil(log10f(id)) + 7], operator delete[]);
        sprintf(path.getValue(), "%s/gen_%u", root_directory, (unsigned) id);
        return load_gen(path.getValue(), id);
    }

    static generation initialize(const char *root_directory) {
        common::container<DIR *> d(opendir(root_directory), closedir);
        dirent *dir;
        if (d.getValue()) {
            unsigned max_gen = 0;
            dirent *max_dir = NULL;
            while ((dir = readdir(d.getValue()))) {
                unsigned gen_id;
                if (dir->d_type == 'd' && sscanf(dir->d_name, "gen_%u", &gen_id) != EOF && gen_id >= max_gen) {
                    max_dir = dir;
                    max_gen = gen_id;
                }
            }
            if (max_dir) {
                common::container<char *> path(new char[strlen(root_directory) + strlen(dir->d_name) + 2], operator delete[]);
                sprintf(path.getValue(), "%s/%s", root_directory, dir->d_name);
                return load_gen(path.getValue(), max_gen);
            }
            throw "No generation directories found";
        } else {
            throw "Directory could not be opened";
        }
    }

    environment::environment(const char *root_directory) : root_directory(root_directory), current_gen(initialize(root_directory)) {}
}
