package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Zx;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;

public class BlocksManager extends AppCompatActivity {

    private String blocks_dir = "";
    private String pallet_dir = "";
    private final HashMap<String, Object> map = new HashMap<>();
    private double insert_n = 0;
    private HashMap<String, Object> m = new HashMap<>();

    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> temp_list = new ArrayList<>();

    private ListView listview1;
    private LinearLayout card2;
    private TextView card2_sub;

    private final Intent intent = new Intent();
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder emptyDialog;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
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

        arrange_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                {
                    final AlertDialog dialog = new AlertDialog.Builder(BlocksManager.this).create();
                    LayoutInflater inflater = getLayoutInflater();
                    final View convertView = inflater.inflate(Resources.layout.settings_popup, null);
                    dialog.setView(convertView);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
                    final EditText pallet = convertView.findViewById(Resources.id.pallet_dir);
                    pallet.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
                    final EditText block = convertView.findViewById(Resources.id.blocks_dir);
                    block.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
                    final TextInputLayout extra = convertView.findViewById(Resources.id.extra_input_layout);
                    extra.setVisibility(View.GONE);
                    final TextView save = convertView.findViewById(Resources.id.save);
                    final TextView cancel = convertView.findViewById(Resources.id.cancel);
                    final TextView de = convertView.findViewById(Resources.id.defaults);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                                    pallet.getText().toString());
                            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                                    block.getText().toString());

                            _readSettings();
                            _refresh_list();
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
                    de.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                                    "/.sketchware/resources/block/My Block/palette.json");
                            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                                    "/.sketchware/resources/block/My Block/block.json");

                            _readSettings();
                            _refresh_list();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                insert_n = -1;
                {
                    final AlertDialog dialog = new AlertDialog.Builder(BlocksManager.this).create();
                    LayoutInflater inflater = getLayoutInflater();
                    final View convertView = inflater.inflate(Resources.layout.add_new_pallete_customview, null);
                    dialog.setView(convertView);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
                    final EditText name = convertView.findViewById(Resources.id.name);
                    final EditText colour = convertView.findViewById(Resources.id.color);
                    final TextView save = convertView.findViewById(Resources.id.save);
                    final TextView cancel = convertView.findViewById(Resources.id.cancel);
                    final ImageView select = convertView.findViewById(Resources.id.select);

                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            LayoutInflater inf = getLayoutInflater();
                            final View a = inf.inflate(Resources.layout.color_picker, null);
                            final Zx zx = new Zx(a, BlocksManager.this, 0, true, false);
                            zx.a(new PCP(BlocksManager.this, colour, dialog));
                            zx.setAnimationStyle(2130771968);
                            zx.showAtLocation(a, 17, 0, 0);
                            dialog.hide();
                        }
                    });

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            try {
                                int c = Color.parseColor(colour.getText().toString());
                                _createPallette(name.getText().toString(), colour.getText().toString());
                                insert_n = -1;
                                dialog.dismiss();
                            } catch (Exception e) {
                            }
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            insert_n = -1;
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
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

    private void _a(final View _view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        if (Build.VERSION.SDK_INT >= 21) {
            _view.setBackground(rippleDrawable);
            _view.setClickable(true);
            _view.setFocusable(true);
        }
    }

    private void _readSettings() {
        pallet_dir = ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                "/.sketchware/resources/block/My Block/palette.json");
        blocks_dir = ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH,
                "/.sketchware/resources/block/My Block/block.json");
    }

    private void _refresh_list() {
        try {
            if (FileUtil.isExistFile(pallet_dir) && !FileUtil.readFile(pallet_dir).equals("")) {
                {
                    android.os.Parcelable prcl;
                    prcl = listview1.onSaveInstanceState();
                    pallet_listmap = new Gson().fromJson(FileUtil.readFile(pallet_dir), Helper.TYPE_MAP_LIST);
                    listview1.setAdapter(new Listview1Adapter(pallet_listmap));
                    ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                    listview1.onRestoreInstanceState(prcl);
                }
            } else {
                pallet_listmap.clear();
                listview1.setAdapter(new Listview1Adapter(pallet_listmap));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
            }
            card2_sub.setText("Blocks: " + (long) (_getN(-1)));
        } catch (Exception e) {
        }
    }

    private void _remove_pallete(final double _p) {
        dialog.setTitle(pallet_listmap.get((int) _p).get("name").toString());
        dialog.setMessage("Remove all blocks related to this palette?");
        dialog.setPositiveButton("Remove permanently", (dialog, which) -> {
            pallet_listmap.remove((int) (_p));
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _removeRelatedBlocks(_p + 9);
            _readSettings();
            _refresh_list();
        });
        dialog.setNegativeButton(Resources.string.common_word_cancel, null);
        dialog.setNeutralButton("Move to recycle bin", (dialog, which) -> {
            _moveRelatedBlocksToRecycleBin(_p + 9);
            pallet_listmap.remove((int) (_p));
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _removeRelatedBlocks(_p + 9);
            _readSettings();
            _refresh_list();
        });
        dialog.create().show();
    }

    private double _getN(final double _p) {
        int n = 0;
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (all_blocks_list.get(i).get("palette").toString().equals(String.valueOf((long) (_p)))) {
                n++;
            }
        }
        return (n);
    }

    private void _createPallette(final String _name, final String _colour) {
        map.put("name", _name);
        map.put("color", _colour);
        if (insert_n == -1) {
            pallet_listmap.add(map);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _readSettings();
            _refresh_list();
            insert_n = -1;
        } else {
            pallet_listmap.add((int) insert_n, map);
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _readSettings();
            _refresh_list();
            _insertBlocksAt(insert_n + 9);
            insert_n = -1;
        }
    }

    private void _showEditDial(final double _p, final String _name, final String _c) {
        {
            final AlertDialog dialog = new AlertDialog.Builder(BlocksManager.this).create();
            LayoutInflater inflater = getLayoutInflater();
            final View convertView = inflater.inflate(Resources.layout.add_new_pallete_customview, null);
            dialog.setView(convertView);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
            final EditText name = convertView.findViewById(Resources.id.name);
            name.setText(_name);
            final EditText colour = convertView.findViewById(Resources.id.color);
            colour.setText(_c);
            final TextView title = convertView.findViewById(Resources.id.title);
            title.setText("Edit palette");
            final TextView save = convertView.findViewById(Resources.id.save);
            final TextView cancel = convertView.findViewById(Resources.id.cancel);
            final ImageView select = convertView.findViewById(Resources.id.select);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    LayoutInflater inf = getLayoutInflater();
                    final View a = inf.inflate(Resources.layout.color_picker, null);
                    final Zx zx = new Zx(a, BlocksManager.this, 0, true, false);
                    zx.a(new PCP(BlocksManager.this, colour, dialog));
                    zx.setAnimationStyle(2130771968);
                    zx.showAtLocation(a, 17, 0, 0);
                    dialog.hide();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    try {
                        int c = Color.parseColor(colour.getText().toString());
                        _editPallete(_p, name.getText().toString(), colour.getText().toString());
                        dialog.dismiss();
                    } catch (Exception e) {
                    }
                }
            });
            cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
            dialog.show();
        }
    }

    private void _editPallete(final double _p, final String _n, final String _c) {
        pallet_listmap.get((int) _p).put("name", _n);
        pallet_listmap.get((int) _p).put("color", _c);
        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
        _readSettings();
        _refresh_list();
    }

    private void _MoveUp(final double _p) {
        if (_p > 0) {
            Collections.swap(pallet_listmap, (int) (_p), (int) (_p + -1));
            {
                android.os.Parcelable prcl;
                prcl = listview1.onSaveInstanceState();
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _swapRelatedBlocks(_p + 9, _p + 8);
                _readSettings();
                _refresh_list();
                listview1.onRestoreInstanceState(prcl);
            }
        }
    }

    private void _recycleBin(final View _v) {
        _a(_v);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", "-1");
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            }
        });
        card2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View _view) {
                emptyDialog.setTitle("Recycle bin");
                emptyDialog.setMessage("Are you sure you want to empty the recycle bin? " +
                        "Blocks inside will be deleted PERMANENTLY, you CANNOT recover them!");
                emptyDialog.setPositiveButton("Empty", (dialog, which) -> _emptyRecyclebin());
                emptyDialog.setNegativeButton(Resources.string.common_word_cancel, null);
                emptyDialog.create().show();
                return true;
            }
        });
    }

    private void _insert_pallete(final double _p) {
        insert_n = _p;
        {
            final AlertDialog dialog = new AlertDialog.Builder(BlocksManager.this).create();
            LayoutInflater inflater = getLayoutInflater();
            final View convertView = inflater.inflate(Resources.layout.add_new_pallete_customview, null);
            dialog.setView(convertView);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
            final EditText name = convertView.findViewById(Resources.id.name);
            final EditText colour = convertView.findViewById(Resources.id.color);
            final TextView save = convertView.findViewById(Resources.id.save);
            final TextView cancel = convertView.findViewById(Resources.id.cancel);
            final ImageView select = convertView.findViewById(Resources.id.select);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    LayoutInflater inf = getLayoutInflater();
                    final View a = inf.inflate(Resources.layout.color_picker, null);
                    final Zx zx = new Zx(a, BlocksManager.this, 0, true, false);
                    zx.a(new PCP(BlocksManager.this, colour, dialog));
                    zx.setAnimationStyle(2130771968);
                    zx.showAtLocation(a, 17, 0, 0);
                    dialog.hide();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    try {
                        int c = Color.parseColor(colour.getText().toString());
                        _createPallette(name.getText().toString(), colour.getText().toString());
                        dialog.dismiss();
                    } catch (Exception e) {
                    }
                }
            });
            cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
            dialog.show();
        }
    }

    private void _moveDown(final double _p) {
        if (_p < (pallet_listmap.size() - 1)) {
            Collections.swap(pallet_listmap, (int) (_p), (int) (_p + 1));
            {
                android.os.Parcelable prcl;
                prcl = listview1.onSaveInstanceState();
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _swapRelatedBlocks(_p + 9, _p + 10);
                _readSettings();
                _refresh_list();
                listview1.onRestoreInstanceState(prcl);
            }
        }
    }

    private void _removeRelatedBlocks(final double _p) {
        temp_list.clear();
        m = new HashMap<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (!(Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _p)) {
                if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) > _p) {
                    m = all_blocks_list.get(i);
                    m.put("palette", String.valueOf((long) (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) - 1)));
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

    private void _swapRelatedBlocks(final double _f, final double _s) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _f) {
                all_blocks_list.get(i).put("palette", "123456789");
            }
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _s) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (_f)));
            }
        }

        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == 123456789) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (_s)));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _insertBlocksAt(final double _p) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if ((Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) > _p) || (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _p)) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) + 1)));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        _readSettings();
        _refresh_list();
    }

    private void _moveRelatedBlocksToRecycleBin(final double _p) {
        m = new HashMap<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _p) {
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
            if (!(Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == -1)) {
                temp_list.add(all_blocks_list.get(i));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(temp_list));
        _readSettings();
        _refresh_list();
    }

    public class Listview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _v, ViewGroup _container) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _view = _v;
            if (_view == null) {
                _view = _inflater.inflate(Resources.layout.pallet_customview, null);
            }

            final LinearLayout linear2 = _view.findViewById(Resources.id.background);
            final LinearLayout colour = _view.findViewById(Resources.id.color);
            final TextView title = _view.findViewById(Resources.id.title);
            final TextView sub = _view.findViewById(Resources.id.sub);

            title.setText(pallet_listmap.get(_position).get("name").toString());
            sub.setText("Blocks: " + (long) (_getN(_position + 9)));
            card2_sub.setText("Blocks: " + (long) (_getN(-1)));
            colour.setBackgroundColor(Color.parseColor((String) _data.get(_position).get("color")));
            _a(linear2);
            linear2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View _view) {
                    final String moveUp = "Move up";
                    final String moveDown = "move down";
                    final String edit = "edit";
                    final String delete = "Delete";
                    final String insert = "Insert";

                    PopupMenu popup = new PopupMenu(BlocksManager.this, linear2);
                    Menu menu = popup.getMenu();
                    menu.add(moveUp);
                    menu.add(moveDown);
                    menu.add(edit);
                    menu.add(delete);
                    menu.add(insert);
                    popup.setOnMenuItemClickListener(item -> {
                        switch (item.getTitle().toString()) {
                            case edit:
                                _showEditDial(_position, pallet_listmap.get(_position).get("name").toString(),
                                        pallet_listmap.get(_position).get("color").toString());
                                break;

                            case delete:
                                _remove_pallete(_position);
                                break;

                            case moveUp:
                                _MoveUp(_position);
                                break;

                            case moveDown:
                                _moveDown(_position);
                                break;

                            case insert:
                                _insert_pallete(_position);
                                break;

                            default:
                        }
                        return true;
                    });
                    popup.show();

                    return true;
                }
            });

            linear2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    intent.setClass(getApplicationContext(), BlocksManagerDetailsActivity.class);
                    intent.putExtra("position", String.valueOf((long) (_position + 9)));
                    intent.putExtra("dirB", blocks_dir);
                    intent.putExtra("dirP", pallet_dir);
                    startActivity(intent);
                }
            });

            return _view;
        }
    }
}
