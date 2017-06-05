/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAutonomousCommand.java
 * Base class for autonomous commands
 */

package ca._1360.liborbit.auto;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates;

import java.util.Collections;
import java.util.List;

/**
 * @param <T> The subsystem type; usually an enum
 */
public abstract class OrbitAutonomousCommand<T> implements OrbitStateMachineSimpleStates {
    private final T subsystem;
    private final long timeout;
    private Runnable gotoNextFunc;
    private boolean running;

    /**
     * @param subsystem The subsystem that the command runs on
     * @param timeout The maximum duration of the command, in milliseconds
     */
    public OrbitAutonomousCommand(T subsystem, long timeout) {
        this.subsystem = subsystem;
        this.timeout = timeout;
    }

    /**
     * @return Gets the subsystem that the command runs on
     */
    public final T getSubsystem() {
        return subsystem;
    }

    /**
     * @return Gets the maximum duration of the command, in milliseconds
     */
    public final long getTimeout() {
        return timeout;
    }

    /**
     * Blocks until the command has exited
     * @throws InterruptedException Thrown if the current thread is interrupted while blocking
     */
    public final void join() throws InterruptedException {
        if (subsystem != null)
            synchronized (subsystem) {
                if (running)
                    subsystem.wait();
            }
    }

    /**
     * @param gotoNextFunc The function to be run when the command wishes to exit
     */
    public final void setGotoNextFunc(Runnable gotoNextFunc) {
        this.gotoNextFunc = gotoNextFunc;
    }

    /**
     * Exits the command
     */
    protected final void gotoNext() {
        gotoNextFunc.run();
    }

    /**
     * To be run when the command starts
     */
    protected void initializeCore() {
    }

    /**
     * To be run when the command exits
     */
    protected void deinitializeCore() {
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachineStates#initialize()
     */
    @Override
    public final void initialize() {
        running = true;
        initializeCore();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachineStates#deinitialize()
     */
    @Override
    public final void deinitialize() {
        deinitializeCore();
        // Inform any threads waiting on this command that it has completed
        if (subsystem != null)
            synchronized (subsystem) {
                running = false;
                subsystem.notifyAll();
            }
        else
            running = false;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates#getConnections()
     */
    @Override
    public List<OrbitPipelineConnection> getConnections() {
        return Collections.emptyList();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates#getStateUpdates()
     */
    @Override
    public List<StateMachineUpdate<?>> getStateUpdates() {
        return Collections.emptyList();
    }
}
