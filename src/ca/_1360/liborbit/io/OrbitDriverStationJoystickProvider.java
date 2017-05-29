package ca._1360.liborbit.io;

public final class OrbitDriverStationJoystickProvider implements OrbitJoystickProvider {
    public OrbitDriverStationJoystickProvider(int id) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double getAxis(int i) {
        return 0;
    }

    @Override
    public boolean getButton(int i) {
        return false;
    }

    @Override
    public int getPov(int i) {
        return 0;
    }

    @Override
    public void setOutput(int i, boolean v) {
    }
}
