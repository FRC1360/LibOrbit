package ca._1360.liborbit.util;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class OrbitMiscUtilities {
    private OrbitMiscUtilities() { }

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(((Iterable<T>) () -> iterator).spliterator(), false);
    }

    public static <T> Stream<T> stream(Supplier<Optional<T>> supplier) {
        return stream(new Iterator<T>() {
            T _next;

            @Override
            public boolean hasNext() {
                if (_next == null) {
                    Optional<T> value = supplier.get();
                    if (value.isPresent()) {
                        _next = value.get();
                        return true;
                    }
                    return false;
                }
                return true;
            }

            @Override
            public T next() {
                if (_next == null)
                    return supplier.get().orElseThrow(NoSuchElementException::new);
                return _next;
            }
        });
    }

    @SafeVarargs
    public static <T> Stream<T> concat(Stream<? extends T>... streams) {
        return Arrays.stream(streams).map(OrbitFunctionUtilities.<Stream<? extends T>, Function<? super T, T>, Stream<T>>specializeSecond(Stream::map, Function.<T>identity())).reduce(Stream::concat).orElse(Stream.empty());
    }

    public static byte[] readBytes(InputStream stream, int count) throws IOException {
        byte[] bytes = new byte[count];
        int r = 0;
        while (r < count)
            r += stream.read(bytes, r, count - r);
        return bytes;
    }

    public static void tryStop(Thread t, String interruptErrorMessage) throws InterruptedIOException {
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            InterruptedIOException exception = new InterruptedIOException(interruptErrorMessage);
            exception.addSuppressed(e);
            throw exception;
        }
    }
}
