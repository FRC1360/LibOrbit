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

import java.util.Arrays;
import java.util.OptionalDouble;

public final class OrbitStandardDeviationFilter extends OrbitPipelineFilter {
    private double nStdDev;
    private int index;
    private final double[] values;

    /**
     * @param nStdDev The maximum number of standard deviations
     * @param nValues The number of recent values to keep
     */
    public OrbitStandardDeviationFilter(double nStdDev, int nValues) {
        this.nStdDev = nStdDev;
        index = -nValues;
        values = new double[nValues];
    }

    /**
     * @return The maximum number of standard deviations
     */
    public double getNStdDev() {
        return nStdDev;
    }

    /**
     * @param nStdDev The number of recent values to keep
     */
    public void setNStdDev(double nStdDev) {
        this.nStdDev = nStdDev;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitPipelineFilter#calculate(double)
     */
    @Override
    protected OptionalDouble calculate(double input) {
    	// Update the next value
        values[index < 0 ? -index : index] = input;
        if (++index == values.length)
            index = 0;
        // Exit if not enough starting values yet
        if (index < 0)
        	return OptionalDouble.empty();
        // Calculate mean
        int limit = index < 0 ? values.length + index : values.length;
        OptionalDouble mean = Arrays.stream(values).limit(limit).average();
        if (!mean.isPresent())
        	return OptionalDouble.empty();
        // Calculate standard deviation
        OptionalDouble s2 = Arrays.stream(values).limit(limit).map(x -> (x - mean.getAsDouble()) * (x - mean.getAsDouble())).average();
        if (!s2.isPresent())
        	return OptionalDouble.empty();
        double s = Math.sqrt(s2.getAsDouble());
        // Filter values and take mean
        return Arrays.stream(values).filter(x -> Math.abs(x - mean.getAsDouble()) / s < nStdDev).average();
    }
}
