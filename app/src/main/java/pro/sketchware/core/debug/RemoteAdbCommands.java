package pro.sketchware.core.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Device operations kept independent of Android so failure paths can be tested without a phone. */
public final class RemoteAdbCommands {
    public interface Channel extends AutoCloseable {
        InputStream input();
        OutputStream output();
        @Override void close() throws IOException;
    }

    public interface Transport {
        Channel open(String service) throws Exception;
    }

    private static final String EXIT = "__SKETCHWARE_REMOTE_EXIT__:";
    private final Transport transport;

    public RemoteAdbCommands(Transport transport) { this.transport = transport; }

    public int currentUser() throws Exception {
        String result = shell("am get-current-user").trim();
        if (!result.matches("[0-9]{1,6}")) throw new IOException("Cannot determine device user: " + result);
        return Integer.parseInt(result);
    }

    public void install(File apk, int user, IntConsumer progress) throws Exception {
        long size = apk.length();
        if (!apk.isFile() || size == 0) throw new IOException("APK does not exist or is empty");
        // The same raw, length-delimited install service used by adb install. No remote temp APK.
        try (Channel channel = transport.open("exec:cmd package install -r -t --user " + user + " -S " + size);
             InputStream input = new FileInputStream(apk)) {
            byte[] buffer = new byte[16384];
            long sent = 0;
            int previous = -1;
            while (sent < size) {
                int count = input.read(buffer, 0, (int) Math.min(buffer.length, size - sent));
                if (count < 0) throw new IOException("APK changed while uploading");
                channel.output().write(buffer, 0, count);
                channel.output().flush(); // libadb streams need explicit flushing to acknowledge each chunk.
                sent += count;
                int percent = (int) (sent * 100 / size);
                if (percent != previous) { progress.accept(percent); previous = percent; }
            }
            BufferedReader reader = reader(channel.input());
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("Success")) return;
                response.append(line).append('\n');
                if (line.contains("Failure") || line.contains("Error") || response.length() > 16384) break;
            }
            throw new IOException(response.toString().trim());
        }
    }

    public int uid(String packageName, int user) throws Exception {
        validatePackage(packageName);
        String output = shell("cmd package list packages -U --user " + user + " " + packageName);
        Matcher matcher = Pattern.compile("(?m)^package:" + Pattern.quote(packageName) + " uid:([0-9]+)\\s*$").matcher(output);
        if (!matcher.find()) throw new IOException("Cannot find installed package UID: " + output);
        return Integer.parseInt(matcher.group(1));
    }

    public void launch(String packageName, int user) throws Exception {
        validatePackage(packageName);
        String output = shell("cmd package resolve-activity --brief --user " + user
                + " -a android.intent.action.MAIN -c android.intent.category.LAUNCHER " + packageName);
        String component = null;
        for (String line : output.split("\\r?\\n")) {
            line = line.trim();
            if (line.matches("[a-zA-Z0-9_.$]+/[a-zA-Z0-9_.$]+") && line.startsWith(packageName + "/")) component = line;
        }
        if (component == null) throw new IOException("No launcher activity: " + output);
        String result = shell("am start --user " + user + " -S -n '" + component + "'");
        if (result.contains("Error:") || result.contains("Exception")) throw new IOException(result);
    }

    public String logService(int uid, String since) {
        if (uid < 0) throw new IllegalArgumentException("Invalid UID");
        if (!since.matches("[0-9]{9,}\\.000")) throw new IllegalArgumentException("Invalid log timestamp");
        return "shell:logcat -b main -b system -b crash --uid=" + uid + " -v threadtime -T '" + since + "'";
    }

    public String shell(String command) throws Exception {
        try (Channel channel = transport.open("shell:" + command + "; printf '\\n" + EXIT + "%s\\n' \"$?\"")) {
            BufferedReader reader = reader(channel.input());
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(EXIT)) {
                    if (!line.substring(EXIT.length()).trim().equals("0")) throw new IOException(output.toString().trim());
                    return output.toString();
                }
                output.append(line).append('\n');
                if (output.length() > 65536) throw new IOException("Device response is too large");
            }
            throw new IOException("Device disconnected before command completed");
        }
    }

    public static void validatePackage(String packageName) {
        if (packageName == null || !packageName.matches("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)+")) {
            throw new IllegalArgumentException("Invalid application ID");
        }
    }

    static BufferedReader reader(InputStream input) {
        return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    }
}
