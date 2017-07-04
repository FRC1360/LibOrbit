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

import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

public enum OrbitSolenoidStates implements OrbitStateMachineStates {
    SOLENOID_ENGAGED(true),
    SOLENOID_DISENGAGED(false);

    private final boolean value;

    OrbitSolenoidStates(boolean value) {
        this.value = value;
    }

    /**
     * @return Get whether the state represents an engaged solenoid
     */
    public boolean getValue() {
        return value;
    }

    /**
     * @param engaged True if the solenoid should be engaged, false if it should be disengaged
     * @return The applicable state
     */
    public static OrbitSolenoidStates getState(boolean engaged) {
        return engaged ? SOLENOID_ENGAGED : SOLENOID_DISENGAGED;
    }
}
