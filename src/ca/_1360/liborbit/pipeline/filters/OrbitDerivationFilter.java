/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitDerivationFilter.java
 * A filter that approximates derivation with respect to time using a finite difference quotient
 */

package ca._1360.liborbit.pipeline.filters;

import java.util.OptionalDouble;

public final class OrbitDerivationFilter extends OrbitPipelineFilter {
    private Double last;
    private long lastTime;

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitPipelineFilter#calculate(double)
     */
    @Override
    protected OptionalDouble calculate(double input) {
        long time = System.currentTimeMillis();
        // No value first time
        if (last == null) {
            last = input;
            lastTime = time;
            return OptionalDouble.empty();
        }
        // Calculate difference quotient
        double derivative = (input - last) / (time - lastTime) * 1000;
        lastTime = time;
        return OptionalDouble.of(derivative);
    }
}
