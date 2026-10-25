package pro.sketchware.core.debug;

import java.util.List;

/** Never select an arbitrary nearby phone just because it is the only advertised device. */
public final class AdbServiceSelector {
    private AdbServiceSelector() { }

    public static DiscoveredAdbService connectionFor(List<DiscoveredAdbService> services, RemoteDeviceTarget target) {
        for (DiscoveredAdbService service : services) {
            if (!service.isPairing() && !target.serviceName.isEmpty() && service.name.equals(target.serviceName)) return service;
        }
        for (DiscoveredAdbService service : services) {
            if (service.isPairing()) continue;
            if (namedFor(service.name, target.guid) || namedFor(service.name, target.serial)) return service;
        }
        // A last-known address is only a candidate; the manager verifies the saved GUID/serial
        // after TLS authentication, before it installs or launches anything.
        return connectionAt(services, target.host);
    }

    public static DiscoveredAdbService connectionAt(List<DiscoveredAdbService> services, String host) {
        DiscoveredAdbService found = null;
        for (DiscoveredAdbService service : services) {
            if (service.isPairing() || !service.endpoint.host.equals(host)) continue;
            if (found != null && !found.endpoint.equals(service.endpoint)) return null;
            found = service;
        }
        return found;
    }

    private static boolean namedFor(String name, String identity) {
        return !identity.isEmpty() && (name.equals("adb-" + identity) || name.startsWith("adb-" + identity + "-"));
    }
}
