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

    /**
     * @param iterator An iterator to generate a stream from
     * @return a stream that retrieves data from the given iterator
     */
    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(((Iterable<T>) () -> iterator).spliterator(), false);
    }

    /**
     * @param supplier A supplier of elements
     * @return A stream that retrieves data from the given supplier, until it returns an empty optional
     */
    public static <T> Stream<T> stream(Supplier<Optional<T>> supplier) {
        return stream(new Iterator<T>() {
            T _next;

            @Override
            public boolean hasNext() {
            	// Ensure supplier is only accessed once per element
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
                T next = _next;
                _next = null;
                return next;
            }
        });
    }

    /**
     * @param streams Streams to be concatenated
     * @return A concatenated stream
     */
    @SafeVarargs
    public static <T> Stream<T> concat(Stream<? extends T>... streams) {
        return Arrays.stream(streams).map(OrbitFunctionUtilities.<Stream<? extends T>, Function<? super T, T>, Stream<T>>specializeSecond(Stream::map, Function.<T>identity())).reduce(Stream::concat).orElse(Stream.empty());
    }

    /**
     * @param stream The stream to read from
     * @param count The number of bytes to read
     * @return An array of the bytes that were read
     * @throws IOException Thrown by the stream
     */
    public static byte[] readBytes(InputStream stream, int count) throws IOException {
        byte[] bytes = new byte[count];
        int r = 0;
        while (r < count)
            r += stream.read(bytes, r, count - r);
        return bytes;
    }

    /**
     * Attempts to stop the given thread, throwing an exception if interrupted
     * @param t The thread to stop
     * @param interruptErrorMessage The error message to use if stopping fails
     * @throws InterruptedIOException Thrown if stopping fails
     */
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
