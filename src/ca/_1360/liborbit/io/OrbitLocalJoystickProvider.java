package ca._1360.liborbit.io;

public final class OrbitLocalJoystickProvider implements OrbitJoystickProvider {
	private int id;
	
	public OrbitLocalJoystickProvider(int id) {
		this.id = id;
		setup();
	}
	
	static {
		System.loadLibrary("liborbit-local-joystick");
		initialize();
	}
	
    @Override
    public native String getName();

    @Override
    public native double getAxis(int i);

    @Override
    public native boolean getButton(int i);

    @Override
    public native int getPov(int i);

    @Override
    public native void setOutput(int i, boolean v);
    
    public static native int refresh();
    
    public static native void reorder(int from, int to);
    
    private native void setup();
    
    private static native void initialize();
}
