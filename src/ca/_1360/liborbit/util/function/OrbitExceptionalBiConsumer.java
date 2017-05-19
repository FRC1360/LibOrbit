package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalBiConsumer<T, U, E extends Throwable> {
    void accept(T t, U u) throws E;
}
