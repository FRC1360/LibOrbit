package ca._1360.liborbit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OrbitDirectedAcyclicGraph<T> implements Iterable<T> {
    private LinkedList<T> objects = new LinkedList<>();
    private ArrayList<Relationship> relationships = new ArrayList<>();

    @Override
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }

    public final Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public synchronized final void add(T object) {
        objects.add(object);
    }

    public synchronized final void add(T object, Iterable<T> from, Iterable<T> to) {
        objects.add(object);
        //TODO: run batch job to add relationships
    }

    public synchronized final void remove(T object) {
        objects.remove(object);
    }

    public synchronized final Relationship createRelationship(T from, T to) {
        Relationship relationship = createRelationshipCore(from, to);
        invalidate();
        return relationship;
    }

    public synchronized final void runBatch(BatchOperation[] operations) {

    }

    protected final Relationship createRelationshipCore(T from, T to) {
        Relationship relationship = new Relationship(from, to);
        relationships.add(relationship);
        return relationship;
    }

    protected final void invalidate() {

    }

    public final class Relationship {
        private T from;
        private T to;

        private Relationship(T from, T to) {
            this.from = from;
            this.to = to;
        }

        public T getFrom() {
            return from;
        }

        public T getTo() {
            return to;
        }

        public void remove() {
            synchronized (OrbitDirectedAcyclicGraph.this) {
                relationships.remove(this);
            }
        }
    }

    public final class BatchOperation {

    }
}
