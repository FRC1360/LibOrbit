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

package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;

import java.util.List;

public interface OrbitStateMachineSimpleStates extends OrbitStateMachineStates {
    List<OrbitPipelineConnection> getConnections();
    List<StateMachineUpdate<?>> getStateUpdates();

    /**
     * Creates a state object
     * @param initialize The function to run to initialize the state
     * @param deinitialize The function to run to deinitialize the state
     * @param connections The pipeline connections that the state requires
     * @param stateUpdates The state updates to perform upon initialization
     * @return
     */
    static OrbitStateMachineSimpleStates create(Runnable initialize, Runnable deinitialize, List<OrbitPipelineConnection> connections, List<StateMachineUpdate<?>> stateUpdates) {
        return new OrbitStateMachineSimpleStates() {
            @Override
            public void initialize() {
                initialize.run();
            }

            @Override
            public void deinitialize() {
                deinitialize.run();
            }

            @Override
            public List<OrbitPipelineConnection> getConnections() {
                return connections;
            }

            @Override
            public List<StateMachineUpdate<?>> getStateUpdates() {
                return stateUpdates;
            }
        };
    }

    /**
     * A state update to another state machine
     * @param <T> The state type of the other state machine
     */
    final class StateMachineUpdate<T extends OrbitStateMachineStates> {
        private final OrbitStateMachine<T> stateMachine;
        private final T state;
        private T lastState;

        /**
         * @param stateMachine The other state machine
         * @param state The new state
         */
        public StateMachineUpdate(OrbitStateMachine<T> stateMachine, T state) {
            this.stateMachine = stateMachine;
            this.state = state;
        }

        /**
         * Performs the update
         */
        public void update() {
            lastState = stateMachine.getState();
            stateMachine.setState(state);
        }

        /**
         * Reverses the update
         */
        public void undo() {
            stateMachine.setState(lastState);
        }
    }
}
