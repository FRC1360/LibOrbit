package ca._1360.liborbit.auto;

import ca._1360.liborbit.statemachine.OrbitStateMachine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class OrbitAutonomousController<T> {
    private OrbitAutonomousMode<T>[] modes;
    private T[] subsystems;
    private ArrayList<OrbitAutonomousMode<T>> selection = new ArrayList<>();
    private Map<T, SubsystemController> controllers;
    private ArrayList<Runnable> startNext;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
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
        controllers = new HashMap<>(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ((Function<Map.Entry<T, ArrayDeque<OrbitAutonomousCommand<T>>>, ArrayDeque<OrbitAutonomousCommand<T>>>) Map.Entry::getValue).andThen(SubsystemController::new))));
        ArrayList<Runnable> oldNext = startNext;
        startNext = new ArrayList<>();
        done = 0;
        oldNext.forEach(Runnable::run);
    }

    public void stop() {
        for (SubsystemController controller : controllers.values())
            controller.setState(new FinishedCommand());
    }

    public void waitFor(T subsystem) throws InterruptedException {
        controllers.get(subsystem).getState().join();
    }

    private final class EndCommand extends OrbitAutonomousCommand<T> {
        private EndCommand() {
            super(null, 0);
        }

        @Override
        protected void initializeCore() {
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
        protected void deinitializeCore() { }
    }

    private final class FinishedCommand extends OrbitAutonomousCommand<T> {
        public FinishedCommand() {
            super(null, 0);
        }

        @Override
        protected void initializeCore() { }

        @Override
        protected void deinitializeCore() { }
    }

    private final class SubsystemController extends OrbitStateMachine<OrbitAutonomousCommand<T>> {
        private ArrayDeque<OrbitAutonomousCommand<T>> queue;
        private ScheduledFuture<?> future;

        private SubsystemController(ArrayDeque<OrbitAutonomousCommand<T>> queue) {
            super(new FinishedCommand());
            getState().setGotoNextFunc(this::next);
            for (OrbitAutonomousCommand<T> command : queue)
                command.setGotoNextFunc(this::next);
            queue.add(new FinishedCommand());
            this.queue = queue;
            next();
        }

        private synchronized void next() {
            if (future != null)
                future.cancel(true);
            OrbitAutonomousCommand<T> command = queue.remove();
            setState(command);
            if (command.getTimeout() != 0)
                future = executor.schedule(this::next, command.getTimeout(), TimeUnit.MILLISECONDS);
        }
    }
}
