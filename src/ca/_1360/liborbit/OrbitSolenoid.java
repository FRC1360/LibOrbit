package ca._1360.liborbit;

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
