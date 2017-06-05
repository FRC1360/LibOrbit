/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineConnection.java
 * A connection from an output endpoint to an input endpoint in the pipeline system
 */

package ca._1360.liborbit.pipeline;

public final class OrbitPipelineConnection {
    private final OrbitPipelineOutputEndpoint source;
    private final OrbitPipelineInputEndpoint destination;
    private boolean enabled;

    /**
     * @param source The output endoint that the connection pulls data from
     * @param destination The input endpoint that the connection pushes data to
     * @param enabled True if the connection should be enabled immediately
     * @throws OrbitPipelineInvalidConfigurationException Thrown if enabling is attempted and the connection is invalid
     */
    public OrbitPipelineConnection(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination, boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        this.source = source;
        this.destination = destination;
        this.enabled = enabled;
        OrbitPipelineManager.updateEnabled(this, enabled);
    }

    /**
     * @return The output endpoint that the connection pulls data from
     */
    public OrbitPipelineOutputEndpoint getSource() {
        return source;
    }

    /**
     * @return The input endpoint that the connection pushes data to
     */
    public OrbitPipelineInputEndpoint getDestination() {
        return destination;
    }

    /**
     * @return True if the connection is currently enabled; false if it is not
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled True if the connection should be enabled; false if it should be disabled
     * @throws OrbitPipelineInvalidConfigurationException Thrown if enabling is attempted and the connection is invalid
     */
    public void setEnabled(boolean enabled) throws OrbitPipelineInvalidConfigurationException {
    	// Update pipeline manager if state is new
        if (enabled != this.enabled) {
            OrbitPipelineManager.updateEnabled(this, enabled);
            this.enabled = enabled;
        }
    }
}
