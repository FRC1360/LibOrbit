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

package ca._1360.liborbit.auto;

import ca._1360.liborbit.io.OrbitSolenoid;
import ca._1360.liborbit.io.OrbitSolenoidStates;

/**
 * @param <T> The subsystem type; usually an enum
 */
public final class OrbitAutonomousSolenoidCommand<T> extends OrbitAutonomousStateMachineUpdateCommand<T, OrbitSolenoidStates> {
    /**
     * @param subsystem The subsystem that the command runs on
     * @param solenoid The solenoid to engage or disengage
     * @param engaged True if the solenoid should be engaged; false if it should be disengaged
     */
    public OrbitAutonomousSolenoidCommand(T subsystem, OrbitSolenoid solenoid, boolean engaged) {
        super(subsystem, solenoid, OrbitSolenoidStates.getState(engaged));
    }
}
