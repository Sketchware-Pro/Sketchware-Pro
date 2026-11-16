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
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImportListItemBinding;
import pro.sketchware.databinding.ManageSoundImportBinding;

public class ManageSoundImportActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public ManageSoundImportBinding binding;
    private QB nameValidator;
    private boolean mediaPlayerIsPrepared;
    private ItemAdapter adapter;
    private ArrayList<ProjectResourceBean> projectSounds;
    private ArrayList<ProjectResourceBean> selectedCollections;
    private int selectedItem = 0;
    private MediaPlayer mediaPlayer;

    private ArrayList<String> getReservedSelectedCollectionNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            names.add(selectedCollection.resName);
        }
        return names;
    }

    private ArrayList<String> getReservedProjectSoundNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectSound : projectSounds) {
            names.add(projectSound.resName);
        }
        return names;
    }

    private boolean doDuplicateNamesExist() {
        ArrayList<String> duplicateNames = new ArrayList<>();
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            if (selectedCollection.isDuplicateCollection) {
                duplicateNames.add(selectedCollection.resName);
            }
        }
        if (!duplicateNames.isEmpty()) {
            String message = getString(R.string.common_message_name_unavailable);
            String names = "";
            for (String str2 : duplicateNames) {
                if (!names.isEmpty()) {
                    names = names + ", ";
                }
                names = names + str2;
            }
            bB.a(getApplicationContext(), message + "\n[" + names + "]", bB.TOAST_WARNING).show();
            return true;
        }
        return false;
    }

    private boolean isNameValid() {
        return nameValidator.b();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_decide) {
                String name = Helper.getText(binding.edInput);
                if (isNameValid()) {
                    if (binding.chkSamename.isChecked()) {
                        int i = 0;
                        while (i < selectedCollections.size()) {
                            ProjectResourceBean sound = selectedCollections.get(i);
                            sound.resName = name + "_" + (++i);
                            sound.isDuplicateCollection = false;
                        }
                    } else {
                        ProjectResourceBean sound = selectedCollections.get(selectedItem);
                        sound.resName = name;
                        sound.isDuplicateCollection = false;
                    }
                    nameValidator.a(getReservedSelectedCollectionNames());
                    adapter.notifyDataSetChanged();
                } else {
                    binding.edInput.setText(selectedCollections.get(selectedItem).resName);
                }
            } else if (id == R.id.img_backbtn) {
                getOnBackPressedDispatcher().onBackPressed();
            } else if (id == R.id.img_play) {
                if (mediaPlayerIsPrepared) {
                    if (mediaPlayer.isPlaying()) {
                        pausePlayback();
                    } else {
                        mediaPlayer.start();
                        binding.imgPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    }
                }
            } else if (id == R.id.tv_sendbtn) {
                if (!doDuplicateNamesExist()) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("results", selectedCollections);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
            return;
        }
        
        binding = ManageSoundImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_OK);
                finish();
            }
        });

        binding.imgBackbtn.setOnClickListener(this);
        binding.tvSendbtn.setText(getString(R.string.common_word_import).toUpperCase());
        binding.tvSendbtn.setOnClickListener(this);
        binding.tvSamename.setText(R.string.design_manager_sound_title_apply_same_naming);

        adapter = new ItemAdapter();
        binding.recyclerList.setAdapter(adapter);
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

        projectSounds = getIntent().getParcelableArrayListExtra("project_sounds");
        selectedCollections = getIntent().getParcelableArrayListExtra("selected_collections");

        binding.tvCurrentnum.setText(String.valueOf(1));
        binding.tvTotalnum.setText(String.valueOf(selectedCollections.size()));
        
        binding.edInput.setText(selectedCollections.get(0).resName);
        // hint & privateImeOptions are now handled in XML

        nameValidator = new QB(getApplicationContext(), binding.tiInput, uq.b, getReservedProjectSoundNames(), getReservedSelectedCollectionNames());

        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollections.size());
            } else {
                nameValidator.c(selectedCollections.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });

        binding.btnDecide.setText(R.string.design_manager_change_name_button);
        binding.btnDecide.setOnClickListener(this);
        binding.imgPlay.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    private void initializeLogic() {
        ArrayList<ProjectResourceBean> duplicateCollections = new ArrayList<>();
        ArrayList<ProjectResourceBean> notDuplicateCollections = new ArrayList<>();
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            if (isNameInUseByProjectSound(selectedCollection.resName)) {
                selectedCollection.isDuplicateCollection = true;
                duplicateCollections.add(selectedCollection);
            } else {
                selectedCollection.isDuplicateCollection = false;
                notDuplicateCollections.add(selectedCollection);
            }
        }
        if (!duplicateCollections.isEmpty()) {
            bB.b(getApplicationContext(), getString(R.string.design_manager_message_collection_name_conflict), bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(), getString(R.string.design_manager_message_collection_name_no_conflict), bB.TOAST_NORMAL).show();
        }
        selectedCollections = new ArrayList<>();
        selectedCollections.addAll(duplicateCollections);
        selectedCollections.addAll(notDuplicateCollections);
    }

    private void pausePlayback() {
        if (mediaPlayer != null && mediaPlayerIsPrepared && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            binding.imgPlay.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
    }

    private void showPreview(int i) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayerIsPrepared = true;
            binding.imgPlay.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        });
        mediaPlayer.setOnCompletionListener(mp -> mediaPlayerIsPrepared = false);
        try {
            String filePath = selectedCollections.get(i).resFullName;
            loadSoundEmbeddedPicture(filePath, binding.imgAlbum, -1);
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNameInUseByProjectSound(String str) {
        for (ProjectResourceBean projectSound : projectSounds) {
            if (projectSound.resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void loadSoundEmbeddedPicture(String filePath, ImageView target, int position) throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(filePath);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(getApplicationContext()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(target);
            } else {
                target.setImageResource(R.drawable.default_album_art_200dp);
                if (position != -1 && selectedItem != position) {
                    target.setBackgroundResource(R.drawable.bg_outline_album);
                }
            }
        } catch (IllegalArgumentException unused) {
            target.setImageResource(R.drawable.default_album_art_200dp);
            if (position != -1 && selectedItem != position) {
                target.setBackgroundResource(R.drawable.bg_outline_album);
            }
        }
        mediaMetadataRetriever.release();
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        public ItemAdapter() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            ProjectResourceBean sound = selectedCollections.get(position);
            if (sound.isDuplicateCollection) {
                viewHolder.binding.imgConflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.binding.imgConflict.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (position == selectedItem) {
                viewHolder.binding.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.binding.img.setBackgroundColor(Color.WHITE);
            }
            try {
                loadSoundEmbeddedPicture(sound.resFullName, viewHolder.binding.img, position);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            viewHolder.binding.tvName.setText(selectedCollections.get(position).resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageImportListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public int getItemCount() {
            return selectedCollections.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final ManageImportListItemBinding binding;

            public ViewHolder(ManageImportListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.binding = itemBinding;
                
                binding.img.setOnClickListener(v -> {
                    if (!mB.a()) {
                        pausePlayback();
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        ManageSoundImportActivity.this.binding.tvCurrentnum.setText(String.valueOf(selectedItem + 1));
                        ManageSoundImportActivity.this.binding.edInput.setText(selectedCollections.get(selectedItem).resName);
                        
                        if (ManageSoundImportActivity.this.binding.chkSamename.isChecked()) {
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
    }
}