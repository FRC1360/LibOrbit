/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalPredicate.java
 * A Predicate with a throws clause
 */

package ca._1360.liborbit.util.function;

/**
 * @param <T> The parameter type
 * @param <E> The throwable type
 */
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
