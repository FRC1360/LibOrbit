package ca._1360.liborbit.server;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.util.OrbitMiscUtilities;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.OptionalDouble;
import java.util.concurrent.LinkedBlockingQueue;

public final class OrbitApiServer implements Closeable {
    private ServerSocket serverSocket;
    private HashMap<String, InputEndpoint> inputs = new HashMap<>();
    private HashMap<String, OutputEndpoint> outputs = new HashMap<>();
    private LinkedBlockingQueue<OrbitApiUpdate> updateQueue = new LinkedBlockingQueue<>();
    private ArrayList<OrbitApiClient> connections = new ArrayList<>();
    private Thread acceptThread;
    private Thread processThread;

    public OrbitApiServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        acceptThread = new Thread(this::runAccept);
        acceptThread.start();
        processThread = new Thread(this::runProcess);
        processThread.start();
    }

    public synchronized OrbitPipelineInputEndpoint getInput(String label) {
        return inputs.computeIfAbsent(label, l -> new InputEndpoint());
    }

    public synchronized OrbitPipelineOutputEndpoint getOutput(String label) {
        return outputs.computeIfAbsent(label, l -> new OutputEndpoint());
    }

    public synchronized void removeInput(String label) throws OrbitPipelineInvalidConfigurationException {
        InputEndpoint endpoint = inputs.remove(label);
        if (endpoint != null) {
            endpoint.stop();
            OrbitPipelineManager.disconnect(endpoint);
        }
    }

    public synchronized void removeOutput(String label) throws OrbitPipelineInvalidConfigurationException {
        OutputEndpoint endpoint = outputs.remove(label);
        if (endpoint != null)
            OrbitPipelineManager.disconnect(endpoint);
    }

    public synchronized void push(OrbitApiUpdate update) {
        updateQueue.offer(update);
    }

    public synchronized OrbitApiClient[] getClients() {
        return connections.toArray(new OrbitApiClient[0]);
    }

    private void runAccept() {
        while (true)
            try {
                Socket socket = serverSocket.accept();
                OrbitApiClient client = new OrbitApiClient(this, socket);
                synchronized (this) {
                    connections.add(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void runProcess() {
        while (true) {
            OrbitApiUpdate update = null;
            do try {
                update = updateQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (update == null);
            connections.forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.wrapException(OrbitApiClient::push, UncheckedIOException::new), update));
        }
    }

    synchronized void disconnect(OrbitApiClient client) {
        connections.remove(client);
    }

    @Override
    public void close() throws IOException {
        OrbitMiscUtilities.tryStop(acceptThread, "Failed to stop the connection accepting thread");
        OrbitMiscUtilities.tryStop(processThread, "Failed to stop the update processing thread");
        serverSocket.close();
        connections.forEach(OrbitFunctionUtilities.wrapException(Closeable::close, UncheckedIOException::new));
        inputs.keySet().forEach(OrbitFunctionUtilities.wrapException(this::removeInput, RuntimeException::new));
        outputs.keySet().forEach(OrbitFunctionUtilities.wrapException(this::removeOutput, RuntimeException::new));
    }

    private final class InputEndpoint implements OrbitPipelineInputEndpoint {
        private double value;
        private boolean updating;

        @Override
        public synchronized void accept(double v) {
            value = value;
            if (!updating) {
                updating = true;
                push(new Update());
            }
        }

        private synchronized void setUpdating(boolean updating) {
            this.updating = updating;
        }

        private synchronized void stop() {
            updating = true;
        }

        private final class Update extends OrbitApiSimpleUpdateBase {
            public Update() {
                super(0);
            }

            @Override
            protected byte[] getPayload() {
                synchronized (InputEndpoint.this) {
                    byte[] payload = new byte[8];
                    ByteBuffer.wrap(payload).putDouble(value);
                    updating = false;
                    return payload;
                }
            }
        }
    }

    private final class OutputEndpoint implements OrbitPipelineOutputEndpoint {
        private Double value;

        @Override
        public OptionalDouble get() {
            return value == null ? OptionalDouble.empty() : OptionalDouble.of(value);
        }

        private void setValue(Double value) {
            this.value = value;
        }
    }
}
