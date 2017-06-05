/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAutonomousStateMachineUpdateCommand.java
 * A command that places a given state machine in a given state
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
