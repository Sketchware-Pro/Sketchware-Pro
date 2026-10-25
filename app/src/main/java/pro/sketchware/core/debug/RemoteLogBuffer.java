package pro.sketchware.core.debug;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/** Small transport buffer consumed by the existing Logcat viewer, including logs captured before it opens. */
public final class RemoteLogBuffer {
    public record Entry(long sequence, String packageName, String line) { }
    public record Snapshot(long generation, long lastSequence, List<Entry> entries) { }
    private final ArrayDeque<Entry> entries = new ArrayDeque<>();
    private long sequence, generation;

    public synchronized void append(String packageName, String line) {
        entries.addLast(new Entry(++sequence, packageName, line.length() > 2000 ? line.substring(0, 2000) : line));
        while (entries.size() > 500) entries.removeFirst();
    }

    public synchronized void clear() { entries.clear(); generation++; }

    public synchronized Snapshot snapshot(long afterSequence, long knownGeneration) {
        List<Entry> fresh = new ArrayList<>();
        for (Entry entry : entries) if (knownGeneration != generation || entry.sequence() > afterSequence) fresh.add(entry);
        return new Snapshot(generation, sequence, fresh);
    }
}
