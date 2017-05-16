package ca._1360.liborbit.pipeline.filters;

public final class OrbitScaleFilter extends OrbitSimplePipelineFilter {
    private double scale;

    public OrbitScaleFilter(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    protected double calculateCore(double input) {
        return scale * input;
    }
}
