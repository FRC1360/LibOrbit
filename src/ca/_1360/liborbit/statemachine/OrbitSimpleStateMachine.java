package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;

import java.util.List;
import java.util.function.Predicate;

public class OrbitSimpleStateMachine<T extends OrbitStateMachineSimpleStates> extends OrbitStateMachine<T> {
    public OrbitSimpleStateMachine(List<T> states) throws OrbitPipelineInvalidConfigurationException {
        super(states.get(0));
        states.get(0).getStateUpdates().forEach(OrbitStateMachineSimpleStates.StateMachineUpdate::update);
        OrbitPipelineManager.runBatch(states.get(0).getConnections().stream().map(OrbitPipelineManager::enableOp).toArray(OrbitPipelineManager.BatchOperation[]::new));
    }

    @Override
    protected boolean setStateCore(T oldState, T newState) {
        List<OrbitPipelineConnection> disable = oldState.getConnections();
        List<OrbitPipelineConnection> enable = newState.getConnections();
        try {
            OrbitPipelineManager.runBatch(disable.stream().filter(((Predicate<OrbitPipelineConnection>) enable::contains).negate()).map(OrbitPipelineManager::disableOp).toArray(OrbitPipelineManager.BatchOperation[]::new));
            newState.getStateUpdates().forEach(OrbitStateMachineSimpleStates.StateMachineUpdate::update);
            OrbitPipelineManager.runBatch(enable.stream().filter(((Predicate<OrbitPipelineConnection>) disable::contains).negate()).map(OrbitPipelineManager::enableOp).toArray(OrbitPipelineManager.BatchOperation[]::new));
            return true;
        } catch (OrbitPipelineInvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
