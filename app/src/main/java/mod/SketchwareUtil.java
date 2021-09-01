package mod;

import static com.besome.sketch.SketchApplication.getContext;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import a.a.a.bB;
import mod.jbk.util.LogUtil;

public class SketchwareUtil {

    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int BOTTOM = 3;

    public static void CustomToast(String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity) {
        Toast toast = Toast.makeText(getContext(), _message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextSize(_textSize);
        textView.setTextColor(_textColor);
        textView.setGravity(Gravity.CENTER);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(_bgColor);
        drawable.setCornerRadius(_radius);
        view.setBackground(drawable);
        view.setPadding(15, 10, 15, 10);
        view.setElevation(10);

        switch (_gravity) {
            case TOP:
                toast.setGravity(Gravity.TOP, 0, 150);
                break;

            case CENTER:
                toast.setGravity(Gravity.CENTER, 0, 0);
                break;

            case BOTTOM:
                toast.setGravity(Gravity.BOTTOM, 0, 150);
                break;
        }
        toast.show();
    }

    public static void CustomToastWithIcon(String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity, int _icon) {
        Toast toast = Toast.makeText(getContext(), _message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextSize(_textSize);
        textView.setTextColor(_textColor);
        textView.setCompoundDrawablesWithIntrinsicBounds(_icon, 0, 0, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setCompoundDrawablePadding(10);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(_bgColor);
        drawable.setCornerRadius(_radius);
        view.setBackground(drawable);
        view.setPadding(10, 10, 10, 10);
        view.setElevation(10);

        switch (_gravity) {
            case TOP:
                toast.setGravity(Gravity.TOP, 0, 150);
                break;

            case CENTER:
                toast.setGravity(Gravity.CENTER, 0, 0);
                break;

            case BOTTOM:
                toast.setGravity(Gravity.BOTTOM, 0, 150);
                break;
        }
        toast.show();
    }

    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {
        Collections.sort(listMap, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {
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
            }
        });
    }

    public static void CropImage(Activity _activity, String _path, int _requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            File _file = new File(_path);
            Uri _contentUri = Uri.fromFile(_file);
            intent.setDataAndType(_contentUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 280);
            intent.putExtra("outputY", 280);
            intent.putExtra("return-data", false);
            _activity.startActivityForResult(intent, _requestCode);
        } catch (ActivityNotFoundException _e) {
            Toast.makeText(_activity, "Your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String copyFromInputStream(InputStream _inputStream) {
        ByteArrayOutputStream _outputStream = new ByteArrayOutputStream();
        byte[] _buf = new byte[1024];
        int _i;
        try {
            while ((_i = _inputStream.read(_buf)) != -1) {
                _outputStream.write(_buf, 0, _i);
            }
            _outputStream.close();
            _inputStream.close();
        } catch (IOException ignored) {
        }

        return _outputStream.toString();
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

    public static void getAllKeysFromMap(Map<String, Object> _map, ArrayList<String> _output) {
        if (_output == null) return;
        _output.clear();
        if (_map == null || _map.size() < 1) return;
        for (Map.Entry<String, Object> stringObjectEntry : _map.entrySet()) {
            _output.add((String) ((Map.Entry<?, ?>) stringObjectEntry).getKey());
        }
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

    public static int dpToSp(float dp) {
        return (int) (dpToPx(dp) / getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}