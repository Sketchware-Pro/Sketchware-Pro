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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.HB;
import a.a.a.Hv;
import a.a.a.Iv;
import a.a.a.Jv;
import a.a.a.Kv;
import a.a.a.Lv;
import a.a.a.Mv;
import a.a.a.Ov;
import a.a.a.Qp;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yy;

public class AddSoundCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public TextInputEditText inputAudioName;
    public TextInputLayout inputLayoutAudioName;
    public TextView filename;
    public TextView audioCurrentTime;
    public TextView audioLengthText;
    public ImageView albumImage;
    public ImageView playAudioBtn;
    public MediaPlayer G;
    public TimerTask I;
    public SeekBar audioSeekBar;
    public WB M;
    public ArrayList<ProjectResourceBean> N;
    public String t;
    public MaterialCardView playerControlLayout;
    public LinearLayout x;
    public MaterialCardView selectAudioBtn;
    public boolean u;
    public Timer H = new Timer();
    public Uri K;
    public boolean L;
    public ProjectResourceBean O;

    public void finish() {
        Timer timer = H;
        if (timer != null) {
            timer.cancel();
        }
        MediaPlayer mediaPlayer = G;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                G.stop();
            }
            G.release();
            G = null;
        }
        super.finish();
    }

    public final ArrayList<String> n() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : N) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    public final void o() {
        MediaPlayer mediaPlayer = G;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        H.cancel();
        G.pause();
        playAudioBtn.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        MaterialCardView relativeLayout;
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (i == 218 && (relativeLayout = selectAudioBtn) != null) {
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
        if (id == R.id.play) {
            q();
            return;
        }
        if (id == R.id.select_file) {
            if (!u) {
                selectAudioBtn.setEnabled(false);
                p();
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, R.string.design_manager_sound_title_add_sound));
        setContentView(R.layout.manage_sound_add);
        d(xB.b().a(this, R.string.common_word_save));
        b(xB.b().a(this, R.string.common_word_cancel));
        Intent intent = getIntent();
        N = intent.getParcelableArrayListExtra("sounds");
        t = intent.getStringExtra("sc_id");
        O = intent.getParcelableExtra("edit_target");
        if (O != null) {
            u = true;
        }
        filename = findViewById(R.id.file_name);
        audioCurrentTime = findViewById(R.id.current_time);
        audioLengthText = findViewById(R.id.file_length);
        playerControlLayout = findViewById(R.id.layout_control);
        x = findViewById(R.id.layout_guide);
        selectAudioBtn = findViewById(R.id.select_file);
        playAudioBtn = findViewById(R.id.play);
        albumImage = findViewById(R.id.img_album);
        audioSeekBar = findViewById(R.id.seek);
        playerControlLayout.setVisibility(View.GONE);
        inputAudioName = findViewById(R.id.ed_input);
        inputLayoutAudioName = findViewById(R.id.ti_input);
        inputAudioName.setHint(xB.b().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        M = new WB(this, inputLayoutAudioName, uq.b, n());
        playAudioBtn.setEnabled(false);
        playAudioBtn.setOnClickListener(this);
        audioSeekBar.setOnSeekBarChangeListener(new Hv(this));
        selectAudioBtn.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        if (u) {
            e(xB.b().a(this, R.string.design_manager_sound_title_edit_sound_name));
            M = new WB(this, inputLayoutAudioName, uq.b, n(), O.resName);
            inputAudioName.setText(O.resName);
            f(a(O));
        }
    }

    public void onPause() {
        super.onPause();
        o();
    }

    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddSoundCollectionActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void p() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 218);
    }

    public final void q() {
        if (G.isPlaying()) {
            o();
            return;
        }
        G.start();
        s();
        playAudioBtn.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    public final void r() {
        char c;
        if (a(M)) {
            if (!u) {
                String obj = inputAudioName.getText().toString();
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
                            bB.a(this, xB.b().a(getApplicationContext(), R.string.collection_duplicated_name), 1).show();
                            return;
                        } else if (c == 1) {
                            bB.a(this, xB.b().a(getApplicationContext(), R.string.collection_no_exist_file), 1).show();
                            return;
                        } else if (c != 2) {
                            return;
                        } else {
                            bB.a(this, xB.b().a(getApplicationContext(), R.string.collection_failed_to_copy), 1).show();
                            return;
                        }
                    }

                }
            } else {
                Qp.g().a(O, inputAudioName.getText().toString(), true);
                bB.a(this, xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete), 1).show();
            }
            finish();
        }
    }

    public final void s() {
        H = new Timer();
        I = new Ov(this);
        H.schedule(I, 100L, 100L);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + O.resFullName;
    }

    public final void f(String str) {
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
            G.setOnPreparedListener(new Kv(this));
            G.setOnCompletionListener(new Lv(this));
            G.setDataSource(this, Uri.parse(str));
            G.prepare();
            L = true;
            a(str, albumImage);
            playerControlLayout.setVisibility(View.VISIBLE);
            x.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a(Uri uri) {
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
            G.setOnPreparedListener(new Iv(this, a));
            G.setOnCompletionListener(new Jv(this));
            G.setDataSource(this, uri);
            G.prepare();
            L = true;
            a(HB.a(this, K), albumImage);
            playerControlLayout.setVisibility(View.VISIBLE);
            x.setVisibility(View.GONE);
            try {
                if (inputAudioName.getText() == null || inputAudioName.getText().length() <= 0) {
                    int lastIndexOf = a.lastIndexOf("/");
                    int lastIndexOf2 = a.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = a.length();
                    }
                    inputAudioName.setText(a.substring(lastIndexOf + 1, lastIndexOf2));
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            L = false;
            playerControlLayout.setVisibility(View.GONE);
            x.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    public final void a(String str, ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new Mv(this, imageView));
            } else {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
            }
            mediaMetadataRetriever.release();
        } catch (Exception ignored) {
            imageView.setImageResource(R.drawable.default_album_art_200dp);
        }
    }

    public static MediaPlayer a(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.G;
    }

    public static Timer b(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.H;
    }

    public static void c(AddSoundCollectionActivity addSoundCollectionActivity) {
        addSoundCollectionActivity.s();
    }

    public static ImageView d(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.playAudioBtn;
    }

    public static SeekBar e(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.audioSeekBar;
    }

    public static TextView f(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.audioLengthText;
    }

    public static TextView g(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.filename;
    }

    public static TextView h(AddSoundCollectionActivity addSoundCollectionActivity) {
        return addSoundCollectionActivity.audioCurrentTime;
    }

    public boolean a(WB wb) {
        if (wb.b()) {
            if ((!L || K == null) && !u) {
                selectAudioBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
                return false;
            }
            return true;
        }
        return false;
    }
}

