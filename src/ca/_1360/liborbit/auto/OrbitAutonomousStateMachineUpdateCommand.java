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

import ca._1360.liborbit.statemachine.OrbitStateMachine;
import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

import java.util.Collections;
import java.util.List;

/**
 * @param <T> The subsystem type; usually an enum
 * @param <U> The state machine state type
 */
public class OrbitAutonomousStateMachineUpdateCommand<T, U extends OrbitStateMachineStates> extends OrbitAutonomousCommand<T> {
    private final StateMachineUpdate<U> update;

    /**
     * @param subsystem The subsystem that the command runs on
     * @param stateMachine The state machine to update
     * @param state The state in which to place stateMachine
     */
    public OrbitAutonomousStateMachineUpdateCommand(T subsystem, OrbitStateMachine<U> stateMachine, U state) {
        super(subsystem, 0);
        update = new StateMachineUpdate<>(stateMachine, state);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#initializeCore()
     */
    @Override
    protected void initializeCore() {
        gotoNext();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#getStateUpdates()
     */
    @Override
    public List<StateMachineUpdate<?>> getStateUpdates() {
        return Collections.singletonList(update);
    }
}
