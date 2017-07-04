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

package ca._1360.liborbit.auto;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

/**
 * @param <T> The subsystem type; usually an enum
 */
public final class OrbitAutonomousWaitCommand<T> extends OrbitAutonomousCommand<T> {
    private final T targetSubsystem;
    private final OrbitAutonomousController<T> controller;
    private Thread thread;

    /**
     * @param subsystem The subsystem that the command runs on
     * @param timeout The maximum duration of the command, in milliseconds
     * @param targetSubsystem The subsystem to wait on
     * @param controller The autonomous controller
     */
    public OrbitAutonomousWaitCommand(T subsystem, long timeout, T targetSubsystem, OrbitAutonomousController<T> controller) {
        super(subsystem, timeout);
        this.targetSubsystem = targetSubsystem;
        this.controller = controller;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#initializeCore()
     */
    @Override
    protected void initializeCore() {
    	// Start a thread that waits for the command to exit
        thread = new Thread(OrbitFunctionUtilities.combine(OrbitFunctionUtilities.handleException(OrbitFunctionUtilities.specializeEx(controller::waitFor, targetSubsystem), e -> {}), this::gotoNext));
        thread.start();
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#deinitializeCore()
     */
    @Override
    protected void deinitializeCore() {
        thread.interrupt();
    }
}
