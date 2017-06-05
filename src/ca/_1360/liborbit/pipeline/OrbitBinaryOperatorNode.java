/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitBinaryOperatorNode.java
 * A pipeline node that applies a binary operator to two inputs to produce an output
 */

package ca._1360.liborbit.pipeline;

import java.util.function.DoubleBinaryOperator;

public final class OrbitBinaryOperatorNode extends OrbitPipelineComplexNodeBase {
    private final InputEndpoint input1 = new InputEndpoint(0.0);
    private final InputEndpoint input2 = new InputEndpoint(0.0);
    private final OutputEndpoint output = new OutputEndpoint(0.0, true);
    private final DoubleBinaryOperator operator;

    /**
     * @param updateIfNoValues True if the node should recalculate even if no values are pushed to inputs
     * @param operator The binary operator
     */
    public OrbitBinaryOperatorNode(boolean updateIfNoValues, DoubleBinaryOperator operator) {
        super(updateIfNoValues);
        this.operator = operator;
    }
    
    // Accessor methods for inputs and outputs

    public OrbitPipelineInputEndpoint getInput1() {
        return input1;
    }

    public OrbitPipelineInputEndpoint getInput2() {
        return input2;
    }

    public OrbitPipelineOutputEndpoint getOutput() {
        return output;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase#update()
     */
    @Override
    protected void update() {
        output.setValue(operator.applyAsDouble(input1.getValue(), input2.getValue()));
    }
}
