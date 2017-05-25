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

    public OrbitMultiChannelStream(InputStream i, OutputStream o) {
        input = new DataInputStream(i);
        output = new DataOutputStream(o);
        receiveThread = new Thread(() -> {
            try {
                while (work) {
                    int c, l;
                    byte[] data;
                    synchronized (input) {
                        c = input.read();
                        l = input.readShort() & 0xFFFF;
                        data = OrbitMiscUtilities.readBytes(input, l);
                    }
                    synchronized (this) {
                        if (channels[c] == null)
                            channels[c] = new Channel(c);
                    }
                    synchronized (channels[c]) {
                        System.out.println("Received on channel " + c);
                        for (byte b : data)
                            channels[c].queue.add(b);
                        channels[c].notify();
                    }
                }
            } catch (Throwable ignored) {
                errorNotifiers.forEach(Runnable::run);
                errorNotifiers.clear();
            }
        });
        receiveThread.start();
    }

    public synchronized InputStream getInputStream(int channel) {
        if (channels[channel] == null)
            channels[channel] = new Channel(channel);
        return channels[channel].getInputStream();
    }

    public synchronized OutputStream getOutputStream(int channel) {
        if (channels[channel] == null)
            channels[channel] = new Channel(channel);
        return channels[channel].getOutputStream();
    }

    @Override
    public void close() throws IOException {
        work = false;
        OrbitMiscUtilities.tryStop(receiveThread, "Failed to stop the receiving thread");
    }

    private class Channel {
        private final int channel;
        private final ChannelInputStream i;
        private final ChannelOutputStream o;
        private final Queue<Byte> queue;

        private Channel(int channel){
            this.channel = channel;
            i = new ChannelInputStream();
            o = new ChannelOutputStream();
            queue = new ArrayDeque<>();
        }

        private InputStream getInputStream() {
            return i;
        }

        private OutputStream getOutputStream() {
            return o;
        }

        private class ChannelInputStream extends InputStream {
            @Override
            public synchronized int read() throws IOException {
                synchronized (Channel.this) {
                    if (queue.size() == 0)
                        try {
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

        private class ChannelOutputStream extends OutputStream {
            @Override
            public void write(int b) throws IOException {
                write(new byte[] { (byte)b }, 0, 1);
            }

            @Override
            public synchronized void write(byte[] b, int off, int len) throws IOException {
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
