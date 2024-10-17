package com.besome.sketch.editor.logic;

import static mod.SketchwareUtil.dpToPx;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import a.a.a.Vs;
import a.a.a.Ws;
import mod.hey.studios.util.Helper;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Vs onBlockCategorySelectListener;
    private LinearLayout paletteContainer;

    private String searchValue = "", searchType = "Contains";
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
            Helper.getResString(R.string.block_category_moreblock)
    };

    private final int[] MainCategoriesColors = {
            0xffee7d16, 0xffcc5b22, 0xffe1a92a,
            0xff5cb722, 0xff23b9a9, 0xffa1887f,
            0xff4a6cd4, 0xff2ca5e2, 0xff8a55d7
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
        searchTextView.setOnClickListener(v -> showSearchDialog());
        addView(searchTextView);
    }

    private void initializePalettes() {

        isFirstItemSelected = false;
        paletteContainer.removeAllViews();

        allPalettes = new ArrayList<>();

        for (int i = 0; i < MainCategoriesNames.length; i++) {
            addPalette(i, MainCategoriesNames[i], MainCategoriesColors[i]);
            HashMap<String, Object> palette = new HashMap<>();
            palette.put("index", i);
            palette.put("text", MainCategoriesNames[i]);
            palette.put("color", MainCategoriesColors[i]);
            allPalettes.add(palette);
        }

        new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector()
                .getPaletteSelector()
                .forEach(palette -> {
                    allPalettes.add(palette);
                    addPalette(
                            Integer.parseInt(palette.get("index").toString()),
                            palette.get("text").toString(),
                            Integer.parseInt(palette.get("color").toString())
                    );
                });

        try {
            if (paletteContainer.getChildCount() > 0) {
                paletteContainer.getChildAt(0).performClick();
            }
        } catch (Exception ignored) {}
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
        View dialogView = LayoutInflater.from(context).inflate(R.layout.palettes_search_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText searchInput = dialogView.findViewById(R.id.edittext_search_value);
        RadioButton radio1 = dialogView.findViewById(R.id.radioButton1);
        RadioButton radio2 = dialogView.findViewById(R.id.radioButton2);

        TextInputLayout inputLayout = dialogView.findViewById(R.id.textInputLayout_search);

        MaterialButton cancelBtn = dialogView.findViewById(R.id.dialog_btn_cancel);
        MaterialButton entreBtn = dialogView.findViewById(R.id.dialog_btn_entre);
        MaterialButton restoreBtn = dialogView.findViewById(R.id.dialog_btn_restore);

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
        entreBtn.setOnClickListener(v -> {
            if (inputLayout.getError() == null) {
                entreBtn.setText(Helper.getResString(R.string.searching));
                startSearch(dialog, searchInput.getText().toString().trim());
            }
        });
        restoreBtn.setOnClickListener(v -> {
            restoreBtn.setText(Helper.getResString(R.string.restoring));
            searchType = "Contains";
            startSearch(dialog, "");
        });

        if (searchType.equals("Contains")) {
            radio1.setChecked(true);
        } else {
            radio2.setChecked(true);
        }
        searchInput.setText(searchValue);

        radio1.setOnCheckedChangeListener((button, isChecked) -> searchType = isChecked ? "Contains" : "StartWith");
        searchInput.addTextChangedListener(new SimpleTextWatcher(s -> validateSearch(s.toString(), inputLayout)));

        dialog.show();
    }

    private boolean matchesSearch(String title) {
        String lowerTitle = title.toLowerCase();
        String lowerSearchValue = searchValue.toLowerCase();
        return searchValue.isEmpty() || (searchType.equals("Contains") && lowerTitle.contains(lowerSearchValue)) ||
                (searchType.equals("StartWith") && lowerTitle.startsWith(lowerSearchValue));
    }

    private boolean canSearch(String query) {
        String trimmedQuery = query.trim().toLowerCase();
        return allPalettes.stream().anyMatch(palette -> {
            String title = palette.get("text").toString().toLowerCase();
            return searchType.equals("Contains") ? title.contains(trimmedQuery) : title.startsWith(trimmedQuery);
        });
    }

    private void validateSearch(String query, TextInputLayout layout) {
        layout.setError(canSearch(query) ? null : Helper.getResString(R.string.search_error_message));
    }

    private void startSearch(AlertDialog dialog, String query) {
        searchValue = query;
        Executors.newSingleThreadExecutor().execute(() -> new Handler(Looper.getMainLooper()).post(() -> {
            initializePalettes();
            dialog.dismiss();
        }));
    }

    private record SimpleTextWatcher(
            java.util.function.Consumer<CharSequence> onTextChanged) implements android.text.TextWatcher {

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(android.text.Editable s) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChanged.accept(s);
            }
        }
}
