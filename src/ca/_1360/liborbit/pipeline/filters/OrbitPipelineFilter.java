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

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

import java.util.OptionalDouble;

// Implements both input endpoint and output endpoint so that accessor methods for input and output are not necessary
public abstract class OrbitPipelineFilter implements OrbitPipelineInputEndpoint, OrbitPipelineOutputEndpoint {
    private OptionalDouble result = OptionalDouble.empty();

    /**
     * To be called to perform the transform
     * @param input The input value
     * @return The output value, if there is one
     */
    protected abstract OptionalDouble calculate(double input);

    /* (non-Javadoc)
     * @see java.util.function.DoubleConsumer#accept(double)
     */
    @Override
    public synchronized final void accept(double v) {
        result = calculate(v);
    }

    /* (non-Javadoc)
     * @see java.util.function.Supplier#get()
     */
    @Override
    public synchronized final OptionalDouble get() {
        return result;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint#dependsOn(ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint)
     */
    @Override
    public boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        return inputEndpoint == this;
    }
}
