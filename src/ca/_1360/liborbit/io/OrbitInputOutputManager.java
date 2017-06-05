/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitInputOutputManager.java
 * Provides access to the robot's primary I/O provider
 */

package ca._1360.liborbit.io;

public final class OrbitInputOutputManager {
    private static OrbitInputOutputProvider provider;

    // Private constructor prevents creation of instances
    private OrbitInputOutputManager() { }

    /**
     * @return The primary I/O provider
     */
    public static OrbitInputOutputProvider getProvider() {
        return provider;
    }

    /**
     * @param provider The new primary I/O provider
     */
    public static void setProvider(OrbitInputOutputProvider provider) {
        OrbitInputOutputManager.provider = provider;
    }
}
