package ca._1360.liborbit.pipeline.filters;

import java.util.OptionalDouble;

public final class OrbitIntegrationFilter extends OrbitSimplePipelineFilter {
    private double integral;
    private long lastTime = System.currentTimeMillis();

    public void reset() {
        integral = 0;
    }

    @Override
    protected double calculateCore(double input) {
        long time = System.currentTimeMillis();
        integral += input * (time - lastTime) / 1000;
        lastTime = time;
        return integral;
    }
}
