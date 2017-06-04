package ca._1360.liborbit.io;

import java.util.HashMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
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
	private final HashMap<OrbitRealEncoderProvider.PortSpecification, OrbitRealEncoderProvider> encoders = new HashMap<>();
	private final OrbitRealAhrsProvider ahrs;
	
	public OrbitRealInputOutputProvider(AHRS ahrs) {
		this.ahrs = new OrbitRealAhrsProvider(ahrs);
	}
	
    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return new OrbitDriverStationJoystickProvider(id);
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
        return encoders.computeIfAbsent(new OrbitRealEncoderProvider.PortSpecification(portA, portB), OrbitRealEncoderProvider::new);
    }

    @Override
    public OrbitAhrsProvider getAhrs() {
        return ahrs;
    }
}
