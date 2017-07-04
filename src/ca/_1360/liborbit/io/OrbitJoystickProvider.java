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

public interface OrbitJoystickProvider {
    /**
     * @return The name of the joystick
     */
    String getName();
    
    /**
     * @param i The axis number
     * @return The value of the given axis
     */
    double getAxis(int i);
    
    /**
     * @param i The button number
     * @return The state of the given button
     */
    boolean getButton(int i);
    
    /**
     * @param i The POV number
     * @return The angle of the given POV, in degrees clockwise from up, or -1 if not pressed
     */
    int getPov(int i);
    
    /**
     * Updates a joystick output
     * @param i The output number
     * @param v The new state of the output
     */
    void setOutput(int i, boolean v);
    
    /**
     * Updates a joystick rumble
     * @param i The rumble number
     * @param v The new power level of the rumble
     */
    void setRumble(int i, double v);
}
