/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineConstantValueSource.java
 * A pipeline output that supplies a constant value
 */

package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;

public final class OrbitPipelineConstantValueSource implements OrbitPipelineOutputEndpoint {
    private double value;

    /**
     * @param value The value that is provided
     */
    public OrbitPipelineConstantValueSource(double value) {
        this.value = value;
    }

    /**
     * @return The value that is provided
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value THe new value to provide
     */
    public void setValue(double value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.util.function.Supplier#get()
     */
    @Override
    public OptionalDouble get() {
        return OptionalDouble.of(value);
    }
}
