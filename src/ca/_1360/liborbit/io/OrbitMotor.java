package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public final class OrbitMotor {
    private final int pdpPort;
    private final OrbitPipelineInputEndpoint powerEndpoint;
    private final OrbitPipelineOutputEndpoint currentEndpoint;

    public OrbitMotor(int victorPort, int pdpPort) {
        this.pdpPort = pdpPort;
        powerEndpoint = null;
        currentEndpoint = null;
    }

    public OrbitPipelineInputEndpoint getPowerEndpoint() {
        return powerEndpoint;
    }

    public OrbitPipelineOutputEndpoint getCurrentEndpoint() {
        return currentEndpoint;
    }
}
