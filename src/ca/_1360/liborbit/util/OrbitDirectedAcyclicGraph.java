package ca._1360.liborbit.util;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OrbitDirectedAcyclicGraph<T> implements Iterable<T> {
    private LinkedList<T> objects = new LinkedList<>();
    private final ArrayList<Relationship> relationships = new ArrayList<>();

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
        runBatch(Stream.concat(Stream.of(addOp(object)), Stream.concat(StreamSupport.stream(from.spliterator(), false).map(OrbitFunctionUtilities.specializeSecond(this::createRelationshipOp, object)), StreamSupport.stream(to.spliterator(), false).map(OrbitFunctionUtilities.specializeFirst(this::createRelationshipOp, object))))::iterator);
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
            operations.forEach(OrbitFunctionUtilities.combine(BatchOperation::run, done::push));
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
        relationships.forEach(OrbitFunctionUtilities.source((BiConsumer<ArrayList<T>, T>) ArrayList::add, OrbitFunctionUtilities.<T, Function<T, ArrayList<T>>, ArrayList<T>>specializeSecond(incomingMap::computeIfAbsent, e -> new ArrayList<>()).compose(Relationship::getTo), Relationship::getFrom));
        ArrayDeque<T> noEdges = new ArrayDeque<>();
        objects.stream().filter(((Predicate<T>) incomingMap::containsKey).negate()).forEach(noEdges::add);
        LinkedList<T> list = new LinkedList<>();
        OrbitMiscUtilities.stream(() -> noEdges.isEmpty() ? Optional.empty() : Optional.of(noEdges.pop())).forEach(object -> {
            list.add(object);
            ArrayList<T> toRemove = new ArrayList<>();
            for (Map.Entry<T, ArrayList<T>> o : incomingMap.entrySet())
                if (o.getValue().contains(object)) {
                    o.getValue().remove(object);
                    if (o.getValue().isEmpty()) {
                        toRemove.add(o.getKey());
                        noEdges.push(o.getKey());
                    }
                }
            toRemove.forEach(incomingMap::remove);
        });
        if (incomingMap.isEmpty())
            objects = list;
        else
            throw new OrbitDirectedCycleException();
    }

    public final BatchOperation addOp(T object) {
        return new BatchOperation(OrbitFunctionUtilities.specialize((Consumer<T>) objects::add, object), OrbitFunctionUtilities.specialize((Consumer<T>) objects::remove, object));
    }

    public final BatchOperation removeOp(T object) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        Stack<Relationship> removed = new Stack<>();
        return new BatchOperation(() -> {
        	int index = objects.indexOf(object);
        	pos.setValue(index);
        	if (index != -1) {
        		objects.remove(pos.getValue().intValue());
            	relationships.forEach(OrbitFunctionUtilities.conditional(removed::add, relationship -> relationship.getFrom() == object || relationship.getTo() == object));
            	removed.forEach(relationships::remove);
        	}
        }, () -> {
        	if (!pos.getValue().equals(-1)) {
        		objects.add(pos.getValue(), object);
            	relationships.addAll(removed);
        	}
        });
    }

    public final BatchOperation createRelationshipOp(T from, T to) {
        OrbitContainer<Relationship> rel = new OrbitContainer<>(null);
        return new BatchOperation(OrbitFunctionUtilities.specializeSupplier(rel::setValue, OrbitFunctionUtilities.specialize(this::createRelationshipCore, from, to)), OrbitFunctionUtilities.specializeSupplier((Consumer<Relationship>) relationships::remove, rel::getValue));
    }

    public final BatchOperation destroyRelationshipOp(Relationship relationship) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        return new BatchOperation(() -> {
            pos.setValue(relationships.indexOf(relationship));
            relationships.remove(pos.getValue().intValue());
        }, OrbitFunctionUtilities.specializeSupplier(OrbitFunctionUtilities.specializeSecond((BiConsumer<Integer, Relationship>) relationships::add, relationship), pos::getValue));
    }

    public final BatchOperation predicateOp(BiPredicate<Set<T>, Set<Relationship>> predicate) {
        return new BatchOperation(() -> {
            assert predicate.test(new HashSet<>(objects), new HashSet<>(relationships));
        }, () -> {});
    }

    public final class Relationship {
        private final T from;
        private final T to;

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
        private final Runnable operation, undoOp;

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
