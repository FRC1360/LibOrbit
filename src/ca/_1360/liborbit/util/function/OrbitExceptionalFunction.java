package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;
}
