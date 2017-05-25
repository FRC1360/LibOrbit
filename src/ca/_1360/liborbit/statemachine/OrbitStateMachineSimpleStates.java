package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;

import java.util.List;
import java.util.Map;

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
        private OrbitStateMachine<T> stateMachine;
        private T state;

        public StateMachineUpdate(OrbitStateMachine<T> stateMachine, T state) {
            this.stateMachine = stateMachine;
            this.state = state;
        }

        public void update() {
            stateMachine.setState(state);
        }
    }
}
