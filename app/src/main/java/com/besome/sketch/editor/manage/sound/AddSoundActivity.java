package com.besome.sketch.editor.manage.sound;

import android.content.Intent;
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

import androidx.fragment.app.FragmentActivity;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.analytics.HitBuilders;

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
        Timer timer = this.J;
        if (timer != null) {
            timer.cancel();
        }
        MediaPlayer mediaPlayer = this.I;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.I.stop();
            }
            this.I.release();
            this.I = null;
        }
        super.finish();
    }

    public final void n() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 218);
    }

    public final void o() {
        if (this.I.isPlaying()) {
            q();
            return;
        }
        this.I.start();
        r();
        this.H.setImageResource(2131165805);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        RelativeLayout relativeLayout;
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (i == 218 && (relativeLayout = this.z) != null) {
            relativeLayout.setEnabled(true);
            if (i2 != -1 || (data = intent.getData()) == null) {
                return;
            }
            a(data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230909:
                finish();
                return;
            case 2131230914:
                p();
                return;
            case 2131231599:
                o();
                return;
            case 2131231710:
                this.z.setEnabled(false);
                n();
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        String a2;
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625283));
        setContentView(2131427565);
        d(xB.b().a(this, 2131625031));
        b(xB.b().a(this, 2131624974));
        Intent intent = getIntent();
        this.P = intent.getStringArrayListExtra("sound_names");
        this.t = intent.getStringExtra("sc_id");
        this.u = intent.getStringExtra("dir_path");
        this.v = intent.getIntExtra("request_code", -1);
        this.D = (TextView) findViewById(2131231059);
        this.E = (TextView) findViewById(2131230937);
        this.F = (TextView) findViewById(2131231057);
        this.w = (LinearLayout) findViewById(2131231323);
        this.x = (LinearLayout) findViewById(2131231334);
        this.y = (LinearLayout) findViewById(2131231350);
        this.A = (CheckBox) findViewById(2131230887);
        this.C = (TextView) findViewById(2131231913);
        this.z = (RelativeLayout) findViewById(2131231710);
        this.H = (ImageView) findViewById(2131231599);
        this.G = (ImageView) findViewById(2131231106);
        this.L = (SeekBar) findViewById(2131231708);
        this.w.setVisibility(0);
        this.x.setVisibility(8);
        this.C.setText(xB.b().a(this, 2131625289));
        this.Q = (EasyDeleteEditText) findViewById(2131230990);
        this.B = this.Q.getEditText();
        this.Q.setHint(xB.b().a(this, 2131625282));
        this.O = new WB(this, this.Q.getTextInputLayout(), uq.b, this.P);
        this.B.setPrivateImeOptions("defaultInputmode=english;");
        this.H.setEnabled(false);
        this.H.setOnClickListener(this);
        this.L.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        this.z.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        if (this.v == 270) {
            e(xB.b().a(this, 2131625285));
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) intent.getParcelableExtra("project_resource");
            this.O = new WB(this, this.Q.getTextInputLayout(), uq.b, new ArrayList());
            this.B.setText(projectResourceBean.resName);
            this.B.setEnabled(false);
            this.A.setEnabled(false);
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
        this.d.setScreenName(AddSoundActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void p() {
        char c;
        if (!a(this.O)) {
            return;
        }
        String obj = this.B.getText().toString();
        String a2 = HB.a(this, this.M);
        if (a2 == null) {
            return;
        }
        ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a2);
        projectResourceBean.savedPos = 1;
        projectResourceBean.isNew = true;
        if (this.A.isChecked()) {
            try {
                Qp.g().a(this.t, projectResourceBean);
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
                        bB.b(this, xB.b().a(this, 2131624903), 1).show();
                        return;
                    } else if (c == 1) {
                        bB.b(this, xB.b().a(this, 2131624905), 1).show();
                        return;
                    } else if (c != 2) {
                        return;
                    } else {
                        bB.b(this, xB.b().a(this, 2131624904), 1).show();
                        return;
                    }
                } else {
                    throw e;
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("project_resource", projectResourceBean);
        setResult(-1, intent);
        finish();
    }

    public final void q() {
        MediaPlayer mediaPlayer = this.I;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.J.cancel();
        this.I.pause();
        this.H.setImageResource(2131165812);
    }

    public final void r() {
        this.J = new Timer();
        this.K = new TimerTask() {
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
        this.J.schedule(this.K, 100L, 100L);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return this.u + File.separator + projectResourceBean.resName + substring;
    }

    public final void a(Uri uri) {
        String a2 = HB.a(this, uri);
        if (a2 == null) {
            return;
        }
        this.M = uri;
        try {
            if (this.I != null) {
                if (this.K != null) {
                    this.K.cancel();
                }
                if (this.I.isPlaying()) {
                    this.I.stop();
                }
            }
            this.I = new MediaPlayer();
            this.I.setAudioStreamType(3);
            this.I.setOnPreparedListener(mp -> {
                H.setImageResource(2131165805);
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
            this.I.setOnCompletionListener(mp -> {
                J.cancel();
                H.setImageResource(2131165812);
                L.setProgress(0);
                E.setText("0 : 00");
            });
            this.I.setDataSource(this, uri);
            this.I.prepare();
            this.N = true;
            a(HB.a(this, this.M), this.G);
            this.x.setVisibility(0);
            this.y.setVisibility(8);
            try {
                if (this.B.getText() != null && this.B.getText().length() > 0) {
                    return;
                }
                int lastIndexOf = a2.lastIndexOf("/");
                int lastIndexOf2 = a2.lastIndexOf(".");
                if (lastIndexOf2 <= 0) {
                    lastIndexOf2 = a2.length();
                }
                this.B.setText(a2.substring(lastIndexOf + 1, lastIndexOf2));
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            this.N = false;
            this.x.setVisibility(8);
            this.y.setVisibility(0);
            e.printStackTrace();
        }
    }

    public final void a(String str, ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with((FragmentActivity) this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(glideDrawable);
                    }
                });
            } else {
                Glide.with((FragmentActivity) this).load((Integer) 2131165520).centerCrop().into(imageView);
            }
        } catch (IllegalArgumentException unused) {
            Glide.with((FragmentActivity) this).load((Integer) 2131165520).centerCrop().into(imageView);
        }
        mediaMetadataRetriever.release();
    }

    public boolean a(WB wb) {
        if (!wb.b()) {
            return false;
        }
        if (this.N && this.M != null) {
            return true;
        }
        this.z.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }
}
