package ca._1360.liborbit.pipeline;

public final class OrbitPipelineConnection {
    private final OrbitPipelineOutputEndpoint source;
    private final OrbitPipelineInputEndpoint destination;
    private boolean enabled;

    public OrbitPipelineConnection(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination, boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        this.source = source;
        this.destination = destination;
        this.enabled = enabled;
        OrbitPipelineManager.updateEnabled(this, enabled);
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

    public void setEnabled(boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        if (enabled != this.enabled)
            OrbitPipelineManager.updateEnabled(this, enabled);
        this.enabled = enabled;
    }
}
