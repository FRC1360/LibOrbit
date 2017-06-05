/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitEncoderProvider.java
 * Base interface for providing encoder functionality
 */

package ca._1360.liborbit.io;

public interface OrbitEncoderProvider {
    /**
     * @return The relative position of the encoder, in encoder ticks, as an integer
     */
    int getPosition();
    
    /**
     * @return The angular velocity of the encoder, in encoder ticks per second
     */
    double getRate();
    
    /**
     * Resets the encoder position
     */
    void reset();
}
