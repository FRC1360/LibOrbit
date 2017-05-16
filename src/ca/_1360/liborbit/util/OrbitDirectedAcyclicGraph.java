package ca._1360.liborbit.util;

import ca._1360.liborbit.util.OrbitContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OrbitDirectedAcyclicGraph<T> implements Iterable<T> {
    private LinkedList<T> objects = new LinkedList<>();
    private ArrayList<Relationship> relationships = new ArrayList<>();

    @Override
    public final Iterator<T> iterator() {
        return objects.iterator();
    }

    public final Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public synchronized final void add(T object) {
        objects.add(object);
    }

    public synchronized final void add(T object, Iterable<T> from, Iterable<T> to) throws BatchOperationException {
        objects.add(object);
        runBatch(Stream.concat(Stream.of(addOp(object)), Stream.concat(StreamSupport.stream(from.spliterator(), false).map(o -> createRelationshipOp(o, object)), StreamSupport.stream(to.spliterator(), false).map(o -> createRelationshipOp(object, o))))::iterator);
    }

    public synchronized final void remove(T object) {
        objects.remove(object);
    }

    public synchronized final Relationship createRelationship(T from, T to) {
        Relationship relationship = createRelationshipCore(from, to);
        invalidate();
        return relationship;
    }

    public synchronized final void destroyRelationship(Relationship relationship) {
        relationships.remove(relationship);
        invalidate();
    }

    public synchronized final void runBatch(Iterable<BatchOperation> operations) throws BatchOperationException {
        Stack<BatchOperation> done = new Stack<>();
        try {
            for (BatchOperation operation : operations) {
                operation.run();
                done.push(operation);
            }
            invalidate();
        } catch (Throwable t) {
            while (!done.empty())
                done.pop().undo();
            throw new BatchOperationException(t);
        }
    }

    protected final Relationship createRelationshipCore(T from, T to) {
        Relationship relationship = new Relationship(from, to);
        relationships.add(relationship);
        return relationship;
    }

    protected final void invalidate() {

    }

    public final BatchOperation addOp(T object) {
        return new BatchOperation(() -> objects.add(object), () -> objects.remove(object));
    }

    public final BatchOperation removeOp(T object) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        return new BatchOperation(() -> {
            pos.setValue(objects.indexOf(object));
            objects.remove(pos.getValue().intValue());
        }, () -> objects.add(pos.getValue(), object));
    }

    public final BatchOperation createRelationshipOp(T from, T to) {
        OrbitContainer<Relationship> rel = new OrbitContainer<>(null);
        return new BatchOperation(() -> rel.setValue(createRelationshipCore(from, to)), () -> relationships.remove(rel.getValue()));
    }

    public final BatchOperation destroyRelationshipOp(Relationship relationship) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        return new BatchOperation(() -> {
            pos.setValue(relationships.indexOf(relationship));
            relationships.remove(pos.getValue().intValue());
        }, () -> relationships.add(pos.getValue(), relationship));
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
        private Runnable operation, undoOp;

        private BatchOperation(Runnable operation, Runnable undoOp) {
            this.operation = operation;
            this.undoOp = undoOp;
        }

        private void run() {
            operation.run();
        }

        private void undo() {
            undoOp.run();
        }
    }

    public static final class BatchOperationException extends Exception {
        private BatchOperationException(Throwable throwable) {
            super("Error during batch operation", throwable);
        }
    }
}
