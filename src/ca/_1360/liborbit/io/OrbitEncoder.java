package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;

public final class OrbitEncoder {
    private final OrbitPipelineInputEndpoint valueEndpoint;
    private final OrbitPipelineInputEndpoint rateEndpoint;

    public OrbitEncoder(int portA, int portB) {
        valueEndpoint = null;
        rateEndpoint = null;
    }

    public int getValueInteger() {
        return 0;
    }

    public OrbitPipelineInputEndpoint getValue() {
        return valueEndpoint;
    }

    public OrbitPipelineInputEndpoint getRate() {
        return rateEndpoint;
    }
}
