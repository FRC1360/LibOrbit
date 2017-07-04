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
public interface OrbitExceptionalFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    /**
     * @param function1 An exceptional function
     * @param function2 An exceptional function
     * @return A function that executes the first function with its parameter, then executes the second function with the result of the first function, and returns the result of the second function
     */
    static <T, V, R, E extends Throwable> OrbitExceptionalFunction<T, R, E> compose(OrbitExceptionalFunction<? super T, ? extends V, ? extends E> function1, OrbitExceptionalFunction<? super V, ? extends R, ? extends E> function2) {
        return t -> function2.apply(function1.apply(t));
    }

    /**
     * @return An exceptional function that returns its input
     */
    static <T, E extends Throwable> OrbitExceptionalFunction<T, T, E> identity() {
        return t -> t;
    }
}
