/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
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
