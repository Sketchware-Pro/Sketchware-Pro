package mod.hilal.saif.activities.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.Collections;
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
    private int oldPos;
    private int newPos;
    private ArrayList<HashMap<String, Object>> pallet_listmap = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;

    private BlocksManagerBinding binding;
    private Vibrator vibrator;
    PaletteLayoutManager layoutManager;


    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        binding = BlocksManagerBinding.inflate(getLayoutInflater());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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

        layoutManager = new PaletteLayoutManager(this);

        binding.paletteRecycler.setLayoutManager(layoutManager);

        binding.paletteRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.paletteRecycler.setAdapter(new PaletteAdapter(pallet_listmap));

        binding.fab.setOnClickListener(v -> showPaletteDialog(false, null, null, "#ffffff", null));

        readSettings();
        refresh_list();
        recycleBin(binding.recycleBinCard);

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                oldPos = viewHolder.getBindingAdapterPosition();
                newPos = target.getBindingAdapterPosition();
                if (oldPos < newPos) {
                    for (int i = oldPos; i < newPos; i++) {
                        Collections.swap(pallet_listmap, i, i + 1);
                    }
                }else{
                    for (int i = oldPos; i > newPos; i--) {
                        Collections.swap(pallet_listmap, i, i - 1);
                    }
                }
                Objects.requireNonNull(binding.paletteRecycler.getAdapter()).notifyItemMoved(oldPos, newPos);

                swapRelatedBlocks(oldPos + 9, newPos + 9);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int action) {
                if (action == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setAlpha(0.7f);
                    binding.background.setClipChildren(false);
                }else{
                    binding.background.setClipChildren(true);
                }
                super.onSelectedChanged(viewHolder, action);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setAlpha(1f);
                FileUtil.writeFile(blocks_dir, new Gson().toJson(all_blocks_list));
                FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));

                readSettings();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    View draggedView = viewHolder.itemView;

                    if (isItInTrash(draggedView, binding.recycleBin)) {
                        int pos = viewHolder.getBindingAdapterPosition();
                        layoutManager.setScrollEnabled(false);
                        binding.recycleBinCard.setAlpha(0.5f);
                        if (!isCurrentlyActive && pos != RecyclerView.NO_POSITION && pos < pallet_listmap.size()) {
                            vibrator.vibrate(40L);
                            draggedView.setVisibility(View.GONE);
                            pallet_listmap.remove(pos);
                            Objects.requireNonNull(binding.paletteRecycler.getAdapter()).notifyItemRemoved(pos);
                            Objects.requireNonNull(binding.paletteRecycler.getAdapter()).notifyItemChanged(pos);
                            moveRelatedBlocksToRecycleBin(pos + 9);
                            removeRelatedBlocks(pos + 9);
                            refreshCount();
                        }
                    }else{
                        binding.recycleBinCard.setAlpha(1f);
                        layoutManager.setScrollEnabled(true);
                    }
                }
            }

            @Override
            public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int viewSize,
                                                    int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
                final int direction = (int) Math.signum(viewSizeOutOfBounds);
                final float distanceRatio = Math.min(1.0f, Math.abs((float) viewSizeOutOfBounds) / viewSize);
                return (int) (direction * distanceRatio * 10);
            }

        });

        itemTouchHelper.attachToRecyclerView(binding.paletteRecycler);
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

        binding.paletteRecycler.setAdapter(new PaletteAdapter(pallet_listmap));
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
    }

    private void swapRelatedBlocks(final double f, final double s) {
        final String TEMP_PALETTE = "TEMP_SWAP";
        for (Map<String, Object> block : all_blocks_list) {
            Object paletteObj = block.get("palette");

            if (paletteObj == null) continue;
            double paletteValue;
            try {
                paletteValue = Double.parseDouble(paletteObj.toString());
            } catch (NumberFormatException e) {
                continue;
            }

            if (paletteValue == f) {
                block.put("palette", TEMP_PALETTE);
            }else if (paletteValue == s) {
                block.put("palette", String.valueOf((long) f));
            }
        }
        for (Map<String, Object> block : all_blocks_list) {
            if (TEMP_PALETTE.equals(block.get("palette"))) {
                block.put("palette", String.valueOf((long) s));
            }
        }
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
            String nameInput = Objects.requireNonNull(dialogBinding.nameEditText.getText()).toString();
            String colorInput = Objects.requireNonNull(dialogBinding.colorEditText.getText()).toString();

            if (nameInput.isEmpty()) {
                SketchwareUtil.toast("Name cannot be empty", Toast.LENGTH_SHORT);
                return;
            }

            if (!PropertiesUtil.isHexColor(colorInput)) {
                SketchwareUtil.toast("Please enter a valid HEX color", Toast.LENGTH_SHORT);
                return;
            }

            if (PropertiesUtil.isHexColor(colorInput)) {
                Color.parseColor(colorInput);
                if (!isEditing) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", nameInput);
                    map.put("color", colorInput);

                    if (insertAtPosition == null) {
                        pallet_listmap.add(map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        Objects.requireNonNull(binding.paletteRecycler.getAdapter()).notifyItemInserted(pallet_listmap.size() - 1);
                        readSettings();
                    }else{
                        pallet_listmap.add(insertAtPosition, map);
                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                        readSettings();
                        Objects.requireNonNull(binding.paletteRecycler.getAdapter()).notifyItemInserted(insertAtPosition);
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
            }
        });

        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
        dialog.a(dialogBinding.getRoot());
        dialog.show();
    }

    private boolean isItInTrash(View draggedView, View trash) {
        int[] trashLocation = new int[2];
        trash.getLocationOnScreen(trashLocation);

        int[] draggedLocation = new int[2];
        draggedView.getLocationOnScreen(draggedLocation);

        int draggedY = draggedLocation[1];

        return draggedY <= (trashLocation[1] + draggedView.getMeasuredHeight() / 2) && draggedY >= ((trashLocation[1] - draggedView.getMeasuredHeight() / 2));
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

            holder.itemView.setVisibility(View.VISIBLE);
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
                    int pos = holder.getAbsoluteAdapterPosition();
                    switch (Objects.requireNonNull(item.getTitle()).toString()) {
                        case edit:
                            showPaletteDialog(true, pos,
                                    Objects.requireNonNull(pallet_listmap.get(pos).get("name")).toString(),
                                    Objects.requireNonNull(pallet_listmap.get(pos).get("color")).toString(), null);
                            break;

                        case delete:
                            new MaterialAlertDialogBuilder(BlocksManager.this)
                                    .setTitle(Objects.requireNonNull(pallet_listmap.get(pos).get("name")).toString())
                                    .setMessage("Remove all blocks related to this palette?")
                                    .setPositiveButton("Remove permanently", (dialog, which) -> {
                                        palettes.remove(pos);
                                        notifyItemRemoved(pos);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        removeRelatedBlocks(pos + 9);
                                        readSettings();
                                        refreshCount();
                                    })
                                    .setNegativeButton(R.string.common_word_cancel, null)
                                    .setNeutralButton("Move to recycle bin", (dialog, which) -> {
                                        moveRelatedBlocksToRecycleBin(position + 9);
                                        palettes.remove(pos);
                                        notifyItemRemoved(pos);
                                        FileUtil.writeFile(pallet_dir, new Gson().toJson(pallet_listmap));
                                        removeRelatedBlocks(pos + 9);
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

            holder.itemBinding.dragHandler.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(holder);
                }

                return false;
            });

            holder.itemBinding.backgroundCard.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), BlocksManagerDetailsActivity.class);
                intent.putExtra("position", String.valueOf((long) (holder.getAbsoluteAdapterPosition() + 9)));
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

    public static class PaletteLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public PaletteLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }
}
