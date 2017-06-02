package ca._1360.liborbit.test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitLocalJoystickProvider;
import ca._1360.liborbit.io.OrbitXbox360Controller;
import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineExportConnector;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;

public final class JoystickTest {
    public static void main(String[] args) throws OrbitPipelineInvalidConfigurationException {
    	OrbitInputOutputManager.setProvider(new OrbitEmulatedInputOutputProvider(null));
        boolean[] connected = OrbitLocalJoystickProvider.refresh();
        if (!connected[0])
        	throw new RuntimeException();
        OrbitXbox360Controller controller = new OrbitXbox360Controller(0);
        new OrbitPipelineConnection(controller.getRightTrigger(), System.out::println, true);
        new OrbitPipelineConnection(controller.getLeftTrigger(), new OrbitPipelineExportConnector<>(x -> x > 0.9, controller::vibrateLeft), true);
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(OrbitPipelineManager::updateAll, 0, 100, TimeUnit.MILLISECONDS);
    }
}
