package a.a.a;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.AddSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

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
            this.z.setVisibility(View.VISIBLE);
            this.g.setVisibility(View.GONE);
            return;
        }
        this.g.setVisibility(View.VISIBLE);
        this.z.setVisibility(View.GONE);
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
        this.l.notifyDataSetChanged();
        i();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 269) {
            if (i2 == Activity.RESULT_OK) {
                this.C.add((ProjectResourceBean) intent.getParcelableExtra("project_resource"));
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), 1).show();
                this.l.notifyDataSetChanged();
                i();
                ((ManageSoundActivity) getActivity()).l().e();
            }
        } else if (i == 270 && i2 == Activity.RESULT_OK) {
            this.C.set(this.l.c, (ProjectResourceBean) intent.getParcelableExtra("project_resource"));
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_edit_complete), 1).show();
            this.l.notifyDataSetChanged();
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
        if (id != R.id.btn_cancel) {
            if (id != R.id.btn_delete || !this.k) {
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
                    this.l.notifyDataSetChanged();
                    this.E = -1;
                    this.D = -1;
                    a(false);
                    i();
                    bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_complete_delete), 1).show();
                    this.H.show();
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
        menuInflater.inflate(R.menu.manage_sound_menu, menu);
        if (this.k) {
            menu.findItem(R.id.menu_sound_delete).setVisible(false);
        } else {
            menu.findItem(R.id.menu_sound_delete).setVisible(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_sound_list, viewGroup, false);
        setHasOptionsMenu(true);
        this.h = (LinearLayout) viewGroup2.findViewById(R.id.layout_btn_group);
        this.i = (Button) viewGroup2.findViewById(R.id.btn_delete);
        this.j = (Button) viewGroup2.findViewById(R.id.btn_cancel);
        this.H = (FloatingActionButton) viewGroup2.findViewById(R.id.fab);
        this.i.setText(xB.b().a(getActivity(), R.string.common_word_delete));
        this.j.setText(xB.b().a(getActivity(), R.string.common_word_cancel));
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.g = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        this.g.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.l = new a(this.g);
        this.g.setAdapter(this.l);
        this.z = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.z.setText(xB.b().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        this.z.setOnClickListener(new dw(this));
        this.H.setOnClickListener(new ew(this));
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_sound_add:
                a(false);
                j();
                break;
            case R.id.menu_sound_delete:
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

    class a extends RecyclerView.Adapter<a.a> {
        public int c = -1;

        class a extends RecyclerView.ViewHolder {
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
                this.t = (CardView) view.findViewById(R.id.layout_item);
                this.u = (CheckBox) view.findViewById(R.id.chk_select);
                this.v = (ImageView) view.findViewById(R.id.img_album);
                this.y = (ImageView) view.findViewById(R.id.img_delete);
                this.w = (TextView) view.findViewById(R.id.tv_sound_name);
                this.x = (ImageView) view.findViewById(R.id.img_play);
                this.z = (TextView) view.findViewById(R.id.tv_currenttime);
                this.A = (ProgressBar) view.findViewById(R.id.prog_playtime);
                this.B = (TextView) view.findViewById(R.id.tv_endtime);
                this.C = (LinearLayout) view.findViewById(R.id.delete_img_container);
                this.x.setOnClickListener(new lw(this, a.this));
                this.u.setVisibility(View.GONE);
                this.t.setOnClickListener(new mw(this, a.this));
                this.t.setOnLongClickListener(new nw(this, a.this));
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new jw(this, ow.this));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ow.a.a holder, int position) {
            String a2;
            ow owVar = ow.this;
            if (!owVar.k) {
                a((ProjectResourceBean) owVar.C.get(position), holder.v);
                holder.v.setVisibility(View.VISIBLE);
                holder.C.setVisibility(View.GONE);
            } else {
                holder.v.setVisibility(View.GONE);
                holder.C.setVisibility(View.VISIBLE);
            }
            if (((ProjectResourceBean) ow.this.C.get(position)).isSelected) {
                holder.y.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.y.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            int i2 = ((ProjectResourceBean) ow.this.C.get(position)).curSoundPosition / 1000;
            if (((ProjectResourceBean) ow.this.C.get(position)).totalSoundDuration == 0) {
                if (((ProjectResourceBean) ow.this.C.get(position)).isNew) {
                    a2 = ((ProjectResourceBean) ow.this.C.get(position)).resFullName;
                } else {
                    ow owVar2 = ow.this;
                    a2 = owVar2.a((ProjectResourceBean) owVar2.C.get(position));
                }
                ((ProjectResourceBean) ow.this.C.get(position)).totalSoundDuration = ow.this.b(a2);
            }
            int i3 = ((ProjectResourceBean) ow.this.C.get(position)).totalSoundDuration / 1000;
            holder.z.setText(String.format("%d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)));
            holder.B.setText(String.format("%d:%02d", Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)));
            holder.u.setChecked(((ProjectResourceBean) ow.this.C.get(position)).isSelected);
            holder.w.setText(((ProjectResourceBean) ow.this.C.get(position)).resName);
            if (ow.this.E == position) {
                if (ow.this.w != null && ow.this.w.isPlaying()) {
                    holder.x.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.x.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.x.setImageResource(R.drawable.circled_play_96_blue);
            }
            holder.A.setMax(((ProjectResourceBean) ow.this.C.get(position)).totalSoundDuration / 100);
            holder.A.setProgress(((ProjectResourceBean) ow.this.C.get(position)).curSoundPosition / 100);
        }

        @Override
        @NonNull
        public ow.a.a onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new a(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
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
                    imageView.setImageResource(R.drawable.default_album_art_200dp);
                    imageView.setBackgroundResource(R.drawable.bg_outline_album);
                }
            } catch (IllegalArgumentException unused) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            } catch (RuntimeException unused2) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
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
            this.o.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
        int i = this.E;
        if (i != -1) {
            this.C.get(i).curSoundPosition = 0;
            this.E = -1;
            this.D = -1;
            this.l.notifyDataSetChanged();
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
            this.h.setVisibility(View.VISIBLE);
        } else {
            this.h.setVisibility(View.GONE);
        }
        this.l.notifyDataSetChanged();
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
            j = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
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
                    this.l.notifyItemChanged(this.E);
                    return;
                }
                this.w.start();
                b(i);
                this.l.notifyDataSetChanged();
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
            this.l.notifyItemChanged(this.D);
        }
        this.E = i;
        this.D = i;
        this.l.notifyItemChanged(this.E);
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
            this.l.notifyItemChanged(this.E);
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
            String a2 = xB.b().a(getActivity(), R.string.common_message_name_unavailable);
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
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_import_complete), 1).show();
            b(arrayList2);
            this.l.notifyDataSetChanged();
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
