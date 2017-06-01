package ca._1360.liborbit.io;

import java.util.OptionalDouble;

public interface OrbitAhrsProvider {
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
