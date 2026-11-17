package com.besome.sketch.editor.manage.sound;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.Qp;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageSoundAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class AddSoundActivity extends BaseDialogActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SOUND_PICKER = 218;

    public ManageSoundAddBinding binding;
    private MediaPlayer nowPlayingPlayer;
    private TimerTask nowPlayingProgressUpdater;
    private WB soundNameValidator;
    private String sc_id;
    private String soundsDirectory;
    private Timer timer = new Timer();
    private Uri soundUri = null;
    private boolean isSoundPlayable = false;

    private final ActivityResultLauncher<Intent> soundPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (binding != null) {
                    binding.selectFile.setEnabled(true);
                }
                Intent data = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                    Uri intentData = data.getData();
                    if (intentData != null) {
                        playSound(intentData);
                    }
                }
            });
    private boolean editingSound = false;

    @Override
    public void finish() {
        if (timer != null) {
            timer.cancel();
        }
        if (nowPlayingPlayer != null) {
            if (nowPlayingPlayer.isPlaying()) {
                nowPlayingPlayer.stop();
            }
            nowPlayingPlayer.release();
            nowPlayingPlayer = null;
        }
        super.finish();
    }

    private void pickSound() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        soundPickerLauncher.launch(Intent.createChooser(intent, getString(R.string.common_word_choose)));
    }

    private void playOrPause() {
        if (nowPlayingPlayer.isPlaying()) {
            pauseNowPlaying();
        } else {
            nowPlayingPlayer.start();
            startNowPlayingProgressUpdater();
            binding.play.setImageResource(R.drawable.ic_mtrl_circle_pause);
        }
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            saveSound();
        } else if (id == R.id.play) {
            playOrPause();
        } else if (id == R.id.select_file) {
            binding.selectFile.setEnabled(false);
            pickSound();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageSoundAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e(getString(R.string.design_manager_sound_title_add_sound));
        d(getString(R.string.common_word_save));
        b(getString(R.string.common_word_cancel));

        Intent intent = getIntent();
        ArrayList<String> existingSoundNames = intent.getStringArrayListExtra("sound_names");
        sc_id = intent.getStringExtra("sc_id");
        soundsDirectory = intent.getStringExtra("dir_path");
        int requestCode = intent.getIntExtra("request_code", -1);
        binding.layoutControl.setVisibility(View.GONE);
        TextInputLayout soundInputLayout = binding.tiInput;
        soundNameValidator = new WB(this, soundInputLayout, uq.b, existingSoundNames);
        binding.play.setEnabled(false);
        binding.play.setOnClickListener(this);
        binding.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (nowPlayingPlayer != null && nowPlayingPlayer.isPlaying() && timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (nowPlayingPlayer != null) {
                    nowPlayingPlayer.seekTo(seekBar.getProgress() * 100);
                    if (nowPlayingPlayer.isPlaying()) {
                        startNowPlayingProgressUpdater();
                    }
                } else {
                    seekBar.setProgress(0);
                }
            }
        });
        binding.selectFile.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        if (requestCode == 270) {
            e(getString(R.string.design_manager_sound_title_edit_sound));
            ProjectResourceBean projectResourceBean = intent.getParcelableExtra("project_resource");
            soundNameValidator = new WB(this, soundInputLayout, uq.b, new ArrayList<>());
            binding.edInput.setText(projectResourceBean.resName);
            binding.edInput.setEnabled(false);
            binding.chkCollection.setEnabled(false);
            String soundPath;
            if (projectResourceBean.isNew) {
                soundPath = projectResourceBean.resFullName;
            } else {
                soundPath = getSoundFilePath(projectResourceBean);
            }
            editingSound = true;
            playSound(Uri.fromFile(new File(soundPath)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseNowPlaying();
    }

    private void saveSound() {
        if (isSoundValid(soundNameValidator)) {
            String soundName = Helper.getText(binding.edInput);

            SketchwareUtil.copySafDocumentToTempFile(soundUri, this, FileUtil.getFileExtension(getFilenameOfPickedFile(soundUri)),
                    tempFile -> {
                        String soundFilePath = tempFile.getAbsolutePath();
                        ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, soundName, soundFilePath);
                        projectResourceBean.savedPos = 1;
                        projectResourceBean.isNew = true;
                        if (binding.chkCollection.isChecked()) {
                            try {
                                Qp.g().a(sc_id, projectResourceBean);
                            } catch (Exception e) {
                                // The bytecode is lying. Checked exceptions suck.
                                //noinspection ConstantConditions
                                if (e instanceof yy) {
                                    String message = e.getMessage();

                                    switch (message) {
                                        case "duplicate_name":
                                            bB.b(this, getString(R.string.collection_duplicated_name), 1).show();
                                            break;

                                        case "file_no_exist":
                                            bB.b(this, getString(R.string.collection_no_exist_file), 1).show();
                                            break;

                                        case "fail_to_copy":
                                            bB.b(this, getString(R.string.collection_failed_to_copy), 1).show();
                                            break;

                                        default:
                                    }
                                    return;
                                } else {
                                    throw e;
                                }
                            }
                        }

                        Intent intent = new Intent();
                        intent.putExtra("project_resource", projectResourceBean);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }, e -> {
                        SketchwareUtil.toastError("Error while loading sound: " + e.getMessage());
                        LogUtil.e("AddSoundActivity", "Failed to load sound", e);
                    });
        }
    }

    private void pauseNowPlaying() {
        if (nowPlayingPlayer != null && nowPlayingPlayer.isPlaying()) {
            timer.cancel();
            nowPlayingPlayer.pause();
            binding.play.setImageResource(R.drawable.ic_mtrl_circle_play);
        }
    }

    private void startNowPlayingProgressUpdater() {
        timer = new Timer();
        nowPlayingProgressUpdater = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (nowPlayingPlayer == null) {
                        timer.cancel();
                    } else {
                        int currentPosition = nowPlayingPlayer.getCurrentPosition() / 1000;
                        binding.currentTime.setText(String.format("%d:%02d", currentPosition / 60, currentPosition % 60));
                        binding.seek.setProgress(nowPlayingPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        };
        timer.schedule(nowPlayingProgressUpdater, 100L, 100L);
    }

    private String getSoundFilePath(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return soundsDirectory + File.separator + projectResourceBean.resName + substring;
    }

    private void playSound(Uri uri) {
        soundUri = uri;
        try {
            if (nowPlayingPlayer != null) {
                if (nowPlayingProgressUpdater != null) {
                    nowPlayingProgressUpdater.cancel();
                }
                if (nowPlayingPlayer.isPlaying()) {
                    nowPlayingPlayer.stop();
                }
            }
            nowPlayingPlayer = new MediaPlayer();
            nowPlayingPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            nowPlayingPlayer.setOnPreparedListener(mp -> {
                binding.play.setImageResource(R.drawable.ic_mtrl_circle_pause);
                binding.play.setEnabled(true);
                binding.seek.setMax(mp.getDuration() / 100);
                binding.seek.setProgress(0);

                int duration = mp.getDuration() / 1000;
                binding.fileLength.setText(String.format("%d:%02d", duration / 60, duration % 60));

                binding.fileName.setText(editingSound ? soundUri.getLastPathSegment() : getFilenameOfPickedFile(soundUri));

                mp.start();
                startNowPlayingProgressUpdater();
            });
            nowPlayingPlayer.setOnCompletionListener(mp -> {
                timer.cancel();
                binding.play.setImageResource(R.drawable.ic_mtrl_circle_play);
                binding.seek.setProgress(0);
                binding.currentTime.setText("0:00");
            });
            nowPlayingPlayer.setDataSource(this, uri);
            nowPlayingPlayer.prepare();
            isSoundPlayable = true;
            setAlbumCover();
            binding.layoutControl.setVisibility(View.VISIBLE);
            binding.layoutGuide.setVisibility(View.GONE);
            if (binding.edInput.getText() == null || binding.edInput.getText().length() <= 0) {
                String extractedName = FileUtil.getFileNameNoExtension(getFilenameOfPickedFile(soundUri));
                extractedName = extractedName.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
                binding.edInput.setText(extractedName);
            }
        } catch (Exception e) {
            isSoundPlayable = false;
            binding.layoutControl.setVisibility(View.GONE);
            binding.layoutGuide.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void setAlbumCover() throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            if (editingSound) {
                mediaMetadataRetriever.setDataSource(soundUri.getPath());
            } else {
                mediaMetadataRetriever.setDataSource(getContentResolver().openFileDescriptor(soundUri, "r").getFileDescriptor());
            }
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(binding.imgAlbum);
            } else {
                Glide.with(this).load(R.drawable.default_album_art_200dp).centerCrop().into(binding.imgAlbum);
            }
        } catch (IllegalArgumentException e) {
            Glide.with(this).load(R.drawable.default_album_art_200dp).centerCrop().into(binding.imgAlbum);
        }
        mediaMetadataRetriever.release();
    }

    private boolean isSoundValid(WB wb) {
        if (!wb.b()) {
            return false;
        }
        if (isSoundPlayable && soundUri != null) {
            return true;
        }
        binding.selectFile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private String getFilenameOfPickedFile(Uri input) {
        Optional<String> displayName = SketchwareUtil.getSafDocumentDisplayName(input);

        //noinspection SimplifyOptionalCallChains
        if (!displayName.isPresent()) return "Unknown filename";
        String name = displayName.get();

        if (name.contains(".")) {
            // Seems like that display name is a filename
            return name;
        } else {
            Optional<String> optionalFilenameExtension = SketchwareUtil.doSingleStringContentQuery(input, MediaStore.MediaColumns.MIME_TYPE);
            String filenameExtension;
            if (optionalFilenameExtension.isPresent()) {
                filenameExtension = optionalFilenameExtension.get().split("/")[1];
            } else {
                // Failed to find out filename extension :/
                filenameExtension = "";
            }

            return name + '.' + filenameExtension;
        }
    }
}