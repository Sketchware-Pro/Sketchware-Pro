package a.a.a;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundImportActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sketchware.remod.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import mod.SketchwareUtil;

public class Yv extends qA implements View.OnClickListener {
    public RecyclerView f;
    public String g;
    public oB i;
    public ArrayList<ProjectResourceBean> j;
    public TextView l;
    public Button m;
    public TimerTask o;
    public MediaPlayer p;
    public String h = "";
    public a k = null;
    public Timer n = new Timer();
    public int q = -1;
    public int r = -1;

    public void h() {
        d();
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + next.resFullName));
            }
        }
        if (arrayList.size() > 0) {
            ArrayList<ProjectResourceBean> d = ((ManageSoundActivity) getActivity()).m().d();
            Intent intent = new Intent(getActivity(), ManageSoundImportActivity.class);
            intent.putParcelableArrayListExtra("project_sounds", d);
            intent.putParcelableArrayListExtra("selected_collections", arrayList);
            startActivityForResult(intent, 232);
        }
        f();
        this.k.notifyDataSetChanged();
    }

    public final void i() {
        Iterator<ProjectResourceBean> it = this.j.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSelected) {
                i++;
            }
        }
        if (i > 0) {
            this.m.setText(xB.b().a(getContext(), R.string.common_word_import_count, Integer.valueOf(i)).toUpperCase());
            this.m.setVisibility(View.VISIBLE);
            return;
        }
        this.m.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.i = new oB();
        this.i.f(this.h);
        if (bundle == null) {
            c();
        } else {
            this.g = bundle.getString("sc_id");
            this.h = bundle.getString("dir_path");
        }
        e();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 232 && i2 == -1 && intent != null) {
            a(intent.getParcelableArrayListExtra("results"));
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == R.id.btn_import) {
            this.m.setVisibility(View.GONE);
            h();
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_sound_list, viewGroup, false);
        this.f = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        this.f.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.k = new a();
        this.f.setAdapter(this.k);
        this.l = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.l.setText(xB.b().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        this.m = (Button) viewGroup2.findViewById(R.id.btn_import);
        this.m.setText(xB.b().a(getContext(), R.string.common_word_import).toUpperCase());
        this.m.setOnClickListener(this);
        this.m.setVisibility(View.GONE);
        viewGroup2.findViewById(R.id.fab).setVisibility(View.GONE);
        return viewGroup2;
    }

    @Override
    public void onPause() {
        super.onPause();
        d();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("sc_id", this.g);
        bundle.putString("dir_path", this.h);
    }

    class a extends RecyclerView.Adapter<a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox t;
            public ImageView u;
            public TextView v;
            public ImageView w;
            public TextView x;
            public ProgressBar y;
            public TextView z;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.t = (CheckBox) itemView.findViewById(R.id.chk_select);
                this.u = (ImageView) itemView.findViewById(R.id.img_album);
                this.v = (TextView) itemView.findViewById(R.id.tv_sound_name);
                this.w = (ImageView) itemView.findViewById(R.id.img_play);
                this.x = (TextView) itemView.findViewById(R.id.tv_currenttime);
                this.y = (ProgressBar) itemView.findViewById(R.id.prog_playtime);
                this.z = (TextView) itemView.findViewById(R.id.tv_endtime);
                this.w.setOnClickListener(v -> {
                    if (!mB.a()) {
                        Yv.this.a(getLayoutPosition());
                    }
                });
                this.t.setOnClickListener(v -> {
                    c = getLayoutPosition();
                    j.get(c).isSelected = t.isChecked();
                    i();
                    notifyItemChanged(c);
                });
            }
        }

        public a() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String str = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + ((ProjectResourceBean) Yv.this.j.get(position)).resFullName;
            holder.t.setVisibility(View.VISIBLE);
            a(str, holder.u);
            int i2 = ((ProjectResourceBean) Yv.this.j.get(position)).curSoundPosition / 1000;
            if (((ProjectResourceBean) Yv.this.j.get(position)).totalSoundDuration == 0) {
                ((ProjectResourceBean) Yv.this.j.get(position)).totalSoundDuration = Yv.this.a(str);
            }
            int i3 = ((ProjectResourceBean) Yv.this.j.get(position)).totalSoundDuration / 1000;
            holder.x.setText(String.format("%d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)));
            holder.z.setText(String.format("%d:%02d", Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)));
            holder.t.setChecked(((ProjectResourceBean) Yv.this.j.get(position)).isSelected);
            holder.v.setText(((ProjectResourceBean) Yv.this.j.get(position)).resName);
            if (Yv.this.r == position) {
                if (Yv.this.p != null && Yv.this.p.isPlaying()) {
                    holder.w.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.w.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.w.setImageResource(R.drawable.circled_play_96_blue);
            }
            holder.y.setMax(((ProjectResourceBean) Yv.this.j.get(position)).totalSoundDuration / 100);
            holder.y.setProgress(((ProjectResourceBean) Yv.this.j.get(position)).curSoundPosition / 100);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return Yv.this.j.size();
        }

        public final void a(String str, ImageView imageView) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(Yv.this.getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(glideDrawable);
                        }
                    });
                } else {
                    imageView.setImageResource(R.drawable.default_album_art_200dp);
                    imageView.setBackgroundResource(R.drawable.bg_outline_album);
                }
            } catch (IllegalArgumentException unused) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            }
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                SketchwareUtil.toastError("Failed to release file " + str + ": " + e);
            }
        }
    }

    public void d() {
        this.n.cancel();
        int i = this.r;
        if (i != -1) {
            this.j.get(i).curSoundPosition = 0;
            this.r = -1;
            this.q = -1;
            this.k.notifyDataSetChanged();
        }
        MediaPlayer mediaPlayer = this.p;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.p.pause();
    }

    public void e() {
        this.j = Qp.g().f();
        this.k.notifyDataSetChanged();
        g();
    }

    public final void f() {
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void g() {
        if (this.j.size() == 0) {
            this.l.setVisibility(View.VISIBLE);
            this.f.setVisibility(View.GONE);
            return;
        }
        this.f.setVisibility(View.VISIBLE);
        this.l.setVisibility(View.GONE);
    }

    public final void b(int i) {
        this.n = new Timer();
        this.o = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    if (p == null) {
                        n.cancel();
                    } else {
                        a.ViewHolder viewHolder = (a.ViewHolder) f.findViewHolderForLayoutPosition(i);
                        int seconds = p.getCurrentPosition() / 1000;
                        viewHolder.x.setText(String.format("%d:%02d", seconds / 60, seconds % 60));
                        viewHolder.y.setProgress(p.getCurrentPosition() / 100);
                    }
                });
            }
        };
        this.n.schedule(this.o, 100L, 100L);
    }

    public void c() {
        this.g = getActivity().getIntent().getStringExtra("sc_id");
        this.h = getActivity().getIntent().getStringExtra("dir_path");
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageSoundActivity) getActivity()).m().a(arrayList2);
            ((ManageSoundActivity) getActivity()).f(0);
        }
    }

    public final void a(int i) {
        if (this.r == i) {
            MediaPlayer mediaPlayer = this.p;
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    this.n.cancel();
                    this.p.pause();
                    this.j.get(this.r).curSoundPosition = this.p.getCurrentPosition();
                    this.k.notifyItemChanged(this.r);
                    return;
                }
                this.p.start();
                b(i);
                this.k.notifyDataSetChanged();
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer2 = this.p;
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
            this.n.cancel();
            this.p.pause();
            this.p.release();
        }
        int i2 = this.q;
        if (i2 != -1) {
            this.j.get(i2).curSoundPosition = 0;
            this.k.notifyItemChanged(this.q);
        }
        this.r = i;
        this.q = i;
        this.k.notifyItemChanged(this.r);
        this.p = new MediaPlayer();
        this.p.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.p.setOnPreparedListener(mp -> {
            p.start();
            b(i);
            k.notifyItemChanged(r);
        });
        this.p.setOnCompletionListener(v -> {
            n.cancel();
            j.get(r).curSoundPosition = 0;
            k.notifyItemChanged(r);
            r = -1;
        });
        try {
            this.p.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + this.j.get(this.r).resFullName);
            this.p.prepare();
        } catch (Exception e) {
            this.r = -1;
            this.k.notifyItemChanged(this.r);
            e.printStackTrace();
        }
    }

    public final int a(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }
}
