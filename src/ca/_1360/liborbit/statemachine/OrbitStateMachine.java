/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitStateMachine.java
 * A basic finite state machine implementation
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
