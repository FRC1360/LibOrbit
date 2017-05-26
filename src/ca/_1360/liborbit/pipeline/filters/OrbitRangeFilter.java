package ca._1360.liborbit.pipeline.filters;

public final class OrbitRangeFilter extends OrbitSimplePipelineFilter {
    private double min;
    private double max;

    public OrbitRangeFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    protected double calculateCore(double input) {
        return input < min ? min : input > max ? max : input;
    }
}
