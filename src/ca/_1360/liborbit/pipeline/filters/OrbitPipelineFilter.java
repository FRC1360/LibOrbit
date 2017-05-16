package ca._1360.liborbit.pipeline.filters;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

import java.util.OptionalDouble;

public abstract class OrbitPipelineFilter implements OrbitPipelineInputEndpoint, OrbitPipelineOutputEndpoint {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OptionalDouble result = OptionalDouble.empty();

    protected abstract OptionalDouble calculate(double input);

    @Override
    public synchronized final void accept(double v) {
        result = calculate(v);
    }

    @Override
    public synchronized final OptionalDouble get() {
        return result;
    }

    @Override
    public boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        return inputEndpoint == this;
    }
}
