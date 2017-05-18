package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OrbitPipelineConnectionBasedStateMachine<T extends OrbitStateMachineStates> extends OrbitStateMachine<T> {
    private Map<T, OrbitPipelineConnection[]> map;

    public OrbitPipelineConnectionBasedStateMachine(T state, Map<T, OrbitPipelineConnection[]> map) throws OrbitPipelineInvalidConfigurationException {
        super(state);
        this.map = map;
        OrbitPipelineManager.runBatch(Arrays.stream(map.get(state)).map(OrbitPipelineManager::enableOp).toArray(OrbitPipelineManager.BatchOperation[]::new));
    }

    @Override
    protected boolean setStateCore(T oldState, T newState) {
        List<OrbitPipelineConnection> disable = Arrays.asList(map.get(oldState));
        List<OrbitPipelineConnection> enable = Arrays.asList(map.get(newState));
        try {
            OrbitPipelineManager.runBatch(Stream.concat(disable.stream().filter(((Predicate<OrbitPipelineConnection>) enable::contains).negate()).map(OrbitPipelineManager::disableOp), enable.stream().filter(((Predicate<OrbitPipelineConnection>) disable::contains).negate()).map(OrbitPipelineManager::enableOp)).toArray(OrbitPipelineManager.BatchOperation[]::new));
            return true;
        } catch (OrbitPipelineInvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
