package ca._1360.liborbit.pipeline;

import java.util.function.DoubleConsumer;

public interface OrbitPipelineInputEndpoint extends DoubleConsumer {
    default void acceptNoInput() { }
}
