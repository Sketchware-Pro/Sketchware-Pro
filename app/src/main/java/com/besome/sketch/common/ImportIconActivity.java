package com.besome.sketch.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import a.a.a.jC;
import a.a.a.pu;
import coil.ComponentRegistry;
import coil.ImageLoader;
import coil.decode.SvgDecoder;
import coil.request.ImageRequest;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.sketchware.remod.R;


import com.sketchware.remod.databinding.DialogSaveIconBinding;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.WB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;

import com.sketchware.remod.databinding.DialogFilterIconsLayoutBinding;
import mod.nethical.svg.SvgUtils;

public class ImportIconActivity extends BaseAppCompatActivity {
    
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

    private RecyclerView iconsList;
    private String iconName;
    private WB iconNameValidator;
    private MenuItem search;
    private SearchView searchView;
    private IconAdapter adapter = null;
    private ArrayList<String> alreadyAddedImageNames;
    private SvgUtils svgUtils;
    /**
     * Current icons' style where 0 stands for filled and 1 stands for outlind
     */
    private final static int OUTLINE_ICONS = 0;
    private final static int FILLED_ICONS = 1;
    
    private int iconType = -1;
    private List<Pair<String, String>> allIconPaths;
    private List<Pair<String, String>> icons;
    private final int ITEMS_PER_PAGE = 20;
    private int currentPage = 0;

    
    private int getGridLayoutColumnCount() {
       return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
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
        if (iconsList.getLayoutManager() instanceof GridLayoutManager manager) {
            manager.setSpanCount(getGridLayoutColumnCount());
        }
        iconsList.requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_icon);

        svgUtils = new SvgUtils(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(getTranslatedString(R.string.design_manager_icon_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        alreadyAddedImageNames = getIntent().getStringArrayListExtra("imageNames");

        iconsList = findViewById(R.id.image_list);
        iconsList.setHasFixedSize(true);
        iconsList.setLayoutManager(new GridLayoutManager(getBaseContext(), getGridLayoutColumnCount()));
        adapter = new IconAdapter();
        iconsList.setAdapter(adapter);
        k();
        
        ExtendedFloatingActionButton filterIconsButton = findViewById(R.id.filterIconsButton);
        filterIconsButton.setOnClickListener(v -> showFilterDialog());
        
        
        iconsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == icons.size() - 1) {
                    // Reached the end, load more items
                    loadMoreItems();
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
        iconName=(adapter.getCurrentList().get(iconPosition).first);
    }

    private void setIconColor() {
           new IconColorChangedIconLoader(this).execute();
    }

    private void listIcons() {
        allIconPaths = new ArrayList<>();
        
        String iconFolderName = switch (iconType){
            case FILLED_ICONS -> "filled";
            case OUTLINE_ICONS -> "outline";
            default -> "filled";
        };
        String iconPackStoreLocation = wq.getExtractedIconPackStoreLocation() + File.separator + "svg/";

        Log.d("Loading icons", "Path: "+ iconPackStoreLocation);
        try (Stream<Path> iconFiles = Files.list(Paths.get(iconPackStoreLocation))) {
            iconFiles.map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(iconName -> allIconPaths.add(new Pair<>(
                            iconName,
                            Paths.get(iconPackStoreLocation, iconName).toString()
                    )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        icons = new ArrayList<>();
        Log.d("icons",allIconPaths.toString());
        loadMoreItems(); // Load the first chunk of items
    }
    
    private void loadMoreItems() {
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, allIconPaths.size());

        if (start < end) {
            List<Pair<String, String>> newItems = allIconPaths.subList(start, end);
            icons.addAll(newItems);
            adapter.submitList(new ArrayList<>(icons));
            currentPage++;
        }
    }

        private void filterIcons(String query) {
        if(query.length()==0){
            icons.clear();
            currentPage = 0;
            loadMoreItems();
            return;
        }
        if(query.length()<3){
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
                .setTitle("Filter Icons")
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Apply", null)
                .create();

        dialog.setView(dialogBinding.getRoot());
        
        dialog.setOnShowListener(dialogInterface -> {
            
            Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                int checkedChipId = dialogBinding.chipGroupStyle.getCheckedChipId();
                if(checkedChipId==R.id.chip_outlined && iconType!=OUTLINE_ICONS){
                    iconType = OUTLINE_ICONS;
                    icons.clear();
                    allIconPaths.clear();
                    listIcons();       
                }
                if(checkedChipId==R.id.chip_outlined && iconType!=FILLED_ICONS){
                   iconType = FILLED_ICONS;
                    icons.clear();
                    allIconPaths.clear();
                    listIcons();              
                }
                dialogInterface.dismiss();
                return;               
                
            });
        });

        dialog.show();

    }
    
    private void showSaveDialog(int iconPosition){
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
                if (iconNameValidator.b() && adapter.selectedIconPosition >= 0) {
                    String resFullname =  adapter.getCurrentList().get(adapter.selectedIconPosition).second;
                    Log.d("svg Imported icon full res", resFullname);
                    Intent intent = new Intent();
                    intent.putExtra("iconName", dialogBinding.inputText.getText().toString());
                    intent.putExtra("iconPath",resFullname);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    return;
                }
                dialogInterface.dismiss();
            });
        });

        svgUtils.loadImage(dialogBinding.icon,adapter.getCurrentList().get(iconPosition).second + File.separator + "outline.svg");
        iconNameValidator = new WB(getApplicationContext(), dialogBinding.textInputLayout, uq.b,  alreadyAddedImageNames);
        String filenameWithoutExtension = iconName.substring(0, iconName.lastIndexOf('.'));
        dialogBinding.inputText.setText(filenameWithoutExtension);
        dialog.setView(dialogBinding.getRoot());
        dialog.show();
    }
    
    private class IconAdapter extends ListAdapter<Pair<String, String>, IconAdapter.ViewHolder> {
        private static final DiffUtil.ItemCallback<Pair<String, String>> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull Pair<String, String> oldItem, @NonNull Pair<String, String> newItem) {
                return oldItem.first.equals(newItem.first);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Pair<String, String> oldItem, @NonNull Pair<String, String> newItem) {
                return true;
            }
        };
        private int selectedIconPosition = -1;

        protected IconAdapter() {
            super(DIFF_CALLBACK);
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout background;
            public final ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);
                background = itemView.findViewById(R.id.icon_bg);
                icon = itemView.findViewById(R.id.img);
                icon.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedIconPosition = getLayoutPosition();
                        setIconName(selectedIconPosition);
                        showSaveDialog(selectedIconPosition);
                        
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String filePath = getItem(position).second; // Adjust according to your data structure
            svgUtils.loadImage(holder.icon, filePath + File.separator + "outline.svg");
            Log.d("Loading",filePath + File.separator + "outline.svg");
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.import_icon_list_item, parent, false));
        }
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
            int oldPosition = activity.adapter.selectedIconPosition;
            activity.adapter.selectedIconPosition = -1;
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
}