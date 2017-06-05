/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAutonomousSolenoidCommand.java
 * A command that puts a solenoid in a given state
 */

package ca._1360.liborbit.auto;

import ca._1360.liborbit.io.OrbitSolenoid;
import ca._1360.liborbit.io.OrbitSolenoidStates;

/**
 * @param <T> The subsystem type; usually an enum
 */
public final class OrbitAutonomousSolenoidCommand<T> extends OrbitAutonomousStateMachineUpdateCommand<T, OrbitSolenoidStates> {
    /**
     * @param subsystem The subsystem that the command runs on
     * @param solenoid The solenoid to engage or disengage
     * @param engaged True if the solenoid should be engaged; false if it should be disengaged
     */
    public OrbitAutonomousSolenoidCommand(T subsystem, OrbitSolenoid solenoid, boolean engaged) {
        super(subsystem, solenoid, OrbitSolenoidStates.getState(engaged));
    }
}
