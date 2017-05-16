package ca._1360.liborbit.server;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.OptionalDouble;

public final class OrbitApiServer {
    ServerSocket socket;
    HashMap<String, InputEndpoint> inputs;
    HashMap<String, OutputEndpoint> outputs;

    public OrbitApiServer(int port) {

    }

    public OrbitPipelineInputEndpoint getInput(String label) {
        return null;
    }

    public OrbitPipelineOutputEndpoint getOutput(String label) {
        return null;
    }

    public void removeInput(String label) {

    }

    public void removeOutput(String label) {

    }

    private class InputEndpoint implements OrbitPipelineInputEndpoint {
        @Override
        public void accept(double v) {

        }
    }

    private class OutputEndpoint implements OrbitPipelineOutputEndpoint {
        @Override
        public OptionalDouble get() {
            return null;
        }
    }
}
