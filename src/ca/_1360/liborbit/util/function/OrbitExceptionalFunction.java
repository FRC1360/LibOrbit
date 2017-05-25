package ca._1360.liborbit.util.function;


@FunctionalInterface
public interface OrbitExceptionalFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    static <T, V, R, E extends Throwable> OrbitExceptionalFunction<T, R, E> compose(OrbitExceptionalFunction<? super T, ? extends V, ? extends E> function1, OrbitExceptionalFunction<? super V, ? extends R, ? extends E> function2) {
        return t -> function2.apply(function1.apply(t));
    }

    static <T, E extends Throwable> OrbitExceptionalFunction<T, T, E> identity() {
        return t -> t;
    }
}
