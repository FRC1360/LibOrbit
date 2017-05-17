package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;

public abstract class OrbitPipelineComplexNodeBase {
    private boolean updateIfNoValues;
    private int count;
    private int updated;
    private boolean values;

    protected OrbitPipelineComplexNodeBase(boolean updateIfNoValues) {
        this.updateIfNoValues = updateIfNoValues;
    }

    protected abstract void update();

    private void runUpdate() {
        if (updated == count) {
            if (updateIfNoValues || values)
                update();
            values = false;
            updated = 0;
        }
    }

    protected final class InputEndpoint implements OrbitPipelineInputEndpoint {
        private double value;

        public InputEndpoint(double value) {
            this.value = value;
            ++count;
        }

        public double getValue() {
            return value;
        }

        private boolean isOf(OrbitPipelineComplexNodeBase base) {
            return base == OrbitPipelineComplexNodeBase.this;
        }

        @Override
        public void accept(double v) {
            value = v;
            values = true;
            ++updated;
            runUpdate();
        }

        @Override
        public void acceptNoInput() {
            ++updated;
            runUpdate();
        }
    }

    protected final class OutputEndpoint implements OrbitPipelineOutputEndpoint {
        private double value;
        private boolean alwaysProvideValue;
        private boolean newValue;

        public OutputEndpoint(double value, boolean alwaysProvideValue) {
            this.value = value;
            this.alwaysProvideValue = alwaysProvideValue;
        }

        public void setValue(double value) {
            this.value = value;
            newValue = true;
        }

        @Override
        public boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
            return inputEndpoint instanceof InputEndpoint && ((InputEndpoint)inputEndpoint).isOf(OrbitPipelineComplexNodeBase.this);
        }

        @Override
        public OptionalDouble get() {
            if (newValue || alwaysProvideValue) {
                newValue = false;
                return OptionalDouble.of(value);
            }
            return OptionalDouble.empty();
        }
    }
}
