// ImportIconActivity.java
package pro.sketchware.activities.importicon;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.DialogFilterIconsLayoutBinding;
import com.sketchware.remod.databinding.DialogSaveIconBinding;
import com.sketchware.remod.databinding.ImportIconBinding;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.WB;
import a.a.a.Zx;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;
import pro.sketchware.activities.importicon.adapters.IconAdapter;
import pro.sketchware.utility.SvgUtils;

public class ImportIconActivity extends BaseAppCompatActivity implements IconAdapter.OnIconSelectedListener {

    private final OnBackPressedCallback searchViewCloser = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            setEnabled(false);
            if (search.isActionViewExpanded()) {
                search.collapseActionView();
                searchView.setQuery("", true);
            } else {
                getOnBackPressedDispatcher().onBackPressed();
            }
        }
    };

    private ImportIconBinding binding;

    private String iconName;
    private WB iconNameValidator;
    private MenuItem search;
    private SearchView searchView;
    private IconAdapter adapter = null;
    private ArrayList<String> alreadyAddedImageNames;
    private SvgUtils svgUtils;
    private String selected_icon_type = ICON_TYPE_ROUND;
    private int selected_color = Color.parseColor("#FFFFFF");
    private String selected_color_hex = "#FFFFFF";
    private int selectedIconPosition = -1;

    private static final String ICON_TYPE_OUTLINE = "outline";
    private static final String ICON_TYPE_SHARP = "sharp";
    private static final String ICON_TYPE_TWO_TONE = "twotone";
    private static final String ICON_TYPE_ROUND = "round";
    private static final String ICON_TYPE_BASELINE = "baseline";

    private List<Pair<String, String>> allIconPaths;
    private List<Pair<String, String>> icons;
    private final int ITEMS_PER_PAGE = 40;
    private int currentPage = 0;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private Zx colorpicker;

    private int getGridLayoutColumnCount() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 80;
    }

    private boolean doExtractedIconsExist() {
        return new oB().e(wq.getExtractedIconPackStoreLocation());
    }

    private void extractIcons() {
        KB.a(this, "icons" + File.separator + "icon_pack.zip", wq.getExtractedIconPackStoreLocation());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (binding.imageList.getLayoutManager() instanceof GridLayoutManager manager) {
            manager.setSpanCount(getGridLayoutColumnCount());
        }
        binding.imageList.requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ImportIconBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        colorpicker = new Zx(this, 0xFFFFFFFF, false, false);
        svgUtils = new SvgUtils(this);
        Toolbar toolbar = binding.toolbar.toolbar;
        binding.toolbar.layoutMainLogo.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getTranslatedString(R.string.design_manager_icon_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        alreadyAddedImageNames = getIntent().getStringArrayListExtra("imageNames");

        binding.imageList.setLayoutManager(new GridLayoutManager(getBaseContext(), getGridLayoutColumnCount()));
        adapter = new IconAdapter(this, selected_icon_type, selected_color, this);
        binding.imageList.setAdapter(adapter);
        k();

        binding.filterIconsButton.setOnClickListener(v -> showFilterDialog());

        binding.imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLoading || isLastPage) return;

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        // Reached the end, load more items
                        isLoading = true;
                        loadMoreItems();
                    }
                }
            }
        });


        new Handler().postDelayed(() -> new InitialIconLoader(this).execute(), 300L);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_import_icon, menu);
        search = menu.findItem(R.id.menu_find);
        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterIcons(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_find) {
            searchViewCloser.setEnabled(true);
            getOnBackPressedDispatcher().addCallback(this, searchViewCloser);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setIconName(int iconPosition) {
        iconName = ("icon_" + adapter.getCurrentList().get(iconPosition).first + "_" + selected_icon_type);
    }

    private void setIconColor() {
        new IconColorChangedIconLoader(this).execute();
    }

    private void listIcons() {
        allIconPaths = new ArrayList<>();

        String iconPackStoreLocation = wq.getExtractedIconPackStoreLocation() + File.separator + "svg/";
        try (Stream<Path> iconFiles = Files.list(Paths.get(iconPackStoreLocation))) {
            iconFiles.map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(folderName -> allIconPaths.add(new Pair<>(
                            folderName,
                            Paths.get(iconPackStoreLocation, folderName).toString()
                    )));
        } catch (IOException e) {
            e.printStackTrace();
        }

        icons = new ArrayList<>();
        currentPage = 0; // Reset currentPage to zero
        Log.d("icons", allIconPaths.toString());
        runOnUiThread(this::loadMoreItems);
    }

    private void loadMoreItems() {
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, allIconPaths.size());

        if (start < end) {
            List<Pair<String, String>> newItems = allIconPaths.subList(start, end);
            icons.addAll(newItems);
            adapter.submitList(new ArrayList<>(icons));
            currentPage++;
        } else {
            // No more items to load
            isLastPage = true;
        }
        isLoading = false;
    }


    private void filterIcons(String query) {
        if (query.isEmpty()) {
            icons.clear();
            currentPage = 0;
            loadMoreItems();
            return;
        }

        var filteredIcons = new ArrayList<Pair<String, String>>(allIconPaths.size());
        for (Pair<String, String> icon : allIconPaths) {
            if (icon.first.toLowerCase().contains(query.toLowerCase())) {
                filteredIcons.add(icon);
            }
        }
        icons.clear();
        icons.addAll(filteredIcons);
        adapter.submitList(new ArrayList<>(icons));
    }

    private void showFilterDialog() {
        DialogFilterIconsLayoutBinding dialogBinding = DialogFilterIconsLayoutBinding.inflate(getLayoutInflater());

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .setTitle("Filter icons")
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Apply", null)
                .create();
        dialog.setView(dialogBinding.getRoot());

        dialogBinding.selectColour.setText("Select Color: " + selected_color_hex);
        dialogBinding.selectColour.setOnClickListener(view -> {
            colorpicker.a(new Zx.b() {
                @Override
                public void a(int var1) {
                    selected_color = var1;
                    selected_color_hex = "#" + String.format("%06X", var1 & (0x00FFFFFF));
                    adapter.setSelectedColor(selected_color);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void a(String var1, int var2) {
                    selected_color = var2;
                    selected_color_hex = "#" + String.format("%06X", var2 & (0x00FFFFFF));
                    adapter.setSelectedColor(selected_color);
                    adapter.notifyDataSetChanged();

                }
            });
            colorpicker.showAtLocation(view, Gravity.CENTER, 0, 0);
        });

        switch (selected_icon_type) {
            case ICON_TYPE_OUTLINE -> dialogBinding.chipGroupStyle.check(R.id.chip_outline);
            case ICON_TYPE_BASELINE -> dialogBinding.chipGroupStyle.check(R.id.chip_baseline);
            case ICON_TYPE_SHARP -> dialogBinding.chipGroupStyle.check(R.id.chip_sharp);
            case ICON_TYPE_TWO_TONE -> dialogBinding.chipGroupStyle.check(R.id.chip_twotone);
            case ICON_TYPE_ROUND -> dialogBinding.chipGroupStyle.check(R.id.chip_round);
        }

        dialog.setOnShowListener(dialogInterface -> {

            Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                int checkedChipId = dialogBinding.chipGroupStyle.getCheckedChipId();
                if (checkedChipId == R.id.chip_outline && !selected_icon_type.equals(ICON_TYPE_OUTLINE)) {
                    updateIcons(ICON_TYPE_OUTLINE);
                }
                if (checkedChipId == R.id.chip_twotone && !selected_icon_type.equals(ICON_TYPE_TWO_TONE)) {
                    updateIcons(ICON_TYPE_TWO_TONE);
                }
                if (checkedChipId == R.id.chip_baseline && !selected_icon_type.equals(ICON_TYPE_BASELINE)) {
                    updateIcons(ICON_TYPE_BASELINE);
                }
                if (checkedChipId == R.id.chip_sharp && !selected_icon_type.equals(ICON_TYPE_SHARP)) {
                    updateIcons(ICON_TYPE_SHARP);
                }
                if (checkedChipId == R.id.chip_round && !selected_icon_type.equals(ICON_TYPE_ROUND)) {
                    updateIcons(ICON_TYPE_ROUND);
                }
                dialogInterface.dismiss();

            });
        });

        dialog.show();

    }

    private void updateIcons(String type) {
        selected_icon_type = type;
        adapter.setSelectedIconType(selected_icon_type);
        adapter.notifyDataSetChanged();
    }

    private void showSaveDialog(int iconPosition) {
        DialogSaveIconBinding dialogBinding = DialogSaveIconBinding.inflate(getLayoutInflater());

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .setTitle("Save")
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Save", null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {

            Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                if (iconNameValidator.b() && selectedIconPosition >= 0) {
                    String resFullname = adapter.getCurrentList().get(selectedIconPosition).second + File.separator + selected_icon_type + ".svg";
                    Log.d("svg Imported icon full res", resFullname);
                    Intent intent = new Intent();
                    intent.putExtra("iconName", dialogBinding.inputText.getText().toString());
                    intent.putExtra("iconPath", resFullname);

                    intent.putExtra("iconColor", selected_color);
                    intent.putExtra("iconColorHex", selected_color_hex);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    return;
                }
                dialogInterface.dismiss();
            });
        });

        svgUtils.loadImage(dialogBinding.icon, adapter.getCurrentList().get(iconPosition).second + File.separator + selected_icon_type + ".svg");
        dialogBinding.icon.setColorFilter(selected_color, PorterDuff.Mode.SRC_IN);
        iconNameValidator = new WB(getApplicationContext(), dialogBinding.textInputLayout, uq.b, alreadyAddedImageNames);
        dialogBinding.licenceInfo.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.apache.org/licenses/LICENSE-2.0.txt");
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        dialogBinding.inputText.setText(iconName);
        dialog.setView(dialogBinding.getRoot());
        dialog.show();
    }

    private static class InitialIconLoader extends MA {
        private final WeakReference<ImportIconActivity> activity;

        public InitialIconLoader(ImportIconActivity activity) {
            super(activity);
            this.activity = new WeakReference<>(activity);
            activity.addTask(this);
        }

        @Override
        public void a() {
            var activity = this.activity.get();
            activity.h();
            activity.setIconColor();
        }

        @Override
        public void b() {
            var activity = this.activity.get();
            if (!activity.doExtractedIconsExist()) {
                activity.extractIcons();
            }
        }

        @Override
        public void a(String str) {
            activity.get().h();
        }

    }

    private static class IconColorChangedIconLoader extends MA {
        private final WeakReference<ImportIconActivity> activity;

        public IconColorChangedIconLoader(ImportIconActivity activity) {
            super(activity);
            this.activity = new WeakReference<>(activity);
            activity.addTask(this);
            activity.k();
        }

        @Override
        public void a() {
            var activity = this.activity.get();
            activity.h();
            int oldPosition = activity.selectedIconPosition;
            activity.selectedIconPosition = -1;
            activity.adapter.notifyItemChanged(oldPosition);
            activity.adapter.submitList(activity.icons);
        }

        @Override
        public void b() {
            var activity = this.activity.get();
            activity.listIcons();
            activity.runOnUiThread(() -> {
                if (activity.searchView != null) {
                    activity.searchView.setQuery("", false);
                }
            });
        }

        @Override
        public void a(String str) {
            activity.get().h();
        }
    }

    @Override
    public void onIconSelected(int position) {
        if (!mB.a()) {
            selectedIconPosition = position;
            setIconName(position);
            showSaveDialog(position);
        }
    }
}
