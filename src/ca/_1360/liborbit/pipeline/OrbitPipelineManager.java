package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.util.OrbitDirectedAcyclicGraph;

import java.util.HashMap;
import java.util.OptionalDouble;

public final class OrbitPipelineManager {
    private static OrbitDirectedAcyclicGraph<OrbitPipelineConnection> connections = new OrbitDirectedAcyclicGraph<>();

    private OrbitPipelineManager() {}

    static void addConnection(OrbitPipelineConnection connection) {
        connections.add(connection, connections.stream().filter(c -> connection.getSource().dependsOn(c.getDestination()))::iterator, connections.stream().filter(c -> c.getSource().dependsOn(connection.getDestination()))::iterator);
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
}
