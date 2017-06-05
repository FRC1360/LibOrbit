/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitDriverStationJoystickProvider.java
 * Provides access to joysticks connected to the driver station of a real robot
 */

package ca._1360.liborbit.io;

import edu.wpi.first.wpilibj.Joystick;

public final class OrbitDriverStationJoystickProvider extends Joystick implements OrbitJoystickProvider {
    /**
     * @param id The port number of the joystick
     */
    public OrbitDriverStationJoystickProvider(int id) {
    	super(id);
    }

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitJoystickProvider#getAxis(int)
	 */
	@Override
	public double getAxis(int i) {
		return getRawAxis(i);
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitJoystickProvider#getButton(int)
	 */
	@Override
	public boolean getButton(int i) {
		return getRawButton(i);
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitJoystickProvider#getPov(int)
	 */
	@Override
	public int getPov(int i) {
		return getPOV(i);
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitJoystickProvider#setRumble(int, double)
	 */
	@Override
	public void setRumble(int i, double v) {
		setRumble(RumbleType.values()[i], v);
	}
}
