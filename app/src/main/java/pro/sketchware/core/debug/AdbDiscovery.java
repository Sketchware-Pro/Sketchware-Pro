package pro.sketchware.core.debug;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.concurrent.Executor;

/** Cross-device mDNS discovery. libadb's bundled discovery filters for the local device. */
public final class AdbDiscovery {
    private final NsdManager nsd;
    private final WifiManager wifi;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final Runnable changed;
    private final Map<String, DiscoveredAdbService> services = new LinkedHashMap<>();
    private final ArrayDeque<NsdServiceInfo> queue = new ArrayDeque<>();
    private final Set<String> queued = new HashSet<>();
    private final Set<String> lost = new HashSet<>();
    private final Map<String, NsdServiceInfo> known = new LinkedHashMap<>();
    private final Map<String, AutoCloseable> watches = new LinkedHashMap<>();
    private final Map<String, Object> watchTokens = new LinkedHashMap<>();
    private final List<NsdManager.DiscoveryListener> listeners = new ArrayList<>();
    private WifiManager.MulticastLock multicast;
    private Object resolution;
    private int references, generation;
    private boolean running;
    private volatile boolean failed;
    private final Runnable refreshLegacy = new Runnable() {
        @Override public void run() {
            if (!running) return;
            for (NsdServiceInfo info : known.values()) if (!watches.containsKey(key(info))) enqueue(info);
            main.postDelayed(this, 4000);
        }
    };

    public AdbDiscovery(Context context, Runnable changed) {
        Context app = context.getApplicationContext();
        nsd = app.getSystemService(NsdManager.class);
        wifi = app.getSystemService(WifiManager.class);
        this.changed = changed;
    }

    public Scan acquire() {
        synchronized (this) { references++; }
        main.post(this::updateRunning);
        return new Scan();
    }

    public synchronized List<DiscoveredAdbService> snapshot() { return new ArrayList<>(services.values()); }
    public boolean hasFailed() { return failed; }

    public void restart() {
        main.post(() -> {
            if (running) stopRunning();
            updateRunning();
        });
    }

    public DiscoveredAdbService await(Predicate<DiscoveredAdbService> matches, long milliseconds,
                                       BooleanSupplier cancelled) throws InterruptedException {
        long deadline = SystemClock.elapsedRealtime() + milliseconds;
        synchronized (this) {
            while (!cancelled.getAsBoolean()) {
                for (DiscoveredAdbService service : services.values()) if (matches.test(service)) return service;
                long remaining = deadline - SystemClock.elapsedRealtime();
                if (remaining <= 0 || failed) return null;
                wait(Math.min(remaining, 200));
            }
        }
        return null;
    }

    private void updateRunning() {
        boolean needed;
        synchronized (this) { needed = references > 0; }
        if (needed == running) return;
        if (!needed) {
            stopRunning();
            return;
        }
        running = true;
        generation++;
        failed = false;
        try {
            if (wifi != null) {
                multicast = wifi.createMulticastLock("Sketchware-ADB-Discovery");
                multicast.setReferenceCounted(false);
                multicast.acquire();
            }
            discover(DiscoveredAdbService.PAIRING, generation);
            discover(DiscoveredAdbService.CONNECT, generation);
            main.postDelayed(refreshLegacy, 4000);
        } catch (RuntimeException error) {
            failed = true;
            synchronized (this) { notifyAll(); }
            changed.run();
        }
    }

    private void stopRunning() {
        running = false;
        generation++;
        for (NsdManager.DiscoveryListener listener : listeners) {
            try { nsd.stopServiceDiscovery(listener); } catch (RuntimeException ignored) { }
        }
        listeners.clear();
        main.removeCallbacks(refreshLegacy);
        for (AutoCloseable watch : watches.values()) try { watch.close(); } catch (Exception ignored) { }
        watches.clear(); watchTokens.clear(); known.clear();
        queue.clear(); queued.clear(); lost.clear();
        resolution = null;
        synchronized (this) { services.clear(); notifyAll(); }
        if (multicast != null && multicast.isHeld()) multicast.release();
        multicast = null;
        changed.run();
    }

    private void discover(String type, int session) {
        NsdManager.DiscoveryListener listener = new NsdManager.DiscoveryListener() {
            @Override public void onDiscoveryStarted(String serviceType) { }
            @Override public void onDiscoveryStopped(String serviceType) { }
            @Override public void onStartDiscoveryFailed(String serviceType, int code) {
                main.post(() -> {
                    if (session != generation) return;
                    failed = true;
                    synchronized (AdbDiscovery.this) { AdbDiscovery.this.notifyAll(); }
                    changed.run();
                });
            }
            @Override public void onStopDiscoveryFailed(String serviceType, int code) { }
            @Override public void onServiceFound(NsdServiceInfo info) {
                main.post(() -> {
                    if (!running || session != generation) return;
                    String key = key(info);
                    lost.remove(key);
                    known.put(key, info);
                    if (Build.VERSION.SDK_INT >= 34 && !watches.containsKey(key)) {
                        Object ticket = new Object();
                        watchTokens.put(key, ticket);
                        try {
                            AutoCloseable watch = Api34.watch(nsd, info, main::post, update -> {
                                if (running && generation == session && watchTokens.get(key) == ticket) publish(update, key);
                            }, () -> {
                                if (watchTokens.get(key) != ticket) return;
                                synchronized (AdbDiscovery.this) { services.remove(key); AdbDiscovery.this.notifyAll(); }
                                changed.run();
                            }, () -> {
                                if (watchTokens.get(key) != ticket) return;
                                watches.remove(key); watchTokens.remove(key);
                                enqueue(info);
                            });
                            watches.put(key, watch);
                            return;
                        } catch (RuntimeException unsupported) { watchTokens.remove(key); }
                    }
                    if (!watches.containsKey(key)) enqueue(info);
                });
            }
            @Override public void onServiceLost(NsdServiceInfo info) {
                main.post(() -> {
                    if (session != generation) return;
                    String key = key(info);
                    lost.add(key);
                    known.remove(key);
                    watchTokens.remove(key);
                    AutoCloseable watch = watches.remove(key);
                    if (watch != null) try { watch.close(); } catch (Exception ignored) { }
                    synchronized (AdbDiscovery.this) { services.remove(key); AdbDiscovery.this.notifyAll(); }
                    changed.run();
                });
            }
        };
        listeners.add(listener);
        nsd.discoverServices(type, NsdManager.PROTOCOL_DNS_SD, listener);
    }

    private void enqueue(NsdServiceInfo info) {
        if (queued.add(key(info))) queue.add(info);
        resolveNext();
    }

    @SuppressWarnings("deprecation")
    private void publish(NsdServiceInfo service, String key) {
        InetAddress host = service.getHost();
        if (Build.VERSION.SDK_INT >= 34) {
            for (InetAddress address : service.getHostAddresses()) {
                if (host == null || address instanceof Inet4Address) host = address;
                if (address instanceof Inet4Address) break;
            }
        }
        if (host == null) return;
        try {
            DiscoveredAdbService result = new DiscoveredAdbService(service.getServiceName(), service.getServiceType(),
                    new RemoteEndpoint(host.getHostAddress(), service.getPort()));
            synchronized (this) { services.put(key, result); notifyAll(); }
            changed.run();
        } catch (IllegalArgumentException ignored) { }
    }

    @SuppressWarnings("deprecation")
    private void resolveNext() {
        if (!running || resolution != null || queue.isEmpty()) return;
        NsdServiceInfo info = queue.remove();
        queued.remove(key(info));
        if (lost.contains(key(info))) { resolveNext(); return; }
        Object ticket = new Object();
        resolution = ticket;
        int session = generation;
        Runnable finish = () -> {
            if (resolution == ticket) { resolution = null; resolveNext(); }
        };
        main.postDelayed(finish, 10000);
        try {
            nsd.resolveService(info, new NsdManager.ResolveListener() {
                @Override public void onResolveFailed(NsdServiceInfo service, int code) { main.post(finish); }
                @Override public void onServiceResolved(NsdServiceInfo service) {
                    main.post(() -> {
                        if (running && generation == session && resolution == ticket && !lost.contains(key(info))) {
                            publish(service, key(info));
                        }
                        finish.run();
                    });
                }
            });
        } catch (RuntimeException error) { finish.run(); }
    }

    private static String key(NsdServiceInfo service) {
        String type = service.getServiceType();
        while (type.startsWith(".")) type = type.substring(1);
        return (type.endsWith(".") ? type : type + ".") + "/" + service.getServiceName();
    }

    public final class Scan implements AutoCloseable {
        private boolean closed;
        @Override public void close() {
            synchronized (AdbDiscovery.this) {
                if (closed) return;
                closed = true;
                references--;
            }
            main.post(AdbDiscovery.this::updateRunning);
        }
    }

    @androidx.annotation.RequiresApi(34)
    private static final class Api34 {
        static AutoCloseable watch(NsdManager manager, NsdServiceInfo info, Executor executor,
                                    Consumer<NsdServiceInfo> updated, Runnable lost, Runnable failed) {
            NsdManager.ServiceInfoCallback callback = new NsdManager.ServiceInfoCallback() {
                @Override public void onServiceInfoCallbackRegistrationFailed(int code) { failed.run(); }
                @Override public void onServiceInfoCallbackUnregistered() { }
                @Override public void onServiceLost() { lost.run(); }
                @Override public void onServiceUpdated(NsdServiceInfo service) { updated.accept(service); }
            };
            manager.registerServiceInfoCallback(info, executor, callback);
            return () -> manager.unregisterServiceInfoCallback(callback);
        }
    }
}
