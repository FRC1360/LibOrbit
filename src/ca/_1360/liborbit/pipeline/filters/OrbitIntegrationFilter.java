/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitIntegrationFilter.java
 * A filter that approximates integration with respect to time using right Riemann sum
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitIntegrationFilter extends OrbitSimplePipelineFilter {
    private double integral;
    private long lastTime = System.currentTimeMillis();

    /**
     * Sets the accumulated sum to zero, effectively starting a new integral at the current time 
     */
    public void reset() {
        integral = 0;
        lastTime = System.currentTimeMillis();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        long time = System.currentTimeMillis();
        // Calculate Riemann sum
        integral += input * (time - lastTime) / 1000;
        lastTime = time;
        return integral;
    }
}
