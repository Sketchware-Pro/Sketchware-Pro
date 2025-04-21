package com.besome.sketch.lib.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ColorBean;
import com.besome.sketch.editor.manage.library.material3.Material3LibraryManager;
import com.besome.sketch.editor.view.ColorGroupItem;

import pro.sketchware.R;
import pro.sketchware.activities.resources.editors.models.ColorModel;
import pro.sketchware.activities.resources.editors.utils.ColorsEditorManager;
import pro.sketchware.databinding.ColorPickerBinding;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.databinding.ItemAttrBinding;
import a.a.a.DB;
import a.a.a.wq;
import a.a.a.GB;
import a.a.a.bB;
import a.a.a.sq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;
import pro.sketchware.lib.validator.ColorInputValidator;
import pro.sketchware.utility.FileUtil;

public class ColorPickerDialog extends PopupWindow {

    private Activity activity;
    private static String sc_id;
    private final ArrayList<ColorBean> colorList = new ArrayList<>();
    private final ArrayList<ColorBean[]> colorGroups = new ArrayList<>();
    private final ArrayList<ResColor> resColors = new ArrayList<>();
    private final ColorPickerBinding binding;
    private ArrayList<Attribute> attributes;
    private b colorPickerCallback;
    private materialColorAttr materialColorAttr;
    private ColorInputValidator colorValidator;
    private int k;
    private int l;
    private int m = -1;
    private DB colorPref;
    private boolean hasMaterialColors;
    private final ColorsAdapter colorsAdapter = new ColorsAdapter();
    private Material3LibraryManager material3LibraryManager;

    public ColorPickerDialog(Activity activity, int var3, boolean isTransparentColor, boolean isNoneColor) {
        super(activity);
        binding = ColorPickerBinding.inflate(activity.getLayoutInflater());
        initialize(activity, getHexColor(var3), isTransparentColor, isNoneColor);
    }

    public ColorPickerDialog(Activity activity, String color, boolean isTransparentColor, boolean isNoneColor, String scId) {
        super(activity);
        binding = ColorPickerBinding.inflate(activity.getLayoutInflater());
        sc_id = scId;
        material3LibraryManager = new Material3LibraryManager(scId);
        hasMaterialColors = true;
        initialize(activity, color, isTransparentColor, isNoneColor);
    }

    public static boolean isValidHexColor(String colorStr) {
        if (colorStr == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^#([a-fA-F0-9]*)");
        Matcher matcher = pattern.matcher(colorStr);
        return matcher.matches();
    }

    private String getHexColor(int color) {
        return String.format("#%06X", color);
    }

    private void deleteAllSavedColors() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setIcon(R.drawable.delete_96);
        dialog.setTitle(xB.b().a(activity, R.string.picker_color_title_delete_all_custom_color));
        dialog.setMessage(xB.b().a(activity, R.string.picker_color_message_delete_all_custom_color));
        dialog.setPositiveButton(xB.b().a(activity, R.string.common_word_delete), (v, which) -> {
            colorPref.a();
            colorGroups.set(0, getSavedColorBeans());
            notifyChanges();
            v.dismiss();
        });
        dialog.setNegativeButton(xB.b().a(activity, R.string.common_word_cancel), null);
        dialog.show();
    }

    public void a(b callback) {
        colorPickerCallback = callback;
    }

    public void materialColorAttr(materialColorAttr callback) {
        materialColorAttr = callback;
    }

    public void initialize(Activity activity, String color, boolean isTransparentColor, boolean isNoneColor) {
        this.activity = activity;
        colorPref = new DB(activity, "P24");
        initializeColorData(isTransparentColor, isNoneColor);
        initializeResColors();
        initializeAttrsList();

        if (color.equals("NONE")) {
            k = colorGroups.size() - 1;
            l = k;
            m = 0;
        }
        if (color.equals("TRANSPARENT")) {
            k = colorGroups.size() - 2;
            l = k;
            m = 0;
        } else if (color.startsWith("#")) {
            int colorInt = Color.parseColor(color);
            for (int groupIndex = 0; groupIndex < colorGroups.size(); ++groupIndex) {
                ColorBean[] colorBeans = colorGroups.get(groupIndex);

                for (int colorIndex = 0; colorIndex < colorBeans.length; ++colorIndex) {
                    if (colorBeans[colorIndex].colorCode == colorInt) {
                        k = groupIndex;
                        l = groupIndex;
                        m = colorIndex;
                        break;
                    }
                }
            }
        } else if (color.startsWith("@color/")) {
            k = 1;
            l = 1;
            for (int i = 0; i < resColors.size(); i++) {
                ResColor resColor = resColors.get(i);
                if (("@color/" + resColor.colorName()).equals(color)) {
                    m = i;
                    break;
                }
            }
            binding.colorList.setAdapter(new resColorsAdapter(resColors, m));
            binding.colorList.post(() -> binding.colorList.scrollToPosition(m));
        } else if (color.startsWith("?")) {
            k = 2;
            l = 2;
            for (int i = 0; i < attributes.size(); i++) {
                Attribute attribute = attributes.get(i);
                if (("?" + attribute.attrName()).equals(color) || ("?attr/" + attribute.attrName()).equals(color)) {
                    m = i;
                    break;
                }
            }
            binding.colorList.setAdapter(new AttrAdapter(attributes, m));
            binding.colorList.post(() -> binding.colorList.scrollToPosition(m));
        }
        super.setBackgroundDrawable(null);
        super.setAnimationStyle(android.R.style.Animation_Dialog);
        super.setFocusable(true);
        super.setOutsideTouchable(true);
        super.setContentView(binding.getRoot());
        int[] widthAndHeight = GB.c(activity);
        super.setWidth(widthAndHeight[0]);
        super.setHeight(widthAndHeight[1]);
        binding.colorList.setHasFixedSize(true);
        binding.colorList.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        if (binding.colorList.getAdapter() == null)
            binding.colorList.setAdapter(colorsAdapter);
        binding.colorList.setItemAnimator(new DefaultItemAnimator());

        binding.tiCustomColor.setHint(xB.b().a(activity, R.string.picker_color_hint_enter_hex_color_code));

        colorValidator = new ColorInputValidator(activity, binding.tiCustomColor, binding.tvCustomColor);
        binding.etCustomColor.setPrivateImeOptions("defaultInputmode=english;");
        binding.tvAddColor.setText(xB.b().a(activity, R.string.common_word_add).toUpperCase());
        binding.tvAddColor.setOnClickListener(view -> {
            if (colorValidator.b()) {
                String formattedColor = String.format("#%8s", Helper.getText(binding.etCustomColor)).replaceAll(" ", "F");
                savePickedColor(formattedColor.toUpperCase());
                notifyChanges();
            }
        });
        binding.colorList.getAdapter().notifyItemChanged(m);
        binding.layoutColorTitle.removeAllViews();

        for (int j = 0; j < colorList.size(); ++j) {
            ColorGroupItem colorGroupItem = new ColorGroupItem(activity);
            ColorBean colorBean = colorList.get(j);
            int finalJ = j;
            colorGroupItem.b.setOnClickListener(v -> {
                l = finalJ;
                if (finalJ == 0 && colorGroups.get(finalJ).length == 0) {
                    bB.b(activity, xB.b().a(activity, R.string.picker_color_custom_color_not_found), 1).show();
                    return;
                }
                if (finalJ == 1 && colorGroups.get(finalJ).length == 0) {
                    bB.b(activity, xB.b().a(activity, R.string.picker_color_xml_is_empty), 1).show();
                    return;
                }
                if (sc_id != null && finalJ == 2 && !material3LibraryManager.isMaterial3Enabled()) {
                    SketchwareUtil.toastError("Please enable Material3 in the Library Manager first");
                    return;
                }
                if (sc_id != null && finalJ == 1) {
                    binding.colorList.setAdapter(new resColorsAdapter(resColors, -1));
                } else if (sc_id != null && finalJ == 2) {
                    binding.colorList.setAdapter(new AttrAdapter(attributes, -1));
                } else {
                    binding.colorList.setAdapter(colorsAdapter);
                }
            });
            colorGroupItem.b.setText(colorBean.colorName);
            colorGroupItem.b.setTextColor(colorBean.displayNameColor);
            colorGroupItem.b.setBackgroundColor(colorBean.colorCode);
            binding.layoutColorTitle.addView(colorGroupItem);
            if (j == k) {
                colorGroupItem.c.setImageResource(colorBean.icon);
                colorGroupItem.c.setVisibility(View.VISIBLE);
            } else {
                colorGroupItem.c.setVisibility(View.GONE);
            }

            colorGroupItem.b.setOnLongClickListener(v -> {
                if (finalJ == 0) deleteAllSavedColors();
                return false;
            });
        }

        Animation animation = binding.getRoot().getAnimation();
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
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setIcon(R.drawable.delete_96);
        dialog.setTitle(xB.b().a(activity, R.string.picker_color_title_delete_custom_color));
        dialog.setMessage(xB.b().a(activity, R.string.picker_color_message_delete_custom_color));
        dialog.setPositiveButton(xB.b().a(activity, R.string.common_word_delete), (v, which) -> {
            removeSavedColor(color);
            notifyChanges();
            v.dismiss();
        });
        dialog.setNegativeButton(xB.b().a(activity, R.string.common_word_cancel), null);
        dialog.show();
    }

    private void initializeColorData(boolean isColorTransparent, boolean isColorNone) {
        colorList.add(new ColorBean("#FFF6F6F6", "CUSTOM", "#212121", R.drawable.checked_grey_32));
        if (sc_id != null)
            colorList.add(new ColorBean("#FFF6F6F6", "colors.xml", "#212121", R.drawable.checked_grey_32));
        if (hasMaterialColors)
            colorList.add(new ColorBean("#FFF6F6F6", "Material 3 Colors", "#212121", R.drawable.checked_grey_32));
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
        if (sc_id != null)
            colorGroups.add(sq.p);
        if (hasMaterialColors)
            colorGroups.add(sq.p);
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
            colorPref.a("P24I1", ((Object) colorToSave));
            colorGroups.set(0, getSavedColorBeans());
            notifyChanges();
        }
    }

    private ColorBean[] getSavedColorBeans() {
        String savedColors = colorPref.f("P24I1");
        ColorBean[] colorBeansResult;
        if (!savedColors.isEmpty()) {
            String[] colorStrings = savedColors.split(",");
            ColorBean[] colorBeans = new ColorBean[colorStrings.length];
            int index = 0;
            while (index < colorStrings.length) {
                try {
                    int parsedColor = Color.parseColor(colorStrings[index]);
                    int red = Color.red(parsedColor);
                    int green = Color.green(parsedColor);
                    int blue = Color.blue(parsedColor);

                    int count = 0;
                    if (red > 240) count++;
                    if (green > 240) count++;
                    if (blue > 240) count++;


                    colorBeans[index] = new ColorBean(colorStrings[index], "CUSTOM",
                            count >= 2 ? "#212121" : "#ffffff",
                            R.drawable.checked_white_32);

                } catch (Exception e) {
                    colorPref.a();
                    colorBeans = new ColorBean[0];
                    break;
                }
                index++;
            }
            colorBeansResult = colorBeans;
        } else {
            colorBeansResult = new ColorBean[0];
        }

        return colorBeansResult;
    }

    private void smoothScrollToCurrentItem() {
        if (k < binding.layoutColorTitle.getChildCount()) {
            View childView = binding.layoutColorTitle.getChildAt(k);
            binding.layoutHsvColor.smoothScrollTo((int) childView.getX(), 0);
            binding.colorList.scrollToPosition(m);
        }
    }

    private void savePickedColor(String color) {
        String savedColors = colorPref.f("P24I1");
        if (savedColors.contains(color)) {
            bB.b(activity, xB.b().a(activity, R.string.picker_color_already_exist), 0).show();
        } else {
            String colorsToSave = color + "," + savedColors;
            colorPref.a("P24I1", (Object) (colorsToSave));
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
        binding.colorList.getAdapter().notifyDataSetChanged();
    }

    public void initializeAttrsList() {
        if (!hasMaterialColors) return;
        attributes = new ArrayList<>();

        attributes.add(new Attribute("colorSurface", R.attr.colorSurface));
        attributes.add(new Attribute("colorOnSurface", R.attr.colorOnSurface));
        attributes.add(new Attribute("colorPrimary", R.attr.colorPrimary));
        attributes.add(new Attribute("colorOnPrimary", R.attr.colorOnPrimary));
        attributes.add(new Attribute("colorPrimaryContainer", R.attr.colorPrimaryContainer));
        attributes.add(new Attribute("colorOnPrimaryContainer", R.attr.colorOnPrimaryContainer));
        attributes.add(new Attribute("colorSecondary", R.attr.colorSecondary));
        attributes.add(new Attribute("colorOnSecondary", R.attr.colorOnSecondary));
        attributes.add(new Attribute("colorSecondaryContainer", R.attr.colorSecondaryContainer));
        attributes.add(new Attribute("colorOnSecondaryContainer", R.attr.colorOnSecondaryContainer));
        attributes.add(new Attribute("colorTertiary", R.attr.colorTertiary));
        attributes.add(new Attribute("colorOnTertiary", R.attr.colorOnTertiary));
        attributes.add(new Attribute("colorTertiaryContainer", R.attr.colorTertiaryContainer));
        attributes.add(new Attribute("colorOnTertiaryContainer", R.attr.colorOnTertiaryContainer));
        attributes.add(new Attribute("colorSurfaceVariant", R.attr.colorSurfaceVariant));
        attributes.add(new Attribute("colorOnSurfaceVariant", R.attr.colorOnSurfaceVariant));
        attributes.add(new Attribute("colorSurfaceInverse", R.attr.colorSurfaceInverse));
        attributes.add(new Attribute("colorOnSurfaceInverse", R.attr.colorOnSurfaceInverse));
        attributes.add(new Attribute("colorError", R.attr.colorError));
        attributes.add(new Attribute("colorOnError", R.attr.colorOnError));
        attributes.add(new Attribute("colorErrorContainer", R.attr.colorErrorContainer));
        attributes.add(new Attribute("colorOnErrorContainer", R.attr.colorOnErrorContainer));
    }

    private void initializeResColors() {
        if (sc_id == null)
            return;
        ColorsEditorManager colorsEditorManager = new ColorsEditorManager();
        String filePath = wq.b(sc_id) + "/files/resource/values/colors.xml";
        String fileNightPath = wq.b(sc_id) + "/files/resource/values-night/colors.xml";

        ArrayList<ColorModel> colorList = new ArrayList<>();
        ArrayList<ColorModel> colorNightList = new ArrayList<>();

        colorsEditorManager.parseColorsXML(colorList, FileUtil.readFileIfExist(filePath));
        colorsEditorManager.parseColorsXML(colorNightList, FileUtil.readFileIfExist(fileNightPath));

        HashMap<String, String> nightColorsMap = new HashMap<>();
        for (ColorModel nightColorModel : colorNightList) {
            nightColorsMap.put(nightColorModel.getColorName(), nightColorModel.getColorValue());
        }

        for (ColorModel colorModel : colorList) {
            int color = PropertiesUtil.parseColor(colorsEditorManager.getColorValue(activity.getApplicationContext(), colorModel.getColorValue(), 3));
            int colorNight;
            if (nightColorsMap.containsKey(colorModel.getColorName())) {
                colorNight = PropertiesUtil.parseColor(colorsEditorManager.getColorValue(activity.getApplicationContext(), nightColorsMap.get(colorModel.getColorName()), 3, true));
            } else {
                colorNight = -1;
            }
            resColors.add(new ResColor(colorModel.getColorName(), color, colorNight));
        }
    }

    public interface b {
        void a(int var1);

        void a(String var1, int var2);
    }

    public interface materialColorAttr {
        void selectedMaterialColorAttr(String attr, int attrId);
    }

    public record Attribute(String attrName, int attr) {
    }

    public record ResColor(String colorName, int colorValue, int nightColorValue) {
    }

    private class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

        public ColorsAdapter() {
        }

        public int getItemCount() {
            return colorGroups.get(l).length;
        }

        public void onBindViewHolder(ColorViewHolder holder, int position) {
            ColorBean colorBean = ((ColorBean[]) colorGroups.get(l))[position];

            holder.tvColorCode.setText(colorBean.getColorCode(l == 0));
            if (position == 0) {
                holder.tvColorName.setText(((ColorBean[]) colorGroups.get(l))[0].colorName);
            } else {
                holder.tvColorName.setText("");
            }
            if (l == 1 && sc_id != null) {
                holder.tvColorName.setText(((ColorBean[]) colorGroups.get(l))[position].colorName);
            }

            holder.tvColorCode.setTextColor(((ColorBean[]) colorGroups.get(l))[position].displayNameColor);
            holder.tvColorName.setTextColor(((ColorBean[]) colorGroups.get(l))[position].displayNameColor);
            holder.layoutColorItem.setBackgroundColor(((ColorBean[]) colorGroups.get(l))[position].colorCode);
            if (position == m && l == k) {
                holder.imgSelector.setImageResource(((ColorBean[]) colorGroups.get(l))[position].icon);
                holder.imgSelector.setVisibility(View.VISIBLE);
            } else {
                holder.imgSelector.setVisibility(View.GONE);
            }

        }

        @NonNull
        public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ColorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.color_picker_item, parent, false));
        }

        private class ColorViewHolder extends RecyclerView.ViewHolder {

            public View layoutColorItem;
            public TextView tvColorCode;
            public TextView tvColorName;
            public ImageView imgSelector;

            public ColorViewHolder(View itemView) {
                super(itemView);
                layoutColorItem = itemView.findViewById(R.id.layout_color_item);
                tvColorCode = itemView.findViewById(R.id.tv_color_code);
                tvColorName = itemView.findViewById(R.id.tv_color_name);
                imgSelector = itemView.findViewById(R.id.img_selector);
                itemView.setOnClickListener(v -> {
                    if (colorPickerCallback != null) {
                        if (Helper.getText(tvColorCode).equals("TRANSPARENT")) {
                            colorPickerCallback.a(0);
                        } else if (Helper.getText(tvColorCode).equals("NONE")) {
                            colorPickerCallback.a(0xffffff);
                        } else if (l == 1 && sc_id != null) {
                            colorPickerCallback.a((String) tvColorName.getText(), Color.parseColor(Helper.getText(tvColorCode)));
                        } else {
                            colorPickerCallback.a(Color.parseColor(Helper.getText(tvColorCode)));
                        }
                    }
                    dismiss();
                });
                itemView.setOnLongClickListener(v -> {
                    if (l == 0) showColorRemoveDialog(Helper.getText(tvColorCode));
                    return false;
                });
            }
        }
    }

    public class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.AttrViewHolder> {

        private final ArrayList<Attribute> attributeList;
        private final Context themedDarkContext;
        private final Context themedLightContext;

        private final int selectedPosition;

        public AttrAdapter(ArrayList<Attribute> attributeList, int selectedPosition) {
            this.attributeList = attributeList;
            this.selectedPosition = selectedPosition;
            if (material3LibraryManager.isDynamicColorsEnabled()) {
                themedDarkContext = new ContextThemeWrapper(activity, R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_Dark);
                themedLightContext = new ContextThemeWrapper(activity, R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_Light);
            } else {
                themedDarkContext = new ContextThemeWrapper(activity, R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_NON_DYNAMIC_Dark);
                themedLightContext = new ContextThemeWrapper(activity, R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_NON_DYNAMIC_Light);
            }
        }

        @NonNull
        @Override
        public AttrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemAttrBinding binding = ItemAttrBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new AttrViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull AttrViewHolder holder, int position) {
            Attribute attribute = attributeList.get(position);
            holder.binding.tvAttrName.setText(attribute.attrName());

            int darkColor = ThemeUtils.getColor(new View(themedDarkContext), attribute.attr());
            int lightColor = ThemeUtils.getColor(new View(themedLightContext), attribute.attr());

            holder.binding.darkContainer.setBackgroundColor(darkColor);
            holder.binding.lightContainer.setBackgroundColor(lightColor);
            holder.binding.darkTtl.setTextColor(lightColor);
            holder.binding.lightTtl.setTextColor(darkColor);
            holder.binding.checkedImg.setVisibility(selectedPosition == position ? View.VISIBLE : View.GONE);

            holder.binding.getRoot().setOnClickListener(view -> {
                if (materialColorAttr != null) {
                    materialColorAttr.selectedMaterialColorAttr(attribute.attrName(), attribute.attr());
                }
                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return attributeList.size();
        }

        public static class AttrViewHolder extends RecyclerView.ViewHolder {
            private final ItemAttrBinding binding;

            public AttrViewHolder(@NonNull ItemAttrBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    public class resColorsAdapter extends RecyclerView.Adapter<resColorsAdapter.resColorsViewHolder> {

        private final ArrayList<ResColor> resColors;

        private final int selectedPosition;

        public resColorsAdapter(ArrayList<ResColor> resColors, int selectedPosition) {
            this.resColors = resColors;
            this.selectedPosition = selectedPosition;
        }

        @NonNull
        @Override
        public resColorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemAttrBinding binding = ItemAttrBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new resColorsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull resColorsViewHolder holder, int position) {
            ResColor resColor = resColors.get(position);
            holder.binding.tvAttrName.setText(resColor.colorName());
            boolean hasNightColor = resColor.nightColorValue() != -1;
            int reversedColor = getReversedColor(resColor.colorValue());

            if (hasNightColor) {
                holder.binding.darkContainer.setBackgroundColor(resColor.nightColorValue());
                if (resColor.colorValue() != resColor.nightColorValue()) {
                    holder.binding.darkTtl.setTextColor(resColor.colorValue());
                    holder.binding.lightTtl.setTextColor(resColor.nightColorValue());
                } else {
                    holder.binding.darkTtl.setTextColor(reversedColor);
                    holder.binding.lightTtl.setTextColor(getReversedColor(resColor.nightColorValue()));
                }
            } else {
                holder.binding.darkContainer.setBackgroundColor(resColor.colorValue());
                holder.binding.darkTtl.setTextColor(reversedColor);
                holder.binding.lightTtl.setTextColor(reversedColor);
            }
            holder.binding.lightContainer.setBackgroundColor(resColor.colorValue());
            holder.binding.checkedImg.setVisibility(selectedPosition == position ? View.VISIBLE : View.GONE);
            holder.binding.checkedImg.setColorFilter(reversedColor);

            holder.binding.getRoot().setOnClickListener(view -> {
                colorPickerCallback.a(resColor.colorName(), resColor.colorValue());
                dismiss();
            });
        }

        private int getReversedColor(int color) {
            int alpha = Color.alpha(color);
            int red = 255 - Color.red(color);
            int green = 255 - Color.green(color);
            int blue = 255 - Color.blue(color);
            return Color.argb(alpha, red, green, blue);
        }

        @Override
        public int getItemCount() {
            return resColors.size();
        }

        public static class resColorsViewHolder extends RecyclerView.ViewHolder {
            private final ItemAttrBinding binding;

            public resColorsViewHolder(@NonNull ItemAttrBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

}
