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

package ca._1360.liborbit.statemachine;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class OrbitStateMachine<T extends OrbitStateMachineStates> {
    private T state;
    private final ArrayList<BiConsumer<T, T>> handlers = new ArrayList<>();

    /**
     * @param state The initial state
     */
    public OrbitStateMachine(T state) {
        this.state = state;
        state.initialize();
    }

    /**
     * @return The current state
     */
    public final synchronized T getState() {
        return state;
    }

    /**
     * @param state The new state
     */
    public final synchronized void setState(T state) {
        T old = this.state;
        // Ensure that the subclass, if any, approves the new state
        if (state != old && setStateCore(old, state)) {
            this.state.deinitialize();
            this.state = state;
            this.state.initialize();
            // Call state change handlers
            for (BiConsumer<T, T> handler : handlers)
                handler.accept(old, this.state);
        }
    }
    
    /**
     * To be run to approve state change
     * @param oldState The current state
     * @param newState The new state
     * @return True if the state change can occur
     */
    protected boolean setStateCore(T oldState, T newState) {
        return true;
    } 

    /**
     * @param handler The state change handler to add
     */
    public final void addStateChangeHandler(BiConsumer<T, T> handler) {
        handlers.add(handler);
    }

    /**
     * @param handler The state change handler to remove
     */
    public final void removeStateChangeHandler(BiConsumer<T, T> handler) {
        handlers.remove(handler);
    }
}
