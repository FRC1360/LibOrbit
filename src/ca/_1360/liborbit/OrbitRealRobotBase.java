package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitRealInputOutputProvider;

public abstract class OrbitRealRobotBase extends OrbitRobotBase {
	public OrbitRealRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		super(cyclePeriod, cycleUnit);
		OrbitInputOutputManager.setProvider(new OrbitRealInputOutputProvider(setUpAhrs()));
	}
}
