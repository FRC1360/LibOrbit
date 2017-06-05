/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitMotor.java
 * An API for accessing real and emulated motor controllers
 */

package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public final class OrbitMotor {
    private final OrbitPipelineInputEndpoint powerEndpoint;
    private final OrbitPipelineOutputEndpoint currentEndpoint;

    /**
     * @param pwmPort The motor`s PWM port
     * @param pdpPort The motor`s PDP port
     */
    public OrbitMotor(int pwmPort, int pdpPort) {
        powerEndpoint = OrbitFunctionUtilities.specializeFirst(OrbitInputOutputManager.getProvider()::setMotorPower, pwmPort)::accept;
        currentEndpoint = (OrbitPipelineSimpleSource) OrbitFunctionUtilities.specialize(OrbitInputOutputManager.getProvider()::getCurrent, pdpPort)::get;
    }
    
    // Accessor methods for pipeline endpoints

    public OrbitPipelineInputEndpoint getPower() {
        return powerEndpoint;
    }

    public OrbitPipelineOutputEndpoint getCurrent() {
        return currentEndpoint;
    }
}
