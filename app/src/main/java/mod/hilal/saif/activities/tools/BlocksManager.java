package mod.hilal.saif.activities.tools;

import static mod.SketchwareUtil.dpToPx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import a.a.a.Zx;
import a.a.a.aB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;

public class BlocksManager extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private String blocks_dir = "";
    private String pallet_dir = "";
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private ListView listview1;
    private LinearLayout card2;
    private TextView card2_sub;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.blocks_manager);
        initialize();
        initializeLogic();
    }

    @Override
    public void onStop() {
        super.onStop();

        BlockLoader.refresh();
    }

    private void initialize() {
        FloatingActionButton _fab = findViewById(R.id.fab);
        listview1 = findViewById(R.id.list_pallete);
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView settings = findViewById(R.id.ig_toolbar_load_file);
        card2 = findViewById(R.id.recycle_bin);
        card2_sub = findViewById(R.id.recycle_sub);

        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back);
        title.setText("Block manager");
        settings.setVisibility(View.VISIBLE);
        settings.setImageResource(R.drawable.settings_96_white);
        Helper.applyRippleToToolbarView(settings);
        settings.setOnClickListener(v -> {
            aB dialog = new aB(this);
            dialog.a(R.drawable.ic_folder_48dp);
            dialog.b("Block configuration");

            LinearLayout.LayoutParams defaultParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout customView = new LinearLayout(this);
            customView.setLayoutParams(defaultParams);
            customView.setOrientation(LinearLayout.VERTICAL);

            TextInputLayout tilPalettesPath = new TextInputLayout(this);
            tilPalettesPath.setLayoutParams(defaultParams);
            tilPalettesPath.setOrientation(LinearLayout.VERTICAL);
            tilPalettesPath.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
            tilPalettesPath.setHint("JSON file with palettes");
            customView.addView(tilPalettesPath);

            EditText palettesPath = new EditText(this);
            palettesPath.setLayoutParams(defaultParams);
            palettesPath.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            palettesPath.setTextSize(14);
            palettesPath.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
            tilPalettesPath.addView(palettesPath);

            TextInputLayout tilBlocksPath = new TextInputLayout(this);
            tilBlocksPath.setLayoutParams(defaultParams);
            tilBlocksPath.setOrientation(LinearLayout.VERTICAL);
            tilBlocksPath.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
            tilBlocksPath.setHint("JSON file with blocks");
            customView.addView(tilBlocksPath);

            EditText blocksPath = new EditText(this);
            blocksPath.setLayoutParams(defaultParams);
            blocksPath.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            blocksPath.setTextSize(14);
            blocksPath.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));
            tilBlocksPath.addView(blocksPath);

            dialog.a(customView);
            dialog.b(Helper.getResString(R.string.common_word_save), view -> {
                ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                        palettesPath.getText().toString());
                ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH,
                        blocksPath.getText().toString());

                _readSettings();
                _refresh_list();
                dialog.dismiss();
            });
            dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
            dialog.configureDefaultButton("Defaults", view -> {
                ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                        ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH));
                ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH,
                        ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH));

                _readSettings();
                _refresh_list();
                dialog.dismiss();
            });
            dialog.show();
        });

        _fab.setOnClickListener(v -> showPaletteDialog(false, null, null, null, null));
    }

    private void initializeLogic() {
        _readSettings();
        _refresh_list();
        _recycleBin(card2);
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
        pallet_dir = FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                (String) ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH));
        blocks_dir = FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH,
                (String) ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH));

        if (FileUtil.isExistFile(blocks_dir)) {
            try {
                all_blocks_list = new Gson().fromJson(FileUtil.readFile(blocks_dir), Helper.TYPE_MAP_LIST);

                if (all_blocks_list != null) {
                    return;
                }
                // fall-through to shared handler
            } catch (JsonParseException e) {
                // fall-through to shared handler
            }

            SketchwareUtil.showFailedToParseJsonDialog(this, new File(blocks_dir), "Custom Blocks", v -> _readSettings());
        }
    }

    private void _refresh_list() {
        parsePaletteJson:
        {
            String paletteJsonContent;
            if (FileUtil.isExistFile(pallet_dir) && !(paletteJsonContent = FileUtil.readFile(pallet_dir)).equals("")) {
                try {
                    pallet_listmap = new Gson().fromJson(paletteJsonContent, Helper.TYPE_MAP_LIST);

                    if (pallet_listmap != null) {
                        break parsePaletteJson;
                    }
                    // fall-through to shared handler
                } catch (JsonParseException e) {
                    // fall-through to shared handler
                }

                SketchwareUtil.showFailedToParseJsonDialog(this, new File(pallet_dir), "Custom Block Palettes", v -> _refresh_list());
            }
            pallet_listmap = new ArrayList<>();
        }

        Parcelable savedState = listview1.onSaveInstanceState();
        listview1.setAdapter(new PaletteAdapter(pallet_listmap));
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        listview1.onRestoreInstanceState(savedState);

        card2_sub.setText("Blocks: " + (long) (_getN(-1)));
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

    private void _MoveUp(final double _p) {
        if (_p > 0) {
            Collections.swap(pallet_listmap, (int) (_p), (int) (_p + -1));

            Parcelable savedState = listview1.onSaveInstanceState();
            FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
            _swapRelatedBlocks(_p + 9, _p + 8);
            _readSettings();
            _refresh_list();
            listview1.onRestoreInstanceState(savedState);
        }
    }

    private void _recycleBin(final View _v) {
        _a(_v);
        card2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BlocksManagerDetailsActivity.class);
            intent.putExtra("position", "-1");
            intent.putExtra("dirB", blocks_dir);
            intent.putExtra("dirP", pallet_dir);
            startActivity(intent);
        });
        card2.setOnLongClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Recycle bin")
                    .setMessage("Are you sure you want to empty the recycle bin? " +
                            "Blocks inside will be deleted PERMANENTLY, you CANNOT recover them!")
                    .setPositiveButton("Empty", (dialog, which) -> _emptyRecyclebin())
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .show();
            return true;
        });
    }

    private void _moveDown(final double _p) {
        if (_p < (pallet_listmap.size() - 1)) {
            Collections.swap(pallet_listmap, (int) (_p), (int) (_p + 1));
            {
                Parcelable savedState = listview1.onSaveInstanceState();
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                _swapRelatedBlocks(_p + 9, _p + 10);
                _readSettings();
                _refresh_list();
                listview1.onRestoreInstanceState(savedState);
            }
        }
    }

    private void _removeRelatedBlocks(final double _p) {
        List<Map<String, Object>> newBlocks = new LinkedList<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (!(Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == _p)) {
                if (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) > _p) {
                    HashMap<String, Object> m = all_blocks_list.get(i);
                    m.put("palette", String.valueOf((long) (Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) - 1)));
                    newBlocks.add(m);
                } else {
                    newBlocks.add(all_blocks_list.get(i));
                }
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(newBlocks));
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
        List<Map<String, Object>> newBlocks = new LinkedList<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (!(Double.parseDouble(all_blocks_list.get(i).get("palette").toString()) == -1)) {
                newBlocks.add(all_blocks_list.get(i));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(newBlocks));
        _readSettings();
        _refresh_list();
    }

    private View.OnClickListener getSharedPaletteColorPickerShower(Dialog dialog, EditText storePickedResultIn) {
        return v -> {
            LayoutInflater inf = getLayoutInflater();
            final View a = inf.inflate(R.layout.color_picker, null);
            final Zx zx = new Zx(a, this, 0, true, false);
            zx.a(new PCP(this, storePickedResultIn, dialog));
            zx.setAnimationStyle(R.anim.abc_fade_in);
            zx.showAtLocation(a, Gravity.CENTER, 0, 0);
            dialog.hide();
        };
    }

    private void showPaletteDialog(boolean isEditing, Integer oldPosition, String oldName, String oldColor, Integer insertAtPosition) {
        aB dialog = new aB(this);
        dialog.a(R.drawable.positive_96);
        dialog.b(!isEditing ? "Create a new palette" : "Edit palette");

        LinearLayout customView = new LinearLayout(this);
        customView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        customView.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout name = new TextInputLayout(this);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
        name.setOrientation(LinearLayout.VERTICAL);
        name.setHint("Name");
        customView.addView(name);

        EditText nameEditText = new EditText(this);
        nameEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        nameEditText.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        nameEditText.setTextColor(0xff000000);
        nameEditText.setHintTextColor(0xff607d8b);
        nameEditText.setTextSize(14);
        if (isEditing) {
            nameEditText.setText(oldName);
        }
        name.addView(nameEditText);

        LinearLayout colorContainer = new LinearLayout(this);
        colorContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        colorContainer.setGravity(Gravity.CENTER | Gravity.LEFT);
        customView.addView(colorContainer);

        TextInputLayout color = new TextInputLayout(this);
        color.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        color.setOrientation(LinearLayout.VERTICAL);
        color.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
        color.setHint("Color");
        colorContainer.addView(color);

        EditText colorEditText = new EditText(this);
        colorEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        colorEditText.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        colorEditText.setTextColor(0xff000000);
        colorEditText.setHintTextColor(0xff607d8b);
        colorEditText.setTextSize(14);
        if (isEditing) {
            colorEditText.setText(oldColor);
        }
        color.addView(colorEditText);

        ImageView openColorPalette = new ImageView(this);
        openColorPalette.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(50), dpToPx(28)));
        openColorPalette.setFocusable(false);
        openColorPalette.setScaleType(ImageView.ScaleType.FIT_CENTER);
        openColorPalette.setImageResource(R.drawable.color_palette_48);
        colorContainer.addView(openColorPalette);

        dialog.a(customView);
        openColorPalette.setOnClickListener(getSharedPaletteColorPickerShower(dialog, colorEditText));

        dialog.b(Helper.getResString(R.string.common_word_save), save -> {
            try {
                String nameInput = nameEditText.getText().toString();
                String colorInput = colorEditText.getText().toString();
                Color.parseColor(colorInput);

                if (!isEditing) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", nameInput);
                    map.put("color", colorInput);

                    if (insertAtPosition == null) {
                        pallet_listmap.add(map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        _readSettings();
                        _refresh_list();
                    } else {
                        pallet_listmap.add(insertAtPosition, map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        _readSettings();
                        _refresh_list();
                        _insertBlocksAt(insertAtPosition + 9);
                    }
                } else {
                    pallet_listmap.get(oldPosition).put("name", nameInput);
                    pallet_listmap.get(oldPosition).put("color", colorInput);
                    FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                    _readSettings();
                    _refresh_list();
                }
                dialog.dismiss();
            } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
                color.setError("Malformed hexadecimal color");
                color.requestFocus();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public class PaletteAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> palettes;

        public PaletteAdapter(ArrayList<HashMap<String, Object>> palettes) {
            this.palettes = palettes;
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
            LayoutInflater _inflater = getLayoutInflater();
            if (convertView == null) {
                convertView = _inflater.inflate(R.layout.pallet_customview, parent, false);
            }

            final LinearLayout background = convertView.findViewById(R.id.background);
            final LinearLayout color = convertView.findViewById(R.id.color);
            final TextView title = convertView.findViewById(R.id.title);
            final TextView sub = convertView.findViewById(R.id.sub);

            title.setText(pallet_listmap.get(position).get("name").toString());
            sub.setText("Blocks: " + (long) (_getN(position + 9)));
            card2_sub.setText("Blocks: " + (long) (_getN(-1)));

            int backgroundColor;
            String paletteColorValue = (String) palettes.get(position).get("color");
            try {
                backgroundColor = Color.parseColor(paletteColorValue);
            } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
                SketchwareUtil.toastError("Invalid background color '" + paletteColorValue + "' in Palette #" + (position + 1));
                backgroundColor = Color.WHITE;
            }
            color.setBackgroundColor(backgroundColor);

            _a(background);
            background.setOnLongClickListener(v -> {
                final String moveUp = "Move up";
                final String moveDown = "Move down";
                final String edit = "Edit";
                final String delete = "Delete";
                final String insert = "Insert";

                PopupMenu popup = new PopupMenu(BlocksManager.this, background);
                Menu menu = popup.getMenu();
                if (position != 0) menu.add(moveUp);
                if (position != getCount() - 1) menu.add(moveDown);
                menu.add(edit);
                menu.add(delete);
                menu.add(insert);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getTitle().toString()) {
                        case edit:
                            showPaletteDialog(true, position,
                                    pallet_listmap.get(position).get("name").toString(),
                                    pallet_listmap.get(position).get("color").toString(), null);
                            break;

                        case delete:
                            new AlertDialog.Builder(BlocksManager.this)
                                    .setTitle(pallet_listmap.get(position).get("name").toString())
                                    .setMessage("Remove all blocks related to this palette?")
                                    .setPositiveButton("Remove permanently", (dialog, which) -> {
                                        pallet_listmap.remove(position);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        _removeRelatedBlocks(position + 9);
                                        _readSettings();
                                        _refresh_list();
                                    })
                                    .setNegativeButton(R.string.common_word_cancel, null)
                                    .setNeutralButton("Move to recycle bin", (dialog, which) -> {
                                        _moveRelatedBlocksToRecycleBin(position + 9);
                                        pallet_listmap.remove(position);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        _removeRelatedBlocks(position + 9);
                                        _readSettings();
                                        _refresh_list();
                                    }).show();
                            break;

                        case moveUp:
                            _MoveUp(position);
                            break;

                        case moveDown:
                            _moveDown(position);
                            break;

                        case insert:
                            showPaletteDialog(false, null, null, null, position);
                            break;

                        default:
                    }
                    return true;
                });
                popup.show();

                return true;
            });

            background.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", String.valueOf((long) (position + 9)));
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            });

            return convertView;
        }
    }
}
