package mod.hey.studios.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import kellinwood.logging.LogManager;
import kellinwood.logging.Logger;
import mod.agus.jcoderz.lib.FileUtil;
import mod.jbk.util.LogUtil;

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

        try {
            FileWriter writer = new FileWriter(path, true);
            PrintStream ps = new PrintStream(new SpecializedOutputStream(writer), true);

            System.setOut(ps);
            System.setErr(ps);
        } catch (IOException e) {
            LogUtil.e("SystemLogPrinter", "IOException while creating FileWriter to " + path, e);
        }
    }

    private static class SpecializedOutputStream extends OutputStream {

        private final FileWriter writer;

        SpecializedOutputStream(FileWriter writer) {
            this.writer = writer;
        }

        @Override
        public void write(int b) throws IOException {
            writer.write(b);
        }

        @Override
        public void flush() throws IOException {
            writer.flush();
            super.flush();
        }

        @Override
        public void close() throws IOException {
            writer.close();
            super.close();
        }
    }
}