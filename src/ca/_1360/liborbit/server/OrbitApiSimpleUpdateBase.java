package ca._1360.liborbit.server;

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

    protected abstract byte[] getPayload();

    @Override
    public void write(IntFunction<DataOutputStream> channelSupplier) throws IOException {
        DataOutputStream stream = channelSupplier.apply(channel);
        if (payload == null)
            payload = getPayload();
        stream.write(payload);
    }
}
