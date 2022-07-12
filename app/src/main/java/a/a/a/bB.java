package a.a.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class bB {

    public static final int TOAST_NORMAL = 0;
    public static final int TOAST_WARNING = 1;

    public static Toast a(Context context, int resString, int duration) {
        return a(context, Helper.getResString(resString), duration, 48, 0.0f, 64.0f, TOAST_NORMAL);
    }

    public static Toast a(Context context, CharSequence charSequence, int duration) {
        return a(context, charSequence, duration, 48, 0.0f, 64.0f, TOAST_NORMAL);
    }

    public static Toast a(Context context, CharSequence charSequence, int duration, int gravity, float xOffset, float yOffset) {
        View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
        LinearLayout linearLayout = inflate.findViewById(R.id.custom_toast_container);
        ((TextView) inflate.findViewById(R.id.tv_stoast)).setText(charSequence.toString());
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(
                gravity,
                (int) xOffset,
                (int) yOffset
        );
        toast.setView(inflate);
        return toast;
    }

    public static Toast a(Context context, CharSequence charSequence, int duration, int gravity, float xOffset, float yOffset, int toastType) {
        try {
            View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
            LinearLayout linearLayout = inflate.findViewById(R.id.custom_toast_container);
            TextView textView = inflate.findViewById(R.id.tv_stoast);
            if (toastType != TOAST_WARNING) {
                linearLayout.setBackgroundResource(R.drawable.bg_toast_normal);
                textView.setTextColor(context.getResources().getColor(R.color.scolor_black_01));
            } else {
                linearLayout.setBackgroundResource(R.drawable.bg_toast_warning);
                textView.setTextColor(context.getResources().getColor(R.color.scolor_red_02));
            }
            textView.setText(charSequence.toString());
            Toast toast = new Toast(context);
            toast.setDuration(duration);
            toast.setGravity(
                    gravity,
                    (int) wB.a(context, xOffset),
                    (int) wB.a(context, yOffset)
            );
            toast.setView(inflate);
            return toast;
        } catch (Exception e) {
            return Toast.makeText(context, charSequence, duration);
        }
    }

    public static Toast b(Context context, int resString, int duration) {
        return a(context, Helper.getResString(resString), duration, 48, 0.0f, 64.0f, TOAST_WARNING);
    }

    public static Toast b(Context context, CharSequence charSequence, int duration) {
        return a(context, charSequence, duration, 48, 0.0f, 64.0f, TOAST_WARNING);
    }
}