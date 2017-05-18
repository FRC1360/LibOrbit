package ca._1360.liborbit.pipeline.filters;

import java.util.function.DoubleUnaryOperator;

public final class OrbitUnaryOperatorFilter extends OrbitSimplePipelineFilter {
    private DoubleUnaryOperator operator;

    public OrbitUnaryOperatorFilter(DoubleUnaryOperator operator) {
        this.operator = operator;
    }

    @Override
    protected double calculateCore(double input) {
        return operator.applyAsDouble(input);
    }
}
