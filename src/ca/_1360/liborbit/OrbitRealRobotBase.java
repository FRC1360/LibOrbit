package ca._1360.liborbit;

import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;

import ca._1360.liborbit.io.OrbitRealInputOutputProvider;

public abstract class OrbitRealRobotBase extends OrbitRobotBase {
	public OrbitRealRobotBase(AHRS ahrs, long cyclePeriod, TimeUnit cycleUnit) {
		super(new OrbitRealInputOutputProvider(ahrs), cyclePeriod, cycleUnit);
	}
}
