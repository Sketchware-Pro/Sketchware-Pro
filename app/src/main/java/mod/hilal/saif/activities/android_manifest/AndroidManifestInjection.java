package mod.hilal.saif.activities.android_manifest;

import static mod.SketchwareUtil.getDip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ProgressMsgBoxBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import a.a.a.jC;
import a.a.a.yq;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;

@SuppressLint("SetTextI18n")
public class AndroidManifestInjection extends AppCompatActivity {

    private final ArrayList<HashMap<String, Object>> list_map = new ArrayList<>();
    private ListView act_list;
    private String sc_id;
    private String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_manifest_injection);

        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name")) {
            sc_id = getIntent().getStringExtra("sc_id");
            activityName = getIntent().getStringExtra("file_name").replaceAll(".java", "");
        }

        setupCustomToolbar();
        checkAttrs();
        setupViews();
        refreshList();
        checkAttrs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAttrs();
        refreshList();
    }

    private void checkAttrs() {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/attributes.json");
        if (FileUtil.isExistFile(path)) {
            ArrayList<HashMap<String, Object>> data = new Gson().fromJson(FileUtil.readFile(path),
                    Helper.TYPE_MAP_LIST);
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
            FileUtil.writeFile(path, new Gson().toJson(data));
        }
    }

    private void setupViews() {
        LinearLayout content = findViewById(R.id.content);
        LinearLayout cards = findViewById(R.id.cards);

        LibraryItemView application_card = new LibraryItemView(this);
        makeup(application_card, R.drawable.icons8_app_attrs, "Application", "Default properties for the app");
        cards.addView(application_card);
        application_card.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("file_name", activityName);
            intent.putExtra("type", "application");
            startActivity(intent);
        });

        {
            LibraryItemView permission_card = new LibraryItemView(this);
            makeup(permission_card, R.drawable.event_on_signin_complete_48dp, "Permissions", "Add custom Permissions to the app");
            cards.addView(permission_card);
            permission_card.setOnClickListener(_view -> {
                Intent inta = new Intent();
                inta.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                inta.putExtra("sc_id", sc_id);
                inta.putExtra("file_name", activityName);
                inta.putExtra("type", "permission");
                startActivity(inta);
            });
        }

        {
            LibraryItemView permission_card = new LibraryItemView(this);
            makeup(permission_card, R.drawable.recycling_48, "Launcher Activity", "Change the default Launcher Activity");
            cards.addView(permission_card);
            permission_card.setOnClickListener(v -> showLauncherActDialog(AndroidManifestInjector.getLauncherActivity(sc_id)));
        }

        LibraryItemView allAct_card = new LibraryItemView(this);
        makeup(allAct_card, R.drawable.icons8_all_activities_attrs, "All Activities", "Add attributes for all Activities");
        cards.addView(allAct_card);
        allAct_card.setOnClickListener(v -> {
            Intent inta = new Intent();
            inta.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
            inta.putExtra("sc_id", sc_id);
            inta.putExtra("file_name", activityName);
            inta.putExtra("type", "all");
            startActivity(inta);
        });

        LibraryItemView appCom_card = new LibraryItemView(this);
        makeup(appCom_card, R.drawable.icons8_app_components, "App Components", "Add extra components");
        cards.addView(appCom_card);
        appCom_card.setOnClickListener(v -> showAppComponentDialog());

        act_list = new ListView(this);
        act_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        act_list.setDividerHeight(0);
        content.addView(act_list);
    }

    private void showAppComponentDialog() {
        Intent intent = new Intent();
        if (ConfigActivity.isLegacyCeEnabled()) {
            intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
        } else {
            intent.setClass(getApplicationContext(), SrcCodeEditor.class);
        }

        String APP_COMPONENTS_PATH = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/app_components.txt");
        if (!FileUtil.isExistFile(APP_COMPONENTS_PATH)) FileUtil.writeFile(APP_COMPONENTS_PATH, "");
        intent.putExtra("content", APP_COMPONENTS_PATH);
        intent.putExtra("xml", "");
        intent.putExtra("disableHeader", "");
        intent.putExtra("title", "App Components");
        startActivity(intent);
    }

    private void showLauncherActDialog(String actnamr) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.custom_dialog_attribute, null);
        create.setView(inflate);
        create.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView btnSave = inflate.findViewById(R.id.dialog_btn_save);
        final TextView btnCancel = inflate.findViewById(R.id.dialog_btn_cancel);

        final EditText inputRes = inflate.findViewById(R.id.dialog_input_res);
        inputRes.setVisibility(View.GONE);
        final EditText inputAttr = inflate.findViewById(R.id.dialog_input_attr);
        inputAttr.setVisibility(View.GONE);
        final EditText inputValue = inflate.findViewById(R.id.dialog_input_value);
        final TextView textView = (TextView) ((ViewGroup) inputAttr.getParent()).getChildAt(0);
        textView.setText("Launcher Activity (e.g. main)");
        inputValue.setText(actnamr);
        inputValue.setHint("Activity name");

        btnSave.setOnClickListener(v -> {
            create.dismiss();
            AndroidManifestInjector.setLauncherActivity(sc_id, inputValue.getText().toString());
            SketchwareUtil.toast("Saved");
        });

        btnCancel.setOnClickListener(Helper.getDialogDismissListener(create));
        create.show();
    }

    // if you change method name, you need also change it in layout
    public void showAddActivityDialog(View view) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.custom_dialog_attribute, null);
        create.setView(inflate);
        create.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView btnSave = inflate.findViewById(R.id.dialog_btn_save);
        final TextView btnCancel = inflate.findViewById(R.id.dialog_btn_cancel);

        final EditText inputRes = inflate.findViewById(R.id.dialog_input_res);
        inputRes.setVisibility(View.GONE);
        final EditText inputAttr = inflate.findViewById(R.id.dialog_input_attr);
        inputAttr.setVisibility(View.GONE);
        final EditText inputValue = inflate.findViewById(R.id.dialog_input_value);
        final TextView textView = (TextView) ((ViewGroup) inputAttr.getParent()).getChildAt(0);
        textView.setText("Activity name");
        inputValue.setText(activityName);
        inputValue.setHint("Activity name");

        btnSave.setOnClickListener(v -> {
            addNewActivity(inputValue.getText().toString());

            create.dismiss();
            SketchwareUtil.toast("New Activity added");
        });

        btnCancel.setOnClickListener(Helper.getDialogDismissListener(create));

        create.show();
    }

    private void addNewActivity(String componentName) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:configChanges=\"orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout\"");

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


        FileUtil.writeFile(path, new Gson().toJson(data));
        refreshList();

    }

    private void a(View view, int i2, int i3) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i2, (float) i2, (float) i2 / 2, (float) i2 / 2, (float) i2, (float) i2, (float) i2 / 2, (float) i2 / 2});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins((int) getDip(4), (int) getDip(2), (int) getDip(4), (int) getDip(2));
        view.setElevation((float) i3);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void refreshList() {
        list_map.clear();
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                if (!temp.contains(Objects.requireNonNull(data.get(i).get("name")).toString())) {
                    if (!Objects.requireNonNull(data.get(i).get("name")).equals("_application_attrs") && !Objects.requireNonNull(data.get(i).get("name")).equals("_apply_for_all_activities") && !Objects.requireNonNull(data.get(i).get("name")).equals("_application_permissions")) {
                        temp.add((String) data.get(i).get("name"));
                    }
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("act_name", temp.get(i));
                list_map.add(map);
            }
            act_list.setAdapter(new ListAdapter(list_map));
            ((BaseAdapter) act_list.getAdapter()).notifyDataSetChanged();
        }
    }

    private void deleteActivity(int pos) {

        String activity_name = (String) list_map.get(pos).get("act_name");
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data;
        data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        for (int i = data.size() - 1; i > -1; i--) {
            String temp = (String) data.get(i).get("name");
            if (Objects.requireNonNull(temp).equals(activity_name)) {
                data.remove(i);
            }
        }
        FileUtil.writeFile(path, new Gson().toJson(data));
        refreshList();
        removeComponents(activity_name);
        SketchwareUtil.toast("activity removed");
    }

    private void removeComponents(String str) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/activities_components.json");
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = data.size() - 1; i > -1; i--) {
                String name = (String) data.get(i).get("name");
                if (Objects.requireNonNull(name).equals(str)) {
                    data.remove(i);
                    break;
                }
            }
            FileUtil.writeFile(path, new Gson().toJson(data));
        }
    }

    private void setupCustomToolbar() {
        ImageView _back = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(_back);
        ImageView _quickSource = findViewById(R.id.ig_toolbar_load_file);

        TextView _title = findViewById(R.id.tx_toolbar_title);
        _title.setText("AndroidManifest Manager");
        _back.setOnClickListener(Helper.getBackPressedClickListener(this));

        _quickSource.setImageResource(R.drawable.code_white_48);
        _quickSource.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(_quickSource);
        _quickSource.setOnClickListener((v1 -> showQuickManifestSourceDialog()));
    }

    private void showQuickManifestSourceDialog() {
        ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(getLayoutInflater());
        loadingDialogBinding.tvProgress.setText("Generating source code...");
        var loadingDialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Please wait")
                .setCancelable(false)
                .setView(loadingDialogBinding.getRoot())
                .create();
        loadingDialog.show();

        new Thread(() -> {
            final String source = new yq(getApplicationContext(), sc_id).getFileSrc("AndroidManifest.xml", jC.b(sc_id), jC.a(sc_id), jC.c(sc_id));

            var dialogBuilder = new MaterialAlertDialogBuilder(this)
                    .setTitle("AndroidManifest.xml")
                    .setPositiveButton("Dismiss", null);

            runOnUiThread(() -> {
                if (isFinishing()) return;
                loadingDialog.dismiss();

                CodeEditor editor = new CodeEditor(this);
                editor.setTypefaceText(Typeface.MONOSPACE);
                editor.setEditable(false);
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
                editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
                editor.setTextSize(14);
                editor.setText(!source.equals("") ? source : "Failed to generate source.");
                editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

                AlertDialog dialog = dialogBuilder.create();
                dialog.setView(editor,
                        (int) getDip(24),
                        (int) getDip(20),
                        (int) getDip(24),
                        (int) getDip(0));
                dialog.show();
            });
        }).start();

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
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_attribute, parent, false);
            }

            View linearLayout = convertView.findViewById(R.id.cus_attr_layout);
            TextView textView = convertView.findViewById(R.id.cus_attr_text);
            ImageView imageView = convertView.findViewById(R.id.cus_attr_btn);

            imageView.setVisibility(View.GONE);
            textView.setText((String) list_map.get(position).get("act_name"));
            linearLayout.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                intent.putExtra("sc_id", sc_id);
                intent.putExtra("file_name", (String) _data.get(position).get("act_name"));
                intent.putExtra("type", "activity");
                startActivity(intent);
            });
            linearLayout.setOnLongClickListener(v -> {
                new AlertDialog.Builder(AndroidManifestInjection.this)
                        .setTitle((String) _data.get(position).get("act_name"))
                        .setMessage("Delete all attributes related to this activity?")
                        .setPositiveButton(R.string.common_word_delete, (dialog, which) -> deleteActivity(position))
                        .setNegativeButton(R.string.common_word_cancel, null)
                        .show();
                return true;
            });

            return convertView;
        }
    }
}





