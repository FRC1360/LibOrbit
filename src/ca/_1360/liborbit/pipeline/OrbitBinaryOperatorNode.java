package ca._1360.liborbit.pipeline;

import java.util.function.DoubleBinaryOperator;

public final class OrbitBinaryOperatorNode extends OrbitPipelineComplexNodeBase {
    private InputEndpoint input1 = new InputEndpoint(0.0);
    private InputEndpoint input2 = new InputEndpoint(0.0);
    private OutputEndpoint output = new OutputEndpoint(0.0, true);
    private DoubleBinaryOperator operator;

    public OrbitBinaryOperatorNode(boolean updateIfNoValues, DoubleBinaryOperator operator) {
        super(updateIfNoValues);
        this.operator = operator;
    }

    public final OrbitPipelineInputEndpoint getInput1() {
        return input1;
    }

    public final OrbitPipelineInputEndpoint getInput2() {
        return input2;
    }

    public final OrbitPipelineOutputEndpoint getOutput() {
        return output;
    }

    @Override
    protected final void update() {
        output.setValue(operator.applyAsDouble(input1.getValue(), input2.getValue()));
    }
}
