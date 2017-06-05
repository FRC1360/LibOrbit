/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitScaleFilter.java
 * A filter that multiplies the input value by a constant scalar
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitScaleFilter extends OrbitSimplePipelineFilter {
    private double scale;

    /**
     * @param scale The scalar
     */
    public OrbitScaleFilter(double scale) {
        this.scale = scale;
    }

    /**
     * @return The scalar
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param scale THe new scalar
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        return scale * input;
    }
}
