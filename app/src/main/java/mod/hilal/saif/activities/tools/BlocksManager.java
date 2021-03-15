package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Zx;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.FileUtil;
import mod.hilal.saif.lib.PCP;

public class BlocksManager extends AppCompatActivity {

    private FloatingActionButton _fab;
    private ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private ImageView arrange_icon;
    private ImageView back_icon;
    private LinearLayout background;
    private String blocks_dir = "";
    private LinearLayout card2;
    private ImageView card2_icon;
    private TextView card2_sub;
    private AlertDialog.Builder dia;
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder emptyDialog;
    private double insert_n = 0.0d;
    private final Intent intent = new Intent();
    private ListView listview1;
    private HashMap<String, Object> m = new HashMap<>();
    private final HashMap<String, Object> map = new HashMap<>();
    private TextView page_title;
    private String pallet_dir = "";
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> temp_list = new ArrayList<>();
    private LinearLayout toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427808);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _fab = findViewById(2131231054);
        background = findViewById(2131232515);
        toolbar = findViewById(2131231847);
        listview1 = findViewById(2131232520);
        back_icon = findViewById(2131232519);
        page_title = findViewById(2131231582);
        arrange_icon = findViewById(2131232518);
        card2 = findViewById(2131232517);
        card2_icon = findViewById(2131232523);
        card2_sub = findViewById(2131232522);
        dia = new AlertDialog.Builder(this);
        dialog = new AlertDialog.Builder(this);
        emptyDialog = new AlertDialog.Builder(this);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        arrange_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog create = new AlertDialog.Builder(BlocksManager.this).create();
                View inflate = getLayoutInflater().inflate(2131427809, null);
                create.setView(inflate);
                create.requestWindowFeature(1);
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = inflate.findViewById(2131232525);
                editText.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
                final EditText editText2 = inflate.findViewById(2131232526);
                editText2.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
                inflate.findViewById(2131232527).setVisibility(View.GONE);
                inflate.findViewById(2131232528).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
                        hashMap.put("palletteDir", editText.getText().toString());
                        hashMap.put("blockDir", editText2.getText().toString());
                        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap));
                        _readSettings();
                        _refresh_list();
                        create.dismiss();
                    }
                });
                inflate.findViewById(2131232351).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        create.dismiss();
                    }
                });
                inflate.findViewById(2131232530).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
                        hashMap.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
                        hashMap.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
                        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap));
                        _readSettings();
                        _refresh_list();
                        create.dismiss();
                    }
                });
                create.show();
            }
        });
        Helper.applyRippleToToolbarView(arrange_icon);
        _fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insert_n = -1.0d;
                final AlertDialog create = new AlertDialog.Builder(BlocksManager.this).create();
                View inflate = getLayoutInflater().inflate(2131427810, null);
                create.setView(inflate);
                create.requestWindowFeature(1);
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = inflate.findViewById(2131231561);
                final EditText editText2 = inflate.findViewById(2131230904);
                inflate.findViewById(2131232352).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        View inflate = getLayoutInflater().inflate(2131427373, null);
                        Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                        zx.a(new PCP(BlocksManager.this, editText2, create));
                        zx.setAnimationStyle(2130771968);
                        zx.showAtLocation(inflate, 17, 0, 0);
                        create.hide();
                    }
                });
                inflate.findViewById(2131232528).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            Color.parseColor(editText2.getText().toString());
                            _createPallette(editText.getText().toString(), editText2.getText().toString());
                            insert_n = -1.0d;
                            create.dismiss();
                        } catch (Exception ignored) {
                        }
                    }
                });
                inflate.findViewById(2131232351).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        insert_n = -1.0d;
                        create.dismiss();
                    }
                });
                create.show();
            }
        });
    }

    private void initializeLogic() {
        _readSettings();
        _refresh_list();
        _recycleBin(card2);
        insert_n = -1.0d;
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
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void _readSettings() {
        if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"))) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
            hashMap.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap));
            _readSettings();
        } else if (!FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")).equals("")) {
            HashMap<String, Object> hashMap2 = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
            if (hashMap2.containsKey("palletteDir")) {
                pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
            } else {
                hashMap2.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
                pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap2));
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
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap2));
            }
        } else {
            HashMap<String, Object> hashMap3 = new HashMap<>();
            hashMap3.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
            hashMap3.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap3));
            _readSettings();
        }
    }

    private void _refresh_list() {
        try {
            if (!FileUtil.isExistFile(pallet_dir) || FileUtil.readFile(pallet_dir).equals("")) {
                pallet_listmap.clear();
                listview1.setAdapter(new Listview1Adapter(pallet_listmap));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
            } else {
                Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
                pallet_listmap = new Gson().fromJson(FileUtil.readFile(pallet_dir), Helper.TYPE_MAP_LIST);
                listview1.setAdapter(new Listview1Adapter(pallet_listmap));
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
        dialog.setNeutralButton("Remove permanently", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pallet_listmap.remove((int) d);
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(d + 9.0d);
                _readSettings();
                _refresh_list();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.setPositiveButton("Move to recycle bin", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                _moveRelatedBlocksToRecycleBin(d + 9.0d);
                pallet_listmap.remove((int) d);
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(d + 9.0d);
                _readSettings();
                _refresh_list();
            }
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
        if (insert_n == -1.0d) {
            pallet_listmap.add(map);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _readSettings();
            _refresh_list();
            insert_n = -1.0d;
            return;
        }
        pallet_listmap.add((int) insert_n, map);
        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
        _readSettings();
        _refresh_list();
        _insertBlocksAt(insert_n + 9.0d);
        insert_n = -1.0d;
    }

    private void _showEditDial(final double d, String str, String str2) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427810, null);
        create.setView(inflate);
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = inflate.findViewById(2131231561);
        editText.setText(str);
        final EditText editText2 = inflate.findViewById(2131230904);
        editText2.setText(str2);
        ((TextView) inflate.findViewById(2131231837)).setText("Edit palette");
        TextView textView = inflate.findViewById(2131232351);
        inflate.findViewById(2131232352).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(2131427373, null);
                Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                zx.a(new PCP(BlocksManager.this, editText2, create));
                zx.setAnimationStyle(2130771968);
                zx.showAtLocation(inflate, 17, 0, 0);
                create.hide();
            }
        });
        inflate.findViewById(2131232528).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Color.parseColor(editText2.getText().toString());
                    _editPallete(d, editText.getText().toString(), editText2.getText().toString());
                    create.dismiss();
                } catch (Exception ignored) {
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                create.dismiss();
            }
        });
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
        if (d > 0.0d) {
            Collections.swap(pallet_listmap, (int) d, (int) (-1.0d + d));
            Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _swapRelatedBlocks(9.0d + d, 8.0d + d);
            _readSettings();
            _refresh_list();
            listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _recycleBin(View view) {
        _a(view);
        card2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", "-1");
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            }
        });
        card2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                emptyDialog.setTitle("Recycle bin");
                emptyDialog.setMessage("Are you sure you want to empty the recycle bin? Blocks inside will be deleted PERMANENTLY, you CANNOT recover them!");
                emptyDialog.setPositiveButton("Empty", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        _emptyRecyclebin();
                    }
                });
                emptyDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                emptyDialog.create().show();
                return true;
            }
        });
    }

    private void _insert_pallete(double d) {
        insert_n = d;
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427810, null);
        create.setView(inflate);
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = inflate.findViewById(2131231561);
        final EditText editText2 = inflate.findViewById(2131230904);
        inflate.findViewById(2131232352).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(2131427373, null);
                Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                zx.a(new PCP(BlocksManager.this, editText2, create));
                zx.setAnimationStyle(2130771968);
                zx.showAtLocation(inflate, 17, 0, 0);
                create.hide();
            }
        });
        inflate.findViewById(2131232528).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Color.parseColor(editText2.getText().toString());
                    _createPallette(editText.getText().toString(), editText2.getText().toString());
                    create.dismiss();
                } catch (Exception ignored) {
                }
            }
        });
        inflate.findViewById(2131232351).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        create.show();
    }

    private void _moveDown(double d) {
        if (d < ((double) (pallet_listmap.size() - 1))) {
            Collections.swap(pallet_listmap, (int) d, (int) (1.0d + d));
            Parcelable onSaveInstanceState = listview1.onSaveInstanceState();
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _swapRelatedBlocks(9.0d + d, 10.0d + d);
            _readSettings();
            _refresh_list();
            listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _removeRelatedBlocks(double d) {
        temp_list.clear();
        m = new HashMap<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) != d) {
                if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) > d) {
                    m = all_blocks_list.get(i);
                    m.put("palette", String.valueOf((long) (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) - 1.0d)));
                    temp_list.add(m);
                } else {
                    temp_list.add(all_blocks_list.get(i));
                }
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(temp_list));
        _readSettings();
        _refresh_list();
    }

    private void _swapRelatedBlocks(double d, double d2) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == d) {
                all_blocks_list.get(i).put("palette", "123456789");
            }
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == d2) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) d));
            }
        }
        for (int i2 = 0; i2 < all_blocks_list.size(); i2++) {
            if (Double.parseDouble(all_blocks_list.get(i2).get("palette").toString()) == 1.23456789E8d) {
                all_blocks_list.get(i2).put("palette", String.valueOf((long) d2));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _insertBlocksAt(double d) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) > d || Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == d) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) + 1.0d)));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _moveRelatedBlocksToRecycleBin(double d) {
        m = new HashMap<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == d) {
                all_blocks_list.get(i).put("palette", "-1");
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _emptyRecyclebin() {
        temp_list.clear();
        m = new HashMap<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) != -1.0d) {
                temp_list.add(all_blocks_list.get(i));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(temp_list));
        _readSettings();
        _refresh_list();
    }

    public class Listview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

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
                convertView = getLayoutInflater().inflate(2131427811, null);
            }
            final LinearLayout linearLayout = convertView.findViewById(2131232515);
            ((TextView) convertView.findViewById(2131231837)).setText(pallet_listmap.get(position).get("name").toString());
            ((TextView) convertView.findViewById(2131232541)).setText("Blocks: ".concat(String.valueOf((long) _getN(position + 9))));
            card2_sub.setText("Blocks: ".concat(String.valueOf((long) _getN(-1.0d))));
            convertView.findViewById(2131230904).setBackgroundColor(Color.parseColor((String) _data.get(position).get("color")));
            _a(linearLayout);
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(BlocksManager.this, linearLayout);
                    final Menu menu = popupMenu.getMenu();
                    menu.add("Move up");
                    menu.add("Move down");
                    menu.add("Edit");
                    menu.add("Delete");
                    menu.add("Insert above");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
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
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                    intent.putExtra("position", String.valueOf(position + 9));
                    intent.putExtra("dirB", blocks_dir);
                    intent.putExtra("dirP", pallet_dir);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}