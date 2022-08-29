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
    public CheckBox A;
    public EditText B;
    public TextView C;
    public TextView D;
    public TextView E;
    public TextView F;
    public ImageView G;
    public ImageView H;
    public MediaPlayer I;
    public TimerTask K;
    public SeekBar L;
    public WB O;
    public ArrayList<String> P;
    public EasyDeleteEditText Q;
    public String t;
    public String u;
    public int v;
    public LinearLayout w;
    public LinearLayout x;
    public LinearLayout y;
    public RelativeLayout z;
    public Timer J = new Timer();
    public Uri M = null;
    public boolean N = false;

    @Override
    public void finish() {
        if (J != null) {
            J.cancel();
        }
        if (I != null) {
            if (I.isPlaying()) {
                I.stop();
            }
            I.release();
            I = null;
        }
        super.finish();
    }

    public final void n() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 218);
    }

    public final void o() {
        if (I.isPlaying()) {
            q();
            return;
        }
        I.start();
        r();
        H.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 218 && z != null) {
            z.setEnabled(true);
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
            z.setEnabled(false);
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
        P = intent.getStringArrayListExtra("sound_names");
        t = intent.getStringExtra("sc_id");
        u = intent.getStringExtra("dir_path");
        v = intent.getIntExtra("request_code", -1);
        D = findViewById(R.id.file_name);
        E = findViewById(R.id.current_time);
        F = findViewById(R.id.file_length);
        w = findViewById(R.id.layout_check);
        x = findViewById(R.id.layout_control);
        y = findViewById(R.id.layout_guide);
        A = findViewById(R.id.chk_collection);
        C = findViewById(R.id.tv_collection);
        z = findViewById(R.id.select_file);
        H = findViewById(R.id.play);
        G = findViewById(R.id.img_album);
        L = findViewById(R.id.seek);
        w.setVisibility(View.VISIBLE);
        x.setVisibility(View.GONE);
        C.setText(xB.b().a(this, R.string.design_manager_title_add_to_collection));
        Q = findViewById(R.id.ed_input);
        B = Q.getEditText();
        Q.setHint(xB.b().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        O = new WB(this, Q.getTextInputLayout(), uq.b, P);
        B.setPrivateImeOptions("defaultInputmode=english;");
        H.setEnabled(false);
        H.setOnClickListener(this);
        L.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (I != null && I.isPlaying() && J != null) {
                    J.cancel();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (I != null) {
                    I.seekTo(seekBar.getProgress() * 100);
                    if (I.isPlaying()) {
                        r();
                    }
                } else {
                    seekBar.setProgress(0);
                }
            }
        });
        z.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        if (v == 270) {
            e(xB.b().a(this, R.string.design_manager_sound_title_edit_sound));
            ProjectResourceBean projectResourceBean = intent.getParcelableExtra("project_resource");
            O = new WB(this, Q.getTextInputLayout(), uq.b, new ArrayList<>());
            B.setText(projectResourceBean.resName);
            B.setEnabled(false);
            A.setEnabled(false);
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
        if (!a(O)) {
            return;
        }
        String obj = B.getText().toString();
        String a2 = HB.a(this, M);
        if (a2 == null) {
            return;
        }
        ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a2);
        projectResourceBean.savedPos = 1;
        projectResourceBean.isNew = true;
        if (A.isChecked()) {
            try {
                Qp.g().a(t, projectResourceBean);
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
        if (I == null || !I.isPlaying()) {
            return;
        }
        J.cancel();
        I.pause();
        H.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
    }

    public final void r() {
        J = new Timer();
        K = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (I == null) {
                        J.cancel();
                    } else {
                        int currentPosition = I.getCurrentPosition() / 1000;
                        E.setText(String.format("%d : %02d", currentPosition / 60, currentPosition % 60));
                        L.setProgress(I.getCurrentPosition() / 100);
                    }
                });
            }
        };
        J.schedule(K, 100L, 100L);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return u + File.separator + projectResourceBean.resName + substring;
    }

    public final void a(Uri uri) {
        String a2 = HB.a(this, uri);
        if (a2 == null) {
            return;
        }
        M = uri;
        try {
            if (I != null) {
                if (K != null) {
                    K.cancel();
                }
                if (I.isPlaying()) {
                    I.stop();
                }
            }
            I = new MediaPlayer();
            I.setAudioStreamType(AudioManager.STREAM_MUSIC);
            I.setOnPreparedListener(mp -> {
                H.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                H.setEnabled(true);
                L.setMax(mp.getDuration() / 100);
                L.setProgress(0);

                int duration = mp.getDuration() / 1000;
                F.setText(String.format("%d : %02d", duration / 60, duration % 60));

                int lastIndexOfSlash = a2.lastIndexOf("/");
                D.setText(a2.substring(lastIndexOfSlash + 1));

                mp.start();
                r();
            });
            I.setOnCompletionListener(mp -> {
                J.cancel();
                H.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                L.setProgress(0);
                E.setText("0 : 00");
            });
            I.setDataSource(this, uri);
            I.prepare();
            N = true;
            a(HB.a(this, M), G);
            x.setVisibility(View.VISIBLE);
            y.setVisibility(View.GONE);
            try {
                if (B.getText() != null && B.getText().length() > 0) {
                    return;
                }
                int lastIndexOf = a2.lastIndexOf("/");
                int lastIndexOf2 = a2.lastIndexOf(".");
                if (lastIndexOf2 <= 0) {
                    lastIndexOf2 = a2.length();
                }
                B.setText(a2.substring(lastIndexOf + 1, lastIndexOf2));
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            N = false;
            x.setVisibility(View.GONE);
            y.setVisibility(View.VISIBLE);
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
        if (N && M != null) {
            return true;
        }
        z.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }
}
