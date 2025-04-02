package mod.hilal.saif.activities.android_manifest;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import a.a.a.jC;
import a.a.a.wB;
import a.a.a.yq;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.remaker.view.CustomAttributeView;
import pro.sketchware.R;
import pro.sketchware.activities.editor.view.CodeViewerActivity;
import pro.sketchware.databinding.AndroidManifestInjectionBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

@SuppressLint("SetTextI18n")
public class AndroidManifestInjection extends BaseAppCompatActivity {

    private final ArrayList<HashMap<String, Object>> activitiesListMap = new ArrayList<>();
    private AndroidManifestInjectionBinding binding;
    private String sc_id;
    private String currentActivityName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AndroidManifestInjectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name")) {
            sc_id = getIntent().getStringExtra("sc_id");
            currentActivityName = getIntent().getStringExtra("file_name").replaceAll(".java", "");
        }

        setupCustomToolbar();
        checkAttrs();
        setupOptions();
        refreshList();
        checkAttrs();

        binding.addActivity.setOnClickListener(v -> showAddActivityDialog());
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAttrs();
        refreshList();
    }

    private void checkAttrs() {
        final String path =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/attributes.json");
        if (FileUtil.isExistFile(path)) {
            ArrayList<HashMap<String, Object>> data =
                    getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                String str = (String) data.get(i).get("name");
                if (Objects.requireNonNull(str).equals("_application_attrs")) {
                    String str2 = (String) data.get(i).get("value");
                    if (Objects.requireNonNull(str2).contains("android:theme")) {
                        return;
                    }
                }
            }
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", "_application_attrs");
            _item.put("value", "android:theme=\"@style/AppTheme\"");
            data.add(_item);
            FileUtil.writeFile(path, getGson().toJson(data));
        }
    }

    private void setupOptions() {
        final List<LibraryItemView> options = new ArrayList<>();

        options.add(
                createOption(
                        "Application",
                        "Default properties for the app",
                        R.drawable.ic_mtrl_settings_applications,
                        v -> {
                            Intent intent = new Intent();
                            intent.setClass(
                                    getApplicationContext(), AndroidManifestInjectionDetails.class);
                            intent.putExtra("sc_id", sc_id);
                            intent.putExtra("file_name", currentActivityName);
                            intent.putExtra("type", "application");
                            startActivity(intent);
                        }));
        options.add(
                createOption(
                        "Permissions",
                        "Add custom Permissions to the app",
                        R.drawable.ic_mtrl_shield_check,
                        v -> {
                            Intent intent = new Intent();
                            intent.setClass(
                                    getApplicationContext(), AndroidManifestInjectionDetails.class);
                            intent.putExtra("sc_id", sc_id);
                            intent.putExtra("file_name", currentActivityName);
                            intent.putExtra("type", "permission");
                            startActivity(intent);
                        }));
        options.add(
                createOption(
                        "Launcher Activity",
                        "Change the default Launcher Activity",
                        R.drawable.ic_mtrl_login,
                        v ->
                                showLauncherActDialog(
                                        AndroidManifestInjector.getLauncherActivity(sc_id))));
        options.add(
                createOption(
                        "All Activities",
                        "Add attributes for all Activities",
                        R.drawable.ic_mtrl_frame_source,
                        v -> {
                            Intent intent = new Intent();
                            intent.setClass(
                                    getApplicationContext(), AndroidManifestInjectionDetails.class);
                            intent.putExtra("sc_id", sc_id);
                            intent.putExtra("file_name", currentActivityName);
                            intent.putExtra("type", "all");
                            startActivity(intent);
                        }));
        options.add(
                createOption(
                        "App Components",
                        "Add extra components",
                        R.drawable.ic_mtrl_component,
                        v -> showAppComponentDialog()));

        options.forEach(binding.cards::addView);
    }

    private final LibraryItemView createOption(
            final String title,
            final String description,
            final int icon,
            View.OnClickListener onClick) {
        var card = new LibraryItemView(this);
        makeup(card, icon, title, description);
        card.setOnClickListener(onClick);
        return card;
    }

    private void showAppComponentDialog() {
        final Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SrcCodeEditor.class);

        final String APP_COMPONENTS_PATH =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/app_components.txt");
        if (!FileUtil.isExistFile(APP_COMPONENTS_PATH)) FileUtil.writeFile(APP_COMPONENTS_PATH, "");
        intent.putExtra("content", APP_COMPONENTS_PATH);
        intent.putExtra("xml", "");
        intent.putExtra("disableHeader", "");
        intent.putExtra("title", "App Components");
        startActivity(intent);
    }

    private void showLauncherActDialog(String actnamr) {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.ic_mtrl_lifecycle);
        dialog.setTitle(Helper.getResString(R.string.change_launcher_activity_dialog_title));
        View view = wB.a(this, R.layout.dialog_add_custom_activity);

        final TextInputEditText activity_name_input = view.findViewById(R.id.activity_name_input);

        activity_name_input.setText(actnamr);

        dialog.setView(view);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (v, which) -> {
            if (!Helper.getText(activity_name_input).trim().isEmpty()) {
                AndroidManifestInjector.setLauncherActivity(
                        sc_id, Helper.getText(activity_name_input));
                SketchwareUtil.toast("Saved");
                v.dismiss();
            } else {
                activity_name_input.setError("Enter activity name");
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    public void showAddActivityDialog() {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.ic_mtrl_add);
        dialog.setTitle(Helper.getResString(R.string.common_word_add_activtiy));
        View inflate = wB.a(this, R.layout.dialog_add_custom_activity);

        final TextInputEditText activity_name_input =
                inflate.findViewById(R.id.activity_name_input);

        activity_name_input.setText(currentActivityName);

        dialog.setView(inflate);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (v, which) -> {
            if (!Helper.getText(activity_name_input).trim().isEmpty()) {
                addNewActivity(Helper.getText(activity_name_input));
                SketchwareUtil.toast("New Activity added");
                v.dismiss();
            } else {
                activity_name_input.setError("Enter activity name");
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void addNewActivity(String componentName) {
        String path =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            data = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put(
                    "value",
                    "android:configChanges=\"orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:hardwareAccelerated=\"true\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:supportsPictureInPicture=\"true\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:screenOrientation=\"portrait\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:theme=\"@style/AppTheme\"");

            data.add(_item);
        }

        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:windowSoftInputMode=\"stateHidden\"");

            data.add(_item);
        }

        FileUtil.writeFile(path, getGson().toJson(data));
        refreshList();
    }

    private void refreshList() {
        activitiesListMap.clear();
        String path =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/attributes.json");
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(path)) {
            data = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                if (!temp.contains(Objects.requireNonNull(data.get(i).get("name")).toString())) {
                    if (!Objects.requireNonNull(data.get(i).get("name"))
                            .equals("_application_attrs")
                            && !Objects.requireNonNull(data.get(i).get("name"))
                            .equals("_apply_for_all_activities")
                            && !Objects.requireNonNull(data.get(i).get("name"))
                            .equals("_application_permissions")) {
                        temp.add((String) data.get(i).get("name"));
                    }
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("act_name", temp.get(i));
                activitiesListMap.add(map);
            }
            binding.activitiesListView.setAdapter(new ListAdapter(activitiesListMap));
            ((BaseAdapter) binding.activitiesListView.getAdapter()).notifyDataSetChanged();
        }
    }

    private void deleteActivity(int pos) {
        String activity_name = (String) activitiesListMap.get(pos).get("act_name");
        String path =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data;
        data = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        for (int i = data.size() - 1; i > -1; i--) {
            String temp = (String) data.get(i).get("name");
            if (Objects.requireNonNull(temp).equals(activity_name)) {
                data.remove(i);
            }
        }
        FileUtil.writeFile(path, getGson().toJson(data));
        refreshList();
        removeComponents(activity_name);
        SketchwareUtil.toast("Activity removed");
    }

    private void removeComponents(String str) {
        String path =
                FileUtil.getExternalStorageDir()
                        .concat("/.sketchware/data/")
                        .concat(sc_id)
                        .concat("/Injection/androidmanifest/activities_components.json");
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(path)) {
            data = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = data.size() - 1; i > -1; i--) {
                String name = (String) data.get(i).get("name");
                if (Objects.requireNonNull(name).equals(str)) {
                    data.remove(i);
                    break;
                }
            }
            FileUtil.writeFile(path, getGson().toJson(data));
        }
    }

    private void setupCustomToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("AndroidManifest Manager");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Show Manifest Source")
                .setIcon(getDrawable(R.drawable.ic_mtrl_code))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        if (title.equals("Show Manifest Source")) {
            showQuickManifestSourceDialog();
        } else {
            return false;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showQuickManifestSourceDialog() {
        k();
        new Thread(
                () -> {
                    final String source =
                            new yq(getApplicationContext(), sc_id)
                                    .getFileSrc(
                                            "AndroidManifest.xml",
                                            jC.b(sc_id),
                                            jC.a(sc_id),
                                            jC.c(sc_id));

                    runOnUiThread(
                            () -> {
                                if (isFinishing()) return;
                                h();
                                var intent = new Intent(this, CodeViewerActivity.class);
                                intent.putExtra(
                                        "code",
                                        !source.isEmpty()
                                                ? source
                                                : "Failed to generate source.");
                                intent.putExtra("sc_id", sc_id);
                                intent.putExtra("scheme", CodeViewerActivity.SCHEME_XML);
                                startActivity(intent);
                            });
                })
                .start();
    }

    private void makeup(LibraryItemView parent, int icon, String title, String description) {
        parent.enabled.setVisibility(View.GONE);
        parent.icon.setImageResource(icon);
        parent.title.setText(title);
        parent.description.setText(description);
    }

    private class ListAdapter extends BaseAdapter {
        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> data) {
            _data = data;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CustomAttributeView attributeView = new CustomAttributeView(parent.getContext());

            attributeView.getImageView().setVisibility(View.GONE);
            attributeView
                    .getTextView()
                    .setText((String) activitiesListMap.get(position).get("act_name"));
            attributeView.setOnClickListener(
                    v -> {
                        Intent intent = new Intent();
                        intent.setClass(
                                getApplicationContext(), AndroidManifestInjectionDetails.class);
                        intent.putExtra("sc_id", sc_id);
                        intent.putExtra("file_name", (String) _data.get(position).get("act_name"));
                        intent.putExtra("type", "activity");
                        startActivity(intent);
                    });
            attributeView.setOnLongClickListener(
                    v -> {
                        {
                            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(AndroidManifestInjection.this);
                            dialog.setIcon(R.drawable.icon_delete);
                            dialog.setTitle(Helper.getResString(
                                    R.string.delete_custom_activity_dialog_title));
                            dialog.setMessage(Helper.getResString(
                                            R.string.delete_custom_activity_dialog_message)
                                    .replace(
                                            "%1$s",
                                            (String) _data.get(position).get("act_name")));

                            dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v1, which) -> {
                                deleteActivity(position);
                                v1.dismiss();
                            });
                            dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
                            dialog.show();
                        }
                        return true;
                    });

            return attributeView;
        }
    }
}
