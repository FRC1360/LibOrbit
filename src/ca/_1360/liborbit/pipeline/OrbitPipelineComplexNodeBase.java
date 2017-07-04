/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
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
