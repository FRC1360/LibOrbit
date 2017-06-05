/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalBiConsumer.java
 * A BiConsumer with a throws clause
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
