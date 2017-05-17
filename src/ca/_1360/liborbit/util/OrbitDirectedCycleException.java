package ca._1360.liborbit.util;

public class OrbitDirectedCycleException extends Exception {
    OrbitDirectedCycleException() {
        super("The current relationship configuration results in a directed cycle");
    }
}
