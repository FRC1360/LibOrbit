package ca._1360.liborbit;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public interface OrbitRobot {
	static void start(OrbitRobot robot, long cyclePeriod, TimeUnit cycleUnit) {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(OrbitFunctionUtilities.combine(OrbitPipelineManager::updateAll, robot::runCycle), 0, cyclePeriod, cycleUnit);
	}

	AHRS setUpAhrs();

	OrbitEmulatedInputOutputProvider setUpEmulation();
	
	void runCycle();
}
