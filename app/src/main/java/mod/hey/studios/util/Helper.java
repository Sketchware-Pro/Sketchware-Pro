package mod.hey.studios.util;

import static pro.sketchware.SketchApplication.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.xB;
import pro.sketchware.lib.base.BaseTextWatcher;
import pro.sketchware.utility.FileUtil;

public class Helper {
    public static final TypeToken<HashMap<String, Object>> TYPE_MAP = new TypeToken<>() {
    };
    public static final TypeToken<ArrayList<HashMap<String, Object>>> TYPE_MAP_LIST = new TypeToken<>() {
    };
    public static final TypeToken<ArrayList<String>> TYPE_STRING = new TypeToken<>() {
    };
    public static final TypeToken<HashMap<String, String>> TYPE_STRING_MAP = new TypeToken<>() {
    };

    private Helper() {
    }

    public static void fixFileprovider() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure").invoke(null);
            } catch (Exception e) {
                Log.e("Helper", "An error occurred while trying to fix death on file URI exposure: " + e.getMessage(), e);
            }
        }
    }

    public static ArrayList<String> createStringList(String... strings) {
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, strings);
        return result;
    }

    public static void setViewsVisibility(boolean hide, View... views) {
        for (View view : views) {
            if (view != null) {
                view.setVisibility(hide ? View.GONE : View.VISIBLE);
            }
        }
    }

    public static String getResString(View target, @StringRes int resId) {
        if (target.isInEditMode()) {
            return target.getContext().getString(resId);
        } else {
            return getResString(resId);
        }
    }

    public static String getResString(@StringRes int resId) {
        return xB.b().a(getContext(), resId);
    }

    public static String getResString(@StringRes int resId, Object... formatArgs) {
        return String.format(getResString(resId), formatArgs);
    }

    public static View.OnClickListener getBackPressedClickListener(final ComponentActivity activity) {
        return v -> activity.getOnBackPressedDispatcher().onBackPressed();
    }

    public static DialogDismissListener getDialogDismissListener(final DialogInterface dialog) {
        return new DialogDismissListener(dialog);
    }

    public static void applyRipple(Context context, View view) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);

        view.setBackgroundResource(typedValue.resourceId);
        view.setClickable(true);
    }

    public static void addBasicTextChangedListener(TextInputEditText input, BasicTextChangedListener listener) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onTextChanged(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @NonNull
    public static String getText(TextView textView) {
        CharSequence text = textView.getText();
        if (!TextUtils.isEmpty(text)) {
            return text.toString();
        } else {
            return "";
        }
    }

    public static void applyRippleToToolbarView(View view) {
        GradientDrawable content = new GradientDrawable();
        content.setCornerRadius(90);

        view.setBackground(
                new RippleDrawable(
                        new ColorStateList(
                                new int[][]{new int[]{0}},
                                new int[]{Color.parseColor("#64b5f6")}
                        ),
                        content,
                        null
                )
        );
    }

    /**
     * Applies a boxy ripple effect to a view.
     *
     * @param target        The view to apply the effect on
     * @param rippleColor   The effect's color
     * @param standardColor The view's color when untouched
     */
    public static void applyRippleEffect(final View target, final int rippleColor, int standardColor) {
        if (!target.isClickable()) {
            target.setClickable(true);
        }

        target.setBackground(
                new RippleDrawable(
                        new ColorStateList(
                                new int[][]{new int[]{}},
                                new int[]{rippleColor}
                        ),
                        new ColorDrawable(standardColor),
                        null
                )
        );
    }

    public static String trimPath(String path) {
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    public static void sortPaths(ArrayList<String> paths) {
        ArrayList<String> directories = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();

        for (String str : paths) {
            if (FileUtil.isDirectory(str)) {
                directories.add(str);
            } else {
                files.add(str);
            }
        }

        // ignore the warnings, we need this for compatibility with pre-API level 24
        //noinspection Java8ListSort
        Collections.sort(directories, String.CASE_INSENSITIVE_ORDER);
        //noinspection Java8ListSort
        Collections.sort(files, String.CASE_INSENSITIVE_ORDER);
        paths.clear();
        paths.addAll(directories);
        paths.addAll(files);
    }

    public static void addClearErrorOnTextChangeTextWatcher(TextInputLayout target) {
        target.getEditText().addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                target.setError(null);
            }
        });
    }

    public interface BasicTextChangedListener {
        void onTextChanged(String str);
    }

    /**
     * A listener class for dismissing {@link Dialog}s.
     */
    public static class DialogDismissListener implements View.OnClickListener {

        private final DialogInterface dialog;

        /**
         * Constructor for {@link Helper#getDialogDismissListener(Dialog)}.
         */
        private DialogDismissListener(DialogInterface dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    }
}
