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
    private final HashMap<String, InputEndpoint> fields = new HashMap<>();

    public OrbitLogController() {
        super(true);
    }

    public synchronized void addOutput(OutputStream outputStream) {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
            for (String header : fields.keySet()) {
                output.writeByte(0);
                output.writeUTF(header);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        outputs.add(outputStream);
    }

    public synchronized OrbitPipelineInputEndpoint getField(String header) {
        return fields.computeIfAbsent(header, x -> {
            for (OutputStream outputStream : outputs) {
                try (DataOutputStream output = new DataOutputStream(outputStream)) {
                    output.writeByte(0);
                    output.writeUTF(header);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new InputEndpoint(0.0);
        });
    }

    @Override
    protected synchronized void update() {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            try (DataOutputStream output = new DataOutputStream(buffer)) {
                output.writeByte(1);
                output.writeLong(System.currentTimeMillis());
                for (InputEndpoint input : fields.values()) {
                    output.writeDouble(input.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = buffer.toByteArray();
            outputs.forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.handleException((OrbitExceptionalBiConsumer<OutputStream, byte[], IOException>) OutputStream::write, Throwable::printStackTrace), bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
