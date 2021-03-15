package mod.hilal.saif.activities.tools;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.FileUtil;

public class BlocksManagerDetailsActivity extends AppCompatActivity {

    private static final String BLOCK_EXPORT_PATH = new File(FileUtil.getExternalStorageDir(), ".sketchware/resources/block/export/").getAbsolutePath();

    public final int REQ_CD_FILE = 101;
    private final Intent file = new Intent(Intent.ACTION_GET_CONTENT);
    private final ArrayList<HashMap<String, Object>> filtered_list = new ArrayList<>();
    private final Intent intents = new Intent();
    private final ArrayList<Integer> reference_list = new ArrayList<>();
    private final ArrayList<String> temp = new ArrayList<>();
    private FloatingActionButton _fab;
    private ArrayList<HashMap<String, Object>> all_blcoks_list = new ArrayList<>();
    private String blocks_path = "";
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder import_dialog;
    private ImageView import_export;
    private ListView listview1;
    private HashMap<String, Object> map = new HashMap<>();
    private String mode = "normal";
    private TextView page_title;
    private ArrayList<HashMap<String, Object>> pallet_list = new ArrayList<>();
    private String pallet_path = "";
    private int pallette = 0;
    private Parcelable prcl;
    private AlertDialog.Builder remove_dialog;
    private ImageView swap;
    private int tempN = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427812);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _fab = findViewById(2131231054);
        listview1 = findViewById(2131232545);
        ImageView back_icon = findViewById(2131232546);
        page_title = findViewById(2131232547);
        import_export = findViewById(2131232548);
        swap = findViewById(2131232549);
        dialog = new AlertDialog.Builder(this);
        file.setType("*/*");
        file.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        remove_dialog = new AlertDialog.Builder(this);
        import_dialog = new AlertDialog.Builder(this);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        import_export.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(BlocksManagerDetailsActivity.this, import_export);
                final Menu menu = popupMenu.getMenu();
                menu.add("Import blocks");
                menu.add("Export blocks");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Import blocks":
                                openFileExplorerImport();
                                break;

                            case "Export blocks":
                                String exportTo = new File(BLOCK_EXPORT_PATH, pallet_list.get(pallette - 9).get("name").toString() + ".json").getAbsolutePath();
                                FileUtil.writeFile(exportTo, new Gson().toJson(filtered_list));
                                SketchwareUtil.toast("Successfully exported blocks to:\n" + exportTo);
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
        Helper.applyRippleToToolbarView(import_export);
        swap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mode.equals("normal")) {
                    swap.setImageResource(2131165894);
                    mode = "editor";
                    import_export.setVisibility(View.GONE);
                    _fabVisibility(false);
                } else {
                    swap.setImageResource(2131165788);
                    mode = "normal";
                    import_export.setVisibility(View.VISIBLE);
                    _fabVisibility(true);
                }
                Parcelable savedInstanceState = listview1.onSaveInstanceState();
                listview1.setAdapter(new Listview1Adapter(filtered_list));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                listview1.onRestoreInstanceState(savedInstanceState);
            }
        });
        Helper.applyRippleToToolbarView(swap);
        _fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intents.setClass(getApplicationContext(), BlocksManagerCreatorActivity.class);
                intents.putExtra("mode", "add");
                intents.putExtra("color", pallet_list.get(pallette - 9).get("color").toString());
                intents.putExtra("path", blocks_path);
                intents.putExtra("pallet", String.valueOf((long) pallette));
                startActivity(intents);
            }
        });
    }

    private void initializeLogic() {
        _receive_intents();
    }

    public void openFileExplorerImport() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = 0;
        properties.selection_type = 0;
        File externalStorageDir = new File(FileUtil.getExternalStorageDir());
        properties.root = externalStorageDir;
        properties.error_dir = externalStorageDir;
        properties.offset = externalStorageDir;
        properties.extensions = new String[]{"json"};
        FilePickerDialog filePickerDialog = new FilePickerDialog(this, properties);
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
                        _importBlocks((ArrayList<HashMap<String, Object>>) new Gson().fromJson(FileUtil.readFile(selections[0]), Helper.TYPE_MAP_LIST));
                    } catch (Exception e) {
                        SketchwareUtil.toastError("Invalid JSON file");
                    }
                }
            }
        });
        filePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CD_FILE) {
            if (resultCode == -1) {
                ArrayList<String> arrayList = new ArrayList<>();
                if (data != null) {
                    if (data.getClipData() != null) {
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            arrayList.add(FileUtil.convertUriToFilePath(getApplicationContext(), data.getClipData().getItemAt(i).getUri()));
                        }
                    } else {
                        arrayList.add(FileUtil.convertUriToFilePath(getApplicationContext(), data.getData()));
                    }
                }
                if (!FileUtil.readFile(arrayList.get(0)).equals("")) {
                    try {
                        _importBlocks((ArrayList<HashMap<String, Object>>) new Gson().fromJson(FileUtil.readFile(arrayList.get(0)), Helper.TYPE_MAP_LIST));
                    } catch (Exception e) {
                        SketchwareUtil.toastError("Invalid JSON file");
                    }
                } else {
                    SketchwareUtil.toastError("The selected file is empty!");
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        prcl = listview1.onSaveInstanceState();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listview1.onRestoreInstanceState(prcl);
            _refreshLists();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onBackPressed() {
        if (mode.equals("editor")) {
            swap.setImageResource(2131165788);
            mode = "normal";
            Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
            listview1.setAdapter(new Listview1Adapter(filtered_list));
            ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
            listview1.onRestoreInstanceState(onSaveInstanceState);
            import_export.setVisibility(View.VISIBLE);
            _fabVisibility(true);
            return;
        }
        finish();
    }

    private void _receive_intents() {
        pallette = Integer.parseInt(getIntent().getStringExtra("position"));
        pallet_path = getIntent().getStringExtra("dirP");
        blocks_path = getIntent().getStringExtra("dirB");
        _refreshLists();
        if (pallette == -1) {
            page_title.setText("Recycle bin");
            swap.setVisibility(View.GONE);
            import_export.setVisibility(View.GONE);
            _fab.setVisibility(View.GONE);
            return;
        }
        page_title.setText(pallet_list.get(pallette - 9).get("name").toString());
    }

    private void _refreshLists() {
        filtered_list.clear();
        reference_list.clear();
        if (FileUtil.readFile(pallet_path).equals("")) {
            FileUtil.writeFile(pallet_path, "[]");
        }
        if (FileUtil.readFile(blocks_path).equals("")) {
            FileUtil.writeFile(blocks_path, "[]");
        }
        try {
            pallet_list = new Gson().fromJson(FileUtil.readFile(pallet_path), Helper.TYPE_MAP_LIST);
            all_blcoks_list = new Gson().fromJson(FileUtil.readFile(blocks_path), Helper.TYPE_MAP_LIST);
        } catch (Exception e) {
            SketchwareUtil.toastError("Invalid file format!\nMake sure that block.json or palette.json file is formatted correctly.");
            finish();
        }
        for (int i = 0; i < all_blcoks_list.size(); i++) {
            if (Double.parseDouble(all_blcoks_list.get(i).get("palette").toString()) == pallette) {
                reference_list.add(i);
                filtered_list.add(all_blcoks_list.get(i));
            }
        }
        Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
        listview1.setAdapter(new Listview1Adapter(filtered_list));
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        listview1.onRestoreInstanceState(onSaveInstanceState);
    }

    private void _a(View view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void _swapitems(int d, int d2) {
        Collections.swap(all_blcoks_list, d, d2);
        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
        _refreshLists();
    }

    private void _showItemPopup(View view, final int d) {
        if (pallette == -1) {
            PopupMenu popupMenu = new PopupMenu(this, view);
            Menu menu = popupMenu.getMenu();
            menu.add("Delete permanently");
            menu.add("Restore");
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getTitle().toString()) {
                        case "Delete permanently":
                            _deleteBlock(d);
                            break;

                        case "Restore":
                            _changePallette(d);
                            break;

                        default:
                            return false;
                    }
                    return true;
                }
            });
            popupMenu.show();
            return;
        }
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu2 = popupMenu.getMenu();
        menu2.add("Insert above");
        menu2.add("Delete");
        menu2.add("Duplicate");
        menu2.add("Move to palette");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Duplicate":
                        _duplicateBlock(d);
                        break;

                    case "Insert above":
                        intents.setClass(getApplicationContext(), BlocksManagerCreatorActivity.class);
                        intents.putExtra("mode", "insert");
                        intents.putExtra("path", blocks_path);
                        intents.putExtra("color", pallet_list.get(pallette - 9).get("color").toString());
                        intents.putExtra("pos", String.valueOf((long) d));
                        startActivity(intents);
                        break;

                    case "Move to palette":
                        _changePallette(d);
                        break;

                    case "Delete":
                        remove_dialog.setTitle("Delete block?")
                                .setMessage("Are you sure you want to delete this block?")
                                .setPositiveButton("Move to Recycle bin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        _moveToRecycleBin(d);
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setNeutralButton("Delete permanently", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        _deleteBlock(d);
                                    }
                                })
                                .create().show();
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void _duplicateBlock(double d) {
        map = new HashMap<>();
        map = all_blcoks_list.get((int) d);
        all_blcoks_list.add(((int) d) + 1, map);
        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
        _refreshLists();
        map = new HashMap<>();
        map = all_blcoks_list.get(((int) d) + 1);
        if (map.get("name").toString().matches("(?s).*_copy[0-9][0-9]")) {
            map.put("name", map.get("name").toString().replaceAll("_copy[0-9][0-9]", "_copy".concat(String.valueOf((long) SketchwareUtil.getRandom(11, 99)))));
        } else {
            map.put("name", map.get("name").toString().concat("_copy").concat(String.valueOf((long) SketchwareUtil.getRandom(11, 99))));
        }
        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
        _refreshLists();
    }

    private void _deleteBlock(double d) {
        all_blcoks_list.remove((int) d);
        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
        _refreshLists();
    }

    private void _moveToRecycleBin(double d) {
        all_blcoks_list.get((int) d).put("palette", "-1");
        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
        _refreshLists();
    }

    private void _changePallette(final int position) {
        int i = 0;
        if (pallette == -1) {
            temp.clear();
            tempN = -1;
            while (i < pallet_list.size()) {
                temp.add(pallet_list.get(i).get("name").toString());
                i++;
            }
            dialog.setTitle("Restore to")
                    .setSingleChoiceItems(temp.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tempN = which;
                        }
                    })
                    .setPositiveButton("Restore", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            if (tempN != -1) {
                                all_blcoks_list.get(position).put("palette", String.valueOf((long) (tempN + 9)));
                                Collections.swap(all_blcoks_list, position, all_blcoks_list.size() - 1);
                                FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
                                _refreshLists();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create().show();
            return;
        }
        temp.clear();
        tempN = pallette - 9;
        while (i < pallet_list.size()) {
            temp.add(pallet_list.get(i).get("name").toString());
            i++;
        }
        dialog.setTitle("Move to")
                .setSingleChoiceItems(temp.toArray(new String[0]), pallette - 9, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tempN = which;
                    }
                })
                .setPositiveButton("Move", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        all_blcoks_list.get(position).put("palette", String.valueOf((long) (tempN + 9)));
                        Collections.swap(all_blcoks_list, position, all_blcoks_list.size() - 1);
                        FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
                        _refreshLists();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    private void _importBlocks(final ArrayList<HashMap<String, Object>> blocks) {
        try {
            temp.clear();
            ArrayList<String> names = new ArrayList<>();
            final ArrayList<Integer> toAdd = new ArrayList<>();
            for (int i = 0; i < blocks.size(); i++) {
                names.add(blocks.get(i).get("name").toString());
            }
            import_dialog.setTitle("Import blocks")
                    .setMultiChoiceItems(names.toArray(new CharSequence[0]), null, new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                toAdd.add(which);
                            } else {
                                toAdd.remove((Integer) which);
                            }
                        }
                    })
                    .setPositiveButton("Import", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i2 = 0; i2 < blocks.size(); i2++) {
                                if (toAdd.contains(i2)) {
                                    map = new HashMap<>();
                                    map = blocks.get(i2);
                                    map.put("palette", String.valueOf((long) pallette));
                                    all_blcoks_list.add(map);
                                }
                            }
                            FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
                            _refreshLists();
                            SketchwareUtil.toast("Imported successfully");
                        }
                    })
                    .setNegativeButton("Reverse", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < blocks.size(); i++) {
                                if (!toAdd.contains(i)) {
                                    map = new HashMap<>();
                                    map = blocks.get(i);
                                    map.put("palette", String.valueOf((long) pallette));
                                    all_blcoks_list.add(map);
                                }
                            }
                            FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
                            _refreshLists();
                            SketchwareUtil.toast("Imported successfully");
                        }
                    })
                    .setNeutralButton("All", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < blocks.size(); i++) {
                                map = new HashMap<>();
                                map = blocks.get(i);
                                map.put("palette", String.valueOf((long) pallette));
                                all_blcoks_list.add(map);
                            }
                            FileUtil.writeFile(blocks_path, new Gson().toJson(all_blcoks_list));
                            _refreshLists();
                            SketchwareUtil.toast("Imported successfully");
                        }
                    })
                    .create().show();
        } catch (Exception e) {
            SketchwareUtil.toast("An error occurred: " + e.getMessage());
        }
    }

    private void _fabVisibility(boolean visible) {
        if (visible) {
            ObjectAnimator.ofFloat(_fab, "translationX", _fab.getTranslationX(), -50.0f, 0.0f).setDuration(400L).start();
            return;
        }
        ObjectAnimator.ofFloat(_fab, "translationX", _fab.getTranslationX(), -50.0f, 250.0f).setDuration(400L).start();
    }

    private class Listview1Adapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
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
                convertView = getLayoutInflater().inflate(2131427813, null);
            }
            final LinearLayout linearLayout = convertView.findViewById(2131232515);
            TextView textView = convertView.findViewById(2131231561);
            TextView spec = convertView.findViewById(2131232553);
            CardView cardView = convertView.findViewById(2131232556);
            CardView cardView2 = convertView.findViewById(2131232557);
            LinearLayout linearLayout2 = convertView.findViewById(2131232555);
            LinearLayout linearLayout3 = convertView.findViewById(2131232298);
            if (mode.equals("normal")) {
                cardView2.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
            } else {
                cardView2.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
            }
            _a(linearLayout3);
            _a(linearLayout2);
            _a(linearLayout);
            textView.setText(_data.get(position).get("name").toString());
            spec.setText(_data.get(position).get("spec").toString());
            String specId = _data.get(position).get("type").toString();
            switch (specId) {
                case " ":
                case "regular":
                    spec.setBackgroundResource(2131166371);
                    break;

                case "b":
                    spec.setBackgroundResource(2131166369);
                    break;

                case "c":
                case "e":
                    spec.setBackgroundResource(2131166374);
                    break;

                case "d":
                    spec.setBackgroundResource(2131166370);
                    break;

                case "f":
                    spec.setBackgroundResource(2131166372);
                    break;

                default:
                    spec.setBackgroundResource(2131166373);
                    break;
            }

            if (pallette == -1) {
                spec.getBackground().setColorFilter(Color.parseColor("#9e9e9e"), PorterDuff.Mode.MULTIPLY);
            } else {
                try {
                    spec.getBackground().setColorFilter(Color.parseColor(_data.get(position).get("color").toString()), PorterDuff.Mode.MULTIPLY);
                } catch (Exception e) {
                    spec.getBackground().setColorFilter(Color.parseColor(pallet_list.get(pallette - 9).get("color").toString()), PorterDuff.Mode.MULTIPLY);
                }
            }
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (position > 0) {
                        _swapitems(reference_list.get(position), reference_list.get(position - 1));
                    }
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (position < filtered_list.size() - 1) {
                        _swapitems(reference_list.get(position), reference_list.get(position + 1));
                    }
                }
            });
            if (mode.equals("normal")) {
                linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        _showItemPopup(linearLayout, reference_list.get(position));
                        return true;
                    }
                });
                if (pallette == -1) {
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            _showItemPopup(linearLayout, reference_list.get(position));
                        }
                    });
                } else {
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            intents.setClass(getApplicationContext(), BlocksManagerCreatorActivity.class);
                            intents.putExtra("mode", "edit");
                            intents.putExtra("color", pallet_list.get(pallette - 9).get("color").toString());
                            intents.putExtra("path", blocks_path);
                            intents.putExtra("pos", String.valueOf(reference_list.get(position)));
                            startActivity(intents);
                        }
                    });
                }
            }
            return convertView;
        }
    }
}