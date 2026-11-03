package pro.sketchware.activities.debug;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pro.sketchware.R;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import mod.khaled.logcat.LogReaderActivity;
import pro.sketchware.core.debug.DiscoveredAdbService;
import pro.sketchware.core.debug.RemoteDebugManager;
import pro.sketchware.core.debug.RemoteDebugSettings;
import pro.sketchware.core.debug.RemoteDeviceTarget;
import pro.sketchware.core.debug.RemoteEndpoint;
import pro.sketchware.databinding.ActivityRemoteDebugBinding;
import pro.sketchware.databinding.DialogRemotePairBinding;
import pro.sketchware.databinding.ViewRemoteDeviceBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

public class RemoteDebugActivity extends BaseAppCompatActivity {
    private ActivityRemoteDebugBinding binding;
    private RemoteDebugManager manager;
    private RemoteDebugSettings settings;
    private String projectId;
    private String packageName;
    private boolean updatingTarget;
    private AlertDialog pairingDialog;
    private AutoCloseable discoverySession;
    private long scanStartedAt;
    private String deviceRows = "";
    private String displayedAddress;
    private final Runnable observer = this::refresh;

    @Override public void onCreate(Bundle state) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(state);
        binding = ActivityRemoteDebugBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UI.addSystemWindowInsetToPadding(binding.remoteToolbar, false, true, false, false);
        UI.addSystemWindowInsetToPadding(binding.remoteContent, true, false, true, true);
        binding.remoteToolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        projectId = getIntent().getStringExtra("sc_id");
        packageName = getIntent().getStringExtra("package_name");
        if (projectId == null || packageName == null) { finish(); return; }
        manager = RemoteDebugManager.get(this);
        settings = new RemoteDebugSettings(this);
        binding.host.setText(settings.getHost());
        binding.port.setText(settings.getPort() == 0 ? "" : String.valueOf(settings.getPort()));
        displayedAddress = settings.getHost() + ":" + settings.getPort();
        binding.remoteTarget.setChecked(settings.isRemote(projectId));
        binding.remoteTarget.setOnCheckedChangeListener((button, checked) -> {
            if (updatingTarget) return;
            if (checked && target() == null) {
                updatingTarget = true;
                binding.remoteTarget.setChecked(false);
                updatingTarget = false;
                SketchwareUtil.toast(getString(R.string.remote_debug_select_device));
                return;
            }
            settings.setRemote(projectId, checked);
        });
        binding.scan.setOnClickListener(v -> {
            scanStartedAt = SystemClock.elapsedRealtime();
            manager.restartDiscovery();
            binding.remoteContent.removeCallbacks(observer);
            binding.remoteContent.postDelayed(observer, 6000);
            refresh();
        });
        binding.connectSaved.setOnClickListener(v -> {
            if (target() == null) SketchwareUtil.toast(getString(R.string.remote_debug_select_device));
            else accepted(manager.reconnect(projectId));
        });
        binding.advancedToggle.setOnClickListener(v -> {
            boolean expanded = binding.advancedGroup.getVisibility() != View.VISIBLE;
            binding.advancedGroup.setVisibility(expanded ? View.VISIBLE : View.GONE);
            binding.advancedToggle.setIconResource(expanded ? R.drawable.selector_ic_expand_less_24
                    : R.drawable.selector_ic_expand_more_24);
        });
        binding.connect.setOnClickListener(v -> {
            RemoteEndpoint endpoint = readEndpoint();
            if (endpoint != null) accepted(manager.connect(endpoint, projectId));
        });
        binding.pair.setOnClickListener(v -> showPairing(null));
        binding.disconnect.setOnClickListener(v -> manager.disconnect());
        binding.openLogs.setOnClickListener(v -> startActivity(new Intent(this, LogReaderActivity.class)
                .putExtra(LogReaderActivity.EXTRA_REMOTE, true).putExtra("sc_id", projectId)
                .putExtra("package_name", packageName)));
        refresh();
    }

    private RemoteDeviceTarget target() {
        try { return settings.getTarget(); }
        catch (IllegalArgumentException error) { return null; }
    }

    private RemoteEndpoint readEndpoint() {
        binding.hostLayout.setError(null);
        binding.portLayout.setError(null);
        String host = binding.host.getText().toString().trim();
        try { new RemoteEndpoint(host, 1); }
        catch (IllegalArgumentException error) {
            binding.hostLayout.setError(getString(R.string.remote_debug_invalid_host));
            return null;
        }
        try { return new RemoteEndpoint(host, Integer.parseInt(binding.port.getText().toString().trim())); }
        catch (IllegalArgumentException error) {
            binding.portLayout.setError(getString(R.string.remote_debug_invalid_port));
            return null;
        }
    }

    private void showPairing(DiscoveredAdbService discovered) {
        String host = discovered == null ? binding.host.getText().toString().trim() : discovered.endpoint.host;
        try { new RemoteEndpoint(host, 1); }
        catch (IllegalArgumentException error) {
            binding.hostLayout.setError(getString(R.string.remote_debug_invalid_host));
            return;
        }
        DialogRemotePairBinding form = DialogRemotePairBinding.inflate(getLayoutInflater());
        form.pairDevice.setText(host);
        form.pairPortLayout.setVisibility(discovered == null ? View.VISIBLE : View.GONE);
        form.pairHelp.setText(discovered == null ? R.string.remote_debug_pair_help : R.string.remote_debug_auto_pair_help);
        pairingDialog = new MaterialAlertDialogBuilder(this).setTitle(R.string.remote_debug_pair)
                .setIcon(R.drawable.ic_mtrl_wifi)
                .setView(form.getRoot()).setNegativeButton(R.string.common_word_cancel, null)
                .setPositiveButton(R.string.remote_debug_pair_connect, null).create();
        pairingDialog.setOnShowListener(dialog -> pairingDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            form.pairPortLayout.setError(null);
            form.pairCodeLayout.setError(null);
            DiscoveredAdbService selected = discovered;
            if (selected == null) {
                try {
                    selected = new DiscoveredAdbService("", DiscoveredAdbService.PAIRING,
                            new RemoteEndpoint(host, Integer.parseInt(form.pairPort.getText().toString().trim())));
                } catch (IllegalArgumentException error) {
                    form.pairPortLayout.setError(getString(R.string.remote_debug_invalid_port));
                    return;
                }
            }
            String code = form.pairCode.getText().toString();
            if (!code.matches("[0-9]{6}")) {
                form.pairCodeLayout.setError(getString(R.string.remote_debug_invalid_code));
                return;
            }
            if (manager.pairAndConnect(selected, code, projectId)) {
                form.pairCode.getText().clear();
                pairingDialog.dismiss();
                refresh();
            } else accepted(false);
        }));
        pairingDialog.show();
    }

    private void renderDevices(List<DiscoveredAdbService> services, boolean busy) {
        Map<String, DeviceRow> rows = new LinkedHashMap<>();
        RemoteDeviceTarget selected = target();
        StringBuilder signature = new StringBuilder(busy + "|" + manager.isConnected() + "|"
                + (selected == null ? "" : selected.host + selected.displayName()));
        for (DiscoveredAdbService service : services) {
            signature.append(service.key()).append(service.endpoint);
            DeviceRow row = rows.computeIfAbsent(service.endpoint.host, ignored -> new DeviceRow());
            if (service.isPairing()) row.pairing = service; else row.connection = service;
        }
        if (deviceRows.equals(signature.toString())) return;
        deviceRows = signature.toString();
        binding.devices.removeAllViews();
        for (DeviceRow row : rows.values()) {
            DiscoveredAdbService service = row.pairing == null ? row.connection : row.pairing;
            ViewRemoteDeviceBinding item = ViewRemoteDeviceBinding.inflate(getLayoutInflater(), binding.devices, false);
            boolean saved = selected != null && selected.host.equals(service.endpoint.host);
            boolean connected = saved && manager.isConnected();
            item.deviceName.setText(saved ? selected.displayName() : getString(R.string.remote_debug_nearby_device));
            item.deviceDetail.setText(getString(row.pairing == null ? R.string.remote_debug_connect_available
                    : R.string.remote_debug_pair_available, service.endpoint.host));
            item.deviceAction.setText(connected && row.pairing == null ? R.string.remote_debug_already_connected
                    : row.pairing == null ? R.string.remote_debug_connect_action : R.string.remote_debug_pair_action);
            item.deviceAction.setEnabled(!busy && (!connected || row.pairing != null));
            item.deviceAction.setOnClickListener(v -> {
                if (row.pairing != null) showPairing(row.pairing);
                else accepted(manager.connectService(row.connection, projectId));
            });
            item.getRoot().setTag(service.name);
            item.getRoot().setTooltipText(service.name);
            item.getRoot().setOnClickListener(v -> item.deviceAction.performClick());
            binding.devices.addView(item.getRoot());
        }
    }

    private void accepted(boolean accepted) {
        if (!accepted) SketchwareUtil.toast(getString(R.string.remote_debug_busy));
        refresh();
    }

    private void refresh() {
        if (manager == null) return;
        boolean busy = manager.isBusy();
        binding.remoteContent.setKeepScreenOn(busy);
        RemoteDeviceTarget selected = target();
        binding.status.setText(selected == null && !busy && !manager.hasFailed()
                ? getString(R.string.remote_debug_empty_help) : manager.getStatus());
        binding.status.setTextColor(MaterialColors.getColor(binding.status,
                manager.hasFailed() ? androidx.appcompat.R.attr.colorError
                        : manager.isConnected() ? androidx.appcompat.R.attr.colorPrimary
                        : com.google.android.material.R.attr.colorOnSurfaceVariant));
        binding.deviceIcon.setImageResource(manager.isConnected() ? R.drawable.ic_mtrl_link_check : R.drawable.ic_mtrl_devices);
        binding.remoteProgress.setVisibility(busy ? View.VISIBLE : View.GONE);
        binding.connect.setEnabled(!busy);
        binding.connectSaved.setEnabled(!busy && target() != null);
        binding.connectSaved.setVisibility(selected == null ? View.GONE : View.VISIBLE);
        binding.connectionActions.setVisibility(selected != null || busy ? View.VISIBLE : View.GONE);
        binding.scan.setEnabled(!busy);
        binding.pair.setEnabled(!busy);
        binding.host.setEnabled(!busy);
        binding.port.setEnabled(!busy);
        binding.remoteTarget.setEnabled(!busy);
        updatingTarget = true;
        binding.remoteTarget.setChecked(settings.isRemote(projectId));
        updatingTarget = false;
        binding.selectedDevice.setText(selected == null ? getString(R.string.remote_debug_no_selection) : selected.displayName());
        binding.selectedAddress.setVisibility(selected == null ? View.GONE : View.VISIBLE);
        binding.selectedAddress.setText(selected == null ? "" : selected.lastEndpoint() == null ? selected.host
                : selected.lastEndpoint().toString());
        List<DiscoveredAdbService> services = manager.getDiscoveredServices();
        renderDevices(services, busy);
        binding.discoveryStatus.setText(manager.hasDiscoveryFailed() ? R.string.remote_debug_discovery_failed
                : !services.isEmpty() ? R.string.remote_debug_devices_found
                : SystemClock.elapsedRealtime() - scanStartedAt < 6000 ? R.string.remote_debug_searching
                : R.string.remote_debug_no_device);
        binding.discoveryProgress.setVisibility(!manager.hasDiscoveryFailed() && services.isEmpty()
                && SystemClock.elapsedRealtime() - scanStartedAt < 6000 ? View.VISIBLE : View.GONE);
        String savedAddress = settings.getHost() + ":" + settings.getPort();
        if (!busy && !binding.host.hasFocus() && !binding.port.hasFocus() && !savedAddress.equals(displayedAddress)) {
            binding.host.setText(settings.getHost());
            binding.port.setText(settings.getPort() == 0 ? "" : String.valueOf(settings.getPort()));
            displayedAddress = savedAddress;
        }
        binding.disconnect.setText(busy ? R.string.common_word_cancel : R.string.remote_debug_disconnect);
        binding.disconnect.setEnabled(busy || manager.isConnected());
        binding.disconnect.setVisibility(busy || manager.isConnected() ? View.VISIBLE : View.GONE);
    }

    @Override protected void onStart() {
        super.onStart();
        if (manager != null) {
            manager.addListener(observer);
            scanStartedAt = SystemClock.elapsedRealtime();
            discoverySession = manager.discover();
            binding.remoteContent.postDelayed(observer, 6000);
            refresh();
        }
    }

    @Override protected void onStop() {
        if (manager != null) manager.removeListener(observer);
        binding.remoteContent.removeCallbacks(observer);
        if (discoverySession != null) {
            try { discoverySession.close(); } catch (Exception ignored) { }
            discoverySession = null;
        }
        super.onStop();
    }

    @Override public void onDestroy() {
        if (pairingDialog != null) pairingDialog.dismiss();
        super.onDestroy();
    }

    private static final class DeviceRow {
        DiscoveredAdbService pairing, connection;
    }
}
