package ca._1360.liborbit.statemachine;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class OrbitStateMachine<T extends OrbitStateMachineStates> {
    private T state;
    private final ArrayList<BiConsumer<T, T>> handlers = new ArrayList<>();

    public OrbitStateMachine(T state) {
        this.state = state;
        state.initialize();
    }

    public final synchronized T getState() {
        return state;
    }

    public final synchronized void setState(T state) {
        T old = this.state;
        if (state != old && setStateCore(old, state)) {
            this.state.deinitialize();
            this.state = state;
            this.state.initialize();
            for (BiConsumer<T, T> handler : handlers)
                handler.accept(old, this.state);
        }
    }
    
    protected boolean setStateCore(T oldState, T newState) {
        return true;
    } 

    public final void addStateChangeHandler(BiConsumer<T, T> handler) {
        handlers.add(handler);
    }

    public final void removeStateChangeHandler(BiConsumer<T, T> handler) {
        handlers.remove(handler);
    }
}
