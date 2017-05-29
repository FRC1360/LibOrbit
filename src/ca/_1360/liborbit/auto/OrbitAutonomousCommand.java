package ca._1360.liborbit.auto;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates;

import java.util.Collections;
import java.util.List;

public abstract class OrbitAutonomousCommand<T> implements OrbitStateMachineSimpleStates {
    private final T subsystem;
    private final long timeout;
    private Runnable gotoNextFunc;
    private boolean running;

    public OrbitAutonomousCommand(T subsystem, long timeout) {
        this.subsystem = subsystem;
        this.timeout = timeout;
    }

    public final T getSubsystem() {
        return subsystem;
    }

    public final long getTimeout() {
        return timeout;
    }

    public final void join() throws InterruptedException {
        if (subsystem != null)
            synchronized (subsystem) {
                if (running)
                    subsystem.wait();
            }
    }

    public final void setGotoNextFunc(Runnable gotoNextFunc) {
        this.gotoNextFunc = gotoNextFunc;
    }

    protected final void gotoNext() {
        gotoNextFunc.run();
    }

    protected void initializeCore() {
    }

    protected void deinitializeCore() {
    }

    @Override
    public final void initialize() {
        running = true;
        initializeCore();
    }

    @Override
    public final void deinitialize() {
        deinitializeCore();
        if (subsystem != null)
            synchronized (subsystem) {
                running = false;
                subsystem.notifyAll();
            }
        else
            running = false;
    }

    @Override
    public List<OrbitPipelineConnection> getConnections() {
        return Collections.emptyList();
    }

    @Override
    public List<StateMachineUpdate<?>> getStateUpdates() {
        return Collections.emptyList();
    }
}
