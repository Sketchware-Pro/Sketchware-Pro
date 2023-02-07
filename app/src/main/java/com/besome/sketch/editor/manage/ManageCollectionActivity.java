package com.besome.sketch.editor.manage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.beans.WidgetCollectionBean;
import com.besome.sketch.editor.manage.font.AddFontCollectionActivity;
import com.besome.sketch.editor.manage.image.AddImageCollectionActivity;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.FB;
import a.a.a.Mp;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Pp;
import a.a.a.Qp;
import a.a.a.Rp;
import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.bB;
import a.a.a.kq;
import a.a.a.mB;
import a.a.a.wq;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;

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

    private Timer soundPlaybackTimeCounter = new Timer();
    private MediaPlayer mediaPlayer;
    private int D = -1;
    private int E = -1;
    private LinearLayout actionButtonGroup;
    private boolean hasDeletedWidget = false;
    private boolean selectingToBeDeletedItems = false;
    private CategoryAdapter categoryAdapter;
    private CollectionAdapter collectionAdapter;
    private RecyclerView collection;
    private ArrayList<ProjectResourceBean> images;
    private ArrayList<ProjectResourceBean> sounds;
    private ArrayList<ProjectResourceBean> fonts;
    private ArrayList<WidgetCollectionBean> widgets;
    private ArrayList<BlockCollectionBean> blocks;
    private ArrayList<MoreBlockCollectionBean> moreBlocks;
    private TextView noItemsNote;
    private FloatingActionButton fab;
    private String sc_id;

    private static String getCategoryLabel(Context context, int position) {
        switch (position) {
            case 0:
                return Helper.getResString(R.string.common_word_image);

            case 1:
                return Helper.getResString(R.string.common_word_sound);

            case 2:
                return Helper.getResString(R.string.common_word_font);

            case 3:
                return Helper.getResString(R.string.common_word_widget);

            case 4:
                return Helper.getResString(R.string.common_word_block);

            default:
                return Helper.getResString(R.string.common_word_moreblock);
        }
    }

    private static int getCategoryIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_picture_48dp;

            case 1:
                return R.drawable.ic_sound_wave_48dp;

            case 2:
                return R.drawable.ic_font_48dp;

            case 3:
                return R.drawable.collage_96;

            case 4:
                return R.drawable.block_96_blue;

            default:
                return R.drawable.more_block_96dp;
        }
    }

    private void showAddImageDialog() {
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, REQUEST_CODE_ADD_IMAGE_DIALOG);
    }

    private void showAddSoundDialog() {
        stopMusicPlayback(sounds);
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", sounds);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, REQUEST_CODE_ADD_SOUND_DIALOG);
    }

    private void showAddFontDialog() {
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", fonts);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, REQUEST_CODE_ADD_FONT_DIALOG);
    }

    private int getBlockIcon(BlockBean block) {
        switch (block.type) {
            case "c":
                return R.drawable.fav_block_c_96dp;

            case "b":
                return R.drawable.fav_block_boolean_96dp;

            case "f":
                return R.drawable.fav_block_final_96dp;

            case "e":
                return R.drawable.fav_block_e_96dp;

            case "d":
                return R.drawable.fav_block_number_96dp;

            case "s":
                return R.drawable.fav_block_string_96dp;

            default:
                return R.drawable.fav_block_command_96dp;
        }
    }

    private void setMusicCoverOnImageView(String filePath, ImageView target) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(filePath);
        byte[] embeddedPicture = metadataRetriever.getEmbeddedPicture();
        if (embeddedPicture != null) {
            Glide.with(getApplicationContext()).load(embeddedPicture).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    target.setImageDrawable(glideDrawable);
                }
            });
        } else {
            target.setImageResource(R.drawable.default_album_art_200dp);
            target.setBackgroundResource(R.drawable.bg_outline_album);
        }

        try {
            metadataRetriever.release();
        } catch (IOException e) {
            String message = "Couldn't close file " + filePath;
            LogUtil.e("ManageCollectionActivity", message, e);
            SketchwareUtil.toastError(message, Toast.LENGTH_LONG);
        }
    }

    private void stopMusicPlayback(ArrayList<ProjectResourceBean> sounds) {
        soundPlaybackTimeCounter.cancel();
        if (E != -1) {
            sounds.get(E).curSoundPosition = 0;
            E = -1;
            D = -1;
            collectionAdapter.notifyDataSetChanged();
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void changeDeletingItemsState(boolean deletingItems) {
        selectingToBeDeletedItems = deletingItems;
        invalidateOptionsMenu();
        unselectToBeDeletedItems();
        if (selectingToBeDeletedItems) {
            stopMusicPlayback(sounds);
            actionButtonGroup.setVisibility(View.VISIBLE);
        } else {
            actionButtonGroup.setVisibility(View.GONE);
            if (categoryAdapter.currentItemId == 3 || categoryAdapter.currentItemId == 4) {
                fab.setVisibility(View.GONE);
            }
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private int getLengthOfSong(String filePath) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(filePath);
        return (int) Long.parseLong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
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
        stopMusicPlayback(sounds);
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

    private void deleteSelectedToBeDeletedItems() {
        for (int i = 0; i < categoryAdapter.getItemCount(); i++) {
            switch (i) {
                case 0:
                    for (ProjectResourceBean bean : images) {
                        if (bean.isSelected) {
                            Op.g().a(bean.resName, false);
                        }
                    }

                    Op.g().e();
                    loadImages();
                    break;

                case 1:
                    for (ProjectResourceBean bean : sounds) {
                        if (bean.isSelected) {
                            Qp.g().a(bean.resName, false);
                        }
                    }

                    Qp.g().e();
                    loadSounds();
                    break;

                case 2:
                    for (ProjectResourceBean bean : fonts) {
                        if (bean.isSelected) {
                            Np.g().a(bean.resName, false);
                        }
                    }

                    Np.g().e();
                    loadFonts();
                    break;

                case 3:
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
                    break;

                case 4:
                    for (BlockCollectionBean bean : blocks) {
                        if (bean.isSelected) {
                            Mp.h().a(bean.name, false);
                        }
                    }

                    Mp.h().e();
                    loadBlocks();
                    break;

                default:
                    for (MoreBlockCollectionBean bean : moreBlocks) {
                        if (bean.isSelected) {
                            Pp.h().a(bean.name, false);
                        }
                    }

                    Pp.h().e();
                    loadMoreBlocks();
                    break;
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
        return categoryAdapter.currentItemId;
    }

    private int getGridLayoutColumnCount() {
        int var1 = (int) ((float) getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density) / 100;
        int var2 = var1;
        if (var1 > 2) {
            var2 = var1 - 1;
        }

        return var2;
    }

    private void scheduleSoundPlaybackTimeCounter(int position) {
        soundPlaybackTimeCounter = new Timer();
        soundPlaybackTimeCounter.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (mediaPlayer == null) {
                        soundPlaybackTimeCounter.cancel();
                    } else {
                        CollectionAdapter.SoundCollectionViewHolder viewHolder = (CollectionAdapter.SoundCollectionViewHolder) collection.findViewHolderForAdapterPosition(position);
                        int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        viewHolder.currentPosition.setText(String.format("%d:%02d", currentPosition / 60, currentPosition % 60));
                        viewHolder.playbackProgress.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                    }
                });
            }
        }, 100L, 100L);
    }

    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_actionbar_title_manager_collection));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        noItemsNote = findViewById(R.id.tv_no_collections);
        noItemsNote.setText(Helper.getResString(R.string.event_message_no_events));
        RecyclerView categories = findViewById(R.id.category_list);
        categories.setHasFixedSize(true);
        categories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        ((SimpleItemAnimator) categories.getItemAnimator()).setSupportsChangeAnimations(false);
        categoryAdapter = new CategoryAdapter();
        categories.setAdapter(categoryAdapter);
        collection = findViewById(R.id.collection_list);
        collection.setHasFixedSize(true);
        collectionAdapter = new CollectionAdapter(collection);
        collection.setAdapter(collectionAdapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        actionButtonGroup = findViewById(R.id.layout_btn_group);

        Button delete = findViewById(R.id.btn_delete);
        Button cancel = findViewById(R.id.btn_cancel);
        delete.setText(Helper.getResString(R.string.common_word_delete));
        cancel.setText(Helper.getResString(R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_IMAGE_DIALOG:
            case REQUEST_CODE_SHOW_IMAGE_DETAILS:
                loadImages();
                break;

            case REQUEST_CODE_ADD_SOUND_DIALOG:
            case REQUEST_CODE_SHOW_SOUND_DETAILS:
                loadSounds();
                break;

            case REQUEST_CODE_ADD_FONT_DIALOG:
            case REQUEST_CODE_SHOW_FONT_DETAILS:
                loadFonts();
                break;

            case REQUEST_CODE_SHOW_WIDGET_DETAILS:
                loadWidgets();
                break;

            case REQUEST_CODE_SHOW_BLOCK_DETAILS:
                loadBlocks();
                break;

            case REQUEST_CODE_SHOW_MORE_BLOCK_DETAILS:
                loadMoreBlocks();
                break;

            default:
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (hasDeletedWidget) {
            setResult(RESULT_OK);
            finish();
        }

        super.onBackPressed();
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
            handleFabOnClick(categoryAdapter.currentItemId);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (collectionAdapter.currentViewType == 0) {
            ((GridLayoutManager) collection.getLayoutManager()).setSpanCount(getGridLayoutColumnCount());
            collection.requestLayout();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!j()) {
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
    public void onDestroy() {
        stopMusicPlayback(sounds);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_collection_delete) {
            changeDeletingItemsState(!selectingToBeDeletedItems);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            soundPlaybackTimeCounter.cancel();
            mediaPlayer.pause();
            sounds.get(E).curSoundPosition = mediaPlayer.getCurrentPosition();
        }
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!j()) {
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

        if (categoryAdapter.currentItemId == -1) {
            collectionAdapter.currentViewType = 0;
            collectionAdapter.setData(images);
            collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), getGridLayoutColumnCount()));
            categoryAdapter.currentItemId = 0;
            categoryAdapter.notifyDataSetChanged();
        }

        if (collectionAdapter != null) {
            collectionAdapter.notifyDataSetChanged();
        }
    }

    private void loadImages() {
        images = Op.g().f();
        if (categoryAdapter.currentItemId == 0) {
            collectionAdapter.setData(images);
            collectionAdapter.currentViewType = 0;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadSounds() {
        sounds = Qp.g().f();
        if (categoryAdapter.currentItemId == 1) {
            collectionAdapter.setData(sounds);
            collectionAdapter.currentViewType = 1;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadFonts() {
        fonts = Np.g().f();
        if (categoryAdapter.currentItemId == 2) {
            collectionAdapter.setData(fonts);
            collectionAdapter.currentViewType = 2;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadWidgets() {
        widgets = Rp.h().f();
        if (categoryAdapter.currentItemId == 3) {
            collectionAdapter.setData(widgets);
            collectionAdapter.currentViewType = 3;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadBlocks() {
        blocks = Mp.h().f();
        if (categoryAdapter.currentItemId == 4) {
            collectionAdapter.setData(blocks);
            collectionAdapter.currentViewType = 4;
        }

        collectionAdapter.notifyDataSetChanged();
    }

    private void loadMoreBlocks() {
        moreBlocks = Pp.h().f();
        if (categoryAdapter.currentItemId == 5) {
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

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        private int currentItemId;

        public CategoryAdapter() {
            currentItemId = -1;
        }

        @Override
        public int getItemCount() {
            return 6;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
            holder.name.setText(ManageCollectionActivity.getCategoryLabel(getApplicationContext(), position));
            holder.icon.setImageResource(ManageCollectionActivity.getCategoryIcon(position));
            ViewPropertyAnimatorCompat var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (currentItemId == position) {
                var3 = ViewCompat.animate(holder.icon);
                var3.scaleX(1.0F);
                var3.scaleY(1.0F);
                var3.setDuration(300L);
                var3.setInterpolator(new AccelerateInterpolator());
                var3.start();
                var3 = ViewCompat.animate(holder.icon);
                var3.scaleX(1.0F);
                var3.scaleY(1.0F);
                var3.setDuration(300L);
                var3.setInterpolator(new AccelerateInterpolator());
                var3.start();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                var4 = new ColorMatrix();
                var4.setSaturation(1.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            } else {
                var3 = ViewCompat.animate(holder.icon);
                var3.scaleX(0.8F);
                var3.scaleY(0.8F);
                var3.setDuration(300L);
                var3.setInterpolator(new DecelerateInterpolator());
                var3.start();
                var3 = ViewCompat.animate(holder.icon);
                var3.scaleX(0.8F);
                var3.scaleY(0.8F);
                var3.setDuration(300L);
                var3.setInterpolator(new DecelerateInterpolator());
                var3.start();
                holder.pointerLeft.setVisibility(View.GONE);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            }
        }

        @Override
        @NonNull
        public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public final ImageView icon;
            public final TextView name;
            public final View pointerLeft;

            public ViewHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_icon);
                name = itemView.findViewById(R.id.tv_name);
                pointerLeft = itemView.findViewById(R.id.pointer_left);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    int layoutPosition = getLayoutPosition();
                    if (layoutPosition != -1 && layoutPosition != currentItemId) {
                        if (currentItemId == 1) {
                            stopMusicPlayback(sounds);
                        }

                        notifyItemChanged(currentItemId);
                        currentItemId = layoutPosition;
                        notifyItemChanged(currentItemId);
                        collection.removeAllViews();
                        collectionAdapter.currentViewType = currentItemId;
                        switch (currentItemId) {
                            case 0:
                                collectionAdapter.setData(images);
                                break;

                            case 1:
                                collectionAdapter.setData(sounds);
                                break;

                            case 2:
                                collectionAdapter.setData(fonts);
                                break;

                            case 3:
                                collectionAdapter.setData(widgets);
                                break;

                            case 4:
                                collectionAdapter.setData(blocks);
                                break;

                            default:
                                collectionAdapter.setData(moreBlocks);
                                break;
                        }

                        if (collectionAdapter.currentViewType == 0) {
                            collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), getGridLayoutColumnCount()));
                            // FloatingActionButton#show()
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
        }
    }

    private class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int lastSelectedItemPosition;
        private int currentViewType;
        private ArrayList<? extends SelectableBean> currentCollectionTypeItems;

        public CollectionAdapter(RecyclerView target) {
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
                    .load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + bean.resFullName)
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(holder.image));

            holder.name.setText(bean.resName);
            holder.checkBox.setChecked(bean.isSelected);
        }

        private void onBindViewHolder(SoundCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            String soundFilePath = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            if (selectingToBeDeletedItems) {
                holder.album.setVisibility(View.GONE);
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                setMusicCoverOnImageView(soundFilePath, holder.album);
                holder.album.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            int soundPositionInS = bean.curSoundPosition / 1000;
            if (bean.totalSoundDuration == 0) {
                bean.totalSoundDuration = getLengthOfSong(soundFilePath);
            }

            int totalSoundDurationInS = bean.totalSoundDuration / 1000;
            holder.currentPosition.setText(String.format("%d:%02d", soundPositionInS / 60, soundPositionInS % 60));
            holder.totalDuration.setText(String.format("%d:%02d", totalSoundDurationInS / 60, totalSoundDurationInS % 60));
            holder.checkBox.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            if (E == position) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    holder.play.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.play.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.play.setImageResource(R.drawable.circled_play_96_blue);
            }

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

            holder.widgetIcon.setImageResource(ViewBean.getViewTypeResId(((ViewBean) bean.widgets.get(0)).type));
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

            int blockId = 0;
            Rs block = new Rs(getBaseContext(), 0, bean.spec, " ", "definedFunc");
            holder.blockArea.addView(block);
            Iterator<String> spec = FB.c(bean.spec).iterator();

            while (true) {
                Rs specBlock;
                while (true) {
                    String specPart;
                    do {
                        if (!spec.hasNext()) {
                            block.k();
                            return;
                        }

                        specPart = spec.next();
                    } while (specPart.charAt(0) != '%');

                    if (specPart.charAt(1) == 'b') {
                        specBlock = new Rs(getBaseContext(), blockId + 1, specPart.substring(3), "b", "getVar");
                        break;
                    }

                    if (specPart.charAt(1) == 'd') {
                        specBlock = new Rs(getBaseContext(), blockId + 1, specPart.substring(3), "d", "getVar");
                        break;
                    }

                    if (specPart.charAt(1) == 's') {
                        specBlock = new Rs(getBaseContext(), blockId + 1, specPart.substring(3), "s", "getVar");
                        break;
                    }

                    if (specPart.charAt(1) == 'm') {
                        String var8 = specPart.substring(specPart.lastIndexOf(".") + 1);
                        String var7 = specPart.substring(specPart.indexOf(".") + 1, specPart.lastIndexOf("."));
                        String type = kq.a(var7);
                        specBlock = new Rs(getBaseContext(), blockId + 1, var8, type, kq.b(var7), "getVar");
                        break;
                    }
                }

                holder.blockArea.addView(specBlock);
                block.a((Ts) block.V.get(blockId), specBlock);
                blockId++;
            }
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
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    return new ImageCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));

                case 1:
                    return new SoundCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));

                case 2:
                    return new FontCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_font_list_item, parent, false));

                case 3:
                    return new WidgetCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_widget_list_item, parent, false));

                case 4:
                    return new BlockCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_block_list_item, parent, false));

                default:
                    return new MoreBlockCollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_more_block_list_item, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = holder.getItemViewType();

            switch (viewType) {
                case 0:
                    onBindViewHolder((ImageCollectionViewHolder) holder, position);
                    break;

                case 1:
                    onBindViewHolder((SoundCollectionViewHolder) holder, position);
                    break;

                case 2:
                    onBindViewHolder((FontCollectionViewHolder) holder, position);
                    break;

                case 3:
                    onBindViewHolder((WidgetCollectionViewHolder) holder, position);
                    break;

                case 4:
                    onBindViewHolder((BlockCollectionViewHolder) holder, position);
                    break;

                case 5:
                    onBindViewHolder((MoreBlockCollectionViewHolder) holder, position);
                    break;
            }
        }

        private class BlockCollectionViewHolder extends RecyclerView.ViewHolder {

            public final CardView cardView;
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

        private class FontCollectionViewHolder extends RecyclerView.ViewHolder {

            public final CardView cardView;
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

        private class ImageCollectionViewHolder extends RecyclerView.ViewHolder {

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

        private class MoreBlockCollectionViewHolder extends RecyclerView.ViewHolder {

            public final CardView cardView;
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

        private class SoundCollectionViewHolder extends RecyclerView.ViewHolder {

            public final ProgressBar playbackProgress;
            public final TextView totalDuration;
            public final LinearLayout deleteContainer;
            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView album;
            public final ImageView delete;
            public final TextView name;
            public final ImageView play;
            public final TextView currentPosition;

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
                        int position = getLayoutPosition();

                        if (E == position) {
                            if (mediaPlayer != null) {
                                if (mediaPlayer.isPlaying()) {
                                    soundPlaybackTimeCounter.cancel();
                                    mediaPlayer.pause();
                                    ((ProjectResourceBean) currentCollectionTypeItems.get(E)).curSoundPosition = mediaPlayer.getCurrentPosition();
                                    collectionAdapter.notifyItemChanged(E);
                                } else {
                                    mediaPlayer.start();
                                    scheduleSoundPlaybackTimeCounter(position);
                                    collectionAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                soundPlaybackTimeCounter.cancel();
                                mediaPlayer.pause();
                                mediaPlayer.release();
                            }

                            if (D != -1) {
                                ((ProjectResourceBean) currentCollectionTypeItems.get(D)).curSoundPosition = 0;
                                collectionAdapter.notifyItemChanged(D);
                            }

                            E = position;
                            D = position;
                            collectionAdapter.notifyItemChanged(E);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(3);
                            mediaPlayer.setOnPreparedListener(mp -> {
                                mediaPlayer.start();
                                scheduleSoundPlaybackTimeCounter(position);
                                collectionAdapter.notifyItemChanged(E);
                            });
                            mediaPlayer.setOnCompletionListener(mp -> {
                                soundPlaybackTimeCounter.cancel();
                                ((ProjectResourceBean) currentCollectionTypeItems.get(E)).curSoundPosition = 0;
                                collectionAdapter.notifyItemChanged(E);
                                E = -1;
                                D = -1;
                            });

                            try {
                                mediaPlayer.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + ((ProjectResourceBean) currentCollectionTypeItems.get(E)).resFullName);
                                mediaPlayer.prepare();
                            } catch (Exception e) {
                                E = -1;
                                collectionAdapter.notifyItemChanged(E);
                                e.printStackTrace();
                            }
                        }
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
        }

        private class WidgetCollectionViewHolder extends RecyclerView.ViewHolder {

            public final CardView cardView;
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
