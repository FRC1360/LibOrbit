/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitApiServer.java
 * A local API server
 */

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

	/**
	 * @param port The local TCP port to listen on
	 * @throws IOException Thrown if there is a problem setting up the listener
	 */
	public OrbitApiServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		acceptThread = new Thread(this::runAccept);
		acceptThread.start();
		processThread = new Thread(this::runProcess);
		processThread.start();

	}

	/**
	 * @param label The label on the input to access
	 * @return An output endpoint giving access to the named input
	 */
	public synchronized OrbitPipelineOutputEndpoint getInput(String label) {
		return inputs.computeIfAbsent(label, _label -> {
			InputEndpoint endpoint = new InputEndpoint(label);
			endpoint.setValue(0.0);
			return endpoint;
		});
	}

	/**
	 * @param label The label on the output to access
	 * @return An input endpoint giving access to the named output
	 */
	public synchronized OrbitPipelineInputEndpoint getOutput(String label) {
		return outputs.computeIfAbsent(label, OutputEndpoint::new);
	}

	/**
	 * @param label The label on the input to remove
	 * @throws OrbitPipelineInvalidConfigurationException Should never be thrown
	 */
	public synchronized void removeInput(String label) throws OrbitPipelineInvalidConfigurationException {
		InputEndpoint endpoint = inputs.remove(label);
		if (endpoint != null)
			OrbitPipelineManager.disconnect(endpoint);
		push(new RemoveUpdate(endpoint));
	}

	/**
	 * @param label The label on the input to remove
	 * @throws OrbitPipelineInvalidConfigurationException Should never be thrown
	 */
	public synchronized void removeOutput(String label) throws OrbitPipelineInvalidConfigurationException {
		OutputEndpoint endpoint = outputs.remove(label);
		if (endpoint != null) {
			endpoint.stop();
			OrbitPipelineManager.disconnect(endpoint);
		}
		push(new RemoveUpdate(endpoint));
	}

	/**
	 * @param update An update to push to all clients
	 */
	public synchronized void push(OrbitApiUpdate update) {
		try {
			updateQueue.put(update);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return An array of the connected clients
	 */
	public synchronized OrbitApiClient[] getClients() {
		return connections.toArray(new OrbitApiClient[0]);
	}

	/**
	 * A thread that loops accepting new clients
	 */
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

	/**
	 * A thread that loops dispatching updates
	 */
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
			try {
				connections.forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.wrapException(OrbitApiClient::push, UncheckedIOException::new), update));
			} catch (UncheckedIOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * To be called by a client when it is disconnecting
	 * @param client The client to disconnect
	 */
	synchronized void disconnect(OrbitApiClient client) {
		connections.remove(client);
	}

	/**
	 * @param input The label on the input to update
	 * @param value The new value of the input
	 */
	synchronized void updateInput(String input, double value) {
		inputs.get(input).setValue(value);
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		// Attempt to stop threads
		OrbitMiscUtilities.tryStop(acceptThread, "Failed to stop the connection accepting thread");
		OrbitMiscUtilities.tryStop(processThread, "Failed to stop the update processing thread");
		// Stop listening
		serverSocket.close();
		// Close all connections
		connections.forEach(OrbitFunctionUtilities.wrapException(Closeable::close, UncheckedIOException::new));
		// Remove all inputs and outputs
		inputs.keySet().forEach(OrbitFunctionUtilities.wrapException(this::removeInput, RuntimeException::new));
		outputs.keySet().forEach(OrbitFunctionUtilities.wrapException(this::removeOutput, RuntimeException::new));
	}
	
	/**
	 * Base class for inputs and outputs
	 */
	private abstract class EndpointBase {
		protected final String label;
		protected double value;
		protected boolean updating;
		
		/**
		 * @param label The endpoint's label
		 */
		public EndpointBase(String label) {
			this.label = label;
		}
		
		/**
		 * @return The endpoint's label
		 */
		public final String getLabel() {
			return label;
		}

		/**
		 * @param updating True if an update is being processed
		 */
		protected final synchronized void setUpdating(boolean updating) {
			this.updating = updating;
		}

		/**
		 * Stops the dispatching of updates
		 */
		public final synchronized void stop() {
			setUpdating(true);
		}

		/**
		 * An update to the endpoint's value
		 */
		protected final class Update extends OrbitApiSimpleUpdateBase {
			/**
			 * @param channel The MCS channel on which to dispatch the update
			 */
			public Update(int channel) {
				super(channel);
			}

			/* (non-Javadoc)
			 * @see ca._1360.liborbit.server.OrbitApiSimpleUpdateBase#writePayload(java.io.DataOutputStream)
			 */
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

	/**
	 * An input
	 */
	private final class InputEndpoint extends EndpointBase implements OrbitPipelineOutputEndpoint {
		/**
		 * @param label The input's label
		 */
		public InputEndpoint(String label) {
			super(label);
		}

		/* (non-Javadoc)
		 * @see java.util.function.Supplier#get()
		 */
		@Override
		public OptionalDouble get() {
			// Treat NaN as empty
			return Double.isNaN(value) ? OptionalDouble.empty() : OptionalDouble.of(value);
		}

		/**
		 * @param value The new value of the input
		 */
		public void setValue(Double value) {
			this.value = value;
			push(new Update(1));
		}
	}

	/**
	 * An output
	 */
	private final class OutputEndpoint extends EndpointBase implements OrbitPipelineInputEndpoint {
		/**
		 * @param label The output's label
		 */
		public OutputEndpoint(String label) {
			super(label);
		}

		/* (non-Javadoc)
		 * @see java.util.function.DoubleConsumer#accept(double)
		 */
		@Override
		public synchronized void accept(double v) {
			value = v;
			if (!updating) {
				setUpdating(true);
				push(new Update(0));
			}
		}
	}
	
	/**
	 * An update that removes an input or output
	 */
	private final class RemoveUpdate extends OrbitApiSimpleUpdateBase {
		private final String label;
		
		/**
		 * @param input The input to remove
		 */
		public RemoveUpdate(InputEndpoint input) {
			super(2);
			label = input.getLabel();
		}
		
		/**
		 * @param output The output to remove
		 */
		public RemoveUpdate(OutputEndpoint output) {
			super(3);
			label = output.getLabel();
		}

		/* (non-Javadoc)
		 * @see ca._1360.liborbit.server.OrbitApiSimpleUpdateBase#writePayload(java.io.DataOutputStream)
		 */
		@Override
		protected void writePayload(DataOutputStream output) throws IOException {
			output.writeUTF(label);
		}
	}
}
