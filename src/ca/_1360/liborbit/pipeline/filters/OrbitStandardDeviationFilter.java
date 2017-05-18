package ca._1360.liborbit.pipeline.filters;

import java.util.Arrays;
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
        values[index < 0 ? -index : index] = input;
        if (++index == values.length)
            index = 0;
        int limit = index < 0 ? values.length + index : values.length;
        double mean = Arrays.stream(values).limit(limit).average().orElse(0.0);
        double s = Math.sqrt(Arrays.stream(values).limit(limit).map(x -> (x - mean) * (x - mean)).average().orElse(0.0));
        return index < 0 ? OptionalDouble.empty() : Arrays.stream(values).filter(x -> Math.abs(x - mean) / s < nStdDev).average();
    }
}
