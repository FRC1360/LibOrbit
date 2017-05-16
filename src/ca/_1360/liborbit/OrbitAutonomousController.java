package ca._1360.liborbit;

import java.util.ArrayDeque;
import java.util.ArrayList;

public final class OrbitAutonomousController<T> {
    private OrbitAutonomousMode<T>[] modes;
    private ArrayList<OrbitAutonomousMode<T>> selection = new ArrayList<>();

    public OrbitAutonomousController(OrbitAutonomousMode<T>[] modes) {
        this.modes = modes;
    }

    public OrbitAutonomousMode<T>[] getModes() {
        return modes;
    }

    public ArrayList<OrbitAutonomousMode<T>> getSelection() {
        return selection;
    }

    public void start() {

    }

    public void stop() {

    }

    private class SubsystemController extends OrbitStateMachine<OrbitAutonomousCommand<T>> {
        ArrayDeque<OrbitAutonomousCommand<T>> queue;

        public SubsystemController(ArrayDeque<OrbitAutonomousCommand<T>> queue) {
            super(queue.remove());
            this.queue = queue;
        }
    }
}
