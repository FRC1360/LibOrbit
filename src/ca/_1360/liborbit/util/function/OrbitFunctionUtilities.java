/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
 */

package ca._1360.liborbit.util.function;

import java.util.Arrays;
import java.util.function.*;

public final class OrbitFunctionUtilities {
    private OrbitFunctionUtilities() { }
    
    // Exception wrapping methods: generate a non-exceptional functional interface from an exceptional one by providing a function to wrap the throwable in an unchecked exception
    
    // Exception handling methods: generate a non-exceptional functional interface from an exceptional one by providing a consumer to handle the throwable that occurs

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
    
    // Specialization methods: specify the value for first, second, both, or only parameter(s) of a functional interface; exceptional versions are suffixed with -Ex 

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
    
    // Supplier specialization methods: specify a supplier to produce the first, second, both, or only parameter(s) of a functional interface; exceptional versions are suffixed with -Ex

    public static <T, U> Consumer<U> specializeFirstSupplier(BiConsumer<? super T, ? super U> biConsumer, Supplier<T> t) {
        return u -> biConsumer.accept(t.get(), u);
    }

    public static <T, U> Consumer<T> specializeSecondSupplier(BiConsumer<? super T, ? super U> biConsumer, Supplier<U> u) {
        return t -> biConsumer.accept(t, u.get());
    }

    public static <T, U, R> Function<U, R> specializeFirstSupplier(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<T> t) {
        return u -> biFunction.apply(t.get(), u);
    }

    public static <T, U, R> Function<T, R> specializeSecondSupplier(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<U> u) {
        return t -> biFunction.apply(t, u.get());
    }

    public static <T, U> Predicate<U> specializeFirstSupplier(BiPredicate<? super T, ? super U> biPredicate, Supplier<T> t) {
        return u -> biPredicate.test(t.get(), u);
    }

    public static <T, U> Predicate<T> specializeSecondSupplier(BiPredicate<? super T, ? super U> biPredicate, Supplier<U> u) {
        return t -> biPredicate.test(t, u.get());
    }

    public static <T> Runnable specializeSupplier(Consumer<? super T> consumer, Supplier<T> t) {
        return () -> consumer.accept(t.get());
    }

    public static <T, U> Runnable specializeSupplier(BiConsumer<? super T, ? super U> biConsumer, Supplier<T> t, Supplier<U> u) {
        return () -> biConsumer.accept(t.get(), u.get());
    }

    public static <T, R> Supplier<R> specializeSupplier(Function<? super T, ? extends R> function, Supplier<T> t) {
        return () -> function.apply(t.get());
    }

    public static <T, U, R> Supplier<R> specializeSupplier(BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<T> t, Supplier<U> u) {
        return () -> biFunction.apply(t.get(), u.get());
    }

    public static <T> BooleanSupplier specializeSupplier(Predicate<? super T> predicate, Supplier<T> t) {
        return () -> predicate.test(t.get());
    }

    public static <T, U> BooleanSupplier specializeSupplier(BiPredicate<? super T, ? super U> biPredicate, Supplier<T> t, Supplier<U> u) {
        return () -> biPredicate.test(t.get(), u.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<U, E> specializeFirstSupplierEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<T> t) {
        return u -> biConsumer.accept(t.get(), u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalConsumer<T, E> specializeSecondSupplierEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<U> u) {
        return t -> biConsumer.accept(t, u.get());
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<U, R, E> specializeFirstSupplierEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<T> t) {
        return u -> biFunction.apply(t.get(), u);
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalFunction<T, R, E> specializeSecondSupplierEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<U> u) {
        return t -> biFunction.apply(t, u.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<U, E> specializeFirstSupplierEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<T> t) {
        return u -> biPredicate.test(t.get(), u);
    }

    public static <T, U, E extends Throwable> OrbitExceptionalPredicate<T, E> specializeSecondSupplierEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<U> u) {
        return t -> biPredicate.test(t, u.get());
    }

    public static <T, E extends Throwable> OrbitExceptionalRunnable<E> specializeSupplierEx(OrbitExceptionalConsumer<? super T, ? extends E> consumer, Supplier<T> t) {
        return () -> consumer.accept(t.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalRunnable<E> specializeSupplierEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, Supplier<T> t, Supplier<U> u) {
        return () -> biConsumer.accept(t.get(), u.get());
    }

    public static <T, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeSupplierEx(OrbitExceptionalFunction<? super T, ? extends R, ? extends E> function, Supplier<T> t) {
        return () -> function.apply(t.get());
    }

    public static <T, U, R, E extends Throwable> OrbitExceptionalSupplier<R, E> specializeSupplierEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, Supplier<T> t, Supplier<U> u) {
        return () -> biFunction.apply(t.get(), u.get());
    }

    public static <T, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeSupplierEx(OrbitExceptionalPredicate<? super T, ? extends E> predicate, Supplier<T> t) {
        return () -> predicate.test(t.get());
    }

    public static <T, U, E extends Throwable> OrbitExceptionalSupplier<Boolean, E> specializeSupplierEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, Supplier<T> t, Supplier<U> u) {
        return () -> biPredicate.test(t.get(), u.get());
    }
    
    // Source supplying methods: produce a single-parameter functional interface from a dual-parameter one by providing a function to map each parameter; exceptional versions are suffixed with -Ex

    public static <T, U, V> Consumer<V> source(BiConsumer<? super T, ? super U> biConsumer, Function<? super V, ? extends T> tAdapter, Function<? super V, ? extends U> uAdapter) {
        return v -> biConsumer.accept(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W> BiConsumer<V, W> source(BiConsumer<? super T, ? super U> biConsumer, BiFunction<? super V, ? super W, ? extends T> tAdapter, BiFunction<? super V, ? super W, ? extends U> uAdapter) {
        return (v, w) -> biConsumer.accept(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }

    public static <T, U, V, R> Function<V, R> source(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super V, ? extends T> tAdapter, Function<? super V, ? extends U> uAdapter) {
        return v -> biFunction.apply(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W, R> BiFunction<V, W, R> source(BiFunction<? super T, ? super U, ? extends R> biFunction, BiFunction<? super V, ? super W, ? extends T> tAdapter, BiFunction<? super V, ? super W, ? extends U> uAdapter) {
        return (v, w) -> biFunction.apply(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }

    public static <T, U, V> Predicate<V> source(BiPredicate<? super T, ? super U> biPredicate, Function<? super V, ? extends T> tAdapter, Function<? super V, ? extends U> uAdapter) {
        return v -> biPredicate.test(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W> BiPredicate<V, W> source(BiPredicate<? super T, ? super U> biPredicate, BiFunction<? super V, ? super W, ? extends T> tAdapter, BiFunction<? super V, ? super W, ? extends U> uAdapter) {
        return (v, w) -> biPredicate.test(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }

    public static <T, U, V, E extends Throwable> OrbitExceptionalConsumer<V, E> sourceEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, OrbitExceptionalFunction<? super V, ? extends T, ? extends E> tAdapter, OrbitExceptionalFunction<? super V, ? extends U, ? extends E> uAdapter) {
        return v -> biConsumer.accept(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W, E extends Throwable> OrbitExceptionalBiConsumer<V, W, E> sourceEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, OrbitExceptionalBiFunction<? super V, ? super W, ? extends T, ? extends E> tAdapter, OrbitExceptionalBiFunction<? super V, ? super W, ? extends U, ? extends E> uAdapter) {
        return (v, w) -> biConsumer.accept(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }

    public static <T, U, V, R, E extends Throwable> OrbitExceptionalFunction<V, R, E> sourceEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, OrbitExceptionalFunction<? super V, ? extends T, ? extends E> tAdapter, OrbitExceptionalFunction<? super V, ? extends U, ? extends E> uAdapter) {
        return v -> biFunction.apply(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W, R, E extends Throwable> OrbitExceptionalBiFunction<V, W, R, E> sourceEx(OrbitExceptionalBiFunction<? super T, ? super U, ? extends R, ? extends E> biFunction, OrbitExceptionalBiFunction<? super V, ? super W, ? extends T, ? extends E> tAdapter, OrbitExceptionalBiFunction<? super V, ? super W, ? extends U, ? extends E> uAdapter) {
        return (v, w) -> biFunction.apply(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }

    public static <T, U, V, E extends Throwable> OrbitExceptionalPredicate<V, E> sourceEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, OrbitExceptionalFunction<? super V, ? extends T, ? extends E> tAdapter, OrbitExceptionalFunction<? super V, ? extends U, ? extends E> uAdapter) {
        return v -> biPredicate.test(tAdapter.apply(v), uAdapter.apply(v));
    }

    public static <T, U, V, W, E extends Throwable> OrbitExceptionalBiPredicate<V, W, E> sourceEx(OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate, OrbitExceptionalBiFunction<? super V, ? super W, ? extends T, ? extends E> tAdapter, OrbitExceptionalBiFunction<? super V, ? super W, ? extends U, ? extends E> uAdapter) {
        return (v, w) -> biPredicate.test(tAdapter.apply(v, w), uAdapter.apply(v, w));
    }
    
    // Combining methods: produce a single result-less functional interface from multiple that executes them in order; exceptional versions are suffixed with -Ex
    
    // Composing methods: produce a single function from multiple that executes them in order, using the result of each as the parameter for the next; all values must be the same type due to Java generics limitations; exceptional versions are suffixed with -Ex
    
    // All/any methods: produce a single predicate-type functional interface from multiple that requires that all or any to approve the parameter(s); exceptional versions are suffixed with -Ex

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
    
    // Conditional methods: produce a result-less functional interface from an equivalent that executes it only if the given boolean supplier or predicate-type functional interface approves; exceptional versions are suffixed with -Ex
    
    public static Runnable conditional(Runnable runnable, BooleanSupplier condition) {
        return () -> {
            if (condition.getAsBoolean())
                runnable.run();
        };
    }

    public static <T> Consumer<T> conditional(Consumer<? super T> consumer, BooleanSupplier condition) {
        return t -> {
            if (condition.getAsBoolean())
                consumer.accept(t);
        };
    }

    public static <T> Consumer<T> conditional(Consumer<? super T> consumer, Predicate<? super T> predicate) {
        return t -> {
            if (predicate.test(t))
                consumer.accept(t);
        };
    }

    public static <T, U> BiConsumer<T, U> conditional(BiConsumer<? super T, ? super U> biConsumer, BooleanSupplier condition) {
        return (t, u) -> {
            if (condition.getAsBoolean())
                biConsumer.accept(t, u);
        };
    }

    public static <T, U> BiConsumer<T, U> conditional(BiConsumer<? super T, ? super U> biConsumer, BiPredicate<? super T, ? super U> biPredicate) {
        return (t, u) -> {
            if (biPredicate.test(t, u))
                biConsumer.accept(t, u);
        };
    }

    public static <E extends Throwable> OrbitExceptionalRunnable<E> conditionalEx(OrbitExceptionalRunnable<? extends E> runnable, OrbitExceptionalSupplier<? extends Boolean, ? extends E> condition) {
        return () -> {
            if (condition.get())
                runnable.run();
        };
    }

    public static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> conditionalEx(OrbitExceptionalConsumer<? super T, ? extends E> consumer, OrbitExceptionalSupplier<? extends Boolean, ? extends E> condition) {
        return t -> {
            if (condition.get())
                consumer.accept(t);
        };
    }

    public static <T, E extends Throwable> OrbitExceptionalConsumer<T, E> conditionalEx(OrbitExceptionalConsumer<? super T, ? extends E> consumer, OrbitExceptionalPredicate<? super T, ? extends E> predicate) {
        return t -> {
            if (predicate.test(t))
                consumer.accept(t);
        };
    }

    public static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> conditionalEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, OrbitExceptionalSupplier<? extends Boolean, ? extends E> condition) {
        return (t, u) -> {
            if (condition.get())
                biConsumer.accept(t, u);
        };
    }

    public static <T, U, E extends Throwable> OrbitExceptionalBiConsumer<T, U, E> conditionalEx(OrbitExceptionalBiConsumer<? super T, ? super U, ? extends E> biConsumer, OrbitExceptionalBiPredicate<? super T, ? super U, ? extends E> biPredicate) {
        return (t, u) -> {
            if (biPredicate.test(t, u))
                biConsumer.accept(t, u);
        };
    }
}
