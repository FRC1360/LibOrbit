/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineInvalidConfigurationException.java
 * An exception thrown by the pipeline manager when the enabling of a connection results in an invalid state
 */

package ca._1360.liborbit.pipeline;

public class OrbitPipelineInvalidConfigurationException extends Exception {
    /**
     * @param throwable The cause of the exception
     */
    OrbitPipelineInvalidConfigurationException(Throwable throwable) {
        super("A attempt was made to enable a connection which would result in an invalid configuration of the pipeline system", throwable);
    }
}
