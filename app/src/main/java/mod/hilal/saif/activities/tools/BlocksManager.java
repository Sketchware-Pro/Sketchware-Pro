package mod.hilal.saif.activities.tools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a.a.a.Zx;
import a.a.a.aB;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;
import pro.sketchware.R;
import pro.sketchware.databinding.BlocksManagerBinding;
import pro.sketchware.databinding.DialogBlockConfigurationBinding;
import pro.sketchware.databinding.DialogPaletteBinding;
import pro.sketchware.databinding.PalletCustomviewBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BlocksManager extends BaseAppCompatActivity {

    private ArrayList<HashMap<String, Object>> all_blocks_list = new ArrayList<>();
    private String blocks_dir;
    private String pallet_dir;
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();

    private BlocksManagerBinding binding;


    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        binding = BlocksManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    @Override
    public void onStop() {
        super.onStop();
        BlockLoader.refresh();
    }

    private void initialize() {
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());

        binding.listPallete.setLayoutManager(new LinearLayoutManager(this));
        binding.listPallete.setAdapter(new PaletteAdapter(pallet_listmap));

        binding.fab.setOnClickListener(v -> showPaletteDialog(false, null, null, "#ffffff", null));

        readSettings();
        refresh_list();
        recycleBin(binding.recycleBinCard);
    }

    @Override
    public void onResume() {
        super.onResume();

        readSettings();
        refresh_list();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Settings").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_settings)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        String title = Objects.requireNonNull(menuItem.getTitle()).toString();
        if (title.equals("Settings")) {
            showBlockConfigurationDialog();
        }else{
            return false;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showBlockConfigurationDialog() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.ic_folder_48dp);
        dialog.b("Block configuration");

        DialogBlockConfigurationBinding dialogBinding = DialogBlockConfigurationBinding.inflate(getLayoutInflater());

        dialogBinding.palettesPath.setText(pallet_dir.replace(FileUtil.getExternalStorageDir(), ""));
        dialogBinding.blocksPath.setText(blocks_dir.replace(FileUtil.getExternalStorageDir(), ""));

        dialog.a(dialogBinding.getRoot());

        dialog.b(Helper.getResString(R.string.common_word_save), view -> {
            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH, Objects.requireNonNull(dialogBinding.palettesPath.getText()).toString());
            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH, Objects.requireNonNull(dialogBinding.blocksPath.getText()).toString());

            readSettings();
            refresh_list();
            dialog.dismiss();
        });

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));

        dialog.configureDefaultButton("Defaults", view -> {
            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH, ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH));
            ConfigActivity.setSetting(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH, ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH));

            readSettings();
            refresh_list();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void readSettings() {
        pallet_dir = FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                (String) ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH));
        blocks_dir = FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH,
                (String) ConfigActivity.getDefaultValue(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH));

        if (FileUtil.isExistFile(blocks_dir) && isValidJson(FileUtil.readFile(blocks_dir))) {
            try {
                all_blocks_list = new Gson().fromJson(FileUtil.readFile(blocks_dir), Helper.TYPE_MAP_LIST);

                if (all_blocks_list != null) {
                    return;
                }
                // fall-through to shared handler
            } catch (JsonParseException e) {
                // fall-through to shared handler
            }

            SketchwareUtil.showFailedToParseJsonDialog(this, new File(blocks_dir), "Custom Blocks", v -> readSettings());
        }
    }

    private Boolean isValidJson(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.isJsonObject() || element.isJsonArray();
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    private void refresh_list() {
        parsePaletteJson:
        {
            String paletteJsonContent;
            if (FileUtil.isExistFile(pallet_dir) && !(paletteJsonContent = FileUtil.readFile(pallet_dir)).isEmpty()) {
                try {
                    pallet_listmap = new Gson().fromJson(paletteJsonContent, Helper.TYPE_MAP_LIST);

                    if (pallet_listmap != null) {
                        break parsePaletteJson;
                    }
                    // fall-through to shared handler
                } catch (JsonParseException e) {
                    // fall-through to shared handler
                }

                SketchwareUtil.showFailedToParseJsonDialog(this, new File(pallet_dir), "Custom Block Palettes", v -> refresh_list());
            }
            pallet_listmap = new ArrayList<>();
        }

        binding.listPallete.setAdapter(new PaletteAdapter(pallet_listmap));
        binding.recycleSub.setText("Blocks: " + (long) (getN(-1)));
        refreshCount();
    }

    private double getN(final double _p) {
        int n = 0;
        if (all_blocks_list == null) return 0;

        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString().equals(String.valueOf((long) (_p)))) {
                n++;
            }
        }
        return (n);
    }

    private void refreshCount() {
        if (pallet_listmap.isEmpty()) {
            binding.paletteCount.setText("No palettes");
        }else{
            binding.paletteCount.setText(pallet_listmap.size() + " Palettes");
        }
    }

    private void recycleBin(final View view) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BlocksManagerDetailsActivity.class);
            intent.putExtra("position", "-1");
            intent.putExtra("dirB", blocks_dir);
            intent.putExtra("dirP", pallet_dir);
            startActivity(intent);
        });
        view.setOnLongClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Recycle bin")
                    .setMessage("Are you sure you want to empty the recycle bin? " +
                            "Blocks inside will be deleted PERMANENTLY, you CANNOT recover them!")
                    .setPositiveButton("Empty", (dialog, which) -> emptyRecyclebin())
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .show();
            return true;
        });
    }

    private void removeRelatedBlocks(final double _p) {
        List<Map<String, Object>> newBlocks = new LinkedList<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (!(Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == _p)) {
                if (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) > _p) {
                    HashMap<String, Object> m = all_blocks_list.get(i);
                    m.put("palette", String.valueOf((long) (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) - 1)));
                    newBlocks.add(m);
                }else{
                    newBlocks.add(all_blocks_list.get(i));
                }
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(newBlocks));
        readSettings();
        refresh_list();
    }

    private void swapRelatedBlocks(final double _f, final double _s) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == _f) {
                all_blocks_list.get(i).put("palette", "123456789");
            }
            if (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == _s) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (_f)));
            }
        }

        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == 123456789) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (_s)));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        readSettings();
    }

    private void insertBlocksAt(final double _p) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if ((Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) > _p) || (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == _p)) {
                all_blocks_list.get(i).put("palette", String.valueOf((long) (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) + 1)));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        readSettings();
        refresh_list();
    }

    private void moveRelatedBlocksToRecycleBin(final double _p) {
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == _p) {
                all_blocks_list.get(i).put("palette", "-1");
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
        readSettings();
    }

    private void emptyRecyclebin() {
        List<Map<String, Object>> newBlocks = new LinkedList<>();
        for (int i = 0; i < all_blocks_list.size(); i++) {
            if (!(Double.parseDouble(Objects.requireNonNull(all_blocks_list.get(i).get("palette")).toString()) == -1)) {
                newBlocks.add(all_blocks_list.get(i));
            }
        }
        FileUtil.writeFile(blocks_dir, new Gson().toJson(newBlocks));
        readSettings();
        refresh_list();
    }


    private void showPaletteDialog(boolean isEditing, Integer oldPosition, String oldName, String oldColor, Integer insertAtPosition) {
        aB dialog = new aB(this);
        dialog.a(R.drawable.icon_style_white_96);
        dialog.b(!isEditing ? "Create a new palette" : "Edit palette");

        DialogPaletteBinding dialogBinding = DialogPaletteBinding.inflate(getLayoutInflater());

        if (isEditing) {
            dialogBinding.nameEditText.setText(oldName);
            dialogBinding.colorEditText.setText(oldColor);
        }

        dialogBinding.openColorPalette.setOnClickListener(v1 -> {
            final Zx zx = new Zx(this, PropertiesUtil.parseColor(oldColor), true, false);
            zx.a(new PCP(dialogBinding.colorEditText));
            zx.showAtLocation(dialogBinding.openColorPalette, Gravity.CENTER, 0, 0);
        });

        dialog.a(dialogBinding.getRoot());

        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            try {
                String nameInput = Objects.requireNonNull(dialogBinding.nameEditText.getText()).toString();
                String colorInput = Objects.requireNonNull(dialogBinding.colorEditText.getText()).toString();

                if (nameInput.isEmpty()) {
                    dialogBinding.name.setError("Name cannot be empty");
                    return;
                }

                if (colorInput.isEmpty()) {
                    dialogBinding.color.setError("Color cannot be empty");
                    return;
                }

                Color.parseColor(colorInput);

                if (!isEditing) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", nameInput);
                    map.put("color", colorInput);

                    if (insertAtPosition == null) {
                        pallet_listmap.add(map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        Objects.requireNonNull(binding.listPallete.getAdapter()).notifyItemInserted(pallet_listmap.size() - 1);
                        readSettings();
                    }else{
                        pallet_listmap.add(insertAtPosition, map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        readSettings();
                        Objects.requireNonNull(binding.listPallete.getAdapter()).notifyItemInserted(insertAtPosition);
                        insertBlocksAt(insertAtPosition + 9);
                    }
                }else{
                    pallet_listmap.get(oldPosition).put("name", nameInput);
                    pallet_listmap.get(oldPosition).put("color", colorInput);
                    FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                    readSettings();
                    refresh_list();
                }
                dialog.dismiss();
            } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
                dialogBinding.color.setError("Malformed hexadecimal color");
                dialogBinding.color.requestFocus();
            }
        });

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));

        dialog.show();
    }


    public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> palettes;

        public PaletteAdapter(ArrayList<HashMap<String, Object>> palettes) {
            this.palettes = palettes;

        }

        @NonNull
        @Override
        public PaletteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PalletCustomviewBinding itemBinding = PalletCustomviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PaletteAdapter.ViewHolder(itemBinding);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBindViewHolder(@NonNull PaletteAdapter.ViewHolder holder, int position) {
            String paletteColorValue = (String) palettes.get(position).get("color");
            assert paletteColorValue != null;
            int backgroundColor = PropertiesUtil.parseColor(paletteColorValue);

            holder.itemBinding.title.setText(Objects.requireNonNull(pallet_listmap.get(position).get("name")).toString());
            holder.itemBinding.sub.setText("Blocks: " + (long) (getN(position + 9)));
            holder.itemBinding.color.setBackgroundColor(backgroundColor);
            holder.itemBinding.dragHandler.setVisibility(View.VISIBLE);
            binding.recycleSub.setText("Blocks: " + (long) (getN(-1)));

            holder.itemBinding.backgroundCard.setOnLongClickListener(v -> {
                final String edit = "Edit";
                final String delete = "Delete";
                final String insert = "Insert";

                PopupMenu popup = new PopupMenu(BlocksManager.this, holder.itemBinding.color);
                Menu menu = popup.getMenu();
                menu.add(edit);
                menu.add(delete);
                menu.add(insert);
                popup.setOnMenuItemClickListener(item -> {
                    switch (Objects.requireNonNull(item.getTitle()).toString()) {
                        case edit:
                            showPaletteDialog(true, position,
                                    Objects.requireNonNull(pallet_listmap.get(position).get("name")).toString(),
                                    Objects.requireNonNull(pallet_listmap.get(position).get("color")).toString(), null);
                            break;

                        case delete:
                            int adapterPos = holder.getAbsoluteAdapterPosition();
                            new MaterialAlertDialogBuilder(BlocksManager.this)
                                    .setTitle(Objects.requireNonNull(pallet_listmap.get(position).get("name")).toString())
                                    .setMessage("Remove all blocks related to this palette?")
                                    .setPositiveButton("Remove permanently", (dialog, which) -> {
                                        palettes.remove(adapterPos);
                                        notifyItemRemoved(adapterPos);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        removeRelatedBlocks(adapterPos + 9);
                                        readSettings();
                                        refreshCount();
                                    })
                                    .setNegativeButton(R.string.common_word_cancel, null)
                                    .setNeutralButton("Move to recycle bin", (dialog, which) -> {
                                        moveRelatedBlocksToRecycleBin(position + 9);
                                        palettes.remove(adapterPos);
                                        notifyItemRemoved(adapterPos);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        removeRelatedBlocks(adapterPos + 9);
                                        readSettings();
                                        refreshCount();
                                    }).show();
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

            holder.itemBinding.backgroundCard.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", String.valueOf((long) (position + 9)));
                intent.putExtra("dirB", blocks_dir);
                intent.putExtra("dirP", pallet_dir);
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return palettes.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public PalletCustomviewBinding itemBinding;

            public ViewHolder(PalletCustomviewBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
        }
    }
}
