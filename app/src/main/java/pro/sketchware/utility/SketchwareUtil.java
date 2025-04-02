package pro.sketchware.utility;

import static pro.sketchware.SketchApplication.getContext;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.TypedValue;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

import a.a.a.bB;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;

public class SketchwareUtil {

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showMessage(Context context, String _s) {
        Toast.makeText(context, _s, Toast.LENGTH_SHORT).show();
    }

    public static int getRandom(int _min, int _max) {
        return new Random().nextInt(_max - _min + 1) + _min;
    }

    public static float getDip(int input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, getContext().getResources().getDisplayMetrics());
    }

    /**
     * Show a Toast styled Sketchware-like.
     *
     * @param message The message to toast
     * @param length  The toast's length, either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void toast(String message, int length) {
        try {
            bB.a(getContext(), message, length).show();
        } catch (RuntimeException e) {
            LogUtil.e("SketchwareUtil", "Failed to toast regular message, " + "Toast's message was: \"" + message + "\"", e);
        }
    }

    /**
     * Show a Toast styled Sketchware-like and with length {@link Toast#LENGTH_SHORT}.
     *
     * @param message The message to toast
     */
    public static void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    /**
     * Show an error Toast styled Sketchware-like and with length {@link Toast#LENGTH_SHORT}.
     *
     * @param message The message to toast
     * @param length  The toast's length, either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void toastError(String message, int length) {
        try {
            bB.b(getContext(), message, length).show();
        } catch (RuntimeException e) {
            LogUtil.e("SketchwareUtil", "Failed to toast regular message, " + "Toast's message was: \"" + message + "\"", e);
        }
    }

    /**
     * Show an error Toast styled Sketchware-like and with length {@link Toast#LENGTH_SHORT}.
     *
     * @param message The message to toast
     */
    public static void toastError(String message) {
        toastError(message, Toast.LENGTH_SHORT);
    }

    /**
     * Converts dps into pixels.
     *
     * @param dp The amount of density-independent pixels to convert
     * @return {@code dp} in pixels
     */
    public static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    /**
     * @return An optional display name of a document picked with Storage access framework.
     */
    public static Optional<String> getSafDocumentDisplayName(Uri uri) {
        return doSingleStringContentQuery(uri, DocumentsContract.Document.COLUMN_DISPLAY_NAME);
    }

    public static Optional<String> doSingleStringContentQuery(Uri uri, String columnName) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, new String[]{columnName}, null, null, null)) {
            if (cursor.moveToFirst() && !cursor.isNull(0)) {
                return Optional.of(cursor.getString(0));
            } else {
                return Optional.empty();
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            LogUtil.e("SketchwareUtil", "Failed to do single string content query for Uri " + uri + " and column name " + columnName, e);
            return Optional.empty();
        }
    }

    public static void copySafDocumentToTempFile(Uri document, Activity context, String tempFileExtension, Consumer<File> tempFileConsumer, Consumer<IOException> exceptionHandler) {
        new Thread(() -> {
            try (ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(document, "r"); FileInputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor())) {
                File temporaryFile = File.createTempFile("document", "." + tempFileExtension);
                try (FileOutputStream outputStream = new FileOutputStream(temporaryFile); BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        bufferedOutputStream.write(buffer, 0, length);
                    }
                }

                context.runOnUiThread(() -> tempFileConsumer.accept(temporaryFile));
            } catch (IOException e) {
                exceptionHandler.accept(e);
            }
        }).start();
    }

    /**
     * @param componentLabel Label of component that failed to be parsed, e.g. Block selector menus
     */
    public static void showFailedToParseJsonDialog(Activity context, File json, String componentLabel, Consumer<Void> afterRenameLogic) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setIcon(R.drawable.break_warning_96_red);
        dialog.setTitle("Couldn't get " + componentLabel);
        dialog.setMessage("Failed to parse " + componentLabel + " from file " + json + ". Fix by renaming old file to " + json.getName() + ".bak? " + "If not, no " + componentLabel + " will be used.");
        dialog.setPositiveButton("Rename", (v, which) -> {
            FileUtil.renameFile(json.getAbsolutePath(), json.getAbsolutePath() + ".bak");
            afterRenameLogic.accept(null);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    public static void showAnErrorOccurredDialog(Activity activity, String errorMessage) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setTitle(Helper.getResString(R.string.common_error_an_error_occurred));
        builder.setIcon(R.drawable.ic_mtrl_warning);
        builder.setMessage(errorMessage);
        builder.setPositiveButton("Okay", null);
        builder.show();
    }

    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {
        Collections.sort(listMap, new Comparator<HashMap<String, Object>>() {
            public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {
                if (isNumber) {
                    int _count1 = Integer.valueOf(_compareMap1.get(key).toString());
                    int _count2 = Integer.valueOf(_compareMap2.get(key).toString());
                    if (ascending) {
                        return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;
                    } else {
                        return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;
                    }
                } else {
                    if (ascending) {
                        return (_compareMap1.get(key).toString()).compareTo(_compareMap2.get(key).toString());
                    } else {
                        return (_compareMap2.get(key).toString()).compareTo(_compareMap1.get(key).toString());
                    }
                }
            }
        });
    }

}