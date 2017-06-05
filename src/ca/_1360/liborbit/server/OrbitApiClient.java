package ca._1360.liborbit.server;

import ca._1360.liborbit.util.OrbitMultiChannelStream;
import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

public final class OrbitApiClient implements Closeable {
    private final OrbitApiServer server;
    private final Socket socket;
    private final OrbitMultiChannelStream mcs;
    private final HashMap<Integer, DataOutputStream> dataOutputStreamMap = new HashMap<>();
    private final Thread processThread;

    OrbitApiClient(OrbitApiServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        mcs = new OrbitMultiChannelStream(socket.getInputStream(), socket.getOutputStream());
        processThread = new Thread(this::runProcess);
        processThread.start();
    }

    public OrbitApiServer getServer() {
        return server;
    }

    public InetAddress remoteAddress() {
        return socket.getInetAddress();
    }

    void push(OrbitApiUpdate update) throws IOException {
        update.write(OrbitFunctionUtilities.specializeSecond(dataOutputStreamMap::computeIfAbsent, ((Function<Integer, OutputStream>) mcs::getOutputStream).andThen(DataOutputStream::new))::apply);
    }
    
    private void runProcess() {
    	DataInputStream data = new DataInputStream(mcs.getInputStream(1));
    	while (true) {
    		try {
				server.updateInput(data.readUTF(), data.readDouble());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

    @Override
    public void close() throws IOException {
        server.disconnect(this);
        dataOutputStreamMap.values().forEach(OrbitFunctionUtilities.wrapException(Closeable::close, UncheckedIOException::new));
        mcs.close();
        socket.close();
    }
}
