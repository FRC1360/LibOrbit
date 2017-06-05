/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitSolenoidStates.java
 * The valid states of a solenoid
 */

package ca._1360.liborbit.io;

import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

public enum OrbitSolenoidStates implements OrbitStateMachineStates {
    SOLENOID_ENGAGED(true),
    SOLENOID_DISENGAGED(false);

    private final boolean value;

    OrbitSolenoidStates(boolean value) {
        this.value = value;
    }

    /**
     * @return Get whether the state represents an engaged solenoid
     */
    public boolean getValue() {
        return value;
    }

    /**
     * @param engaged True if the solenoid should be engaged, false if it should be disengaged
     * @return The applicable state
     */
    public static OrbitSolenoidStates getState(boolean engaged) {
        return engaged ? SOLENOID_ENGAGED : SOLENOID_DISENGAGED;
    }
}
