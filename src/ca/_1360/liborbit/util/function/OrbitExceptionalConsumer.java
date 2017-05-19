package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalConsumer<T, E extends Throwable> {
    void accept(T t) throws E;
}
