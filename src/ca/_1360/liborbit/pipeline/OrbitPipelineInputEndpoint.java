/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineComplexNodeBase.java
 * Base interface for pipeline input endpoints
 */

package ca._1360.liborbit.pipeline;

import java.util.function.DoubleConsumer;

public interface OrbitPipelineInputEndpoint extends DoubleConsumer {
    /**
     * To be called to perform internal updates when no value is available
     */
    default void acceptNoInput() { }
}
