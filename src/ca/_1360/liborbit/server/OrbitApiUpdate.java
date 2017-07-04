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

package ca._1360.liborbit.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;

public interface OrbitApiUpdate {
    /**
     * To be called to write the update
     * @param stream A function to get the output stream for a given MCS channel on the current client
     * @throws IOException Thrown if something goes wrong in writing the update
     */
    void write(IntFunction<DataOutputStream> stream) throws IOException;
}
