package ca._1360.liborbit.pipeline.filters;

public final class OrbitIdentityFilter extends OrbitSimplePipelineFilter {
    @Override
    protected double calculateCore(double input) {
        return input;
    }
}
