package pro.sketchware.activities.appcompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.content.res.AppCompatResources;

import a.a.a.jC;
import a.a.a.mB;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import dev.aldi.sayuti.editor.injection.AppCompatInjection;

import mod.hey.studios.util.Helper;

import pro.sketchware.R;
import pro.sketchware.activities.appcompat.adapters.AppCompatAdapter;
import pro.sketchware.databinding.CustomDialogAttributeBinding;
import pro.sketchware.databinding.ManageAppCompatBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ManageAppCompatActivity extends BaseAppCompatActivity {

    private ManageAppCompatBinding binding;

    private String sc_id;
    private String filename;

    private ProjectFileBean projectFile;
    private ProjectLibraryBean projectLibrary;

    private String path;

    private AppCompatAdapter adapter;

    private String tabSelected;

    private ArrayList<HashMap<String, Object>> activityInjections = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);

        super.onCreate(savedInstanceState);
        binding = ManageAppCompatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        filename = getIntent().getStringExtra("file_name");
        getSupportActionBar().setSubtitle(filename);

        UI.addSystemWindowInsetToPadding(binding.list, false, false, false, true);
        binding.toolbar.setNavigationOnClickListener(
                v -> {
                    if (!mB.a()) {
                        onBackPressed();
                    }
                });
        binding.fab.setOnClickListener(v -> dialog("create", 0));
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        path =
                FileUtil.getExternalStorageDir()
                        + "/.sketchware/data/"
                        + sc_id
                        + "/injection/appcompat/"
                        + filename.replaceAll(".xml", "");
        if (!FileUtil.isExistFile(path) || FileUtil.readFile(path).isEmpty()) {
            activityInjections =
                    new Gson()
                            .fromJson(
                                    AppCompatInjection.getDefaultActivityInjections(),
                                    Helper.TYPE_MAP_LIST);
        } else {
            activityInjections = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        }
        adapter = new AppCompatAdapter();
        adapter.setOnItemClickListener(
                item -> {
                    PopupMenu popupMenu = new PopupMenu(ManageAppCompatActivity.this, item.first);
                    popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Edit");
                    popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Delete");
                    popupMenu.setOnMenuItemClickListener(
                            itemMenu -> {
                                int position = adapter.getCurrentList().indexOf(item.second);
                                int originalPosition = activityInjections.indexOf(item.second);
                                if (itemMenu.getItemId() == 0) {
                                    dialog("edit", originalPosition);
                                } else {
                                    if (originalPosition != -1) {
                                        activityInjections.remove(originalPosition);
                                        FileUtil.writeFile(
                                                path, new Gson().toJson(activityInjections));
                                        adapter.submitList(filterInjections(tabSelected));
                                    }
                                }
                                return true;
                            });
                    popupMenu.show();
                });

        binding.list.setAdapter(adapter);
        binding.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        var widget = tab.getTag().toString().toLowerCase();
                        tabSelected = widget;
                        adapter.submitList(filterInjections(widget));
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                });
        List<String> appCompats = new ArrayList<>();
        initializeProjectBean();
        if (projectFile.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            if (projectLibrary.isEnabled()) {
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                    appCompats.add("Toolbar");
                    appCompats.add("AppBarLayout");
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)
                        || projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    appCompats.add("CoordinatorLayout");
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    appCompats.add("FloatingActionButton");
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    appCompats.add("DrawerLayout");
                    appCompats.add("NavigationDrawer");
                }
                if (appCompats.isEmpty()) {
                    setNote("No options are found.", "No AppCompat options are currently available in this activity.");
                } else {
                    for (int i = 0; i < appCompats.size(); i++) {
                        TabLayout.Tab tab = binding.tabLayout.newTab();
                        tab.setText(appCompats.get(i));
                        tab.setTag(appCompats.get(i));
                        binding.tabLayout.addTab(tab);
                    }
                    binding.tabLayout.setVisibility(View.VISIBLE);
                    binding.tabsDivider.setVisibility(View.VISIBLE);
                    binding.fab.setVisibility(View.VISIBLE);
                }
            } else {
                setNote(
                        "AppCompat is disabled.", "Please enable AppCompat in the Library Manager to use it.");
            }
        } else {
            setNote("Not available.", "You're not currently in the Activity layout.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (projectFile == null || projectLibrary == null) {
            initializeProjectBean();
        }
        if (projectFile.fileType != ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY
                || !projectLibrary.isEnabled()) {
            return false;
        }
        menu.add(Menu.NONE, 0, Menu.NONE, R.string.common_word_reset)
                .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_reset))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0 -> {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
                dialog.setTitle(R.string.common_word_reset);
                dialog.setMessage(
                        "Are you sure you want to reset appcompat attributes for " + filename + "?");
                dialog.setPositiveButton(
                        R.string.common_word_yes,
                        (d, w) -> {
                            resetData();
                            adapter.submitList(filterInjections(tabSelected));
                        });
                dialog.setNegativeButton(R.string.common_word_no, null);
                dialog.show();
                return true;
            }
            default -> {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppCompatInjection.refreshInjections();
    }

    private void initializeProjectBean() {
        projectFile = jC.b(sc_id).b(filename);
        projectLibrary = jC.c(sc_id).c();
    }

    private void resetData() {
        var defInjections = AppCompatInjection.getDefaultActivityInjections();
        activityInjections = new Gson().fromJson(defInjections, Helper.TYPE_MAP_LIST);
        FileUtil.writeFile(path, defInjections);
    }

    private void setNote(String title, String message) {
        if ((title == null && message == null) || (title.isEmpty() && message.isEmpty())) {
            return;
        }

        binding.noContentLayout.setVisibility(View.VISIBLE);
        binding.noteTitle.setText(title);
        binding.noteMessage.setText(message);
    }

    private void dialog(String type, int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(type.equals("create") ? "Add new attribute" : "Edit attribute");
        CustomDialogAttributeBinding attributeBinding =
                CustomDialogAttributeBinding.inflate(getLayoutInflater());
        dialog.setView(attributeBinding.getRoot());

        attributeBinding.inputLayoutRes.setVisibility(View.GONE);

        if (type.equals("edit")) {
            String injectionValue = activityInjections.get(position).get("value").toString();
            attributeBinding.inputAttr.setText(
                    injectionValue.substring(0, injectionValue.indexOf("=")));
            attributeBinding.inputValue.setText(
                    injectionValue.substring(
                            injectionValue.indexOf("\"") + 1, injectionValue.length() - 1));
        }

        dialog.setPositiveButton(
                R.string.common_word_save,
                (dialog1, which) -> {
                    String nameInput = Helper.getText(attributeBinding.inputAttr);
                    String valueInput = Helper.getText(attributeBinding.inputValue);
                    if (!nameInput.trim().isEmpty()
                            && !valueInput.trim().isEmpty()) {
                        String newValue = nameInput + "=\"" + valueInput + "\"";
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", tabSelected);
                        map.put("value", newValue);
                        if (type.equals("create")) {
                            activityInjections.add(map);
                            SketchwareUtil.toast("Added");
                        } else if (type.equals("edit")) {
                            if (position != -1) {
                                activityInjections.remove(position);
                                activityInjections.add(position, map);
                            }
                            SketchwareUtil.toast("Saved");
                        }
                        dialog1.dismiss();
                        FileUtil.writeFile(path, new Gson().toJson(activityInjections));
                        adapter.submitList(filterInjections(tabSelected));
                    }
                });
        dialog.setNegativeButton(
                R.string.common_word_cancel, (dialog1, which) -> dialog1.dismiss());
        dialog.show();
        Objects.requireNonNull(dialog.create().getWindow())
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        attributeBinding.inputRes.requestFocus();
    }

    private List<HashMap<String, Object>> filterInjections(String widgetName) {
        List<HashMap<String, Object>> filteredList = new ArrayList<>();
        for (HashMap<String, Object> injection : activityInjections) {
            if (injection.containsKey("type")
                    && injection.get("type").toString().equals(widgetName)) {
                filteredList.add(injection);
            }
        }
        return filteredList;
    }
}
