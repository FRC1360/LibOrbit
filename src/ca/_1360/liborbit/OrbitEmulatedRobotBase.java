package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitInputOutputManager;

public abstract class OrbitEmulatedRobotBase implements OrbitRobot {
	public OrbitEmulatedRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		OrbitRobot.start(this, cyclePeriod, cycleUnit);
		OrbitEmulatedInputOutputProvider provider = setUpEmulation();
		provider.start();
		OrbitInputOutputManager.setProvider(provider);
	}
	
	public void robotInit() { }
	
	public void disabledInit() { }
	
	public void autonomousInit() { }
	
	public void teleopInit() { }
	
	public final boolean isReal() {
		return false;
	}
	
	public final boolean isSimulation() {
		return true;
	}
}
