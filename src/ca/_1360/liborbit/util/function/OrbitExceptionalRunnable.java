package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalRunnable<E extends Throwable> {
    void run() throws E;
}
