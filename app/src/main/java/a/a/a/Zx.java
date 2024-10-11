package a.a.a;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ColorBean;
import com.besome.sketch.editor.view.ColorGroupItem;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;

public class Zx extends PopupWindow {

    private b colorPickerCallback;
    private final ArrayList<ColorBean> colorList = new ArrayList<>();
    private final ArrayList<ColorBean[]> colorGroups = new ArrayList<>();
    private LinearLayout d;
    private XB colorValidator;
    private EditText f;
    private TextView g;
    private TextView h;
    private HorizontalScrollView i;
    private RecyclerView recyclerView;
    private int k;
    private int l;
    private int m = -1;
    private DB colorPref;
    private Activity activity;

    public Zx(View contentView, Activity activity, int var3, boolean var4, boolean var5) {
        super(activity);
        initialize(contentView, activity, var3, var4, var5);
    }

    private void deleteAllSavedColors() {
        aB dialog = new aB(activity);
        dialog.a(2131165524);
        dialog.b(xB.b().a(activity, 2131625755));
        dialog.a(xB.b().a(activity, 0x7f0e0719));
        dialog.b(xB.b().a(activity, 2131624986), v -> {
            colorPref.a();
            colorGroups.set(0, getSavedColorBeans());
            notifyChanges();
            dialog.dismiss();
        });
        dialog.a(xB.b().a(activity, 2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void a(b callback) {
        colorPickerCallback = callback;
    }

    public void initialize(View contentView, Activity activity, int var3, boolean var4, boolean var5) {
        this.activity = activity;
        colorPref = new DB(activity, "P24");
        initializeColorData(var4, var5);

        for (int groupIndex = 0; groupIndex < colorGroups.size(); ++groupIndex) {
            ColorBean[] colorBeans = colorGroups.get(groupIndex);

            for (int colorIndex = 0; colorIndex < colorBeans.length; ++colorIndex) {
                if (colorBeans[colorIndex].colorCode == var3) {
                    k = groupIndex;
                    l = groupIndex;
                    m = colorIndex;
                    break;
                }
            }
        }

        super.setFocusable(true);
        super.setOutsideTouchable(true);
        super.setContentView(contentView);
        int[] widthAndHeight = GB.c(activity);
        super.setWidth(widthAndHeight[0]);
        super.setHeight(widthAndHeight[1]);
        i = contentView.findViewById(2131231351);
        d = contentView.findViewById(2131231327);
        recyclerView = contentView.findViewById(2131230905);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        recyclerView.setAdapter(new ColorsAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        f = contentView.findViewById(2131231026);
        ((TextInputLayout) contentView.findViewById(2131231807)).setHint(xB.b().a(activity, 2131625752));
        g = contentView.findViewById(2131231932);
        colorValidator = new XB(activity, contentView.findViewById(2131231807), g);
        f.setPrivateImeOptions("defaultInputmode=english;");
        h = contentView.findViewById(2131231864);
        h.setText(xB.b().a(activity, 2131624970).toUpperCase());
        h.setOnClickListener(view -> {
            if (colorValidator.b()) {
                String formattedColor = String.format("#%8s", f.getText().toString()).replaceAll(" ", "F");
                savePickedColor(formattedColor.toUpperCase());
                notifyChanges();
            }
        });
        recyclerView.getAdapter().notifyItemChanged(m);
        d.removeAllViews();

        for (int j = 0; j < colorList.size(); ++j) {
            ColorGroupItem colorGroupItem = new ColorGroupItem(activity);
            ColorBean colorBean = colorList.get(j);
            int finalJ = j;
            colorGroupItem.b.setOnClickListener(v -> {
                l = finalJ;
                if (finalJ == 0 && colorGroups.get(finalJ).length == 0) {
                    bB.b(activity, xB.b().a(activity, 2131625751), 1).show();

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            });
            colorGroupItem.b.setText(colorBean.colorName);
            colorGroupItem.b.setTextColor(colorBean.displayNameColor);
            colorGroupItem.b.setBackgroundColor(colorBean.colorCode);
            d.addView(colorGroupItem);
            if (j == k) {
                colorGroupItem.c.setImageResource(colorBean.icon);
                colorGroupItem.c.setVisibility(0);
            } else {
                colorGroupItem.c.setVisibility(8);
            }

            colorGroupItem.b.setOnLongClickListener(v -> {
                if (finalJ == 0) deleteAllSavedColors();
                return false;
            });
        }

        Animation animation = contentView.getAnimation();
        if (animation != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    smoothScrollToCurrentItem();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

    }

    private void showColorRemoveDialog(String color) {
        aB dialog = new aB(activity);
        dialog.a(2131165524);
        dialog.b(xB.b().a(activity, 2131625756));
        dialog.a(xB.b().a(activity, 2131625754));
        dialog.b(xB.b().a(activity, 2131624986), v -> {
            removeSavedColor(color);
            notifyChanges();
            dialog.dismiss();
        });
        dialog.a(xB.b().a(activity, 2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void initializeColorData(boolean isColorTransparent, boolean isColorNone) {
        colorList.add(new ColorBean("#FFF6F6F6", "CUSTOM", "#212121", 2131165412));
        colorList.add(sq.p[0]);
        colorList.add(sq.q[0]);
        colorList.add(sq.r[0]);
        colorList.add(sq.s[0]);
        colorList.add(sq.t[0]);
        colorList.add(sq.u[0]);
        colorList.add(sq.v[0]);
        colorList.add(sq.w[0]);
        colorList.add(sq.x[0]);
        colorList.add(sq.y[0]);
        colorList.add(sq.z[0]);
        colorList.add(sq.A[0]);
        colorList.add(sq.B[0]);
        colorList.add(sq.C[0]);
        colorList.add(sq.D[0]);
        colorList.add(sq.E[0]);
        colorList.add(sq.F[0]);
        colorList.add(sq.G[0]);
        colorList.add(sq.H[0]);
        colorList.add(sq.I[0]);
        colorList.add(sq.J[0]);
        colorGroups.add(getSavedColorBeans());
        colorGroups.add(sq.p);
        colorGroups.add(sq.q);
        colorGroups.add(sq.r);
        colorGroups.add(sq.s);
        colorGroups.add(sq.t);
        colorGroups.add(sq.u);
        colorGroups.add(sq.v);
        colorGroups.add(sq.w);
        colorGroups.add(sq.x);
        colorGroups.add(sq.y);
        colorGroups.add(sq.z);
        colorGroups.add(sq.A);
        colorGroups.add(sq.B);
        colorGroups.add(sq.C);
        colorGroups.add(sq.D);
        colorGroups.add(sq.E);
        colorGroups.add(sq.F);
        colorGroups.add(sq.G);
        colorGroups.add(sq.H);
        colorGroups.add(sq.I);
        colorGroups.add(sq.J);
        if (isColorTransparent) {
            colorList.add(sq.K[0]);
            colorGroups.add(sq.K);
        }
        if (isColorNone) {
            colorList.add(sq.L[0]);
            colorGroups.add(sq.L);
        }
    }

    private void removeSavedColor(String color) {
        String savedColors = colorPref.f("P24I1");
        if (savedColors.contains(color)) {
            String colorToRemove = color + ",";
            String colorToSave = savedColors.replaceAll(colorToRemove, "");
            colorPref.a("P24I1", colorToSave);
            colorGroups.set(0, getSavedColorBeans());
            notifyChanges();
        }
    }

    private ColorBean[] getSavedColorBeans() {
        String savedColors = colorPref.f("P24I1");
        ColorBean[] var4;
        if (!savedColors.isEmpty()) {
            String[] colorStrings = savedColors.split(",");
            ColorBean[] colorBeans = new ColorBean[colorStrings.length];
            int var3 = 0;

            while (true) {
                var4 = colorBeans;
                if (var3 >= colorStrings.length) {
                    break;
                }

                label52:
                {
                    label61:
                    {
                        int var5;
                        int var6;
                        int var7;
                        int var8;
                        boolean var10001;
                        try {
                            var5 = Color.parseColor(colorStrings[var3]);
                            var6 = Color.red(var5);
                            var7 = Color.green(var5);
                            var8 = Color.blue(var5);
                        } catch (Exception var11) {
                            var10001 = false;
                            break label61;
                        }

                        byte var14;
                        if (var6 > 240) {
                            var14 = 1;
                        } else {
                            var14 = 0;
                        }

                        var6 = var14;
                        if (var7 > 240) {
                            var6 = var14 + 1;
                        }

                        var5 = var6;
                        if (var8 > 240) {
                            var5 = var6 + 1;
                        }

                        if (var5 >= 2) {
                            label44:
                            {
                                ColorBean var13;
                                try {
                                    var13 = new ColorBean(colorStrings[var3], "CUSTOM", "#212121", 2131165412);
                                } catch (Exception var9) {
                                    var10001 = false;
                                    break label44;
                                }

                                colorBeans[var3] = var13;
                                break label52;
                            }
                        } else {
                            try {
                                colorBeans[var3] = new ColorBean(colorStrings[var3], "CUSTOM", "#ffffff", 2131165414);
                                break label52;
                            } catch (Exception var10) {
                                var10001 = false;
                            }
                        }
                    }

                    colorPref.a();
                    colorBeans = new ColorBean[0];
                }

                ++var3;
            }
        } else {
            var4 = new ColorBean[0];
        }

        return var4;
    }

    private void smoothScrollToCurrentItem() {
        if (k < d.getChildCount()) {
            View childView = d.getChildAt(k);
            i.smoothScrollTo((int) childView.getX(), 0);
            recyclerView.scrollToPosition(m);
        }
    }

    private void savePickedColor(String color) {
        String savedColors = colorPref.f("P24I1");
        if (savedColors.contains(color)) {
            bB.b(activity, xB.b().a(activity, 2131625750), 0).show();
        } else {
            String colorsToSave = color + "," + savedColors;
            colorPref.a("P24I1", colorsToSave);
            colorGroups.set(0, getSavedColorBeans());
            notifyChanges();
            k = 0;
            smoothScrollToCurrentItem();
        }
    }

    private void notifyChanges() {
        l = 0;
        k = 0;
        m = 0;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

        public ColorsAdapter() {
        }

        public int getItemCount() {
            return colorGroups.get(l).length;
        }

        public void onBindViewHolder(ColorViewHolder holder, int position) {
            ColorBean colorBean = ((ColorBean[]) colorGroups.get(l))[position];

            holder.u.setText(colorBean.getColorCode(l == 0));
            if (position == 0) {
                holder.v.setText(((ColorBean[]) colorGroups.get(l))[0].colorName);
            } else {
                holder.v.setText("");
            }

            holder.u.setTextColor(((ColorBean[]) colorGroups.get(l))[position].displayNameColor);
            holder.v.setTextColor(((ColorBean[]) colorGroups.get(l))[position].displayNameColor);
            holder.t.setBackgroundColor(((ColorBean[]) colorGroups.get(l))[position].colorCode);
            if (position == m && l == k) {
                holder.w.setImageResource(((ColorBean[]) colorGroups.get(l))[position].icon);
                holder.w.setVisibility(0);
            } else {
                holder.w.setVisibility(8);
            }

        }

        @NonNull
        public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ColorViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427375, parent, false));
        }

        private class ColorViewHolder extends RecyclerView.ViewHolder {

            public View t;
            public TextView u;
            public TextView v;
            public ImageView w;

            public ColorViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(2131231326);
                u = itemView.findViewById(2131231915);
                v = itemView.findViewById(2131231916);
                w = itemView.findViewById(2131231182);
                itemView.setOnClickListener(v -> {
                    if (colorPickerCallback != null) {
                        if (u.getText().toString().equals("TRANSPARENT")) {
                            colorPickerCallback.a(0);
                        } else if (u.getText().toString().equals("NONE")) {
                            colorPickerCallback.a(0xffffff);
                        } else {
                            colorPickerCallback.a(Color.parseColor(u.getText().toString()));
                        }
                    }
                    dismiss();
                });
                itemView.setOnLongClickListener(v -> {
                    if (l == 0) showColorRemoveDialog(u.getText().toString());
                    return false;
                });
            }
        }
    }

    public interface b {
        void a(int var1);
    }
}
