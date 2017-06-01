package ca._1360.liborbit.io;

import ca._1360.liborbit.statemachine.OrbitStateMachine;

public final class OrbitSolenoid extends OrbitStateMachine<OrbitSolenoidStates> {
    private final int port;

    public OrbitSolenoid(int port) {
        super(OrbitSolenoidStates.SOLENOID_DISENGAGED);
        this.port = port;
    }

    @Override
    protected boolean setStateCore(OrbitSolenoidStates oldState, OrbitSolenoidStates newState) {
        OrbitInputOutputManager.getProvider().setSolenoid(port, newState.getValue());
        return true;
    }
}
