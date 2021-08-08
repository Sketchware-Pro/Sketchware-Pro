package mod.alucard.tn.apksigner;

import android.content.Context;
import android.util.Log;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.apksigner.ApkSignerTool;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class ApkSigner {

    private static final String TESTKEY_DIR_IN_FILES = "libs" + File.separator + "testkey" + File.separator;
    public Context context;

    public ApkSigner(Context c) {
        context = c;
    }

    /**
     * Sign an APK with testkey.
     *
     * @param inputPath  The APK file to sign
     * @param outputPath File to output the signed APK to
     * @param callback   Callback for System.out during signing. May be null
     */
    public void signWithTestKey(@NonNull String inputPath, @NonNull String outputPath, @Nullable LogCallback callback) {
        LogWriter logger = new LogWriter(callback);
        long savedTimeMillis = System.currentTimeMillis();
        PrintStream oldOut = System.out;

        ArrayList<String> args = new ArrayList<String>() {{
            add("sign");
            add("--in");
            add(inputPath);
            add("--out");
            add(outputPath);
            add("--key");
            add(new File(context.getFilesDir(), TESTKEY_DIR_IN_FILES + "testkey.pk8").getAbsolutePath());
            add("--cert");
            add(new File(context.getFilesDir(), TESTKEY_DIR_IN_FILES + "testkey.x509.pem").getAbsolutePath());
        }};

        logger.write("Signing an APK file with these arguments: " + args);

        /* If the signing has a callback, we need to change System.out to our logger */
        if (callback != null) {
            System.setOut(new PrintStream(logger));
        }

        try {
            ApkSignerTool.main(args.toArray(new String[0]));
        } catch (Exception e) {
            logger.write("An error occurred while trying to sign the APK file " + inputPath +
                    " and outputting it to " + outputPath + ": " + e.getMessage() + "\n" +
                    "Stack trace: " + Log.getStackTraceString(e));
        }

        logger.write("Signing an APK file took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

        /* Try to revert System.out */
        if (callback != null) {
            System.setOut(oldOut);
        }
    }

    public interface LogCallback {
        void onNewLineLogged(String line);
    }

    private static class LogWriter extends OutputStream {

        private final LogCallback mCallback;
        private String mCache;

        private LogWriter(LogCallback callback) {
            mCallback = callback;
        }

        @Override
        public void write(int b) {
            if (isLoggingDisabled()) return;

            mCache += (char) b;

            if (((char) b) == '\n') {
                mCallback.onNewLineLogged(mCache);
            }
        }

        private void write(String s) {
            if (isLoggingDisabled()) return;

            for (byte b : s.getBytes()) {
                write(b);
            }
        }

        private boolean isLoggingDisabled() {
            return mCallback == null;
        }
    }
}