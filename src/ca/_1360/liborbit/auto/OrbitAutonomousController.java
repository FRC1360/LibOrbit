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

import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.statemachine.OrbitSimpleStateMachine;
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
import java.util.stream.Stream;

/**
 * @param <T> The subsystem type; usually an enum
 */
public final class OrbitAutonomousController<T> {
    private final List<OrbitAutonomousMode<T>> modes;
    private final T[] subsystems;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
    private final ArrayList<OrbitAutonomousMode<T>> selection = new ArrayList<>();
    private Map<T, SubsystemController> controllers;
    private ArrayList<Runnable> startNext;
    private int done;

    /**
     * @param modes The modes that can be selected to run
     * @param subsystems The valid subsystems
     */
    public OrbitAutonomousController(List<OrbitAutonomousMode<T>> modes, T[] subsystems) {
        this.modes = modes;
        this.subsystems = subsystems;
    }

    /**
     * @return The modes that can be selected to run
     */
    public List<OrbitAutonomousMode<T>> getModes() {
        return new ArrayList<>(modes);
    }

    /**
     * @return The modes currently selected to run
     */
    public ArrayList<OrbitAutonomousMode<T>> getSelection() {
        return selection;
    }

    /**
     * Runs the selected autonomous modes in order; non-blocking
     */
    public void start() {
    	// Reset fields
        done = 0;
        startNext = new ArrayList<>();
        // Create a queue for each subsystem, and organize them into a map of subsystem to queue
        Map<T, ArrayDeque<OrbitAutonomousCommand<T>>> map = Arrays.stream(subsystems).collect(Collectors.toMap(Function.identity(), t -> new ArrayDeque<>(Collections.singleton(new FinishedCommand()))));
        // Add the commands from each mode to their respective queues
        for (OrbitAutonomousMode<T> mode : selection) {
            mode.add(c -> map.get(c.getSubsystem()).add(c));
            map.values().forEach(OrbitFunctionUtilities.specializeSecondSupplier((BiConsumer<? super ArrayDeque<OrbitAutonomousCommand<T>>, ? super EndCommand>) Deque::add, EndCommand::new));
        }
        // Create a subsystem controller for each subsystem, and initialize it with its queue of commands
        controllers = new HashMap<>(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ((Function<Map.Entry<T, ArrayDeque<OrbitAutonomousCommand<T>>>, ArrayDeque<OrbitAutonomousCommand<T>>>) Map.Entry::getValue).andThen(OrbitFunctionUtilities.wrapException(SubsystemController::new, RuntimeException::new)))));
        // Move each subsystem controller onto its first real command
        controllers.values().stream().map(OrbitStateMachine::getState).forEach(OrbitAutonomousCommand::gotoNext);
    }

    /**
     * Stops the autonomous mode
     */
    public void stop() {
    	// Start a finish command on each subsystem controller
    	if (controllers != null)
    		controllers.values().forEach(OrbitFunctionUtilities.specializeSecondSupplier(SubsystemController::setState, FinishedCommand::new));
    }

    /**
     * Blocks until the command currently executing on a given subsystem has exited
     * @param subsystem The subsystem to use
     * @throws InterruptedException Thrown if the current thread is interrupted while blocking
     */
    public void waitFor(T subsystem) throws InterruptedException {
    	// Call the join method on the current command
        controllers.get(subsystem).getState().join();
    }

    /**
     * Command to be inserted at the end of a mode to wait for all subsystems to complete before continuing 
     */
    private final class EndCommand extends OrbitAutonomousCommand<T> {
        private EndCommand() {
        	// No subsystem or timeout necessary
            super(null, 0);
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.auto.OrbitAutonomousCommand#initializeCore()
         */
        @Override
        protected void initializeCore() {
        	// Increment the number of subsystems that have finished and compare to total number of subsystems
            if (++done == controllers.size()) {
            	// Call the next function provided by other subsystems
                ArrayList<Runnable> oldNext = startNext;
                startNext = new ArrayList<>();
                done = 0;
                oldNext.forEach(Runnable::run);
                // Start next command
                gotoNext();
            } else
            	// Add next function to list
                startNext.add(this::gotoNext);
        }
    }

    /**
     * Empty command to be switched to once a subsystem has completed all commands
     */
    private final class FinishedCommand extends OrbitAutonomousCommand<T> {
        public FinishedCommand() {
            super(null, 0);
        }
    }

    /**
     * A state machine that represents the 
     */
    private final class SubsystemController extends OrbitSimpleStateMachine<OrbitAutonomousCommand<T>> {
        private ArrayDeque<OrbitAutonomousCommand<T>> queue;
        private ScheduledFuture<?> future;

        /**
         * @param queue The commands to be run on this subsystem
         * @throws OrbitPipelineInvalidConfigurationException Thrown if the first command provides an invalid pipeline configuration
         */
        private SubsystemController(ArrayDeque<OrbitAutonomousCommand<T>> queue) throws OrbitPipelineInvalidConfigurationException {
        	// Start with new empty command
            super(Stream.concat(Stream.of(new FinishedCommand()), queue.stream()).collect(Collectors.toList()));
            // Set the next function for each command
            queue.forEach(OrbitFunctionUtilities.source((BiConsumer<OrbitAutonomousCommand<T>, Runnable>) OrbitAutonomousCommand::setGotoNextFunc, Function.identity(), OrbitFunctionUtilities.specializeFirst((BiFunction<Consumer<OrbitAutonomousCommand<T>>, OrbitAutonomousCommand<T>, Runnable>) OrbitFunctionUtilities::specialize, this::next)));
            // Set up end of queue
            queue.add(new FinishedCommand());
            this.queue = queue;
            // Start the subsystem
            next(null);
        }

        /**
         * Moves the subsystem to the next command in the queue
         * @param last If not null, only moves if the subsystem is on the given command
         */
        private synchronized void next(OrbitAutonomousCommand<T> last) {
        	// Exit if in invalid state
            if (last != null && getState() != last)
                return;
            // Cancel timeout if it exists
            if (future != null)
                future.cancel(true);
            // Start the next command
            OrbitAutonomousCommand<T> command = queue.remove();
            setState(command);
            // Start timeout if applicable
            future = command.getTimeout() == 0 ? null : executor.schedule(OrbitFunctionUtilities.specialize(this::next, command), command.getTimeout(), TimeUnit.MILLISECONDS);
        }
    }
}
