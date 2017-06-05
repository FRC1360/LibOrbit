/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitRealAhrsProvider.java
 * Provides access to an AHRS on a real robot
 */

package ca._1360.liborbit.io;

import java.util.OptionalDouble;

import com.kauailabs.navx.frc.AHRS;

public class OrbitRealAhrsProvider implements OrbitAhrsProvider {
	private AHRS ahrs;
	
	/**
	 * @param ahrs The raw AHRS object
	 */
	public OrbitRealAhrsProvider(AHRS ahrs) {
		this.ahrs = ahrs;
	}
	
	// Accessor methods for properties

	@Override
	public OptionalDouble getContinuousAngle() {
		return OptionalDouble.of(ahrs.getAngle());
	}

	@Override
	public OptionalDouble getYaw() {
		return OptionalDouble.of(ahrs.getYaw());
	}

	@Override
	public OptionalDouble getPitch() {
		return OptionalDouble.of(ahrs.getPitch());
	}

	@Override
	public OptionalDouble getRoll() {
		return OptionalDouble.of(ahrs.getRoll());
	}

	@Override
	public OptionalDouble getAccelX() {
		return OptionalDouble.of(ahrs.getWorldLinearAccelX());
	}

	@Override
	public OptionalDouble getAccelY() {
		return OptionalDouble.of(ahrs.getWorldLinearAccelY());
	}

	@Override
	public OptionalDouble getAccelZ() {
		return OptionalDouble.of(ahrs.getWorldLinearAccelZ());
	}

	@Override
	public OptionalDouble getVelX() {
		return OptionalDouble.of(ahrs.getVelocityX());
	}

	@Override
	public OptionalDouble getVelY() {
		return OptionalDouble.of(ahrs.getVelocityY());
	}

	@Override
	public OptionalDouble getVelZ() {
		return OptionalDouble.of(ahrs.getVelocityZ());
	}

	@Override
	public OptionalDouble getRawAccelX() {
		return OptionalDouble.of(ahrs.getRawAccelX());
	}

	@Override
	public OptionalDouble getRawAccelY() {
		return OptionalDouble.of(ahrs.getRawAccelY());
	}

	@Override
	public OptionalDouble getRawAccelZ() {
		return OptionalDouble.of(ahrs.getRawAccelZ());
	}

	@Override
	public OptionalDouble getRawGyroX() {
		return OptionalDouble.of(ahrs.getRawGyroX());
	}

	@Override
	public OptionalDouble getRawGyroY() {
		return OptionalDouble.of(ahrs.getRawGyroY());
	}

	@Override
	public OptionalDouble getRawGyroZ() {
		return OptionalDouble.of(ahrs.getRawGyroZ());
	}

	@Override
	public OptionalDouble getRawMagX() {
		return OptionalDouble.of(ahrs.getRawMagX());
	}

	@Override
	public OptionalDouble getRawMagY() {
		return OptionalDouble.of(ahrs.getRawMagY());
	}

	@Override
	public OptionalDouble getRawMagZ() {
		return OptionalDouble.of(ahrs.getRawMagZ());
	}

	@Override
	public OptionalDouble getBarometricPressure() {
		return OptionalDouble.of(ahrs.getBarometricPressure());
	}
}
