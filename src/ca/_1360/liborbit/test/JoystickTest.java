package ca._1360.liborbit.test;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitLocalJoystickProvider;

public final class JoystickTest {
    public static void main(String[] args) {
    	OrbitInputOutputManager.setProvider(new OrbitEmulatedInputOutputProvider(null));
        int count = OrbitLocalJoystickProvider.refresh();
        for (int i = 0; i < count; ++i) {
        	System.out.println(OrbitInputOutputManager.getProvider().getJoystick(i).getName());
        }
    }
}
