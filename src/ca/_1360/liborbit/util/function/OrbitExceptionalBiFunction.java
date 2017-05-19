package ca._1360.liborbit.util.function;

public interface OrbitExceptionalBiFunction<T, U, R, E extends Throwable> {
    R apply(T t, U u) throws E;
}
