package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Zx;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;

public class BlocksManager extends AppCompatActivity {

    private static final String SETTINGS_FILE_PATH = ConfigActivity.SETTINGS_FILE.getAbsolutePath();

    private final Intent intent = new Intent();
    private final HashMap<String, Object> map = new HashMap<>();
    private ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private String blocks_dir = "";
    private LinearLayout card2;
    private TextView card2_sub;
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder emptyDialog;
    private int insert_n = 0;
    private ListView listview1;
    private String pallet_dir = "";
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.blocks_manager);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        FloatingActionButton _fab = findViewById(Resources.id.fab);
        listview1 = findViewById(Resources.id.list_pallete);
        ImageView back_icon = findViewById(Resources.id.back_icon);
        ImageView arrange_icon = findViewById(Resources.id.dirs);
        card2 = findViewById(Resources.id.recycle_bin);
        card2_sub = findViewById(Resources.id.recycle_sub);
        dialog = new AlertDialog.Builder(this);
        emptyDialog = new AlertDialog.Builder(this);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);
        arrange_icon.setOnClickListener(v -> {
            final AlertDialog create = new AlertDialog.Builder(BlocksManager.this).create();
            View inflate = getLayoutInflater().inflate(Resources.layout.settings_popup, null);
            create.setView(inflate);
            create.requestWindowFeature(Window.FEATURE_NO_TITLE);
            create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final EditText editText = inflate.findViewById(Resources.id.pallet_dir);
            editText.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
            final EditText editText2 = inflate.findViewById(Resources.id.blocks_dir);
            editText2.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
            inflate.findViewById(Resources.id.extra_input_layout).setVisibility(View.GONE);
            inflate.findViewById(Resources.id.save).setOnClickListener(save -> {
                HashMap<String, Object> hashMap = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE_PATH), Helper.TYPE_MAP);
                hashMap.put("palletteDir", editText.getText().toString());
                hashMap.put("blockDir", editText2.getText().toString());
                FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap));
                _readSettings();
                _refresh_list();
                create.dismiss();
            });
            inflate.findViewById(Resources.id.cancel).setOnClickListener(
                    Helper.getDialogDismissListener(create));
            inflate.findViewById(Resources.id.defaults).setOnClickListener(defaults -> {
                HashMap<String, Object> hashMap = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE_PATH), Helper.TYPE_MAP);
                hashMap.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
                hashMap.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
                FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap));
                _readSettings();
                _refresh_list();
                create.dismiss();
            });
            create.show();
        });
        Helper.applyRippleToToolbarView(arrange_icon);
        _fab.setOnClickListener(v -> {
            insert_n = -1;
            final AlertDialog create = new AlertDialog.Builder(this).create();
            View inflate = getLayoutInflater().inflate(Resources.layout.add_new_pallete_customview, null);
            create.setView(inflate);
            create.requestWindowFeature(Window.FEATURE_NO_TITLE);
            create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final EditText editText = inflate.findViewById(Resources.id.name);
            final EditText editText2 = inflate.findViewById(Resources.id.color);
            inflate.findViewById(Resources.id.select).setOnClickListener(select -> {
                View colorPicker = getLayoutInflater().inflate(Resources.layout.color_picker, null);
                Zx zx = new Zx(colorPicker, BlocksManager.this, 0, true, false);
                zx.a(new PCP(BlocksManager.this, editText2, create));
                zx.setAnimationStyle(Resources.anim.abc_fade_in);
                zx.showAtLocation(colorPicker, Gravity.CENTER, 0, 0);
                create.hide();
            });
            inflate.findViewById(Resources.id.save).setOnClickListener(save -> {
                try {
                    Color.parseColor(editText2.getText().toString());
                    _createPallette(editText.getText().toString(), editText2.getText().toString());
                    insert_n = -1;
                    create.dismiss();
                } catch (Exception e) {
                    editText2.setError("Hex color is not formed well");
                }
            });
            inflate.findViewById(Resources.id.cancel).setOnClickListener(cancel -> {
                insert_n = -1;
                create.dismiss();
            });
            create.show();
        });
    }

    private void initializeLogic() {
        _readSettings();
        _refresh_list();
        _recycleBin(card2);
        insert_n = -1;
    }

    @Override
    public void onResume() {
        super.onResume();
        _readSettings();
        _refresh_list();
    }

    @Override
    public void onStop() {
        super.onStop();
        BlockLoader.refresh();
    }

    private void _a(View view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(0xffffffff);
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{0x20008dcd}), gradientDrawable, null);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void _readSettings() {
        if (!FileUtil.isExistFile(SETTINGS_FILE_PATH)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
            hashMap.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
            FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap));
            _readSettings();
        } else if (!FileUtil.readFile(SETTINGS_FILE_PATH).equals("")) {
            HashMap<String, Object> hashMap2 = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE_PATH), Helper.TYPE_MAP);
            if (hashMap2.containsKey("palletteDir")) {
                pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
            } else {
                hashMap2.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
                pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
                FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap2));
            }
            if (hashMap2.containsKey("blockDir")) {
                blocks_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("blockDir").toString());
                all_blocks_list.clear();
                if (FileUtil.isExistFile(blocks_dir) && !FileUtil.readFile(blocks_dir).equals("")) {
                    try {
                        all_blocks_list = new Gson().fromJson(FileUtil.readFile(blocks_dir), Helper.TYPE_MAP_LIST);
                    } catch (Exception ignored) {
                    }
                }
            } else {
                hashMap2.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
                blocks_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("blockDir").toString());
                FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap2));
            }
        } else {
            HashMap<String, Object> hashMap3 = new HashMap<>();
            hashMap3.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
            hashMap3.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
            FileUtil.writeFile(SETTINGS_FILE_PATH, new Gson().toJson(hashMap3));
            _readSettings();
        }
    }

    private void _refresh_list() {
        try {
            if (!FileUtil.isExistFile(pallet_dir) || FileUtil.readFile(pallet_dir).equals("")) {
                pallet_listmap.clear();
                listview1.setAdapter(new PalettesAdapter(pallet_listmap));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
            } else {
                Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
                pallet_listmap = new Gson().fromJson(FileUtil.readFile(pallet_dir), Helper.TYPE_MAP_LIST);
                listview1.setAdapter(new PalettesAdapter(pallet_listmap));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                listview1.onRestoreInstanceState(onSaveInstanceState);
            }
            card2_sub.setText("Blocks: ".concat(String.valueOf((long) _getN(-1.0d))));
        } catch (Exception ignored) {
        }
    }

    private void _remove_pallete(final double d) {
        dialog.setTitle(pallet_listmap.get((int) d).get("name").toString());
        dialog.setMessage("Remove all blocks related to this palette?");
        dialog.setNeutralButton("Remove permanently", (dialog, which) -> {
            pallet_listmap.remove((int) d);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _removeRelatedBlocks(d + 9.0d);
            _readSettings();
            _refresh_list();
        });
        dialog.setNegativeButton(Resources.string.common_word_cancel, null);
        dialog.setPositiveButton("Move to recycle bin", (dialog, which) -> {
            _moveRelatedBlocksToRecycleBin(d + 9.0d);
            pallet_listmap.remove((int) d);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _removeRelatedBlocks(d + 9.0d);
            _readSettings();
            _refresh_list();
        });
        dialog.create().show();
    }

    private double _getN(double d) {
        int i = 0;
        for (int i2 = 0; i2 < all_blocks_list.size(); i2++) {
            if (all_blocks_list.get(i2).get("palette").toString().equals(String.valueOf((long) d))) {
                i++;
            }
        }
        return i;
    }

    private void _createPallette(String str, String str2) {
        map.put("name", str);
        map.put("color", str2);
        if (insert_n == -1) {
            pallet_listmap.add(map);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _readSettings();
            _refresh_list();
        } else {
            pallet_listmap.add(insert_n, map);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _readSettings();
            _refresh_list();
            _insertBlocksAt(insert_n + 9);
        }
        insert_n = -1;
    }

    private void _showEditDial(final double d, String str, String str2) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.add_new_pallete_customview, null);
        create.setView(inflate);
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = inflate.findViewById(Resources.id.name);
        editText.setText(str);
        final EditText editText2 = inflate.findViewById(Resources.id.color);
        editText2.setText(str2);
        ((TextView) inflate.findViewById(Resources.id.title)).setText("Edit palette");
        TextView textView = inflate.findViewById(Resources.id.cancel);
        inflate.findViewById(Resources.id.select).setOnClickListener(v -> {
            View colorPicker = getLayoutInflater().inflate(Resources.layout.color_picker, null);
            Zx zx = new Zx(colorPicker, BlocksManager.this, 0, true, false);
            zx.a(new PCP(BlocksManager.this, editText2, create));
            zx.setAnimationStyle(0x7f010000);
            zx.showAtLocation(colorPicker, 17, 0, 0);
            create.hide();
        });
        inflate.findViewById(Resources.id.save).setOnClickListener(save -> {
            try {
                Color.parseColor(editText2.getText().toString());
                _editPallete(d, editText.getText().toString(), editText2.getText().toString());
                create.dismiss();
            } catch (Exception ignored) {
            }
        });
        textView.setOnClickListener(Helper.getDialogDismissListener(create));
        create.show();
    }

    private void _editPallete(double d, String str, String str2) {
        pallet_listmap.get((int) d).put("name", str);
        pallet_listmap.get((int) d).put("color", str2);
        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
        _readSettings();
        _refresh_list();
    }

    private void _MoveUp(double d) {
        if (d > 0) {
            Collections.swap(pallet_listmap, (int) d, (int) (d - 1));
            Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _swapRelatedBlocks(d + 9, d + 8);
            _readSettings();
            _refresh_list();
            listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _recycleBin(View view) {
        _a(view);
        card2.setOnClickListener(v -> {
            intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
            intent.putExtra("position", "-1");
            intent.putExtra("dirB", blocks_dir);
            intent.putExtra("dirP", pallet_dir);
            startActivity(intent);
        });
        card2.setOnLongClickListener(v -> {
            emptyDialog.setTitle("Recycle bin");
            emptyDialog.setMessage("Are you sure you want to empty the recycle bin? " +
                    "Blocks inside will be deleted PERMANENTLY, you CANNOT recover them!");
            emptyDialog.setPositiveButton("Empty", (dialog, which) -> _emptyRecyclebin());
            emptyDialog.setNegativeButton(Resources.string.common_word_cancel, null);
            emptyDialog.create().show();
            return true;
        });
    }

    private void _insert_pallete(int d) {
        insert_n = d;
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.add_new_pallete_customview, null);
        create.setView(inflate);
        create.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText editText = inflate.findViewById(Resources.id.name);
        final EditText editText2 = inflate.findViewById(Resources.id.color);
        inflate.findViewById(Resources.id.select).setOnClickListener(select -> {
            View colorPicker = getLayoutInflater().inflate(Resources.layout.color_picker, null);
            Zx zx = new Zx(colorPicker, BlocksManager.this, 0, true, false);
            zx.a(new PCP(BlocksManager.this, editText2, create));
            zx.setAnimationStyle(0x7f010000);
            zx.showAtLocation(colorPicker, Gravity.CENTER, 0, 0);
            create.hide();
        });
        inflate.findViewById(Resources.id.save).setOnClickListener(save -> {
            try {
                Color.parseColor(editText2.getText().toString());
                _createPallette(editText.getText().toString(), editText2.getText().toString());
                create.dismiss();
            } catch (Exception ignored) {
            }
        });
        inflate.findViewById(Resources.id.cancel).setOnClickListener(
                Helper.getDialogDismissListener(create));
        create.show();
    }

    private void _moveDown(double d) {
        if (d < pallet_listmap.size() - 1) {
            Collections.swap(pallet_listmap, (int) d, (int) (d + 1));
            Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _swapRelatedBlocks(9.0d + d, 10.0d + d);
            _readSettings();
            _refresh_list();
            listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _removeRelatedBlocks(double d) {
        ArrayList<HashMap<String, Object>> temp_list = new ArrayList<>();
        for (HashMap<String, Object> block : all_blocks_list) {
            int palette = Integer.parseInt(block.get("palette").toString());

            if (palette != d) {
                if (palette > d) {
                    // Modifying map in list instead of new temporary map because that's the old logic
                    block.put("palette", palette - 1);
                    temp_list.add(block);
                } else {
                    temp_list.add(block);
                }
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(temp_list));
        _readSettings();
        _refresh_list();
    }

    private void _swapRelatedBlocks(double d, double d2) {
        for (HashMap<String, Object> block : all_blocks_list) {
            int palette = Integer.parseInt(block.get("palette").toString());

            if (palette == d) {
                block.put("palette", String.valueOf((long) d2));
            } else if (palette == d2) {
                block.put("palette", String.valueOf((long) d));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _insertBlocksAt(double d) {
        for (HashMap<String, Object> block : all_blocks_list) {
            int palette = Integer.parseInt(block.get("palette").toString());
            if (palette >= d) {
                block.put("palette", palette);
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _moveRelatedBlocksToRecycleBin(double d) {
        for (HashMap<String, Object> block : all_blocks_list) {
            if (Double.parseDouble(block.get("palette").toString()) == d) {
                block.put("palette", "-1");
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _emptyRecyclebin() {
        ArrayList<HashMap<String, Object>> regularBlocks = new ArrayList<>();
        for (HashMap<String, Object> block : all_blocks_list) {
            if (Integer.parseInt(block.get("palette").toString()) != -1) {
                regularBlocks.add(block);
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(regularBlocks));
        _readSettings();
        _refresh_list();
    }

    private class PalettesAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> palettes;

        public PalettesAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            palettes = arrayList;
        }

        @Override
        public int getCount() {
            return palettes.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return palettes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(Resources.layout.pallet_customview, null);
            }
            final LinearLayout linearLayout = convertView.findViewById(Resources.id.background);
            ((TextView) convertView.findViewById(Resources.id.title)).setText(pallet_listmap.get(position).get("name").toString());
            ((TextView) convertView.findViewById(Resources.id.sub)).setText("Blocks: ".concat(String.valueOf((long) _getN(position + 9))));
            card2_sub.setText("Blocks: ".concat(String.valueOf((long) _getN(-1.0d))));
            convertView.findViewById(Resources.id.color).setBackgroundColor(Color.parseColor((String) palettes.get(position).get("color")));
            _a(linearLayout);
            linearLayout.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(BlocksManager.this, linearLayout);
                final Menu menu = popupMenu.getMenu();
                if (position != 0) {
                    menu.add("Move up");
                }
                if (position != getCount() - 1) {
                    menu.add("Move down");
                }
                menu.add("Edit");
                menu.add("Delete");
                menu.add("Insert above");
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getTitle().toString()) {
                        case "Move up":
                            _MoveUp(position);
                            break;

                        case "Move down":
                            _moveDown(position);
                            break;

                        case "Insert above":
                            _insert_pallete(position);
                            break;

                        case "Edit":
                            _showEditDial(position, pallet_listmap.get(position).get("name").toString(), pallet_listmap.get(position).get("color").toString());
                            break;

                        case "Delete":
                            _remove_pallete(position);
                            break;

                        default:
                            return false;
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
            linearLayout.setOnClickListener(v -> {
                intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", String.valueOf(position + 9));
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            });

            return convertView;
        }
    }
}
