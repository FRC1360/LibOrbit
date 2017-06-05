/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalBiFunction.java
 * A BiFunction with a throws clause
 */

package ca._1360.liborbit.util.function;

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
