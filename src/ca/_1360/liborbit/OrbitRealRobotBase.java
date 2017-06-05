/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitRealRobotBase.java
 * Base class for robot controller running on real hardware
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
