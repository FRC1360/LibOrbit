package ca._1360.liborbit.io;

import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

public enum OrbitSolenoidStates implements OrbitStateMachineStates {
    SOLENOID_ENGAGED(true),
    SOLENOID_DISENGAGED(false);

    private boolean value;

    OrbitSolenoidStates(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
