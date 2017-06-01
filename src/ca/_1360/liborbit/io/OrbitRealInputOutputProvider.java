package ca._1360.liborbit.io;

public final class OrbitRealInputOutputProvider implements OrbitInputOutputProvider {
    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return null;
    }

    @Override
    public void setMotorPower(int port, double power) {
    }

    @Override
    public void setSolenoid(int port, boolean engaged) {
    }

    @Override
    public void setDigitalOut(int port, boolean high) {
    }

    @Override
    public double getCurrent(int port) {
        return 0;
    }

    @Override
    public boolean getDigitalIn(int port) {
        return false;
    }

    @Override
    public OrbitEncoderProvider getEncoder(int portA, int portB) {
        return null;
    }

    @Override
    public OrbitAhrsProvider getAhrs() {
        return null;
    }
}
