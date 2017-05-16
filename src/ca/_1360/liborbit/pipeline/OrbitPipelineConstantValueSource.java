package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;

public final class OrbitPipelineConstantValueSource implements OrbitPipelineOutputEndpoint {
    private double value;

    public OrbitPipelineConstantValueSource(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public OptionalDouble get() {
        return OptionalDouble.of(value);
    }
}
