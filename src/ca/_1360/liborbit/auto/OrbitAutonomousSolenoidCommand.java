package ca._1360.liborbit.auto;

import ca._1360.liborbit.io.OrbitSolenoid;
import ca._1360.liborbit.io.OrbitSolenoidStates;

public final class OrbitAutonomousSolenoidCommand<T> extends OrbitAutonomousStateMachineUpdateCommand<T, OrbitSolenoidStates> {
    public OrbitAutonomousSolenoidCommand(T subsystem, OrbitSolenoid solenoid, boolean engaged) {
        super(subsystem, solenoid, OrbitSolenoidStates.getState(engaged));
    }
}
