/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineFilter.java
 * Base class for all pipeline filters
 */

package ca._1360.liborbit.pipeline.filters;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

import java.util.OptionalDouble;

// Implements both input endpoint and output endpoint so that accessor methods for input and output are not necessary
public abstract class OrbitPipelineFilter implements OrbitPipelineInputEndpoint, OrbitPipelineOutputEndpoint {
    private OptionalDouble result = OptionalDouble.empty();

    /**
     * To be called to perform the transform
     * @param input The input value
     * @return The output value, if there is one
     */
    protected abstract OptionalDouble calculate(double input);

    /* (non-Javadoc)
     * @see java.util.function.DoubleConsumer#accept(double)
     */
    @Override
    public synchronized final void accept(double v) {
        result = calculate(v);
    }

    /* (non-Javadoc)
     * @see java.util.function.Supplier#get()
     */
    @Override
    public synchronized final OptionalDouble get() {
        return result;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint#dependsOn(ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint)
     */
    @Override
    public boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        return inputEndpoint == this;
    }
}
