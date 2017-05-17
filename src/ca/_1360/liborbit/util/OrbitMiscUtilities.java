package ca._1360.liborbit.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class OrbitMiscUtilities {
    private OrbitMiscUtilities() {
    }

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
}
