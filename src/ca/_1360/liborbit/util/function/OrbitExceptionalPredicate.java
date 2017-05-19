package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalPredicate<T, E extends Throwable> {
    boolean test(T t) throws E;
}
