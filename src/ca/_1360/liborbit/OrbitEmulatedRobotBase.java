/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitEmulatedRobotBase.java
 * Base class for robot controller running on emulated hardware
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
