package com.besome.sketch.editor.logic;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import pro.sketchware.R;
import pro.sketchware.databinding.LogicEditorTopMenuBinding;

import a.a.a.xB;
import pro.sketchware.utility.ThemeUtils;

public class LogicTopMenu extends LinearLayout {

    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private int colorSurfaceContainerHigh;
    private int colorDefault;
    private int colorOnDrag;

    private LogicEditorTopMenuBinding binding;

    public LogicTopMenu(Context context) {
        super(context);
        initialize(context);
    }

    public LogicTopMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        binding = LogicEditorTopMenuBinding.inflate(LayoutInflater.from(context), this, true);
        binding.tvDelete.setText(xB.b().a(getContext(), R.string.common_word_delete));
        binding.tvCopy.setText(xB.b().a(getContext(), R.string.common_word_duplicate));
        binding.tvFavorite.setText(xB.b().a(getContext(), R.string.common_word_collection));
        binding.tvDetail.setText(xB.b().a(getContext(), R.string.common_word_detail));
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorSurfaceContainerHigh, typedValue, true);
        colorSurfaceContainerHigh = typedValue.data;
        colorDefault = getResources().getColor(R.color.view_property_tab_deactive_text);
        colorOnDrag = Color.WHITE;
    }

    public void a(boolean var1) {
        j = var1;
        if (var1) {
            binding.layoutCopy.setCardBackgroundColor(getResources().getColor(R.color.scolor_green_normal));
            binding.tvCopy.setTextColor(colorOnDrag);
            binding.ivCopy.setColorFilter(colorOnDrag);
        } else {
            binding.layoutCopy.setCardBackgroundColor(colorSurfaceContainerHigh);
            binding.tvCopy.setTextColor(colorDefault);
            binding.ivCopy.setColorFilter(colorDefault);
        }

    }

    public boolean a() {
        return j;
    }

    public boolean a(float var1, float var2) {
        int var3 = binding.layoutCopy.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
            return false;
        } else {
            int[] var5 = new int[2];
            binding.layoutCopy.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + binding.layoutCopy.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + binding.layoutCopy.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void b(boolean var1) {
        i = var1;
        if (var1) {
            binding.layoutDelete.setCardBackgroundColor(getResources().getColor(R.color.scolor_red_02));
            binding.tvDelete.setTextColor(colorOnDrag);
            binding.ivTrash.setColorFilter(colorOnDrag);
        } else {
            binding.layoutDelete.setCardBackgroundColor(colorSurfaceContainerHigh);
            binding.tvDelete.setTextColor(colorDefault);
            binding.ivTrash.setColorFilter(colorDefault);
        }

    }

    public boolean b() {
        return i;
    }

    public boolean b(float var1, float var2) {
        int var3 = binding.layoutDelete.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
            return false;
        } else {
            int[] var5 = new int[2];
            binding.layoutDelete.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + binding.layoutDelete.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + binding.layoutDelete.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void c(boolean var1) {
        l = var1;
        if (var1) {
            binding.layoutDetail.setCardBackgroundColor(getResources().getColor(R.color.scolor_green_violet));
            binding.tvDetail.setTextColor(colorOnDrag);
            binding.ivDetail.setColorFilter(colorOnDrag);
        } else {
            binding.layoutDetail.setCardBackgroundColor(colorSurfaceContainerHigh);
            binding.tvDetail.setTextColor(colorDefault);
            binding.ivDetail.setColorFilter(colorDefault);
        }

    }

    public boolean c() {
        return l;
    }

    public boolean c(float var1, float var2) {
        int var3 = binding.layoutDetail.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
            return false;
        } else {
            int[] var5 = new int[2];
            binding.layoutDetail.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + binding.layoutDetail.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + binding.layoutDetail.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void d(boolean var1) {
        k = var1;
        if (var1) {
            binding.layoutFavorite.setCardBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            binding.tvFavorite.setTextColor(colorOnDrag);
            binding.ivBookmark.setColorFilter(colorOnDrag);
        } else {
            binding.layoutFavorite.setCardBackgroundColor(colorSurfaceContainerHigh);
            binding.tvFavorite.setTextColor(colorDefault);
            binding.ivBookmark.setColorFilter(colorDefault);
        }

    }

    public boolean d() {
        return k;
    }

    public boolean d(float var1, float var2) {
        int var3 = binding.layoutFavorite.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
            return false;
        } else {
            int[] var5 = new int[2];
            binding.layoutFavorite.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + binding.layoutFavorite.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + binding.layoutFavorite.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void e(boolean var1) {
        if (var1) {
            binding.layoutFavorite.setVisibility(VISIBLE);
            binding.layoutCopy.setVisibility(VISIBLE);
            binding.layoutDetail.setVisibility(GONE);
        } else {
            binding.layoutFavorite.setVisibility(GONE);
            binding.layoutCopy.setVisibility(GONE);
            binding.layoutDetail.setVisibility(VISIBLE);
        }

    }
}
