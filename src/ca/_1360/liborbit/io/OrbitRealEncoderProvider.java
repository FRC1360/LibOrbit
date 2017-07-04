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

import edu.wpi.first.wpilibj.Encoder;

public final class OrbitRealEncoderProvider extends Encoder implements OrbitEncoderProvider {
	/**
	 * @param ports The encoder's port specification
	 */
	public OrbitRealEncoderProvider(PortSpecification ports) {
		super(ports.getPortA(), ports.getPortB());
	}
	
	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitEncoderProvider#getPosition()
	 */
	@Override
	public int getPosition() {
		return get();
	}
    
    /**
     * A port specification consisting of two DIO ports
     */
    public static final class PortSpecification {
    	private final int portA;
    	private final int portB;
    	
    	/**
    	 * @param portA The encoder's first DIO port
    	 * @param portB The encoder's second DIO port
    	 */
    	public PortSpecification(int portA, int portB) {
    		this.portA = portA;
    		this.portB = portB;
    	}
    	
		/**
		 * @return The encoder's first DIO port
		 */
		public int getPortA() {
			return portA;
		}
		
		/**
		 * @return THe encoder's second DIO port
		 */
		public int getPortB() {
			return portB;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			// DIO ports are 0-9, so they fit in 4 bits for hashing 
			return (portA << 4) + portB;
		}
    }
}