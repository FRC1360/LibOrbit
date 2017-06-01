package ca._1360.liborbit.io;

public interface OrbitInputOutputProvider {
    OrbitJoystickProvider getJoystick(int id);
    void setMotorPower(int port, double power);
    void setSolenoid(int port, boolean engaged);
    void setDigitalOut(int port, boolean high);
    double getCurrent(int port);
    boolean getDigitalIn(int port);
    OrbitEncoderProvider getEncoder(int portA, int portB);
    OrbitAhrsProvider getAhrs();
}
