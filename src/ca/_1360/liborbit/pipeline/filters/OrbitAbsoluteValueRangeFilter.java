/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAbsoluteValueRangeFilter.java
 * A filter that restricts the range of the absolute value
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitAbsoluteValueRangeFilter extends OrbitSimplePipelineFilter {
    private double min;
    private double max;

    /**
     * @param min The minimum absolute value
     * @param max The maximum absolute value
     */
    public OrbitAbsoluteValueRangeFilter(double min, double max) {
        this.min = Math.abs(min);
        this.max = Math.abs(max);
    }

    /**
     * @return The minimum absolute value
     */
    public double getMin() {
        return min;
    }

    /**
     * @return The maximum absolute value
     */
    public double getMax() {
        return max;
    }

    /**
     * @param min The new minimum absolute value
     */
    public void setMin(double min) {
        this.min = Math.abs(min);
    }

    /**
     * @param max The new maximum absolute value
     */
    public void setMax(double max) {
        this.max = Math.abs(max);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
    	// Ensure that input's sign remains in the result
        return Math.copySign(Math.max(Math.min(Math.abs(input), max), min), input);
    }
}
