package pro.sketchware.core.debug;

import android.content.Context;
import android.content.SharedPreferences;

public final class RemoteDebugSettings {
    private final SharedPreferences preferences;

    public RemoteDebugSettings(Context context) {
        preferences = context.getSharedPreferences("remote_debug", Context.MODE_PRIVATE);
    }

    public String getHost() { return preferences.getString("host", ""); }
    public int getPort() { return preferences.getInt("port", 0); }

    public RemoteEndpoint getEndpoint() {
        return new RemoteEndpoint(getHost(), getPort());
    }

    public void saveEndpoint(RemoteEndpoint endpoint) {
        preferences.edit().putString("host", endpoint.host).putInt("port", endpoint.port).apply();
    }

    public RemoteDeviceTarget getTarget() {
        RemoteDeviceTarget target = new RemoteDeviceTarget(getHost(), getPort(),
                preferences.getString("service", ""), preferences.getString("serial", ""),
                preferences.getString("guid", ""), preferences.getString("label", ""),
                preferences.getBoolean("automatic", true));
        if (!target.isConfigured()) throw new IllegalArgumentException("No selected device");
        return target;
    }

    public void remember(RemoteDeviceTarget target, RemoteDeviceTarget requested, boolean select) {
        if (!select) {
            try { if (!getTarget().sameDevice(requested)) return; }
            catch (IllegalArgumentException error) { return; }
        }
        preferences.edit().putString("host", target.host).putInt("port", target.port)
                .putString("service", target.serviceName).putString("serial", target.serial)
                .putString("guid", target.guid).putString("label", target.label)
                .putBoolean("automatic", target.automatic).apply();
    }

    public boolean isRemote(String projectId) {
        return projectId != null && preferences.getBoolean("project:" + projectId, false);
    }

    public void setRemote(String projectId, boolean enabled) {
        if (projectId != null) preferences.edit().putBoolean("project:" + projectId, enabled).apply();
    }
}
