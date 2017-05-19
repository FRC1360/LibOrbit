package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalSupplier<T, E extends Throwable> {
    T get() throws E;
}
