package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public final class OrbitMotor {
    private final OrbitPipelineInputEndpoint powerEndpoint;
    private final OrbitPipelineOutputEndpoint currentEndpoint;

    public OrbitMotor(int pwmPort, int pdpPort) {
        powerEndpoint = OrbitFunctionUtilities.specializeFirst(OrbitInputOutputManager.getProvider()::setMotorPower, pwmPort)::accept;
        currentEndpoint = (OrbitPipelineSimpleSource) OrbitFunctionUtilities.specialize(OrbitInputOutputManager.getProvider()::getCurrent, pdpPort)::get;
    }

    public OrbitPipelineInputEndpoint getPowerEndpoint() {
        return powerEndpoint;
    }

    public OrbitPipelineOutputEndpoint getCurrentEndpoint() {
        return currentEndpoint;
    }
}
