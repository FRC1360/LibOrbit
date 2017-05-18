package ca._1360.liborbit.pipeline;

public class OrbitPipelineInvalidConfigurationException extends Exception {
    OrbitPipelineInvalidConfigurationException(Throwable throwable) {
        super("A attempt was made to enable a connection which would result in an invalid configuration of the pipeline system", throwable);
    }
}
