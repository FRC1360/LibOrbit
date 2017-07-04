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
public interface OrbitExceptionalPredicate<T, E extends Throwable> {
    boolean test(T t) throws E;

    /**
     * @return An exceptional predicate that produces opposite results
     */
    default OrbitExceptionalPredicate<T, E> negate() {
        return t -> !test(t);
    }

    /**
     * @param predicate1 An exceptional predicate
     * @param predicate2 An exceptional predicate
     * @return An exceptional predicate that requires both predicates to approve the parameter
     */
    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> and(OrbitExceptionalPredicate<? super T, ? extends E> predicate1, OrbitExceptionalPredicate<? super T, ? extends E> predicate2) {
        return t -> predicate1.test(t) && predicate2.test(t);
    }

    /**
     * @param predicate1 An exceptional predicate
     * @param predicate2 An exceptional predicate
     * @return An exceptional predicate that requires at least one predicate to approve the parameter
     */
    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> or(OrbitExceptionalPredicate<? super T, ? extends E> predicate1, OrbitExceptionalPredicate<? super T, ? extends E> predicate2) {
        return t -> predicate1.test(t) || predicate2.test(t);
    }

    /**
     * @return An exceptional predicate that always approves its parameter
     */
    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> always() {
        return t -> true;
    }

    /**
     * @return An exceptional predicate that never approves its parameter
     */
    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> never() {
        return t -> false;
    }
}
