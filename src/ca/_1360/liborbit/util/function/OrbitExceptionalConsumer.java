/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalConsumer.java
 * A Consumer with a throws clause
 */

package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    /**
     * @param consumer1 An exceptional consumer
     * @param consumer2 An exceptional consumer
     * @return An exceptional consumer that executes both consumers with its parameter
     */
    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> both(OrbitExceptionalConsumer<? super T, ? extends E> consumer1, OrbitExceptionalConsumer<? super T, ? extends E> consumer2) {
        return t -> {
            consumer1.accept(t);
            consumer1.accept(t);
        };
    }

    /**
     * @return An exceptional consumer that does nothing
     */
    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> noOp() {
        return t -> {};
    }
}
