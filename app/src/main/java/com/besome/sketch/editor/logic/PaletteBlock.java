package com.besome.sketch.editor.logic;

import static pro.sketchware.utility.ThemeUtils.getColor;
import static pro.sketchware.utility.ThemeUtils.isDarkThemeEnabled;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.wB;
import pro.sketchware.R;
import pro.sketchware.databinding.PaletteBlockBinding;

public class PaletteBlock extends LinearLayout {

    public float f = 0.0F;
    private PaletteBlockBinding binding;
    private Context context;

    public PaletteBlock(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        binding = PaletteBlockBinding.inflate(LayoutInflater.from(context), this, true);
        f = wB.a(context, 1.0F);
    }

    public Ts a(String var1, String var2, String var3) {
        View view = new View(context);
        view.setLayoutParams(getLayoutParams(8.0F));
        binding.blockBuilder.addView(view);
        Rs blockView = new Rs(context, -1, var1, var2, var3);
        blockView.setContentDescription(generateContentDescription(var3));
        blockView.setBlockType(1);
        // main reason why some blocks are not showing because Ts class is using View#LAYER_TYPE_SOFTWARE.
        // we are changing it to fix it.
        blockView.setLayerType(LAYER_TYPE_HARDWARE, null);
        binding.blockBuilder.addView(blockView);
        return blockView;
    }

    public Ts a(String var1, String var2, String var3, String var4) {
        View view = new View(context);
        view.setLayoutParams(getLayoutParams(8.0F));
        binding.blockBuilder.addView(view);
        Rs blockView = new Rs(context, -1, var1, var2, var3, var4);
        blockView.setContentDescription(generateContentDescription(var4));
        blockView.setBlockType(1);
        binding.blockBuilder.addView(blockView);
        return blockView;
    }

    public TextView a(String title) {
        var textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(10.0F);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding((int) (f * 8.0F), 0, (int) (f * 8.0F), 0);

        var cardView = new MaterialCardView(context);
        var params = getLayoutParams(30.0F);
        params.setMargins(0, 0, (int) (f * 4), (int) (f * 6));
        cardView.setLayoutParams(params);
        cardView.setCardBackgroundColor(getColor(context, isDarkThemeEnabled(context) ? R.attr.colorSurfaceContainerHigh : R.attr.colorSurfaceContainerHighest));
        cardView.addView(textView);

        binding.actionsContainer.addView(cardView);
        return textView;
    }

    public void a() {
        binding.blockBuilder.removeAllViews();
        binding.actionsContainer.removeAllViews();
    }

    public void a(String title, int color) {
        var cardView = new MaterialCardView(context);
        var params = getLayoutParams(18.0F);
        params.topMargin = (int) (f * 16.0F);
        cardView.setLayoutParams(params);
        cardView.setCardBackgroundColor(color);
        cardView.setRadius(f * 8f);

        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextColor(getColor(context, isDarkThemeEnabled(context) ? R.attr.colorOnSurface : R.attr.colorOnSurfaceInverse));
        textView.setTextSize(10.0F);
        textView.setGravity(Gravity.CENTER | Gravity.LEFT);
        textView.setPadding((int) (f * 12.0F), 0, (int) (f * 12.0F), 0);
        cardView.addView(textView);

        binding.blockBuilder.addView(cardView);
    }

    public void addDeprecatedBlock(String message, String type, String opCode) {
        if (message != null && !message.isEmpty()) {
            a(message, getColor(context, isDarkThemeEnabled(context) ? R.attr.colorSurfaceContainerHigh : R.attr.colorSurfaceInverse));
        }
        Ts blockView = a("", type, opCode);
        blockView.e = 0xFFBDBDBD;
        blockView.setTag(opCode);
    }

    private String generateContentDescription(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {
            char currentChar = name.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                // Check if previous char is not already a space (for acronyms like "HTTPExample")
                // and if the current char is not part of an acronym (e.g. the TTP in HTTP)
                // For simplicity here, just add a space before any uppercase unless it's followed by lowercase.
                if (i + 1 < name.length() && Character.isLowerCase(name.charAt(i + 1)) || Character.isLowerCase(name.charAt(i - 1))) {
                    result.append(' ');
                }
            }
            result.append(currentChar);
        }
        return result.toString();
    }

    private LinearLayout.LayoutParams getLayoutParams(float heightMultiplier) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (f * heightMultiplier));
    }

    public void setDragEnabled(boolean dragEnabled) {
        if (dragEnabled) {
            binding.scroll.b();
            binding.scrollHorizontal.b();
        } else {
            binding.scroll.a();
            binding.scrollHorizontal.a();
        }
    }

    public void setMinWidth(int minWidth) {
        binding.scroll.setMinimumWidth(minWidth - (int) (f * 5.0F));
        binding.scrollHorizontal.setMinimumWidth(minWidth - (int) (f * 5.0F));
        getLayoutParams().width = minWidth;
    }

    public void setUseScroll(boolean useScroll) {
        binding.scroll.setUseScroll(useScroll);
        binding.scrollHorizontal.setUseScroll(useScroll);
    }
}
