package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public final class OrbitAhrs {
    private final OrbitAhrsProvider provider = OrbitInputOutputManager.getProvider().getAhrs();
    private final OrbitPipelineOutputEndpoint continuousAngleEndpoint = provider::getContinuousAngle;
    private final OrbitPipelineOutputEndpoint yawEndpoint = provider::getYaw;
    private final OrbitPipelineOutputEndpoint pitchEndpoint = provider::getPitch;
    private final OrbitPipelineOutputEndpoint rollEndpoint = provider::getRoll;
    private final OrbitPipelineOutputEndpoint accelXEndpoint = provider::getAccelX;
    private final OrbitPipelineOutputEndpoint accelYEndpoint = provider::getAccelY;
    private final OrbitPipelineOutputEndpoint accelZEndpoint = provider::getAccelZ;
    private final OrbitPipelineOutputEndpoint velXEndpoint = provider::getVelX;
    private final OrbitPipelineOutputEndpoint velYEndpoint = provider::getVelY;
    private final OrbitPipelineOutputEndpoint velZEndpoint = provider::getVelZ;
    private final OrbitPipelineOutputEndpoint posXEndpoint = provider::getPosX;
    private final OrbitPipelineOutputEndpoint posYEndpoint = provider::getPosY;
    private final OrbitPipelineOutputEndpoint posZEndpoint = provider::getPosZ;
    private final OrbitPipelineOutputEndpoint rawAccelXEndpoint = provider::getRawAccelX;
    private final OrbitPipelineOutputEndpoint rawAccelYEndpoint = provider::getRawAccelY;
    private final OrbitPipelineOutputEndpoint rawAccelZEndpoint = provider::getRawAccelZ;
    private final OrbitPipelineOutputEndpoint rawGyroXEndpoint = provider::getRawGyroX;
    private final OrbitPipelineOutputEndpoint rawGyroYEndpoint = provider::getRawGyroY;
    private final OrbitPipelineOutputEndpoint rawGyroZEndpoint = provider::getRawGyroZ;
    private final OrbitPipelineOutputEndpoint rawMagXEndpoint = provider::getRawMagX;
    private final OrbitPipelineOutputEndpoint rawMagYEndpoint = provider::getRawMagY;
    private final OrbitPipelineOutputEndpoint rawMagZEndpoint = provider::getRawMagZ;
    private final OrbitPipelineOutputEndpoint barometricPressureEndpoint = provider::getBarometricPressure;
    
    public OrbitAhrs() { }

    public OrbitPipelineOutputEndpoint getContinuousAngle() {
        return continuousAngleEndpoint;
    }

    public OrbitPipelineOutputEndpoint getYaw() {
        return yawEndpoint;
    }

    public OrbitPipelineOutputEndpoint getPitch() {
        return pitchEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRoll() {
        return rollEndpoint;
    }

    public OrbitPipelineOutputEndpoint getAccelX() {
        return accelXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getAccelY() {
        return accelYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getAccelZ() {
        return accelZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getVelX() {
        return velXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getVelY() {
        return velYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getVelZ() {
        return velZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getPosX() {
        return posXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getPosY() {
        return posYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getPosZ() {
        return posZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawAccelX() {
        return rawAccelXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawAccelY() {
        return rawAccelYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawAccelZ() {
        return rawAccelZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawGyroX() {
        return rawGyroXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawGyroY() {
        return rawGyroYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawGyroZ() {
        return rawGyroZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawMagX() {
        return rawMagXEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawMagY() {
        return rawMagYEndpoint;
    }

    public OrbitPipelineOutputEndpoint getRawMagZ() {
        return rawMagZEndpoint;
    }

    public OrbitPipelineOutputEndpoint getBarometricPressure() {
        return barometricPressureEndpoint;
    }
}
