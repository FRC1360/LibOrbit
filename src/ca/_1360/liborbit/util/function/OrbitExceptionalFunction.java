/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalFunction.java
 * A Function with a throws clause
 */

package ca._1360.liborbit.util.function;

/**
 * @param <T> The parameter type
 * @param <R> The return type
 * @param <E> The throwable type
 */
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
