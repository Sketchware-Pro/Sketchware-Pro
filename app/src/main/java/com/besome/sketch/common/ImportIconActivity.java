package com.besome.sketch.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.stream.Stream;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.WB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;

import com.sketchware.remod.databinding.DialogFilterIconsLayoutBinding;

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
    
    /**
     * Current icons' color, where 0 stands for black, 1 for grey, and 2 for white.
     */
    private int iconType = -1;
    private ArrayList<Pair<String, String>> icons = new ArrayList<>();

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
        icons = new ArrayList<>();
        
        String iconFolderName = "filled";
        String iconPackStoreLocation = wq.getExtractedIconPackStoreLocation();
        try (Stream<Path> iconFiles = Files.list(Paths.get(iconPackStoreLocation, iconFolderName))) {
            iconFiles.map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(iconName -> icons.add(new Pair<>(
                            iconName,
                            Paths.get(iconPackStoreLocation,iconFolderName, iconName).toString()
                    )));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterIcons(String query) {
        var filteredIcons = new ArrayList<Pair<String, String>>(icons.size());
        for (Pair<String, String> icon : icons) {
            if (icon.first.toLowerCase().contains(query.toLowerCase())) {
                filteredIcons.add(icon);
            }
        }
        adapter.submitList(filteredIcons);
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
                    Intent intent = new Intent();
                    intent.putExtra("iconName", dialogBinding.inputText.getText().toString());
                    intent.putExtra("iconPath", adapter.getCurrentList().get(adapter.selectedIconPosition).second);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    return;
                }
                dialogInterface.dismiss();
            });
        });

        loadImage(dialogBinding.icon,adapter.getCurrentList().get(iconPosition).second);
        
        iconNameValidator = new WB(getApplicationContext(), dialogBinding.textInputLayout, uq.b,  alreadyAddedImageNames);
        String filenameWithoutExtension = iconName.substring(0, iconName.lastIndexOf('.'));
        dialogBinding.inputText.setText(filenameWithoutExtension);
        dialog.setView(dialogBinding.getRoot());
        dialog.show();
    }
    
        private void loadImage(ImageView imageView, String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            ImageLoader imageLoader = new ImageLoader.Builder(this)
                .components(new ComponentRegistry.Builder()
                    .add(new SvgDecoder.Factory())
                    .build())
                .build();
            
            ImageRequest request = new ImageRequest.Builder(this)
                .data(file)
                .target(imageView)
                .build();
    
            imageLoader.enqueue(request);
    }  else {
            // Handle the case where the file doesn't exist
        }
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
            loadImage(holder.icon, filePath);
            
            
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