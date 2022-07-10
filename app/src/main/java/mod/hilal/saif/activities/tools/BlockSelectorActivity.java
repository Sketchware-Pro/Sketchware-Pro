package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import dev.aldi.sayuti.block.ExtraBlockClassInfo;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class BlockSelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<String> display = new ArrayList<>();
    private LinearLayout add;
    private LinearLayout add_value;
    private ImageView back_icon;
    private LinearLayout background;
    private LinearLayout bottom;
    private MaterialButton cancel;
    private CardView card;
    private CardView cardview1;
    private LinearLayout container;
    private ArrayList<String> contents = new ArrayList<>();
    private int current_item = 0;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    private LinearLayout delete;
    private AlertDialog.Builder dialog_warn;
    private LinearLayout edit;
    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview5;
    private boolean isNewGroup = false;
    private TextView label;
    private LinearLayout linear11;
    private TextInputLayout linear12;
    private LinearLayout linear13;
    private LinearLayout linear15;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private ListView listview1;
    private HashMap<String, Object> map = new HashMap<>();
    private EditText name;
    private TextInputLayout name_lay;
    private ImageView options_menu;
    private TextView page_title;
    private MaterialButton save;
    private Spinner spinner1;
    private EditText title;
    private LinearLayout toolbar;
    private EditText value;
    private AlertDialog.Builder warn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427817);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        background = findViewById(2131232594);
        toolbar = findViewById(2131231847);
        card = findViewById(2131232597);
        linear13 = findViewById(2131232598);
        bottom = findViewById(2131230798);
        back_icon = findViewById(2131232600);
        page_title = findViewById(2131232596);
        label = findViewById(2131232601);
        container = findViewById(2131232602);
        spinner1 = findViewById(2131232603);
        delete = findViewById(2131232604);
        edit = findViewById(2131232605);
        add = findViewById(2131230754);
        name = findViewById(2131231561);
        title = findViewById(2131231837);
        cancel = findViewById(2131232610);
        save = findViewById(2131232528);
        listview1 = findViewById(2131232612);
        cardview1 = findViewById(2131232562);
        value = findViewById(2131232614);
        add_value = findViewById(2131232615);
        dialog_warn = new AlertDialog.Builder(this);
        warn = new AlertDialog.Builder(this);
        //fixbug();
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
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
            if (current_item == 0) {
                return true;
            }
            dialog_warn.setTitle(contents.get(position))
                    .setMessage("Delete this item?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        contents.remove(position);
                        map.put("data", contents);
                        _save_item();
                        _showItem(current_item);
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Copy item", (dialog, which) -> {
                        BlockSelectorActivity blockSelectorActivity = BlockSelectorActivity.this;
                        getApplicationContext();
                        ((ClipboardManager) blockSelectorActivity.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", contents.get(position)));
                        SketchwareUtil.toast("Copied to clipboard");
                    })
                    .create().show();
            return true;
        });

        options_menu = new ImageView(this);
        options_menu.setImageResource(2131165791);
        options_menu.setPadding((int) SketchwareUtil.getDip(9),
                (int) SketchwareUtil.getDip(9),
                (int) SketchwareUtil.getDip(9),
                (int) SketchwareUtil.getDip(9));
        options_menu.setLayoutParams(new LinearLayout.LayoutParams((int) SketchwareUtil.getDip(40), (int) SketchwareUtil.getDip(40), 0.0f));
        options_menu.setScaleType(ImageView.ScaleType.FIT_XY);
        options_menu.setOnClickListener(v -> showOptionsMenu());
        toolbar.addView(options_menu);
        applyRippleToView(back_icon, delete, edit, add, cancel, save, add_value, options_menu);
    }

    private void save() {
        if (isNewGroup) {
            if (name.getText().toString().isEmpty()) {
                SketchwareUtil.toast("Enter a name");
                return;
            } else if (title.getText().toString().isEmpty()) {
                SketchwareUtil.toast("Enter a title");
                return;
            }
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
            if (name.getText().toString().isEmpty()) {
                SketchwareUtil.toast("Enter a name");
                return;
            } else if (title.getText().toString().isEmpty()) {
                SketchwareUtil.toast("Enter a title");
                return;
            }
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

    private void showOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(BlockSelectorActivity.this, options_menu);
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
        switch (v.getId()) {
            case 2131230754://add
                _fabVisibility(false);
                TransitionManager.beginDelayedTransition(background, autoTransition);
                name.setText("");
                title.setText("");
                isNewGroup = true;
                Helper.setViewsVisibility(true, options_menu, add, edit);
                Helper.setViewsVisibility(false, label, delete, container);
                spinner1.setEnabled(false);
                listview1.setEnabled(false);
                break;

            case 2131232615://add_value
                if (current_item != 0) {
                    if (value.getText().toString().isEmpty()) {
                        SketchwareUtil.toast("Enter a value");
                        return;
                    }
                    contents.add(value.getText().toString());
                    map.put("data", contents);
                    _save_item();
                    _showItem(current_item);
                    value.setText("");
                    return;
                }
                SketchwareUtil.toastError("This menu can't be modified.");
                break;

            case 2131232604://delete
                if (current_item != 0) {
                    warn.setMessage("Remove this menu and its items?")
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
                    return;
                }
                SketchwareUtil.toastError("This menu can't be deleted.");
                break;

            case 2131232605://edit
                if (current_item != 0) {
                    _fabVisibility(false);
                    name.setText(map.get("name").toString());
                    title.setText(map.get("title").toString());
                    TransitionManager.beginDelayedTransition(background, autoTransition);
                    container.setVisibility(View.VISIBLE);
                    Helper.setViewsVisibility(true, options_menu, add, edit, delete);
                    spinner1.setEnabled(false);
                    listview1.setEnabled(false);
                    return;
                }
                SketchwareUtil.toastError("This menu can't be modified.");
                break;

            case 2131232528://save
                save();
                break;

            case 2131232610://cancel
                _fabVisibility(true);
                TransitionManager.beginDelayedTransition(background, autoTransition);
                Helper.setViewsVisibility(false, options_menu, add, edit, delete);
                Helper.setViewsVisibility(true, container, label);
                spinner1.setEnabled(true);
                listview1.setEnabled(true);
                isNewGroup = false;
                break;
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
        dialogProperties.selection_mode = 0;
        dialogProperties.selection_type = 0;
        File file = new File(FileUtil.getExternalStorageDir());
        dialogProperties.root = file;
        dialogProperties.error_dir = file;
        dialogProperties.offset = file;
        dialogProperties.extensions = new String[]{"json"};
        FilePickerDialog filePickerDialog = new FilePickerDialog(this, dialogProperties);
        filePickerDialog.setTitle("Select a JSON file");
        filePickerDialog.setDialogSelectionListener(selections -> {
            if (FileUtil.readFile(selections[0]).equals("")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else if (FileUtil.readFile(selections[0]).equals("[]")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else {
                try {
                    _importMenu(new Gson().fromJson(FileUtil.readFile(selections[0]), Helper.TYPE_MAP_LIST));
                } catch (Exception e) {
                    SketchwareUtil.toastError("Invalid JSON file");
                }
            }
        });
        filePickerDialog.show();
    }

    public void _importMenu(ArrayList<HashMap<String, Object>> menu) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/block/My Block/menu.json");
        data.addAll(menu);
        FileUtil.writeFile(concat, new Gson().toJson(data));
        _readFile();
        if (data.size() != 0) {
            _showItem(0);
        }
        SketchwareUtil.toast("Successfully imported menu");
    }

    private void _readFile() {
        data.clear();
        if (new File(FileUtil.getExternalStorageDir(), ".sketchware/resources/block/My Block/menu.json").exists()) {
            data = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/block/My Block/menu.json")), Helper.TYPE_MAP_LIST);
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get("name").toString().equals("typeview")) {
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
        map = new HashMap<>();
        map.put("name", "typeview");
        map.put("title", "select type :");
        map.put("data", arrayList);
        data.add(0, map);
        map = new HashMap<>();
        _save_item();
        _refresh_display();
    }

    private void fixbug() {
        //TODO: Fix this method to not throw NPEs (??? did I change anything?)
        ViewGroup viewGroup = (ViewGroup) name.getParent().getParent().getParent();
        ((ViewGroup) name.getParent()).removeView(name);
        ((ViewGroup) title.getParent()).removeView(title);
        viewGroup.removeView((ViewGroup) name.getParent().getParent());
        viewGroup.removeView((ViewGroup) title.getParent().getParent());
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
        listview1.setAdapter(new ArrayAdapter<>(getBaseContext(), 17367043, contents));
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        listview1.onRestoreInstanceState(onSaveInstanceState);
    }

    private void _fabVisibility(boolean visible) {
        if (visible) {
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(200L);
            TransitionManager.beginDelayedTransition(background, autoTransition);
            bottom.setVisibility(View.VISIBLE);
            return;
        }
        AutoTransition autoTransition2 = new AutoTransition();
        autoTransition2.setDuration(200L);
        TransitionManager.beginDelayedTransition(background, autoTransition2);
        bottom.setVisibility(View.GONE);
    }

    private void _save_item() {
        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/block/My Block/menu.json"), new Gson().toJson(data));
    }

    private void _refresh_display() {
        display.clear();
        for (int i = 0; i < data.size(); i++) {
            display.add(data.get(i).get("name").toString());
        }
        spinner1.setAdapter(new ArrayAdapter<>(getBaseContext(), 17367049, display));
        ((BaseAdapter) spinner1.getAdapter()).notifyDataSetChanged();
    }
}
