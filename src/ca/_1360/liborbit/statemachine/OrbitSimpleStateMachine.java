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
