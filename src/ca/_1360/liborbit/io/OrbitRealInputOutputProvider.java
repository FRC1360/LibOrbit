package ca._1360.liborbit.io;

import java.util.HashMap;
import java.util.OptionalDouble;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public final class OrbitRealInputOutputProvider implements OrbitInputOutputProvider {
	private final PowerDistributionPanel pdp = new PowerDistributionPanel();
	private final HashMap<Integer, Victor> motors = new HashMap<>();
	private final HashMap<Integer, Solenoid> solenoids = new HashMap<>();
	private final HashMap<Integer, DigitalOutput> digitalOutputs = new HashMap<>();
	private final HashMap<Integer, DigitalInput> digitalInputs = new HashMap<>();
	private final HashMap<EncoderPortSpecification, EncoderProvider> encoders = new HashMap<>();
	private final AhrsProvider ahrs;
	
	public OrbitRealInputOutputProvider(AHRS ahrs) {
		this.ahrs = new AhrsProvider(ahrs);
	}
	
    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return new JoystickProvider(id);
    }

    @Override
    public void setMotorPower(int port, double power) {
    	motors.computeIfAbsent(port, Victor::new).set(power);
    }

    @Override
    public void setSolenoid(int port, boolean engaged) {
    	solenoids.computeIfAbsent(port, Solenoid::new).set(engaged);
    }

    @Override
    public void setDigitalOut(int port, boolean high) {
    	digitalOutputs.computeIfAbsent(port, DigitalOutput::new).set(high);
    }

    @Override
    public double getCurrent(int port) {
        return pdp.getCurrent(port);
    }

    @Override
    public boolean getDigitalIn(int port) {
        return digitalInputs.computeIfAbsent(port, DigitalInput::new).get();
    }

    @Override
    public OrbitEncoderProvider getEncoder(int portA, int portB) {
        return encoders.computeIfAbsent(new EncoderPortSpecification(portA, portB), EncoderProvider::new);
    }

    @Override
    public OrbitAhrsProvider getAhrs() {
        return ahrs;
    }
    
    private final class JoystickProvider extends Joystick implements OrbitJoystickProvider {
		public JoystickProvider(int port) {
			super(port);
		}

		@Override
		public double getAxis(int i) {
			return getRawAxis(i);
		}

		@Override
		public boolean getButton(int i) {
			return getRawButton(i);
		}

		@Override
		public int getPov(int i) {
			return getPOV(i);
		}

		@Override
		public void setRumble(int i, double v) {
			setRumble(RumbleType.values()[i], v);
		}
    }
    
    private final class EncoderPortSpecification {
    	private final int portA;
    	private final int portB;
    	
    	public EncoderPortSpecification(int portA, int portB) {
    		this.portA = portA;
    		this.portB = portB;
    	}
    	
		public int getPortA() {
			return portA;
		}
		
		public int getPortB() {
			return portB;
		}
		
		@Override
		public int hashCode() {
			return (portA << 4) + portB;
		}
    }
    
    private final class EncoderProvider extends Encoder implements OrbitEncoderProvider {
    	public EncoderProvider(EncoderPortSpecification ports) {
    		super(ports.getPortA(), ports.getPortB());
		}
    	
		@Override
		public int getPosition() {
			// TODO Auto-generated method stub
			return 0;
		}
    }
    
    private final class AhrsProvider implements OrbitAhrsProvider {
    	private AHRS ahrs;
    	
		public AhrsProvider(AHRS ahrs) {
			this.ahrs = ahrs;
		}

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
}
