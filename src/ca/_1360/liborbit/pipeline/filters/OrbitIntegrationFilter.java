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

public final class OrbitIntegrationFilter extends OrbitSimplePipelineFilter {
    private double integral;
    private long lastTime = System.currentTimeMillis();

    /**
     * Sets the accumulated sum to zero, effectively starting a new integral at the current time 
     */
    public void reset() {
        integral = 0;
        lastTime = System.currentTimeMillis();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitSimplePipelineFilter#calculateCore(double)
     */
    @Override
    protected double calculateCore(double input) {
        long time = System.currentTimeMillis();
        // Calculate Riemann sum
        integral += input * (time - lastTime) / 1000;
        lastTime = time;
        return integral;
    }
}
