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

package ca._1360.liborbit.util;

import ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase;
import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.util.function.OrbitExceptionalBiConsumer;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public final class OrbitLogController extends OrbitPipelineComplexNodeBase {
    private final ArrayList<OutputStream> outputs = new ArrayList<>();
    // Map is used to prevent duplication
    private final HashMap<String, InputEndpoint> fields = new HashMap<>();

    public OrbitLogController() {
        super(true);
    }

    /**
     * @param outputStream The output stream to write to
     * @throws IOException Thrown if there is an issue writing the headers to the stream
     */
    public synchronized void addOutput(OutputStream outputStream) throws IOException {
        DataOutputStream output = new DataOutputStream(outputStream);
    	// Write a command to add each field
        for (String header : fields.keySet()) {
            output.writeByte(0);
            output.writeUTF(header);
        }
        outputs.add(outputStream);
    }

    /**
     * @param header The field header
     * @return An input endpoint giving access to the named field
     */
    public synchronized OrbitPipelineInputEndpoint getField(String header) {
        return fields.computeIfAbsent(header, x -> {
        	// Write a command to each output stream to add the new field
            for (OutputStream outputStream : outputs) {
                DataOutputStream output = new DataOutputStream(outputStream);
                try {
					output.writeByte(0);
					output.writeUTF(header);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            return new InputEndpoint(0.0);
        });
    }

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase#update()
     */
    @Override
    protected synchronized void update() {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
        	byte[] bytes;
            try (DataOutputStream output = new DataOutputStream(buffer)) {
            	// Generate buffer
                output.writeByte(1);
                output.writeLong(System.currentTimeMillis());
                for (InputEndpoint input : fields.values()) {
                    output.writeDouble(input.getValue());
                }
                bytes = buffer.toByteArray();
            }
            // Write to each output
            outputs.forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.handleException((OrbitExceptionalBiConsumer<OutputStream, byte[], IOException>) OutputStream::write, Throwable::printStackTrace), bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
