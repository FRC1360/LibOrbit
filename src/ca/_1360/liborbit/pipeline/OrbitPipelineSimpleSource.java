package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface OrbitPipelineSimpleSource extends OrbitPipelineOutputEndpoint, DoubleSupplier {
    @Override
    default OptionalDouble get() {
        return OptionalDouble.of(getAsDouble());
    }
}
