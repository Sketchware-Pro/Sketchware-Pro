package com.besome.sketch.editor.manage;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.beans.WidgetCollectionBean;
import com.besome.sketch.editor.manage.font.AddFontActivity;
import com.besome.sketch.editor.manage.font.AddFontCollectionActivity;
import com.besome.sketch.editor.manage.image.AddImageCollectionActivity;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigationrail.NavigationRailView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import a.a.a.Mp;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Pp;
import a.a.a.Qp;
import a.a.a.Rp;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import mod.jbk.util.AudioMetadata;
import mod.jbk.util.BlockUtil;
import mod.jbk.util.SoundPlayingAdapter;
import pro.sketchware.R;

public class ManageCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_IMAGE_DIALOG = 267;
    private static final int REQUEST_CODE_SHOW_IMAGE_DETAILS = 268;
    private static final int REQUEST_CODE_ADD_SOUND_DIALOG = 269;
    private static final int REQUEST_CODE_SHOW_SOUND_DETAILS = 270;
    private static final int REQUEST_CODE_ADD_FONT_DIALOG = 271;
    private static final int REQUEST_CODE_SHOW_FONT_DETAILS = 272;
    private static final int REQUEST_CODE_SHOW_WIDGET_DETAILS = 273;
    private static final int REQUEST_CODE_SHOW_BLOCK_DETAILS = 274;
    private static final int REQUEST_CODE_SHOW_MORE_BLOCK_DETAILS = 279;

    private LinearLayout actionButtonGroup;
    private boolean hasDeletedWidget;
    private boolean selectingToBeDeletedItems;
    private CollectionAdapter collectionAdapter;
    private RecyclerView collection;
    private NavigationRailView categories;
    private ArrayList<ProjectResourceBean> images;
    private ArrayList<ProjectResourceBean> sounds;
    private ArrayList<ProjectResourceBean> fonts;
    private ArrayList<WidgetCollectionBean> widgets;
    private ArrayList<BlockCollectionBean> blocks;
    private ArrayList<MoreBlockCollectionBean> moreBlocks;
    private int currentItemId = 1;
    private final int collectionItemsSize = 6;
    private TextView noItemsNote;
    private FloatingActionButton fab;
    private String sc_id;

    private void showAddImageDialog() {
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, REQUEST_CODE_ADD_IMAGE_DIALOG);
    }

    private void showAddSoundDialog() {
        collectionAdapter.stopPlayback();
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", sounds);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, REQUEST_CODE_ADD_SOUND_DIALOG);
    }

    private void showAddFontDialog() {
        Intent intent = new Intent(getApplicationContext(), AddFontActivity.class);
        intent.putParcelableArrayListExtra("font_names", fonts);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("add_to_collection", true);
        startActivityForResult(intent, REQUEST_CODE_ADD_FONT_DIALOG);
    }

    private int getBlockIcon(BlockBean block) {
        return switch (block.type) {
            case "c" -> R.drawable.fav_block_c_96dp;
            case "b" -> R.drawable.fav_block_boolean_96dp;
            case "f" -> R.drawable.fav_block_final_96dp;
            case "e" -> R.drawable.fav_block_e_96dp;
            case "d" -> R.drawable.fav_block_number_96dp;
            case "s" -> R.drawable.fav_block_string_96dp;
            default -> R.drawable.fav_block_command_96dp;
        };
    }

    private void changeDeletingItemsState(boolean deletingItems) {
        selectingToBeDeletedItems = deletingItems;
        invalidateOptionsMenu();
        unselectToBeDeletedItems();
        if (selectingToBeDeletedItems) {
            collectionAdapter.stopPlayback();
            actionButtonGroup.setVisibility(View.VISIBLE);
        } else {
            actionButtonGroup.setVisibility(View.GONE);
            if (currentItemId == 3 || currentItemId == 4) {
                fab.setVisibility(View.GONE);
            }
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void handleFabOnClick(int categoryId) {
        if (categoryId == 0) {
            showAddImageDialog();
        } else if (categoryId == 1) {
            showAddSoundDialog();
        } else {
            showAddFontDialog();
        }
    }

    private void openImageDetails(int position) {
        ProjectResourceBean editTarget = images.get(position);
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, REQUEST_CODE_SHOW_IMAGE_DETAILS);
    }

    private void openSoundDetails(int position) {
        ProjectResourceBean editTarget = sounds.get(position);
        collectionAdapter.stopPlayback();
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", sounds);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, REQUEST_CODE_SHOW_SOUND_DETAILS);
    }

    private void openFontDetails(int position) {
        ProjectResourceBean editTarget = fonts.get(position);
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", fonts);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, REQUEST_CODE_SHOW_FONT_DETAILS);
    }

    private void openWidgetDetails(int position) {
        String widgetName = Rp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowWidgetCollectionActivity.class);
        intent.putExtra("widget_name", widgetName);
        startActivityForResult(intent, REQUEST_CODE_SHOW_WIDGET_DETAILS);
    }

    private void openBlockDetails(int position) {
        String blockName = Mp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, REQUEST_CODE_SHOW_BLOCK_DETAILS);
    }

    private void openMoreBlockDetails(int position) {
        String blockName = Pp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowMoreBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, REQUEST_CODE_SHOW_MORE_BLOCK_DETAILS);
    }

    private int getSelectedIndex(int id) {
        if (id == R.id.image) {
            return 0;
        } else if (id == R.id.audio) {
            return 1;
        } else if (id == R.id.font) {
            return 2;
        } else if (id == R.id.widget) {
            return 3;
        } else if (id == R.id.block) {
            return 4;
        } else if (id == R.id.moreblock) {
            return 5;
        }
        return -1;
    }

    private void setSelectedIndex(int item) {
        if (!mB.a()) {
            if (item != -1 && item != currentItemId) {
                if (currentItemId == 1) {
                    collectionAdapter.stopPlayback();
                }
                currentItemId = item;
                collection.removeAllViews();
                collectionAdapter.currentViewType = currentItemId;
                collectionAdapter.setData(switch (currentItemId) {
                    case 0 -> images;
                    case 1 -> sounds;
                    case 2 -> fonts;
                    case 3 -> widgets;
                    case 4 -> blocks;
                    default -> moreBlocks;
                });

                if (collectionAdapter.currentViewType == 0) {
                    collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), getGridLayoutColumnCount()));
                    fab.show();
                } else {
                    collection.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    if (collectionAdapter.currentViewType != 1 && collectionAdapter.currentViewType != 2) {
                        fab.hide();
                    } else {
                        fab.show();
                    }
                }

                collectionAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteSelectedToBeDeletedItems() {
        for (int i = 0; i < collectionItemsSize; i++) {
            switch (i) {
                case 0 -> {
                    for (ProjectResourceBean bean : images) {
                        if (bean.isSelected) {
                            Op.g().a(bean.resName, false);
                        }
                    }
                    Op.g().e();
                    loadImages();
                }
                case 1 -> {
                    for (ProjectResourceBean bean : sounds) {
                        if (bean.isSelected) {
                            Qp.g().a(bean.resName, false);
                        }
                    }
                    Qp.g().e();
                    loadSounds();
                }
                case 2 -> {
                    for (ProjectResourceBean bean : fonts) {
                        if (bean.isSelected) {
                            Np.g().a(bean.resName, false);
                        }
                    }
                    Np.g().e();
                    loadFonts();
                }
                case 3 -> {
                    for (WidgetCollectionBean bean : widgets) {
                        if (bean.isSelected) {
                            if (!hasDeletedWidget) {
                                hasDeletedWidget = true;
                            }

                            Rp.h().a(bean.name, false);
                        }
                    }
                    Rp.h().e();
                    loadWidgets();
                }
                case 4 -> {
                    for (BlockCollectionBean bean : blocks) {
                        if (bean.isSelected) {
                            Mp.h().a(bean.name, false);
                        }
                    }
                    Mp.h().e();
                    loadBlocks();
                }
                default -> {
                    for (MoreBlockCollectionBean bean : moreBlocks) {
                        if (bean.isSelected) {
                            Pp.h().a(bean.name, false);
                        }
                    }
                    Pp.h().e();
                    loadMoreBlocks();
                }
            }
        }

        unselectToBeDeletedItems();
        changeDeletingItemsState(false);
        int id = getCurrentCategoryItemId();
        if (id == 0 || id == 1 || id == 2) {
            fab.show();
        }

        bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_complete_delete), 1).show();
        collectionAdapter.notifyDataSetChanged();
    }

    private int getCurrentCategoryItemId() {
        return currentItemId;
    }

    private int getGridLayoutColumnCount() {
        int var1 = (int) ((float) getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density) / 100;
        int var2 = var1;
        if (var1 > 2) {
            var2 = var1 - 1;
        }

        return var2;
    }

    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Helper.getResString(R.string.design_actionbar_title_manager_collection));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        noItemsNote = findViewById(R.id.tv_no_collections);
        noItemsNote.setText(Helper.getResString(R.string.event_message_no_events));
        categories = findViewById(R.id.category_list);
        collection = findViewById(R.id.collection_list);
        collectionAdapter = new CollectionAdapter(collection);
        collection.setAdapter(collectionAdapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        actionButtonGroup = findViewById(R.id.layout_btn_group);

        MaterialButton delete = findViewById(R.id.btn_delete);
        MaterialButton cancel = findViewById(R.id.btn_cancel);
        delete.setText(Helper.getResString(R.string.common_word_delete));
        cancel.setText(Helper.getResString(R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);


        categories.setOnItemSelectedListener(item -> {
            setSelectedIndex(getSelectedIndex(item.getItemId()));
            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_IMAGE_DIALOG, REQUEST_CODE_SHOW_IMAGE_DETAILS -> loadImages();
            case REQUEST_CODE_ADD_SOUND_DIALOG, REQUEST_CODE_SHOW_SOUND_DETAILS -> loadSounds();
            case REQUEST_CODE_ADD_FONT_DIALOG, REQUEST_CODE_SHOW_FONT_DETAILS -> loadFonts();
            case REQUEST_CODE_SHOW_WIDGET_DETAILS -> loadWidgets();
            case REQUEST_CODE_SHOW_BLOCK_DETAILS -> loadBlocks();
            case REQUEST_CODE_SHOW_MORE_BLOCK_DETAILS -> loadMoreBlocks();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (selectingToBeDeletedItems) {
            changeDeletingItemsState(false);
        } else {
            if (hasDeletedWidget) {
                setResult(RESULT_OK);
                finish();
            }

            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_cancel && selectingToBeDeletedItems) {
            changeDeletingItemsState(false);
        } else if (id == R.id.btn_delete && selectingToBeDeletedItems) {
            deleteSelectedToBeDeletedItems();
        } else if (id == R.id.fab) {
            changeDeletingItemsState(false);
            handleFabOnClick(currentItemId);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (collectionAdapter.currentViewType == 0) {
            ((GridLayoutManager) Objects.requireNonNull(collection.getLayoutManager())).setSpanCount(getGridLayoutColumnCount());
            collection.requestLayout();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isStoragePermissionGranted()) {
            finish();
        }

        setContentView(R.layout.manage_collection);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_collection_menu, menu);
        menu.findItem(R.id.menu_collection_delete).setVisible(!selectingToBeDeletedItems);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_collection_delete) {
            changeDeletingItemsState(!selectingToBeDeletedItems);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        loadAllCollectionItems();
        categories.setSelectedItemId(R.id.image);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStoragePermissionGranted()) {
            finish();
        }

        if (collectionAdapter != null) {
            collectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void loadAllCollectionItems() {
        images = Op.g().f();
        sounds = Qp.g().f();
        fonts = Np.g().f();
        widgets = Rp.h().f();
        blocks = Mp.h().f();
        moreBlocks = Pp.h().f();

        if (currentItemId == -1) {
            collectionAdapter.currentViewType = 0;
            collectionAdapter.setData(images);
            collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), getGridLayoutColumnCount()));
            currentItemId = 0;
        }

        if (collectionAdapter != null) {
            collectionAdapter.notifyDataSetChanged();
        }
    }

    private void loadImages() {
        images = Op.g().f();
        if (currentItemId == 0) {
            collectionAdapter.setData(images);
            collectionAdapter.currentViewType = 0;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadSounds() {
        sounds = Qp.g().f();
        if (currentItemId == 1) {
            collectionAdapter.setData(sounds);
            collectionAdapter.currentViewType = 1;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadFonts() {
        fonts = Np.g().f();
        if (currentItemId == 2) {
            collectionAdapter.setData(fonts);
            collectionAdapter.currentViewType = 2;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadWidgets() {
        widgets = Rp.h().f();
        if (currentItemId == 3) {
            collectionAdapter.setData(widgets);
            collectionAdapter.currentViewType = 3;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadBlocks() {
        blocks = Mp.h().f();
        if (currentItemId == 4) {
            collectionAdapter.setData(blocks);
            collectionAdapter.currentViewType = 4;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadMoreBlocks() {
        moreBlocks = Pp.h().f();
        if (currentItemId == 5) {
            collectionAdapter.setData(moreBlocks);
            collectionAdapter.currentViewType = 5;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void unselectToBeDeletedItems() {
        int id = getCurrentCategoryItemId();

        switch (id) {
            case 0:
                for (ProjectResourceBean bean : images) {
                    bean.isSelected = false;
                }
                break;

            case 1:
                for (ProjectResourceBean bean : sounds) {
                    bean.isSelected = false;
                }
                break;

            case 2:
                for (ProjectResourceBean bean : fonts) {
                    bean.isSelected = false;
                }
                break;

            case 3:
                for (WidgetCollectionBean bean : widgets) {
                    bean.isSelected = false;
                }
                break;

            default:
                for (BlockCollectionBean bean : blocks) {
                    bean.isSelected = false;
                }
                break;
        }
    }


    private class CollectionAdapter extends SoundPlayingAdapter<SoundPlayingAdapter.ViewHolder> {
        private int lastSelectedItemPosition;
        private int currentViewType;
        private ArrayList<? extends SelectableBean> currentCollectionTypeItems;

        public CollectionAdapter(RecyclerView target) {
            super(ManageCollectionActivity.this);
            lastSelectedItemPosition = -1;
            currentViewType = -1;
            target.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (currentViewType == 3 || currentViewType == 4 || currentViewType == 5) {
                        return;
                    }
                    if (dy > 2) {
                        if (!fab.isEnabled()) {
                            return;
                        }
                        fab.hide();
                    } else if (dy < -2 && fab.isEnabled()) {
                        fab.show();
                    }
                }
            });
            currentCollectionTypeItems = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            return currentCollectionTypeItems.size();
        }

        private void onBindViewHolder(ImageCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isNinePatch()) {
                holder.ninePatchIcon.setVisibility(View.VISIBLE);
            } else {
                holder.ninePatchIcon.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + bean.resFullName)
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(holder.image).getView());

            holder.name.setText(bean.resName);
            holder.checkBox.setChecked(bean.isSelected);
        }

        private void onBindViewHolder(SoundCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.album.setVisibility(View.GONE);
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                var audioMetadata = holder.audioMetadata;
                var audio = getAudio(position);
                if (audioMetadata == null || !audioMetadata.getSource().equals(audio)) {
                    audioMetadata = holder.audioMetadata = AudioMetadata.fromPath(audio);
                    bean.totalSoundDuration = audioMetadata.getDurationInMs();
                    audioMetadata.setEmbeddedPictureAsAlbumCover(ManageCollectionActivity.this, holder.album);
                }
                holder.album.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            int soundPositionInS = bean.curSoundPosition / 1000;

            int totalSoundDurationInS = bean.totalSoundDuration / 1000;
            holder.currentPosition.setText(String.format("%d:%02d", soundPositionInS / 60, soundPositionInS % 60));
            holder.totalDuration.setText(String.format("%d:%02d", totalSoundDurationInS / 60, totalSoundDurationInS % 60));
            holder.checkBox.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            boolean playing = position == soundPlayer.getNowPlayingPosition() && soundPlayer.isPlaying();
            holder.play.setImageResource(playing ? R.drawable.ic_pause_blue_circle_48dp : R.drawable.circled_play_96_blue);
            holder.playbackProgress.setMax(bean.totalSoundDuration / 100);
            holder.playbackProgress.setProgress(bean.curSoundPosition / 100);
        }

        private void onBindViewHolder(FontCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.checkBox.setChecked(bean.isSelected);
            holder.name.setText(bean.resName + ".ttf");

            try {
                holder.preview.setTypeface(Typeface.createFromFile(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + bean.resFullName));
                holder.preview.setText(Helper.getResString(R.string.design_manager_font_description_example_sentence));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void onBindViewHolder(WidgetCollectionViewHolder holder, int position) {
            WidgetCollectionBean bean = (WidgetCollectionBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
                holder.widgetIcon.setVisibility(View.GONE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
                holder.widgetIcon.setVisibility(View.VISIBLE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.widgetIcon.setImageResource(ViewBean.getViewTypeResId(bean.widgets.get(0).type));
            holder.name.setText(bean.name);
            holder.checkBox.setChecked(bean.isSelected);
        }

        private void onBindViewHolder(BlockCollectionViewHolder holder, int position) {
            BlockCollectionBean bean = (BlockCollectionBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
                holder.blockIcon.setVisibility(View.GONE);
            } else {
                holder.blockIcon.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.blockIcon.setImageResource(getBlockIcon(bean.blocks.get(0)));
            holder.name.setText(bean.name);
            holder.checkBox.setChecked(bean.isSelected);
        }

        private void onBindViewHolder(MoreBlockCollectionViewHolder holder, int position) {
            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) currentCollectionTypeItems.get(position);
            if (selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.name.setText(bean.name);
            holder.checkBox.setChecked(bean.isSelected);
            holder.blockArea.removeAllViews();
            BlockUtil.loadMoreblockPreview(holder.blockArea, bean.spec);
        }

        private void setData(ArrayList<? extends SelectableBean> beans) {
            currentCollectionTypeItems = beans;
            if (beans.size() <= 0) {
                noItemsNote.setVisibility(View.VISIBLE);
            } else {
                noItemsNote.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemViewType(int position) {
            position = currentViewType;

            if (position == 0) {
                return 0;
            } else if (position == 1) {
                return 1;
            } else if (position == 2) {
                return 2;
            } else if (position == 3) {
                return 3;
            } else if (position == 4) {
                return 4;
            } else {
                return 5;
            }
        }

        @Override
        @NonNull
        public SoundPlayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return switch (viewType) {
                case 0 ->
                        new ImageCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
                case 1 ->
                        new SoundCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
                case 2 ->
                        new FontCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_font_list_item, parent, false));
                case 3 ->
                        new WidgetCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_widget_list_item, parent, false));
                case 4 ->
                        new BlockCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_block_list_item, parent, false));
                default ->
                        new MoreBlockCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_more_block_list_item, parent, false));
            };
        }

        @Override
        public void onBindViewHolder(@NonNull SoundPlayingAdapter.ViewHolder holder, int position) {
            int viewType = holder.getItemViewType();

            switch (viewType) {
                case 0 -> onBindViewHolder((ImageCollectionViewHolder) holder, position);
                case 1 -> onBindViewHolder((SoundCollectionViewHolder) holder, position);
                case 2 -> onBindViewHolder((FontCollectionViewHolder) holder, position);
                case 3 -> onBindViewHolder((WidgetCollectionViewHolder) holder, position);
                case 4 -> onBindViewHolder((BlockCollectionViewHolder) holder, position);
                case 5 -> onBindViewHolder((MoreBlockCollectionViewHolder) holder, position);
            }
        }

        @Override
        public ProjectResourceBean getData(int position) {
            return (ProjectResourceBean) currentCollectionTypeItems.get(position);
        }

        @Override
        public Path getAudio(int position) {
            return Paths.get(wq.a(), "sound", "data", getData(position).resFullName);
        }

        private class BlockCollectionViewHolder extends SoundlessViewHolder {
            public final MaterialCardView cardView;
            public final CheckBox checkBox;
            public final ImageView blockIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;

            public BlockCollectionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                blockIcon = itemView.findViewById(R.id.img_block);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_block_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openBlockDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class FontCollectionViewHolder extends SoundlessViewHolder {
            public final MaterialCardView cardView;
            public final CheckBox checkBox;
            public final ImageView fontIcon;
            public final ImageView delete;
            public final TextView name;
            public final TextView preview;
            public final LinearLayout deleteContainer;

            public FontCollectionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                fontIcon = itemView.findViewById(R.id.img_font);
                fontIcon.setVisibility(View.GONE);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_font_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                preview = itemView.findViewById(R.id.tv_font_preview);
                preview.setText(Helper.getResString(R.string.common_word_preview));
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openFontDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return false;
                });
            }
        }

        private class ImageCollectionViewHolder extends SoundlessViewHolder {
            public final CheckBox checkBox;
            public final TextView name;
            public final ImageView image;
            public final ImageView delete;
            public final ImageView ninePatchIcon;
            public final LinearLayout deleteContainer;

            public ImageCollectionViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.chk_select);
                name = itemView.findViewById(R.id.tv_image_name);
                image = itemView.findViewById(R.id.img);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                ninePatchIcon = itemView.findViewById(R.id.img_nine_patch);
                checkBox.setVisibility(View.GONE);
                image.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openImageDetails(lastSelectedItemPosition);
                    }
                });
                image.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class MoreBlockCollectionViewHolder extends SoundlessViewHolder {
            public final MaterialCardView cardView;
            public final CheckBox checkBox;
            public final ImageView delete;
            public final LinearLayout deleteContainer;
            public final TextView name;
            public final RelativeLayout blockArea;

            public MoreBlockCollectionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                name = itemView.findViewById(R.id.tv_block_name);
                blockArea = itemView.findViewById(R.id.block_area);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openMoreBlockDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class SoundCollectionViewHolder extends SoundPlayingAdapter.ViewHolder {
            public final ProgressBar playbackProgress;
            public final TextView totalDuration;
            public final LinearLayout deleteContainer;
            public final MaterialCardView cardView;
            public final CheckBox checkBox;
            public final ImageView album;
            public final ImageView delete;
            public final TextView name;
            public final ImageView play;
            public final TextView currentPosition;

            private AudioMetadata audioMetadata;

            public SoundCollectionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                album = itemView.findViewById(R.id.img_album);
                name = itemView.findViewById(R.id.tv_sound_name);
                play = itemView.findViewById(R.id.img_play);
                delete = itemView.findViewById(R.id.img_delete);
                currentPosition = itemView.findViewById(R.id.tv_currenttime);
                playbackProgress = itemView.findViewById(R.id.prog_playtime);
                totalDuration = itemView.findViewById(R.id.tv_endtime);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                play.setOnClickListener(v -> {
                    if (selectingToBeDeletedItems) {
                        soundPlayer.onPlayPressed(getLayoutPosition());
                    }
                });
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openSoundDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }

            @Override
            protected TextView getCurrentPosition() {
                return currentPosition;
            }

            @Override
            protected ProgressBar getPlaybackProgress() {
                return playbackProgress;
            }
        }

        private class WidgetCollectionViewHolder extends SoundlessViewHolder {
            public final MaterialCardView cardView;
            public final CheckBox checkBox;
            public final ImageView widgetIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;

            public WidgetCollectionViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                widgetIcon = itemView.findViewById(R.id.img_widget);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_widget_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = getLayoutPosition();
                    if (selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        notifyItemChanged(lastSelectedItemPosition);
                    } else {
                        openWidgetDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = getLayoutPosition();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }
    }
}
