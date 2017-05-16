package ca._1360.liborbit.pipeline.filters;

public final class OrbitSlewFilter extends OrbitSimplePipelineFilter {
    private double maxRate;
    private double minValue;
    private double maxValue;
    private long lastUpdateTime;

    public OrbitSlewFilter(double maxRate, double minValue, double maxValue) {
        this.maxRate = maxRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getMaxRate() {
        return maxRate;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected double calculateCore(double input) {
        return 0;
    }
}
