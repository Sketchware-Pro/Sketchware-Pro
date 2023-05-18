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
        for (ProjectResourceBean next : j) {
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
        k.notifyDataSetChanged();
    }

    public final void i() {
        int i = 0;
        for (ProjectResourceBean projectResourceBean : j) {
            if (projectResourceBean.isSelected) {
                i++;
            }
        }
        if (i > 0) {
            m.setText(xB.b().a(getContext(), R.string.common_word_import_count, i).toUpperCase());
            m.setVisibility(View.VISIBLE);
        } else {
            m.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        i = new oB();
        i.f(h);
        if (savedInstanceState == null) {
            c();
        } else {
            g = savedInstanceState.getString("sc_id");
            h = savedInstanceState.getString("dir_path");
        }
        e();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 232 && resultCode == -1 && data != null) {
            a(data.getParcelableArrayListExtra("results"));
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.btn_import) {
            m.setVisibility(View.GONE);
            h();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup2 = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        f = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        f.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        k = new a();
        f.setAdapter(k);
        l = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        l.setText(xB.b().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        m = (Button) viewGroup2.findViewById(R.id.btn_import);
        m.setText(xB.b().a(getContext(), R.string.common_word_import).toUpperCase());
        m.setOnClickListener(this);
        m.setVisibility(View.GONE);
        viewGroup2.findViewById(R.id.fab).setVisibility(View.GONE);
        return viewGroup2;
    }

    @Override
    public void onPause() {
        super.onPause();
        d();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sc_id", g);
        outState.putString("dir_path", h);
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
                t = itemView.findViewById(R.id.chk_select);
                u = itemView.findViewById(R.id.img_album);
                v = itemView.findViewById(R.id.tv_sound_name);
                w = itemView.findViewById(R.id.img_play);
                x = itemView.findViewById(R.id.tv_currenttime);
                y = itemView.findViewById(R.id.prog_playtime);
                z = itemView.findViewById(R.id.tv_endtime);
                w.setOnClickListener(v -> {
                    if (!mB.a()) {
                        Yv.this.a(getLayoutPosition());
                    }
                });
                t.setOnClickListener(v -> {
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
            ProjectResourceBean bean = j.get(position);
            String str = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            holder.t.setVisibility(View.VISIBLE);
            a(str, holder.u);
            int i2 = bean.curSoundPosition / 1000;
            if (bean.totalSoundDuration == 0) {
                bean.totalSoundDuration = Yv.this.a(str);
            }
            String text = String.format("%d:%02d", i2 / 60, i2 % 60);
            holder.x.setText(text);
            holder.z.setText(text);
            holder.t.setChecked(bean.isSelected);
            holder.v.setText(bean.resName);
            if (r == position) {
                if (p != null && p.isPlaying()) {
                    holder.w.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.w.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.w.setImageResource(R.drawable.circled_play_96_blue);
            }
            holder.y.setMax(bean.totalSoundDuration / 100);
            holder.y.setProgress(bean.curSoundPosition / 100);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return j.size();
        }

        public final void a(String str, ImageView imageView) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
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
        n.cancel();
        if (r != -1) {
            j.get(r).curSoundPosition = 0;
            r = -1;
            q = -1;
            k.notifyDataSetChanged();
        }
        if (p != null && p.isPlaying()) {
            p.pause();
        }
    }

    public void e() {
        j = Qp.g().f();
        k.notifyDataSetChanged();
        g();
    }

    public final void f() {
        for (ProjectResourceBean projectResourceBean : j) {
            projectResourceBean.isSelected = false;
        }
    }

    public void g() {
        if (j.size() == 0) {
            l.setVisibility(View.VISIBLE);
            f.setVisibility(View.GONE);
        } else {
            f.setVisibility(View.VISIBLE);
            l.setVisibility(View.GONE);
        }
    }

    public final void b(int i) {
        n = new Timer();
        o = new TimerTask() {
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
        n.schedule(o, 100L, 100L);
    }

    public void c() {
        g = getActivity().getIntent().getStringExtra("sc_id");
        h = getActivity().getIntent().getStringExtra("dir_path");
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageSoundActivity) getActivity()).m().a(arrayList2);
            ((ManageSoundActivity) getActivity()).f(0);
        }
    }

    public final void a(int i) {
        if (r == i) {
            if (p != null) {
                if (p.isPlaying()) {
                    n.cancel();
                    p.pause();
                    j.get(r).curSoundPosition = p.getCurrentPosition();
                    k.notifyItemChanged(r);
                    return;
                }
                p.start();
                b(i);
                k.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (p != null && p.isPlaying()) {
            n.cancel();
            p.pause();
            p.release();
        }
        if (q != -1) {
            j.get(q).curSoundPosition = 0;
            k.notifyItemChanged(q);
        }
        r = i;
        q = i;
        k.notifyItemChanged(r);
        p = new MediaPlayer();
        p.setAudioStreamType(AudioManager.STREAM_MUSIC);
        p.setOnPreparedListener(mp -> {
            p.start();
            b(i);
            k.notifyItemChanged(r);
        });
        p.setOnCompletionListener(v -> {
            n.cancel();
            j.get(r).curSoundPosition = 0;
            k.notifyItemChanged(r);
            r = -1;
        });
        try {
            p.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + j.get(r).resFullName);
            p.prepare();
        } catch (Exception e) {
            r = -1;
            k.notifyItemChanged(r);
            e.printStackTrace();
        }
    }

    public final int a(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }
}
