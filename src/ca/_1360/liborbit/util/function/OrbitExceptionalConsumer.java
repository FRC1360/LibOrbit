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
public interface OrbitExceptionalConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    /**
     * @param consumer1 An exceptional consumer
     * @param consumer2 An exceptional consumer
     * @return An exceptional consumer that executes both consumers with its parameter
     */
    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> both(OrbitExceptionalConsumer<? super T, ? extends E> consumer1, OrbitExceptionalConsumer<? super T, ? extends E> consumer2) {
        return t -> {
            consumer1.accept(t);
            consumer1.accept(t);
        };
    }

    /**
     * @return An exceptional consumer that does nothing
     */
    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> noOp() {
        return t -> {};
    }
}
