package ca._1360.liborbit.test;

import ca._1360.liborbit.pipeline.*;
import ca._1360.liborbit.pipeline.filters.OrbitIntegrationFilter;
import ca._1360.liborbit.pipeline.filters.OrbitUnaryOperatorFilter;
import ca._1360.liborbit.util.OrbitContainer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimplePipelineTest {
    public static void main(String[] args) throws OrbitPipelineInvalidConfigurationException {
        OrbitContainer<Double> sineOut = new OrbitContainer<>(0.0);
        OrbitContainer<Double> integralOut = new OrbitContainer<>(0.0);
        OrbitContainer<Double> diffOut = new OrbitContainer<>(1.0);
        OrbitPipelineSimpleSource source = diffOut::getValue;
        OrbitUnaryOperatorFilter sine = new OrbitUnaryOperatorFilter(Math::sin);
        OrbitIntegrationFilter integral = new OrbitIntegrationFilter();
        OrbitBinaryOperatorNode diff = new OrbitBinaryOperatorNode(true, (x, y) -> x - y);
        new OrbitPipelineConnection(source, sine, true);
        new OrbitPipelineConnection(source, integral, true);
        new OrbitPipelineConnection(sine, diff.getInput1(), true);
        new OrbitPipelineConnection(integral, diff.getInput2(), true);
        new OrbitPipelineConnection(sine, sineOut::setValue, true);
        new OrbitPipelineConnection(integral, integralOut::setValue, true);
        new OrbitPipelineConnection(diff.getOutput(), diffOut::setValue, true);
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> {
            OrbitPipelineManager.updateAll();
            System.out.printf("%f,%f,%f\n", sineOut.getValue(), integralOut.getValue(), diffOut.getValue());
        }, 0, 10, TimeUnit.MILLISECONDS);
    }
}
