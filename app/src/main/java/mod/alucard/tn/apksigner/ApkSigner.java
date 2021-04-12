package mod.alucard.tn.apksigner;

import android.content.Context;
import android.util.Log;

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

    public void signWithTestKey(String inputPath, String outputPath, LogCallback callback) {
        Object savedAccessFlags = null;
        Object savedOut = null;
        LogWriter logger = new LogWriter(callback);
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();
        args.add("sign");
        args.add("--in");
        args.add(inputPath);
        args.add("--out");
        args.add(outputPath);
        args.add("--key");
        args.add(new File(context.getFilesDir(), TESTKEY_DIR_IN_FILES + "testkey.pk8").getAbsolutePath());
        args.add("--cert");
        args.add(new File(context.getFilesDir(), TESTKEY_DIR_IN_FILES + "testkey.x509.pem").getAbsolutePath());
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
        if (callback != null) {
            try {
                Field outField = System.class.getField("out");
                Field accessFlagsField = Field.class.getField("accessFlags");
                if (savedOut != null) {
                    if (savedAccessFlags != null) {
                        if (outField.isAccessible()) {
                            if (accessFlagsField.isAccessible()) {
                                outField.set(null, savedOut);
                                accessFlagsField.set(null, savedAccessFlags);
                            } else {
                                logger.write("accessFlagsField isn't accessible anymore, skipping reverting field out in System");
                            }
                        } else {
                            logger.write("outField isn't accessible anymore, skipping reverting field out in System");
                        }
                    } else {
                        logger.write("savedAccessFlags is null, skipping reverting field out in System");
                    }
                } else {
                    logger.write("savedOut is null, skipping reverting field out in System");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.write("Failed to revert field out in System: " + e.getMessage());
            }
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
            if (isLoggingEnabled()) {
                mCache += (char) b;

                if (((char) b) == '\n') {
                    mCallback.onNewLineLogged(mCache);
                }
            }
        }

        private void write(String s) {
            if (isLoggingEnabled()) {
                for (byte b : s.getBytes()) {
                    write(b);
                }
            }
        }

        private boolean isLoggingEnabled() {
            return mCallback != null;
        }
    }
}