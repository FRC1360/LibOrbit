package ca._1360.liborbit.io;

import net.java.games.input.Controller;

public final class OrbitLocalJoystickProvider implements OrbitJoystickProvider {
    private final Controller controller;

    public OrbitLocalJoystickProvider(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return controller.getName();
    }

    @Override
    public double getAxis(int i) {
        return 0.0;
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
