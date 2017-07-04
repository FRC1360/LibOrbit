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
 * @param <T> The return type
 * @param <E> The throwable type
 */
@FunctionalInterface
public interface OrbitExceptionalSupplier<T, E extends Throwable> {
    T get() throws E;

    /**
     * @param value The constant result
     * @return An exceptional supplier that always produces the given value
     */
    static <T, E extends Throwable> OrbitExceptionalSupplier<T, E> constant(T value) {
        return () -> value;
    }
}
