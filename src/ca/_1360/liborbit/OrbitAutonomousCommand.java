package ca._1360.liborbit;

public abstract class OrbitAutonomousCommand<T> implements OrbitStateMachineStates {
    private T subsystem;
    private Runnable gotoNextFunc;

    public OrbitAutonomousCommand(T subsystem) {
        this.subsystem = subsystem;
    }

    public T getSubsystem() {
        return subsystem;
    }

    public void setGotoNextFunc(Runnable gotoNextFunc) {
        this.gotoNextFunc = gotoNextFunc;
    }

    protected void gotoNext() {
        gotoNextFunc.run();
    }

    @Override
    public abstract void initialize();

    @Override
    public abstract void deinitialize();
}
