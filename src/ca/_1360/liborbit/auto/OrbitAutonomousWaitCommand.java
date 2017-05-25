package ca._1360.liborbit.auto;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public final class OrbitAutonomousWaitCommand<T> extends OrbitAutonomousCommand<T> {
    private T targetSubsystem;
    private OrbitAutonomousController<T> controller;
    private Thread thread;

    public OrbitAutonomousWaitCommand(T subsystem, T targetSubsystem, OrbitAutonomousController<T> controller) {
        super(subsystem);
        this.targetSubsystem = targetSubsystem;
        this.controller = controller;
    }

    @Override
    protected void initializeCore() {
        thread = new Thread(OrbitFunctionUtilities.combine(OrbitFunctionUtilities.handleException(OrbitFunctionUtilities.specializeEx(controller::waitFor, targetSubsystem), e -> {}), this::gotoNext));
        thread.start();
    }

    @Override
    protected void deinitializeCore() {
        thread.interrupt();
    }
}