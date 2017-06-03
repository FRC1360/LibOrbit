package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitAhrsProvider;
import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;

public abstract class OrbitEmulatedRobotBase extends OrbitRobotBase {
	public OrbitEmulatedRobotBase(OrbitAhrsProvider provider, long cyclePeriod, TimeUnit cycleUnit) {
		super(new OrbitEmulatedInputOutputProvider(provider), cyclePeriod, cycleUnit);
	}
}
