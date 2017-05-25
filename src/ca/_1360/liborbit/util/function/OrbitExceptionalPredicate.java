package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalPredicate<T, E extends Throwable> {
    boolean test(T t) throws E;

    default OrbitExceptionalPredicate<T, E> negate() {
        return t -> !test(t);
    }

    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> and(OrbitExceptionalPredicate<? super T, ? extends E> predicate1, OrbitExceptionalPredicate<? super T, ? extends E> predicate2) {
        return t -> predicate1.test(t) && predicate2.test(t);
    }

    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> or(OrbitExceptionalPredicate<? super T, ? extends E> predicate1, OrbitExceptionalPredicate<? super T, ? extends E> predicate2) {
        return t -> predicate1.test(t) || predicate2.test(t);
    }

    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> always() {
        return t -> true;
    }

    static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> never() {
        return t -> false;
    }
}
