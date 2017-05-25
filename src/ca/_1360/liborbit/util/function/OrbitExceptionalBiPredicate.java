package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalBiPredicate<T, U, E extends Throwable> {
    boolean test(T t, U u) throws E;

    default OrbitExceptionalBiPredicate<T, U, E> negate() {
        return (t, u) -> !test(t, u);
    }

    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> and(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate1, OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate2) {
        return (t, u) -> biPredicate1.test(t, u) && biPredicate2.test(t, u);
    }

    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> or(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate1, OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate2) {
        return (t, u) -> biPredicate1.test(t, u) || biPredicate2.test(t, u);
    }

    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> always() {
        return (t, u) -> true;
    }

    static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> never() {
        return (t, u) -> false;
    }
}
