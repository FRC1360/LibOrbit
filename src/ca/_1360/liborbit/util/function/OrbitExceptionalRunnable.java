package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalRunnable<E extends Throwable> {
    void run() throws E;

    static <E extends Throwable> OrbitExceptionalRunnable<E> both(OrbitExceptionalRunnable<? extends E> runnable1, OrbitExceptionalRunnable<? extends E> runnable2) {
        return () -> {
            runnable1.run();
            runnable2.run();
        };
    }

    static <E extends Throwable> OrbitExceptionalRunnable<E> noOp() {
        return () -> {};
    }
}
