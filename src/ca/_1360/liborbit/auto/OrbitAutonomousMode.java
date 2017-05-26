package ca._1360.liborbit.auto;

import java.util.function.Consumer;

public interface OrbitAutonomousMode<T> {
    void add(Consumer<OrbitAutonomousCommand<T>> consumer);
}
