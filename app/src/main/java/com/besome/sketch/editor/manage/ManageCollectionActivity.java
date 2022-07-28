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
import android.view.View.OnClickListener;
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

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.besome.sketch.editor.manage.font.AddFontCollectionActivity;
import com.besome.sketch.editor.manage.image.AddImageCollectionActivity;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.Bi;
import a.a.a.FB;
import a.a.a.Mp;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Pp;
import a.a.a.Qp;
import a.a.a.Rp;
import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.Ze;
import a.a.a.bB;
import a.a.a.ef;
import a.a.a.kq;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xo;

public class ManageCollectionActivity extends BaseAppCompatActivity implements OnClickListener {

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
        String label;
        if (position == 0) {
            label = xB.b().a(context, R.string.common_word_image);
        } else if (position == 1) {
            label = xB.b().a(context, R.string.common_word_sound);
        } else if (position == 2) {
            label = xB.b().a(context, R.string.common_word_font);
        } else if (position == 3) {
            label = xB.b().a(context, R.string.common_word_widget);
        } else if (position == 4) {
            label = xB.b().a(context, R.string.common_word_block);
        } else {
            label = xB.b().a(context, R.string.common_word_moreblock);
        }

        return label;
    }

    private static int getCategoryIcon(int position) {
        if (position == 0) {
            position = R.drawable.ic_picture_48dp;
        } else if (position == 1) {
            position = R.drawable.ic_sound_wave_48dp;
        } else if (position == 2) {
            position = R.drawable.ic_font_48dp;
        } else if (position == 3) {
            position = R.drawable.collage_96;
        } else if (position == 4) {
            position = R.drawable.block_96_blue;
        } else {
            position = R.drawable.more_block_96dp;
        }

        return position;
    }

    private void showAddImageDialog() {
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, 267);
    }

    private void showAddSoundDialog() {
        stopMusicPlayback(sounds);
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", sounds);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, 269);
    }

    private void showAddFontDialog() {
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", fonts);
        intent.putExtra("sc_id", sc_id);
        startActivityForResult(intent, 271);
    }

    private int getBlockIcon(BlockBean block) {
        if (block.type.equals("c")) {
            return R.drawable.fav_block_c_96dp;
        } else if (block.type.equals("b")) {
            return R.drawable.fav_block_boolean_96dp;
        } else if (block.type.equals("f")) {
            return R.drawable.fav_block_final_96dp;
        } else if (block.type.equals("e")) {
            return R.drawable.fav_block_e_96dp;
        } else if (block.type.equals("d")) {
            return R.drawable.fav_block_number_96dp;
        } else if (block.type.equals("s")) {
            return R.drawable.fav_block_string_96dp;
        } else {
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

        metadataRetriever.release();
    }

    private void stopMusicPlayback(ArrayList<ProjectResourceBean> sounds) {
        soundPlaybackTimeCounter.cancel();
        if (E != -1) {
            sounds.get(E).curSoundPosition = 0;
            E = -1;
            D = -1;
            collectionAdapter.c();
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

        collectionAdapter.c();
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
        startActivityForResult(intent, 268);
    }

    private void openSoundDetails(int position) {
        ProjectResourceBean editTarget = sounds.get(position);
        stopMusicPlayback(sounds);
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", sounds);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, 270);
    }

    private void openFontDetails(int position) {
        ProjectResourceBean editTarget = fonts.get(position);
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", fonts);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, 272);
    }

    private void openWidgetDetails(int position) {
        String widgetName = Rp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowWidgetCollectionActivity.class);
        intent.putExtra("widget_name", widgetName);
        startActivityForResult(intent, 273);
    }

    private void openBlockDetails(int position) {
        String blockName = Mp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, 274);
    }

    private void openMoreBlockDetails(int position) {
        String blockName = Pp.h().g().get(position);
        Intent intent = new Intent(getApplicationContext(), ShowMoreBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, 279);
    }

    private void deleteSelectedToBeDeletedItems() {
        for (int i = 0; i < categoryAdapter.a(); i++) {
            if (i == 0) {
                for (ProjectResourceBean bean : images) {
                    if (bean.isSelected) {
                        Op.g().a(bean.resName, false);
                    }
                }

                Op.g().e();
                loadImages();
            } else if (i == 1) {
                for (ProjectResourceBean bean : sounds) {
                    if (bean.isSelected) {
                        Qp.g().a(bean.resName, false);
                    }
                }

                Qp.g().e();
                loadSounds();
            } else if (i == 2) {
                for (ProjectResourceBean bean : fonts) {
                    if (bean.isSelected) {
                        Np.g().a(bean.resName, false);
                    }
                }

                Np.g().e();
                loadFonts();
            } else if (i == 3) {
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
            } else if (i == 4) {
                for (BlockCollectionBean bean : blocks) {
                    if (bean.isSelected) {
                        Mp.h().a(bean.name, false);
                    }
                }

                Mp.h().e();
                loadBlocks();
            } else {
                for (MoreBlockCollectionBean bean : moreBlocks) {
                    if (bean.isSelected) {
                        Pp.h().a(bean.name, false);
                    }
                }

                Pp.h().e();
                loadMoreBlocks();
            }
        }

        unselectToBeDeletedItems();
        changeDeletingItemsState(false);
        int id = getCurrentCategoryItemId();
        if (id == 0 || id == 1 || id == 2) {
            fab.f();
        }

        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_complete_delete), 1).show();
        collectionAdapter.c();
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
                        CollectionAdapter.SoundCollectionViewHolder viewHolder = (CollectionAdapter.SoundCollectionViewHolder) collection.d(position);
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
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_collection));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        noItemsNote = findViewById(R.id.tv_no_collections);
        noItemsNote.setText(xB.b().a(getApplicationContext(), R.string.event_message_no_events));
        RecyclerView categories = findViewById(R.id.category_list);
        categories.setHasFixedSize(true);
        categories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        // ((androidx.recyclerview.widget.SimpleItemAnimator) …).setSupportsChangeAnimations(false);
        ((Bi) categories.getItemAnimator()).a(false);
        categoryAdapter = new CategoryAdapter(this);
        categories.setAdapter(categoryAdapter);
        collection = findViewById(R.id.collection_list);
        collection.setHasFixedSize(true);
        collectionAdapter = new CollectionAdapter(this, collection);
        collection.setAdapter(collectionAdapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        actionButtonGroup = findViewById(R.id.layout_btn_group);

        Button delete = findViewById(R.id.btn_delete);
        Button cancel = findViewById(R.id.btn_cancel);
        delete.setText(xB.b().a(getApplicationContext(), R.string.common_word_delete));
        cancel.setText(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        if (!j.h()) {
            xo.a(getApplicationContext());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 267:
            case 268:
                loadImages();
                break;

            case 269:
            case 270:
                loadSounds();
                break;

            case 271:
            case 272:
                loadFonts();
                break;

            case 273:
                loadWidgets();
                break;

            case 274:
                loadBlocks();
                break;

            case 279:
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
            // GridLayoutManager#setSpanCount(int)
            ((GridLayoutManager) collection.getLayoutManager()).d(getGridLayoutColumnCount());
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

        d.setScreenName(ManageCollectionActivity.class.getSimpleName());
        d.send((new ScreenViewBuilder()).build());
        if (collectionAdapter != null) {
            // RecyclerView.ViewHolder#clearReturnedFromScrapFlag()
            collectionAdapter.c();
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
            collectionAdapter.a(images);
            collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), getGridLayoutColumnCount()));
            categoryAdapter.currentItemId = 0;
            categoryAdapter.c();
        }

        if (collectionAdapter != null) {
            collectionAdapter.c();
        }
    }

    private void loadImages() {
        images = Op.g().f();
        if (categoryAdapter.currentItemId == 0) {
            collectionAdapter.a(images);
            collectionAdapter.currentViewType = 0;
        }

        collectionAdapter.c();
    }

    private void loadSounds() {
        sounds = Qp.g().f();
        if (categoryAdapter.currentItemId == 1) {
            collectionAdapter.a(sounds);
            collectionAdapter.currentViewType = 1;
        }

        collectionAdapter.c();
    }

    private void loadFonts() {
        fonts = Np.g().f();
        if (categoryAdapter.currentItemId == 2) {
            collectionAdapter.a(fonts);
            collectionAdapter.currentViewType = 2;
        }

        collectionAdapter.c();
    }

    private void loadWidgets() {
        widgets = Rp.h().f();
        if (categoryAdapter.currentItemId == 3) {
            collectionAdapter.a(widgets);
            collectionAdapter.currentViewType = 3;
        }

        collectionAdapter.c();
    }

    private void loadBlocks() {
        blocks = Mp.h().f();
        if (categoryAdapter.currentItemId == 4) {
            collectionAdapter.a(blocks);
            collectionAdapter.currentViewType = 4;
        }

        collectionAdapter.c();
    }

    private void loadMoreBlocks() {
        moreBlocks = Pp.h().f();
        if (categoryAdapter.currentItemId == 5) {
            collectionAdapter.a(moreBlocks);
            collectionAdapter.currentViewType = 5;
        }

        collectionAdapter.c();
    }

    private void unselectToBeDeletedItems() {
        int id = getCurrentCategoryItemId();

        if (id == 0) {
            for (ProjectResourceBean bean : images) {
                bean.isSelected = false;
            }
        } else if (id == 1) {
            for (ProjectResourceBean bean : sounds) {
                bean.isSelected = false;
            }
        } else if (id == 2) {
            for (ProjectResourceBean bean : fonts) {
                bean.isSelected = false;
            }
        } else if (id == 3) {
            for (WidgetCollectionBean bean : widgets) {
                bean.isSelected = false;
            }
        } else {
            for (BlockCollectionBean bean : blocks) {
                bean.isSelected = false;
            }
        }
    }

    private class CategoryAdapter extends RecyclerView.a<CategoryAdapter.ViewHolder> {

        private int currentItemId;
        public final ManageCollectionActivity d;

        public CategoryAdapter(ManageCollectionActivity var1) {
            d = var1;
            currentItemId = -1;
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return 6;
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(CategoryAdapter.ViewHolder holder, int position) {
            holder.name.setText(ManageCollectionActivity.getCategoryLabel(d.getApplicationContext(), position));
            holder.icon.setImageResource(ManageCollectionActivity.getCategoryIcon(position));
            ef var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (currentItemId == position) {
                var3 = Ze.a(holder.icon);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                var3 = Ze.a(holder.icon);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                var4 = new ColorMatrix();
                var4.setSaturation(1.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            } else {
                var3 = Ze.a(holder.icon);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                var3 = Ze.a(holder.icon);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                holder.pointerLeft.setVisibility(View.GONE);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public CategoryAdapter.ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.v implements OnClickListener {

            public final ImageView icon;
            public final TextView name;
            public final View pointerLeft;
            public final CategoryAdapter w;

            public ViewHolder(CategoryAdapter var1, View itemView) {
                super(itemView);
                w = var1;
                icon = itemView.findViewById(R.id.img_icon);
                name = itemView.findViewById(R.id.tv_name);
                pointerLeft = itemView.findViewById(R.id.pointer_left);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    if (j() != -1 && j() != w.currentItemId) {
                        if (w.currentItemId == 1) {
                            stopMusicPlayback(w.d.sounds);
                        }

                        w.c(w.currentItemId);
                        w.currentItemId = j();
                        w.c(w.currentItemId);
                        w.d.collection.removeAllViews();
                        w.d.collectionAdapter.currentViewType = w.currentItemId;
                        if (w.currentItemId == 0) {
                            w.d.collectionAdapter.a(w.d.images);
                        } else if (w.currentItemId == 1) {
                            w.d.collectionAdapter.a(w.d.sounds);
                        } else if (w.currentItemId == 2) {
                            w.d.collectionAdapter.a(w.d.fonts);
                        } else if (w.currentItemId == 3) {
                            w.d.collectionAdapter.a(w.d.widgets);
                        } else if (w.currentItemId == 4) {
                            w.d.collectionAdapter.a(w.d.blocks);
                        } else {
                            w.d.collectionAdapter.a(w.d.moreBlocks);
                        }

                        if (w.d.collectionAdapter.currentViewType == 0) {
                            w.d.collection.setLayoutManager(new GridLayoutManager(getApplicationContext(), w.d.getGridLayoutColumnCount()));
                            w.d.fab.f();
                        } else {
                            w.d.collection.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
                            if (w.d.collectionAdapter.currentViewType != 1 && w.d.collectionAdapter.currentViewType != 2) {
                                w.d.fab.c();
                            } else {
                                w.d.fab.f();
                            }
                        }

                        w.d.collectionAdapter.c();
                    }
                }
            }
        }
    }

    private class CollectionAdapter extends RecyclerView.a<RecyclerView.v> {

        private int lastSelectedItemPosition;
        private int currentViewType;
        private ArrayList<? extends SelectableBean> currentCollectionTypeItems;
        public final ManageCollectionActivity f;

        public CollectionAdapter(ManageCollectionActivity var1, RecyclerView target) {
            f = var1;
            lastSelectedItemPosition = -1;
            currentViewType = -1;
            // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
            target.a(new RecyclerView.m() {
                @Override
                // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
                public void a(RecyclerView recyclerView, int dx, int dy) {
                    super.a(recyclerView, dx, dy);
                    if (currentViewType == 3 || currentViewType == 4 || currentViewType == 5) {
                        return;
                    }
                    if (dy > 2) {
                        if (!fab.isEnabled()) {
                            return;
                        }
                        fab.c();
                    } else if (dy < -2 && fab.isEnabled()) {
                        fab.f();
                    }
                }
            });
            currentCollectionTypeItems = new ArrayList<>();
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return currentCollectionTypeItems.size();
        }

        public void a(BlockCollectionViewHolder holder, int position) {
            BlockCollectionBean var3 = (BlockCollectionBean) currentCollectionTypeItems.get(position);
            if (f.selectingToBeDeletedItems) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
                holder.blockIcon.setVisibility(View.GONE);
            } else {
                holder.blockIcon.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (var3.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.blockIcon.setImageResource(getBlockIcon(var3.blocks.get(0)));
            holder.name.setText(var3.name);
            holder.checkBox.setChecked(var3.isSelected);
        }

        public void a(FontCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (f.selectingToBeDeletedItems) {
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
                holder.preview.setText(xB.b().a(f.getApplicationContext(), R.string.design_manager_font_description_example_sentence));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void a(ImageCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (f.selectingToBeDeletedItems) {
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

            Glide.with(f.getApplicationContext()).load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + bean.resFullName)
                    .asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.image));
            holder.name.setText(bean.resName);
            holder.checkBox.setChecked(bean.isSelected);
        }

        public void a(MoreBlockCollectionViewHolder holder, int position) {
            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) currentCollectionTypeItems.get(position);
            if (f.selectingToBeDeletedItems) {
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
            position = 0;
            Rs header = new Rs(f.getBaseContext(), 0, bean.spec, " ", "definedFunc");
            holder.blockArea.addView(header);
            Iterator<String> var5 = FB.c(bean.spec).iterator();

            while (true) {
                Rs block;
                while (true) {
                    String var6;
                    do {
                        if (!var5.hasNext()) {
                            header.k();
                            return;
                        }

                        var6 = var5.next();
                    } while (var6.charAt(0) != '%');

                    if (var6.charAt(1) == 'b') {
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "b", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'd') {
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "d", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 's') {
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "s", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'm') {
                        String var8 = var6.substring(var6.lastIndexOf(".") + 1);
                        String var7 = var6.substring(var6.indexOf(".") + 1, var6.lastIndexOf("."));
                        var6 = kq.a(var7);
                        block = new Rs(f.getBaseContext(), position + 1, var8, var6, kq.b(var7), "getVar");
                        break;
                    }
                }

                holder.blockArea.addView(block);
                header.a((Ts) header.V.get(position), block);
                ++position;
            }
        }

        public void a(SoundCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            String soundFilePath = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            if (f.selectingToBeDeletedItems) {
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
            if (f.E == position) {
                if (f.mediaPlayer != null && f.mediaPlayer.isPlaying()) {
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

        public void a(WidgetCollectionViewHolder holder, int position) {
            WidgetCollectionBean bean = (WidgetCollectionBean) currentCollectionTypeItems.get(position);
            if (f.selectingToBeDeletedItems) {
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

        public void a(ArrayList<? extends SelectableBean> beans) {
            currentCollectionTypeItems = beans;
            if (beans.size() <= 0) {
                f.noItemsNote.setVisibility(View.VISIBLE);
            } else {
                f.noItemsNote.setVisibility(View.GONE);
            }
        }

        @Override
        // RecyclerView.Adapter#getItemViewType(int)
        public int b(int position) {
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
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public RecyclerView.v b(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new ImageCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
            } else if (viewType == 1) {
                return new SoundCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
            } else if (viewType == 2) {
                return new FontCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_font_list_item, parent, false));
            } else if (viewType == 3) {
                return new WidgetCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_widget_list_item, parent, false));
            } else if (viewType == 4) {
                return new BlockCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_block_list_item, parent, false));
            } else {
                return new MoreBlockCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_more_block_list_item, parent, false));
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(RecyclerView.v holder, int position) {
            // RecyclerView.ViewHolder#getItemViewType()
            int viewType = holder.i();

            if (viewType == 0) {
                a((ImageCollectionViewHolder) holder, position);
            } else if (viewType == 1) {
                a((SoundCollectionViewHolder) holder, position);
            } else if (viewType == 2) {
                a((FontCollectionViewHolder) holder, position);
            } else if (viewType == 3) {
                a((WidgetCollectionViewHolder) holder, position);
            } else if (viewType == 4) {
                a((BlockCollectionViewHolder) holder, position);
            } else if (viewType == 5) {
                a((MoreBlockCollectionViewHolder) holder, position);
            }
        }

        private class BlockCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView blockIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public BlockCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                blockIcon = itemView.findViewById(R.id.img_block);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_block_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openBlockDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class FontCollectionViewHolder extends RecyclerView.v {

            public final CollectionAdapter A;
            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView fontIcon;
            public final ImageView delete;
            public final TextView name;
            public final TextView preview;
            public final LinearLayout deleteContainer;

            public FontCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                A = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                fontIcon = itemView.findViewById(R.id.img_font);
                fontIcon.setVisibility(View.GONE);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_font_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                preview = itemView.findViewById(R.id.tv_font_preview);
                preview.setText(xB.b().a(var1.f.getApplicationContext(), R.string.common_word_preview));
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openFontDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return false;
                });
            }
        }

        private class ImageCollectionViewHolder extends RecyclerView.v {

            public final CheckBox checkBox;
            public final TextView name;
            public final ImageView image;
            public final ImageView delete;
            public final ImageView ninePatchIcon;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public ImageCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                checkBox = itemView.findViewById(R.id.chk_select);
                name = itemView.findViewById(R.id.tv_image_name);
                image = itemView.findViewById(R.id.img);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                ninePatchIcon = itemView.findViewById(R.id.img_nine_patch);
                checkBox.setVisibility(View.GONE);
                image.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openImageDetails(lastSelectedItemPosition);
                    }
                });
                image.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class MoreBlockCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView delete;
            public final LinearLayout deleteContainer;
            public final TextView name;
            public final RelativeLayout blockArea;
            public final CollectionAdapter z;

            public MoreBlockCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                name = itemView.findViewById(R.id.tv_block_name);
                blockArea = itemView.findViewById(R.id.block_area);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openMoreBlockDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class SoundCollectionViewHolder extends RecyclerView.v {

            public final ProgressBar playbackProgress;
            public final TextView totalDuration;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter D;
            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView album;
            public final ImageView delete;
            public final TextView name;
            public final ImageView play;
            public final TextView currentPosition;

            public SoundCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                D = var1;
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
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        int position = j();

                        if (E == position) {
                            if (mediaPlayer != null) {
                                if (mediaPlayer.isPlaying()) {
                                    soundPlaybackTimeCounter.cancel();
                                    mediaPlayer.pause();
                                    ((ProjectResourceBean) currentCollectionTypeItems.get(E)).curSoundPosition = mediaPlayer.getCurrentPosition();
                                    collectionAdapter.c(E);
                                } else {
                                    mediaPlayer.start();
                                    scheduleSoundPlaybackTimeCounter(position);
                                    collectionAdapter.c();
                                }
                            }
                        } else {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                soundPlaybackTimeCounter.cancel();
                                mediaPlayer.pause();
                                mediaPlayer.release();
                            }

                            if (ManageCollectionActivity.this.D != -1) {
                                ((ProjectResourceBean) currentCollectionTypeItems.get(ManageCollectionActivity.this.D)).curSoundPosition = 0;
                                collectionAdapter.c(ManageCollectionActivity.this.D);
                            }

                            E = position;
                            ManageCollectionActivity.this.D = position;
                            collectionAdapter.c(E);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(3);
                            mediaPlayer.setOnPreparedListener(mp -> {
                                mediaPlayer.start();
                                scheduleSoundPlaybackTimeCounter(position);
                                collectionAdapter.c(E);
                            });
                            mediaPlayer.setOnCompletionListener(mp -> {
                                soundPlaybackTimeCounter.cancel();
                                ((ProjectResourceBean) currentCollectionTypeItems.get(E)).curSoundPosition = 0;
                                collectionAdapter.c(E);
                                E = -1;
                                ManageCollectionActivity.this.D = -1;
                            });

                            try {
                                mediaPlayer.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + ((ProjectResourceBean) currentCollectionTypeItems.get(E)).resFullName);
                                mediaPlayer.prepare();
                            } catch (Exception e) {
                                E = -1;
                                collectionAdapter.c(E);
                                e.printStackTrace();
                            }
                        }
                    }
                });
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openSoundDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class WidgetCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView widgetIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public WidgetCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                widgetIcon = itemView.findViewById(R.id.img_widget);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_widget_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.selectingToBeDeletedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                    } else {
                        openWidgetDetails(lastSelectedItemPosition);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    changeDeletingItemsState(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }
    }
}
