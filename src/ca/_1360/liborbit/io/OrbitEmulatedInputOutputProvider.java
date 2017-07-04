/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
 */

package ca._1360.liborbit.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;

public final class OrbitEmulatedInputOutputProvider implements OrbitInputOutputProvider {
    private final EmulatedMotor[] motors = new EmulatedMotor[10];
    private final boolean[] solenoids = new boolean[8];
    private final boolean[] dio = new boolean[10];
    private final ArrayList<EmulatedEncoder> encoders = new ArrayList<>();
    private final OrbitAhrsProvider ahrsProvider;
    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    private Future<?> future;

    /**
     * @param ahrsProvider A provider for emulated AHRS functionality
     */
    public OrbitEmulatedInputOutputProvider(OrbitAhrsProvider ahrsProvider) {
        this.ahrsProvider = ahrsProvider;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getJoystick(int)
     */
    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return new OrbitLocalJoystickProvider(id);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setMotorPower(int, double)
     */
    @Override
    public void setMotorPower(int port, double power) {
        motors[port].setPowerLevel(power);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setSolenoid(int, boolean)
     */
    @Override
    public void setSolenoid(int port, boolean engaged) {
        solenoids[port] = engaged;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setDigitalOut(int, boolean)
     */
    @Override
    public void setDigitalOut(int port, boolean high) {
        dio[port] = high;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getCurrent(int)
     */
    @Override
    public double getCurrent(int port) {
    	// Motors are stored by PWM port, but current is referenced by PDP port
        return Arrays.stream(motors).filter(motor -> motor.getPdpPort() == port).mapToDouble(EmulatedMotor::calculateCurrent).findAny().orElse(0.0);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getDigitalIn(int)
     */
    @Override
    public boolean getDigitalIn(int port) {
        return dio[port];
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getEncoder(int, int)
     */
    @Override
    public synchronized OrbitEncoderProvider getEncoder(int portA, int portB) {
    	// Encoders are referenced by a pair of DIO ports
        for (EmulatedEncoder encoder : encoders)
            if (encoder.getPortA() == portA && encoder.getPortB() == portB)
                return encoder;
        return null;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getAhrs()
     */
    @Override
    public OrbitAhrsProvider getAhrs() {
        return ahrsProvider;
    }

    /**
     * @param port The PWM port of the motor
     * @return The emulated motor on the given PWM port
     */
    public EmulatedMotor getMotor(int port) {
        return motors[port];
    }

    /**
     * @param port The PCM port of the solenoid
     * @return The state of the given PCM port
     */
    public boolean getSolenoid(int port) {
        return solenoids[port];
    }

    /**
     * @param port The DIO port of the output
     * @return The state of the given DIO port
     */
    public boolean getDio(int port) {
        return dio[port];
    }

    /**
     * @param port The DIO port of the input
     * @param value The new state to assign to the given DIO port
     */
    public void setDio(int port, boolean value) {
        dio[port] = value;
    }

    /**
     * @return A list of the emulated encoders
     */
    public List<EmulatedEncoder> getEncoders() {
        return new ArrayList<>(encoders);
    }

    /**
     * Creates an emulated motor with the given configuration supply
     * @param pwmPort The motor's PWM port
     * @param pdpPort The motor's PDP port
     * @param inertia A supplier for the moment of rotational inertia of the load, in kilogram metres squared
     * @param friction A supplier for the friction that the motor experiences, in newton metres
     */
    public void mapMotor(int pwmPort, int pdpPort, DoubleSupplier inertia, DoubleSupplier friction) {
        motors[pwmPort] = new EmulatedMotor(pdpPort, inertia, friction);
    }

    /**
     * Creates an emulated motor with the given configuration
     * @param pwmPort The motor's PWM port
     * @param pdpPort The motor's PDP port
     * @param inertia The moment of rotational inertia of the load, in kilogram metres squared
     * @param friction The friction that the motor experiences, in newton metres
     */
    public void mapMotor(int pwmPort, int pdpPort, double inertia, double friction) {
        motors[pwmPort] = new EmulatedMotor(pdpPort, () -> inertia, () -> friction);
    }

    /**
     * Creates an emulated encoder with the given configuration
     * @param portA The encoder's first DIO port
     * @param portB The encoder's second DIO port
     * @param pwmPort The PWM port of the motor that the encoder is connected to
     * @param ratio A supplier for the ratio of encoder ticks to motor shaft radians
     */
    public void mapEncoder(int portA, int portB, int pwmPort, DoubleSupplier ratio) {
        encoders.add(new EmulatedEncoder(portA, portB, motors[pwmPort], ratio));
    }

    /**
     * Creates an emulated encoder with the given configuration
     * @param portA The encoder's first DIO port
     * @param portB The encoder's second DIO port
     * @param pwmPort The PWM port of the motor that the encoder is connected to
     * @param ratio The ratio of encoder ticks to motor shaft radians
     */
    public void mapEncoder(int portA, int portB, int pwmPort, double ratio) {
        encoders.add(new EmulatedEncoder(portA, portB, motors[pwmPort], () -> ratio));
    }

    /**
     * Starts the emulator
     */
    public synchronized void start() {
        if (future == null)
            future = scheduler.scheduleAtFixedRate(this::update, 0, 100, TimeUnit.NANOSECONDS);
    }

    /**
     * Stops the emulator
     */
    public synchronized void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }

    /**
     * Updates all motors and encoders
     */
    public synchronized void update() {
        Arrays.stream(motors).filter(Objects::nonNull).forEach(EmulatedMotor::update);
        encoders.forEach(EmulatedEncoder::update);
    }

    /**
     * An emulated motor
     */
    public static final class EmulatedMotor {
        private static final double MOTOR_CONSTANT = 0.05;
        private static final double RESISTANCE = 0.4;
        private static final double BASE_VOLTAGE = 12.0;

        private final int pdpPort;
        private final DoubleSupplier inertia;
        private final DoubleSupplier friction;
        private double currentInertia;
        private double powerLevel = 0.0;
        private double angularVelocity = 0.0;
        private double torque = 0.0;

        /**
     * @param pdpPort The motor's PDP port
     * @param inertia A supplier for the moment of rotational inertia of the load, in kilogram metres squared
     * @param friction A supplier for the friction that the motor experiences, in newton metres
         */
        public EmulatedMotor(int pdpPort, DoubleSupplier inertia, DoubleSupplier friction) {
            this.pdpPort = pdpPort;
            this.inertia = inertia;
            this.friction = friction;
            currentInertia = inertia.getAsDouble();
        }

        /**
         * @return The motor's PDP port
         */
        public int getPdpPort() {
            return pdpPort;
        }

        /**
         * @return The motor's current power level
         */
        public double getPowerLevel() {
            return powerLevel;
        }

        /**
         * @return The motor's angular velocity, in radians per second
         */
        public double getAngularVelocity() {
            return angularVelocity;
        }

        /**
         * @param powerLevel The motor's new power level
         */
        public void setPowerLevel(double powerLevel) {
            this.powerLevel = powerLevel;
        }

        /**
         * @return The motor's approximate current draw
         */
        public double calculateCurrent() {
            double current = torque / MOTOR_CONSTANT;
            // Current draw must be absolute, but cannot oppose voltage
            return Math.signum(current) == Math.signum(powerLevel) ? Math.abs(current) : 0.0;
        }

        /**
         * Updates the motor's state
         */
        public void update() {
            double newInertia = inertia.getAsDouble();
            // Comply with the conservation of momentum
            if (newInertia != currentInertia) {
                angularVelocity *= currentInertia / newInertia;
                currentInertia = newInertia;
            }
            // Calculate torque, angular acceleration, and new angular velocity
            torque = (BASE_VOLTAGE * powerLevel - angularVelocity * MOTOR_CONSTANT) * MOTOR_CONSTANT / RESISTANCE;
            double accel = (torque - Math.copySign(friction.getAsDouble(), angularVelocity)) / inertia.getAsDouble();
            double newVel = angularVelocity + accel * 0.0000001;
            // Update angular velocity as applicable
            if (Double.isNaN(newVel))
            	return;
            if (powerLevel != 0.0 || Math.signum(newVel) == Math.signum(angularVelocity))
                angularVelocity = newVel;
            else
                angularVelocity = 0.0;
        }
    }

    /**
     * An emulated encoder
     */
    public static final class EmulatedEncoder implements OrbitEncoderProvider {
        private final int portA;
        private final int portB;
        private final EmulatedMotor motor;
        private final DoubleSupplier ratio;
        private double position = 0.0;

        /**
     * @param portA The encoder's first DIO port
     * @param portB The encoder's second DIO port
     * @param pwmPort The PWM port of the motor that the encoder is connected to
     * @param ratio A supplier for the ratio of encoder ticks to motor shaft radians
         */
        public EmulatedEncoder(int portA, int portB, EmulatedMotor motor, DoubleSupplier ratio) {
            this.portA = portA;
            this.portB = portB;
            this.motor = motor;
            this.ratio = ratio;
        }

        /**
         * @return The encoder's first DIO port
         */
        public int getPortA() {
            return portA;
        }

        /**
         * @return The encoder's second DIO port
         */
        public int getPortB() {
            return portB;
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.io.OrbitEncoderProvider#getPosition()
         */
        @Override
        public int getPosition() {
            return (int) (position * ratio.getAsDouble());
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.io.OrbitEncoderProvider#getRate()
         */
        @Override
        public double getRate() {
            return motor.getAngularVelocity() * ratio.getAsDouble();
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.io.OrbitEncoderProvider#reset()
         */
        @Override
        public void reset() {
            position = 0.0;
        }

        /**
         * Updates the encoder's position
         */
        public void update() {
            position += motor.getAngularVelocity() * 0.0000001;
        }
    }
}
