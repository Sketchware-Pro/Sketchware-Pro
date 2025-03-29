package com.besome.sketch.editor.manage.sound;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.HB;
import a.a.a.Qp;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageSoundAddBinding;

public class AddSoundCollectionActivity extends BaseDialogActivity implements View.OnClickListener {

    public MediaPlayer G;
    public TimerTask I;
    public WB M;
    public ArrayList<ProjectResourceBean> N;
    public String t;
    public boolean u;
    public Timer H = new Timer();
    public Uri K;
    public boolean L;
    public ProjectResourceBean O;

    private ManageSoundAddBinding binding;

    public void finish() {
        if (H != null) H.cancel();
        if (G != null) {
            if (G.isPlaying()) {
                G.stop();
            }
            G.release();
            G = null;
        }
        super.finish();
    }

    private ArrayList<String> getResourceNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : N) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    private void o() {
        if (G == null || !G.isPlaying()) return;
        H.cancel();
        G.pause();
        binding.play.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        MaterialCardView relativeLayout;
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (i == 218 && (relativeLayout = binding.selectFile) != null) {
            relativeLayout.setEnabled(true);
            if (i2 != -1 || (data = intent.getData()) == null) {
                return;
            }
            K = data;
            if (HB.a(this, K) == null) {
                return;
            }
            a(data);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.common_dialog_cancel_button) {
            finish();
            return;
        }
        if (id == R.id.common_dialog_ok_button) {
            r();
            return;
        }
        if (id == binding.play.getId()) {
            q();
            return;
        }
        if (id == binding.selectFile.getId()) {
            if (!u) {
                binding.selectFile.setEnabled(false);
                p();
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, R.string.design_manager_sound_title_add_sound));
        binding = ManageSoundAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        d(xB.b().a(this, R.string.common_word_save));
        b(xB.b().a(this, R.string.common_word_cancel));
        Intent intent = getIntent();
        N = intent.getParcelableArrayListExtra("sounds");
        t = intent.getStringExtra("sc_id");
        O = intent.getParcelableExtra("edit_target");
        if (O != null) {
            u = true;
        }
        binding.layoutControl.setVisibility(View.GONE);
        binding.tiInput.setHint(xB.b().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        M = new WB(this, binding.tiInput, uq.b, getResourceNames());
        binding.play.setEnabled(false);
        binding.play.setOnClickListener(this);
        binding.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (G == null || !G.isPlaying() || H == null) return;
                H.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (G != null) {
                    G.seekTo(seekBar.getProgress() * 100);
                    if (G.isPlaying()) {
                        s();
                        return;
                    }
                    return;
                }
                seekBar.setProgress(0);
            }
        });
        binding.selectFile.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        if (u) {
            e(xB.b().a(this, R.string.design_manager_sound_title_edit_sound_name));
            M = new WB(this, binding.tiInput, uq.b, getResourceNames(), O.resName);
            binding.edInput.setText(O.resName);
            f(a(O));
        }
    }

    public void onPause() {
        super.onPause();
        o();
    }

    private void p() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 218);
    }

    private void q() {
        if (G.isPlaying()) {
            o();
            return;
        }
        G.start();
        s();
        binding.play.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    private void r() {
        if (a(M)) {
            if (!u) {
                String obj = Helper.getText(binding.edInput);
                String a = HB.a(this, K);
                if (a == null) {
                    return;
                }
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a);
                projectResourceBean.savedPos = 1;
                projectResourceBean.isNew = true;
                try {
                    Qp.g().a(t, projectResourceBean);
                    bB.a(this, xB.b().a(getApplicationContext(), R.string.design_manager_message_add_complete), 1).show();
                } catch (Exception e) {
                    // the bytecode's lying
                    // noinspection ConstantValue
                    if (e instanceof yy) {
                        var messageId = switch (e.getMessage()) {
                            case "duplicate_name" -> R.string.collection_duplicated_name;
                            case "file_no_exist" -> R.string.collection_no_exist_file;
                            case "fail_to_copy" -> R.string.collection_failed_to_copy;
                            default -> 0;
                        };
                        if (messageId != 0) {
                            bB.a(this, xB.b().a(getApplicationContext(), messageId), bB.TOAST_WARNING).show();
                        }
                    } else {
                        throw e;
                    }
                }
            } else {
                Qp.g().a(O, Helper.getText(binding.edInput), true);
                bB.a(this, xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete), 1).show();
            }
            finish();
        }
    }

    private void s() {
        H = new Timer();
        I = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (G == null) {
                        H.cancel();
                        return;
                    }
                    int currentPosition = G.getCurrentPosition() / 100;
                    binding.currentTime.setText(String.format("%02d : %02d", currentPosition / 60, currentPosition % 60));
                    binding.seek.setProgress(currentPosition / 100);
                });
            }
        };
        H.schedule(I, 100L, 100L);
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + O.resFullName;
    }

    private void f(String str) {
        try {
            if (G != null) {
                if (I != null) {
                    I.cancel();
                }
                if (G.isPlaying()) {
                    G.stop();
                }
            }
            G = new MediaPlayer();
            G.setAudioStreamType(3);
            G.setOnPreparedListener(mediaPlayer -> {
                binding.play.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                binding.play.setEnabled(true);
                binding.seek.setMax(mediaPlayer.getDuration() / 100);
                binding.seek.setProgress(0);
                int duration = mediaPlayer.getDuration() / 100;
                binding.fileLength.setText(String.format("%02d : %02d", duration / 60, duration % 60));
                G.start();
                s();
            });
            G.setOnCompletionListener(mediaPlayer -> {
                H.cancel();
                binding.play.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                binding.seek.setProgress(0);
                binding.currentTime.setText("00 : 00");
            });
            G.setDataSource(this, Uri.parse(str));
            G.prepare();
            L = true;
            a(str, binding.imgAlbum);
            binding.layoutControl.setVisibility(View.VISIBLE);
            binding.layoutGuide.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(Uri uri) {
        String a = HB.a(this, uri);
        K = uri;
        try {
            if (G != null) {
                if (I != null) {
                    I.cancel();
                }
                if (G.isPlaying()) {
                    G.stop();
                }
            }
            G = new MediaPlayer();
            G.setAudioStreamType(3);
            G.setOnPreparedListener(mediaPlayer -> {
                if (a == null) return;
                binding.play.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                binding.play.setEnabled(true);
                binding.seek.setMax(mediaPlayer.getDuration() / 100);
                binding.seek.setProgress(0);
                int duration = mediaPlayer.getDuration() / 100;
                binding.fileLength.setText(String.format("%02d : %02d", duration / 60, duration % 60));
                binding.fileName.setText(a.substring(a.lastIndexOf("/") + 1));
                mediaPlayer.start();
                s();
            });
            G.setOnCompletionListener(mediaPlayer -> {
                H.cancel();
                binding.play.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                binding.seek.setProgress(0);
                binding.currentTime.setText("00 : 00");
            });
            G.setDataSource(this, uri);
            G.prepare();
            L = true;
            a(HB.a(this, K), binding.imgAlbum);
            binding.layoutControl.setVisibility(View.VISIBLE);
            binding.layoutGuide.setVisibility(View.GONE);
            try {
                if (binding.edInput.getText() == null || binding.edInput.getText().length() <= 0) {
                    int lastIndexOf = a.lastIndexOf("/");
                    int lastIndexOf2 = a.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = a.length();
                    }
                    binding.edInput.setText(a.substring(lastIndexOf + 1, lastIndexOf2));
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            L = false;
            binding.layoutControl.setVisibility(View.GONE);
            binding.layoutGuide.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void a(String str, ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(imageView);
            } else {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
            }
            mediaMetadataRetriever.release();
        } catch (Exception ignored) {
            imageView.setImageResource(R.drawable.default_album_art_200dp);
        }
    }

    public boolean a(WB wb) {
        if (wb.b()) {
            if ((!L || K == null) && !u) {
                binding.selectFile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
                return false;
            }
            return true;
        }
        return false;
    }
}

