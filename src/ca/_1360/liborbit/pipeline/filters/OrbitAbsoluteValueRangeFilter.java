package ca._1360.liborbit.pipeline.filters;

public final class OrbitAbsoluteValueRangeFilter extends OrbitSimplePipelineFilter {
    private double min;
    private double max;

    public OrbitAbsoluteValueRangeFilter(double min, double max) {
        this.min = Math.abs(min);
        this.max = Math.abs(max);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = Math.abs(min);
    }

    public void setMax(double max) {
        this.max = Math.abs(max);
    }

    @Override
    protected double calculateCore(double input) {
        return Math.copySign(input, Math.max(Math.min(Math.abs(input), max), min));
    }
}
