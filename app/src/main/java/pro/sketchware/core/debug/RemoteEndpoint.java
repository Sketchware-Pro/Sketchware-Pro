package pro.sketchware.core.debug;

/** An explicitly selected device. The pairing and connection ports are deliberately separate. */
public final class RemoteEndpoint {
    public final String host;
    public final int port;

    public RemoteEndpoint(String host, int port) {
        String value = host == null ? "" : host.trim();
        if (value.startsWith("[") && value.endsWith("]")) value = value.substring(1, value.length() - 1);
        if (value.isEmpty() || value.length() > 253 || !value.matches("[a-zA-Z0-9._:%-]+")) {
            throw new IllegalArgumentException("Invalid device address");
        }
        if (port < 1 || port > 65535) throw new IllegalArgumentException("Invalid device port");
        this.host = value;
        this.port = port;
    }

    @Override public String toString() {
        return (host.contains(":") ? "[" + host + "]" : host) + ":" + port;
    }

    @Override public boolean equals(Object other) {
        return other instanceof RemoteEndpoint endpoint && host.equals(endpoint.host) && port == endpoint.port;
    }

    @Override public int hashCode() {
        return 31 * host.hashCode() + port;
    }
}
