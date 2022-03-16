package mod;

import static com.besome.sketch.SketchApplication.getContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import a.a.a.bB;
import mod.jbk.util.LogUtil;

public class SketchwareUtil {

    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {
        Collections.sort(listMap, (_compareMap1, _compareMap2) -> {
            if (isNumber) {
                int _count1 = Integer.parseInt(_compareMap1.get(key).toString());
                int _count2 = Integer.parseInt(_compareMap2.get(key).toString());
                if (ascending) {
                    return _count1 < _count2 ? -1 : 0;
                } else {
                    return _count1 > _count2 ? -1 : 0;
                }
            } else {
                if (ascending) {
                    return (_compareMap1.get(key).toString()).compareTo(_compareMap2.get(key).toString());
                } else {
                    return (_compareMap2.get(key).toString()).compareTo(_compareMap1.get(key).toString());
                }
            }
        });
    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * @param v A View which should be visible and "connected" to the currently focused view
     * @see SketchwareUtil#hideKeyboard()
     */
    public static void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Hide the keyboard. This may show the keyboard if it's not opened.
     * Use {@link SketchwareUtil#hideKeyboard(View)} instead, if possible.
     *
     * @see SketchwareUtil#hideKeyboard(View)
     */
    public static void hideKeyboard() {
        InputMethodManager _inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        _inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * @param v A View which should be focused (and wants to receive IME input)
     */
    public static void showKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, 0);
    }

    /**
     * Use {@link SketchwareUtil#showKeyboard(View)} instead, if possible.
     */
    public static void showKeyboard() {
        InputMethodManager _inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        _inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void showMessage(Context context, String _s) {
        Toast.makeText(context, _s, Toast.LENGTH_SHORT).show();
    }

    public static int getLocationX(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }

    public static int getLocationY(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    public static int getRandom(int _min, int _max) {
        return new Random().nextInt(_max - _min + 1) + _min;
    }

    public static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    public static float getDip(int input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, getContext().getResources().getDisplayMetrics());
    }

    public static int getDisplayWidthPixels() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDisplayHeightPixels() {
        return getContext().getResources().getDisplayMetrics().heightPixels;
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
            LogUtil.e("SketchwareUtil", "Failed to toast regular message, " +
                    "Toast's message was: \"" + message + "\"", e);
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
            LogUtil.e("SketchwareUtil", "Failed to toast regular message, " +
                    "Toast's message was: \"" + message + "\"", e);
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

}