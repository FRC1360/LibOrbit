/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitIdentityFilter.java
 * A no-op/identity filter
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitIdentityFilter extends OrbitSimplePipelineFilter {
    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        return input;
    }
}
