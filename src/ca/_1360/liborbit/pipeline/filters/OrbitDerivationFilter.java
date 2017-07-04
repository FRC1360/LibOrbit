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

import java.util.OptionalDouble;

public final class OrbitDerivationFilter extends OrbitPipelineFilter {
    private Double last;
    private long lastTime;

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.filters.OrbitPipelineFilter#calculate(double)
     */
    @Override
    protected OptionalDouble calculate(double input) {
        long time = System.currentTimeMillis();
        // No value first time
        if (last == null) {
            last = input;
            lastTime = time;
            return OptionalDouble.empty();
        }
        // Calculate difference quotient
        double derivative = (input - last) / (time - lastTime) * 1000;
        lastTime = time;
        return OptionalDouble.of(derivative);
    }
}
