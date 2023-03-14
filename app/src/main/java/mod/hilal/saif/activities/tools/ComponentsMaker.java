package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentsMaker extends Activity {

    private static final String PATH_COMPONENTS_FILE = ".sketchware/data/system/component.json";
    private static final String PATH_COMPONENT_EXPORT = ".sketchware/data/system/export/components/";
    private static final String PATH_EXPORT_ALL_COMPONENTS_FILE_NAME = "All_Components.json";
    private static final String COMPONENTS_FILE_PATH = new File(FileUtil.getExternalStorageDir(), PATH_COMPONENTS_FILE).getAbsolutePath();
    private static final String COMPONENT_EXPORT_PATH = new File(FileUtil.getExternalStorageDir(), PATH_COMPONENT_EXPORT).getAbsolutePath();

    private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_attribute);
        setToolbar();
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ComponentsHandler.refreshCachedCustomComponents();
    }

    private void setupViews() {
        FloatingActionButton fab = findViewById(R.id.add_attr_fab);
        fab.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), ComponentsMakerCreator.class)));
        listView = findViewById(R.id.add_attr_listview);
        refreshList();
    }

    private void a(View view, int i, int i2, boolean z) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f, (float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation((float) i2);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void refreshList() {
        File componentsFile = new File(COMPONENTS_FILE_PATH);
        if (componentsFile.exists()) {
            listMap = new Gson().fromJson(FileUtil.readFile(componentsFile.getAbsolutePath()), Helper.TYPE_MAP_LIST);
        }
        Parcelable savedState = listView.onSaveInstanceState();
        listView.setAdapter(new ListAdapter(listMap));
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        listView.onRestoreInstanceState(savedState);
    }

    private void deleteItem(int position) {
        listMap.remove(position);
        FileUtil.writeFile(COMPONENTS_FILE_PATH, new Gson().toJson(listMap));
        refreshList();
    }

    private void openFileExplorerImport() {
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = 0;
        dialogProperties.selection_type = 0;
        File externalStorageDir = new File(FileUtil.getExternalStorageDir());
        dialogProperties.root = externalStorageDir;
        dialogProperties.error_dir = externalStorageDir;
        dialogProperties.offset = externalStorageDir;
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
                    ArrayList<HashMap<String, Object>> components = new Gson().fromJson(FileUtil.readFile(selections[0]), Helper.TYPE_MAP_LIST);
                    _importComponents(components);
                } catch (Exception e) {
                    SketchwareUtil.toastError("Invalid JSON file");
                }
            }
        });
        filePickerDialog.show();
    }

    private void _importComponents(ArrayList<HashMap<String, Object>> arrayList) {
        listMap.addAll(arrayList);
        FileUtil.writeFile(COMPONENTS_FILE_PATH, new Gson().toJson(listMap));
        refreshList();
        SketchwareUtil.toast("Successfully imported components");
    }

    private void exportComponent(int position) {
        String fileName = listMap.get(position).get("name").toString() + ".json";
        ArrayList<HashMap<String, Object>> componentList = new ArrayList<>();
        componentList.add(listMap.get(position));
        FileUtil.writeFile(new File(COMPONENT_EXPORT_PATH, fileName).getAbsolutePath(), new Gson().toJson(componentList));
        SketchwareUtil.toast("Successfully exported component to:\n/Internal storage/" + PATH_COMPONENT_EXPORT + fileName, Toast.LENGTH_LONG);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Component manager");
        final ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);
        final ImageView more_icon = findViewById(R.id.ig_toolbar_load_file);
        more_icon.setVisibility(View.VISIBLE);
        more_icon.setImageResource(R.drawable.ic_more_vert_white_24dp);
        more_icon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, more_icon);
            Menu menu = popupMenu.getMenu();
            menu.add("Import components");
            menu.add("Export components");
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getTitle().toString()) {
                    case "Export components":
                        FileUtil.writeFile(new File(COMPONENT_EXPORT_PATH, PATH_EXPORT_ALL_COMPONENTS_FILE_NAME).getAbsolutePath(), new Gson().toJson(listMap));
                        SketchwareUtil.toast("Successfully exported components to:\n/Internal storage/" +
                                        COMPONENT_EXPORT_PATH + PATH_EXPORT_ALL_COMPONENTS_FILE_NAME,
                                Toast.LENGTH_LONG);
                        break;

                    case "Import components":
                        openFileExplorerImport();
                        break;

                    default:
                        return false;
                }
                return true;
            });
            popupMenu.show();
        });
        Helper.applyRippleToToolbarView(more_icon);
    }

    private class ListAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int i) {
            return _data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_pro, parent, false);
            }
            LinearLayout root = convertView.findViewById(R.id.custom_view_pro_background);
            a(root, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView icon = convertView.findViewById(R.id.custom_view_pro_img);
            icon.setImageResource(Integer.parseInt(_data.get(position).get("icon").toString()));
            ((LinearLayout) icon.getParent()).setGravity(17);
            ((TextView) convertView.findViewById(R.id.custom_view_pro_title)).setText(_data.get(position).get("name").toString());
            ((TextView) convertView.findViewById(R.id.custom_view_pro_subtitle)).setText(_data.get(position).get("description").toString());
            root.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ComponentsMakerCreator.class);
                intent.putExtra("pos", String.valueOf(position));
                intent.putExtra("name", _data.get(position).get("name").toString());
                startActivity(intent);
            });
            root.setOnLongClickListener(v -> {
                new AlertDialog.Builder(ComponentsMaker.this)
                        .setTitle(_data.get(position).get("name").toString())
                        .setItems(new String[]{"Export", "Delete"}, (dialog, which) -> {
                            if (which == 0) {
                                exportComponent(position);
                            } else if (which == 1) {
                                deleteItem(position);
                            }
                        })
                        .show();
                return true;
            });
            return convertView;
        }
    }
}