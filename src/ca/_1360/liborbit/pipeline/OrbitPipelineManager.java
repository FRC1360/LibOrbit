package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrbitPipelineManager {
    private final static OrbitDirectedAcyclicGraph<OrbitPipelineConnection> connections = new OrbitDirectedAcyclicGraph<>();
    private final static ArrayList<Consumer<Throwable>> exceptionHandlers = new ArrayList<>();

    private OrbitPipelineManager() {}

    static synchronized void updateEnabled(OrbitPipelineConnection connection, boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        if (enabled)
            try {
                connections.runBatch(enableOp(connection).getOperations());
            } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
                throw new OrbitPipelineInvalidConfigurationException(e);
            }
        else
            connections.remove(connection);
    }

    public static synchronized void updateAll() {
    	try {
	        HashMap<OrbitPipelineOutputEndpoint, OptionalDouble> results = new HashMap<>();
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
    
    public static synchronized void addExceptionHandler(Consumer<Throwable> handler) {
    	exceptionHandlers.add(handler);
    }

    public static synchronized void runBatch(BatchOperation[] operations) throws OrbitPipelineInvalidConfigurationException {
        try {
            connections.runBatch(Arrays.stream(operations).map(BatchOperation::getOperations).flatMap(List::stream)::iterator);
        } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
            throw new OrbitPipelineInvalidConfigurationException(e);
        }
    }

    public static void disconnect(OrbitPipelineInputEndpoint endpoint) throws OrbitPipelineInvalidConfigurationException {
        runBatch(connections.stream().filter(c -> c.getDestination() == endpoint).map(OrbitPipelineManager::disableOp).toArray(BatchOperation[]::new));
    }

    public static void disconnect(OrbitPipelineOutputEndpoint endpoint) throws OrbitPipelineInvalidConfigurationException {
        runBatch(connections.stream().filter(c -> c.getSource() == endpoint).map(OrbitPipelineManager::disableOp).toArray(BatchOperation[]::new));
    }

    public static BatchOperation disconnectOp(OrbitPipelineInputEndpoint endpoint) {
        return new BatchOperation(connections.stream().filter(c -> c.getDestination() == endpoint).flatMap(((Function<OrbitPipelineConnection, BatchOperation>) OrbitPipelineManager::disableOp).andThen(BatchOperation::getOperations).andThen(List::stream)).collect(Collectors.toList()));
    }

    public static BatchOperation disconnectOp(OrbitPipelineOutputEndpoint endpoint) {
        return new BatchOperation(connections.stream().filter(c -> c.getSource() == endpoint).flatMap(((Function<OrbitPipelineConnection, BatchOperation>) OrbitPipelineManager::disableOp).andThen(BatchOperation::getOperations).andThen(List::stream)).collect(Collectors.toList()));
    }

    public static BatchOperation enableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Stream.concat(Stream.concat(Stream.of(connections.predicateOp((objects, relationships) -> connection.isEnabled() || objects.stream().noneMatch(o -> o.getDestination() == connection.getDestination()))), Stream.of(connections.addOp(connection))), Stream.concat(connections.stream().filter(((Function<OrbitPipelineConnection, OrbitPipelineInputEndpoint>) OrbitPipelineConnection::getDestination).andThen(connection.getSource()::dependsOn)::apply).map(c -> connections.createRelationshipOp(c, connection)), connections.stream().filter(((Function<OrbitPipelineConnection, OrbitPipelineOutputEndpoint>) OrbitPipelineConnection::getSource).andThen(OrbitFunctionUtilities.specializeSecond(OrbitPipelineOutputEndpoint::dependsOn, connection.getDestination())::test)::apply).map(c -> connections.createRelationshipOp(connection, c)))).collect(Collectors.toList()));
    }

    public static BatchOperation disableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Collections.singletonList(connections.removeOp(connection)));
    }

    public static BatchOperation miscOp(Runnable operation, Runnable undoOp) {
        return new BatchOperation(Collections.singletonList(connections.new BatchOperation(operation, undoOp)));
    }

    public static final class BatchOperation {
        private List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> operations;

        private BatchOperation(List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> operations) {
            this.operations = operations;
        }

        private List<OrbitDirectedAcyclicGraph<OrbitPipelineConnection>.BatchOperation> getOperations() {
            return operations;
        }
    }
}
