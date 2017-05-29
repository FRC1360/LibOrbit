package ca._1360.liborbit.test;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public final class JoystickTest {
    public static void main(String[] args) {
        for (Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            System.out.println(controller.getName());
            for (Component component : controller.getComponents())
                System.out.printf("\t%s (%s)\n", component.getName(), component.getIdentifier().getName());
        }
    }
}
