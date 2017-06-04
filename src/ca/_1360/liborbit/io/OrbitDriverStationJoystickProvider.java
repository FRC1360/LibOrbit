package ca._1360.liborbit.io;

import edu.wpi.first.wpilibj.Joystick;

public final class OrbitDriverStationJoystickProvider extends Joystick implements OrbitJoystickProvider {
    public OrbitDriverStationJoystickProvider(int id) {
    	super(id);
    }

	@Override
	public double getAxis(int i) {
		return getRawAxis(i);
	}

	@Override
	public boolean getButton(int i) {
		return getRawButton(i);
	}

	@Override
	public int getPov(int i) {
		return getPOV(i);
	}

	@Override
	public void setRumble(int i, double v) {
		setRumble(RumbleType.values()[i], v);
	}
}
