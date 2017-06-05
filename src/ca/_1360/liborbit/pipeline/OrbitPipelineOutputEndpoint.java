/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineOutputEndpoint.java
 * Base interface for output endpoints
 */

package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;
import java.util.function.Supplier;

public interface OrbitPipelineOutputEndpoint extends Supplier<OptionalDouble> {
    /**
     * @param inputEndpoint
     * @return True if the output endpoint's value internally depends on the given input endpoint's value
     */
    default boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        return false;
    }
}
