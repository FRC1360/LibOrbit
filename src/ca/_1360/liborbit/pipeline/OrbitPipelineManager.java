package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrbitPipelineManager {
    private static OrbitDirectedAcyclicGraph<OrbitPipelineConnection> connections = new OrbitDirectedAcyclicGraph<>();

    private OrbitPipelineManager() {}

    static void updateEnabled(OrbitPipelineConnection connection, boolean enabled) throws OrbitPipelineInvalidConfigurationException {
        if (enabled)
            try {
                connections.runBatch(enableOp(connection).getOperations());
            } catch (OrbitDirectedAcyclicGraph.BatchOperationException e) {
                throw new OrbitPipelineInvalidConfigurationException(e);
            }
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

    public static void runBatch(BatchOperation[] operations) throws OrbitPipelineInvalidConfigurationException {
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
        return new BatchOperation(connections.stream().filter(c -> c.getDestination() == endpoint).map(OrbitPipelineManager::disableOp).map(BatchOperation::getOperations).flatMap(List::stream).collect(Collectors.toList()));
    }

    public static BatchOperation disconnectOp(OrbitPipelineOutputEndpoint endpoint) {
        return new BatchOperation(connections.stream().filter(c -> c.getSource() == endpoint).map(OrbitPipelineManager::disableOp).map(BatchOperation::getOperations).flatMap(List::stream).collect(Collectors.toList()));
    }

    public static BatchOperation enableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Stream.concat(Stream.concat(Stream.of(connections.predicateOp((objects, relationships) -> objects.stream().noneMatch(o -> o.getDestination() == connection.getDestination()))), Stream.of(connections.addOp(connection))), Stream.concat(connections.stream().filter(c -> connection.getSource().dependsOn(c.getDestination())).map(c -> connections.createRelationshipOp(c, connection)), connections.stream().filter(c -> c.getSource().dependsOn(connection.getDestination())).map(c -> connections.createRelationshipOp(connection, c)))).collect(Collectors.toList()));
    }

    public static BatchOperation disableOp(OrbitPipelineConnection connection) {
        return new BatchOperation(Collections.singletonList(connections.removeOp(connection)));
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
