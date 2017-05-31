package ca._1360.liborbit.io;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;

public final class OrbitEmulatedInputOutputProvider implements OrbitInputOutputProvider {
    private final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    private final EmulatedMotor[] motors = new EmulatedMotor[10];
    private final boolean[] solenoids = new boolean[8];
    private final boolean[] dio = new boolean[10];
    private final ArrayList<EmulatedEncoder> encoders = new ArrayList<>();
    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    private Future<?> future;

    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return new OrbitLocalJoystickProvider(controllers[id]);
    }

    @Override
    public void setMotorPower(int port, double power) {
        motors[port].setPowerLevel(power);
    }

    @Override
    public void setSolenoid(int port, boolean engaged) {
        solenoids[port] = engaged;
    }

    @Override
    public void setDigitalOut(int port, boolean high) {
        dio[port] = high;
    }

    @Override
    public double getCurrent(int port) {
        return Arrays.stream(motors).filter(motor -> motor.getPdpPort() == port).mapToDouble(EmulatedMotor::calculateCurrent).findAny().orElse(0.0);
    }

    @Override
    public boolean getDigitalIn(int port) {
        return dio[port];
    }

    @Override
    public synchronized OrbitEncoderProvider getEncoder(int portA, int portB) {
        for (EmulatedEncoder encoder : encoders)
            if (encoder.getPortA() == portA && encoder.getPortB() == portB)
                return encoder;
        return null;
    }

    public EmulatedMotor getMotor(int port) {
        return motors[port];
    }

    public boolean getSolenoid(int port) {
        return solenoids[port];
    }

    public boolean getDio(int port) {
        return dio[port];
    }

    public void setDio(int port, boolean value) {
        dio[port] = value;
    }

    public List<EmulatedEncoder> getEncoders() {
        return new ArrayList<>(encoders);
    }

    public void mapMotor(int pwmPort, int pdpPort, DoubleSupplier inertia, DoubleSupplier friction) {
        motors[pwmPort] = new EmulatedMotor(pdpPort, inertia, friction);
    }

    public void mapMotor(int pwmPort, int pdpPort, double inertia, double friction) {
        motors[pwmPort] = new EmulatedMotor(pdpPort, () -> inertia, () -> friction);
    }

    public void mapEncoder(int portA, int portB, int pwmPort, DoubleSupplier ratio) {
        encoders.add(new EmulatedEncoder(portA, portB, motors[pwmPort], ratio));
    }

    public void mapEncoder(int portA, int portB, int pwmPort, double ratio) {
        encoders.add(new EmulatedEncoder(portA, portB, motors[pwmPort], () -> ratio));
    }

    public synchronized void start() {
        if (future == null)
            future = scheduler.scheduleAtFixedRate(this::update, 0, 100, TimeUnit.NANOSECONDS);
    }

    public synchronized void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }

    public synchronized void update() {
        Arrays.stream(motors).filter(Objects::nonNull).forEach(EmulatedMotor::update);
        encoders.forEach(EmulatedEncoder::update);
    }

    public static final class EmulatedMotor {
        private static final double MOTOR_CONSTANT = 0.05;
        private static final double RESISTANCE = 0.5;
        private static final double BASE_VOLTAGE = 12.0;

        private final int pdpPort;
        private final DoubleSupplier inertia;
        private final DoubleSupplier friction;
        private double powerLevel = 0.0;
        private double angularVelocity = 0.0;
        private double torque = 0.0;

        public EmulatedMotor(int pdpPort, DoubleSupplier inertia, DoubleSupplier friction) {
            this.pdpPort = pdpPort;
            this.inertia = inertia;
            this.friction = friction;
        }

        public int getPdpPort() {
            return pdpPort;
        }

        public double getPowerLevel() {
            return powerLevel;
        }

        public double getAngularVelocity() {
            return angularVelocity;
        }

        public void setPowerLevel(double powerLevel) {
            this.powerLevel = powerLevel;
        }

        public double calculateCurrent() {
            double current = torque / MOTOR_CONSTANT;
            return Math.signum(current) == Math.signum(powerLevel) ? Math.abs(current) : 0.0;
        }

        public void update() {
            torque = (BASE_VOLTAGE * powerLevel - angularVelocity * MOTOR_CONSTANT) * MOTOR_CONSTANT / RESISTANCE;
            double accel = (torque - Math.copySign(friction.getAsDouble(), angularVelocity)) / inertia.getAsDouble();
            double newVel = angularVelocity + accel * 0.0001;
            if (powerLevel != 0.0 || Math.signum(newVel) == Math.signum(angularVelocity))
                angularVelocity = newVel;
            else
                angularVelocity = 0.0;
        }
    }

    public static final class EmulatedEncoder implements OrbitEncoderProvider {
        private final int portA;
        private final int portB;
        private final EmulatedMotor motor;
        private final DoubleSupplier ratio;
        private double position = 0.0;

        public EmulatedEncoder(int portA, int portB, EmulatedMotor motor, DoubleSupplier ratio) {
            this.portA = portA;
            this.portB = portB;
            this.motor = motor;
            this.ratio = ratio;
        }

        public int getPortA() {
            return portA;
        }

        public int getPortB() {
            return portB;
        }

        @Override
        public int getPosition() {
            return (int) (position * ratio.getAsDouble());
        }

        @Override
        public double getRate() {
            return motor.getAngularVelocity() * ratio.getAsDouble();
        }

        @Override
        public void reset() {
            position = 0.0;
        }

        public void update() {
            position += motor.getAngularVelocity() * 0.01;
        }
    }
}
