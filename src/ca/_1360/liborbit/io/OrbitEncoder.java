package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;

import java.util.OptionalDouble;

public final class OrbitEncoder {
    private final OrbitEncoderProvider provider;
    private final OrbitPipelineOutputEndpoint valueEndpoint;
    private final OrbitPipelineOutputEndpoint rateEndpoint;

    public OrbitEncoder(int portA, int portB) {
        provider = OrbitInputOutputManager.getProvider().getEncoder(portA, portB);
        valueEndpoint = () -> OptionalDouble.of(provider.getPosition());
        rateEndpoint = (OrbitPipelineSimpleSource) provider::getRate;
    }

    public int getValueInteger() {
        return 0;
    }

    public OrbitPipelineOutputEndpoint getValue() {
        return valueEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRate() {
        return rateEndpoint;
    }
}
