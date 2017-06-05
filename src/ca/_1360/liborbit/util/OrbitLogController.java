/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitLogCOntroller.java
 * Provides access to a binary data log via the pipeline system
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
     */
    public synchronized void addOutput(OutputStream outputStream) {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
        	// Write a command to add each field
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

    /**
     * @param header The field header
     * @return An input endpoint giving access to the named field
     */
    public synchronized OrbitPipelineInputEndpoint getField(String header) {
        return fields.computeIfAbsent(header, x -> {
        	// Write a command to each output stream to add the new field
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

    /* (non-Javadoc)
     * @see ca._1360.liborbit.pipeline.OrbitPipelineComplexNodeBase#update()
     */
    @Override
    protected synchronized void update() {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            try (DataOutputStream output = new DataOutputStream(buffer)) {
            	// Generate buffer
                output.writeByte(1);
                output.writeLong(System.currentTimeMillis());
                for (InputEndpoint input : fields.values()) {
                    output.writeDouble(input.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = buffer.toByteArray();
            // Write to each output
            outputs.forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.handleException((OrbitExceptionalBiConsumer<OutputStream, byte[], IOException>) OutputStream::write, Throwable::printStackTrace), bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
