package ca._1360.liborbit;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitInputOutputProvider;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;
import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class OrbitRobotBase extends IterativeRobot {
	public OrbitRobotBase(OrbitInputOutputProvider provider, long cyclePeriod, TimeUnit cycleUnit) {
		OrbitInputOutputManager.setProvider(provider);
		if (provider instanceof OrbitEmulatedInputOutputProvider)
			setUpEmulation((OrbitEmulatedInputOutputProvider) provider);
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(OrbitFunctionUtilities.combine(OrbitPipelineManager::updateAll, this::runCycle), 0, cyclePeriod, cycleUnit);
	}

	protected abstract void setUpEmulation(OrbitEmulatedInputOutputProvider provider);
	
	protected abstract void runCycle();
}
