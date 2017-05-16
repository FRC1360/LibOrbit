package ca._1360.liborbit;

public final class OrbitPid extends OrbitPipelineComplexNodeBase {
    long lastTime;
    InputEndpoint epsilon;
    InputEndpoint kP;
    InputEndpoint kI;
    InputEndpoint kD;
    InputEndpoint kIInner;
    InputEndpoint kIOuter;
    InputEndpoint target;
    InputEndpoint input;
    OutputEndpoint error = new OutputEndpoint(0.0, false);
    OutputEndpoint output = new OutputEndpoint(0.0, false);

    public OrbitPid(double epsilon, double kP, double kI, double kD, double kIInner, double kIOuter, double target, double input) {
        super(true);
        this.epsilon = new InputEndpoint(epsilon);
        this.kP = new InputEndpoint(kP);
        this.kI = new InputEndpoint(kI);
        this.kD = new InputEndpoint(kD);
        this.kIInner = new InputEndpoint(kIInner);
        this.kIOuter = new InputEndpoint(kIOuter);
        this.target = new InputEndpoint(target);
        this.input = new InputEndpoint(input);
    }

    public InputEndpoint getEpsilon() {
        return epsilon;
    }

    public InputEndpoint getkP() {
        return kP;
    }

    public InputEndpoint getkI() {
        return kI;
    }

    public InputEndpoint getkD() {
        return kD;
    }

    public InputEndpoint getkIInner() {
        return kIInner;
    }

    public InputEndpoint getkIOuter() {
        return kIOuter;
    }

    public InputEndpoint getTarget() {
        return target;
    }

    public InputEndpoint getInput() {
        return input;
    }

    public OutputEndpoint getError() {
        return error;
    }

    public OutputEndpoint getOutput() {
        return output;
    }

    @Override
    protected void update() {

    }
}
