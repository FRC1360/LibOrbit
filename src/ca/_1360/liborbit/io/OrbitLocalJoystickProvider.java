/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitLocalJoystickProvider.java
 * Provides access to joysticks connected to the host of an emulated robot; implemented in native code
 */

package ca._1360.liborbit.io;

public final class OrbitLocalJoystickProvider implements OrbitJoystickProvider {
	private int id;
	
	/**
	 * @param id The index of the joystick
	 */
	public OrbitLocalJoystickProvider(int id) {
		this.id = id;
		setup();
	}
	
	static {
		// Load and initialize native library
		System.loadLibrary("liborbit-local-joystick");
		initialize();
	}
	
    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitJoystickProvider#getName()
     */
    @Override
    public native String getName();

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitJoystickProvider#getAxis(int)
     */
    @Override
    public native double getAxis(int i);

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitJoystickProvider#getButton(int)
     */
    @Override
    public native boolean getButton(int i);

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitJoystickProvider#getPov(int)
     */
    @Override
    public native int getPov(int i);

    /* (non-Javadoc)
     * @see ca._1360.liborbit.io.OrbitJoystickProvider#setOutput(int, boolean)
     */
    @Override
    public native void setOutput(int i, boolean v);

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.io.OrbitJoystickProvider#setRumble(int, double)
	 */
	@Override
	public native void setRumble(int i, double v);
    
    /**
     * @return An array of valid indices, with boolean values indicating if a joystick is connected to each index
     */
    public static native boolean[] refresh();
    
    /**
     * Moves a joystick from one index to another, shifting the joysticks in between
     * @param from The initial index of the joystick
     * @param to The new index of the joystick
     */
    public static native void reorder(int from, int to);
    
    /**
     * Sets up the current joystick
     */
    private native void setup();
    
    /**
     * Initializes the native library
     */
    private static native void initialize();
}
