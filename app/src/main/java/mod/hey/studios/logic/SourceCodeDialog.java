package mod.hey.studios.logic;

import static mod.SketchwareUtil.getDip;

import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.mB;

public class SourceCodeDialog {

    public static void show(Context context, String code) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(layoutParams);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        horizontalScrollView.setLayoutParams(layoutParams);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        horizontalScrollView.addView(linearLayout);
        scrollView.addView(horizontalScrollView);

        TextView textView = newTextView(context, mB.a(context, code));
        linearLayout.addView(textView);

        new AlertDialog.Builder(context)
                .setTitle("Source code")
                .setView(scrollView)
                .setPositiveButton(Resources.string.common_word_close, null)
                .show();
    }

    private static TextView newTextView(Context c, SpannableStringBuilder paramSpannableStringBuilder) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(c);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(
                (int) getDip(8),
                (int) getDip(12),
                (int) getDip(8),
                (int) getDip(12)
        );
        textView.setTextColor(0xff000000);
        textView.setText(paramSpannableStringBuilder);
        textView.setTextSize(14f);
        textView.setHorizontalScrollBarEnabled(true);
        textView.setTextIsSelectable(true);
        return textView;
    }
}
