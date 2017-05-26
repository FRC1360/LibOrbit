package ca._1360.liborbit.pipeline;

import java.util.function.DoubleBinaryOperator;

public final class OrbitBinaryOperatorNode extends OrbitPipelineComplexNodeBase {
    private final InputEndpoint input1 = new InputEndpoint(0.0);
    private final InputEndpoint input2 = new InputEndpoint(0.0);
    private final OutputEndpoint output = new OutputEndpoint(0.0, true);
    private final DoubleBinaryOperator operator;

    public OrbitBinaryOperatorNode(boolean updateIfNoValues, DoubleBinaryOperator operator) {
        super(updateIfNoValues);
        this.operator = operator;
    }

    public OrbitPipelineInputEndpoint getInput1() {
        return input1;
    }

    public OrbitPipelineInputEndpoint getInput2() {
        return input2;
    }

    public OrbitPipelineOutputEndpoint getOutput() {
        return output;
    }

    @Override
    protected void update() {
        output.setValue(operator.applyAsDouble(input1.getValue(), input2.getValue()));
    }
}
