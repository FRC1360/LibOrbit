/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalBiPredicate.java
 * A BiPredicate with a throws clause
 */

package ca._1360.liborbit.util.function;

/**
 * @param <T> The first parameter type
 * @param <U> The second parameter type
 * @param <E> The throwable type
 */
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
