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

import java.util.function.DoubleBinaryOperator;

public final class OrbitBinaryOperatorNode extends OrbitPipelineComplexNodeBase {
    private final InputEndpoint input1 = new InputEndpoint(0.0);
    private final InputEndpoint input2 = new InputEndpoint(0.0);
    private final OutputEndpoint output = new OutputEndpoint(0.0, true);
    private final DoubleBinaryOperator operator;

    /**
     * @param updateIfNoValues True if the node should recalculate even if no values are pushed to inputs
     * @param operator The binary operator
     */
    public OrbitBinaryOperatorNode(boolean updateIfNoValues, DoubleBinaryOperator operator) {
        super(updateIfNoValues);
        this.operator = operator;
    }
    
    // Accessor methods for inputs and outputs

    public OrbitPipelineInputEndpoint getInput1() {
        return input1;
    }

    public OrbitPipelineInputEndpoint getInput2() {
        return input2;
    }

    public OrbitPipelineOutputEndpoint getOutput() {
        return output;
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase#update()
     */
    @Override
    protected void update() {
        output.setValue(operator.applyAsDouble(input1.getValue(), input2.getValue()));
    }
}
