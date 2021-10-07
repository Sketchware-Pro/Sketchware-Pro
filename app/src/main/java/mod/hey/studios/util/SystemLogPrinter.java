package mod.hey.studios.util;

import java.io.OutputStream;
import java.io.PrintStream;

import kellinwood.logging.LogManager;
import kellinwood.logging.Logger;
import mod.agus.jcoderz.lib.FileUtil;

public class SystemLogPrinter {

    private static final String PATH = FileUtil.getExternalStorageDir().concat("/.sketchware/debug.txt");

    public static void start() {
        start(PATH);
    }

    public static void start(String path) {
        // Remove logging in Kellinwood's zipsigner
        LogManager.setLoggerFactory(category -> new Logger() {
            @Override
            public boolean isErrorEnabled() {
                return false;
            }

            @Override
            public void error(String message) {
            }

            @Override
            public void error(String message, Throwable t) {
            }

            @Override
            public boolean isWarnEnabled() {
                return false;
            }

            @Override
            public void warn(String message) {
            }

            @Override
            public void warn(String message, Throwable t) {
            }

            @Override
            public boolean isInfoEnabled() {
                return false;
            }

            @Override
            public void info(String message) {
            }

            @Override
            public void info(String message, Throwable t) {
            }

            @Override
            public boolean isDebugEnabled() {
                return false;
            }

            @Override
            public void debug(String message) {
            }

            @Override
            public void debug(String message, Throwable t) {
            }
        });

        // Reset
        FileUtil.writeFile(path, "");

        PrintStream ps = new PrintStream(new OutputStream() {
            private String cache;

            @Override
            public void write(int b) {
                if (cache == null) cache = "";

                if (((char) b) == '\n') {
                    // Write each line printed to the specified path
                    FileUtil.writeFile(path,
                            FileUtil.readFile(path) + "\n" + cache);

                    cache = "";
                } else {
                    cache += (char) b;
                }
            }
        });

        System.setOut(ps);
        System.setErr(ps);
    }
}