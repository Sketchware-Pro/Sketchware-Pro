package pro.sketchware.core.debug;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Gives libadb's blocking pairing/TLS sockets a cancellable lifetime and a TCP connect timeout.
 * This bridge forwards bytes without terminating TLS; pairing and TLS still terminate at the device.
 * libadb 3.1.1 does not expose its pairing socket or a socket factory.
 */
final class AdbSocketBridge implements Closeable {
    private final ServerSocket listener;
    private final Socket remote = new Socket();
    private volatile Socket local;
    private volatile boolean closed;

    AdbSocketBridge() throws IOException {
        listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"));
    }

    void connect(RemoteEndpoint endpoint) throws IOException {
        remote.connect(new InetSocketAddress(endpoint.host, endpoint.port), 10000);
        remote.setTcpNoDelay(true);
        daemon("ADB-Bridge-Accept", () -> {
            try {
                Socket accepted = listener.accept();
                local = accepted;
                if (closed) { accepted.close(); return; }
                listener.close();
                accepted.setTcpNoDelay(true);
                daemon("ADB-Bridge-Upload", () -> copy(accepted, remote));
                copy(remote, accepted);
            } catch (IOException ignored) {
                close();
            }
        });
    }

    int getPort() { return listener.getLocalPort(); }

    private void copy(Socket source, Socket target) {
        try {
            byte[] bytes = new byte[16384];
            int count;
            while ((count = source.getInputStream().read(bytes)) != -1) {
                target.getOutputStream().write(bytes, 0, count);
            }
        } catch (IOException ignored) {
            // Closing either socket unblocks both directions, including a stalled TLS handshake.
        } finally {
            close();
        }
    }

    private static void daemon(String name, Runnable action) {
        Thread thread = new Thread(action, name);
        thread.setDaemon(true);
        thread.start();
    }

    @Override public void close() {
        closed = true;
        closeQuietly(listener);
        closeQuietly(remote);
        closeQuietly(local);
    }

    private static void closeQuietly(Closeable resource) {
        if (resource != null) try { resource.close(); } catch (IOException ignored) { }
    }
}
