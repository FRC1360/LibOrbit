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

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public class OrbitXbox360Controller extends OrbitJoystickBase {
	/**
	 * @param port The controller's port
	 */
	public OrbitXbox360Controller(int port) {
		super(port);
	}
	
	// Accessor methods for property values and pipeline endpoints
	
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
    
    public OrbitPipelineInputEndpoint getLeftRumble() {
    	return getRumble(0);
    }
    
    public OrbitPipelineInputEndpoint getRightRumble() {
    	return getRumble(1);
    }
}
