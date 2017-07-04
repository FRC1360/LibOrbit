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

/**
 * @param <T> The first parameter type
 * @param <U> The second parameter type
 * @param <R> The return type
 * @param <E> The throwable type
 */
public interface OrbitExceptionalBiFunction<T, U, R, E extends Throwable> {
    R apply(T t, U u) throws E;

    /**
     * @param biFunction An exceptional bi-function
     * @param function An exceptional function
     * @return An exceptional bi-function that executes the original bi-function with its parameters, then executes the function with the result of the bi-function, and returns the result of the function
     */
    static <T, U, V, R, E extends Throwable> OrbitExceptionalBiFunction<T, U, R, E> compose(OrbitExceptionalBiFunction<? super T, ? super U, ? extends V, ? extends E> biFunction, OrbitExceptionalFunction<? super V, ? extends R, ? extends E> function) {
        return (t, u) -> function.apply(biFunction.apply(t, u));
    }
}
