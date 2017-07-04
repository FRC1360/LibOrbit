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

package ca._1360.liborbit.pipeline;

import java.util.function.Consumer;
import java.util.function.DoubleFunction;

/**
 * @param <T> The type of the target parameter
 */
public final class OrbitPipelineExportConnector<T> implements OrbitPipelineInputEndpoint {
    private final DoubleFunction<T> function;
    private final Consumer<T> target;

    /**
     * @param function A function to transform the value
     * @param target The target that uses the transformed value
     */
    public OrbitPipelineExportConnector(DoubleFunction<T> function, Consumer<T> target) {
        this.function = function;
        this.target = target;
    }

    /* (non-Javadoc)
     * @see java.util.function.DoubleConsumer#accept(double)
     */
    @Override
    public void accept(double v) {
    	// Transform and use value
        target.accept(function.apply(v));
    }
}
