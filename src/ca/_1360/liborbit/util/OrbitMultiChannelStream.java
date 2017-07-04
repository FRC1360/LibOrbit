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

package ca._1360.liborbit.util;

import java.io.*;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public final class OrbitMultiChannelStream implements Closeable {
    private static final int MAX_BLOCK = 65535;

    private final DataInputStream input;
    private final DataOutputStream output;
    private final Channel[] channels = new Channel[256];
    private final LinkedList<Runnable> errorNotifiers = new LinkedList<>();
    private final Thread receiveThread;
    private boolean work = true;

    /**
     * @param i The input stream
     * @param o The output stream
     */
    public OrbitMultiChannelStream(InputStream i, OutputStream o) {
    	// Wrap streams in data streams
        input = new DataInputStream(i);
        output = new DataOutputStream(o);
        // Start a receiving thread
        receiveThread = new Thread(() -> {
            try {
                while (work) {
                    int c, l;
                    byte[] data;
                    // Read data
                    synchronized (input) {
                        c = input.read();
                        l = input.readShort() & 0xFFFF;
                        data = OrbitMiscUtilities.readBytes(input, l);
                    }
                    // Ensure channel exists
                    synchronized (this) {
                        if (channels[c] == null)
                            channels[c] = new Channel(c);
                    }
                    // Write to channel
                    synchronized (channels[c]) {
                        System.out.println("Received on channel " + c);
                        for (byte b : data)
                            channels[c].queue.add(b);
                        channels[c].notify();
                    }
                }
            } catch (Throwable ignored) {
            	// Invoke error notifier
                errorNotifiers.forEach(Runnable::run);
                errorNotifiers.clear();
            }
        });
        receiveThread.start();
    }

    /**
     * @param channel The channel for which to get the input stream
     * @return The input stream for the given channel
     */
    public synchronized InputStream getInputStream(int channel) {
        if (channels[channel] == null)
            channels[channel] = new Channel(channel);
        return channels[channel].getInputStream();
    }

    /**
     * @param channel The channel for which to get the output stream
     * @return The output stream for the given channel
     */
    public synchronized OutputStream getOutputStream(int channel) {
        if (channels[channel] == null)
            channels[channel] = new Channel(channel);
        return channels[channel].getOutputStream();
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        work = false;
        OrbitMiscUtilities.tryStop(receiveThread, "Failed to stop the receiving thread");
    }

    /**
     * A channel
     */
    private class Channel {
        private final int channel;
        private final ChannelInputStream i;
        private final ChannelOutputStream o;
        private final Queue<Byte> queue;

        /**
         * @param channel The channel number
         */
        private Channel(int channel){
            this.channel = channel;
            i = new ChannelInputStream();
            o = new ChannelOutputStream();
            queue = new ArrayDeque<>();
        }

        /**
         * @return The input stream
         */
        private InputStream getInputStream() {
            return i;
        }

        /**
         * @return The output stream
         */
        private OutputStream getOutputStream() {
            return o;
        }

        /**
         * An input stream for the channel
         */
        private class ChannelInputStream extends InputStream {
            /* (non-Javadoc)
             * @see java.io.InputStream#read()
             */
            @Override
            public synchronized int read() throws IOException {
                synchronized (Channel.this) {
                    if (queue.size() == 0)
                        try {
                        	// Wait for there to be a byte available
                            Runnable errorNotifier = Thread.currentThread()::interrupt;
                            errorNotifiers.add(errorNotifier);
                            Channel.this.wait();
                            errorNotifiers.remove(errorNotifier);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new IOException(e);
                        }
                    return queue.remove();
                }
            }
        }

        /**
         * An output stream for the channel
         */
        private class ChannelOutputStream extends OutputStream {
            /* (non-Javadoc)
             * @see java.io.OutputStream#write(int)
             */
            @Override
            public void write(int b) throws IOException {
                write(new byte[] { (byte)b }, 0, 1);
            }

            /* (non-Javadoc)
             * @see java.io.OutputStream#write(byte[], int, int)
             */
            @Override
            public synchronized void write(byte[] b, int off, int len) throws IOException {
            	// Write up to 64 kB - 1 in each message block
                while (len != 0) {
                    int n = Math.min(len, MAX_BLOCK);
                    synchronized(output){
                        output.write(channel);
                        output.writeShort((short) n);
                        output.flush();
                        output.write(b, off, n);
                        output.flush();
                    }
                    off += n;
                    len -= n;
                }
            }
        }
    }
}
