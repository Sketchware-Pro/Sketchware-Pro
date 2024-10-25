package com.besome.sketch.editor.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.LogicEditorTopMenuBinding;

import a.a.a.xB;

public class LogicTopMenu extends LinearLayout {

    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;

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
        binding.tvCopy.setText(xB.b().a(getContext(), R.string.common_word_copy));
        binding.tvFavorite.setText(xB.b().a(getContext(), R.string.common_word_collection));
        binding.tvDetail.setText(xB.b().a(getContext(), R.string.common_word_detail));
    }

    public void a(boolean var1) {
        j = var1;
        if (var1) {
            binding.layoutCopy.setBackgroundColor(getResources().getColor(R.color.scolor_green_normal));
            binding.tvCopy.setTextColor(0xffffffff);
            binding.ivCopy.setImageResource(R.drawable.copy_48_white);
        } else {
            binding.layoutCopy.setBackgroundColor(0xfffefefe);
            binding.tvCopy.setTextColor(0xff7d7d7d);
            binding.ivCopy.setImageResource(R.drawable.copy_48_gray);
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
            binding.layoutDelete.setBackgroundColor(getResources().getColor(R.color.scolor_red_02));
            binding.tvDelete.setTextColor(-1);
            binding.ivTrash.setImageResource(R.drawable.ic_trashcan_white_48dp);
        } else {
            binding.layoutDelete.setBackgroundColor(0xfffefefe);
            binding.tvDelete.setTextColor(0xff7d7d7d);
            binding.ivTrash.setImageResource(R.drawable.icon_delete);
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
            binding.layoutDetail.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            binding.tvDetail.setTextColor(0xffffffff);
            binding.ivDetail.setImageResource(R.drawable.block_96_white);
        } else {
            binding.layoutDetail.setBackgroundColor(0xfffefefe);
            binding.tvDetail.setTextColor(0xff7d7d7d);
            binding.ivDetail.setImageResource(R.drawable.block_flled_grey);
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
            binding.layoutFavorite.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            binding.tvFavorite.setTextColor(0xffffffff);
            binding.ivBookmark.setImageResource(R.drawable.bookmark_48_white);
        } else {
            binding.layoutFavorite.setBackgroundColor(0xfffefefe);
            binding.tvFavorite.setTextColor(0xff7d7d7d);
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_red_48dp);
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
