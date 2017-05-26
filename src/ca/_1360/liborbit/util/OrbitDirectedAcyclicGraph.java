package ca._1360.liborbit.util;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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
        relationships.removeIf(relationship -> relationship.getTo() == object || relationship.getFrom() == object);
    }

    public synchronized final Relationship createRelationship(T from, T to) throws OrbitDirectedCycleException {
        boolean success = false;
        Relationship relationship = createRelationshipCore(from, to);
        try {
            invalidate();
            success = true;
            return relationship;
        } finally {
            if (!success)
                destroyRelationship(relationship);
        }
    }

    public synchronized final void destroyRelationship(Relationship relationship) {
        relationships.remove(relationship);
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

    protected final void invalidate() throws OrbitDirectedCycleException {
        HashMap<T, ArrayList<T>> incomingMap = new HashMap<>();
        for (Relationship relationship : relationships)
            incomingMap.computeIfAbsent(relationship.getTo(), e -> new ArrayList<>()).add(relationship.getFrom());
        ArrayDeque<T> noEdges = new ArrayDeque<>();
        objects.stream().filter(((Predicate<T>) incomingMap::containsKey).negate()).forEach(noEdges::add);
        LinkedList<T> list = new LinkedList<>();
        OrbitMiscUtilities.stream(() -> noEdges.isEmpty() ? Optional.empty() : Optional.of(noEdges.pop())).forEach(object -> {
            list.add(object);
            incomingMap.entrySet().stream().filter(o -> o.getValue().contains(object)).forEach(o -> {
                o.getValue().remove(object);
                if (o.getValue().isEmpty()) {
                    incomingMap.remove(o.getKey());
                    noEdges.push(o.getKey());
                }
            });
        });
        if (incomingMap.isEmpty())
            objects = list;
        else
            throw new OrbitDirectedCycleException();
    }

    public final BatchOperation addOp(T object) {
        return new BatchOperation(() -> objects.add(object), () -> objects.remove(object));
    }

    public final BatchOperation removeOp(T object) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        Stack<Relationship> removed = new Stack<>();
        return new BatchOperation(() -> {
            pos.setValue(objects.indexOf(object));
            objects.remove(pos.getValue().intValue());
            for (Relationship relationship : relationships)
                if (relationship.getFrom() == object || relationship.getTo() == object) {
                    relationships.remove(relationship);
                    removed.add(relationship);
                }
        }, () -> {
            objects.add(pos.getValue(), object);
            relationships.addAll(removed);
        });
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

    public final BatchOperation predicateOp(BiPredicate<Set<T>, Set<Relationship>> predicate) {
        return new BatchOperation(() -> {
            assert predicate.test(new HashSet<>(objects), new HashSet<>(relationships));
        }, () -> {});
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

        public BatchOperation(Runnable operation, Runnable undoOp) {
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
