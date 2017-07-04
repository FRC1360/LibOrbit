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

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public final class OrbitMotor {
    private final OrbitPipelineInputEndpoint powerEndpoint;
    private final OrbitPipelineOutputEndpoint currentEndpoint;

    /**
     * @param pwmPort The motor`s PWM port
     * @param pdpPort The motor`s PDP port
     */
    public OrbitMotor(int pwmPort, int pdpPort) {
        powerEndpoint = OrbitFunctionUtilities.specializeFirst(OrbitInputOutputManager.getProvider()::setMotorPower, pwmPort)::accept;
        currentEndpoint = (OrbitPipelineSimpleSource) OrbitFunctionUtilities.specialize(OrbitInputOutputManager.getProvider()::getCurrent, pdpPort)::get;
    }
    
    // Accessor methods for pipeline endpoints

    public OrbitPipelineInputEndpoint getPower() {
        return powerEndpoint;
    }

    public OrbitPipelineOutputEndpoint getCurrent() {
        return currentEndpoint;
    }
}
