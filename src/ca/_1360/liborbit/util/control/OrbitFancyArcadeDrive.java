/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitFancyArcadeDrive.java
 * A pipeline node that does arcade drive calculations using a weighed exponential for turning 
 */

package ca._1360.liborbit.util.control;

import ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase;
import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public class OrbitFancyArcadeDrive extends OrbitPipelineComplexNodeBase {
	private final InputEndpoint throttle = new InputEndpoint(0);
	private final InputEndpoint turn = new InputEndpoint(0);
	private final InputEndpoint weight;
	private final OutputEndpoint left = new OutputEndpoint(0, true);
	private final OutputEndpoint right = new OutputEndpoint(0, true);
	
	/**
	 * @param weightFactor The initial turning weight factor
	 */
	public OrbitFancyArcadeDrive(double weightFactor) {
		super(false);
		weight = new InputEndpoint(weightFactor);
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase#update()
	 */
	@Override
	protected void update() {
		double throttleValue = throttle.getValue();
		double turnValue = turn.getValue();
		double weightValue = weight.getValue();
		
		// Exponential - a = t*e^(w*|t|)
		double adjustment = Math.exp(weightValue * Math.abs(turnValue)) * turnValue;
		
		left.setValue(throttleValue + adjustment);
		right.setValue(throttleValue - adjustment);
	}
	
	// Accessor methods for pipeline endpoints

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
