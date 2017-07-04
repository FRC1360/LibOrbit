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

package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;

import java.util.OptionalDouble;

public final class OrbitEncoder {
    private final OrbitEncoderProvider provider;
    private final OrbitPipelineOutputEndpoint valueEndpoint;
    private final OrbitPipelineOutputEndpoint rateEndpoint;

    /**
     * @param portA The encoder's first DIO port
     * @param portB The encoder's second DIO port
     */
    public OrbitEncoder(int portA, int portB) {
        provider = OrbitInputOutputManager.getProvider().getEncoder(portA, portB);
        valueEndpoint = () -> OptionalDouble.of(provider.getPosition());
        rateEndpoint = (OrbitPipelineSimpleSource) provider::getRate;
    }
    
    /**
     * @return The relative position of the encoder, in encoder ticks, as an integer
     */
    public int getIntPosition() {
    	return provider.getPosition();
    }

    /**
     * @return The relative position of the encoder, in encoder ticks
     */
    public OrbitPipelineOutputEndpoint getValue() {
        return valueEndpoint;
    }

    /**
     * @return The angular velocity of the encoder, in encoder ticks per second
     */
    public OrbitPipelineOutputEndpoint getRate() {
        return rateEndpoint;
    }
}
