/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitApiSimpleUpdateBase.java
 * Base class for updates that dispatch a constant payload on a single channel
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
