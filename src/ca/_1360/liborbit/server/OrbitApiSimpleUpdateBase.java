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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;

public abstract class OrbitApiSimpleUpdateBase implements OrbitApiUpdate {
    private final int channel;
    private byte[] payload;

    /**
     * @param channel The MCS channel on which to dispatch the update 
     */
    public OrbitApiSimpleUpdateBase(int channel) {
        this.channel = channel;
    }

    /**
     * @return The MCS channel on which to dispatch the update
     */
    public int getChannel() {
        return channel;
    }

    /**
     * To be called once to generate the payload
     * @param output A stream-wrapped buffer to write the payload to
     * @throws IOException Thrown if something goes wrong in writing the payload
     */
    protected abstract void writePayload(DataOutputStream output) throws IOException;

    /* (non-Javadoc)
     * @see ca._1360.liborbit.server.OrbitApiUpdate#write(java.util.function.IntFunction)
     */
    @Override
    public final void write(IntFunction<DataOutputStream> channelSupplier) throws IOException {
    	// Save the payload to only generate once
        if (payload == null) {
        	try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
        		try (DataOutputStream data = new DataOutputStream(output)) {
        			writePayload(data);
        		}
        		payload = output.toByteArray();
        	}
        }
        channelSupplier.apply(channel).write(payload);
    }
}
