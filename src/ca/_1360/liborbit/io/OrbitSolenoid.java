package ca._1360.liborbit.io;

import ca._1360.liborbit.statemachine.OrbitStateMachine;

public final class OrbitSolenoid extends OrbitStateMachine<OrbitSolenoidStates> {
    public OrbitSolenoid(int port) {
        super(OrbitSolenoidStates.SOLENOID_DISENGAGED);
    }

    @Override
    protected boolean setStateCore(OrbitSolenoidStates oldState, OrbitSolenoidStates newState) {
        //TODO: update solenoid
        return true;
    }
}
