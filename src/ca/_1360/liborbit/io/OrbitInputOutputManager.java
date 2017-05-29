package ca._1360.liborbit.io;

public final class OrbitInputOutputManager {
    private static OrbitInputOutputProvider provider;

    private OrbitInputOutputManager() { }

    public static OrbitInputOutputProvider getProvider() {
        return provider;
    }

    public static void setProvider(OrbitInputOutputProvider provider) {
        OrbitInputOutputManager.provider = provider;
    }
}
