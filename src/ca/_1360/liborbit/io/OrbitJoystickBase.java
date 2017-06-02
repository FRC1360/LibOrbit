package ca._1360.liborbit.io;

import java.util.HashMap;
import java.util.OptionalDouble;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

public abstract class OrbitJoystickBase {
    private final OrbitJoystickProvider provider;
    private final HashMap<Integer, OrbitPipelineOutputEndpoint> axes = new HashMap<>();

    public OrbitJoystickBase(int id) {
        provider = OrbitInputOutputManager.getProvider().getJoystick(id);
    }
    
    public final String getName() {
    	return provider.getName();
    }
    
    protected final OrbitPipelineOutputEndpoint getAxis(int i) {
    	return axes.computeIfAbsent(i, _i -> () -> OptionalDouble.of(provider.getAxis(i)));
    }
    
    protected final boolean getButton(int i) {
    	return provider.getButton(i);
    }
    
    protected final int getPov(int i) {
    	return provider.getPov(i);
    }
    
    protected final void setOutput(int i, boolean v) {
    	provider.setOutput(i, v);
    }
}
