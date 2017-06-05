/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineManager.java
 * Manages the pipeline system and changes thereto
 */

package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrbitPipelineManager {
	// The system is stored as a DAG of connections, with dependencies between connections as relationships
    private final static OrbitDirectedAcyclicGraph<OrbitPipelineConnection> connections = new OrbitDirectedAcyclicGraph<>();
    private final static ArrayList<Consumer<Throwable>> exceptionHandlers = new ArrayList<>();

    // Private constructor prevents creation of instances
    private OrbitPipelineManager() {}

    /**
     * Updates the enabled state of a connection
     * @param connection The connection of which to update the state
     * @param enabled True if the connection is being enabled; false if it is being disabled
     * @throws OrbitPipelineInvalidConfigurationException Thrown enabling is attempted and the connection is invalid
     */
    static synchronized void updateEnabled(OrbitPipelineConnection connection, boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        if (enabled)
            try {
            	// Adding a connection requires multiple operations on the DAG model
                connections.runBatch(enableOp(connection).getOperations());
            } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
                throw new OrbitPipelineInvalidConfigurationException(e);
            }
        else
            connections.remove(connection);
    }

    /**
     * Updates all connections
     */
    public static synchronized void updateAll() {
    	try {
    		// Values are stored in a map to prevent querying the same output twice
	        HashMap<OrbitPipelineOutputEndpoint, OptionalDouble> results = new HashMap<>();
	        // A list of update task is generated and then executed, to prevent concurrent modification issues 
	        List<Runnable> todo = connections.stream().<Runnable>map(connection -> () -> {
	            OptionalDouble value = results.computeIfAbsent(connection.getSource(), OrbitPipelineOutputEndpoint::get);
	            if (value.isPresent())
	                connection.getDestination().accept(value.getAsDouble());
	            else
	                connection.getDestination().acceptNoInput();
	        }).collect(Collectors.toList());
	        todo.forEach(Runnable::run);
    	} catch (Throwable t) {
    		exceptionHandlers.forEach(handler -> handler.accept(t));
    	}
    }
    
    /**
     * @param handler A handler to add to the exception handlers list
     */
    public static synchronized void addExceptionHandler(Consumer<Throwable> handler) {
    	exceptionHandlers.add(handler);
    }

    /**
     * Performs a set of operations, rolling back changes if an error occurs or if the final state is invalid
     * @param operations The operations to perform
     * @throws OrbitPipelineInvalidConfigurationException Thrown if the batch operation is unsuccessful
     */
    public static synchronized void runBatch(BatchOperation[] operations) throws OrbitPipelineInvalidConfigurationException {
        try {
        	// Condense into a set of DAG operations, then send to DAG
            connections.runBatch(Arrays.stream(operations).map(BatchOperation::getOperations).flatMap(List::stream)::iterator);
        } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
            throw new OrbitPipelineInvalidConfigurationException(e);
        }
    }

    /**
     * Disables all connections to a given input endpoint
     * @param endpoint The endpoint to disconnect
     * @throws OrbitPipelineInvalidConfigurationException Should never be thrown
     */
    public static void disconnect(OrbitPipelineInputEndpoint endpoint) throws OrbitPipelineInvalidConfigurationException {
        runBatch(connections.stream().filter(c -> c.getDestination() == endpoint).map(OrbitPipelineManager::disableOp).toArray(BatchOperation[]::new));
    }

    /**
     * Disables all connections to a given output endpoint
     * @param endpoint The endpoint to disconnect 
     * @throws OrbitPipelineInvalidConfigurationException Should never be thrown
     */
    public static void disconnect(OrbitPipelineOutputEndpoint endpoint) throws OrbitPipelineInvalidConfigurationException {
        runBatch(connections.stream().filter(c -> c.getSource() == endpoint).map(OrbitPipelineManager::disableOp).toArray(BatchOperation[]::new));
    }

    /**
     * @param endpoint The endpoint to disconnect
     * @return An operation to disconnect the given input endpoint
     */
    public static BatchOperation disconnectOp(OrbitPipelineInputEndpoint endpoint) {
        return new BatchOperation(connections.stream().filter(c -> c.getDestination() == endpoint).flatMap(((Function<OrbitPipelineConnection, BatchOperation>) OrbitPipelineManager::disableOp).andThen(BatchOperation::getOperations).andThen(List::stream)).collect(Collectors.toList()));
    }

    /**
     * @param endpoint The endpoint to disconnect
     * @return An operation to disconnect the given output endpoint
     */
    public static BatchOperation disconnectOp(OrbitPipelineOutputEndpoint endpoint) {
        return new BatchOperation(connections.stream().filter(c -> c.getSource() == endpoint).flatMap(((Function<OrbitPipelineConnection, BatchOperation>) OrbitPipelineManager::disableOp).andThen(BatchOperation::getOperations).andThen(List::stream)).collect(Collectors.toList()));
    }

    /**
     * @param connection The connection to enable
     * @return An operation to enable the given connection
     */
    public static BatchOperation enableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Stream.concat(Stream.concat(Stream.of(connections.predicateOp((objects, relationships) -> connection.isEnabled() || objects.stream().noneMatch(o -> o.getDestination() == connection.getDestination()))), Stream.of(connections.addOp(connection))), Stream.concat(connections.stream().filter(((Function<OrbitPipelineConnection, OrbitPipelineInputEndpoint>) OrbitPipelineConnection::getDestination).andThen(connection.getSource()::dependsOn)::apply).map(c -> connections.createRelationshipOp(c, connection)), connections.stream().filter(((Function<OrbitPipelineConnection, OrbitPipelineOutputEndpoint>) OrbitPipelineConnection::getSource).andThen(OrbitFunctionUtilities.specializeSecond(OrbitPipelineOutputEndpoint::dependsOn, connection.getDestination())::test)::apply).map(c -> connections.createRelationshipOp(connection, c)))).collect(Collectors.toList()));
    }

    /**
     * @param connection The connection to disable
     * @return An operation to disable the given connection
     */
    public static BatchOperation disableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Collections.singletonList(connections.removeOp(connection)));
    }

    /**
     * @param operation The function to execute as the operation
     * @param undoOp The function to execute to undo the operation
     * @return An operation that executes a miscellaneous function
     */
    public static BatchOperation miscOp(Runnable operation, Runnable undoOp) {
        return new BatchOperation(Collections.singletonList(connections.new BatchOperation(operation, undoOp)));
    }

    /**
     * A batch operation
     */
    public static final class BatchOperation {
        private List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> operations;

        /**
         * @param operations The DAG operations that this operation consists of
         */
        private BatchOperation(List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> operations) {
            this.operations = operations;
        }

        /**
         * @return The DAG operation that this operation consists of
         */
        private List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> getOperations() {
            return operations;
        }
    }
}
