package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;

import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;

import java.util.HashMap;
import java.util.OptionalDouble;

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

    public static void runBatch(BatchOperation[] operations) {

    }

    public static final class BatchOperation {

    }

    public static final class CyclicDependencyException extends Exception {
        private CyclicDependencyException(Throwable throwable) {
            super("A attempt was made to add a connection which would result in a cyclic dependency in the pipeline system", throwable);
        }
    }
}
