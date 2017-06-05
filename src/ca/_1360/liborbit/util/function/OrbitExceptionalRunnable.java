/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitExceptionalRunnable.java
 * A Runnable with a throws clause
 */

package ca._1360.liborbit.util.function;

@FunctionalInterface
public interface OrbitExceptionalRunnable<E extends Throwable> {
    void run() throws E;

    /**
     * @param runnable1 An exceptional runnable
     * @param runnable2 An exceptional runnable
     * @return An exceptional runnable that executes both runnables
     */
    static <E extends Throwable> OrbitExceptionalRunnable<E> both(OrbitExceptionalRunnable<? extends E> runnable1, OrbitExceptionalRunnable<? extends E> runnable2) {
        return () -> {
            runnable1.run();
            runnable2.run();
        };
    }

    /**
     * @return An exceptional runnable that does nothing
     */
    static <E extends Throwable> OrbitExceptionalRunnable<E> noOp() {
        return () -> {};
    }
}
