package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitInputOutputManager;

public abstract class OrbitEmulatedRobotBase extends OrbitRobotBase {
	public OrbitEmulatedRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		super(cyclePeriod, cycleUnit);
		OrbitInputOutputManager.setProvider(setUpEmulation());
	}
}
