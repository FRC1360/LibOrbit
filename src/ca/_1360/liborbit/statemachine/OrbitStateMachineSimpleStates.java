package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;

import java.util.List;

public interface OrbitStateMachineSimpleStates extends OrbitStateMachineStates {
    List<OrbitPipelineConnection> getConnections();
    List<StateMachineUpdate<?>> getStateUpdates();

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

    final class StateMachineUpdate<T extends OrbitStateMachineStates> {
        private final OrbitStateMachine<T> stateMachine;
        private final T state;
        private T lastState;

        public StateMachineUpdate(OrbitStateMachine<T> stateMachine, T state) {
            this.stateMachine = stateMachine;
            this.state = state;
        }

        public void update() {
            lastState = stateMachine.getState();
            stateMachine.setState(state);
        }

        public void undo() {
            stateMachine.setState(lastState);
        }
    }
}
