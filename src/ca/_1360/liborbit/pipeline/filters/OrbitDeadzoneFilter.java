/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitDeadzoneFilter.java
 * A filter that zeros any value with a significantly small absolute value
 */

package ca._1360.liborbit.pipeline.filters;

public final class OrbitDeadzoneFilter extends OrbitSimplePipelineFilter {
	private double deadzone;
	
	/**
	 * @param deadzone The absolute value threshold
	 */
	public OrbitDeadzoneFilter(double deadzone) {
		this.deadzone = deadzone;
	}

	/**
	 * @return The absolute value threshold
	 */
	public double getDeadzone() {
		return deadzone;
	}

	/**
	 * @param deadzone The new absolute value threshold
	 */
	public void setDeadzone(double deadzone) {
		this.deadzone = deadzone;
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
	 */
	@Override
	protected double calculateCore(double input) {
		return Math.abs(input) >= deadzone ? input : 0.0;
	}
}
