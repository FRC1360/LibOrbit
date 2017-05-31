package ca._1360.liborbit.test;

import ca._1360.liborbit.io.OrbitEmulatedInputOutputProvider;
import ca._1360.liborbit.io.OrbitEncoder;
import ca._1360.liborbit.io.OrbitInputOutputManager;
import ca._1360.liborbit.io.OrbitMotor;
import ca._1360.liborbit.pipeline.OrbitPipelineConnection;
import ca._1360.liborbit.pipeline.OrbitPipelineConstantValueSource;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.pipeline.filters.OrbitSlewFilter;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EmulatorTest {
    public static void main(String[] args) throws OrbitPipelineInvalidConfigurationException, InterruptedException {
        OrbitEmulatedInputOutputProvider provider = new OrbitEmulatedInputOutputProvider();
        provider.mapMotor(0, 0, 0.00884421747, 0.6);
        provider.mapEncoder(0, 1, 0, 14.0);
        OrbitInputOutputManager.setProvider(provider);
        OrbitMotor motor = new OrbitMotor(0, 0);
        OrbitEncoder encoder = new OrbitEncoder(0, 1);
        OrbitSlewFilter slew = new OrbitSlewFilter(0.01, -1.0, 1.0);
        OrbitPipelineConstantValueSource targetPower = new OrbitPipelineConstantValueSource(0.8);
        new OrbitPipelineConnection(targetPower, slew, true);
        new OrbitPipelineConnection(slew, motor.getPowerEndpoint(), true);
        provider.start();
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> {
            OrbitPipelineManager.updateAll();
            System.out.printf("%f,%f\n", encoder.getRate().get().orElse(0.0), motor.getCurrentEndpoint().get().orElse(0.0));
        }, 0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(5000);
        targetPower.setValue(-0.05);
        Thread.sleep(200);
        targetPower.setValue(0.0);
    }
}
