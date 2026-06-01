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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ide.sketchware.R;

public class LogcatPanel {

    private static final int MAX_LINES = 5000;
    private static final long BATCH_INTERVAL_MS = 100;
    private static final int COLOR_VERBOSE = 0xFF888888;
    private static final int COLOR_DEBUG = 0xFF2196F3;
    private static final int COLOR_INFO = 0xFF4CAF50;
    private static final int COLOR_WARN = 0xFFFF9800;
    private static final int COLOR_ERROR = 0xFFF44336;

    private final List<String> logLines = new ArrayList<>();
    private final List<String> pendingLines = new LinkedList<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private Process logcatProcess;
    private Thread readerThread;
    private volatile boolean isRunning = false;
    private boolean batchPosted = false;

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
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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

        int insertStart = logLines.size();
        logLines.addAll(batch);

        if (logLines.size() > MAX_LINES) {
            int excess = logLines.size() - MAX_LINES;
            logLines.subList(0, excess).clear();
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyItemRangeInserted(insertStart, batch.size());
        }

        if (recyclerView != null) {
            recyclerView.scrollToPosition(logLines.size() - 1);
        }
    }

    private static int getColorForLevel(String line) {
        if (line.length() < 2) return COLOR_VERBOSE;
        char level = line.charAt(0);
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
            String line = logLines.get(position);
            holder.textView.setText(line);
            holder.textView.setTextColor(getColorForLevel(line));
        }

        @Override
        public int getItemCount() {
            return logLines.size();
        }
    }
}
