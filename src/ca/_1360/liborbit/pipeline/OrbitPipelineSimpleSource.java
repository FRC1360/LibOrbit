/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineSimpleSource.java
 * Base interface for output endpoints that always produce a value with a given supplier method
 */

package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface OrbitPipelineSimpleSource extends OrbitPipelineOutputEndpoint, DoubleSupplier {
    /* (non-Javadoc)
     * @see java.util.function.Supplier#get()
     */
    @Override
    default OptionalDouble get() {
        return OptionalDouble.of(getAsDouble());
    }
}
