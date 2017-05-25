package ca._1360.liborbit.util.function;

public interface OrbitExceptionalBiFunction<T, U, R, E extends Throwable> {
    R apply(T t, U u) throws E;

    static <T, U, V, R, E extends Throwable> OrbitExceptionalBiFunction<T, U, R, E> compose(OrbitExceptionalBiFunction<? super T, ? super U, ? extends V, ? extends E> biFunction, OrbitExceptionalFunction<? super V, ? extends R, ? extends E> function) {
        return (t, u) -> function.apply(biFunction.apply(t, u));
    }
}
