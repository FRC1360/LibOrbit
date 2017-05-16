package ca._1360.liborbit;

public final class OrbitPipelineConnection {
    private OrbitPipelineOutputEndpoint source;
    private OrbitPipelineInputEndpoint destination;
    private boolean enabled;

    public OrbitPipelineConnection(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination, boolean enabled) {
        this.source = source;
        this.destination = destination;
        this.enabled = enabled;
        OrbitPipelineManager.addConnection(this);
    }

    public OrbitPipelineOutputEndpoint getSource() {
        return source;
    }

    public OrbitPipelineInputEndpoint getDestination() {
        return destination;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
