package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import dev.aldi.sayuti.block.ExtraBlockClassInfo;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class BlockSelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final File BLOCK_SELECTOR_MENUS_FILE = new File(Environment.getExternalStorageDirectory(), ".sketchware/resources/block/My Block/menu.json");

    private final ArrayList<String> display = new ArrayList<>();
    private LinearLayout add;
    private LinearLayout background;
    private LinearLayout bottom;
    private LinearLayout container;
    private ArrayList<String> contents = new ArrayList<>();
    private int current_item = 0;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    private LinearLayout delete;
    private LinearLayout edit;
    private boolean isNewGroup = false;
    private TextView label;
    private ListView listview1;
    private HashMap<String, Object> map = new HashMap<>();
    private EditText name;
    private ImageView options_menu;
    private Spinner spinner1;
    private EditText title;
    private EditText value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        background = findViewById(R.id.back);
        bottom = findViewById(R.id.bottom);
        ImageView back = findViewById(R.id.ig_toolbar_back);
        label = findViewById(R.id.label);
        container = findViewById(R.id.contai);
        spinner1 = findViewById(R.id.spinner);
        delete = findViewById(R.id.dele);
        edit = findViewById(R.id.edi);
        add = findViewById(R.id.add);
        name = findViewById(R.id.name);
        title = findViewById(R.id.title);
        MaterialButton cancel = findViewById(R.id.canc);
        MaterialButton save = findViewById(R.id.save);
        listview1 = findViewById(R.id.listv);
        value = findViewById(R.id.val);
        LinearLayout add_value = findViewById(R.id.add_val);
        fixbug();
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        add_value.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _showItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listview1.setOnItemLongClickListener((parent, view, position, id) -> {
            if (current_item != 0) {
                new AlertDialog.Builder(this).setTitle(contents.get(position))
                        .setMessage("Delete this item?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            contents.remove(position);
                            map.put("data", contents);
                            _save_item();
                            _showItem(current_item);
                        })
                        .setNegativeButton("Cancel", null)
                        .setNeutralButton("Copy item", (dialog, which) -> {
                            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", contents.get(position)));
                            SketchwareUtil.toast("Copied to clipboard");
                        })
                        .show();
            }
            return true;
        });

        options_menu = findViewById(R.id.ig_toolbar_load_file);
        options_menu.setVisibility(View.VISIBLE);
        options_menu.setImageResource(R.drawable.ic_more_vert_white_24dp);
        options_menu.setOnClickListener(v -> showOptionsMenu());
        applyRippleToView(back, delete, edit, add, cancel, save, add_value, options_menu);
    }

    private void save() {
        if (name.getText().toString().isEmpty()) {
            SketchwareUtil.toast("Enter a name");
        } else if (title.getText().toString().isEmpty()) {
            SketchwareUtil.toast("Enter a title");
        } else {
            if (isNewGroup) {
                map = new HashMap<>();
                map.put("name", name.getText().toString());
                map.put("title", title.getText().toString());
                map.put("data", new ArrayList<>());
                data.add(map);
                _save_item();
                _refresh_display();
                _fabVisibility(true);
                spinner1.setSelection(data.size() - 1);
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(200L);
                TransitionManager.beginDelayedTransition(background, autoTransition);
                container.setVisibility(View.GONE);
                Helper.setViewsVisibility(false, options_menu, add, edit, delete);
                spinner1.setEnabled(true);
                listview1.setEnabled(true);
                isNewGroup = false;
            } else {
                map.put("name", name.getText().toString());
                map.put("title", title.getText().toString());
                _save_item();
                _refresh_display();
                _fabVisibility(true);
                spinner1.setSelection(current_item);
                AutoTransition autoTransition2 = new AutoTransition();
                autoTransition2.setDuration(200L);
                TransitionManager.beginDelayedTransition(background, autoTransition2);
                container.setVisibility(View.GONE);
                Helper.setViewsVisibility(false, options_menu, add, edit, delete);
                spinner1.setEnabled(true);
                listview1.setEnabled(true);
            }
            label.setVisibility(View.GONE);
        }
    }

    private void showOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(this, options_menu);
        Menu menu = popupMenu.getMenu();
        menu.add("Import block selector menus");
        menu.add("Export current block selector menu");
        menu.add("Export all block selector menus");
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Export current block selector menu":
                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
                    arrayList.add(data.get(current_item));
                    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/block/export/menu/") + data.get(current_item).get("name") + ".json", new Gson().toJson(arrayList));
                    SketchwareUtil.toast("Successfully exported block menu to:\n/Internal storage/.sketchware/resources/block/export", Toast.LENGTH_LONG);
                    break;

                case "Import block selector menus":
                    openFileExplorerImport();
                    break;

                case "Export all block selector menus":
                    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/block/export/menu/") + "All_Menus.json", new Gson().toJson(data));
                    SketchwareUtil.toast("Successfully exported block menus to:\n/Internal storage/.sketchware/resources/block/export", Toast.LENGTH_LONG);
                    break;

                default:
                    return false;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onClick(View v) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200L);
        int id = v.getId();

        if (id == R.id.add) {
            _fabVisibility(false);
            TransitionManager.beginDelayedTransition(background, autoTransition);
            name.setText("");
            title.setText("");
            isNewGroup = true;
            Helper.setViewsVisibility(true, options_menu, add, edit);
            Helper.setViewsVisibility(false, label, delete, container);
            spinner1.setEnabled(false);
            listview1.setEnabled(false);
        } else if (id == R.id.add_val) {
            if (current_item != 0) {
                if (value.getText().toString().isEmpty()) {
                    SketchwareUtil.toast("Enter a value");
                } else {
                    contents.add(value.getText().toString());
                    map.put("data", contents);
                    _save_item();
                    _showItem(current_item);
                    value.setText("");
                }
            } else {
                SketchwareUtil.toastError("This menu can't be modified.");
            }
        } else if (id == R.id.dele) {
            if (current_item != 0) {
                new AlertDialog.Builder(this).setMessage("Remove this menu and its items?")
                        .setPositiveButton("Remove", (dialog, which) -> {
                            data.remove(spinner1.getSelectedItemPosition());
                            _save_item();
                            _refresh_display();
                            _fabVisibility(true);
                            isNewGroup = false;
                            spinner1.setSelection(0);
                        })
                        .setNegativeButton("Cancel", null)
                        .create().show();
            } else {
                SketchwareUtil.toastError("This menu can't be deleted.");
            }
        } else if (id == R.id.edi) {
            if (current_item != 0) {
                _fabVisibility(false);
                name.setText(map.get("name").toString());
                title.setText(map.get("title").toString());
                TransitionManager.beginDelayedTransition(background, autoTransition);
                container.setVisibility(View.VISIBLE);
                Helper.setViewsVisibility(true, options_menu, add, edit, delete);
                spinner1.setEnabled(false);
                listview1.setEnabled(false);
            } else {
                SketchwareUtil.toastError("This menu can't be modified.");
            }
        } else if (id == R.id.save) {
            save();
        } else if (id == R.id.canc) {
            _fabVisibility(true);
            TransitionManager.beginDelayedTransition(background, autoTransition);
            Helper.setViewsVisibility(false, options_menu, add, edit, delete);
            Helper.setViewsVisibility(true, container, label);
            spinner1.setEnabled(true);
            listview1.setEnabled(true);
            isNewGroup = false;
        }
    }

    private void applyRippleToView(View... views) {
        for (View view : views) {
            Helper.applyRippleToToolbarView(view);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ExtraBlockClassInfo.loadEBCI();
    }

    private void initializeLogic() {
        Helper.setViewsVisibility(true, container, label);
        _readFile();
        if (data.size() != 0) {
            _showItem(0);
        }
    }

    public void openFileExplorerImport() {
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = DialogConfigs.SINGLE_MODE;
        dialogProperties.selection_type = DialogConfigs.FILE_SELECT;
        File file = Environment.getExternalStorageDirectory();
        dialogProperties.root = file;
        dialogProperties.error_dir = file;
        dialogProperties.offset = file;
        dialogProperties.extensions = new String[]{"json"};
        FilePickerDialog filePickerDialog = new FilePickerDialog(this, dialogProperties);
        filePickerDialog.setTitle("Select a JSON file");
        filePickerDialog.setDialogSelectionListener(selections -> {
            String fileContent = FileUtil.readFile(selections[0]);
            if (fileContent.equals("")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else if (fileContent.equals("[]")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else {
                try {
                    _importMenu(new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST));
                } catch (Exception e) {
                    SketchwareUtil.toastError("Invalid JSON file");
                }
            }
        });
        filePickerDialog.show();
    }

    public void _importMenu(ArrayList<HashMap<String, Object>> menu) {
        data.addAll(menu);
        FileUtil.writeFile(BLOCK_SELECTOR_MENUS_FILE.getAbsolutePath(), new Gson().toJson(data));
        _readFile();
        if (data.size() != 0) {
            _showItem(0);
        }
        SketchwareUtil.toast("Successfully imported menu");
    }

    private void _readFile() {
        data.clear();
        parser:
        {
            if (BLOCK_SELECTOR_MENUS_FILE.exists()) {
                try {
                    data = new Gson().fromJson(FileUtil.readFile(BLOCK_SELECTOR_MENUS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);

                    if (data != null) {
                        break parser;
                    }
                    // fall-through to shared handler
                } catch (JsonParseException e) {
                    // fall-through to shared handler
                }

                SketchwareUtil.showFailedToParseJsonDialog(this, BLOCK_SELECTOR_MENUS_FILE,
                        "Block selector menus", v -> _readFile());
                data = new ArrayList<>();
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if ("typeview".equals(data.get(i).get("name"))) {
                _refresh_display();
                return;
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("View");
        arrayList.add("ViewGroup");
        arrayList.add("LinearLayout");
        arrayList.add("RelativeLayout");
        arrayList.add("ScrollView");
        arrayList.add("HorizontalScrollView");
        arrayList.add("TextView");
        arrayList.add("EditText");
        arrayList.add("Button");
        arrayList.add("RadioButton");
        arrayList.add("CheckBox");
        arrayList.add("Switch");
        arrayList.add("ImageView");
        arrayList.add("SeekBar");
        arrayList.add("ListView");
        arrayList.add("Spinner");
        arrayList.add("WebView");
        arrayList.add("MapView");
        arrayList.add("ProgressBar");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "typeview");
        map.put("title", "select type :");
        map.put("data", arrayList);
        data.add(0, map);
        _refresh_display();
    }

    private void fixbug() {
        ViewGroup viewGroup = (ViewGroup) name.getParent().getParent().getParent();
        viewGroup.removeView((ViewGroup) name.getParent().getParent());
        viewGroup.removeView((ViewGroup) title.getParent().getParent());
        ((ViewGroup) name.getParent()).removeView(name);
        ((ViewGroup) title.getParent()).removeView(title);
        viewGroup.addView(title, 0);
        title.setHint("");
        TextView textView = new TextView(this);
        textView.setTextColor(855638016);
        textView.setPadding((int) SketchwareUtil.getDip(8),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(8),
                (int) SketchwareUtil.getDip(0));
        textView.setText("Title");
        viewGroup.addView(textView, 0);
        viewGroup.addView(name, 0);
        name.setHint("");
        TextView textView2 = new TextView(this);
        textView2.setTextColor(855638016);
        textView2.setPadding((int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(0));
        textView2.setText("Name");
        viewGroup.addView(textView2, 0);
    }

    private void _showItem(int d) {
        current_item = d;
        map = data.get(d);
        name.setText(map.get("name").toString());
        title.setText(map.get("title").toString());
        contents = (ArrayList<String>) map.get("data");
        Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
        listview1.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, contents));
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        listview1.onRestoreInstanceState(onSaveInstanceState);
    }

    private void _fabVisibility(boolean visible) {
        if (visible) {
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(200L);
            TransitionManager.beginDelayedTransition(background, autoTransition);
            bottom.setVisibility(View.VISIBLE);
        } else {
            AutoTransition autoTransition2 = new AutoTransition();
            autoTransition2.setDuration(200L);
            TransitionManager.beginDelayedTransition(background, autoTransition2);
            bottom.setVisibility(View.GONE);
        }
    }

    private void _save_item() {
        FileUtil.writeFile(BLOCK_SELECTOR_MENUS_FILE.getAbsolutePath(), new Gson().toJson(data));
    }

    private void _refresh_display() {
        display.clear();
        for (int i = 0; i < data.size(); i++) {
            display.add(data.get(i).get("name").toString());
        }
        spinner1.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, display));
        ((BaseAdapter) spinner1.getAdapter()).notifyDataSetChanged();
    }
}
