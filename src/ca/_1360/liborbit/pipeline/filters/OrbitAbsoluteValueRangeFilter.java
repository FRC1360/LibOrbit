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
