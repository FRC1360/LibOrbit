package ca._1360.liborbit;

import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public final class OrbitRobotUtils {
	private OrbitRobotUtils() { }
	
	public static void connect(OrbitPipelineOutputEndpoint source, OrbitPipelineInputEndpoint destination) {
		try {
			new OrbitPipelineConnection(source, destination, true);
		} catch (OrbitPipelineInvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
