/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineComplexNodeBase.java
 * Base class for fully locally dependent nodes with multiple inputs or outputs
 */

package ca._1360.liborbit.pipeline;

import java.util.OptionalDouble;

public abstract class OrbitPipelineComplexNodeBase {
    private final boolean updateIfNoValues;
    private int count;
    private int updated;
    private boolean values;

    /**
     * @param updateIfNoValues True if the node should recalculate even if no values are pushed to the inputs
     */
    protected OrbitPipelineComplexNodeBase(boolean updateIfNoValues) {
        this.updateIfNoValues = updateIfNoValues;
    }

    /**
     * To be called to recalculate the outputs
     */
    protected abstract void update();

    /**
     * If applicable, resets the update counters and recalculates
     */
    private void runUpdate() {
        if (updated == count) {
            if (updateIfNoValues || values)
                update();
            values = false;
            updated = 0;
        }
    }

    /**
     * An input endpoint that works with the complex node system
     */
    protected final class InputEndpoint implements OrbitPipelineInputEndpoint {
        private double value;

        /**
         * @param value The initial value of the input
         */
        public InputEndpoint(double value) {
            this.value = value;
            ++count;
        }

        /**
         * @return Gets the current value of the input
         */
        public double getValue() {
            return value;
        }

        /**
         * @param base A complex node
         * @return True if this endpoint belongs to base
         */
        private boolean isOf(OrbitPipelineComplexNodeBase base) {
            return base == OrbitPipelineComplexNodeBase.this;
        }

        /* (non-Javadoc)
         * @see java.util.function.DoubleConsumer#accept(double)
         */
        @Override
        public void accept(double v) {
        	// Update value, increment update counter, and run update if applicable
            value = v;
            values = true;
            ++updated;
            runUpdate();
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint#acceptNoInput()
         */
        @Override
        public void acceptNoInput() {
        	// Increment update counter and run update if applicable
            ++updated;
            runUpdate();
        }
    }

    /**
     * An output endpoint that works with the complex node system
     */
    protected final class OutputEndpoint implements OrbitPipelineOutputEndpoint {
        private double value;
        private final boolean alwaysProvideValue;
        private boolean newValue;

        /**
         * @param value The initial value of the output
         * @param alwaysProvideValue True if the output should retain its previous value if it is not updated by the recalculation
         */
        public OutputEndpoint(double value, boolean alwaysProvideValue) {
            this.value = value;
            this.alwaysProvideValue = alwaysProvideValue;
        }

        /**
         * @param value The new value of the output
         */
        public void setValue(double value) {
            this.value = value;
            newValue = true;
        }

        /* (non-Javadoc)
         * @see ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint#dependsOn(ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint)
         */
        @Override
        public boolean dependsOn(OrbitPipelineInputEndpoint inputEndpoint) {
        	// Check to see if the input is a complex node input that belongs to this complex node
            return inputEndpoint instanceof InputEndpoint && ((InputEndpoint)inputEndpoint).isOf(OrbitPipelineComplexNodeBase.this);
        }

        /* (non-Javadoc)
         * @see java.util.function.Supplier#get()
         */
        @Override
        public OptionalDouble get() {
        	// Determine whether or not value should be provided
            if (newValue || alwaysProvideValue) {
                newValue = false;
                return OptionalDouble.of(value);
            }
            return OptionalDouble.empty();
        }
    }
}
