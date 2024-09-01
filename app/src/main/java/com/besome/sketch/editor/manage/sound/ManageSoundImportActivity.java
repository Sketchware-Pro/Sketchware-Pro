package com.besome.sketch.editor.manage.sound;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;

public class ManageSoundImportActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public QB nameValidator;
    public boolean B;
    public ImageView img_album;
    public ImageView img_play;
    public ImageView img_backbtn;
    public TextView tv_currentnum;
    public TextView tv_totalnum;
    public TextView tv_sendbtn;
    public TextView tv_samename;
    public RecyclerView recycler_list;
    public EasyDeleteEditText ed_input;
    public EditText ed_input_edittext;
    public CheckBox chk_samename;
    public ItemAdapter adapter;
    public ArrayList<ProjectResourceBean> projectSounds;
    public ArrayList<ProjectResourceBean> selectedCollections;
    public int selectedCollectionsSize = 0;
    public int selectedItem = 0;
    public Button btn_decide;
    public MediaPlayer mediaPlayer;

    public static ArrayList a(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.selectedCollections;
    }

    public static int b(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.selectedItem;
    }

    public static ImageView c(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.img_play;
    }

    public static TextView d(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.tv_currentnum;
    }

    public static EditText e(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.ed_input_edittext;
    }

    public static CheckBox f(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.chk_samename;
    }

    public static ItemAdapter g(ManageSoundImportActivity manageSoundImportActivity) {
        return manageSoundImportActivity.adapter;
    }

    public final ArrayList<String> getReservedSelectedCollectionNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            names.add(it.next().resName);
        }
        return names;
    }

    public final ArrayList<String> getReservedProjectSoundNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        Iterator<ProjectResourceBean> it = this.projectSounds.iterator();
        while (it.hasNext()) {
            names.add(it.next().resName);
        }
        return names;
    }

    public final boolean n() {
        ArrayList arrayList = new ArrayList();
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isDuplicateCollection) {
                arrayList.add(next.resName);
            }
        }
        if (arrayList.size() > 0) {
            String a = xB.b().a(getApplicationContext(), R.string.common_message_name_unavailable);
            Iterator it2 = arrayList.iterator();
            String str = "";
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (str.length() > 0) {
                    str = str + ", ";
                }
                str = str + str2;
            }
            bB.a(getApplicationContext(), a + "\n[" + str + "]", bB.TOAST_WARNING).show();
            return true;
        }
        return false;
    }

    public final boolean isNameValid() {
        return this.nameValidator.b();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            switch (v.getId()) {
                case R.id.btn_decide:
                    String obj = this.ed_input_edittext.getText().toString();
                    if (isNameValid()) {
                        if (this.chk_samename.isChecked()) {
                            int i = 0;
                            while (i < this.selectedCollections.size()) {
                                ProjectResourceBean projectResourceBean2 = this.selectedCollections.get(i);
                                StringBuilder sb = new StringBuilder();
                                sb.append(obj);
                                sb.append("_");
                                i++;
                                sb.append(i);
                                projectResourceBean2.resName = sb.toString();
                                projectResourceBean2.isDuplicateCollection = false;
                            }
                        } else {
                            ProjectResourceBean projectResourceBean = this.selectedCollections.get(this.selectedItem);
                            projectResourceBean.resName = obj;
                            projectResourceBean.isDuplicateCollection = false;
                        }
                        this.nameValidator.a(getReservedSelectedCollectionNames());
                        this.adapter.notifyDataSetChanged();
                    } else {
                        this.ed_input_edittext.setText(this.selectedCollections.get(this.selectedItem).resName);
                    }
                    break;
                case R.id.img_backbtn:
                    onBackPressed();
                    break;
                case R.id.img_play:
                    if (this.B) {
                        if (this.mediaPlayer.isPlaying()) {
                            pausePlayback();
                        } else {
                            this.mediaPlayer.start();
                            this.img_play.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                        }
                    }
                    break;
                case R.id.tv_sendbtn:
                    if (!n()) {
                        MediaPlayer mediaPlayer = this.mediaPlayer;
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            this.mediaPlayer.stop();
                            this.mediaPlayer.release();
                        }
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("results", this.selectedCollections);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        setContentView(R.layout.manage_sound_import);
        this.img_backbtn = findViewById(R.id.img_backbtn);
        this.img_backbtn.setOnClickListener(this);
        this.tv_currentnum = findViewById(R.id.tv_currentnum);
        this.tv_totalnum = findViewById(R.id.tv_totalnum);
        this.tv_sendbtn = findViewById(R.id.tv_sendbtn);
        this.tv_sendbtn.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        this.tv_sendbtn.setOnClickListener(this);
        this.tv_samename = findViewById(R.id.tv_samename);
        this.tv_samename.setText(xB.b().a(getApplicationContext(), R.string.design_manager_sound_title_apply_same_naming));
        this.adapter = new ItemAdapter();
        this.recycler_list = findViewById(R.id.recycler_list);
        this.recycler_list.setHasFixedSize(true);
        this.recycler_list.setAdapter(this.adapter);
        this.recycler_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.projectSounds = getIntent().getParcelableArrayListExtra("project_sounds");
        this.selectedCollections = getIntent().getParcelableArrayListExtra("selected_collections");
        this.selectedCollectionsSize = this.selectedCollections.size();
        this.tv_currentnum.setText(String.valueOf(1));
        this.tv_totalnum.setText(String.valueOf(this.selectedCollectionsSize));
        this.ed_input = findViewById(R.id.ed_input);
        this.ed_input_edittext = this.ed_input.getEditText();
        this.ed_input_edittext.setText(this.selectedCollections.get(0).resName);
        this.ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        this.ed_input.setHint(xB.b().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        this.nameValidator = new QB(getApplicationContext(), this.ed_input.getTextInputLayout(), uq.b, getReservedProjectSoundNames(), getReservedSelectedCollectionNames());
        this.chk_samename = findViewById(R.id.chk_samename);
        this.chk_samename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollections.size());
            } else {
                nameValidator.c(selectedCollections.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });
        this.btn_decide = findViewById(R.id.btn_decide);
        this.btn_decide.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        this.btn_decide.setOnClickListener(this);
        this.img_album = findViewById(R.id.img_album);
        this.img_play = findViewById(R.id.img_play);
        this.img_play.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mediaPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayback();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeLogic();
        showPreview(0);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageSoundImportActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void initializeLogic() {
        ArrayList duplicateCollections = new ArrayList();
        ArrayList notDuplicateCollections = new ArrayList();
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            ProjectResourceBean selectedCollection = it.next();
            if (isNameInUseByProjectSound(selectedCollection.resName)) {
                selectedCollection.isDuplicateCollection = true;
                duplicateCollections.add(selectedCollection);
            } else {
                selectedCollection.isDuplicateCollection = false;
                notDuplicateCollections.add(selectedCollection);
            }
        }
        if (duplicateCollections.size() > 0) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), bB.TOAST_NORMAL).show();
        }
        this.selectedCollections = new ArrayList<>();
        Iterator it2 = duplicateCollections.iterator();
        while (it2.hasNext()) {
            this.selectedCollections.add((ProjectResourceBean) it2.next());
        }
        Iterator it3 = notDuplicateCollections.iterator();
        while (it3.hasNext()) {
            this.selectedCollections.add((ProjectResourceBean) it3.next());
        }
    }

    public void pausePlayback() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && this.B && mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
            this.img_play.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout layout_item;
            public ImageView img_conflict;
            public ImageView img;
            public TextView tv_name;

            public ViewHolder(View itemView) {
                super(itemView);
                this.layout_item = itemView.findViewById(R.id.layout_item);
                this.img_conflict = itemView.findViewById(R.id.img_conflict);
                this.img = itemView.findViewById(R.id.img);
                this.tv_name = itemView.findViewById(R.id.tv_name);
                this.img.setOnClickListener(v -> {
                    if (!mB.a()) {
                        pausePlayback();
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        tv_currentnum.setText(String.valueOf(selectedItem + 1));
                        ed_input_edittext.setText(selectedCollections.get(selectedItem).resName);
                        if (chk_samename.isChecked()) {
                            nameValidator.c(null);
                            nameValidator.a(selectedCollections.size());
                        } else {
                            nameValidator.c(selectedCollections.get(selectedItem).resName);
                            nameValidator.a(1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

        public ItemAdapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ProjectResourceBean sound = ManageSoundImportActivity.this.selectedCollections.get(position);
            String str = sound.resFullName;
            int unused = ManageSoundImportActivity.this.selectedItem;
            if (sound.isDuplicateCollection) {
                viewHolder.img_conflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.img_conflict.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (position == ManageSoundImportActivity.this.selectedItem) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundColor(Color.WHITE);
            }
            try {
                ManageSoundImportActivity.this.loadSoundEmbeddedPicture(str, viewHolder.img, position);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            viewHolder.tv_name.setText(ManageSoundImportActivity.this.selectedCollections.get(position).resName);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_import_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return ManageSoundImportActivity.this.selectedCollections.size();
        }
    }

    public static int a(ManageSoundImportActivity manageSoundImportActivity, int i) {
        manageSoundImportActivity.selectedItem = i;
        return i;
    }

    public static void b(ManageSoundImportActivity manageSoundImportActivity, int i) throws IOException {
        manageSoundImportActivity.showPreview(i);
    }

    public final void showPreview(int i) {
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.mediaPlayer.setOnPreparedListener(mp -> {
            B = true;
            img_play.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        });
        this.mediaPlayer.setOnCompletionListener(mp -> B = false);
        try {
            String filePath = this.selectedCollections.get(i).resFullName;
            loadSoundEmbeddedPicture(filePath, this.img_album, -1);
            this.mediaPlayer.setDataSource(filePath);
            this.mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean isNameInUseByProjectSound(String str) {
        Iterator<ProjectResourceBean> it = this.projectSounds.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final void loadSoundEmbeddedPicture(String filePath, ImageView target, int position) throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(filePath);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(getApplicationContext()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        target.setImageDrawable(glideDrawable);
                    }
                });
            } else {
                target.setImageResource(R.drawable.default_album_art_200dp);
                if (position != -1 && this.selectedItem != position) {
                    target.setBackgroundResource(R.drawable.bg_outline_album);
                }
            }
        } catch (IllegalArgumentException unused) {
            target.setImageResource(R.drawable.default_album_art_200dp);
            if (position != -1 && this.selectedItem != position) {
                target.setBackgroundResource(R.drawable.bg_outline_album);
            }
        }
        mediaMetadataRetriever.release();
    }
}
