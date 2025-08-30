package com.besome.sketch.lib.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.sq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.resourceseditor.components.models.ColorModel;
import pro.sketchware.activities.resourceseditor.components.utils.ColorsEditorManager;
import pro.sketchware.databinding.ColorPickerBinding;
import pro.sketchware.databinding.ColorPickerCustomAttrBinding;
import pro.sketchware.databinding.ColorPickerSimpleHexBinding;
import pro.sketchware.databinding.ItemAttrBinding;
import pro.sketchware.lib.validator.ColorInputValidator;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.SketchwareUtil;

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
    private yq yq;
    private ColorsEditorManager colorsEditorManager;

    public ColorPickerDialog(Activity activity, int var3, boolean isTransparentColor, boolean isNoneColor) {
        super(activity);
        binding = ColorPickerBinding.inflate(activity.getLayoutInflater());
        initialize(activity, getHexColor(var3), isTransparentColor, isNoneColor);
    }

    public ColorPickerDialog(Activity activity, String color, boolean isTransparentColor, boolean isNoneColor, String scId) {
        super(activity);
        binding = ColorPickerBinding.inflate(activity.getLayoutInflater());
        sc_id = scId;
        yq = new yq(activity, sc_id);
        yq.a(jC.c(sc_id), jC.b(sc_id), jC.a(sc_id), a.a.a.yq.ExportType.SOURCE_CODE_VIEWING);
        material3LibraryManager = new Material3LibraryManager(scId);
        colorsEditorManager = new ColorsEditorManager();
        hasMaterialColors = true;
        initialize(activity, color, isTransparentColor, isNoneColor);
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
                if (("?" + attribute.name()).equals(color) || ("?attr/" + attribute.name()).equals(color)) {
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

        binding.fab.setOnClickListener(view -> {
            if (sc_id != null && l == 2) {
                showCustomAttrCreatorDialog();
            } else {
                showCustomColorCreatorDialog();
            }
        });

        binding.colorList.getAdapter().notifyItemChanged(m);
        binding.layoutColorTitle.removeAllViews();

        for (int j = 0; j < colorList.size(); ++j) {
            ColorGroupItem colorGroupItem = new ColorGroupItem(activity);
            ColorBean colorBean = colorList.get(j);
            int finalJ = j;
            colorGroupItem.tvColorName.setOnClickListener(v -> {
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
            colorGroupItem.tvColorName.setText(colorBean.colorName);
            colorGroupItem.tvColorName.setTextColor(colorBean.displayNameColor);
            colorGroupItem.tvColorName.setBackgroundColor(colorBean.colorCode);
            binding.layoutColorTitle.addView(colorGroupItem);
            if (j == k) {
                colorGroupItem.imgColorSelector.setImageResource(colorBean.icon);
                colorGroupItem.imgColorSelector.setVisibility(View.VISIBLE);
            } else {
                colorGroupItem.imgColorSelector.setVisibility(View.GONE);
            }

            colorGroupItem.tvColorName.setOnLongClickListener(v -> {
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

    private void showCustomColorCreatorDialog() {
        ColorPickerSimpleHexBinding dialogBinding = ColorPickerSimpleHexBinding.inflate(activity.getLayoutInflater());
        colorValidator = new ColorInputValidator(activity, dialogBinding.tiCustomColor, dialogBinding.colorPreview);
        dialogBinding.etCustomColor.setPrivateImeOptions("defaultInputmode=english;");

        new MaterialAlertDialogBuilder(activity)
                .setTitle(Helper.getResString(R.string.save_to_collection))
                .setView(dialogBinding.getRoot())
                .setPositiveButton(Helper.getResString(R.string.common_word_save), (dialog, which) -> {
                    if (colorValidator.b()) {
                        String customColor = Helper.getText(dialogBinding.etCustomColor);
                        if (customColor.startsWith("#")) {
                            customColor = customColor.substring(1);
                        }
                        String formattedColor = String.format("#%8s", customColor).replaceAll(" ", "F");
                        savePickedColor(formattedColor.toUpperCase());
                        notifyChanges();
                    }
                })
                .setNegativeButton(Helper.getResString(R.string.common_word_cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showCustomAttrCreatorDialog() {
        ColorPickerCustomAttrBinding dialogBinding = ColorPickerCustomAttrBinding.inflate(activity.getLayoutInflater());

        dialogBinding.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                String fullMaterialAttrPrefix = "com.google.android.material.R.attr.";
                String attrPrefix = "R.attr.";

                if (input.startsWith(fullMaterialAttrPrefix)) {
                    input = input.substring(fullMaterialAttrPrefix.length());
                } else if (input.startsWith(attrPrefix)) {
                    input = input.substring(attrPrefix.length());
                } else {
                    dialogBinding.inputLayout.setError(Helper.getResString(R.string.unknown_attr));
                    return;
                }

                String color = colorsEditorManager.getColorValueFromAttrs(activity, input, 1, false);

                if (color.equals(colorsEditorManager.defaultHexColor)) {
                    dialogBinding.inputLayout.setError(Helper.getResString(R.string.unknown_attr));
                    return;
                }
                dialogBinding.inputLayout.setError(null);
                int lightColor = PropertiesUtil.parseColor(color);
                int darkColor = PropertiesUtil.parseColor(colorsEditorManager.getColorValueFromAttrs(activity, input, 1, true));

                // viewBinding don't support include , so here we should use findViewById
                dialogBinding.getRoot().findViewById(R.id.dark_container).setBackgroundColor(darkColor);
                dialogBinding.getRoot().findViewById(R.id.light_container).setBackgroundColor(lightColor);
                ((TextView) dialogBinding.getRoot().findViewById(R.id.tvAttrName)).setText(input);
                ((TextView) dialogBinding.getRoot().findViewById(R.id.dark_ttl)).setTextColor(lightColor);
                ((TextView) dialogBinding.getRoot().findViewById(R.id.light_ttl)).setTextColor(darkColor);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        var dialog = new MaterialAlertDialogBuilder(activity)
                .setTitle(Helper.getResString(R.string.save_to_collection))
                .setView(dialogBinding.getRoot())
                .setPositiveButton(Helper.getResString(R.string.common_word_save), null)
                .setNegativeButton(Helper.getResString(R.string.common_word_cancel), null)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (dialogBinding.inputLayout.getError() == null && !dialogBinding.input.getText().toString().isEmpty()) {
                String attributeName = ((TextView) dialogBinding.getRoot().findViewById(R.id.tvAttrName)).getText().toString();

                if (isAttrExist(attributeName)) {
                    SketchwareUtil.toastError(Helper.getResString(R.string.attr_already_exist));
                    return;
                }

                String savedAttrs = colorPref.f("P24I2");
                String attrsToSave = savedAttrs + "," + attributeName;
                colorPref.a("P24I2", (Object) (attrsToSave));
                attributes.add(new Attribute(attributeName, savedAttrs.isEmpty() ? "Custom" : null));
                assert binding.colorList.getAdapter() != null;
                binding.colorList.getAdapter().notifyItemInserted(attributes.size());
                binding.colorList.smoothScrollToPosition(attributes.size());
                dialog.dismiss();
            } else {
                SketchwareUtil.toastError(Helper.getResString(R.string.unknown_attr));
            }
        });
    }

    private boolean isAttrExist(String attr) {
        for (Attribute attribute : attributes) {
            if (attribute.name.equals(attr)) {
                return true;
            }
        }
        return false;
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

        attributes.add(new Attribute("colorSurface", "Surface"));
        attributes.add(new Attribute("colorOnSurface"));
        attributes.add(new Attribute("colorSurfaceVariant"));
        attributes.add(new Attribute("colorOnSurfaceVariant"));
        attributes.add(new Attribute("colorSurfaceInverse"));
        attributes.add(new Attribute("colorOnSurfaceInverse"));

        attributes.add(new Attribute("colorPrimary", "Primary"));
        attributes.add(new Attribute("colorOnPrimary"));
        attributes.add(new Attribute("colorPrimaryContainer"));
        attributes.add(new Attribute("colorOnPrimaryContainer"));

        attributes.add(new Attribute("colorSecondary", "Secondary"));
        attributes.add(new Attribute("colorOnSecondary"));
        attributes.add(new Attribute("colorSecondaryContainer"));
        attributes.add(new Attribute("colorOnSecondaryContainer"));

        attributes.add(new Attribute("colorTertiary", "Tertiary"));
        attributes.add(new Attribute("colorOnTertiary"));
        attributes.add(new Attribute("colorTertiaryContainer"));
        attributes.add(new Attribute("colorOnTertiaryContainer"));

        attributes.add(new Attribute("colorError", "Error"));
        attributes.add(new Attribute("colorOnError"));
        attributes.add(new Attribute("colorErrorContainer"));
        attributes.add(new Attribute("colorOnErrorContainer"));

        String savedAttrs = colorPref.f("P24I2");
        if (savedAttrs != null && !savedAttrs.isEmpty()) {
            String[] customAttrs = savedAttrs.split(",");
            for (int i = 0; i < customAttrs.length; i++) {
                String attr = customAttrs[i];
                if (attr.isEmpty()) continue;
                attributes.add(new Attribute(
                        attr,
                        i == 0 ? "Custom" : null));
            }
        }

    }

    private void initializeResColors() {
        if (sc_id == null || yq == null)
            return;
        String fileNightPath = wq.b(sc_id) + "/files/resource/values-night/colors.xml";

        ArrayList<ColorModel> colorList = new ArrayList<>();
        ArrayList<ColorModel> colorNightList = new ArrayList<>();

        colorsEditorManager.parseColorsXML(colorList, yq.getXMLColor());
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
        void selectedMaterialColorAttr(String attr, int attrColor);
    }

    public record Attribute(String name, String title) {

        public Attribute(String name) {
            this(name, null);
        }

        public boolean hasTitle() {
            return title != null;
        }

        public String attrTitle() {
            return title + " Colors :";
        }
    }

    public record ResColor(String colorName, int colorValue, int nightColorValue) {
    }

    private class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

        public ColorsAdapter() {
        }

        @Override
        public int getItemCount() {
            return colorGroups.get(l).length;
        }

        @Override
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

        @Override
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

        private final int selectedPosition;

        public AttrAdapter(ArrayList<Attribute> attributeList, int selectedPosition) {
            this.attributeList = attributeList;
            this.selectedPosition = selectedPosition;
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
            holder.binding.tvAttrName.setText(attribute.name());

            int lightColor = PropertiesUtil.parseColor(colorsEditorManager.getColorValueFromAttrs(activity, attribute.name(), 1, false));
            int darkColor = PropertiesUtil.parseColor(colorsEditorManager.getColorValueFromAttrs(activity, attribute.name(), 1, true));

            holder.binding.darkContainer.setBackgroundColor(darkColor);
            holder.binding.lightContainer.setBackgroundColor(lightColor);
            holder.binding.darkTtl.setTextColor(lightColor);
            holder.binding.lightTtl.setTextColor(darkColor);
            holder.binding.title.setVisibility(attribute.hasTitle() ? View.VISIBLE : View.GONE);
            if (attribute.hasTitle()) {
                holder.binding.title.setText(attribute.attrTitle());
            }
            holder.binding.checkedImg.setVisibility(selectedPosition == position ? View.VISIBLE : View.GONE);

            holder.binding.container.setOnClickListener(view -> {
                if (materialColorAttr != null) {
                    materialColorAttr.selectedMaterialColorAttr(attribute.name(), material3LibraryManager.canUseNightVariantColors() ? darkColor : lightColor);
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

            holder.binding.container.setOnClickListener(view -> {
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
