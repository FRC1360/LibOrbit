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

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitInputOutputManager;

public abstract class OrbitEmulatedRobotBase implements OrbitRobot {
	/**
	 * @param cyclePeriod The period of the main control loop
	 * @param cycleUnit The units for cyclePeriod
	 */
	public OrbitEmulatedRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		OrbitRobot.start(this, cyclePeriod, cycleUnit);
		OrbitEmulatedInputOutputProvider provider = setUpEmulation();
		provider.start();
		OrbitInputOutputManager.setProvider(provider);
	}
	
	/**
	 * To be called when the robot starts
	 */
	public void robotInit() { }
	
	/**
	 * To be called when the robot enters disabled mode 
	 */
	public void disabledInit() { }
	
	/**
	 * To be called when the robot enters autonomous mode
	 */
	public void autonomousInit() { }
	
	/**
	 * To be called when the robot enters teleop mode
	 */
	public void teleopInit() { }
	
	/**
	 * @return False, as a robot running on the emulator is not real
	 */
	public final boolean isReal() {
		return false;
	}
	
	/**
	 * @return True, as a robot running on the emulator is a simulation
	 */
	public final boolean isSimulation() {
		return true;
	}
}
