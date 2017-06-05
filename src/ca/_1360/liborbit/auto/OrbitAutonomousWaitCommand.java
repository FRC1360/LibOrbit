/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAutonomousWaitCommand.java
 * A command that waits for another subsystem to exit its current command
 */

package ca._1360.liborbit.auto;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

/**
 * @param <T> The subsystem type; usually an enum
 */
public final class OrbitAutonomousWaitCommand<T> extends OrbitAutonomousCommand<T> {
    private final T targetSubsystem;
    private final OrbitAutonomousController<T> controller;
    private Thread thread;

    /**
     * @param subsystem The subsystem that the command runs on
     * @param timeout The maximum duration of the command, in milliseconds
     * @param targetSubsystem The subsystem to wait on
     * @param controller The autonomous controller
     */
    public OrbitAutonomousWaitCommand(T subsystem, long timeout, T targetSubsystem, OrbitAutonomousController<T> controller) {
        super(subsystem, timeout);
        this.targetSubsystem = targetSubsystem;
        this.controller = controller;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#initializeCore()
     */
    @Override
    protected void initializeCore() {
    	// Start a thread that waits for the command to exit
        thread = new Thread(OrbitFunctionUtilities.combine(OrbitFunctionUtilities.handleException(OrbitFunctionUtilities.specializeEx(controller::waitFor, targetSubsystem), e -> {}), this::gotoNext));
        thread.start();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#deinitializeCore()
     */
    @Override
    protected void deinitializeCore() {
        thread.interrupt();
    }
}
