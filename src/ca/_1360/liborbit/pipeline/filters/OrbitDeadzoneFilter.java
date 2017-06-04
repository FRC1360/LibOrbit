package ca._1360.liborbit.pipeline.filters;

public final class OrbitDeadzoneFilter extends OrbitSimplePipelineFilter {
	private double deadzone;
	
	public OrbitDeadzoneFilter(double deadzone) {
		this.deadzone = deadzone;
	}

	public double getDeadzone() {
		return deadzone;
	}

	public void setDeadzone(double deadzone) {
		this.deadzone = deadzone;
	}

	@Override
	protected double calculateCore(double input) {
		return Math.abs(input) >= deadzone ? input : 0.0;
	}
}
