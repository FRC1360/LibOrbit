package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> both(OrbitExceptionalConsumer<? super T, ? extends E> consumer1, OrbitExceptionalConsumer<? super T, ? extends E> consumer2) {
        return t -> {
            consumer1.accept(t);
            consumer1.accept(t);
        };
    }

    static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> noOp() {
        return t -> {};
    }
}
