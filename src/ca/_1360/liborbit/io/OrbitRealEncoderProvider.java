package ca._1360.liborbit.io;

import edu.wpi.first.wpilibj.Encoder;

public final class OrbitRealEncoderProvider extends Encoder implements OrbitEncoderProvider {
	public OrbitRealEncoderProvider(PortSpecification ports) {
		super(ports.getPortA(), ports.getPortB());
	}
	
	@Override
	public int getPosition() {
		return get();
	}
    
    public static final class PortSpecification {
    	private final int portA;
    	private final int portB;
    	
    	public PortSpecification(int portA, int portB) {
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
}