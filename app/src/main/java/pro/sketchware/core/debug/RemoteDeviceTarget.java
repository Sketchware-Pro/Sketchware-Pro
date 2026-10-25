package pro.sketchware.core.debug;

/** Immutable run target: preserve the chosen device even if settings change during a build. */
public final class RemoteDeviceTarget {
    public final String host, serviceName, serial, guid, label;
    public final int port;
    public final boolean automatic;

    public RemoteDeviceTarget(String host, int port, String serviceName, String serial,
                              String guid, String label, boolean automatic) {
        this.host = host == null ? "" : host.trim();
        if (!this.host.isEmpty()) new RemoteEndpoint(this.host, 1);
        if (port < 0 || port > 65535) throw new IllegalArgumentException("Invalid device port");
        this.port = port;
        this.serviceName = clean(serviceName);
        this.serial = clean(serial);
        this.guid = clean(guid);
        this.label = clean(label);
        this.automatic = automatic;
    }

    public static RemoteDeviceTarget manual(RemoteEndpoint endpoint) {
        return new RemoteDeviceTarget(endpoint.host, endpoint.port, "", "", "", "", false);
    }

    public boolean isConfigured() { return !host.isEmpty() || !serviceName.isEmpty(); }
    public RemoteEndpoint lastEndpoint() { return host.isEmpty() || port == 0 ? null : new RemoteEndpoint(host, port); }
    public String displayName() { return !label.isEmpty() ? label : !serial.isEmpty() ? serial : host; }

    public boolean sameDevice(RemoteDeviceTarget other) {
        if (other == null) return false;
        if (!guid.isEmpty() && !other.guid.isEmpty()) return guid.equals(other.guid);
        if (!serial.isEmpty() && !other.serial.isEmpty()) return serial.equals(other.serial);
        if (!serviceName.isEmpty() && !other.serviceName.isEmpty()) return serviceName.equals(other.serviceName);
        return !host.isEmpty() && host.equals(other.host);
    }

    public boolean acceptsIdentity(String observedGuid, String observedSerial) {
        if (!guid.isEmpty() && !clean(observedGuid).isEmpty()) return guid.equals(observedGuid.trim());
        if (!serial.isEmpty() && !clean(observedSerial).isEmpty()) return serial.equals(observedSerial.trim());
        return guid.isEmpty() && serial.isEmpty();
    }

    private static String clean(String value) { return value == null ? "" : value.trim(); }
}
