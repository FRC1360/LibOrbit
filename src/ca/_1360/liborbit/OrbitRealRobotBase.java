package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitRealInputOutputProvider;
import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class OrbitRealRobotBase extends IterativeRobot implements OrbitRobot {
	public OrbitRealRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		OrbitRobot.start(this, cyclePeriod, cycleUnit);
		OrbitInputOutputManager.setProvider(new OrbitRealInputOutputProvider(setUpAhrs()));
	}
}
