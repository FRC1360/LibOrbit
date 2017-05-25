package ca._1360.liborbit.auto;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates;

import java.util.Collections;
import java.util.List;

public abstract class OrbitAutonomousCommand<T> implements OrbitStateMachineSimpleStates {
    private final T subsystem;
    private Runnable gotoNextFunc;
    private boolean running;
    private long timeout;

    public OrbitAutonomousCommand(T subsystem, long timeout) {
        this.subsystem = subsystem;
        this.timeout = timeout;
    }

    public T getSubsystem() {
        return subsystem;
    }

    public long getTimeout() {
        return timeout;
    }

    public void join() throws InterruptedException {
        synchronized (subsystem) {
            if (running)
                subsystem.wait();
        }
    }

    public void setGotoNextFunc(Runnable gotoNextFunc) {
        this.gotoNextFunc = gotoNextFunc;
    }

    protected void gotoNext() {
        gotoNextFunc.run();
    }

    protected void initializeCore() {
    }

    protected void deinitializeCore() {
    }

    @Override
    public void initialize() {
        running = true;
        initializeCore();
    }

    @Override
    public void deinitialize() {
        deinitializeCore();
        synchronized (subsystem) {
            running = false;
            subsystem.notifyAll();
        }
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
