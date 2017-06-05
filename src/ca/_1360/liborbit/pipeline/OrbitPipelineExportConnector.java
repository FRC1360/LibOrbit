/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineExportConnector.java
 * Input endpoint for transforming and pushing the pipeline value
 */

package ca._1360.liborbit.pipeline;

import java.util.function.Consumer;
import java.util.function.DoubleFunction;

/**
 * @param <T> The type of the target parameter
 */
public final class OrbitPipelineExportConnector<T> implements OrbitPipelineInputEndpoint {
    private final DoubleFunction<T> function;
    private final Consumer<T> target;

    /**
     * @param function A function to transform the value
     * @param target The target that uses the transformed value
     */
    public OrbitPipelineExportConnector(DoubleFunction<T> function, Consumer<T> target) {
        this.function = function;
        this.target = target;
    }

    /* (non-Javadoc)
     * @see java.util.function.DoubleConsumer#accept(double)
     */
    @Override
    public void accept(double v) {
    	// Transform and use value
        target.accept(function.apply(v));
    }
}
