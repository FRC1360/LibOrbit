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
 * @param <E> The throwable type
 */
@FunctionalInterface
public interface OrbitExceptionalBiConsumer<T, U, E extends Throwable> {
    void accept(T t, U u) throws E;

    /**
     * @param biConsumer1 An exceptional bi-consumer
     * @param biConsumer2 An exceptional bi-consumer
     * @return An exceptional bi-consumer that executes both bi-consumers with its parameters
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> both(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer1, OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer2) {
        return (t, u) -> {
            biConsumer1.accept(t, u);
            biConsumer2.accept(t, u);
        };
    }

    /**
     * @return An exceptional bi-consumer that does nothing
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> noOp() {
        return (t, u) -> {};
    }
}
