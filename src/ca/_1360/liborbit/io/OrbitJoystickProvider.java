/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitEmulatedInputOutputProvider.java
 * Base interface for providers of joystick functionality
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
