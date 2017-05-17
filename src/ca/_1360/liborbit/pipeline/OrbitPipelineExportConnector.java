package ca._1360.liborbit.pipeline;

import java.util.function.Consumer;
import java.util.function.DoubleFunction;

public final class OrbitPipelineExportConnector<T> implements OrbitPipelineInputEndpoint {
    private DoubleFunction<T> function;
    private Consumer<T> target;

    public OrbitPipelineExportConnector(DoubleFunction<T> function, Consumer<T> target) {
        this.function = function;
        this.target = target;
    }

    @Override
    public void accept(double v) {
        target.accept(function.apply(v));
    }
}
