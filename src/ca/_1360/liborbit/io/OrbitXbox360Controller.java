package ca._1360.liborbit.io;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public class OrbitXbox360Controller extends OrbitJoystickBase {
	public OrbitXbox360Controller(int port) {
		super(port);
	}
	
    public OrbitPipelineOutputEndpoint getLeftXAxis() {
        return getAxis(0);
    }

   
    public OrbitPipelineOutputEndpoint getLeftYAxis() {
        return getAxis(1);
    }

    
    public OrbitPipelineOutputEndpoint getLeftTrigger() {
        return getAxis(2);
    }

    
    public OrbitPipelineOutputEndpoint getRightTrigger() {
        return getAxis(3);
    }

   
    public OrbitPipelineOutputEndpoint getRightXAxis() {
        return getAxis(4);
    }

    
    public OrbitPipelineOutputEndpoint getRightYAxis() {
        return getAxis(5);
    }

    public boolean getButtonA() {
        return getButton(1);
    }

    public boolean getButtonB() {
        return getButton(2);
    }

    public boolean getButtonX() {
        return getButton(3);
    }

    public boolean getButtonY() {
        return getButton(4);
    }

    public boolean getButtonLB() {
        return getButton(5);
    }

    public boolean getButtonRB() {
        return getButton(6);
    }

    public boolean getButtonBack() {
        return getButton(7);
    }

    public boolean getButtonStart() {
        return getButton(8);
    }

    public boolean getClickLeftStick() {
        return getButton(9);
    }

    public boolean getClickRightStick() {
        return getButton(10);
    }
    
    public int getPov() {
    	return getPov(0);
    }
    
    public void vibrateLeft(boolean on) {
    	setOutput(0, on);
    }
    
    public void vibrateRight(boolean on) {
    	setOutput(1, on);
    }
}
