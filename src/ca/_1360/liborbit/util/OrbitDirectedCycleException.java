/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitDirectedCycleException.java
 * Thrown by a DAG when an operation results in a cycle being formed
 */

package ca._1360.liborbit.util;

public class OrbitDirectedCycleException extends Exception {
    OrbitDirectedCycleException() {
        super("The current relationship configuration results in a directed cycle");
    }
}
