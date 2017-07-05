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

package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitRealInputOutputProvider;
import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class OrbitRealRobotBase extends IterativeRobot implements OrbitRobot {
	private static boolean real = false;
	
	/**
	 * @param cyclePeriod The period of the main control loop
	 * @param cycleUnit The units for cyclePeriod
	 */
	public OrbitRealRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		real = true;
		OrbitRobot.start(this, cyclePeriod, cycleUnit);
		OrbitInputOutputManager.setProvider(new OrbitRealInputOutputProvider(setUpAhrs()));
	}
	
	/**
	 * @return True if a real robot controller has been initialized
	 */
	public static boolean isReal() {
		return real;
	}
}
