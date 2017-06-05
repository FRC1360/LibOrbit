package ca._1360.liborbit.server;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineInvalidConfigurationException;
import ca._1360.liborbit.pipeline.OrbitPipelineManager;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.util.OrbitMiscUtilities;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.OptionalDouble;
import java.util.concurrent.LinkedBlockingQueue;

public final class OrbitApiServer implements Closeable {
	private final ServerSocket serverSocket;
	private final HashMap<String, InputEndpoint> inputs = new HashMap<>();
	private final HashMap<String, OutputEndpoint> outputs = new HashMap<>();
	private final LinkedBlockingQueue<OrbitApiUpdate> updateQueue = new LinkedBlockingQueue<>();
	private final ArrayList<OrbitApiClient> connections = new ArrayList<>();
	private final Thread acceptThread;
	private final Thread processThread;

	public OrbitApiServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		acceptThread = new Thread(this::runAccept);
		acceptThread.start();
		processThread = new Thread(this::runProcess);
		processThread.start();

	}

	public synchronized OrbitPipelineOutputEndpoint getInput(String label) {
		return inputs.computeIfAbsent(label, _label -> {
			InputEndpoint endpoint = new InputEndpoint(label);
			endpoint.setValue(0.0);
			return endpoint;
		});
	}

	public synchronized OrbitPipelineInputEndpoint getOutput(String label) {
		return outputs.computeIfAbsent(label, OutputEndpoint::new);
	}

	public synchronized void removeInput(String label) throws OrbitPipelineInvalidConfigurationException {
		InputEndpoint endpoint = inputs.remove(label);
		if (endpoint != null)
			OrbitPipelineManager.disconnect(endpoint);
		push(new RemoveUpdate(endpoint));
	}

	public synchronized void removeOutput(String label) throws OrbitPipelineInvalidConfigurationException {
		OutputEndpoint endpoint = outputs.remove(label);
		if (endpoint != null) {
			endpoint.stop();
			OrbitPipelineManager.disconnect(endpoint);
		}
		push(new RemoveUpdate(endpoint));
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
			do
				try {
					update = updateQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			while (update == null);
			connections.forEach(OrbitFunctionUtilities.specializeSecond(
					OrbitFunctionUtilities.wrapException(OrbitApiClient::push, UncheckedIOException::new), update));
		}
	}

	synchronized void disconnect(OrbitApiClient client) {
		connections.remove(client);
	}

	synchronized void updateInput(String input, double value) {
		inputs.get(input).setValue(value);
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
	
	private abstract class EndpointBase {
		protected final String label;
		protected double value;
		protected boolean updating;
		
		public EndpointBase(String label) {
			this.label = label;
		}
		
		public final String getLabel() {
			return label;
		}

		protected final synchronized void setUpdating(boolean updating) {
			this.updating = updating;
		}

		public final synchronized void stop() {
			setUpdating(true);
		}

		protected final class Update extends OrbitApiSimpleUpdateBase {
			public Update(int channel) {
				super(channel);
			}

			@Override
			protected void writePayload(DataOutputStream output) throws IOException {
				output.writeUTF(label);
				synchronized (EndpointBase.this) {
					output.writeDouble(value);
					setUpdating(false);
				}
			}
		}
	}

	private final class InputEndpoint extends EndpointBase implements OrbitPipelineOutputEndpoint {
		public InputEndpoint(String label) {
			super(label);
		}

		@Override
		public OptionalDouble get() {
			return Double.isNaN(value) ? OptionalDouble.empty() : OptionalDouble.of(value);
		}

		public void setValue(Double value) {
			this.value = value;
			push(new Update(1));
		}
	}

	private final class OutputEndpoint extends EndpointBase implements OrbitPipelineInputEndpoint {
		public OutputEndpoint(String label) {
			super(label);
		}

		@Override
		public synchronized void accept(double v) {
			value = v;
			if (!updating) {
				setUpdating(true);
				push(new Update(0));
			}
		}
	}
	
	private final class RemoveUpdate extends OrbitApiSimpleUpdateBase {
		private final String label;
		
		public RemoveUpdate(InputEndpoint input) {
			super(2);
			label = input.getLabel();
		}
		
		public RemoveUpdate(OutputEndpoint output) {
			super(3);
			label = output.getLabel();
		}

		@Override
		protected void writePayload(DataOutputStream output) throws IOException {
			output.writeUTF(label);
		}
	}
}
