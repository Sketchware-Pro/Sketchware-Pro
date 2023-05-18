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
    private RecyclerView soundsList;
    private String sc_id;
    private ArrayList<ProjectResourceBean> sounds;
    private TextView noSoundsText;
    private Button importSounds;
    private MediaPlayer mediaPlayer;
    private String h = "";
    private a adapter = null;
    private Timer timer = new Timer();
    private int q = -1;
    private int r = -1;

    private void h() {
        d();
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        for (ProjectResourceBean next : sounds) {
            if (next.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + next.resFullName));
            }
        }
        if (arrayList.size() > 0) {
            ArrayList<ProjectResourceBean> d = ((ManageSoundActivity) requireActivity()).m().d();
            Intent intent = new Intent(requireActivity(), ManageSoundImportActivity.class);
            intent.putParcelableArrayListExtra("project_sounds", d);
            intent.putParcelableArrayListExtra("selected_collections", arrayList);
            startActivityForResult(intent, 232);
        }
        f();
        adapter.notifyDataSetChanged();
    }

    private void i() {
        int i = 0;
        for (ProjectResourceBean projectResourceBean : sounds) {
            if (projectResourceBean.isSelected) {
                i++;
            }
        }
        if (i > 0) {
            importSounds.setText(xB.b().a(getContext(), R.string.common_word_import_count, i).toUpperCase());
            importSounds.setVisibility(View.VISIBLE);
        } else {
            importSounds.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        oB fileUtil = new oB();
        // create dirs if they don't exist
        fileUtil.f(h);
        if (savedInstanceState == null) {
            c();
        } else {
            sc_id = savedInstanceState.getString("sc_id");
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
            importSounds.setVisibility(View.GONE);
            h();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup2 = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        soundsList = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        soundsList.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        adapter = new a();
        soundsList.setAdapter(adapter);
        noSoundsText = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        noSoundsText.setText(xB.b().a(requireActivity(), R.string.design_manager_sound_description_guide_add_sound));
        importSounds = (Button) viewGroup2.findViewById(R.id.btn_import);
        importSounds.setText(xB.b().a(getContext(), R.string.common_word_import).toUpperCase());
        importSounds.setOnClickListener(this);
        importSounds.setVisibility(View.GONE);
        viewGroup2.findViewById(R.id.fab).setVisibility(View.GONE);
        return viewGroup2;
    }

    @Override
    public void onPause() {
        super.onPause();
        d();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sc_id", sc_id);
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
                    sounds.get(c).isSelected = t.isChecked();
                    i();
                    notifyItemChanged(c);
                });
            }
        }

        public a() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);
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
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
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
            return sounds.size();
        }

        public final void a(String str, ImageView imageView) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(requireActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new SimpleTarget<GlideDrawable>() {
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
        timer.cancel();
        if (r != -1) {
            sounds.get(r).curSoundPosition = 0;
            r = -1;
            q = -1;
            adapter.notifyDataSetChanged();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void e() {
        sounds = Qp.g().f();
        adapter.notifyDataSetChanged();
        g();
    }

    private void f() {
        for (ProjectResourceBean projectResourceBean : sounds) {
            projectResourceBean.isSelected = false;
        }
    }

    private void g() {
        if (sounds.size() == 0) {
            noSoundsText.setVisibility(View.VISIBLE);
            soundsList.setVisibility(View.GONE);
        } else {
            soundsList.setVisibility(View.VISIBLE);
            noSoundsText.setVisibility(View.GONE);
        }
    }

    private void b(int i) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requireActivity().runOnUiThread(() -> {
                    if (mediaPlayer == null) {
                        timer.cancel();
                    } else {
                        Yv.a.ViewHolder viewHolder = (Yv.a.ViewHolder) soundsList.findViewHolderForLayoutPosition(i);
                        int seconds = mediaPlayer.getCurrentPosition() / 1000;
                        viewHolder.x.setText(String.format("%d:%02d", seconds / 60, seconds % 60));
                        viewHolder.y.setProgress(mediaPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        }, 100L, 100L);
    }

    private void c() {
        sc_id = requireActivity().getIntent().getStringExtra("sc_id");
        h = requireActivity().getIntent().getStringExtra("dir_path");
    }

    private void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageSoundActivity) requireActivity()).m().a(arrayList2);
            ((ManageSoundActivity) requireActivity()).f(0);
        }
    }

    private void a(int i) {
        if (r == i) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    timer.cancel();
                    mediaPlayer.pause();
                    sounds.get(r).curSoundPosition = mediaPlayer.getCurrentPosition();
                    adapter.notifyItemChanged(r);
                    return;
                }
                mediaPlayer.start();
                b(i);
                adapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            timer.cancel();
            mediaPlayer.pause();
            mediaPlayer.release();
        }
        if (q != -1) {
            sounds.get(q).curSoundPosition = 0;
            adapter.notifyItemChanged(q);
        }
        r = i;
        q = i;
        adapter.notifyItemChanged(r);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayer.start();
            b(i);
            adapter.notifyItemChanged(r);
        });
        mediaPlayer.setOnCompletionListener(v -> {
            timer.cancel();
            sounds.get(r).curSoundPosition = 0;
            adapter.notifyItemChanged(r);
            r = -1;
        });
        try {
            mediaPlayer.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + sounds.get(r).resFullName);
            mediaPlayer.prepare();
        } catch (Exception e) {
            r = -1;
            adapter.notifyItemChanged(r);
            e.printStackTrace();
        }
    }

    private int a(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }
}
