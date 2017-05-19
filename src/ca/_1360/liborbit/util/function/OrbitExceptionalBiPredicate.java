package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalBiPredicate<T, U, E extends Throwable> {
    boolean test(T t, U u) throws E;
}
