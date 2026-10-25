package mod.khaled.logcat;

import static pro.sketchware.utility.FileUtil.createNewFileIfNotPresent;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.core.debug.RemoteDebugManager;
import pro.sketchware.core.debug.RemoteDebugSettings;
import pro.sketchware.core.debug.RemoteLogBuffer;
import android.os.Environment;
import pro.sketchware.databinding.ActivityLogcatreaderBinding;
import pro.sketchware.databinding.EasyDeleteEdittextBinding;
import pro.sketchware.databinding.ViewLogcatItemBinding;
import pro.sketchware.lib.base.BaseTextWatcher;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class LogReaderActivity extends BaseAppCompatActivity {
    public static final String EXTRA_REMOTE = "remote_logcat";
    private boolean remoteMode, loggerRegistered;
    private String requestedPackage;
    private RemoteDebugManager remoteManager;
    private long remoteSequence, remoteGeneration = -1;
    private final Runnable remoteObserver = this::receiveRemoteLogs;

    private final BroadcastReceiver logger = new Logger();
    private final Pattern logPattern = Pattern.compile("^(.*\\d) ([VAFDEIW]) (.*): (.*)");
    private final ArrayList<HashMap<String, Object>> mainList = new ArrayList<>();
    private String pkgFilter = "";
    private String packageName = "pro.sketchware";
    private boolean autoScroll = true;
    private ArrayList<String> pkgFilterList = new ArrayList<>();

    private ActivityLogcatreaderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);

        binding = ActivityLogcatreaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    private void initialize() {
        remoteMode = getIntent().getBooleanExtra(EXTRA_REMOTE, false);
        requestedPackage = getIntent().getStringExtra("package_name");
        if (remoteMode) {
            remoteManager = RemoteDebugManager.get(this);
            if (requestedPackage != null) packageName = requestedPackage;
        }
        binding.logsRecyclerView.setAdapter(new Adapter(new ArrayList<>()));
        binding.topAppBar.getMenu().findItem(R.id.action_remote_capture).setVisible(remoteMode);
        binding.topAppBar.getMenu().findItem(R.id.action_auto_scroll).setChecked(autoScroll);

        if (!remoteMode) {
            IntentFilter intentFilter = new IntentFilter("pro.sketchware.ACTION_NEW_DEBUG_LOG");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(logger, intentFilter, Context.RECEIVER_EXPORTED);
            } else {
                registerReceiver(logger, intentFilter);
            }
            loggerRegistered = true;
        }

        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_clear) {
                if (remoteMode) remoteManager.clearLogs();
                mainList.clear();
                ((Adapter) binding.logsRecyclerView.getAdapter()).deleteAll();
            } else if (id == R.id.action_auto_scroll) {
                autoScroll = !item.isChecked();
                item.setChecked(autoScroll);
                followLatest();
            } else if (id == R.id.action_filter) {
                showFilterDialog();
            } else if (id == R.id.action_export) {
                exportLogcat(mainList);
            } else if (id == R.id.action_remote_capture && remoteMode) {
                if (remoteManager.isBusy()) {
                    SketchwareUtil.toast(getString(R.string.remote_debug_busy));
                } else if (remoteManager.isLogging()) {
                    remoteManager.stopLogs();
                } else {
                    try {
                        remoteManager.readLogs(new RemoteDebugSettings(this).getTarget(), packageName);
                    } catch (IllegalArgumentException missingDevice) {
                        SketchwareUtil.toast(getString(R.string.remote_debug_select_device));
                    }
                }
            }
            return true;
        });

        binding.searchInput.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }
        });
    }

    void showFilterDialog() {
        var dialogBinding = EasyDeleteEdittextBinding.inflate(getLayoutInflater());
        View view = dialogBinding.getRoot();

        dialogBinding.imgDelete.setVisibility(View.GONE);

        var builder = new MaterialAlertDialogBuilder(this)
                .setTitle("Filter by package name")
                .setMessage("For multiple package names, separate them with a comma (,).")
                .setIcon(R.drawable.ic_mtrl_filter)
                .setView(view)
                .setPositiveButton("Apply", (dialog, which) -> {
                    pkgFilter = Helper.getText(dialogBinding.easyEdInput);
                    pkgFilterList = new ArrayList<>();
                    for (String name : pkgFilter.split(",")) if (!name.trim().isEmpty()) pkgFilterList.add(name.trim());
                    applyFilters();
                })
                .setNeutralButton("Reset", (dialog, which) -> {
                    pkgFilter = "";
                    pkgFilterList.clear();
                    dialogBinding.easyEdInput.setText("");
                    applyFilters();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .create();

        builder.show();
    }

    private String safeGet(HashMap<String, Object> log, String key) {
        Object value = log.get(key);
        return value != null ? value.toString() : "";
    }

    private boolean matchesFilters(HashMap<String, Object> log) {
        return (pkgFilterList.isEmpty() || pkgFilterList.contains(safeGet(log, "pkgName")))
                && safeGet(log, "logRaw").toLowerCase(Locale.ROOT)
                .contains(Helper.getText(binding.searchInput).toLowerCase(Locale.ROOT));
    }

    private void applyFilters() {
        ArrayList<HashMap<String, Object>> filtered = new ArrayList<>();
        for (HashMap<String, Object> log : mainList) if (matchesFilters(log)) filtered.add(log);
        binding.logsRecyclerView.setAdapter(new Adapter(filtered));
    }

    private void updateEmptyState() {
        binding.noContentLayout.setVisibility(mainList.isEmpty() && Helper.getText(binding.searchInput).isEmpty()
                ? View.VISIBLE : View.GONE);
    }

    private void followLatest() {
        int count = binding.logsRecyclerView.getAdapter().getItemCount();
        if (autoScroll && count > 0) {
            binding.logsRecyclerView.getLayoutManager().scrollToPosition(count - 1);
            binding.appBarLayout.setExpanded(false);
        }
    }

    private void exportLogcat(ArrayList<HashMap<String, Object>> logs) {
        if (logs.isEmpty()) {
            SketchwareUtil.toastError("Nothing to Export");
            return;
        }
        try {
            String fileName = Calendar.getInstance(Locale.ENGLISH).getTimeInMillis() + ".txt";
            String filePath = Environment.getExternalStorageDirectory() + "/.sketchware/logcat/" + packageName + "/" + fileName;
            String stars = "*".repeat(95);
            String blank = " ".repeat(87);
            createNewFileIfNotPresent(filePath);
            StringBuilder contentBuilder = new StringBuilder();
            String formattedDate = new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm:ss", Locale.ENGLISH).format(new Date());

            contentBuilder.append(stars).append("\n");
            contentBuilder.append(stars).append("\n");
            contentBuilder.append("**").append(blank).append("**");
            contentBuilder.append("\n**    Exported logcat reader for ").append(packageName).append(" on ").append(formattedDate).append("  **\n");
            contentBuilder.append("**").append(blank).append("**\n");
            contentBuilder.append(stars).append("\n");
            contentBuilder.append(stars).append("\n");

            for (HashMap<String, Object> log : logs) {
                String date = safeGet(log, "date");
                String type = safeGet(log, "type");
                String tag = safeGet(log, "header");
                String body = safeGet(log, "body");

                if (!type.isEmpty()) {
                    contentBuilder.append("\n\n|-- Log Type: ").append(type).append("\n");
                    contentBuilder.append("    |-- Date: ").append(date).append("\n");
                    contentBuilder.append("    |-- Tag: ").append(tag).append("\n");
                    contentBuilder.append("    |-- Message: ").append(body).append("\n");
                    contentBuilder.append("------------------------------------------------");
                } else {
                    contentBuilder.append('\n').append(safeGet(log, "logRaw"));
                }

            }
            FileUtil.writeFile(filePath, contentBuilder.toString());
            SketchwareUtil.toast("Logcat exported successfully: " + filePath);
        } catch (Exception ex) {
            SketchwareUtil.toastError("Something went wrong!");
        }
    }

    @Override public void onStart() {
        super.onStart();
        if (remoteMode) {
            remoteManager.addListener(remoteObserver);
            receiveRemoteLogs();
        }
    }

    @Override
    public void onStop() {
        if (remoteMode) remoteManager.removeListener(remoteObserver);
        super.onStop();
        binding.searchInput.clearFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loggerRegistered) unregisterReceiver(logger);
    }

    private void receiveRemoteLogs() {
        RemoteLogBuffer.Snapshot snapshot = remoteManager.getLogs(remoteSequence, remoteGeneration);
        if (snapshot.generation() != remoteGeneration) {
            mainList.clear();
            ((Adapter) binding.logsRecyclerView.getAdapter()).deleteAll();
            remoteGeneration = snapshot.generation();
        }
        for (RemoteLogBuffer.Entry entry : snapshot.entries()) {
            if (requestedPackage != null && !entry.packageName().isEmpty()
                    && !requestedPackage.equals(entry.packageName())) continue;
            appendLog(entry.line(), entry.packageName().isEmpty() ? requestedPackage : entry.packageName());
        }
        if (!snapshot.entries().isEmpty()) followLatest();
        remoteSequence = snapshot.lastSequence();
        String device = remoteManager.getConnectedName();
        binding.topAppBar.setSubtitle(getString(R.string.remote_debug_logcat_source,
                device.isEmpty() ? packageName : device));
        binding.topAppBar.getMenu().findItem(R.id.action_remote_capture).setTitle(
                remoteManager.isLogging() ? R.string.remote_debug_stop_logs : R.string.remote_debug_read_logs);
    }

    private void appendLog(String line, String sourcePackage) {
        if (line == null) return;
        HashMap<String, Object> map = new HashMap<>();
        if (sourcePackage != null) {
            map.put("pkgName", sourcePackage);
            packageName = sourcePackage;
        }
        map.put("logRaw", line);
        Matcher matcher = logPattern.matcher(line);
        if (matcher.matches()) {
            map.put("date", matcher.group(1).trim());
            map.put("type", matcher.group(2));
            map.put("header", matcher.group(3).trim());
            map.put("body", matcher.group(4));
            map.put("culturedLog", "true");
        }
        mainList.add(map);
        Adapter adapter = (Adapter) binding.logsRecyclerView.getAdapter();
        if (remoteMode) while (mainList.size() > 500) adapter.removeLog(mainList.remove(0));
        if (matchesFilters(map)) adapter.updateList(map);
        updateEmptyState();
        if (!remoteMode) followLatest();
    }

    private class Logger extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            appendLog(intent.getStringExtra("log"), intent.getStringExtra("packageName"));
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public Adapter(ArrayList<HashMap<String, Object>> data) {
            this.data = new ArrayList<>(data);
            updateEmptyState();
        }

        public void updateList(HashMap<String, Object> _map) {
            data.add(_map);
            binding.logsRecyclerView.getAdapter().notifyItemInserted(data.size() - 1);

        }

        public void removeLog(HashMap<String, Object> log) {
            int index = data.indexOf(log);
            if (index >= 0) {
                data.remove(index);
                notifyItemRemoved(index);
            }
        }

        public void deleteAll() {
            data.clear();
            binding.logsRecyclerView.getAdapter().notifyDataSetChanged();
            updateEmptyState();
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var listBinding = ViewLogcatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            listBinding.getRoot().setLayoutParams(layoutParams);
            return new ViewHolder(listBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            var binding = holder.listBinding;
            HashMap<String, Object> item = data.get(position);

            if (item.containsKey("pkgName")) {
                binding.pkgName.setText(safeGet(item, "pkgName"));
                binding.pkgName.setVisibility(View.VISIBLE);
            } else {
                binding.pkgName.setVisibility(View.GONE);
            }
            if (item.containsKey("culturedLog")) {
                binding.dateHeader.setVisibility(View.VISIBLE);
                binding.type.setText(safeGet(item, "type"));
                binding.dateHeader.setText(safeGet(item, "date") + " | " + safeGet(item, "header"));
                switch (safeGet(item, "type")) {
                    case "A" -> binding.type.setBackgroundColor(0xFF9C27B0);
                    case "D" -> binding.type.setBackgroundColor(0xFF2196F3);
                    case "E", "F" -> binding.type.setBackgroundColor(0xFFF44336);
                    case "I" -> binding.type.setBackgroundColor(0xFF4CAF50);
                    case "V" -> binding.type.setBackgroundColor(0xFF000000);
                    case "W" -> binding.type.setBackgroundColor(0xFFFFC107);
                    default -> {
                        binding.type.setBackgroundColor(0xFF000000);
                        binding.type.setText("U");
                    }
                }
                binding.log.setText(safeGet(item, "body"));
                if (position + 1 < data.size() && safeGet(item, "date").equals(safeGet(data.get(position + 1), "date"))) {
                    HashMap<String, Object> next = data.get(position + 1);
                    if (safeGet(item, "pkgName").equals(safeGet(next, "pkgName"))) binding.pkgName.setVisibility(View.GONE);
                    if (safeGet(item, "header").equals(safeGet(next, "header"))) binding.dateHeader.setVisibility(View.GONE);
                }
            } else {
                binding.log.setText(safeGet(item, "logRaw"));
                binding.type.setBackgroundColor(0xFF000000);
                binding.type.setText("U");
                binding.dateHeader.setVisibility(View.GONE);
            }
            binding.getRoot().setOnLongClickListener(v -> {
                SketchwareUtil.toast("Copied to clipboard");
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", safeGet(item, "logRaw")));
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private static class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewLogcatItemBinding listBinding;

            public ViewHolder(@NonNull ViewLogcatItemBinding listBinding) {
                super(listBinding.getRoot());
                this.listBinding = listBinding;
            }
        }
    }
}
