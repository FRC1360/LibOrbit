/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitStateMachineStates.java
 * Base interface for valid state machine states
 */

package ca._1360.liborbit.statemachine;

public interface OrbitStateMachineStates {
    /**
     * To be run when the machine enters this state
     */
    default void initialize() { }

    /**
     * To be run when the machine exits this state
     */
    default void deinitialize() { }
}
