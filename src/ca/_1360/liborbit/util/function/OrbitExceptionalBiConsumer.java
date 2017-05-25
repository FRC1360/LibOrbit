package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalBiConsumer<T, U, E extends Throwable> {
    void accept(T t, U u) throws E;

    static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> both(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer1, OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer2) {
        return (t, u) -> {
            biConsumer1.accept(t, u);
            biConsumer2.accept(t, u);
        };
    }

    static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> noOp() {
        return (t, u) -> {};
    }
}
