/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitStateMachineSimpleStates.java
 * Base interface for states compatible with OrbitSimpleStateMachine
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
