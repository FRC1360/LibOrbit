package ca._1360.liborbit.auto;

import ca._1360.liborbit.statemachine.OrbitStateMachine;
import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

import java.util.Collections;
import java.util.List;

public class OrbitAutonomousStateMachineUpdateCommand<T, U extends OrbitStateMachineStates> extends OrbitAutonomousCommand<T> {
    private final StateMachineUpdate<U> update;

    public OrbitAutonomousStateMachineUpdateCommand(T subsystem, OrbitStateMachine<U> stateMachine, U state) {
        super(subsystem, 0);
        update = new StateMachineUpdate<>(stateMachine, state);
    }

    @Override
    public void initialize() {
        gotoNext();
    }

    @Override
    public List<StateMachineUpdate<?>> getStateUpdates() {
        return Collections.singletonList(update);
    }
}
