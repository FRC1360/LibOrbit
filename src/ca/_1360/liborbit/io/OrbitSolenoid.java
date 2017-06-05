/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitSolenoid.java
 * An API for accessing real or emulated pneumatic solenoids, represented as a state machine
 */

package ca._1360.liborbit.io;

import ca._1360.liborbit.statemachine.OrbitStateMachine;

public final class OrbitSolenoid extends OrbitStateMachine<OrbitSolenoidStates> {
    private final int port;

    /**
     * @param port The solenoid's PCM port
     */
    public OrbitSolenoid(int port) {
    	// Ensure that the solenoid is disengaged
        super(OrbitSolenoidStates.SOLENOID_DISENGAGED);
        this.port = port;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachine#setStateCore(ca._1360.liborbit.statemachine.OrbitStateMachineStates, ca._1360.liborbit.statemachine.OrbitStateMachineStates)
     */
    @Override
    protected boolean setStateCore(OrbitSolenoidStates oldState, OrbitSolenoidStates newState) {
    	// Update the solenoid through the primary I/O provider when the state changes
        OrbitInputOutputManager.getProvider().setSolenoid(port, newState.getValue());
        return true;
    }
}
