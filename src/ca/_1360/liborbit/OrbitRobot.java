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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public interface OrbitRobot {
	/**
	 * Starts the main control loop
	 * @param robot The robot controller
	 * @param cyclePeriod The period of the main control loop
	 * @param cycleUnit The units for cyclePeriod
	 */
	static void start(OrbitRobot robot, long cyclePeriod, TimeUnit cycleUnit) {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(OrbitFunctionUtilities.combine(OrbitPipelineManager::updateAll, robot::runCycle), 0, cyclePeriod, cycleUnit);
	}

	/**
	 * @return An AHRS configured on the correct port; may be null if AHRS is never used
	 */
	AHRS setUpAhrs();

	/**
	 * @return An emulation provider configured with required motors and sensors
	 */
	OrbitEmulatedInputOutputProvider setUpEmulation();
	
	/**
	 * To be run each cycle of the main control loop
	 */
	void runCycle();
}
