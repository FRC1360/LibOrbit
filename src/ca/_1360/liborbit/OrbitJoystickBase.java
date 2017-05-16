package ca._1360.liborbit;

public abstract class OrbitJoystickBase {
    private OrbitJoystickProvider provider;

    public OrbitJoystickBase(OrbitJoystickProvider provider) {
        this.provider = provider;
    }

    protected final String getName() {
        return provider.getName();
    }

    protected final double getAxis(int i) {
        return provider.getAxis(i);
    }

    protected final boolean getButton(int i) {
        return provider.getButton(i);
    }

    protected final int getPov(int i) {
        return provider.getPov(i);
    }

    protected final void setOutput(int i, boolean v) {
        provider.setOutput(i, v);
    }
}
