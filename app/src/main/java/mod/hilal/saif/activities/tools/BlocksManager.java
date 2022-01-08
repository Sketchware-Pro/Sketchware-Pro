package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Zx;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.lib.PCP;

public class BlocksManager extends AppCompatActivity {


    private FloatingActionButton _fab;
    private String blocks_dir = "";
    private String pallet_dir = "";
    private final HashMap<String, Object> map = new HashMap<>();
    private double insert_n = 0;
    private HashMap<String, Object> m = new HashMap<>();

    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> temp_list = new ArrayList<>();

    private LinearLayout background;
    private LinearLayout toolbar;
    //private CardView linear6;
    //private TextView textview6;
    private ListView listview1;
    private ImageView back_icon;
    private TextView page_title;
    private ImageView arrange_icon;
    private LinearLayout card2;
    //private LinearLayout linear8;
    //private LinearLayout linear9;
    private ImageView card2_icon;
    //private TextView textview4;
    private TextView card2_sub;

    private final Intent intent = new Intent();
    private AlertDialog.Builder dia;
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder emptyDialog;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(2131427808);
        initialize(_savedInstanceState);
        initializeLogic();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        _fab = findViewById(2131232516);

        background = findViewById(2131232515);
        toolbar = findViewById(2131232524);
        //linear6 = (CardView) findViewById(R.id.linear6);
        //textview6 = (TextView) findViewById(R.id.textview6);
        listview1 = findViewById(2131232520);
        back_icon = findViewById(2131232519);
        page_title = findViewById(2131232521);
        arrange_icon = findViewById(2131232518);
        card2 = findViewById(2131232517);
        //linear8 = (LinearLayout) findViewById(R.id.linear8);
        //linear9 = (LinearLayout) findViewById(R.id.linear9);
        card2_icon = findViewById(2131232523);
        //textview4 = (TextView) findViewById(R.id.textview4);
        card2_sub = findViewById(2131232522);
        dia = new AlertDialog.Builder(this);
        dialog = new AlertDialog.Builder(this);
        emptyDialog = new AlertDialog.Builder(this);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        arrange_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                {
                    final AlertDialog dialog = new AlertDialog.Builder(BlocksManager.this).create();
                    LayoutInflater inflater = getLayoutInflater();
                    final View convertView = inflater.inflate(2131427809, null);
                    dialog.setView(convertView);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
                    final EditText pallet = convertView.findViewById(2131232525);
                    pallet.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
                    final EditText block = convertView.findViewById(2131232526);
                    block.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
                    final TextInputLayout extra = convertView.findViewById(2131232527);
                    extra.setVisibility(View.GONE);
                    final TextView save = convertView.findViewById(2131232528);
                    final TextView cancel = convertView.findViewById(2131232529);
                    final TextView de = convertView.findViewById(2131232530);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            HashMap<String, Object> _t = new HashMap<>();

                            _t = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), new TypeToken<HashMap<String, Object>>() {
                            }.getType());

                            _t.put("palletteDir", pallet.getText().toString());

                            _t.put("blockDir", block.getText().toString());

                            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

                            _readSettings();

                            _refresh_list();

                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            dialog.dismiss();
                        }
                    });
                    de.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            HashMap<String, Object> _t = new HashMap<>();

                            _t = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), new TypeToken<HashMap<String, Object>>() {
                            }.getType());

                            _t.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");

                            _t.put("blockDir", "/.sketchware/resources/block/My Block/block.json");

                            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

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
                    final View convertView = inflater.inflate(2131427810, null);
                    dialog.setView(convertView);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
                    final EditText name = convertView.findViewById(2131232534);
                    final EditText colour = convertView.findViewById(2131232535);
                    final TextView save = convertView.findViewById(2131232536);
                    final TextView cancel = convertView.findViewById(2131232537);
                    final ImageView select = convertView.findViewById(2131232538);


                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            LayoutInflater inf = getLayoutInflater();
                            final View a = inf.inflate(2131427373, null);
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
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override

    public void onResume() {

        super.onResume();

        _readSettings();

        _refresh_list();

    }

    private void _a(final View _view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        // gradientDrawable.setCornerRadii(new float[]{(float) rad, (float) rad, (float) rad/2, (float) rad/2, (float) rad, (float) rad, (float) rad/2, (float) rad/2});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        if (Build.VERSION.SDK_INT >= 21) {
            //    view.setElevation((float) shadow);
            _view.setBackground(rippleDrawable);
            _view.setClickable(true);
            _view.setFocusable(true);
        }
    }


    private void _readSettings() {
        if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"))) {

            if (!FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")).equals("")) {

                HashMap<String, Object> _t = new HashMap<>();

                _t = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), new TypeToken<HashMap<String, Object>>() {
                }.getType());

                if (_t.containsKey("palletteDir")) {

                    pallet_dir = FileUtil.getExternalStorageDir().concat(_t.get("palletteDir").toString());

                } else {

                    _t.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");

                    pallet_dir = FileUtil.getExternalStorageDir().concat(_t.get("palletteDir").toString());

                    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

                }

                if (_t.containsKey("blockDir")) {

                    blocks_dir = FileUtil.getExternalStorageDir().concat(_t.get("blockDir").toString());

                    all_blocks_list.clear();

                    if (FileUtil.isExistFile(blocks_dir) && !FileUtil.readFile(blocks_dir).equals("")) {

                        try {

                            all_blocks_list = new Gson().fromJson(FileUtil.readFile(blocks_dir), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                            }.getType());
                        } catch (Exception e) {


                        }

                    }

                } else {

                    _t.put("blockDir", "/.sketchware/resources/block/My Block/block.json");

                    blocks_dir = FileUtil.getExternalStorageDir().concat(_t.get("blockDir").toString());

                    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

                }

            } else {

                HashMap<String, Object> _t = new HashMap<>();

                _t.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");

                _t.put("blockDir", "/.sketchware/resources/block/My Block/block.json");

                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

                _readSettings();

            }

        } else {

            HashMap<String, Object> _t = new HashMap<>();

            _t.put("palletteDir", "/.sketchware/resources/block/My Block/palette.json");

            _t.put("blockDir", "/.sketchware/resources/block/My Block/block.json");

            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(_t));

            _readSettings();

        }
    }


    private void _refresh_list() {
        try {

            if (FileUtil.isExistFile(pallet_dir) && !FileUtil.readFile(pallet_dir).equals("")) {
                {
                    android.os.Parcelable prcl;
                    prcl = listview1.onSaveInstanceState();
                    pallet_listmap = new Gson().fromJson(FileUtil.readFile(pallet_dir), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    listview1.setAdapter(new Listview1Adapter(pallet_listmap));
                    ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                    listview1.onRestoreInstanceState(prcl);
                }
            } else {
                pallet_listmap.clear();
                listview1.setAdapter(new Listview1Adapter(pallet_listmap));
                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
            }
            card2_sub.setText("blocks : ".concat(String.valueOf((long) (_getN(-1)))));
        } catch (Exception e) {


        }
    }


    private void _remove_pallete(final double _p) {
        dialog.setTitle(pallet_listmap.get((int) _p).get("name").toString());
        dialog.setMessage("Do you want to remove all blocks related to this pallette??");
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                pallet_listmap.remove((int) (_p));
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(_p + 9);
                _readSettings();
                _refresh_list();
            }
        });
        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {

            }
        });
        dialog.setNeutralButton("recycle bin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _moveRelatedBlocksToRecycleBin(_p + 9);
                pallet_listmap.remove((int) (_p));
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _removeRelatedBlocks(_p + 9);
                _readSettings();
                _refresh_list();
            }
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
            final View convertView = inflater.inflate(2131427810, null);
            dialog.setView(convertView);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
            final EditText name = convertView.findViewById(2131232534);
            name.setText(_name);
            final EditText colour = convertView.findViewById(2131232535);
            colour.setText(_c);
            final TextView title = convertView.findViewById(2131232539);
            title.setText("Edit Pallette");
            final TextView save = convertView.findViewById(2131232536);
            final TextView cancel = convertView.findViewById(2131232537);
            final ImageView select = convertView.findViewById(2131232538);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    LayoutInflater inf = getLayoutInflater();
                    final View a = inf.inflate(2131427373, null);
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
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    dialog.dismiss();
                }
            });
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
                emptyDialog.setMessage("do you want to empty recycle bin?");
                emptyDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _emptyRecyclebin();
                    }
                });
                emptyDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {

                    }
                });
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
            final View convertView = inflater.inflate(2131427810, null);
            dialog.setView(convertView);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
            final EditText name = convertView.findViewById(2131232534);
            final EditText colour = convertView.findViewById(2131232535);
            final TextView save = convertView.findViewById(2131232536);
            final TextView cancel = convertView.findViewById(2131232537);
            final ImageView select = convertView.findViewById(2131232538);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    LayoutInflater inf = getLayoutInflater();
                    final View a = inf.inflate(2131427373, null);
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
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    dialog.dismiss();
                }
            });
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
                _view = _inflater.inflate(2131427811, null);
            }

            //final androidx.cardview.widget.CardView cardview = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview);
            final LinearLayout linear2 = _view.findViewById(2131232543);
            //final androidx.cardview.widget.CardView cardview2 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview2);
            //final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
            final LinearLayout colour = _view.findViewById(2131232542);
            final TextView title = _view.findViewById(2131232540);
            final TextView sub = _view.findViewById(2131232541);

            title.setText(pallet_listmap.get(_position).get("name").toString());
            sub.setText("blocks : ".concat(String.valueOf((long) (_getN(_position + 9)))));
            card2_sub.setText("blocks : ".concat(String.valueOf((long) (_getN(-1)))));
            colour.setBackgroundColor(Color.parseColor((String) _data.get(_position).get("color")));
            _a(linear2);
            linear2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View _view) {
                    {
                        PopupMenu popup = new PopupMenu(BlocksManager.this, linear2);
                        Menu menu = popup.getMenu();
                        menu.add("move up");
                        menu.add("move down");
                        menu.add("edit");
                        menu.add("delete");
                        menu.add("insert");
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getTitle().toString()) {
                                    case "edit": {

                                        _showEditDial(_position, pallet_listmap.get(_position).get("name").toString(), pallet_listmap.get(_position).get("color").toString());

                                        break;
                                    }
                                    case "delete": {

                                        _remove_pallete(_position);

                                        break;
                                    }
                                    case "move up": {

                                        _MoveUp(_position);

                                        break;
                                    }
                                    case "move down": {

                                        _moveDown(_position);

                                        break;
                                    }
                                    case "insert": {

                                        _insert_pallete(_position);

                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        popup.show();
                    }
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
