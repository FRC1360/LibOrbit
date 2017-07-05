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

import java.util.HashMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public final class OrbitRealInputOutputProvider implements OrbitInputOutputProvider {
	private final PowerDistributionPanel pdp = new PowerDistributionPanel();
	// Maps are used to prevent duplication of resources
	private final HashMap<Integer, Victor> motors = new HashMap<>();
	private final HashMap<Integer, Solenoid> solenoids = new HashMap<>();
	private final HashMap<Integer, DigitalOutput> digitalOutputs = new HashMap<>();
	private final HashMap<Integer, DigitalInput> digitalInputs = new HashMap<>();
	private final HashMap<OrbitRealEncoderProvider.PortSpecification, OrbitRealEncoderProvider> encoders = new HashMap<>();
	private final OrbitRealAhrsProvider ahrs;
	
	/**
	 * @param ahrs The raw AHRS object
	 */
	public OrbitRealInputOutputProvider(AHRS ahrs) {
		this.ahrs = new OrbitRealAhrsProvider(ahrs);
	}
	
    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getJoystick(int)
     */
    @Override
    public OrbitJoystickProvider getJoystick(int id) {
        return new OrbitDriverStationJoystickProvider(id);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setMotorPower(int, double)
     */
    @Override
    public void setMotorPower(int port, double power) {
    	// All motors are assumed to be using VEX Victor motor controllers
    	motors.computeIfAbsent(port, Victor::new).set(power);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setSolenoid(int, boolean)
     */
    @Override
    public void setSolenoid(int port, boolean engaged) {
    	solenoids.computeIfAbsent(port, Solenoid::new).set(engaged);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#setDigitalOut(int, boolean)
     */
    @Override
    public void setDigitalOut(int port, boolean high) {
    	digitalOutputs.computeIfAbsent(port, DigitalOutput::new).set(high);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getCurrent(int)
     */
    @Override
    public double getCurrent(int port) {
        return pdp.getCurrent(port);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getDigitalIn(int)
     */
    @Override
    public boolean getDigitalIn(int port) {
        return digitalInputs.computeIfAbsent(port, DigitalInput::new).get();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getEncoder(int, int)
     */
    @Override
    public OrbitEncoderProvider getEncoder(int portA, int portB) {
        return encoders.computeIfAbsent(new OrbitRealEncoderProvider.PortSpecification(portA, portB), OrbitRealEncoderProvider::new);
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitInputOutputProvider#getAhrs()
     */
    @Override
    public OrbitAhrsProvider getAhrs() {
        return ahrs;
    }
}
