/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitSlewFilter.java
 * A filter that restricts the rate of change and range of the value
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitSlewFilter extends OrbitSimplePipelineFilter {
    private double last;
    private double maxRate;
    private double minValue;
    private double maxValue;
    private long lastUpdateTime;

    /**
     * @param maxRate The maximum rate of change
     * @param minValue The minimum value
     * @param maxValue The maximum value
     */
    public OrbitSlewFilter(double maxRate, double minValue, double maxValue) {
        this.maxRate = maxRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
        lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * @return The maximum rate of change
     */
    public double getMaxRate() {
        return maxRate;
    }

    /**
     * @return The minimum value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * @return The maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxRate The new maximum rate of change
     */
    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

    /**
     * @param minValue The new minimum value
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * @param maxValue The new maximum value
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        long time = System.currentTimeMillis();
        last = Math.max(Math.min(last + Math.copySign(Math.min(Math.abs(input - last), maxRate * (time - lastUpdateTime)), input - last), maxValue), minValue);
        lastUpdateTime = time;
        return last;
    }
}
