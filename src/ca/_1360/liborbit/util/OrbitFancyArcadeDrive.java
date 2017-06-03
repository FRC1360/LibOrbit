package ca._1360.liborbit.util;

import ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase;
import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public class OrbitFancyArcadeDrive extends OrbitPipelineComplexNodeBase {
	private final InputEndpoint throttle = new InputEndpoint(0);
	private final InputEndpoint turn = new InputEndpoint(0);
	private final InputEndpoint weight;
	private final OutputEndpoint left = new OutputEndpoint(0, true);
	private final OutputEndpoint right = new OutputEndpoint(0, true);
	
	public OrbitFancyArcadeDrive(double weightFactor) {
		super(false);
		weight = new InputEndpoint(weightFactor);
	}

	@Override
	protected void update() {
		double throttleValue = throttle.getValue();
		double turnValue = turn.getValue();
		double weightValue = weight.getValue();
		
		double adjustment = Math.exp(weightValue * Math.abs(turnValue)) * turnValue;
		
		left.setValue(throttleValue + adjustment);
		right.setValue(throttleValue - adjustment);
	}

	public OrbitPipelineInputEndpoint getThrottle() {
		return throttle;
	}

	public OrbitPipelineInputEndpoint getTurn() {
		return turn;
	}

	public OrbitPipelineInputEndpoint getWeight() {
		return weight;
	}

	public OrbitPipelineOutputEndpoint getLeft() {
		return left;
	}

	public OrbitPipelineOutputEndpoint getRight() {
		return right;
	}
}
