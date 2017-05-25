package ca._1360.liborbit.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;

public interface OrbitApiUpdate {
    void write(IntFunction<DataOutputStream> stream) throws IOException;
}
