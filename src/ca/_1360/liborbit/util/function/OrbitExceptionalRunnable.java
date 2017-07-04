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

package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalRunnable<E extends Throwable> {
    void run() throws E;

    /**
     * @param runnable1 An exceptional runnable
     * @param runnable2 An exceptional runnable
     * @return An exceptional runnable that executes both runnables
     */
    static <E extends Throwable> OrbitExceptionalRunnable<E> both(OrbitExceptionalRunnable<? extends E> runnable1, OrbitExceptionalRunnable<? extends E> runnable2) {
        return () -> {
            runnable1.run();
            runnable2.run();
        };
    }

    /**
     * @return An exceptional runnable that does nothing
     */
    static <E extends Throwable> OrbitExceptionalRunnable<E> noOp() {
        return () -> {};
    }
}
