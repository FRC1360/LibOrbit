package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public final class OrbitMotor {
    private int pdpPort;
    private OrbitPipelineInputEndpoint powerEndpoint;
    private OrbitPipelineOutputEndpoint currentEndpoint;

    public OrbitMotor(int victorPort, int pdpPort) {
        this.pdpPort = pdpPort;
    }

    public OrbitPipelineInputEndpoint getPowerEndpoint() {
        return powerEndpoint;
    }

    public OrbitPipelineOutputEndpoint getCurrentEndpoint() {
        return currentEndpoint;
    }
}
