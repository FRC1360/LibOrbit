package ca._1360.liborbit.pipeline.filters;

import java.util.OptionalDouble;

public final class OrbitDerivationFilter extends OrbitPipelineFilter {
    private Double last;
    private long lastTime;

    @Override
    protected OptionalDouble calculate(double input) {
        long time = System.currentTimeMillis();
        if (last == null) {
            last = input;
            lastTime = time;
            return OptionalDouble.empty();
        }
        double derivative = (input - last) / (time - lastTime) * 1000;
        lastTime = time;
        return OptionalDouble.of(derivative);
    }
}
