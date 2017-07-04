/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
 */

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

    /**
     * @param server The local API server
     * @param socket The connection socket
     * @throws IOException Thrown in connection setup goes wrong
     */
    OrbitApiClient(OrbitApiServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        mcs = new OrbitMultiChannelStream(socket.getInputStream(), socket.getOutputStream());
        processThread = new Thread(this::runProcess);
        processThread.start();
    }

    /**
     * @return The local API server
     */
    public OrbitApiServer getServer() {
        return server;
    }

    /**
     * @return The client's IP address
     */
    public InetAddress remoteAddress() {
        return socket.getInetAddress();
    }

    /**
     * @param update The update to push
     * @throws IOException Thrown if there is an issue sending the update
     */
    void push(OrbitApiUpdate update) throws IOException {
        update.write(OrbitFunctionUtilities.specializeSecond(dataOutputStreamMap::computeIfAbsent, ((Function<Integer, OutputStream>) mcs::getOutputStream).andThen(DataOutputStream::new))::apply);
    }
    
    /**
     * A thread that loops reading input updates
     */
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

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        server.disconnect(this);
        dataOutputStreamMap.values().forEach(OrbitFunctionUtilities.wrapException(Closeable::close, UncheckedIOException::new));
        mcs.close();
        socket.close();
    }
}
