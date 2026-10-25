package pro.sketchware.core.debug;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.muntashirakon.adb.AdbStream;
import pro.sketchware.R;

/** One explicit remote device, with serialized operations and a bounded, in-memory log buffer. */
public final class RemoteDebugManager {
    private static RemoteDebugManager instance;
    private final Context context;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    private final CopyOnWriteArrayList<Runnable> listeners = new CopyOnWriteArrayList<>();
    private final RemoteLogBuffer logs = new RemoteLogBuffer();
    private final AtomicBoolean busy = new AtomicBoolean();
    private final AtomicBoolean notificationPending = new AtomicBoolean();
    private final RemoteDebugSettings settings;
    private final AdbDiscovery discovery;
    private int discoveryScreens;
    private volatile boolean allowAutoReconnect = true;
    private volatile long nextAutoAttempt;
    private volatile AdbSocketBridge bridge;
    private volatile AdbStream logStream;
    private volatile String status;
    private volatile boolean connected;
    private volatile boolean failed;
    private volatile boolean cancelled;
    private volatile boolean timedOut;
    private volatile String logPackage = "";
    // Accessed only by worker; the UI never waits on libadb's internal locks.
    private RemoteAdbClient client;
    private RemoteEndpoint endpoint;
    private RemoteDeviceTarget activeTarget;
    private volatile String connectedName = "";
    private final Runnable healthCheck = new Runnable() {
        @Override public void run() {
            if (discoveryScreens == 0) return;
            if (connected && !busy.get()) worker.execute(() -> {
                if (busy.get() || client == null || activeTarget == null) return;
                DiscoveredAdbService current = AdbServiceSelector.connectionFor(discovery.snapshot(), activeTarget);
                if (!client.isConnected() || current != null && !current.endpoint.equals(endpoint)) {
                    closeConnection();
                    setStatus(text(R.string.remote_debug_finding_connection));
                    main.post(RemoteDebugManager.this::maybeReconnect);
                }
            });
            maybeReconnect();
            main.postDelayed(this, 5000);
        }
    };

    private RemoteDebugManager(Context context) {
        this.context = context.getApplicationContext();
        settings = new RemoteDebugSettings(this.context);
        discovery = new AdbDiscovery(this.context, this::onDiscoveryChanged);
        status = text(R.string.remote_debug_disconnected);
    }

    public static synchronized RemoteDebugManager get(Context context) {
        if (instance == null) instance = new RemoteDebugManager(context);
        return instance;
    }

    public boolean isBusy() { return busy.get(); }
    public boolean isConnected() { return connected; }
    public boolean hasFailed() { return failed; }
    public boolean isLogging() { return logStream != null; }
    public String getStatus() { return status; }
    public String getLogPackage() { return logPackage; }
    public String getConnectedName() { return connectedName; }
    public List<DiscoveredAdbService> getDiscoveredServices() { return discovery.snapshot(); }
    public boolean hasDiscoveryFailed() { return discovery.hasFailed(); }
    public void restartDiscovery() { discovery.restart(); }

    public AutoCloseable discover() {
        if (discoveryScreens++ == 0) main.postDelayed(healthCheck, 5000);
        AdbDiscovery.Scan scan = discovery.acquire();
        AtomicBoolean closed = new AtomicBoolean();
        return () -> {
            if (!closed.compareAndSet(false, true)) return;
            discoveryScreens--;
            if (discoveryScreens == 0) main.removeCallbacks(healthCheck);
            scan.close();
        };
    }
    public void addListener(Runnable listener) { listeners.add(listener); }
    public void removeListener(Runnable listener) { listeners.remove(listener); }

    public RemoteLogBuffer.Snapshot getLogs(long afterSequence, long generation) { return logs.snapshot(afterSequence, generation); }
    public void clearLogs() { logs.clear(); notifyChanged(); }

    public boolean pair(RemoteEndpoint pairingEndpoint, String code) {
        return pairAndConnect(new DiscoveredAdbService("", DiscoveredAdbService.PAIRING, pairingEndpoint), code, null);
    }

    public boolean pairAndConnect(DiscoveredAdbService service, String code, String projectId) {
        if (code == null || !code.matches("[0-9]{6}")) throw new IllegalArgumentException("Invalid pairing code");
        allowAutoReconnect = true;
        return execute(90, () -> {
            closeConnection();
            setStatus(text(R.string.remote_debug_pairing));
            try (AdbDiscovery.Scan ignored = discovery.acquire()) {
                RemoteAdbClient pairingClient = new RemoteAdbClient(context);
                try {
                    openBridge(service.endpoint);
                    pairingClient.pair("127.0.0.1", bridge.getPort(), code);
                    checkCancelled();
                } finally {
                    if (bridge != null) bridge.close();
                    bridge = null;
                }
                RemoteDeviceTarget pending = new RemoteDeviceTarget(service.endpoint.host, 0, "", "", "", "", true);
                settings.remember(pending, null, true);
                setStatus(text(R.string.remote_debug_finding_connection));
                DiscoveredAdbService connection = awaitConnection(pending, 10000);
                checkCancelled();
                if (connection == null) {
                    setStatus(text(R.string.remote_debug_paired));
                    return;
                }
                connectResolved(new RemoteDeviceTarget(connection.endpoint.host, connection.endpoint.port,
                        connection.name, "", "", "", true), connection, true);
                settings.setRemote(projectId, true);
                setStatus(context.getString(R.string.remote_debug_connected, connectedName));
            }
        });
    }

    public boolean connect(RemoteEndpoint target) {
        return connectTarget(RemoteDeviceTarget.manual(target), null, true);
    }

    public boolean connect(RemoteEndpoint target, String projectId) {
        return connectTarget(RemoteDeviceTarget.manual(target), projectId, true);
    }

    public boolean connectService(DiscoveredAdbService service, String projectId) {
        return connectTarget(new RemoteDeviceTarget(service.endpoint.host, service.endpoint.port,
                service.name, "", "", "", true), projectId, true);
    }

    public boolean reconnect(String projectId) {
        return connectTarget(settings.getTarget(), projectId, false);
    }

    private boolean connectTarget(RemoteDeviceTarget target, String projectId, boolean select) {
        allowAutoReconnect = true;
        return execute(45, () -> {
            ensureConnected(target, select);
            settings.setRemote(projectId, true);
            setStatus(context.getString(R.string.remote_debug_connected, connectedName));
        });
    }

    public boolean deploy(RemoteDeviceTarget target, File apk, String packageName) {
        RemoteAdbCommands.validatePackage(packageName);
        allowAutoReconnect = true;
        return execute(240, () -> {
            stopLogsInternal();
            clearLogs();
            logPackage = packageName;
            ensureConnected(target, false);
            RemoteAdbCommands commands = commands();
            int user = commands.currentUser();
            append(context.getString(R.string.remote_debug_deploying, packageName, connectedName));
            commands.install(apk, user, percent -> setStatus(context.getString(R.string.remote_debug_installing, percent)));
            checkCancelled();
            append(text(R.string.remote_debug_installed));
            int uid = commands.uid(packageName, user);
            // Use the DEVICE clock before launch so an immediate startup crash stays in the buffer,
            // even if logcat's subprocess is not scheduled until after am start.
            startLogs(commands.logService(uid, commands.shell("date +%s").trim() + ".000"));
            setStatus(text(R.string.remote_debug_starting));
            commands.launch(packageName, user);
            checkCancelled();
            setStatus(context.getString(R.string.remote_debug_running, packageName));
        });
    }

    public boolean readLogs(RemoteDeviceTarget target, String packageName) {
        RemoteAdbCommands.validatePackage(packageName);
        allowAutoReconnect = true;
        return execute(40, () -> {
            stopLogsInternal();
            if (!packageName.equals(logPackage)) clearLogs();
            logPackage = packageName;
            ensureConnected(target, false);
            RemoteAdbCommands commands = commands();
            int uid = commands.uid(packageName, commands.currentUser());
            startLogs(commands.logService(uid, commands.shell("date +%s").trim() + ".000"));
            setStatus(context.getString(R.string.remote_debug_running, packageName));
        });
    }

    public boolean stopLogs() {
        return execute(10, () -> {
            stopLogsInternal();
            setStatus(connected ? text(R.string.remote_debug_logs_stopped) : text(R.string.remote_debug_disconnected));
        });
    }

    public void disconnect() {
        allowAutoReconnect = false;
        if (busy.get()) {
            cancelled = true;
            AdbSocketBridge active = bridge;
            if (active != null) active.close();
            return;
        }
        execute(10, () -> { closeConnection(); setStatus(text(R.string.remote_debug_disconnected)); });
    }

    private boolean execute(int timeoutSeconds, Work work) {
        if (!busy.compareAndSet(false, true)) return false;
        cancelled = false;
        timedOut = false;
        failed = false;
        notifyChanged();
        worker.execute(() -> {
            var timeout = timer.schedule(() -> {
                timedOut = true;
                AdbSocketBridge active = bridge;
                if (active != null) active.close();
            }, timeoutSeconds, TimeUnit.SECONDS);
            try {
                work.run();
                checkCancelled();
            } catch (Exception error) {
                failed = true;
                closeConnection();
                nextAutoAttempt = SystemClock.elapsedRealtime() + 15000;
                String reason = timedOut ? text(R.string.remote_debug_timeout)
                        : cancelled ? text(R.string.remote_debug_cancelled)
                        : error.getMessage() == null || error.getMessage().isBlank()
                        ? error.getClass().getSimpleName() : error.getMessage();
                setStatus(context.getString(R.string.remote_debug_error, reason));
                append(status);
            } finally {
                timeout.cancel(false);
                busy.set(false);
                notifyChanged();
                main.post(this::maybeReconnect);
            }
        });
        return true;
    }

    private void openBridge(RemoteEndpoint target) throws Exception {
        bridge = new AdbSocketBridge();
        checkCancelled();
        bridge.connect(target);
        checkCancelled();
    }

    private void ensureConnected(RemoteDeviceTarget target, boolean select) throws Exception {
        if (client != null && client.isConnected() && activeTarget != null
                && (target.automatic ? activeTarget.sameDevice(target) : endpoint.equals(target.lastEndpoint()))) {
            AdbSocketBridge probeBridge = bridge;
            var probeTimeout = timer.schedule(probeBridge::close, 5, TimeUnit.SECONDS);
            try {
                commands().shell("echo connected");
                checkCancelled();
                if (select) settings.remember(activeTarget, target, true);
                return;
            } catch (Exception staleConnection) {
                closeConnection();
                checkCancelled();
            } finally {
                probeTimeout.cancel(false);
            }
        }
        DiscoveredAdbService found = null;
        if (target.automatic) {
            setStatus(text(R.string.remote_debug_finding_connection));
            try (AdbDiscovery.Scan ignored = discovery.acquire()) { found = awaitConnection(target, 8000); }
            checkCancelled();
        }
        if (found == null && target.lastEndpoint() == null) throw new IOException(text(R.string.remote_debug_no_device));
        connectResolved(target, found, select);
    }

    private void connectResolved(RemoteDeviceTarget target, DiscoveredAdbService service, boolean select) throws Exception {
        RemoteEndpoint address = service == null ? target.lastEndpoint() : service.endpoint;
        setStatus(context.getString(R.string.remote_debug_connecting, address.toString()));
        closeConnection();
        client = new RemoteAdbClient(context);
        openBridge(address);
        if (!client.connect("127.0.0.1", bridge.getPort())) throw new IOException(text(R.string.remote_debug_timeout));
        checkCancelled();
        String sdk = commands().shell("getprop ro.build.version.sdk").trim();
        if (!sdk.matches("[0-9]+") || Integer.parseInt(sdk) < 30) {
            throw new IOException(text(R.string.remote_debug_requires_android11));
        }
        String guid = commands().shell("getprop persist.adb.wifi.guid").trim();
        String serial = commands().shell("getprop ro.serialno").trim();
        if (!target.acceptsIdentity(guid, serial)) throw new IOException(text(R.string.remote_debug_wrong_device));
        String label = commands().shell("getprop ro.product.model").trim();
        connectedName = label.isEmpty() ? address.toString() : label;
        endpoint = address;
        activeTarget = new RemoteDeviceTarget(address.host, address.port,
                service == null ? target.serviceName : service.name, serial, guid, connectedName, true);
        settings.remember(activeTarget, target, select);
        connected = true;
    }

    private DiscoveredAdbService awaitConnection(RemoteDeviceTarget target, long timeout) throws InterruptedException {
        return discovery.await(service -> {
            DiscoveredAdbService selected = AdbServiceSelector.connectionFor(discovery.snapshot(), target);
            return selected != null && selected.key().equals(service.key());
        }, timeout, () -> cancelled || timedOut);
    }

    private void onDiscoveryChanged() {
        notifyChanged();
        maybeReconnect();
    }

    private void maybeReconnect() {
        if (discoveryScreens == 0 || busy.get() || connected || !allowAutoReconnect
                || SystemClock.elapsedRealtime() < nextAutoAttempt) return;
        try {
            RemoteDeviceTarget target = settings.getTarget();
            if (AdbServiceSelector.connectionFor(discovery.snapshot(), target) == null) return;
            nextAutoAttempt = SystemClock.elapsedRealtime() + 15000;
            connectTarget(target, null, false);
        } catch (IllegalArgumentException ignored) { }
    }

    private RemoteAdbCommands commands() {
        return new RemoteAdbCommands(service -> {
            checkCancelled();
            AdbStream stream = client.openStream(service);
            return new RemoteAdbCommands.Channel() {
                private final java.io.InputStream input = stream.openInputStream();
                private final java.io.OutputStream output = stream.openOutputStream();
                @Override public java.io.InputStream input() { return input; }
                @Override public java.io.OutputStream output() { return output; }
                @Override public void close() throws IOException { stream.close(); }
            };
        });
    }

    private void startLogs(String service) throws Exception {
        stopLogsInternal();
        AdbStream stream = client.openStream(service);
        logStream = stream;
        String sourcePackage = logPackage;
        Thread reader = new Thread(() -> {
            try {
                BufferedReader input = RemoteAdbCommands.reader(stream.openInputStream());
                String line;
                while (logStream == stream && (line = input.readLine()) != null) {
                    if (logStream == stream) {
                        logs.append(sourcePackage, line);
                        notifyChanged();
                    }
                }
            } catch (IOException error) {
                if (logStream == stream) append(text(R.string.remote_debug_logs_stopped));
            } finally {
                try { stream.close(); } catch (IOException ignored) { }
                if (logStream == stream) {
                    logStream = null;
                    setStatus(text(R.string.remote_debug_logs_stopped));
                }
            }
        }, "Sketchware-Remote-Logcat");
        reader.setDaemon(true);
        reader.start();
    }

    private void stopLogsInternal() {
        AdbStream stream = logStream;
        logStream = null;
        if (stream != null) try { stream.close(); } catch (IOException ignored) { }
    }

    private void closeConnection() {
        connected = false;
        connectedName = "";
        activeTarget = null;
        endpoint = null;
        AdbSocketBridge active = bridge;
        bridge = null;
        if (active != null) active.close();
        stopLogsInternal();
        if (client != null) try { client.disconnect(); } catch (IOException ignored) { }
        client = null;
    }

    private void checkCancelled() throws IOException {
        if (cancelled || timedOut) throw new IOException("Operation interrupted");
    }

    private void append(String line) {
        logs.append(logPackage, line);
        notifyChanged();
    }

    private String text(int id) { return context.getString(id); }
    private void setStatus(String value) { status = value; notifyChanged(); }

    private void notifyChanged() {
        if (notificationPending.compareAndSet(false, true)) {
            main.postDelayed(() -> {
                notificationPending.set(false);
                for (Runnable listener : listeners) listener.run();
            }, 200);
        }
    }

    private interface Work { void run() throws Exception; }
}
