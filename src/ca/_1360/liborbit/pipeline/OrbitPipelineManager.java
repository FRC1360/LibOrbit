package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrbitPipelineManager {
    private static OrbitDirectedAcyclicGraph<OrbitPipelineConnection> connections = new OrbitDirectedAcyclicGraph<>();

    private OrbitPipelineManager() {}

    static void addConnection(OrbitPipelineConnection connection) throws CyclicDependencyException {
        try {
            connections.add(connection, connections.stream().filter(c -> connection.getSource().dependsOn(c.getDestination()))::iterator, connections.stream().filter(c -> c.getSource().dependsOn(connection.getDestination()))::iterator);
        } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
            throw new CyclicDependencyException(e);
        }
    }

    static void updateEnabled(OrbitPipelineConnection connection, boolean enabled) throws CyclicDependencyException {
        if (enabled)
            addConnection(connection);
        else
            connections.remove(connection);
    }

    public static void updateAll() {
        HashMap<OrbitPipelineOutputEndpoint, OptionalDouble> results = new HashMap<>();
        for (OrbitPipelineConnection connection : connections) {
            OptionalDouble value = results.computeIfAbsent(connection.getSource(), OrbitPipelineOutputEndpoint::get);
            if (value.isPresent())
                connection.getDestination().accept(value.getAsDouble());
            else
                connection.getDestination().acceptNoInput();
        }
    }

    public static void runBatch(BatchOperation[] operations) throws CyclicDependencyException {
        try {
            connections.runBatch(Arrays.stream(operations).map(BatchOperation::getOperations).flatMap(List::stream)::iterator);
        } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
            throw new CyclicDependencyException(e);
        }
    }

    public static BatchOperation enableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Stream.concat(Stream.of(connections.addOp(connection)), Stream.concat(connections.stream().filter(c -> connection.getSource().dependsOn(c.getDestination())).map(c -> connections.createRelationshipOp(c, connection)), connections.stream().filter(c -> c.getSource().dependsOn(connection.getDestination())).map(c -> connections.createRelationshipOp(connection, c)))).collect(Collectors.toList()));
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

    public static final class CyclicDependencyException extends Exception {
        private CyclicDependencyException(Throwable throwable) {
            super("A attempt was made to add a connection which would result in a cyclic dependency in the pipeline system", throwable);
        }
    }
}
