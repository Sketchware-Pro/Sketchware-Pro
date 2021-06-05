package mod.alucard.tn.apksigner;

import android.content.Context;
import android.util.Log;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.apksigner.ApkSignerTool;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
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
        Object savedAccessFlags = null;
        Object savedOut = null;

        LogWriter logger = new LogWriter(callback);
        long savedTimeMillis = System.currentTimeMillis();

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

        logger.write("Signing an APK file with these arguments: " + args.toString());

        /* If the signing has a callback, we need to change System.out to our logger */
        if (callback != null) {
            try {
                Field outField = System.class.getField("out");
                Field accessFlagsField = Field.class.getField("accessFlags");

                accessFlagsField.setAccessible(true);
                savedAccessFlags = accessFlagsField.get(null);
                accessFlagsField.set(outField, outField.getModifiers() & -17);
                outField.setAccessible(true);
                savedOut = outField.get(null);
                outField.set(null, new PrintStream(logger));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.write("Failed to get/set field out in System: " + e.getMessage());
            }
        }

        try {
            ApkSignerTool.main(args.toArray(new String[0]));
        } catch (Exception e) {
            logger.write("An error occurred while trying to sign the APK file " + inputPath
                    + " and outputting it to " + outputPath
                    + ": " + e.getMessage() + "\nStack trace: " + Log.getStackTraceString(e));
        }

        logger.write("Signing an APK file took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

        /* Try to revert System.out */
        if (callback == null) {
            return;
        }

        try {
            Field outField = System.class.getField("out");
            Field accessFlagsField = Field.class.getField("accessFlags");

            if (savedOut == null) {
                logger.write("savedOut is null, skipping reverting field out in System");
                return;
            }

            if (savedAccessFlags == null) {
                logger.write("savedAccessFlags is null, skipping reverting field out in System");
                return;
            }

            if (!outField.isAccessible()) {
                logger.write("outField isn't accessible anymore, skipping reverting field out in System");
                return;
            }

            if (!accessFlagsField.isAccessible()) {
                logger.write("accessFlagsField isn't accessible anymore, skipping reverting field out in System");
                return;
            }

            outField.set(null, savedOut);
            accessFlagsField.set(null, savedAccessFlags);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.write("Failed to revert field out in System: " + e.getMessage());
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