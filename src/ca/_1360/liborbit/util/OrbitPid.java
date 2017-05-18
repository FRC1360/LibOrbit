package ca._1360.liborbit.util;

import ca._1360.liborbit.pipeline.*;
import ca._1360.liborbit.pipeline.filters.OrbitDerivationFilter;
import ca._1360.liborbit.pipeline.filters.OrbitIdentityFilter;
import ca._1360.liborbit.pipeline.filters.OrbitIntegrationFilter;
import ca._1360.liborbit.pipeline.filters.OrbitUnaryOperatorFilter;
import ca._1360.liborbit.statemachine.OrbitPipelineConnectionBasedStateMachine;
import ca._1360.liborbit.statemachine.OrbitStateMachineStates;

import java.util.HashMap;

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
    private OrbitPipelineConnectionBasedStateMachine<IntegralUseStates> integralControlStateMachine;

    public OrbitPid(double kP, double kI, double kD, double kIInner, double kIOuter, double target) {
        pScale.getInput2().accept(kP);
        iScale.getInput2().accept(kI);
        dScale.getInput2().accept(kD);
        inner.accept(kIInner);
        outer.accept(kIOuter);
        error.getInput2().accept(target);
        try {
            HashMap<IntegralUseStates, OrbitPipelineConnection[]> map = new HashMap<>();
            map.put(IntegralUseStates.ENABLED, new OrbitPipelineConnection[]{new OrbitPipelineConnection(addID.getOutput(), addMain.getInput2(), false)});
            map.put(IntegralUseStates.DISABLED, new OrbitPipelineConnection[]{new OrbitPipelineConnection(derivative, addMain.getInput2(), false)});
            integralControlStateMachine = new OrbitPipelineConnectionBasedStateMachine<>(IntegralUseStates.ENABLED, map);
            integralControlStateMachine.addStateChangeHandler((oldState, newState) -> {
                if (newState == IntegralUseStates.ENABLED)
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
                    OrbitPipelineManager.enableOp(new OrbitPipelineConnection(absError, new OrbitPipelineExportConnector<>(e -> e >= inner.get().orElse(0.0) && e <= outer.get().orElse(Double.POSITIVE_INFINITY) ? IntegralUseStates.ENABLED : IntegralUseStates.DISABLED, integralControlStateMachine::setState), false))
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

    private enum IntegralUseStates implements OrbitStateMachineStates {
        ENABLED, DISABLED;
    }
}
