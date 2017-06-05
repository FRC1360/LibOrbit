/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitApiUpdate.java
 * Base interface for API updates
 */

package ca._1360.liborbit.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;

public interface OrbitApiUpdate {
    /**
     * To be called to write the update
     * @param stream A function to get the output stream for a given MCS channel on the current client
     * @throws IOException Thrown if something goes wrong in writing the update
     */
    void write(IntFunction<DataOutputStream> stream) throws IOException;
}
