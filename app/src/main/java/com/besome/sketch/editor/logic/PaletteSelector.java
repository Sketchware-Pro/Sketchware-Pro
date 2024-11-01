package com.besome.sketch.editor.logic;

import static pro.sketchware.utility.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import pro.sketchware.R;
import pro.sketchware.databinding.PalettesSearchDialogBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import a.a.a.Vs;
import a.a.a.Ws;
import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Vs onBlockCategorySelectListener;
    private LinearLayout paletteContainer;

    private String searchValue = "";
    private boolean isFirstItemSelected = false;

    private List<HashMap<String, Object>> allPalettes;
    private final String[] MainCategoriesNames = {
            Helper.getResString(R.string.block_category_var),
            Helper.getResString(R.string.block_category_list),
            Helper.getResString(R.string.block_category_control),
            Helper.getResString(R.string.block_category_operator),
            Helper.getResString(R.string.block_category_math),
            Helper.getResString(R.string.block_category_file),
            Helper.getResString(R.string.block_category_view_func),
            Helper.getResString(R.string.block_category_component_func),
            Helper.getResString(R.string.xml_strings),
            Helper.getResString(R.string.block_category_moreblock)
    };

    private final int[] MainCategoriesColors = {
            0xffee7d16, 0xffcc5b22, 0xffe1a92a,
            0xff5cb722, 0xff23b9a9, 0xffa1887f,
            0xff4a6cd4, 0xff2ca5e2, 0xff7c83db,
            0xff8a55d7
    };

    private final int[] MainCategoriesIds = {
            0, 1, 2, 3, 4, 5, 6, 7, -1, 8
    };

    public PaletteSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));

        addSearchHeader();
        paletteContainer = new LinearLayout(context);
        paletteContainer.setOrientation(VERTICAL);
        addView(paletteContainer);
        initializePalettes();
    }

    private void addSearchHeader() {
        TextView searchTextView = new TextView(context);
        searchTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        searchTextView.setText(Helper.getResString(R.string.search_header));
        searchTextView.setPadding(dpToPx(0), dpToPx(4), dpToPx(0), dpToPx(4));
        searchTextView.setOnClickListener(v -> showSearchDialog());
        addView(searchTextView);
    }

    private void initializePalettes() {
        isFirstItemSelected = false;
        paletteContainer.removeAllViews();

        allPalettes = new ArrayList<>();

        for (int i = 0; i < MainCategoriesNames.length; i++) {
            addPaletteWithMap(MainCategoriesIds[i], MainCategoriesNames[i], MainCategoriesColors[i]);
        }

        new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector()
                .getPaletteSelector()
                .forEach(this::addDynamicPalette);

        try {
            if (paletteContainer.getChildCount() > 0) {
                paletteContainer.getChildAt(0).performClick();
            }
        } catch (Exception ignored) {
        }
    }

    public void performClickPalette(int tag) {
        try {
            if (paletteContainer != null && paletteContainer.getChildCount() > 0) {
                View view = paletteContainer.findViewWithTag(String.valueOf(tag));
                if (view != null) {
                    view.performClick();
                }
            }
        } catch (Exception ignored) {}
    }

    private void addPaletteWithMap(int id, String title, int color) {
        addPalette(id, title, color);
        allPalettes.add(createPaletteMap(id, title, color));
    }

    private void addDynamicPalette(HashMap<String, Object> palette) {
        int index = Integer.parseInt(palette.get("index").toString());
        String text = palette.get("text").toString();
        int color = Integer.parseInt(palette.get("color").toString());

        addPalette(index, text, color);
        allPalettes.add(palette);
    }

    private void addPalette(int id, String title, int color) {
        if (matchesSearch(title)) {
            Ws paletteView = new Ws(context, id, title, color);
            paletteView.setTag(String.valueOf(id));
            paletteView.setOnClickListener(this);
            paletteContainer.addView(paletteView);
            if (!isFirstItemSelected) {
                isFirstItemSelected = true;
                paletteView.setSelected(true);
            }
        }
    }

    private HashMap<String, Object> createPaletteMap(int index, String categoryName, int categoryColor) {
        HashMap<String, Object> palette = new HashMap<>();
        palette.put("index", index);
        palette.put("text", categoryName);
        palette.put("color", categoryColor);
        return palette;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Ws paletteView) {
            unselectAllPalettes();
            paletteView.setSelected(true);
            onBlockCategorySelectListener.a(paletteView.getId(), paletteView.getColor());
        }
    }

    private void unselectAllPalettes() {
        for (int i = 0; i < paletteContainer.getChildCount(); i++) {
            Ws child = (Ws) paletteContainer.getChildAt(i);
            child.setSelected(false);
        }
    }

    public void setOnBlockCategorySelectListener(Vs listener) {
        onBlockCategorySelectListener = listener;
    }

    private void showSearchDialog() {
        aB dialog = new aB((Activity) context);
        PalettesSearchDialogBinding binding = PalettesSearchDialogBinding.inflate(((Activity) context).getLayoutInflater());

        dialog.b(context.getString(R.string.search_in_palettes_dialog_title));
        dialog.b(context.getString(R.string.search), v1 -> {
            if (binding.textInputLayoutSearch.getError() == null) {
                startSearch(dialog, binding.edittextSearchValue.getText().toString().trim());
            }
            dialog.dismiss();
        });
        dialog.a(context.getString(R.string.cancel), v1 -> dialog.dismiss());
        if (!searchValue.isEmpty()) {
            dialog.configureDefaultButton(context.getString(R.string.restore), v1 -> startSearch(dialog, ""));
        }
        dialog.a(binding.getRoot());
        binding.edittextSearchValue.setText(searchValue);
        binding.edittextSearchValue.addTextChangedListener(new SimpleTextWatcher(s -> validateSearch(s.toString(), binding.textInputLayoutSearch)));
        dialog.show();
    }

    private boolean matchesSearch(String title) {
        String lowerTitle = title.toLowerCase();
        String lowerSearchValue = searchValue.toLowerCase();
        return searchValue.isEmpty() || lowerTitle.contains(lowerSearchValue);
    }

    private boolean canSearch(String query) {
        String trimmedQuery = query.trim().toLowerCase();
        return allPalettes.stream().anyMatch(palette -> {
            String title = palette.get("text").toString().toLowerCase();
            return title.contains(trimmedQuery);
        });
    }

    private void validateSearch(String query, TextInputLayout layout) {
        layout.setError(canSearch(query) ? null : Helper.getResString(R.string.search_error_message));
    }

    private void startSearch(aB dialog, String query) {
        searchValue = query;
        Executors.newSingleThreadExecutor().execute(() -> new Handler(Looper.getMainLooper()).post(() -> {
            initializePalettes();
            dialog.dismiss();
        }));
    }

    private record SimpleTextWatcher(
            java.util.function.Consumer<CharSequence> onTextChanged) implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(android.text.Editable s) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged.accept(s);
        }
    }
}
