package ca._1360.liborbit;

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
