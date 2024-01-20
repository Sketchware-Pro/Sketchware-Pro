package com.besome.sketch.editor.manage.sound;

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
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class AddSoundCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public EasyDeleteEditText A;
    public TextView B;
    public TextView C;
    public TextView D;
    public ImageView E;
    public ImageView F;
    public MediaPlayer G;
    public TimerTask I;
    public SeekBar J;
    public WB M;
    public ArrayList<ProjectResourceBean> N;
    public String t;
    public LinearLayout v;
    public LinearLayout w;
    public LinearLayout x;
    public RelativeLayout y;
    public EditText z;
    public boolean u = false;
    public Timer H = new Timer();
    public Uri K = null;
    public boolean L = false;
    public ProjectResourceBean O = null;

    public void finish() {
        Timer timer = this.H;
        if (timer != null) {
            timer.cancel();
        }
        MediaPlayer mediaPlayer = this.G;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.G.stop();
            }
            this.G.release();
            this.G = null;
        }
        super.finish();
    }

    public final ArrayList<String> n() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.N.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final void o() {
        MediaPlayer mediaPlayer = this.G;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.H.cancel();
        this.G.pause();
        this.F.setImageResource(0x7f070274);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onActivityResult(int i, int i2, Intent intent) {
        RelativeLayout relativeLayout;
        Uri data;
        super/*androidx.fragment.app.FragmentActivity*/.onActivityResult(i, i2, intent);
        if (i == 218 && (relativeLayout = this.y) != null) {
            relativeLayout.setEnabled(true);
            if (i2 != -1 || (data = intent.getData()) == null) {
                return;
            }
            this.K = data;
            if (HB.a(this, this.K) == null) {
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
                r();
                return;
            case 2131231599:
                q();
                return;
            case 2131231710:
                if (this.u) {
                    return;
                }
                this.y.setEnabled(false);
                p();
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 0x7f0e0543));
        setContentView(0x7f0b00ed);
        d(xB.b().a(this, 0x7f0e0447));
        b(xB.b().a(this, 0x7f0e040e));
        Intent intent = getIntent();
        this.N = intent.getParcelableArrayListExtra("sounds");
        this.t = intent.getStringExtra("sc_id");
        this.O = intent.getParcelableExtra("edit_target");
        if (this.O != null) {
            this.u = true;
        }
        this.B = (TextView) findViewById(0x7f080153);
        this.C = (TextView) findViewById(0x7f0800d9);
        this.D = (TextView) findViewById(0x7f080151);
        this.v = (LinearLayout) findViewById(0x7f08025b);
        this.v.setVisibility(8);
        this.w = (LinearLayout) findViewById(0x7f080266);
        this.x = (LinearLayout) findViewById(0x7f080276);
        this.y = (RelativeLayout) findViewById(0x7f0803de);
        this.F = (ImageView) findViewById(0x7f08036f);
        this.E = (ImageView) findViewById(0x7f080182);
        this.J = (SeekBar) findViewById(0x7f0803dc);
        this.w.setVisibility(8);
        this.A = findViewById(0x7f08010e);
        this.z = this.A.getEditText();
        this.z.setPrivateImeOptions("defaultInputmode=english;");
        this.A.setHint(xB.b().a(this, 0x7f0e0542));
        this.M = new WB(this, this.A.getTextInputLayout(), uq.b, n());
        this.F.setEnabled(false);
        this.F.setOnClickListener(this);
        this.J.setOnSeekBarChangeListener(new Hv(this));
        this.y.setOnClickListener(this);
        ((BaseDialogActivity) this).r.setOnClickListener(this);
        ((BaseDialogActivity) this).s.setOnClickListener(this);
        if (this.u) {
            e(xB.b().a(this, 0x7f0e0546));
            this.M = new WB(this, this.A.getTextInputLayout(), uq.b, n(), this.O.resName);
            this.z.setText(this.O.resName);
            f(a(this.O));
        }
    }

    public void onPause() {
        super/*com.besome.sketch.lib.base.BaseAppCompatActivity*/.onPause();
        o();
    }

    public void onResume() {
        super/*com.besome.sketch.lib.base.BaseAppCompatActivity*/.onResume();
        ((BaseAppCompatActivity) this).d.setScreenName(AddSoundCollectionActivity.class.getSimpleName().toString());
        ((BaseAppCompatActivity) this).d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void p() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 0x7f0e0410)), 218);
    }

    public final void q() {
        if (this.G.isPlaying()) {
            o();
            return;
        }
        this.G.start();
        s();
        this.F.setImageResource(0x7f07026d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void r() {
        char c;
        if (a(this.M)) {
            if (!this.u) {
                String obj = this.z.getText().toString();
                String a = HB.a(this, this.K);
                if (a == null) {
                    return;
                }
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a);
                ((SelectableBean) projectResourceBean).savedPos = 1;
                ((SelectableBean) projectResourceBean).isNew = true;
                try {
                    Qp.g().a(this.t, projectResourceBean);
                    bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e053c), 1).show();
                } catch (yy e) {
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
                        bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e03c7), 1).show();
                        return;
                    } else if (c == 1) {
                        bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e03c9), 1).show();
                        return;
                    } else if (c != 2) {
                        return;
                    } else {
                        bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e03c8), 1).show();
                        return;
                    }
                }
            } else {
                Qp.g().a(this.O, this.z.getText().toString(), true);
                bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e053f), 1).show();
            }
            finish();
        }
    }

    public final void s() {
        this.H = new Timer();
        this.I = new Ov(this);
        this.H.schedule(this.I, 100L, 100L);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + this.O.resFullName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void f(String str) {
        try {
            if (this.G != null) {
                if (this.I != null) {
                    this.I.cancel();
                }
                if (this.G.isPlaying()) {
                    this.G.stop();
                }
            }
            this.G = new MediaPlayer();
            this.G.setAudioStreamType(3);
            this.G.setOnPreparedListener(new Kv(this));
            this.G.setOnCompletionListener(new Lv(this));
            this.G.setDataSource(this, Uri.parse(str));
            this.G.prepare();
            this.L = true;
            a(str, this.E);
            this.w.setVisibility(0);
            this.x.setVisibility(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(Uri uri) {
        String a = HB.a(this, uri);
        this.K = uri;
        try {
            if (this.G != null) {
                if (this.I != null) {
                    this.I.cancel();
                }
                if (this.G.isPlaying()) {
                    this.G.stop();
                }
            }
            this.G = new MediaPlayer();
            this.G.setAudioStreamType(3);
            this.G.setOnPreparedListener(new Iv(this, a));
            this.G.setOnCompletionListener(new Jv(this));
            this.G.setDataSource(this, uri);
            this.G.prepare();
            this.L = true;
            a(HB.a(this, this.K), this.E);
            this.w.setVisibility(0);
            this.x.setVisibility(8);
            try {
                if (this.z.getText() == null || this.z.getText().length() <= 0) {
                    int lastIndexOf = a.lastIndexOf("/");
                    int lastIndexOf2 = a.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = a.length();
                    }
                    this.z.setText(a.substring(lastIndexOf + 1, lastIndexOf2));
                }
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            this.L = false;
            this.w.setVisibility(8);
            this.x.setVisibility(0);
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
                imageView.setImageResource(0x7f070150);
            }
        } catch (IllegalArgumentException unused) {
            imageView.setImageResource(0x7f070150);
        }
        mediaMetadataRetriever.release();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(WB wb) {
        if (wb.b()) {
            if ((!this.L || this.K == null) && !this.u) {
                this.y.startAnimation(AnimationUtils.loadAnimation(this, 0x7f01000c));
                return false;
            }
            return true;
        }
        return false;
    }
}
