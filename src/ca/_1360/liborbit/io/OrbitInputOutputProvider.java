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

public interface OrbitInputOutputProvider {
    /**
     * @param id The joystick's port
     * @return A provider for the joystick on the given port
     */
    OrbitJoystickProvider getJoystick(int id);
    
    /**
     * Sets the power level of a motor
     * @param port The motor's PWM port
     * @param power The motor's new power level
     */
    void setMotorPower(int port, double power);
    
    /**
     * @param port The solenoid's PCM port
     * @param engaged The solenoid's new state
     */
    void setSolenoid(int port, boolean engaged);
    
    /**
     * @param port The output's DIO port
     * @param high The new state of the output
     */
    void setDigitalOut(int port, boolean high);
    
    /**
     * @param port The PDP port
     * @return The current draw from the given PDP port
     */
    double getCurrent(int port);
    
    /**
     * @param port The input's DIO port
     * @return The state of the input
     */
    boolean getDigitalIn(int port);
    
    /**
     * @param portA The encoder's first port
     * @param portB The encoder's second port
     * @return A provider for the encoder on the given ports
     */
    OrbitEncoderProvider getEncoder(int portA, int portB);
    
    /**
     * @return A provider for AHRS data
     */
    OrbitAhrsProvider getAhrs();
}
