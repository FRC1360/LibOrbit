package ca._1360.liborbit;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public final class OrbitLogController extends OrbitPipelineComplexNodeBase {
    private PrintStream[] outputs;
    HashMap<String, InputEndpoint> fields = new HashMap<>();

    public OrbitLogController(OutputStream... outputs) {
        super(true);
        this.outputs = Arrays.stream(outputs).map(PrintStream::new).toArray(PrintStream[]::new);
    }

    public OrbitPipelineInputEndpoint getField(String header) {
        return fields.computeIfAbsent(header, x -> new InputEndpoint(0.0));
    }

    @Override
    protected void update() {
        String line = String.format("[%d]\t%s", System.currentTimeMillis(), fields.entrySet().stream().map(f -> f.getKey() + ": " + f.getValue().getValue()).reduce((x, y) -> x + ", " + y));
        for (PrintStream output : outputs)
            output.println(line);
    }
}
