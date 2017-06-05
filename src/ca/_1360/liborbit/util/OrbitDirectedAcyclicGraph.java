/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitDirectedAcyclicGraph.java
 * A complex directed graph that holds its elements in an order such that all relationships point forward
 */

package ca._1360.liborbit.util;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @param <T> The element type
 */
public class OrbitDirectedAcyclicGraph<T> implements Iterable<T> {
    private LinkedList<T> objects = new LinkedList<>();
    private final ArrayList<Relationship> relationships = new ArrayList<>();

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public final Iterator<T> iterator() {
        return objects.iterator();
    }

    /**
     * @return A stream of the DAG's contents
     */
    public final Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Adds an element to the DAG without any relationships
     * @param object The element to add
     */
    public synchronized final void add(T object) {
        objects.add(object);
    }

    /**
     * Adds an element to the DAG with the given relationships
     * @param object The element to add
     * @param from The elements from which the new element should have a relationship
     * @param to The elements to which the new element should have a relationship
     * @throws BatchOperationException Thrown if the new relationships cause a cycle to be formed
     */
    public synchronized final void add(T object, Iterable<T> from, Iterable<T> to) throws BatchOperationException {
        objects.add(object);
        runBatch(Stream.concat(Stream.of(addOp(object)), Stream.concat(StreamSupport.stream(from.spliterator(), false).map(OrbitFunctionUtilities.specializeSecond(this::createRelationshipOp, object)), StreamSupport.stream(to.spliterator(), false).map(OrbitFunctionUtilities.specializeFirst(this::createRelationshipOp, object))))::iterator);
    }

    /**
     * Removes an element and all its relationships
     * @param object The element to remove
     */
    public synchronized final void remove(T object) {
        objects.remove(object);
        relationships.removeIf(relationship -> relationship.getTo() == object || relationship.getFrom() == object);
    }

    /**
     * Creates a relationship
     * @param from The element from which the relationship should come
     * @param to The element to which the relationship should go
     * @return The relationship
     * @throws OrbitDirectedCycleException Thrown if the new relationship causes a cycle to be formed
     */
    public synchronized final Relationship createRelationship(T from, T to) throws OrbitDirectedCycleException {
        boolean success = false;
        Relationship relationship = createRelationshipCore(from, to);
        // Ensure validity
        try {
            invalidate();
            success = true;
            // Find the matching relationship if it already exists
            return relationship == null ? relationships.stream().filter(r -> r.from == from && r.to == to).findAny().get() : relationship;
        } finally {
        	// Remove the new relationship if unsuccessful
            if (!success)
                destroyRelationship(relationship);
        }
    }

    /**
     * Destroys a relationship
     * @param relationship The relationship to destroy
     */
    public synchronized final void destroyRelationship(Relationship relationship) {
        relationships.remove(relationship);
    }

    /**
     * Performs a series of operations, rolling back changes if an error occurs or if the end result causes a cycle to be formed
     * @param operations The operations to perform
     * @throws BatchOperationException Thrown if an error occurs or if the end result causes a cycle to be formed
     */
    public synchronized final void runBatch(Iterable<BatchOperation> operations) throws BatchOperationException {
        Stack<BatchOperation> done = new Stack<>();
        try {
        	// Run operations and push them onto the done stack
            operations.forEach(OrbitFunctionUtilities.combine(BatchOperation::run, done::push));
            invalidate();
        } catch (Throwable t) {
        	// Undo all successful operations if not successful
            while (!done.empty())
                done.pop().undo();
            throw new BatchOperationException(t);
        }
    }

    /**
     * Core function to create a new relationship
     * @param from The element from which the relationship should come
     * @param to The element to which the relationship should go
     * @return The new relationship, or null if it already exists
     */
    protected final Relationship createRelationshipCore(T from, T to) {
    	if (relationships.stream().anyMatch(r -> r.from == from && r.to == to))
    		return null;
        Relationship relationship = new Relationship(from, to);
        relationships.add(relationship);
        return relationship;
    }

    /**
     * Places the elements in a valid order, using Kahn's algorithm for topological sorting
     * @throws OrbitDirectedCycleException Thrown if a cycle exists
     */
    protected final void invalidate() throws OrbitDirectedCycleException {
    	// Map all elements to sources of incoming relationships
        HashMap<T, ArrayList<T>> incomingMap = new HashMap<>();
        relationships.forEach(OrbitFunctionUtilities.source((BiConsumer<ArrayList<T>, T>) ArrayList::add, OrbitFunctionUtilities.<T, Function<T, ArrayList<T>>, ArrayList<T>>specializeSecond(incomingMap::computeIfAbsent, e -> new ArrayList<>()).compose(Relationship::getTo), Relationship::getFrom));
        // Create queue of maps without incoming relationships
        ArrayDeque<T> noEdges = new ArrayDeque<>();
        objects.stream().filter(((Predicate<T>) incomingMap::containsKey).negate()).forEach(noEdges::add);
        // Process elements
        LinkedList<T> list = new LinkedList<>();
        OrbitMiscUtilities.stream(() -> noEdges.isEmpty() ? Optional.empty() : Optional.of(noEdges.pop())).forEach(object -> {
            list.add(object);
            ArrayList<T> toRemove = new ArrayList<>();
            // Remove element and populate noEdges with newly valid elements
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
        // Check if sorting was successful
        if (incomingMap.isEmpty())
            objects = list;
        else
            throw new OrbitDirectedCycleException();
    }

    /**
     * @param object The element to add
     * @return An operation to add the given element
     */
    public final BatchOperation addOp(T object) {
    	OrbitContainer<Boolean> isNew = new OrbitContainer<Boolean>(true);
        return new BatchOperation(() -> {
        	if (objects.contains(object))
        		isNew.setValue(false);
        	else
        		objects.add(object);
        }, OrbitFunctionUtilities.conditional(OrbitFunctionUtilities.specialize((Consumer<T>) objects::remove, object), isNew::getValue));
    }

    /**
     * @param object The element to remove
     * @return An operation to remove the given element
     */
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

    /**
     * @param from The element from which the relationship should come
     * @param to The element to which the relationship should go
     * @return An operation to create the given relationship
     */
    public final BatchOperation createRelationshipOp(T from, T to) {
        OrbitContainer<Relationship> rel = new OrbitContainer<>(null);
        return new BatchOperation(OrbitFunctionUtilities.specializeSupplier(rel::setValue, OrbitFunctionUtilities.specialize(this::createRelationshipCore, from, to)), OrbitFunctionUtilities.specializeSupplier((Consumer<Relationship>) relationships::remove, rel::getValue));
    }

    /**
     * @param relationship The relationship to destroy
     * @return An operation to destroy the given relationship
     */
    public final BatchOperation destroyRelationshipOp(Relationship relationship) {
        OrbitContainer<Integer> pos = new OrbitContainer<>(null);
        return new BatchOperation(() -> {
            pos.setValue(relationships.indexOf(relationship));
            relationships.remove(pos.getValue().intValue());
        }, OrbitFunctionUtilities.specializeSupplier(OrbitFunctionUtilities.specializeSecond((BiConsumer<Integer, Relationship>) relationships::add, relationship), pos::getValue));
    }

    /**
     * @param predicate A predicate on the sets of elements and relationships
     * @return An operation to assert the given predicate
     */
    public final BatchOperation predicateOp(BiPredicate<Set<T>, Set<Relationship>> predicate) {
        return new BatchOperation(() -> {
            assert predicate.test(new HashSet<>(objects), new HashSet<>(relationships));
        }, () -> {});
    }

    /**
     * A relationship in the DAG
     */
    public final class Relationship {
        private final T from;
        private final T to;

        /**
         * @param from The element from which the relationship comes
         * @param to The element to which the relationship goes
         */
        private Relationship(T from, T to) {
            this.from = from;
            this.to = to;
        }

        /**
         * @return The element from which the relationship comes
         */
        public T getFrom() {
            return from;
        }

        /**
         * @return The element to which the relationship goes
         */
        public T getTo() {
            return to;
        }

        /**
         * Removes the relationship from the DAG
         */
        public void remove() {
            synchronized (OrbitDirectedAcyclicGraph.this) {
                relationships.remove(this);
            }
        }
    }

    /**
     * A batch operation
     */
    public final class BatchOperation {
        private final Runnable operation, undoOp;

        /**
         * @param operation The function to perform the operation
         * @param undoOp The function to revert the operation
         */
        public BatchOperation(Runnable operation, Runnable undoOp) {
            this.operation = operation;
            this.undoOp = undoOp;
        }

        /**
         * Performs the operation
         */
        private void run() {
            operation.run();
        }

        /**
         * Reverts the operation
         */
        private void undo() {
            undoOp.run();
        }
    }

    /**
     * An exception thrown when the batch set fails
     */
    public static final class BatchOperationException extends Exception {
        /**
         * @param throwable The cause of the exception
         */
        private BatchOperationException(Throwable throwable) {
            super("Error during batch operation", throwable);
        }
    }
}
