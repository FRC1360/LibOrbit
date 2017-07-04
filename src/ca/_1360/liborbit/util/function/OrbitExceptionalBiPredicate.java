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
public interface OrbitExceptionalBiPredicate<T, U, E extends Throwable> {
    boolean test(T t, U u) throws E;

    /**
     * @return An exceptional bi-predicate that produces the opposite results
     */
    default OrbitExceptionalBiPredicate<T, U, E> negate() {
        return (t, u) -> !test(t, u);
    }

    /**
     * @param biPredicate1 An exceptional bi-predicate
     * @param biPredicate2 An exceptional bi-predicate
     * @return An exceptional bi-predicate that requires both bi-predicates to approve the parameters
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> and(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate1, OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate2) {
        return (t, u) -> biPredicate1.test(t, u) && biPredicate2.test(t, u);
    }

    /**
     * @param biPredicate1 An exceptional bi-predicate
     * @param biPredicate2 An exceptional bi-predicate
     * @return An exceptional bi-predicate that requires at least one of the bi-predicates to approve the parameters 
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> or(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate1, OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate2) {
        return (t, u) -> biPredicate1.test(t, u) || biPredicate2.test(t, u);
    }

    /**
     * @return An exceptional bi-predicate that always approves the parameters
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> always() {
        return (t, u) -> true;
    }

    /**
     * @return An exceptional bi-predicate that never approves the parameters
     */
    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> never() {
        return (t, u) -> false;
    }
}
