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
import ca._1360.liborbit.util.OrbitContainer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EmulatorTest {
    public static void main(String[] args) throws OrbitPipelineInvalidConfigurationException, InterruptedException {
        OrbitEmulatedInputOutputProvider provider = new OrbitEmulatedInputOutputProvider(null);
        OrbitContainer<Double> inertia = new OrbitContainer<>(0.00884421747);
        OrbitContainer<Double> friction = new OrbitContainer<>(0.6);
        provider.mapMotor(0, 0, inertia::getValue, friction::getValue);
        provider.mapEncoder(0, 1, 0, 14.0);
        OrbitInputOutputManager.setProvider(provider);
        OrbitMotor motor = new OrbitMotor(0, 0);
        OrbitEncoder encoder = new OrbitEncoder(0, 1);
        OrbitSlewFilter slew = new OrbitSlewFilter(0.01, -1.0, 1.0);
        OrbitPipelineConstantValueSource targetPower = new OrbitPipelineConstantValueSource(1.0);
        new OrbitPipelineConnection(targetPower, slew, true);
        new OrbitPipelineConnection(slew, motor.getPowerEndpoint(), true);
        provider.start();
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> {
            OrbitPipelineManager.updateAll();
            System.out.printf("%f,%f\n", encoder.getRate().get().orElse(0.0), motor.getCurrentEndpoint().get().orElse(0.0));
        }, 0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(4000);
        inertia.setValue(0.016);
        friction.setValue(1.0);
        Thread.sleep(1000);
        targetPower.setValue(-0.05);
        Thread.sleep(200);
        targetPower.setValue(0.0);
    }
}
