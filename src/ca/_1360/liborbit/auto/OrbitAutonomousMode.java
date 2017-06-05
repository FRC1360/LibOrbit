/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAutonomousMode.java
 * Base interface for autonomous modes
 */

package ca._1360.liborbit.auto;

import java.util.function.Consumer;

/**
 * @param <T> The subsystem type; usually an enum
 */
public interface OrbitAutonomousMode<T> {
    /**
     * To be run to construct the mode and add its commands to the controller
     * @param consumer Function to add a command
     */
    void add(Consumer<OrbitAutonomousCommand<T>> consumer);
}
