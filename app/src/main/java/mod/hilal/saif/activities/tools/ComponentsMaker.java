package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ComponentsMaker extends Activity {

    private static final String PATH_COMPONENTS_FILE = ".sketchware/data/system/component.json";
    private static final String PATH_COMPONENT_EXPORT = ".sketchware/data/system/export/components/";
    private static final String PATH_EXPORT_ALL_COMPONENTS_FILE_NAME = "All_Components.json";
    private static final String COMPONENTS_FILE_PATH = new File(FileUtil.getExternalStorageDir(), PATH_COMPONENTS_FILE).getAbsolutePath();
    private static final String COMPONENT_EXPORT_PATH = new File(FileUtil.getExternalStorageDir(), PATH_COMPONENT_EXPORT).getAbsolutePath();

    private ViewGroup base;
    private AlertDialog.Builder dia;
    private FloatingActionButton fab;
    private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427795);
        setToolbar();
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void setupViews() {
        this.fab = findViewById(2131232439);
        this.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ComponentsMakerCreator.class);
                startActivity(intent);
            }
        });
        this.listView = findViewById(2131232438);
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
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                if (FileUtil.readFile(selections[0]).equals("")) {
                    SketchwareUtil.toastError("The selected file is empty!");
                } else if (FileUtil.readFile(selections[0]).equals("[]")) {
                    SketchwareUtil.toastError("The selected file is empty!");
                } else {
                    try {
                        _importComponents((ArrayList<HashMap<String, Object>>) new Gson().fromJson(FileUtil.readFile(selections[0]), Helper.TYPE_MAP_LIST));
                    } catch (Exception e) {
                        SketchwareUtil.toastError("Invalid JSON file");
                    }
                }
            }
        });
        filePickerDialog.show();
    }

    private void _importComponents(ArrayList<HashMap<String, Object>> arrayList) {
        this.listMap.addAll(arrayList);
        FileUtil.writeFile(COMPONENTS_FILE_PATH, new Gson().toJson(this.listMap));
        refreshList();
        SketchwareUtil.toast("Successfully imported components");
    }

    private void exportComponent(int position) {
        String fileName = listMap.get(position).get("name").toString() + ".json";
        ArrayList<HashMap<String, Object>> componentList = new ArrayList<>();
        componentList.add(this.listMap.get(position));
        FileUtil.writeFile(new File(COMPONENT_EXPORT_PATH, fileName).getAbsolutePath(), new Gson().toJson(componentList));
        SketchwareUtil.toast("Successfully exported component to:\n/Internal storage/" + PATH_COMPONENT_EXPORT + fileName);
    }

    private void makeup(View view, int i, String str, String str2) {
        View inflate = getLayoutInflater().inflate(2131427537, null);
        LinearLayout linearLayout = inflate.findViewById(2131230931);
        ImageView imageView = inflate.findViewById(2131231428);
        ((TextView) inflate.findViewById(2131231965)).setVisibility(View.GONE);
        imageView.setImageResource(i);
        ((LinearLayout) imageView.getParent()).setGravity(17);
        ((TextView) inflate.findViewById(2131231430)).setText(str);
        ((TextView) inflate.findViewById(2131231427)).setText(str2);
        ((ViewGroup) view).addView(inflate);
    }

    private TextView getTextClone(String str, float f, String str2) {
        TextView textView = getLayoutInflater().inflate(2131427796, null).findViewById(2131232441);
        textView.setText(str);
        textView.setTextColor(Color.parseColor(str2));
        textView.setTextSize(f);
        ((LinearLayout) textView.getParent()).removeView(textView);
        return textView;
    }

    private CardView newCard(int i, int i2, float f) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2, f);
        layoutParams.setMargins((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2));
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding((int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2));
        cardView.setCardBackgroundColor(-1);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    private LinearLayout newLayout(int i, int i2, float f) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2, f));
        linearLayout.setPadding((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
    }

    private TextView newText(String str, float f, int i, int i2, int i3, float f2) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(i2, i3, f2));
        textView.setPadding((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4));
        textView.setTextColor(i);
        textView.setText(str);
        textView.setTextSize(f);
        return textView;
    }

    private void setToolbar() {
        ((TextView) findViewById(2131232458)).setText("Component manager");
        final ImageView back_icon = findViewById(2131232457);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        final ImageView more_icon = findViewById(2131232459);
        more_icon.setVisibility(View.VISIBLE);
        more_icon.setImageResource(2131165791);
        more_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ComponentsMaker.this, more_icon);
                Menu menu = popupMenu.getMenu();
                menu.add("Import components");
                menu.add("Export components");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Export components":
                                FileUtil.writeFile(new File(COMPONENT_EXPORT_PATH, PATH_EXPORT_ALL_COMPONENTS_FILE_NAME).getAbsolutePath(), new Gson().toJson(listMap));
                                SketchwareUtil.toast("Successfully exported components to:\n/Internal storage/" + COMPONENT_EXPORT_PATH + PATH_EXPORT_ALL_COMPONENTS_FILE_NAME);
                                break;

                            case "Import components":
                                openFileExplorerImport();
                                break;

                            default:
                                return false;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        Helper.applyRippleToToolbarView(more_icon);
    }

    private class ListAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            this._data = arrayList;
        }

        @Override
        public int getCount() {
            return this._data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int i) {
            return this._data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427802, null);
            }
            LinearLayout root = convertView.findViewById(2131232468);
            a(root, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView icon = convertView.findViewById(2131232469);
            icon.setImageResource(Integer.parseInt(this._data.get(position).get("icon").toString()));
            ((LinearLayout) icon.getParent()).setGravity(17);
            ((TextView) convertView.findViewById(2131232470)).setText(this._data.get(position).get("name").toString());
            ((TextView) convertView.findViewById(2131232471)).setText(this._data.get(position).get("description").toString());
            root.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), ComponentsMakerCreator.class);
                    intent.putExtra("pos", String.valueOf(position));
                    intent.putExtra("name", ListAdapter.this._data.get(position).get("name").toString());
                    startActivity(intent);
                }
            });
            root.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ComponentsMaker.this);
                    builder.setTitle(ListAdapter.this._data.get(position).get("name").toString())
                            .setItems(new String[]{"Export", "Delete"}, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        exportComponent(position);
                                    }
                                    if (which == 1) {
                                        deleteItem(position);
                                    }
                                }
                            })
                            .create().show();
                    return true;
                }
            });
            return convertView;
        }
    }
}