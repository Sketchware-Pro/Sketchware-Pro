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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        if (C.size() == 0) {
            z.setVisibility(View.VISIBLE);
            g.setVisibility(View.GONE);
        } else {
            g.setVisibility(View.VISIBLE);
            z.setVisibility(View.GONE);
        }
    }

    public final void j() {
        f();
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", f);
        intent.putExtra("dir_path", A);
        intent.putExtra("sound_names", c());
        startActivityForResult(intent, 269);
    }

    public final void k() {
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", f);
        intent.putExtra("dir_path", A);
        intent.putExtra("sound_names", c());
        intent.putExtra("request_code", 270);
        intent.putExtra("project_resource", C.get(l.c));
        startActivityForResult(intent, 270);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        B = new oB();
        B.f(A);
        C = new ArrayList<>();
        if (savedInstanceState == null) {
            e();
        } else {
            f = savedInstanceState.getString("sc_id");
            A = savedInstanceState.getString("dir_path");
            C = savedInstanceState.getParcelableArrayList("sounds");
        }
        l.notifyDataSetChanged();
        i();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 269) {
            if (resultCode == Activity.RESULT_OK) {
                C.add((ProjectResourceBean) data.getParcelableExtra("project_resource"));
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), 1).show();
                l.notifyDataSetChanged();
                i();
                ((ManageSoundActivity) getActivity()).l().e();
            }
        } else if (requestCode == 270 && resultCode == Activity.RESULT_OK) {
            C.set(l.c, (ProjectResourceBean) data.getParcelableExtra("project_resource"));
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_edit_complete), 1).show();
            l.notifyDataSetChanged();
            i();
            ((ManageSoundActivity) getActivity()).l().e();
        }
    }

    @Override
    public void onClick(View v) {
        if (mB.a()) {
            return;
        }
        int id = v.getId();
        if (id != R.id.btn_cancel) {
            if (id != R.id.btn_delete || !k) {
                return;
            }
            int size = C.size();
            while (true) {
                size--;
                if (size >= 0) {
                    ProjectResourceBean projectResourceBean = C.get(size);
                    projectResourceBean.curSoundPosition = 0;
                    if (projectResourceBean.isSelected) {
                        C.remove(size);
                    }
                } else {
                    l.notifyDataSetChanged();
                    E = -1;
                    D = -1;
                    a(false);
                    i();
                    bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_complete_delete), 1).show();
                    H.show();
                    return;
                }
            }
        } else if (k) {
            a(false);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manage_sound_menu, menu);
        menu.findItem(R.id.menu_sound_delete).setVisible(!k);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup2 = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        setHasOptionsMenu(true);
        h = viewGroup2.findViewById(R.id.layout_btn_group);
        i = viewGroup2.findViewById(R.id.btn_delete);
        j = viewGroup2.findViewById(R.id.btn_cancel);
        H = viewGroup2.findViewById(R.id.fab);
        i.setText(xB.b().a(getActivity(), R.string.common_word_delete));
        j.setText(xB.b().a(getActivity(), R.string.common_word_cancel));
        i.setOnClickListener(this);
        j.setOnClickListener(this);
        g = viewGroup2.findViewById(R.id.sound_list);
        g.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        l = new a(g);
        g.setAdapter(l);
        z = viewGroup2.findViewById(R.id.tv_guide);
        z.setText(xB.b().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        z.setOnClickListener(v -> {
            if (!mB.a()) {
                a(false);
                j();
            }
        });
        H.setOnClickListener(v -> {
            if (!mB.a()) {
                a(false);
                j();
            }
        });
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sound_add:
                a(false);
                j();
                break;
            case R.id.menu_sound_delete:
                a(!k);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        f();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", f);
        outState.putString("dir_path", A);
        outState.putParcelableArrayList("sounds", C);
        super.onSaveInstanceState(outState);
    }

    class a extends RecyclerView.Adapter<a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
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

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.layout_item);
                u = itemView.findViewById(R.id.chk_select);
                v = itemView.findViewById(R.id.img_album);
                y = itemView.findViewById(R.id.img_delete);
                w = itemView.findViewById(R.id.tv_sound_name);
                x = itemView.findViewById(R.id.img_play);
                z = itemView.findViewById(R.id.tv_currenttime);
                A = itemView.findViewById(R.id.prog_playtime);
                B = itemView.findViewById(R.id.tv_endtime);
                C = itemView.findViewById(R.id.delete_img_container);
                x.setOnClickListener(v -> {
                    if (!mB.a()) {
                        c = getLayoutPosition();
                        if (!k) {
                            ow.this.a(c);
                        }
                    }
                });
                u.setVisibility(View.GONE);
                t.setOnClickListener(v -> {
                    if (!mB.a()) {
                        c = getLayoutPosition();
                    }
                    if (k) {
                        u.setChecked(!u.isChecked());
                        ow.this.C.get(c).isSelected = u.isChecked();
                        notifyItemChanged(c);
                    } else {
                        f();
                        k();
                    }
                });
                t.setOnLongClickListener(v -> {
                    ow.this.a(true);
                    c = getLayoutPosition();
                    u.setChecked(!u.isChecked());
                    ow.this.C.get(c).isSelected = u.isChecked();
                    return true;
                });
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (H.isEnabled()) {
                                H.hide();
                            }
                        } else if (dy < -2) {
                            if (H.isEnabled()) {
                                H.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
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
            } catch (RuntimeException unused2) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            }
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<ProjectResourceBean> d() {
        return C;
    }

    public final void e() {
        f = getActivity().getIntent().getStringExtra("sc_id");
        A = jC.d(f).o();
        ArrayList<ProjectResourceBean> arrayList = jC.d(f).c;
        if (arrayList == null) {
            return;
        }
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            C.add(it.next().clone());
        }
    }

    public void f() {
        u.cancel();
        MediaPlayer mediaPlayer = m;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            m.pause();
            o.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
        int i = E;
        if (i != -1) {
            C.get(i).curSoundPosition = 0;
            E = -1;
            D = -1;
            l.notifyDataSetChanged();
        }
        MediaPlayer mediaPlayer2 = w;
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
            w.pause();
        }
    }

    public final void g() {
        Iterator<ProjectResourceBean> it = C.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void h() {
        ArrayList<ProjectResourceBean> arrayList = C;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<ProjectResourceBean> it = C.iterator();
            while (it.hasNext()) {
                ProjectResourceBean next = it.next();
                if (next.isNew) {
                    B.c(a(next.resFullName));
                }
            }
        }
        Iterator<ProjectResourceBean> it2 = C.iterator();
        while (it2.hasNext()) {
            ProjectResourceBean next2 = it2.next();
            if (next2.isNew) {
                try {
                    String a2 = a(next2);
                    if (B.e(a2)) {
                        B.c(a2);
                    }
                    a(next2.resFullName, a2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < C.size(); i++) {
            ProjectResourceBean projectResourceBean = C.get(i);
            if (projectResourceBean.isNew) {
                String str = projectResourceBean.resFullName;
                String substring = str.substring(str.lastIndexOf("."));
                C.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.resName + substring));
            }
        }
        jC.d(f).c(C);
        jC.d(f).y();
        jC.a(f).c(jC.d(f));
        jC.a(f).k();
    }

    public void b(ArrayList<ProjectResourceBean> arrayList) {
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            C.add(it.next());
        }
    }

    public boolean c(String str) {
        Iterator<ProjectResourceBean> it = C.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void a(boolean z) {
        k = z;
        getActivity().invalidateOptionsMenu();
        g();
        if (k) {
            f();
            h.setVisibility(View.VISIBLE);
        } else {
            h.setVisibility(View.GONE);
        }
        l.notifyDataSetChanged();
    }

    public final void b(int i) {
        u = new Timer();
        v = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    if (w == null) {
                        u.cancel();
                    } else {
                        a.ViewHolder holder = (a.ViewHolder) g.findViewHolderForLayoutPosition(i);
                        int positionInS = w.getCurrentPosition() / 1000;
                        holder.z.setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
                        holder.A.setProgress(w.getCurrentPosition() / 100);
                    }
                });
            }
        };
        u.schedule(v, 100L, 100L);
    }

    public final ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = C.iterator();
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
        if (E == i) {
            MediaPlayer mediaPlayer = w;
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    u.cancel();
                    w.pause();
                    C.get(E).curSoundPosition = w.getCurrentPosition();
                    l.notifyItemChanged(E);
                    return;
                }
                w.start();
                b(i);
                l.notifyDataSetChanged();
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer2 = w;
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
            u.cancel();
            w.pause();
            w.release();
        }
        int i2 = D;
        if (i2 != -1) {
            C.get(i2).curSoundPosition = 0;
            l.notifyItemChanged(D);
        }
        E = i;
        D = i;
        l.notifyItemChanged(E);
        w = new MediaPlayer();
        w.setAudioStreamType(3);
        w.setOnPreparedListener(mp -> {
            w.start();
            b(i);
            l.notifyItemChanged(E);
        });
        w.setOnCompletionListener(mp -> {
            u.cancel();
            C.get(E).curSoundPosition = 0;
            l.notifyItemChanged(E);
            E = -1;
        });
        try {
            if (C.get(E).isNew) {
                a2 = C.get(E).resFullName;
            } else {
                a2 = a(C.get(E));
            }
            w.setDataSource(a2);
            w.prepare();
        } catch (Exception e) {
            E = -1;
            l.notifyItemChanged(E);
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
            l.notifyDataSetChanged();
        }
        i();
    }

    public final String a(String str) {
        return A + File.separator + str;
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return A + File.separator + projectResourceBean.resName + substring;
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
        } catch (Exception unused) {
        }
    }
}
