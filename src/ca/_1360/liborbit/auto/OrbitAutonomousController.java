package ca._1360.liborbit.auto;

import ca._1360.liborbit.statemachine.OrbitStateMachine;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class OrbitAutonomousController<T> {
    private final OrbitAutonomousMode<T>[] modes;
    private final T[] subsystems;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
    private final ArrayList<OrbitAutonomousMode<T>> selection = new ArrayList<>();
    private Map<T, SubsystemController> controllers;
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
        Map<T, ArrayDeque<OrbitAutonomousCommand<T>>> map = Arrays.stream(subsystems).collect(Collectors.toMap(Function.identity(), t -> new ArrayDeque<>(Collections.singleton(new EndCommand()))));
        selection.forEach(OrbitFunctionUtilities.specializeSecond((BiConsumer<OrbitAutonomousMode<T>, Consumer<OrbitAutonomousCommand<T>>>) OrbitAutonomousMode::add, c -> map.get(c.getSubsystem()).add(c)));
        controllers = new HashMap<>(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ((Function<Map.Entry<T, ArrayDeque<OrbitAutonomousCommand<T>>>, ArrayDeque<OrbitAutonomousCommand<T>>>) Map.Entry::getValue).andThen(SubsystemController::new))));
        ArrayList<Runnable> oldNext = startNext;
        startNext = new ArrayList<>();
        done = 0;
        oldNext.forEach(Runnable::run);
    }

    public void stop() {
        controllers.values().forEach(OrbitFunctionUtilities.specializeSecondSupplier(SubsystemController::setState, FinishedCommand::new));
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
            queue.forEach(OrbitFunctionUtilities.source((BiConsumer<OrbitAutonomousCommand<T>, Runnable>) OrbitAutonomousCommand::setGotoNextFunc, Function.identity(), OrbitFunctionUtilities.specializeFirst((BiFunction<Consumer<OrbitAutonomousCommand<T>>, OrbitAutonomousCommand<T>, Runnable>) OrbitFunctionUtilities::specialize, this::next)));
            queue.add(new FinishedCommand());
            this.queue = queue;
            next(null);
        }

        private synchronized void next(OrbitAutonomousCommand<T> last) {
            if (last != null && getState() != last)
                return;
            if (future != null)
                future.cancel(true);
            OrbitAutonomousCommand<T> command = queue.remove();
            setState(command);
            if (command.getTimeout() != 0)
                future = executor.schedule(OrbitFunctionUtilities.specialize(this::next, command), command.getTimeout(), TimeUnit.MILLISECONDS);
        }
    }
}
