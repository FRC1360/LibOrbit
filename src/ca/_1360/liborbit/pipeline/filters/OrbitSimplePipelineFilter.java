/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitSimplePipelineFilter.java
 * Base class for filters that always produce an output
 */

package ca._1360.liborbit.pipeline.filters;

import java.util.OptionalDouble;

public abstract class OrbitSimplePipelineFilter extends OrbitPipelineFilter {
    /**
     * @param input The input value
     * @return The output value
     */
    protected abstract double calculateCore(double input);

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitPipelineFilter#calculate(double)
     */
    @Override
    protected final OptionalDouble calculate(double input) {
        return OptionalDouble.of(calculateCore(input));
    }
}
