package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public final class OrbitPipelineConnection {
    private OrbitPipelineOutputEndpoint source;
    private OrbitPipelineInputEndpoint destination;
    private boolean enabled;

    public OrbitPipelineConnection(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination, boolean enabled) throws OrbitPipelineManager.CyclicDependencyException {
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

    public void setEnabled(boolean enabled) throws OrbitPipelineManager.CyclicDependencyException {
        if (enabled != this.enabled)
            OrbitPipelineManager.updateEnabled(this, enabled);
        this.enabled = enabled;
    }
}
