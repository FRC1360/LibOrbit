package ca._1360.liborbit.pipeline;

public class OrbitPipelineCyclicDependencyException extends Exception {
    OrbitPipelineCyclicDependencyException(Throwable throwable) {
        super("A attempt was made to add a connection which would result in a cyclic dependency in the pipeline system", throwable);
    }
}
