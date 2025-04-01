package com.besome.sketch.editor.logic;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import a.a.a.Vs;
import a.a.a.aB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PalettesSearchDialogBinding;

public class PaletteSelector extends RecyclerView {

    private final Context context;

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

    private String searchValue = "";
    private PaletteSelectorAdapter paletteAdapter;
    private List<paletteSelectorRecord> allPalettes;

    public PaletteSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void initialize(Vs onBlockCategorySelectListener) {
        paletteAdapter = new PaletteSelectorAdapter(this, onBlockCategorySelectListener);
        setAdapter(paletteAdapter);

        Executors.newSingleThreadExecutor().execute(() ->
                new Handler(Looper.getMainLooper()).post(this::initializePalettes)
        );
    }

    private void initializePalettes() {
        allPalettes = new ArrayList<>();

        for (int i = 0; i < MainCategoriesNames.length; i++) {
            allPalettes.add(new paletteSelectorRecord(MainCategoriesIds[i], MainCategoriesNames[i], MainCategoriesColors[i]));
        }

        new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector()
                .getPaletteSelector()
                .forEach(this::addDynamicPalette);

        paletteAdapter.setPalettes(allPalettes);

        if (paletteAdapter.getItemCount() > 0) {
            paletteAdapter.selectPosition(0);
        }
    }

    public void performClickPalette(int tag) {
        paletteAdapter.selectPaletteById(tag);
    }

    private void addDynamicPalette(HashMap<String, Object> paletteMap) {
        String text = Objects.requireNonNull(paletteMap.get("text")).toString();
        if (matchesSearch(text)) {
            int index = Integer.parseInt(Objects.requireNonNull(paletteMap.get("index")).toString());
            int color = Integer.parseInt(Objects.requireNonNull(paletteMap.get("color")).toString());
            allPalettes.add(new paletteSelectorRecord(index, text, color));
        }
    }

    public boolean matchesSearch(String title) {
        return searchValue.isEmpty() || title.toLowerCase().contains(searchValue.toLowerCase());
    }

    public void showSearchDialog() {
        aB dialog = new aB((Activity) context);
        PalettesSearchDialogBinding binding = PalettesSearchDialogBinding.inflate(((Activity) context).getLayoutInflater());

        dialog.b(Helper.getResString(R.string.search_in_palettes_dialog_title));
        dialog.b(Helper.getResString(R.string.search), v1 -> {
            if (binding.textInputLayoutSearch.getError() == null) {
                startSearch(dialog, Helper.getText(binding.edittextSearchValue).trim());
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.cancel), v1 -> dialog.dismiss());
        if (!searchValue.isEmpty()) {
            dialog.configureDefaultButton(Helper.getResString(R.string.restore), v1 -> startSearch(dialog, ""));
        }
        dialog.a(binding.getRoot());
        binding.edittextSearchValue.setText(searchValue);
        binding.edittextSearchValue.addTextChangedListener(new SimpleTextWatcher(s -> validateSearch(s.toString(), binding.textInputLayoutSearch)));
        dialog.show();
    }

    private boolean canSearch(String query) {
        String trimmedQuery = query.trim().toLowerCase();
        return allPalettes.stream().anyMatch(palette -> {
            String title = palette.text().toLowerCase();
            return title.contains(trimmedQuery);
        });
    }

    private void validateSearch(String query, TextInputLayout layout) {
        layout.setError(canSearch(query) ? null : Helper.getResString(R.string.search_error_message));
    }

    private void startSearch(aB dialog, String query) {
        searchValue = query;
        Executors.newSingleThreadExecutor().execute(() ->
                new Handler(Looper.getMainLooper()).post(() -> {
                    initializePalettes();
                    dialog.dismiss();
                })
        );
    }

    public void setOnBlockCategorySelectListener(Vs listener) {
        initialize(listener);
    }

    public record paletteSelectorRecord(int index, String text, int color) {
    }

    public record SimpleTextWatcher(
            java.util.function.Consumer<CharSequence> onTextChanged) implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged.accept(s);
        }
    }
}
