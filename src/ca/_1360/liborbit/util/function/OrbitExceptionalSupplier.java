/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalSupplier.java
 * A Supplier with a throws clause
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
