package ca._1360.liborbit.util.function;

import java.util.Arrays;
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

    public static <E extends Throwable> Runnable wrapException(OrbitExceptionalRunnable<? extends E> runnable, Function<? super E, ? extends RuntimeException> wrapper) {
        return () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                throw wrapper.apply((E) e);
            }
        };
    }

    public static <E extends Throwable> Runnable handleException(OrbitExceptionalRunnable<? extends E> runnable, Consumer<? super E> handler) {
        return () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                handler.accept((E) e);
            }
        };
    }

    public static <T, U> Consumer<U> specializeFirst(BiConsumer<? super T, ? super U> biConsumer, T t) {
        return u -> biConsumer.accept(t, u);
    }

    public static <T, U> Consumer<T> specializeSecond(BiConsumer<? super T, ? super U> biConsumer, U u) {
        return t -> biConsumer.accept(t, u);
    }

    public static <T, U, R> Function<U, R> specializeFirst(BiFunction<? super T, ? super U, ? extends R> biFunction, T t) {
        return u -> biFunction.apply(t, u);
    }

    public static <T, U, R> Function<T, R> specializeSecond(BiFunction<? super T, ? super U, ? extends R> biFunction, U u) {
        return t -> biFunction.apply(t, u);
    }

    public static <T, U> Predicate<U> specializeFirst(BiPredicate<? super T, ? super U> biPredicate, T t) {
        return u -> biPredicate.test(t, u);
    }

    public static <T, U> Predicate<T> specializeSecond(BiPredicate<? super T, ? super U> biPredicate, U u) {
        return t -> biPredicate.test(t, u);
    }

    public static <T> Runnable specialize(Consumer<? super T> consumer, T t) {
        return () -> consumer.accept(t);
    }

    public static <T, U> Runnable specialize(BiConsumer<? super T, ? super U> biConsumer, T t, U u) {
        return () -> biConsumer.accept(t, u);
    }

    public static <T, R> Supplier<R> specialize(Function<? super T, ? extends R> function, T t) {
        return () -> function.apply(t);
    }

    public static <T, U, R> Supplier<R> specialize(BiFunction<? super T, ? super U, ? extends R> biFunction, T t, U u) {
        return () -> biFunction.apply(t, u);
    }

    public static <T> BooleanSupplier specialize(Predicate<? super T> predicate, T t) {
        return () -> predicate.test(t);
    }

    public static <T, U> BooleanSupplier specialize(BiPredicate<? super T, ? super U> biPredicate, T t, U u) {
        return () -> biPredicate.test(t, u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<U, E> specializeFirstEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, T t) {
        return u -> biConsumer.accept(t, u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<T, E> specializeSecondEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, U u) {
        return t -> biConsumer.accept(t, u);
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<U, R, E> specializeFirstEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, T t) {
        return u -> biFunction.apply(t, u);
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<T, R, E> specializeSecondEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, U u) {
        return t -> biFunction.apply(t, u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<U, E> specializeFirstEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, T t) {
        return u -> biPredicate.test(t, u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<T, E> specializeSecondEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, U u) {
        return t -> biPredicate.test(t, u);
    }

    public static <T, E extends Throwable> OrbitExceptionalRunnable<E> specializeEx(OrbitExceptionalConsumer<? super T, ? extends E> consumer, T t) {
        return () -> consumer.accept(t);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalRunnable<E> specializeEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, T t, U u) {
        return () -> biConsumer.accept(t, u);
    }

    public static <T, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeEx(OrbitExceptionalFunction<? super T, ? extends R, ? extends E> function, T t) {
        return () -> function.apply(t);
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, T t, U u) {
        return () -> biFunction.apply(t, u);
    }

    public static <T, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeEx(OrbitExceptionalPredicate<? super T, ? extends E> predicate, T t) {
        return () -> predicate.test(t);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, T t, U u) {
        return () -> biPredicate.test(t, u);
    }

    public static <T, U> Consumer<U> specializeFirst(BiConsumer<? super T, ? super U> biConsumer, Supplier<T> t) {
        return u -> biConsumer.accept(t.get(), u);
    }

    public static <T, U> Consumer<T> specializeSecond(BiConsumer<? super T, ? super U> biConsumer, Supplier<U> u) {
        return t -> biConsumer.accept(t, u.get());
    }

    public static <T, U, R> Function<U, R> specializeFirst(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<T> t) {
        return u -> biFunction.apply(t.get(), u);
    }

    public static <T, U, R> Function<T, R> specializeSecond(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<U> u) {
        return t -> biFunction.apply(t, u.get());
    }

    public static <T, U> Predicate<U> specializeFirst(BiPredicate<? super T, ? super U> biPredicate, Supplier<T> t) {
        return u -> biPredicate.test(t.get(), u);
    }

    public static <T, U> Predicate<T> specializeSecond(BiPredicate<? super T, ? super U> biPredicate, Supplier<U> u) {
        return t -> biPredicate.test(t, u.get());
    }

    public static <T> Runnable specialize(Consumer<? super T> consumer, Supplier<T> t) {
        return () -> consumer.accept(t.get());
    }

    public static <T, U> Runnable specialize(BiConsumer<? super T, ? super U> biConsumer, Supplier<T> t, Supplier<U> u) {
        return () -> biConsumer.accept(t.get(), u.get());
    }

    public static <T, R> Supplier<R> specialize(Function<? super T, ? extends R> function, Supplier<T> t) {
        return () -> function.apply(t.get());
    }

    public static <T, U, R> Supplier<R> specialize(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<T> t, Supplier<U> u) {
        return () -> biFunction.apply(t.get(), u.get());
    }

    public static <T> BooleanSupplier specialize(Predicate<? super T> predicate, Supplier<T> t) {
        return () -> predicate.test(t.get());
    }

    public static <T, U> BooleanSupplier specialize(BiPredicate<? super T, ? super U> biPredicate, Supplier<T> t, Supplier<U> u) {
        return () -> biPredicate.test(t.get(), u.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<U, E> specializeFirstEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<T> t) {
        return u -> biConsumer.accept(t.get(), u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<T, E> specializeSecondEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<U> u) {
        return t -> biConsumer.accept(t, u.get());
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<U, R, E> specializeFirstEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<T> t) {
        return u -> biFunction.apply(t.get(), u);
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<T, R, E> specializeSecondEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<U> u) {
        return t -> biFunction.apply(t, u.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<U, E> specializeFirstEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<T> t) {
        return u -> biPredicate.test(t.get(), u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<T, E> specializeSecondEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<U> u) {
        return t -> biPredicate.test(t, u.get());
    }

    public static <T, E extends Throwable> OrbitExceptionalRunnable<E> specializeEx(OrbitExceptionalConsumer<? super T, ? extends E> consumer, Supplier<T> t) {
        return () -> consumer.accept(t.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalRunnable<E> specializeEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<T> t, Supplier<U> u) {
        return () -> biConsumer.accept(t.get(), u.get());
    }

    public static <T, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeEx(OrbitExceptionalFunction<? super T, ? extends R, ? extends E> function, Supplier<T> t) {
        return () -> function.apply(t.get());
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<T> t, Supplier<U> u) {
        return () -> biFunction.apply(t.get(), u.get());
    }

    public static <T, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeEx(OrbitExceptionalPredicate<? super T, ? extends E> predicate, Supplier<T> t) {
        return () -> predicate.test(t.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<T> t, Supplier<U> u) {
        return () -> biPredicate.test(t.get(), u.get());
    }

    public static Runnable combine(Runnable... runnables) {
        return Arrays.stream(runnables).reduce((a, b) -> () -> {
            a.run();
            b.run();
        }).orElse(() -> {});
    }

    @SafeVarargs
    public static <T> Consumer<T> combine(Consumer<? super T>... consumers) {
        return Arrays.stream(consumers).map(consumer -> (Consumer<T>) consumer::accept).reduce(Consumer::andThen).orElse(t -> {});
    }

    @SafeVarargs
    public static <T, U> BiConsumer<T, U> combine(BiConsumer<? super T, ? super U>... biConsumers) {
        return Arrays.stream(biConsumers).map(biConsumer -> (BiConsumer<T, U>) biConsumer::accept).reduce(BiConsumer::andThen).orElse((t, u) -> {});
    }

    @SafeVarargs
    public static <T> UnaryOperator<T> compose(Function<? super T, ? extends T>... functions) {
        return Arrays.stream(functions).map(function -> (Function<T, T>) function::apply).reduce(Function::andThen).orElse(Function.identity())::apply;
    }

    @SafeVarargs
    public static <T> Predicate<T> all(Predicate<? super T>... predicates) {
        return Arrays.stream(predicates).map(predicate -> (Predicate<T>) predicate::test).reduce(Predicate::and).orElse(t -> true);
    }

    @SafeVarargs
    public static <T, U> BiPredicate<T, U> all(BiPredicate<? super T, ? super U>... biPredicates) {
        return Arrays.stream(biPredicates).map(biPredicate -> (BiPredicate<T, U>) biPredicate::test).reduce(BiPredicate::and).orElse((t, u) -> true);
    }

    @SafeVarargs
    public static <T> Predicate<T> any(Predicate<? super T>... predicates) {
        return Arrays.stream(predicates).map(predicate -> (Predicate<T>) predicate::test).reduce(Predicate::or).orElse(t -> false);
    }

    @SafeVarargs
    public static <T, U> BiPredicate<T, U> any(BiPredicate<? super T, ? super U>... biPredicates) {
        return Arrays.stream(biPredicates).map(biPredicate -> (BiPredicate<T, U>) biPredicate::test).reduce(BiPredicate::or).orElse((t, u) -> false);
    }

    @SafeVarargs
    public static <E extends Throwable> OrbitExceptionalRunnable<E> combineEx(OrbitExceptionalRunnable<? extends E>... runnables) {
        return Arrays.stream(runnables).map(runnable -> (OrbitExceptionalRunnable<E>) runnable::run).reduce(OrbitExceptionalRunnable::both).orElse(OrbitExceptionalRunnable.noOp());
    }

    @SafeVarargs
    public static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> combineEx(OrbitExceptionalConsumer<? super T, ? extends E>... consumers) {
        return Arrays.stream(consumers).map(consumer -> (OrbitExceptionalConsumer<T, E>) consumer::accept).reduce(OrbitExceptionalConsumer::both).orElse(OrbitExceptionalConsumer.noOp());
    }

    @SafeVarargs
    public static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> combineEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E>... biConsumers) {
        return Arrays.stream(biConsumers).map(biConsumer -> (OrbitExceptionalBiConsumer<T, U, E>) biConsumer::accept).reduce(OrbitExceptionalBiConsumer::both).orElse(OrbitExceptionalBiConsumer.noOp());
    }

    @SafeVarargs
    public static <T, E extends Throwable> OrbitExceptionalFunction<T, T, E> composeEx(OrbitExceptionalFunction<? super T, ? extends T, ? extends E>... functions) {
        return Arrays.stream(functions).map(function -> (OrbitExceptionalFunction<T, T, E>) function::apply).reduce(OrbitExceptionalFunction::compose).orElse(OrbitExceptionalFunction.identity());
    }

    @SafeVarargs
    public static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> allEx(OrbitExceptionalPredicate<? super T, ? extends E>... predicates) {
        return Arrays.stream(predicates).map(predicate -> (OrbitExceptionalPredicate<T, E>) predicate::test).reduce(OrbitExceptionalPredicate::and).orElse(OrbitExceptionalPredicate.always());
    }

    @SafeVarargs
    public static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> allEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E>... biPredicates) {
        return Arrays.stream(biPredicates).map(biPredicate -> (OrbitExceptionalBiPredicate<T, U, E>) biPredicate::test).reduce(OrbitExceptionalBiPredicate::and).orElse(OrbitExceptionalBiPredicate.always());
    }

    @SafeVarargs
    public static <T, E extends Throwable> OrbitExceptionalPredicate<T, E> anyEx(OrbitExceptionalPredicate<? super T, ? extends E>... predicates) {
        return Arrays.stream(predicates).map(predicate -> (OrbitExceptionalPredicate<T, E>) predicate::test).reduce(OrbitExceptionalPredicate::or).orElse(OrbitExceptionalPredicate.never());
    }

    @SafeVarargs
    public static <T, U, E extends Throwable> OrbitExceptionalBiPredicate<T, U, E> anyEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E>... biPredicates) {
        return Arrays.stream(biPredicates).map(biPredicate -> (OrbitExceptionalBiPredicate<T, U, E>) biPredicate::test).reduce(OrbitExceptionalBiPredicate::or).orElse(OrbitExceptionalBiPredicate.never());
    }
}
