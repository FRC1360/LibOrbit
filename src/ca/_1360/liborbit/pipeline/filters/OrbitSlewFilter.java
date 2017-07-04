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

public final class OrbitSlewFilter extends OrbitSimplePipelineFilter {
    private double last;
    private double maxRate;
    private double minValue;
    private double maxValue;
    private long lastUpdateTime;

    /**
     * @param maxRate The maximum rate of change
     * @param minValue The minimum value
     * @param maxValue The maximum value
     */
    public OrbitSlewFilter(double maxRate, double minValue, double maxValue) {
        this.maxRate = maxRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
        lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * @return The maximum rate of change
     */
    public double getMaxRate() {
        return maxRate;
    }

    /**
     * @return The minimum value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * @return The maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxRate The new maximum rate of change
     */
    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

    /**
     * @param minValue The new minimum value
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * @param maxValue The new maximum value
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        long time = System.currentTimeMillis();
        last = Math.max(Math.min(last + Math.copySign(Math.min(Math.abs(input - last), maxRate * (time - lastUpdateTime)), input - last), maxValue), minValue);
        lastUpdateTime = time;
        return last;
    }
}
