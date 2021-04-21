package mod.alucard.tn.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import mod.SketchwareUtil;


public class LayoutUtilities {

    /**
     * Simple usage of this class
     * <p>
     * LinearLayout linearLayout = new LinearLayout(this);
     * LayoutUtilities layoutUtilities = new LayoutUtilities(this);
     * <p>
     * layoutUtilities.layoutParams(linearLayout, -1, -1, 1.0f);
     * layoutUtilities.makeToolbar(true);
     * <p>
     * linearLayout.addView(layoutUtilities.linearLayout,
     * layoutUtilities.layoutParams(layoutUtilities.linearLayout,
     * layoutUtilities.Dp(this, 50), -1, 0.0f ));
     * linearLayout.setPadding(0, 0, 0, 0);
     * <p>
     * layoutUtilities.back.setOnClickListener(v -> {
     * finish();
     * });
     * <p>
     * layoutUtilities.textView.setText("Assets Manager");
     * TooltipCompat.setTooltipText(layoutUtilities.tools, "Tools Image Long Clicked");
     * setContentView(linearLayout);
     */

    public LinearLayout linearLayout;
    public Context context;
    public TextView textView;
    public ImageView tools;
    public ImageView back;

    public LayoutUtilities(Context c) {
        context = c;
    }

    public LinearLayout makeToolbar(Boolean isToolsImageEnabled) {
        linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.parseColor("#ff008dcd"));
        linearLayout.setPadding(10, 8, 4, 8);

        back = new ImageView(context);
        back.setImageResource(Resources.drawable.arrow_back_white_48dp);
        back.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        linearLayout.addView(back, layoutParams(back, (int) SketchwareUtil.getDip(30), (int) SketchwareUtil.getDip(30), 0.0f));

        textView = new TextView(context);
        textView.setTextSize(15);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -1, 1.0f);
        params.setMargins(8, 0, 0, 0);
        textView.setLayoutParams(params);
        textView.setPadding(10, 4, 8, 6);

        linearLayout.addView(textView);

        if (isToolsImageEnabled) {
            tools = new ImageView(context);
            layoutParams(tools, (int) SketchwareUtil.getDip(30),(int) SketchwareUtil.getDip(30), 0.0f);
            tools.setPadding(8, 8, 8, 8);
            tools.setBackgroundResource(Resources.drawable.add_file_48);
            tools.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            linearLayout.addView(tools);
        }
        return linearLayout;
    }

    public LinearLayout.LayoutParams layoutParams(View view, int height, int width, float weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height, weight);
        view.setLayoutParams(params);
        return params;
    }
}
