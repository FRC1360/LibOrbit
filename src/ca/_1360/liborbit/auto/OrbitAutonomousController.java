package ca._1360.liborbit.auto;

import ca._1360.liborbit.statemachine.OrbitStateMachine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class OrbitAutonomousController<T> {
    private OrbitAutonomousMode<T>[] modes;
    private T[] subsystems;
    private ArrayList<OrbitAutonomousMode<T>> selection = new ArrayList<>();
    private List<SubsystemController> controllers;
    private ArrayList<Runnable> startNext;
    private int done;

    public OrbitAutonomousController(OrbitAutonomousMode<T>[] modes, T[] subsystems) {
        this.modes = modes;
        this.subsystems = subsystems;
    }

    public OrbitAutonomousMode<T>[] getModes() {
        return modes;
    }

    public ArrayList<OrbitAutonomousMode<T>> getSelection() {
        return selection;
    }

    public void start() {
        done = -1;
        startNext = new ArrayList<>();
        HashMap<T, ArrayDeque<OrbitAutonomousCommand<T>>> map = new HashMap<>();
        for (T subsystem : subsystems)
            map.put(subsystem, new ArrayDeque<>());
        for (OrbitAutonomousMode<T> mode : selection)
            mode.add(c -> map.get(c.getSubsystem()).add(c));
        controllers = map.values().stream().map(SubsystemController::new).collect(Collectors.toList());
        ArrayList<Runnable> oldNext = startNext;
        startNext = new ArrayList<>();
        done = 0;
        oldNext.forEach(Runnable::run);
    }

    public void stop() {
        for (SubsystemController controller : controllers)
            controller.setState(new FinishedCommand());
    }

    private final class EndCommand extends OrbitAutonomousCommand<T> {
        private EndCommand() {
            super(null);
        }

        @Override
        public void initialize() {
            ++done;
            if (done == controllers.size()) {
                ArrayList<Runnable> oldNext = startNext;
                startNext = new ArrayList<>();
                done = 0;
                oldNext.forEach(Runnable::run);
                gotoNext();
            } else {
                startNext.add(this::gotoNext);
            }
        }

        @Override
        public void deinitialize() { }
    }

    private final class FinishedCommand extends OrbitAutonomousCommand<T> {
        public FinishedCommand() {
            super(null);
        }

        @Override
        public void initialize() { }

        @Override
        public void deinitialize() { }
    }

    private final class SubsystemController extends OrbitStateMachine<OrbitAutonomousCommand<T>> {
        private ArrayDeque<OrbitAutonomousCommand<T>> queue;

        private SubsystemController(ArrayDeque<OrbitAutonomousCommand<T>> queue) {
            super(new EndCommand());
            getState().setGotoNextFunc(() -> setState(queue.remove()));
            for (OrbitAutonomousCommand<T> command : queue)
                command.setGotoNextFunc(() -> setState(queue.remove()));
            queue.remove();
            queue.add(new FinishedCommand());
            this.queue = queue;
        }
    }
}
