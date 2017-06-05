/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitRealEncoderProvider.java
 * Provides access to quadrature encoders on a real robot
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