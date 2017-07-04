/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
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
