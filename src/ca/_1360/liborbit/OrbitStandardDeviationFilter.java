package ca._1360.liborbit;

import java.util.OptionalDouble;

public final class OrbitStandardDeviationFilter extends OrbitPipelineFilter {
    private double nStdDev;
    private int index;
    private double[] values;

    public OrbitStandardDeviationFilter(double nStdDev, int nValues) {
        this.nStdDev = nStdDev;
        index = -nValues;
        values = new double[nValues];
    }

    @Override
    protected OptionalDouble calculate(double input) {
        return OptionalDouble.empty();
    }
}
