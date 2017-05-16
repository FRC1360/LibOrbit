package ca._1360.liborbit.pipeline.filters;

import java.util.OptionalDouble;

public abstract class OrbitSimplePipelineFilter extends OrbitPipelineFilter {
    protected abstract double calculateCore(double input);

    @Override
    protected OptionalDouble calculate(double input) {
        return OptionalDouble.of(calculateCore(input));
    }
}
