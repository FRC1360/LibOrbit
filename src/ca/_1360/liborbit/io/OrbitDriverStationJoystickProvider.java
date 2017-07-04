/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
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
