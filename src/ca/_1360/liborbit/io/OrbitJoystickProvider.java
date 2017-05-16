package ca._1360.liborbit.io;

public interface OrbitJoystickProvider {
    String getName();
    double getAxis(int i);
    boolean getButton(int i);
    int getPov(int i);
    void setOutput(int i, boolean v);
}
