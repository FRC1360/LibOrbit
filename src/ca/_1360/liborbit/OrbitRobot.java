/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitRobot.java
 * Base interface for robot controllers
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
