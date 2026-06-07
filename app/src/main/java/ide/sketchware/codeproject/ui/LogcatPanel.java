package ide.sketchware.codeproject.ui;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ide.sketchware.R;
import ide.sketchware.utility.FileUtil;

public class LogcatPanel {

    private static final int MAX_LINES = 5000;
    private static final long BATCH_INTERVAL_MS = 100;
    private static final int COLOR_VERBOSE = 0xFF888888;
    private static final int COLOR_DEBUG = 0xFF2196F3;
    private static final int COLOR_INFO = 0xFF4CAF50;
    private static final int COLOR_WARN = 0xFFFF9800;
    private static final int COLOR_ERROR = 0xFFF44336;
    private static final Pattern LOG_PATTERN = Pattern.compile("^(.*\\d) ([VADEIWF]) (.*): (.*)");
    private static final Pattern BRIEF_LOG_PATTERN = Pattern.compile("^([VADEIWF])/([^\\(]+)\\(\\s*\\d+\\):\\s?(.*)");

    private final List<LogLine> logLines = new ArrayList<>();
    private final List<LogLine> visibleLines = new ArrayList<>();
    private final List<String> pendingLines = new LinkedList<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private Process logcatProcess;
    private Thread readerThread;
    private volatile boolean isRunning = false;
    private boolean batchPosted = false;
    private String filter = "";
    private String packageFilter = "";
    private List<String> packageFilters = new ArrayList<>();
    private String packageName = "ide.sketchware";

    public void attach(RecyclerView rv) {
        this.recyclerView = rv;
        this.adapter = new LogAdapter();
        LinearLayoutManager lm = new LinearLayoutManager(rv.getContext());
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;

        try {
            logcatProcess = Runtime.getRuntime().exec(new String[]{"logcat", "-v", "brief"});
            readerThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(logcatProcess.getInputStream()))) {
                    String line;
                    while (isRunning && (line = reader.readLine()) != null) {
                        synchronized (pendingLines) {
                            if (!isRunning) break;
                            pendingLines.add(line);
                            if (!batchPosted) {
                                batchPosted = true;
                                mainHandler.postDelayed(this::drainBatch, BATCH_INTERVAL_MS);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();
        } catch (Exception e) {
            isRunning = false;
        }
    }

    public void stop() {
        isRunning = false;
        mainHandler.removeCallbacksAndMessages(null);
        synchronized (pendingLines) {
            pendingLines.clear();
            batchPosted = false;
        }
        if (logcatProcess != null) {
            logcatProcess.destroy();
            logcatProcess = null;
        }
        if (readerThread != null) {
            readerThread.interrupt();
            readerThread = null;
        }
    }

    public void clear() {
        mainHandler.removeCallbacksAndMessages(null);
        synchronized (pendingLines) {
            pendingLines.clear();
            batchPosted = false;
        }
        logLines.clear();
        visibleLines.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setFilter(String filter) {
        this.filter = filter == null ? "" : filter.trim().toLowerCase();
        rebuildVisibleLines();
    }

    public void setPackageName(String packageName) {
        if (packageName != null && !packageName.trim().isEmpty()) {
            this.packageName = packageName.trim();
        }
    }

    public String getPackageFilter() {
        return packageFilter;
    }

    public void setPackageFilter(String packageFilter) {
        this.packageFilter = packageFilter == null ? "" : packageFilter.trim();
        packageFilters = new ArrayList<>();
        if (!this.packageFilter.isEmpty()) {
            for (String item : this.packageFilter.split(",")) {
                String trimmed = item.trim();
                if (!trimmed.isEmpty()) {
                    packageFilters.add(trimmed.toLowerCase(Locale.ROOT));
                }
            }
        }
        rebuildVisibleLines();
    }

    public boolean exportTo(File file) {
        if (file == null || logLines.isEmpty()) return false;
        StringBuilder builder = new StringBuilder();
        String stars = "*".repeat(95);
        String blank = " ".repeat(87);
        String formattedDate = new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm:ss", Locale.ENGLISH)
                .format(Calendar.getInstance(Locale.ENGLISH).getTime());

        builder.append(stars).append('\n');
        builder.append(stars).append('\n');
        builder.append("**").append(blank).append("**");
        builder.append("\n**    Exported logcat reader for ").append(packageName)
                .append(" on ").append(formattedDate).append("  **\n");
        builder.append("**").append(blank).append("**\n");
        builder.append(stars).append('\n');
        builder.append(stars).append('\n');

        for (LogLine line : logLines) {
            if (line.isParsed()) {
                builder.append("\n\n|-- Log Type: ").append(line.type).append('\n');
                builder.append("    |-- Date: ").append(line.date).append('\n');
                builder.append("    |-- Tag: ").append(line.header).append('\n');
                builder.append("    |-- Message: ").append(line.body).append('\n');
                builder.append("------------------------------------------------");
            } else {
                builder.append('\n').append(line.raw);
            }
        }
        FileUtil.writeFile(file.getAbsolutePath(), builder.toString());
        return true;
    }

    private void drainBatch() {
        if (!isRunning) return;
        List<String> batch;
        synchronized (pendingLines) {
            batch = new ArrayList<>(pendingLines);
            pendingLines.clear();
            batchPosted = false;
        }
        if (batch.isEmpty()) return;

        int visibleInsertStart = visibleLines.size();
        List<LogLine> parsedBatch = new ArrayList<>();
        for (String line : batch) {
            parsedBatch.add(LogLine.from(line, packageName));
        }
        logLines.addAll(parsedBatch);

        if (logLines.size() > MAX_LINES) {
            int excess = logLines.size() - MAX_LINES;
            logLines.subList(0, excess).clear();
            rebuildVisibleLines();
        } else {
            if (filter.isEmpty()) {
                int added = 0;
                for (LogLine line : parsedBatch) {
                    if (matchesPackageFilter(line)) {
                        visibleLines.add(line);
                        added++;
                    }
                }
                if (added > 0) {
                    adapter.notifyItemRangeInserted(visibleInsertStart, added);
                }
            } else {
                rebuildVisibleLines();
            }
        }

        if (recyclerView != null && !visibleLines.isEmpty()) {
            recyclerView.scrollToPosition(visibleLines.size() - 1);
        }
    }

    private void rebuildVisibleLines() {
        visibleLines.clear();
        if (filter.isEmpty()) {
            for (LogLine line : logLines) {
                if (matchesPackageFilter(line)) {
                    visibleLines.add(line);
                }
            }
        } else {
            for (LogLine line : logLines) {
                if (matchesPackageFilter(line) && line.raw.toLowerCase().contains(filter)) {
                    visibleLines.add(line);
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private boolean matchesPackageFilter(LogLine line) {
        if (packageFilters.isEmpty()) return true;
        String rawLower = line.raw.toLowerCase(Locale.ROOT);
        for (String pkg : packageFilters) {
            if (rawLower.contains(pkg)) {
                return true;
            }
        }
        return false;
    }

    private static int getColorForLevel(LogLine line) {
        char level = line.type.isEmpty() ? 'V' : line.type.charAt(0);
        switch (level) {
            case 'V':
                return COLOR_VERBOSE;
            case 'D':
                return COLOR_DEBUG;
            case 'I':
                return COLOR_INFO;
            case 'W':
                return COLOR_WARN;
            case 'E':
            case 'F':
                return COLOR_ERROR;
            default:
                return COLOR_VERBOSE;
        }
    }

    private static class LogLine {
        final String raw;
        final String packageName;
        final String date;
        final String type;
        final String header;
        final String body;

        LogLine(String raw, String packageName, String date, String type, String header, String body) {
            this.raw = raw;
            this.packageName = packageName;
            this.date = date;
            this.type = type;
            this.header = header;
            this.body = body;
        }

        static LogLine from(String raw, String packageName) {
            Matcher matcher = LOG_PATTERN.matcher(raw);
            if (matcher.matches()) {
                return new LogLine(raw, packageName, matcher.group(1).trim(), matcher.group(2).trim(),
                        matcher.group(3), matcher.group(4));
            }
            matcher = BRIEF_LOG_PATTERN.matcher(raw);
            if (matcher.matches()) {
                return new LogLine(raw, packageName, "", matcher.group(1).trim(),
                        matcher.group(2).trim(), matcher.group(3));
            }
            return new LogLine(raw, packageName, "", "", "", raw);
        }

        boolean isParsed() {
            return !type.isEmpty();
        }

        String displayText() {
            if (isParsed()) {
                String prefix = date.isEmpty()
                        ? packageName + " | " + header
                        : packageName + " | " + date + " | " + header;
                return prefix + "\n" + body;
            }
            return packageName + " | " + raw;
        }
    }

    private class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.log_line);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_logcat_line, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LogLine line = visibleLines.get(position);
            holder.textView.setText(line.displayText());
            holder.textView.setTextColor(getColorForLevel(line));
        }

        @Override
        public int getItemCount() {
            return visibleLines.size();
        }
    }
}
