/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAbsoluteValueRangeFilter.java
 * A filter that applies a unary operator
 */

package ca._1360.liborbit.pipeline.filters;

import java.util.function.DoubleUnaryOperator;

public final class OrbitUnaryOperatorFilter extends OrbitSimplePipelineFilter {
    private final DoubleUnaryOperator operator;

    /**
     * @param operator The operator
     */
    public OrbitUnaryOperatorFilter(DoubleUnaryOperator operator) {
        this.operator = operator;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        return operator.applyAsDouble(input);
    }
}
