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
    private Intent intent = new Intent();
    private ListView listview1;
    private HashMap<String, Object> m = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private TextView page_title;
    private String pallet_dir = "";
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> temp_list = new ArrayList<>();
    private LinearLayout toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427808);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
        this._fab = (FloatingActionButton) findViewById(2131231054);
        this.background = (LinearLayout) findViewById(2131232515);
        this.toolbar = (LinearLayout) findViewById(2131231847);
        this.listview1 = (ListView) findViewById(2131232520);
        this.back_icon = (ImageView) findViewById(2131232519);
        this.page_title = (TextView) findViewById(2131231582);
        this.arrange_icon = (ImageView) findViewById(2131232518);
        this.card2 = (LinearLayout) findViewById(2131232517);
        this.card2_icon = (ImageView) findViewById(2131232523);
        this.card2_sub = (TextView) findViewById(2131232522);
        this.dia = new AlertDialog.Builder(this);
        this.dialog = new AlertDialog.Builder(this);
        this.emptyDialog = new AlertDialog.Builder(this);
        this.back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        this.arrange_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog create = new AlertDialog.Builder(BlocksManager.this).create();
                View inflate = getLayoutInflater().inflate(2131427809, (ViewGroup) null);
                create.setView(inflate);
                create.requestWindowFeature(1);
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = (EditText) inflate.findViewById(2131232525);
                editText.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
                final EditText editText2 = (EditText) inflate.findViewById(2131232526);
                editText2.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
                ((TextInputLayout) inflate.findViewById(2131232527)).setVisibility(8);
                ((TextView) inflate.findViewById(2131232528)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
                        hashMap.put("palletteDir", editText.getText().toString());
                        hashMap.put("blockDir", editText2.getText().toString());
                        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap));
                        _readSettings();
                        _refresh_list();
                        create.dismiss();
                    }
                });
                ((TextView) inflate.findViewById(2131232351)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        create.dismiss();
                    }
                });
                ((TextView) inflate.findViewById(2131232530)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
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
        this._fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insert_n = -1.0d;
                final AlertDialog create = new AlertDialog.Builder(BlocksManager.this).create();
                View inflate = getLayoutInflater().inflate(2131427810, null);
                create.setView(inflate);
                create.requestWindowFeature(1);
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = (EditText) inflate.findViewById(2131231561);
                final EditText editText2 = (EditText) inflate.findViewById(2131230904);
                ((ImageView) inflate.findViewById(2131232352)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        View inflate = getLayoutInflater().inflate(2131427373, (ViewGroup) null);
                        Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                        zx.a(new PCP(BlocksManager.this, editText2, create));
                        zx.setAnimationStyle(2130771968);
                        zx.showAtLocation(inflate, 17, 0, 0);
                        create.hide();
                    }
                });
                ((TextView) inflate.findViewById(2131232528)).setOnClickListener(new View.OnClickListener() {
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
                ((TextView) inflate.findViewById(2131232351)).setOnClickListener(new View.OnClickListener() {
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
        _recycleBin(this.card2);
        this.insert_n = -1.0d;
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
            HashMap<String, Object> hashMap2 = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
            if (hashMap2.containsKey("palletteDir")) {
                this.pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
            } else {
                hashMap2.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");
                this.pallet_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("palletteDir").toString());
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(hashMap2));
            }
            if (hashMap2.containsKey("blockDir")) {
                this.blocks_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("blockDir").toString());
                this.all_blocks_list.clear();
                if (FileUtil.isExistFile(this.blocks_dir) && !FileUtil.readFile(this.blocks_dir).equals("")) {
                    try {
                        this.all_blocks_list = (ArrayList<HashMap<String, Object>>) new Gson().fromJson(FileUtil.readFile(this.blocks_dir), Helper.TYPE_MAP_LIST);
                    } catch (Exception ignored) {
                    }
                }
            } else {
                hashMap2.put("blockDir", "/.sketchware/resources/block/My Block/block.json");
                this.blocks_dir = FileUtil.getExternalStorageDir().concat(hashMap2.get("blockDir").toString());
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
            if (!FileUtil.isExistFile(this.pallet_dir) || FileUtil.readFile(this.pallet_dir).equals("")) {
                this.pallet_listmap.clear();
                this.listview1.setAdapter((ListAdapter) new Listview1Adapter(this.pallet_listmap));
                ((BaseAdapter) this.listview1.getAdapter()).notifyDataSetChanged();
            } else {
                Parcelable onSaveInstanceState = this.listview1.onSaveInstanceState();
                this.pallet_listmap = (ArrayList<HashMap<String, Object>>) new Gson().fromJson(FileUtil.readFile(this.pallet_dir), Helper.TYPE_MAP_LIST);
                this.listview1.setAdapter((ListAdapter) new Listview1Adapter(this.pallet_listmap));
                ((BaseAdapter) this.listview1.getAdapter()).notifyDataSetChanged();
                this.listview1.onRestoreInstanceState(onSaveInstanceState);
            }
            this.card2_sub.setText("Blocks: ".concat(String.valueOf((long) _getN(-1.0d))));
        } catch (Exception ignored) {
        }
    }

    private void _remove_pallete(final double d) {
        this.dialog.setTitle(this.pallet_listmap.get((int) d).get("name").toString());
        this.dialog.setMessage("Remove all blocks related to this palette?");
        this.dialog.setNeutralButton("Remove permanently", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pallet_listmap.remove((int) d);
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(d + 9.0d);
                _readSettings();
                _refresh_list();
            }
        });
        this.dialog.setNegativeButton("Cancel", null);
        this.dialog.setPositiveButton("Move to recycle bin", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                _moveRelatedBlocksToRecycleBin(d + 9.0d);
                pallet_listmap.remove((int) d);
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(d + 9.0d);
                _readSettings();
                _refresh_list();
            }
        });
        this.dialog.create().show();
    }

    private double _getN(double d) {
        int i = 0;
        for (int i2 = 0; i2 < this.all_blocks_list.size(); i2++) {
            if (this.all_blocks_list.get(i2).get("palette").toString().equals(String.valueOf((long) d))) {
                i++;
            }
        }
        return (double) i;
    }

    private void _createPallette(String str, String str2) {
        this.map.put("name", str);
        this.map.put("color", str2);
        if (this.insert_n == -1.0d) {
            this.pallet_listmap.add(this.map);
            FileUtil.writeFile(this.pallet_dir, new Gson().toJson(this.pallet_listmap));
            _readSettings();
            _refresh_list();
            this.insert_n = -1.0d;
            return;
        }
        this.pallet_listmap.add((int) this.insert_n, this.map);
        FileUtil.writeFile(this.pallet_dir, new Gson().toJson(this.pallet_listmap));
        _readSettings();
        _refresh_list();
        _insertBlocksAt(this.insert_n + 9.0d);
        this.insert_n = -1.0d;
    }

    private void _showEditDial(final double d, String str, String str2) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427810, (ViewGroup) null);
        create.setView(inflate);
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = (EditText) inflate.findViewById(2131231561);
        editText.setText(str);
        final EditText editText2 = (EditText) inflate.findViewById(2131230904);
        editText2.setText(str2);
        ((TextView) inflate.findViewById(2131231837)).setText("Edit palette");
        TextView textView = (TextView) inflate.findViewById(2131232351);
        ((ImageView) inflate.findViewById(2131232352)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(2131427373, (ViewGroup) null);
                Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                zx.a(new PCP(BlocksManager.this, editText2, create));
                zx.setAnimationStyle(2130771968);
                zx.showAtLocation(inflate, 17, 0, 0);
                create.hide();
            }
        });
        ((TextView) inflate.findViewById(2131232528)).setOnClickListener(new View.OnClickListener() {
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
        this.pallet_listmap.get((int) d).put("name", str);
        this.pallet_listmap.get((int) d).put("color", str2);
        FileUtil.writeFile(this.pallet_dir, new Gson().toJson(this.pallet_listmap));
        _readSettings();
        _refresh_list();
    }

    private void _MoveUp(double d) {
        if (d > 0.0d) {
            Collections.swap(this.pallet_listmap, (int) d, (int) (-1.0d + d));
            Parcelable onSaveInstanceState = this.listview1.onSaveInstanceState();
            FileUtil.writeFile(this.pallet_dir, new Gson().toJson(this.pallet_listmap));
            _swapRelatedBlocks(9.0d + d, 8.0d + d);
            _readSettings();
            _refresh_list();
            this.listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _recycleBin(View view) {
        _a(view);
        this.card2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", "-1");
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            }
        });
        this.card2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                emptyDialog.setTitle("Recycle bin");
                emptyDialog.setMessage("Are you sure you want to empty the recycle bin? Blocks inside will be deleted PERMANENTLY, you CAN NOT recover them!");
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
        this.insert_n = d;
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427810, (ViewGroup) null);
        create.setView(inflate);
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = (EditText) inflate.findViewById(2131231561);
        final EditText editText2 = (EditText) inflate.findViewById(2131230904);
        ((ImageView) inflate.findViewById(2131232352)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(2131427373, (ViewGroup) null);
                Zx zx = new Zx(inflate, BlocksManager.this, 0, true, false);
                zx.a(new PCP(BlocksManager.this, editText2, create));
                zx.setAnimationStyle(2130771968);
                zx.showAtLocation(inflate, 17, 0, 0);
                create.hide();
            }
        });
        ((TextView) inflate.findViewById(2131232528)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Color.parseColor(editText2.getText().toString());
                    _createPallette(editText.getText().toString(), editText2.getText().toString());
                    create.dismiss();
                } catch (Exception ignored) {
                }
            }
        });
        ((TextView) inflate.findViewById(2131232351)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        create.show();
    }

    private void _moveDown(double d) {
        if (d < ((double) (this.pallet_listmap.size() - 1))) {
            Collections.swap(this.pallet_listmap, (int) d, (int) (1.0d + d));
            Parcelable onSaveInstanceState = this.listview1.onSaveInstanceState();
            FileUtil.writeFile(this.pallet_dir, new Gson().toJson(this.pallet_listmap));
            _swapRelatedBlocks(9.0d + d, 10.0d + d);
            _readSettings();
            _refresh_list();
            this.listview1.onRestoreInstanceState(onSaveInstanceState);
        }
    }

    private void _removeRelatedBlocks(double d) {
        this.temp_list.clear();
        this.m = new HashMap<>();
        for (int i = 0; i < this.all_blocks_list.size(); i++) {
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) != d) {
                if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) > d) {
                    this.m = this.all_blocks_list.get(i);
                    this.m.put("palette", String.valueOf((long) (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) - 1.0d)));
                    this.temp_list.add(this.m);
                } else {
                    this.temp_list.add(this.all_blocks_list.get(i));
                }
            }
        }
        FileUtil.writeFile(this.blocks_dir, new Gson().toJson(this.temp_list));
        _readSettings();
        _refresh_list();
    }

    private void _swapRelatedBlocks(double d, double d2) {
        for (int i = 0; i < this.all_blocks_list.size(); i++) {
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) == d) {
                this.all_blocks_list.get(i).put("palette", "123456789");
            }
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) == d2) {
                this.all_blocks_list.get(i).put("palette", String.valueOf((long) d));
            }
        }
        for (int i2 = 0; i2 < this.all_blocks_list.size(); i2++) {
            if (Double.parseDouble(this.all_blocks_list.get(i2).get("palette").toString()) == 1.23456789E8d) {
                this.all_blocks_list.get(i2).put("palette", String.valueOf((long) d2));
            }
        }
        FileUtil.writeFile(this.blocks_dir, new Gson().toJson(this.all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _insertBlocksAt(double d) {
        for (int i = 0; i < this.all_blocks_list.size(); i++) {
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) > d || Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) == d) {
                this.all_blocks_list.get(i).put("palette", String.valueOf((long) (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) + 1.0d)));
            }
        }
        FileUtil.writeFile(this.blocks_dir, new Gson().toJson(this.all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _moveRelatedBlocksToRecycleBin(double d) {
        this.m = new HashMap<>();
        for (int i = 0; i < this.all_blocks_list.size(); i++) {
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) == d) {
                this.all_blocks_list.get(i).put("palette", "-1");
            }
        }
        FileUtil.writeFile(this.blocks_dir, new Gson().toJson(this.all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _emptyRecyclebin() {
        this.temp_list.clear();
        this.m = new HashMap<>();
        for (int i = 0; i < this.all_blocks_list.size(); i++) {
            if (Double.parseDouble(this.all_blocks_list.get(i).get("palette").toString()) != -1.0d) {
                this.temp_list.add(this.all_blocks_list.get(i));
            }
        }
        FileUtil.writeFile(this.blocks_dir, new Gson().toJson(this.temp_list));
        _readSettings();
        _refresh_list();
    }

    public class Listview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> arrayList) {
            this._data = arrayList;
        }

        @Override
        public int getCount() {
            return this._data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return this._data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427811, null);
            }
            final LinearLayout linearLayout = (LinearLayout) convertView.findViewById(2131232515);
            ((TextView) convertView.findViewById(2131231837)).setText(pallet_listmap.get(position).get("name").toString());
            ((TextView) convertView.findViewById(2131232541)).setText("Blocks: ".concat(String.valueOf((long) _getN((double) (position + 9)))));
            card2_sub.setText("Blocks: ".concat(String.valueOf((long) _getN(-1.0d))));
            ((LinearLayout) convertView.findViewById(2131230904)).setBackgroundColor(Color.parseColor((String) this._data.get(position).get("color")));
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
                    intent.putExtra("position", String.valueOf((long) (position + 9)));
                    intent.putExtra("dirB", blocks_dir);
                    intent.putExtra("dirP", pallet_dir);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}