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

import ca._1360.liborbit.statemachine.OrbitStateMachine;

public final class OrbitSolenoid extends OrbitStateMachine<OrbitSolenoidStates> {
    private final int port;

    /**
     * @param port The solenoid's PCM port
     */
    public OrbitSolenoid(int port) {
    	// Ensure that the solenoid is disengaged
        super(OrbitSolenoidStates.SOLENOID_DISENGAGED);
        this.port = port;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachine#setStateCore(ca._1360.liborbit.statemachine.OrbitStateMachineStates, ca._1360.liborbit.statemachine.OrbitStateMachineStates)
     */
    @Override
    protected boolean setStateCore(OrbitSolenoidStates oldState, OrbitSolenoidStates newState) {
    	// Update the solenoid through the primary I/O provider when the state changes
        OrbitInputOutputManager.getProvider().setSolenoid(port, newState.getValue());
        return true;
    }
}
