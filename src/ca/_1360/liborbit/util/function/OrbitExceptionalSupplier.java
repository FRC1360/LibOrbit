package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalSupplier<T, E extends Throwable> {
    T get() throws E;

    static <T, E extends Throwable> OrbitExceptionalSupplier<T, E> constant(T value) {
        return () -> value;
    }
}
