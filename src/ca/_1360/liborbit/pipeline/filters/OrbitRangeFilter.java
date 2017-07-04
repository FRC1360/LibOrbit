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

public final class OrbitRangeFilter extends OrbitSimplePipelineFilter {
    private double min;
    private double max;

    /**
     * @param min The minimum value
     * @param max The maximum value
     */
    public OrbitRangeFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return The minimum value
     */
    public double getMin() {
        return min;
    }

    /**
     * @return The maximum value
     */
    public double getMax() {
        return max;
    }

    /**
     * @param min The new minimum value
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @param max The new maximum value
     */
    public void setMax(double max) {
        this.max = max;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        return input < min ? min : input > max ? max : input;
    }
}
