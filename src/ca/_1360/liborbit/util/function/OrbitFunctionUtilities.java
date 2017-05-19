package ca._1360.liborbit.util.function;

import java.util.function.*;

public final class OrbitFunctionUtilities {
    private OrbitFunctionUtilities() { }

    public static <T, E extends Throwable> Consumer<T> wrapException(OrbitExceptionalConsumer<? super T, ? extends E> consumer, Function<? super E, ? extends RuntimeException> wrapper) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, E extends Throwable> Consumer<T> handleException(OrbitExceptionalConsumer<? super T, ? extends E> consumer, Consumer<? super E> handler) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                handler.accept((E) e);
            }
        };
    }

    public static <T, U, E extends Throwable> BiConsumer<T, U> wrapException(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Function<? super E, ? extends RuntimeException> wrapper) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, U, E extends Throwable> BiConsumer<T, U> handleException(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Consumer<? super E> handler) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Throwable e) {
                handler.accept((E) e);
            }
        };
    }

    public static <T, R, E extends Throwable> Function<T, R> wrapException(OrbitExceptionalFunction<? super T, ? extends R, ? extends E> function, Function<? super E, ? extends RuntimeException> wrapper) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, R, E extends Throwable> Function<T, R> handleException(OrbitExceptionalFunction<? super T, ? extends  R, ? extends E> function, Consumer<? super E> handler) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                handler.accept((E) e);
                return null;
            }
        };
    }

    public static <T, U, R, E extends Throwable> BiFunction<T, U, R> wrapException(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Function<? super E, ? extends RuntimeException> wrapper) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, U, R, E extends Throwable> BiFunction<T, U, R> handleException(OrbitExceptionalBiFunction<? super T, ? super U, ? extends  R, ? extends E> biFunction, Consumer<? super E> handler) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Throwable e) {
                handler.accept((E) e);
                return null;
            }
        };
    }

    public static <T, E extends Throwable> Predicate<T> wrapException(OrbitExceptionalPredicate<? super T, ? extends E> predicate, Function<? super E, ? extends RuntimeException> wrapper) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, E extends Throwable> Predicate<T> handleException(OrbitExceptionalPredicate<? super T, ? extends E> predicate, Consumer<? super E> handler) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Throwable e) {
                handler.accept((E) e);
                return false;
            }
        };
    }

    public static <T, U, E extends Throwable> BiPredicate<T, U> wrapException(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Function<? super E, ? extends RuntimeException> wrapper) {
        return (t, u) -> {
            try {
                return biPredicate.test(t, u);
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, U, E extends Throwable> BiPredicate<T, U> handleException(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Consumer<? super E> handler) {
        return (t, u) -> {
            try {
                return biPredicate.test(t, u);
            } catch (Throwable e) {
                handler.accept((E) e);
                return false;
            }
        };
    }

    public static <T, E extends Throwable> Supplier<T> wrapException(OrbitExceptionalSupplier<? extends T, ? extends E> supplier, Function<? super E, ? extends RuntimeException> wrapper) {
        return () -> {
            try {
                return supplier.get();
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <T, E extends Throwable> Supplier<T> handleException(OrbitExceptionalSupplier<? extends T, ? extends E> supplier, Consumer<? super E> handler) {
        return () -> {
            try {
                return supplier.get();
            } catch (Throwable e) {
                handler.accept((E) e);
                return null;
            }
        };
    }

    public static <T, U> Consumer<U> specializeFirst(BiConsumer<? super T, ? super U> biConsumer, T first) {
        return second -> biConsumer.accept(first, second);
    }

    public static <T, U> Consumer<T> specializeSecond(BiConsumer<? super T, ? super U> biConsumer, U second) {
        return first -> biConsumer.accept(first, second);
    }

    public static <T, U, R> Function<U, R> specializeFirst(BiFunction<? super T, ? super U, ? extends R> biFunction, T first) {
        return second -> biFunction.apply(first, second);
    }

    public static <T, U, R> Function<T, R> specializeSecond(BiFunction<? super T, ? super U, ? extends R> biFunction, U second) {
        return first -> biFunction.apply(first, second);
    }

    public static <T, U> Predicate<U> specializeFirst(BiPredicate<? super T, ? super U> biPredicate, T first) {
        return second -> biPredicate.test(first, second);
    }

    public static <T, U> Predicate<T> specializeSecond(BiPredicate<? super T, ? super U> biPredicate, U second) {
        return first -> biPredicate.test(first, second);
    }
}
