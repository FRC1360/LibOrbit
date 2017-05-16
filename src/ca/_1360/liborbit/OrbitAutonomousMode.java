package ca._1360.liborbit;

import java.util.function.Consumer;

public abstract class OrbitAutonomousMode<T> {
    public abstract void add(Consumer<OrbitAutonomousCommand<T>> consumer);
}
