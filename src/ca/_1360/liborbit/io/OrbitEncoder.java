/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitEncoder.java
 * An API for accessing real or emulated quadrature encoders
 */

package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineSimpleSource;

import java.util.OptionalDouble;

public final class OrbitEncoder {
    private final OrbitEncoderProvider provider;
    private final OrbitPipelineOutputEndpoint valueEndpoint;
    private final OrbitPipelineOutputEndpoint rateEndpoint;

    /**
     * @param portA The encoder's first DIO port
     * @param portB The encoder's second DIO port
     */
    public OrbitEncoder(int portA, int portB) {
        provider = OrbitInputOutputManager.getProvider().getEncoder(portA, portB);
        valueEndpoint = () -> OptionalDouble.of(provider.getPosition());
        rateEndpoint = (OrbitPipelineSimpleSource) provider::getRate;
    }
    
    /**
     * @return The relative position of the encoder, in encoder ticks, as an integer
     */
    public int getIntPosition() {
    	return provider.getPosition();
    }

    /**
     * @return The relative position of the encoder, in encoder ticks
     */
    public OrbitPipelineOutputEndpoint getValue() {
        return valueEndpoint;
    }

    /**
     * @return The angular velocity of the encoder, in encoder ticks per second
     */
    public OrbitPipelineOutputEndpoint getRate() {
        return rateEndpoint;
    }
}
