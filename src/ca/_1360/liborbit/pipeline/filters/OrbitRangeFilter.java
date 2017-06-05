/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitRangeFilter.java
 * A filter that restricts the range of the value
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitRangeFilter extends OrbitSimplePipelineFilter {
    private double min;
    private double max;

    /**
     * @param min The minimum value
     * @param max The maximum value
     */
    public OrbitRangeFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return The minimum value
     */
    public double getMin() {
        return min;
    }

    /**
     * @return The maximum value
     */
    public double getMax() {
        return max;
    }

    /**
     * @param min The new minimum value
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @param max The new maximum value
     */
    public void setMax(double max) {
        this.max = max;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        return input < min ? min : input > max ? max : input;
    }
}
