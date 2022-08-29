package com.besome.sketch.editor.manage.sound;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.HB;
import a.a.a.Qp;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.xB;
import a.a.a.yy;

public class AddSoundActivity extends BaseDialogActivity implements View.OnClickListener {
    private CheckBox addToCollection;
    private EditText soundName;
    private TextView nowPlayingFilename;
    private TextView nowPlayingProgress;
    private TextView nowPlayingTotalDuration;
    private ImageView albumCover;
    private ImageView playPause;
    private MediaPlayer nowPlayingPlayer;
    private TimerTask nowPlayingProgressUpdater;
    private SeekBar nowPlayingProgressBar;
    private WB soundNameValidator;
    private String sc_id;
    private String soundsDirectory;
    private LinearLayout nowPlayingContainer;
    private LinearLayout guide;
    private RelativeLayout selectFile;
    private Timer timer = new Timer();
    private Uri soundUri = null;
    private boolean nowPlaying = false;

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

    public final void n() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 218);
    }

    public final void o() {
        if (nowPlayingPlayer.isPlaying()) {
            q();
            return;
        }
        nowPlayingPlayer.start();
        r();
        playPause.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 218 && selectFile != null) {
            selectFile.setEnabled(true);
            Uri intentData;
            if (resultCode == Activity.RESULT_OK && (intentData = data.getData()) != null) {
                a(intentData);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            p();
        } else if (id == R.id.play) {
            o();
        } else if (id == R.id.select_file) {
            selectFile.setEnabled(false);
            n();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String a2;
        super.onCreate(savedInstanceState);
        e(xB.b().a(this, R.string.design_manager_sound_title_add_sound));
        setContentView(R.layout.manage_sound_add);
        d(xB.b().a(this, R.string.common_word_save));
        b(xB.b().a(this, R.string.common_word_cancel));

        Intent intent = getIntent();
        ArrayList<String> existingSoundNames = intent.getStringArrayListExtra("sound_names");
        sc_id = intent.getStringExtra("sc_id");
        soundsDirectory = intent.getStringExtra("dir_path");
        int requestCode = intent.getIntExtra("request_code", -1);
        nowPlayingFilename = findViewById(R.id.file_name);
        nowPlayingProgress = findViewById(R.id.current_time);
        nowPlayingTotalDuration = findViewById(R.id.file_length);
        LinearLayout addToCollectionContainer = findViewById(R.id.layout_check);
        nowPlayingContainer = findViewById(R.id.layout_control);
        guide = findViewById(R.id.layout_guide);
        addToCollection = findViewById(R.id.chk_collection);
        TextView addToCollectionLabel = findViewById(R.id.tv_collection);
        selectFile = findViewById(R.id.select_file);
        playPause = findViewById(R.id.play);
        albumCover = findViewById(R.id.img_album);
        nowPlayingProgressBar = findViewById(R.id.seek);
        addToCollectionContainer.setVisibility(View.VISIBLE);
        nowPlayingContainer.setVisibility(View.GONE);
        addToCollectionLabel.setText(xB.b().a(this, R.string.design_manager_title_add_to_collection));
        EasyDeleteEditText easyDeleteSoundName = findViewById(R.id.ed_input);
        soundName = easyDeleteSoundName.getEditText();
        easyDeleteSoundName.setHint(xB.b().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        soundNameValidator = new WB(this, easyDeleteSoundName.getTextInputLayout(), uq.b, existingSoundNames);
        soundName.setPrivateImeOptions("defaultInputmode=english;");
        playPause.setEnabled(false);
        playPause.setOnClickListener(this);
        nowPlayingProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                        r();
                    }
                } else {
                    seekBar.setProgress(0);
                }
            }
        });
        selectFile.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        if (requestCode == 270) {
            e(xB.b().a(this, R.string.design_manager_sound_title_edit_sound));
            ProjectResourceBean projectResourceBean = intent.getParcelableExtra("project_resource");
            soundNameValidator = new WB(this, easyDeleteSoundName.getTextInputLayout(), uq.b, new ArrayList<>());
            soundName.setText(projectResourceBean.resName);
            soundName.setEnabled(false);
            addToCollection.setEnabled(false);
            if (projectResourceBean.isNew) {
                a2 = projectResourceBean.resFullName;
            } else {
                a2 = a(projectResourceBean);
            }
            a(Uri.fromFile(new File(a2)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        q();
    }

    @Override
    public void onResume() {
        super.onResume();
        d.setScreenName(AddSoundActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void p() {
        char c;
        if (!a(soundNameValidator)) {
            return;
        }
        String obj = soundName.getText().toString();
        String a2 = HB.a(this, soundUri);
        if (a2 == null) {
            return;
        }
        ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a2);
        projectResourceBean.savedPos = 1;
        projectResourceBean.isNew = true;
        if (addToCollection.isChecked()) {
            try {
                Qp.g().a(sc_id, projectResourceBean);
            } catch (Exception e) {
                // The bytecode is lying. Checked exceptions suck.
                //noinspection ConstantConditions
                if (e instanceof yy) {
                    String message = e.getMessage();
                    int hashCode = message.hashCode();
                    if (hashCode == -2111590760) {
                        if (message.equals("fail_to_copy")) {
                            c = 2;
                        }
                        c = 65535;
                    } else if (hashCode != -1587253668) {
                        if (hashCode == -105163457 && message.equals("duplicate_name")) {
                            c = 0;
                        }
                        c = 65535;
                    } else {
                        if (message.equals("file_no_exist")) {
                            c = 1;
                        }
                        c = 65535;
                    }
                    if (c == 0) {
                        bB.b(this, xB.b().a(this, R.string.collection_duplicated_name), 1).show();
                        return;
                    } else if (c == 1) {
                        bB.b(this, xB.b().a(this, R.string.collection_no_exist_file), 1).show();
                        return;
                    } else if (c != 2) {
                        return;
                    } else {
                        bB.b(this, xB.b().a(this, R.string.collection_failed_to_copy), 1).show();
                        return;
                    }
                } else {
                    throw e;
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("project_resource", projectResourceBean);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public final void q() {
        if (nowPlayingPlayer == null || !nowPlayingPlayer.isPlaying()) {
            return;
        }
        timer.cancel();
        nowPlayingPlayer.pause();
        playPause.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
    }

    public final void r() {
        timer = new Timer();
        nowPlayingProgressUpdater = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (nowPlayingPlayer == null) {
                        timer.cancel();
                    } else {
                        int currentPosition = nowPlayingPlayer.getCurrentPosition() / 1000;
                        nowPlayingProgress.setText(String.format("%d : %02d", currentPosition / 60, currentPosition % 60));
                        nowPlayingProgressBar.setProgress(nowPlayingPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        };
        timer.schedule(nowPlayingProgressUpdater, 100L, 100L);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return soundsDirectory + File.separator + projectResourceBean.resName + substring;
    }

    public final void a(Uri uri) {
        String a2 = HB.a(this, uri);
        if (a2 == null) {
            return;
        }
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
            nowPlayingPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            nowPlayingPlayer.setOnPreparedListener(mp -> {
                playPause.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                playPause.setEnabled(true);
                nowPlayingProgressBar.setMax(mp.getDuration() / 100);
                nowPlayingProgressBar.setProgress(0);

                int duration = mp.getDuration() / 1000;
                nowPlayingTotalDuration.setText(String.format("%d : %02d", duration / 60, duration % 60));

                int lastIndexOfSlash = a2.lastIndexOf("/");
                nowPlayingFilename.setText(a2.substring(lastIndexOfSlash + 1));

                mp.start();
                r();
            });
            nowPlayingPlayer.setOnCompletionListener(mp -> {
                timer.cancel();
                playPause.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                nowPlayingProgressBar.setProgress(0);
                nowPlayingProgress.setText("0 : 00");
            });
            nowPlayingPlayer.setDataSource(this, uri);
            nowPlayingPlayer.prepare();
            nowPlaying = true;
            a(HB.a(this, soundUri), albumCover);
            nowPlayingContainer.setVisibility(View.VISIBLE);
            guide.setVisibility(View.GONE);
            try {
                if (soundName.getText() != null && soundName.getText().length() > 0) {
                    return;
                }
                int lastIndexOf = a2.lastIndexOf("/");
                int lastIndexOf2 = a2.lastIndexOf(".");
                if (lastIndexOf2 <= 0) {
                    lastIndexOf2 = a2.length();
                }
                soundName.setText(a2.substring(lastIndexOf + 1, lastIndexOf2));
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            nowPlaying = false;
            nowPlayingContainer.setVisibility(View.GONE);
            guide.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    public final void a(String str, ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(glideDrawable);
                    }
                });
            } else {
                Glide.with(this).load(R.drawable.default_album_art_200dp).centerCrop().into(imageView);
            }
        } catch (IllegalArgumentException unused) {
            Glide.with(this).load(R.drawable.default_album_art_200dp).centerCrop().into(imageView);
        }
        mediaMetadataRetriever.release();
    }

    public boolean a(WB wb) {
        if (!wb.b()) {
            return false;
        }
        if (nowPlaying && soundUri != null) {
            return true;
        }
        selectFile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }
}
