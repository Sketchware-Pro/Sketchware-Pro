package com.besome.sketch.editor.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.a.a.Vs;
import a.a.a.Ws;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import com.sketchware.remod.R;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Vs onBlockCategorySelectListener;
    private List<HashMap<String, Object>> allPalettes;
    private LinearLayout paletteContainer;
    private String SearchValue = "";
    private Boolean IsLoadStarted = false;
    private Boolean IsFirstItemSelected = false;
    private static SharedPreferences SPSaveSearchMainData;

    public PaletteSelector(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        SPSaveSearchMainData = context.getSharedPreferences("SPSaveSearchMainData", Context.MODE_PRIVATE);
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setPadding(
                (int) wB.a(context, 8f),
                (int) wB.a(context, 4f),
                (int) wB.a(context, 8f),
                (int) wB.a(context, 4f)
        );

        TextView searchTextView = new TextView(context);
        searchTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        searchTextView.setText("  Search...");
        searchTextView.setOnClickListener(v -> showSearchDialog());
        addView(searchTextView);

        paletteContainer = new LinearLayout(context);
        paletteContainer.setOrientation(LinearLayout.VERTICAL);
        paletteContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        addView(paletteContainer);

        initializePalettes();
    }

    private void unselectAllPalettes() {
        for (int i = 0; i < paletteContainer.getChildCount(); i++) {
            Ws childAt = (Ws) paletteContainer.getChildAt(i);
            if (childAt != null) {
                childAt.setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Ws) {
            unselectAllPalettes();
            Ws paletteView = (Ws) v;
            paletteView.setSelected(true);
            onBlockCategorySelectListener.a(paletteView.getId(), paletteView.getColor());
        }
    }

    public void setOnBlockCategorySelectListener(Vs listener) {
        onBlockCategorySelectListener = listener;
    }

    private void initializePalettes() {

        allPalettes = new ArrayList<>();

        String[] texts = {
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

        int[] colors = {0xffee7d16, 0xffcc5b22, 0xffe1a92a, 0xff5cb722, 0xff23b9a9, 0xffa1887f, 0xff4a6cd4, 0xff2ca5e2, 0xff8a55d7};

        for (int i = 0; i < texts.length; i++) {
            HashMap<String, Object> palette = new HashMap<>();
            palette.put("index", i);
            palette.put("text", texts[i]);
            palette.put("color", colors[i]);
            allPalettes.add(palette);
            addPalette(i, texts[i], colors[i]);
        }


        for (HashMap<String, Object> palette : new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector().getPaletteSelector()) {
            allPalettes.add(palette);
            addPalette(
                    Integer.parseInt(palette.get("index").toString()),
                    palette.get("text").toString(),
                    Integer.parseInt(palette.get("color").toString())
            );
        }
        try {
            if (paletteContainer.getChildCount() > 0) {
                View firstChild = paletteContainer.getChildAt(0);
                firstChild.performClick();
            }
        } catch (Exception ignored) {}
    }

    private void addPalette(int id, String title, int color) {
        String SearchType = "N1";
        try {
            if (SPSaveSearchMainData.contains("type")) {
                SearchType = SPSaveSearchMainData.getString("type", "");
            }
        } catch (Exception ignored) {}


        if (SearchType.equals("N1") && title.toLowerCase().contains(SearchValue.toLowerCase()) ||
                SearchType.equals("N2") && title.toLowerCase().startsWith(SearchValue.toLowerCase()) ||
                SearchValue.isEmpty()) {
            Ws paletteView = new Ws(context, id, title, color);
            paletteView.setTag(String.valueOf(id));
            paletteView.setOnClickListener(this);
            paletteContainer.addView(paletteView);
            if (!IsFirstItemSelected) {
                IsFirstItemSelected = true;
                paletteView.setSelected(true);
            }
        }
    }


    public void showSearchDialog() {
        IsLoadStarted = false;
        IsFirstItemSelected = false;

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.palettes_search_dialog)
                .setCancelable(false)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View inflate = LayoutInflater.from(context).inflate(R.layout.palettes_search_dialog, null);
        dialog.setView(inflate);

        final TextView dialogTitle = inflate.findViewById(R.id.dialog_title);
        final MaterialButton btnCancel = inflate.findViewById(R.id.dialog_btn_cancel);
        final MaterialButton btnSearch = inflate.findViewById(R.id.dialog_btn_entre);
        final MaterialButton btnRestore = inflate.findViewById(R.id.dialog_btn_restore);
        final RadioButton radio1 = inflate.findViewById(R.id.radiob_1);
        final RadioButton radio2 = inflate.findViewById(R.id.radiob_2);
        final EditText searchValue = inflate.findViewById(R.id.edittext_search_value);
        final TextInputLayout searchInputLayout = inflate.findViewById(R.id.textInputLayout_search);

        dialogTitle.setText(Helper.getResString(R.string.search_in_palettes));
        btnSearch.setText(Helper.getResString(R.string.common_word_search));
        btnRestore.setText(Helper.getResString(R.string.common_word_restore));
        btnCancel.setText(Helper.getResString(R.string.common_word_cancel));

        String errorMessage = Helper.getResString(R.string.search_error_msg);

        if (SPSaveSearchMainData.contains("type")) {
            searchValue.setText(SPSaveSearchMainData.getString("value", ""));
            if ("N1".equals(SPSaveSearchMainData.getString("type", ""))) {
                radio1.setChecked(true);
            } else {
                radio2.setChecked(true);
            }
        } else {
            radio1.setChecked(true);
        }

        CompoundButton.OnCheckedChangeListener checkedChangeListener = (buttonView, isChecked) -> {
            SPSaveSearchMainData.edit().putString("type", isChecked ? "N1" : "N2").apply();
            validateSearch(searchValue.getText().toString(), searchInputLayout, errorMessage);
        };

        radio1.setOnCheckedChangeListener(checkedChangeListener);

        searchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SPSaveSearchMainData.edit().putString("type", radio1.isChecked() ? "N1" : "N2").apply();
                validateSearch(s.toString(), searchInputLayout, errorMessage);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCancel.setOnClickListener(v -> {
            if (!IsLoadStarted) {
                dialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(v -> {
            if (!IsLoadStarted && validateSearch(searchValue.getText().toString(), searchInputLayout, errorMessage)) {
                IsLoadStarted = true;
                btnSearch.setText(Helper.getResString(R.string.searching));
                SPSaveSearchMainData.edit()
                        .putString("value", searchValue.getText().toString().trim())
                        .apply();
                SearchTask searchTask = new SearchTask(dialog, searchValue.getText().toString().trim());
                searchTask.execute();
            }
        });

        btnRestore.setOnClickListener(v -> {
            if (!IsLoadStarted) {
                IsLoadStarted = true;
                btnRestore.setText(Helper.getResString(R.string.restoring));
                ResetSavedInfo();
                SearchTask searchTask = new SearchTask(dialog, "");
                searchTask.execute();
            }
        });

        dialog.show();
    }

    private boolean validateSearch(String query, TextInputLayout layout, String errorMessage) {
        boolean isValid = CanUpdateList(query);
        if (isValid) {
            layout.setError(null);
        } else {
            layout.setError(errorMessage);
        }
        return isValid;
    }

    public void filterPalettes(String valueStr) {
        IsFirstItemSelected = false;
        paletteContainer.removeAllViews();
        SearchValue = valueStr.trim();
        initializePalettes();
    }

    public static void ResetSavedInfo() {
        SPSaveSearchMainData.edit().putString("value", "").apply();
        SPSaveSearchMainData.edit().putString("type", "N1").apply();
    }

    private boolean CanUpdateList(String query) {
        String trimmedQuery = query.trim();
        String searchType = SPSaveSearchMainData.getString("type", "N1");

        for (HashMap<String, Object> palette : allPalettes) {
            String title = palette.get("text").toString();
            if ((searchType.equals("N1") && title.toLowerCase().contains(trimmedQuery.toLowerCase())) ||
                    (searchType.equals("N2") && title.toLowerCase().startsWith(trimmedQuery.toLowerCase())) ||
                    trimmedQuery.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public class SearchTask {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final AlertDialog dialog;
        private final String searchQuery;

        public SearchTask(AlertDialog dialog, String searchQuery) {
            this.dialog = dialog;
            this.searchQuery = searchQuery;
        }

        public void execute() {
            executorService.execute(() -> handler.post(() -> {
                filterPalettes(searchQuery);
                dialog.dismiss();
            }));
        }
    }
}