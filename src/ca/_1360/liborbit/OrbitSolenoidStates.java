package ca._1360.liborbit;

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
