/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitSimpleStateMachine.java
 * A state machine with states that consist of required pipeline connections and state machine updates
 */

package ca._1360.liborbit.statemachine;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.util.OrbitMiscUtilities;

import java.util.List;
import java.util.function.Predicate;

public class OrbitSimpleStateMachine<T extends OrbitStateMachineSimpleStates> extends OrbitStateMachine<T> {
    /**
     * @param states The valid states, with the initial state as the first element
     * @throws OrbitPipelineInvalidConfigurationException Thrown if the first state provides invalid connections
     */
    public OrbitSimpleStateMachine(List<T> states) throws OrbitPipelineInvalidConfigurationException {
        super(initialize(states.get(0)));
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachine#setStateCore(ca._1360.liborbit.statemachine.OrbitStateMachineStates, ca._1360.liborbit.statemachine.OrbitStateMachineStates)
     */
    @Override
    protected boolean setStateCore(T oldState, T newState) {
        List<OrbitPipelineConnection> disable = oldState.getConnections();
        List<OrbitPipelineConnection> enable = newState.getConnections();
        try {
            OrbitPipelineManager.runBatch(OrbitMiscUtilities.concat(disable.stream().filter(((Predicate<OrbitPipelineConnection>) enable::contains).negate()).map(OrbitPipelineManager::disableOp), newState.getStateUpdates().stream().map(update -> OrbitPipelineManager.miscOp(update::update, update::undo)), enable.stream().filter(((Predicate<OrbitPipelineConnection>) disable::contains).negate()).map(OrbitPipelineManager::enableOp)).toArray(OrbitPipelineManager.BatchOperation[]::new));
            return true;
        } catch (OrbitPipelineInvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Wrapper required so that the state is fully initialized before being passed to super constructor
     * @param state The initial state
     * @return The initial state
     * @throws OrbitPipelineInvalidConfigurationException Thrown if the first state provides invalid connections
     */
    private static <T extends OrbitStateMachineSimpleStates> T initialize(T state) throws OrbitPipelineInvalidConfigurationException {
    	state.getStateUpdates().forEach(OrbitStateMachineSimpleStates.StateMachineUpdate::update);
        OrbitPipelineManager.runBatch(state.getConnections().stream().map(OrbitPipelineManager::enableOp).toArray(OrbitPipelineManager.BatchOperation[]::new));
        return state;
    }
}
