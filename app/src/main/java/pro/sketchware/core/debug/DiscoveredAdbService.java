package pro.sketchware.core.debug;

public final class DiscoveredAdbService {
    public static final String PAIRING = "_adb-tls-pairing._tcp.";
    public static final String CONNECT = "_adb-tls-connect._tcp.";
    public final String name, type;
    public final RemoteEndpoint endpoint;

    public DiscoveredAdbService(String name, String type, RemoteEndpoint endpoint) {
        this.name = name;
        while (type.startsWith(".")) type = type.substring(1);
        this.type = type.endsWith(".") ? type : type + ".";
        this.endpoint = endpoint;
    }

    public boolean isPairing() { return PAIRING.equals(type); }
    public String key() { return type + "/" + name; }
}
