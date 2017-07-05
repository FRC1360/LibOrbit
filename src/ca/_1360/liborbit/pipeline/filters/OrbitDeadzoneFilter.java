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
