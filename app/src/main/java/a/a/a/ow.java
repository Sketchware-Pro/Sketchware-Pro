package a.a.a;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.AddSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ow extends qA implements View.OnClickListener {
    public oB B;
    public ArrayList<ProjectResourceBean> C;
    public FloatingActionButton H;
    public String f;
    public RecyclerView g;
    public LinearLayout h;
    public Button i;
    public Button j;
    public MediaPlayer m;
    public TimerTask v;
    public MediaPlayer w;
    public TextView z;
    public boolean k = false;
    public a l = null;
    public ImageView n = null;
    public ImageView o = null;
    public SeekBar p = null;
    public TextView q = null;
    public TextView r = null;
    public TextView s = null;
    public EditText t = null;
    public Timer u = new Timer();
    public boolean x = false;
    public Uri y = null;
    public String A = "";
    public int D = -1;
    public int E = -1;
    public TextView F = null;
    public ProgressBar G = null;

    public final void i() {
        if (this.C.size() == 0) {
            this.z.setVisibility(0);
            this.g.setVisibility(8);
            return;
        }
        this.g.setVisibility(0);
        this.z.setVisibility(8);
    }

    public final void j() {
        f();
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", this.f);
        intent.putExtra("dir_path", this.A);
        intent.putExtra("sound_names", c());
        startActivityForResult(intent, 269);
    }

    public final void k() {
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", this.f);
        intent.putExtra("dir_path", this.A);
        intent.putExtra("sound_names", c());
        intent.putExtra("request_code", 270);
        intent.putExtra("project_resource", this.C.get(this.l.c));
        startActivityForResult(intent, 270);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.B = new oB();
        this.B.f(this.A);
        this.C = new ArrayList<>();
        if (bundle == null) {
            e();
        } else {
            this.f = bundle.getString("sc_id");
            this.A = bundle.getString("dir_path");
            this.C = bundle.getParcelableArrayList("sounds");
        }
        this.l.c();
        i();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 269) {
            if (i2 == -1) {
                this.C.add((ProjectResourceBean) intent.getParcelableExtra("project_resource"));
                bB.a(getActivity(), xB.b().a(getActivity(), 2131625276), 1).show();
                this.l.c();
                i();
                ((ManageSoundActivity) getActivity()).l().e();
            }
        } else if (i == 270 && i2 == -1) {
            this.C.set(this.l.c, (ProjectResourceBean) intent.getParcelableExtra("project_resource"));
            bB.a(getActivity(), xB.b().a(getActivity(), 2131625279), 1).show();
            this.l.c();
            i();
            ((ManageSoundActivity) getActivity()).l().e();
        }
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        int id = view.getId();
        if (id != 2131230810) {
            if (id != 2131230817 || !this.k) {
                return;
            }
            int size = this.C.size();
            while (true) {
                size--;
                if (size >= 0) {
                    ProjectResourceBean projectResourceBean = this.C.get(size);
                    projectResourceBean.curSoundPosition = 0;
                    if (projectResourceBean.isSelected) {
                        this.C.remove(size);
                    }
                } else {
                    this.l.c();
                    this.E = -1;
                    this.D = -1;
                    a(false);
                    i();
                    bB.a(getActivity(), xB.b().a(getActivity(), 2131624935), 1).show();
                    this.H.f();
                    return;
                }
            }
        } else if (this.k) {
            a(false);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(2131492882, menu);
        if (this.k) {
            menu.findItem(2131231537).setVisible(false);
        } else {
            menu.findItem(2131231537).setVisible(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(2131427441, viewGroup, false);
        setHasOptionsMenu(true);
        this.h = (LinearLayout) viewGroup2.findViewById(2131231319);
        this.i = (Button) viewGroup2.findViewById(2131230817);
        this.j = (Button) viewGroup2.findViewById(2131230810);
        this.H = (FloatingActionButton) viewGroup2.findViewById(2131231054);
        this.i.setText(xB.b().a(getActivity(), 2131624986));
        this.j.setText(xB.b().a(getActivity(), 2131624974));
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.g = (RecyclerView) viewGroup2.findViewById(2131231746);
        this.g.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.l = new a(this.g);
        this.g.setAdapter(this.l);
        this.z = (TextView) viewGroup2.findViewById(2131231997);
        this.z.setText(xB.b().a(getActivity(), 2131625281));
        this.z.setOnClickListener(new dw(this));
        this.H.setOnClickListener(new ew(this));
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131231536:
                a(false);
                j();
                break;
            case 2131231537:
                a(!this.k);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        f();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.f);
        bundle.putString("dir_path", this.A);
        bundle.putParcelableArrayList("sounds", this.C);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.a<a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public ProgressBar A;
            public TextView B;
            public LinearLayout C;
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public TextView w;
            public ImageView x;
            public ImageView y;
            public TextView z;

            public a(View view) {
                super(view);
                this.t = (CardView) view.findViewById(2131231359);
                this.u = (CheckBox) view.findViewById(2131230893);
                this.v = (ImageView) view.findViewById(2131231106);
                this.y = (ImageView) view.findViewById(2131231132);
                this.w = (TextView) view.findViewById(2131232169);
                this.x = (ImageView) view.findViewById(2131231165);
                this.z = (TextView) view.findViewById(2131231931);
                this.A = (ProgressBar) view.findViewById(2131231607);
                this.B = (TextView) view.findViewById(2131231967);
                this.C = (LinearLayout) view.findViewById(2131230959);
                this.x.setOnClickListener(new lw(this, a.this));
                this.u.setVisibility(8);
                this.t.setOnClickListener(new mw(this, a.this));
                this.t.setOnLongClickListener(new nw(this, a.this));
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new jw(this, ow.this));
            }
        }

        @Override
        public void b(a aVar, int i) {
            String a2;
            ow owVar = ow.this;
            if (!owVar.k) {
                a((ProjectResourceBean) owVar.C.get(i), aVar.v);
                aVar.v.setVisibility(0);
                aVar.C.setVisibility(8);
            } else {
                aVar.v.setVisibility(8);
                aVar.C.setVisibility(0);
            }
            if (((ProjectResourceBean) ow.this.C.get(i)).isSelected) {
                aVar.y.setImageResource(2131165707);
            } else {
                aVar.y.setImageResource(2131165875);
            }
            int i2 = ((ProjectResourceBean) ow.this.C.get(i)).curSoundPosition / 1000;
            if (((ProjectResourceBean) ow.this.C.get(i)).totalSoundDuration == 0) {
                if (((ProjectResourceBean) ow.this.C.get(i)).isNew) {
                    a2 = ((ProjectResourceBean) ow.this.C.get(i)).resFullName;
                } else {
                    ow owVar2 = ow.this;
                    a2 = owVar2.a((ProjectResourceBean) owVar2.C.get(i));
                }
                ((ProjectResourceBean) ow.this.C.get(i)).totalSoundDuration = ow.this.b(a2);
            }
            int i3 = ((ProjectResourceBean) ow.this.C.get(i)).totalSoundDuration / 1000;
            aVar.z.setText(String.format("%d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)));
            aVar.B.setText(String.format("%d:%02d", Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)));
            aVar.u.setChecked(((ProjectResourceBean) ow.this.C.get(i)).isSelected);
            aVar.w.setText(((ProjectResourceBean) ow.this.C.get(i)).resName);
            if (ow.this.E == i) {
                if (ow.this.w != null && ow.this.w.isPlaying()) {
                    aVar.x.setImageResource(2131165804);
                } else {
                    aVar.x.setImageResource(2131165434);
                }
            } else {
                aVar.x.setImageResource(2131165434);
            }
            aVar.A.setMax(((ProjectResourceBean) ow.this.C.get(i)).totalSoundDuration / 100);
            aVar.A.setProgress(((ProjectResourceBean) ow.this.C.get(i)).curSoundPosition / 100);
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427568, viewGroup, false));
        }

        @Override
        public int a() {
            return ow.this.C.size();
        }

        public final void a(ProjectResourceBean projectResourceBean, ImageView imageView) {
            String a2;
            if (!projectResourceBean.isNew) {
                a2 = ow.this.a(projectResourceBean);
            } else {
                a2 = projectResourceBean.resFullName;
            }
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(a2);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(ow.this.getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into((DrawableRequestBuilder<byte[]>) new kw(this, imageView));
                } else {
                    imageView.setImageResource(2131165520);
                    imageView.setBackgroundResource(2131165346);
                }
            } catch (IllegalArgumentException unused) {
                imageView.setImageResource(2131165520);
                imageView.setBackgroundResource(2131165346);
            } catch (RuntimeException unused2) {
                imageView.setImageResource(2131165520);
                imageView.setBackgroundResource(2131165346);
            }
            mediaMetadataRetriever.release();
        }
    }

    public ArrayList<ProjectResourceBean> d() {
        return this.C;
    }

    public final void e() {
        this.f = getActivity().getIntent().getStringExtra("sc_id");
        this.A = jC.d(this.f).o();
        ArrayList<ProjectResourceBean> arrayList = jC.d(this.f).c;
        if (arrayList == null) {
            return;
        }
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.C.add(it.next().clone());
        }
    }

    public void f() {
        this.u.cancel();
        MediaPlayer mediaPlayer = this.m;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.m.pause();
            this.o.setImageResource(2131165812);
        }
        int i = this.E;
        if (i != -1) {
            this.C.get(i).curSoundPosition = 0;
            this.E = -1;
            this.D = -1;
            this.l.c();
        }
        MediaPlayer mediaPlayer2 = this.w;
        if (mediaPlayer2 == null || !mediaPlayer2.isPlaying()) {
            return;
        }
        this.w.pause();
    }

    public final void g() {
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void h() {
        ArrayList<ProjectResourceBean> arrayList = this.C;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<ProjectResourceBean> it = this.C.iterator();
            while (it.hasNext()) {
                ProjectResourceBean next = it.next();
                if (next.isNew) {
                    this.B.c(a(next.resFullName));
                }
            }
        }
        Iterator<ProjectResourceBean> it2 = this.C.iterator();
        while (it2.hasNext()) {
            ProjectResourceBean next2 = it2.next();
            if (next2.isNew) {
                try {
                    String a2 = a(next2);
                    if (this.B.e(a2)) {
                        this.B.c(a2);
                    }
                    a(next2.resFullName, a2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < this.C.size(); i++) {
            ProjectResourceBean projectResourceBean = this.C.get(i);
            if (projectResourceBean.isNew) {
                String str = projectResourceBean.resFullName;
                String substring = str.substring(str.lastIndexOf("."));
                this.C.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.resName + substring));
            }
        }
        jC.d(this.f).c(this.C);
        jC.d(this.f).y();
        jC.a(this.f).c(jC.d(this.f));
        jC.a(this.f).k();
    }

    public void b(ArrayList<ProjectResourceBean> arrayList) {
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.C.add(it.next());
        }
    }

    public boolean c(String str) {
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void a(boolean z) {
        this.k = z;
        getActivity().invalidateOptionsMenu();
        g();
        if (this.k) {
            f();
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(8);
        }
        this.l.c();
    }

    public final void b(int i) {
        this.u = new Timer();
        this.v = new iw(this, i);
        this.u.schedule(this.v, 100L, 100L);
    }

    public final ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final int b(String str) {
        long j;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            j = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
        } catch (IllegalArgumentException unused) {
            j = 0;
        }
        return (int) j;
    }

    public final void a(int i) {
        String a2;
        if (this.E == i) {
            MediaPlayer mediaPlayer = this.w;
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    this.u.cancel();
                    this.w.pause();
                    this.C.get(this.E).curSoundPosition = this.w.getCurrentPosition();
                    this.l.c(this.E);
                    return;
                }
                this.w.start();
                b(i);
                this.l.c();
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer2 = this.w;
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
            this.u.cancel();
            this.w.pause();
            this.w.release();
        }
        int i2 = this.D;
        if (i2 != -1) {
            this.C.get(i2).curSoundPosition = 0;
            this.l.c(this.D);
        }
        this.E = i;
        this.D = i;
        this.l.c(this.E);
        this.w = new MediaPlayer();
        this.w.setAudioStreamType(3);
        this.w.setOnPreparedListener(new fw(this, i));
        this.w.setOnCompletionListener(new gw(this));
        try {
            if (this.C.get(this.E).isNew) {
                a2 = this.C.get(this.E).resFullName;
            } else {
                a2 = a(this.C.get(this.E));
            }
            this.w.setDataSource(a2);
            this.w.prepare();
        } catch (Exception e) {
            this.E = -1;
            this.l.c(this.E);
            e.printStackTrace();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            String str = next.resName;
            if (c(str)) {
                arrayList3.add(str);
            } else {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, str, next.resFullName);
                projectResourceBean.isNew = true;
                arrayList2.add(projectResourceBean);
            }
        }
        if (arrayList3.size() > 0) {
            String a2 = xB.b().a(getActivity(), 2131624950);
            Iterator it2 = arrayList3.iterator();
            String str2 = "";
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (str2.length() > 0) {
                    str2 = str2 + ", ";
                }
                str2 = str2 + str3;
            }
            bB.a(getActivity(), a2 + "\n[" + str2 + "]", 1).show();
        } else {
            bB.a(getActivity(), xB.b().a(getActivity(), 2131625280), 1).show();
            b(arrayList2);
            this.l.c();
        }
        i();
    }

    public final String a(String str) {
        return this.A + File.separator + str;
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return this.A + File.separator + projectResourceBean.resName + substring;
    }

    public final void a(String str, String str2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (FileNotFoundException | Exception unused) {
        }
    }
}
