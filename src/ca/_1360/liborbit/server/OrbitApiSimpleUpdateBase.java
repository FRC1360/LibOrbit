package ca._1360.liborbit.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;

public abstract class OrbitApiSimpleUpdateBase implements OrbitApiUpdate {
    private final int channel;
    private byte[] payload;

    public OrbitApiSimpleUpdateBase(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }

    protected abstract void writePayload(DataOutputStream output) throws IOException;

    @Override
    public final void write(IntFunction<DataOutputStream> channelSupplier) throws IOException {
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
