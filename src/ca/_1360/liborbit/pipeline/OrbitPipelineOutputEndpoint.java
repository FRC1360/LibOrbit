package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;
import java.util.function.Supplier;

public interface OrbitPipelineOutputEndpoint extends Supplier<OptionalDouble> {
    default boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        return false;
    }
}
