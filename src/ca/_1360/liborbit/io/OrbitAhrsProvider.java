/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitAhrsProvider.java
 * Base interface for providers of AHRS functionality
 */

package ca._1360.liborbit.io;

import java.util.OptionalDouble;

public interface OrbitAhrsProvider {
	// Getter function for each property
    OptionalDouble getContinuousAngle();
    OptionalDouble getYaw();
    OptionalDouble getPitch();
    OptionalDouble getRoll();
    OptionalDouble getAccelX();
    OptionalDouble getAccelY();
    OptionalDouble getAccelZ();
    OptionalDouble getVelX();
    OptionalDouble getVelY();
    OptionalDouble getVelZ();
    OptionalDouble getRawAccelX();
    OptionalDouble getRawAccelY();
    OptionalDouble getRawAccelZ();
    OptionalDouble getRawGyroX();
    OptionalDouble getRawGyroY();
    OptionalDouble getRawGyroZ();
    OptionalDouble getRawMagX();
    OptionalDouble getRawMagY();
    OptionalDouble getRawMagZ();
    OptionalDouble getBarometricPressure();
}
