package ca._1360.liborbit.util;

import ca._1360.liborbit.pipeline.*;
import ca._1360.liborbit.pipeline.filters.OrbitDerivationFilter;
import ca._1360.liborbit.pipeline.filters.OrbitIdentityFilter;
import ca._1360.liborbit.pipeline.filters.OrbitIntegrationFilter;
import ca._1360.liborbit.pipeline.filters.OrbitUnaryOperatorFilter;
import ca._1360.liborbit.statemachine.OrbitSimpleStateMachine;
import ca._1360.liborbit.statemachine.OrbitStateMachineSimpleStates;

import java.util.Arrays;
import java.util.Collections;

public final class OrbitPid {
    private OrbitBinaryOperatorNode error = new OrbitBinaryOperatorNode(true, (x, y) -> x - y);
    private OrbitBinaryOperatorNode pScale = new OrbitBinaryOperatorNode(true, (x, y) -> x * y);
    private OrbitBinaryOperatorNode iScale = new OrbitBinaryOperatorNode(true, (x, y) -> x * y);
    private OrbitBinaryOperatorNode dScale = new OrbitBinaryOperatorNode(true, (x, y) -> x * y);
    private OrbitIdentityFilter inner = new OrbitIdentityFilter();
    private OrbitIdentityFilter outer = new OrbitIdentityFilter();
    private OrbitIntegrationFilter integral = new OrbitIntegrationFilter();
    private OrbitDerivationFilter derivative = new OrbitDerivationFilter();
    private OrbitBinaryOperatorNode addMain = new OrbitBinaryOperatorNode(true, (x, y) -> x + y);
    private OrbitBinaryOperatorNode addID = new OrbitBinaryOperatorNode(true, (x, y) -> x + y);
    private OrbitUnaryOperatorFilter absError = new OrbitUnaryOperatorFilter(Math::abs);
    private OrbitSimpleStateMachine<OrbitStateMachineSimpleStates> integralControlStateMachine;

    public OrbitPid(double kP, double kI, double kD, double kIInner, double kIOuter, double target) {
        pScale.getInput2().accept(kP);
        iScale.getInput2().accept(kI);
        dScale.getInput2().accept(kD);
        inner.accept(kIInner);
        outer.accept(kIOuter);
        error.getInput2().accept(target);
        try {
            OrbitStateMachineSimpleStates enabled = OrbitStateMachineSimpleStates.create(() -> {}, () -> {}, Collections.singletonList(new OrbitPipelineConnection(addID.getOutput(), addMain.getInput2(), false)), Collections.emptyList());
            OrbitStateMachineSimpleStates disabled = OrbitStateMachineSimpleStates.create(() -> {}, () -> {}, Collections.singletonList(new OrbitPipelineConnection(derivative, addMain.getInput2(), false)), Collections.emptyList());
            integralControlStateMachine = new OrbitSimpleStateMachine<>(Arrays.asList(enabled, disabled));
            integralControlStateMachine.addStateChangeHandler((oldState, newState) -> {
                if (newState == enabled)
                    integral.reset();
            });
            OrbitPipelineManager.runBatch(new OrbitPipelineManager.BatchOperation[]{
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(error.getOutput(), integral, true)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(error.getOutput(), derivative, true)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(error.getOutput(), pScale.getInput1(), true)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(integral, iScale.getInput1(), true)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(derivative, dScale.getInput1(), true)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(pScale.getOutput(), addMain.getInput1(), false)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(iScale.getOutput(), addID.getInput1(), false)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(dScale.getOutput(), addID.getInput2(), false)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(error.getOutput(), absError, false)),
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(absError, new OrbitPipelineExportConnector<>(e -> e >= inner.get().orElse(0.0) && e <= outer.get().orElse(Double.POSITIVE_INFINITY) ? enabled : disabled, integralControlStateMachine::setState), false))
            });
        } catch (OrbitPipelineInvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public OrbitPipelineInputEndpoint getkP() {
        return pScale.getInput2();
    }

    public OrbitPipelineInputEndpoint getkI() {
        return iScale.getInput2();
    }

    public OrbitPipelineInputEndpoint getkD() {
        return dScale.getInput2();
    }

    public OrbitPipelineInputEndpoint getkIInner() {
        return inner;
    }

    public OrbitPipelineInputEndpoint getkIOuter() {
        return outer;
    }

    public OrbitPipelineInputEndpoint getTarget() {
        return error.getInput2();
    }

    public OrbitPipelineInputEndpoint getInput() {
        return error.getInput1();
    }

    public OrbitPipelineOutputEndpoint getError() {
        return error.getOutput();
    }

    public OrbitPipelineOutputEndpoint getOutput() {
        return addMain.getOutput();
    }
}
