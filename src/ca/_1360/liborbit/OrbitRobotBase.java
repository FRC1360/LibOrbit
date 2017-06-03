package ca._1360.liborbit;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;
import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class OrbitRobotBase extends IterativeRobot {
	public OrbitRobotBase(long cyclePeriod, TimeUnit cycleUnit) {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(OrbitFunctionUtilities.combine(OrbitPipelineManager::updateAll, this::runCycle), 0, cyclePeriod, cycleUnit);
	}
	
	protected static void connect(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination) {
		try {
			new OrbitPipelineConnection(source, destination, true);
		} catch (OrbitPipelineInvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract AHRS setUpAhrs();

	protected abstract OrbitEmulatedInputOutputProvider setUpEmulation();
	
	protected abstract void runCycle();
}
