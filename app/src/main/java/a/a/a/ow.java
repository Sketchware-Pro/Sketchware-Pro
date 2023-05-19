package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.AddSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import mod.jbk.util.AudioMetadata;

public class ow extends qA implements View.OnClickListener {
    private oB fileUtil;
    private ArrayList<ProjectResourceBean> sounds;
    private FloatingActionButton add;
    private String sc_id;
    private RecyclerView soundsList;
    private LinearLayout actionContainer;
    private MediaPlayer mediaPlayer;
    private TextView noSoundsText;
    public boolean k = false;
    private a adapter = null;
    private Timer timer = new Timer();
    private String A = "";
    private int D = -1;
    private int E = -1;

    private void i() {
        if (sounds.size() == 0) {
            noSoundsText.setVisibility(View.VISIBLE);
            soundsList.setVisibility(View.GONE);
        } else {
            soundsList.setVisibility(View.VISIBLE);
            noSoundsText.setVisibility(View.GONE);
        }
    }

    private void j() {
        f();
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", A);
        intent.putExtra("sound_names", c());
        startActivityForResult(intent, 269);
    }

    private void k() {
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", A);
        intent.putExtra("sound_names", c());
        intent.putExtra("request_code", 270);
        intent.putExtra("project_resource", sounds.get(adapter.lastSelectedSound));
        startActivityForResult(intent, 270);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileUtil = new oB();
        fileUtil.f(A);
        sounds = new ArrayList<>();
        if (savedInstanceState == null) {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
            A = jC.d(sc_id).o();
            ArrayList<ProjectResourceBean> arrayList = jC.d(sc_id).c;
            if (arrayList != null) {
                for (ProjectResourceBean projectResourceBean : arrayList) {
                    sounds.add(projectResourceBean.clone());
                }
            }
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            A = savedInstanceState.getString("dir_path");
            sounds = savedInstanceState.getParcelableArrayList("sounds");
        }
        adapter.notifyDataSetChanged();
        i();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 269) {
            if (resultCode == Activity.RESULT_OK) {
                sounds.add((ProjectResourceBean) data.getParcelableExtra("project_resource"));
                bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_add_complete), 1).show();
                adapter.notifyDataSetChanged();
                i();
                ((ManageSoundActivity) requireActivity()).l().e();
            }
        } else if (requestCode == 270 && resultCode == Activity.RESULT_OK) {
            sounds.set(adapter.lastSelectedSound, (ProjectResourceBean) data.getParcelableExtra("project_resource"));
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_edit_complete), 1).show();
            adapter.notifyDataSetChanged();
            i();
            ((ManageSoundActivity) requireActivity()).l().e();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && k) {
            int id = v.getId();
            if (id == R.id.btn_cancel) {
                a(false);
            } else if (id == R.id.btn_delete) {
                int size = sounds.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        adapter.notifyDataSetChanged();
                        E = -1;
                        D = -1;
                        a(false);
                        i();
                        bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.common_message_complete_delete), 1).show();
                        add.show();
                        break;
                    } else {
                        ProjectResourceBean projectResourceBean = sounds.get(size);
                        projectResourceBean.curSoundPosition = 0;
                        if (projectResourceBean.isSelected) {
                            sounds.remove(size);
                        }
                    }
                }
            }
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
        ViewGroup item = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        setHasOptionsMenu(true);
        actionContainer = item.findViewById(R.id.layout_btn_group);
        Button delete = item.findViewById(R.id.btn_delete);
        Button cancel = item.findViewById(R.id.btn_cancel);
        add = item.findViewById(R.id.fab);
        delete.setText(xB.b().a(requireActivity(), R.string.common_word_delete));
        cancel.setText(xB.b().a(requireActivity(), R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        soundsList = item.findViewById(R.id.sound_list);
        soundsList.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new a(soundsList);
        soundsList.setAdapter(adapter);
        noSoundsText = item.findViewById(R.id.tv_guide);
        noSoundsText.setText(xB.b().a(requireActivity(), R.string.design_manager_sound_description_guide_add_sound));
        noSoundsText.setOnClickListener(v -> {
            if (!mB.a()) {
                a(false);
                j();
            }
        });
        add.setOnClickListener(v -> {
            if (!mB.a()) {
                a(false);
                j();
            }
        });
        return item;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_sound_add) {
            a(false);
            j();
        } else if (itemId == R.id.menu_sound_delete) {
            a(!k);
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
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", A);
        outState.putParcelableArrayList("sounds", sounds);
        super.onSaveInstanceState(outState);
    }

    private class a extends RecyclerView.Adapter<a.ViewHolder> {
        private int lastSelectedSound = -1;
        private final Map<ProjectResourceBean, AudioMetadata> cachedAudioMetadata = new HashMap<>();

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final ProgressBar progress;
            public final TextView endTime;
            public final LinearLayout deleteContainer;
            public final CardView root;
            public final CheckBox selected;
            public final ImageView album;
            public final TextView name;
            public final ImageView play;
            public final ImageView delete;
            public final TextView currentTime;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                root = itemView.findViewById(R.id.layout_item);
                selected = itemView.findViewById(R.id.chk_select);
                album = itemView.findViewById(R.id.img_album);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_sound_name);
                play = itemView.findViewById(R.id.img_play);
                currentTime = itemView.findViewById(R.id.tv_currenttime);
                progress = itemView.findViewById(R.id.prog_playtime);
                endTime = itemView.findViewById(R.id.tv_endtime);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                play.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                        if (!k) {
                            ow.this.a(lastSelectedSound);
                        }
                    }
                });
                selected.setVisibility(View.GONE);
                root.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                    }
                    if (k) {
                        selected.setChecked(!selected.isChecked());
                        sounds.get(lastSelectedSound).isSelected = selected.isChecked();
                        notifyItemChanged(lastSelectedSound);
                    } else {
                        f();
                        k();
                    }
                });
                root.setOnLongClickListener(v -> {
                    ow.this.a(true);
                    lastSelectedSound = getLayoutPosition();
                    selected.setChecked(!selected.isChecked());
                    sounds.get(lastSelectedSound).isSelected = selected.isChecked();
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
                            if (add.isEnabled()) {
                                add.hide();
                            }
                        } else if (dy < -2) {
                            if (add.isEnabled()) {
                                add.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);
            if (!k) {
                AudioMetadata audioMetadata = cachedAudioMetadata.get(bean);
                if (audioMetadata == null) {
                    audioMetadata = AudioMetadata.fromPath(bean.isNew ? bean.resFullName : a(bean));
                    cachedAudioMetadata.put(bean, audioMetadata);
                    bean.totalSoundDuration = audioMetadata.getDurationInMs();
                }
                audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.album);
                holder.album.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            } else {
                holder.album.setVisibility(View.GONE);
                holder.deleteContainer.setVisibility(View.VISIBLE);
            }
            holder.delete.setImageResource(bean.isSelected ? R.drawable.ic_checkmark_green_48dp : R.drawable.ic_trashcan_white_48dp);

            int positionInS = bean.curSoundPosition / 1000;
            int totalDurationInS = bean.totalSoundDuration / 1000;
            holder.currentTime.setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
            holder.endTime.setText(String.format("%d:%02d", totalDurationInS / 60, totalDurationInS % 60));

            holder.selected.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            if (E == position) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    holder.play.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.play.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.play.setImageResource(R.drawable.circled_play_96_blue);
            }
            holder.progress.setMax(bean.totalSoundDuration / 100);
            holder.progress.setProgress(bean.curSoundPosition / 100);
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
    }

    public ArrayList<ProjectResourceBean> d() {
        return sounds;
    }

    public void f() {
        timer.cancel();
        if (E != -1) {
            sounds.get(E).curSoundPosition = 0;
            E = -1;
            D = -1;
            adapter.notifyDataSetChanged();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void unselectAll() {
        for (ProjectResourceBean projectResourceBean : sounds) {
            projectResourceBean.isSelected = false;
        }
    }

    public void h() {
        if (sounds != null && sounds.size() > 0) {
            for (ProjectResourceBean next : sounds) {
                if (next.isNew) {
                    fileUtil.c(a(next.resFullName));
                }
            }
        }
        for (ProjectResourceBean next2 : sounds) {
            if (next2.isNew) {
                try {
                    String a2 = a(next2);
                    if (fileUtil.e(a2)) {
                        fileUtil.c(a2);
                    }
                    a(next2.resFullName, a2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < sounds.size(); i++) {
            ProjectResourceBean projectResourceBean = sounds.get(i);
            if (projectResourceBean.isNew) {
                String str = projectResourceBean.resFullName;
                String substring = str.substring(str.lastIndexOf("."));
                sounds.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.resName + substring));
            }
        }
        jC.d(sc_id).c(sounds);
        jC.d(sc_id).y();
        jC.a(sc_id).c(jC.d(sc_id));
        jC.a(sc_id).k();
    }

    private void b(ArrayList<ProjectResourceBean> arrayList) {
        sounds.addAll(arrayList);
    }

    private boolean c(String str) {
        for (ProjectResourceBean projectResourceBean : sounds) {
            if (projectResourceBean.resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void a(boolean z) {
        k = z;
        requireActivity().invalidateOptionsMenu();
        unselectAll();
        if (k) {
            f();
            actionContainer.setVisibility(View.VISIBLE);
        } else {
            actionContainer.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
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
                        ow.a.ViewHolder holder = (ow.a.ViewHolder) soundsList.findViewHolderForLayoutPosition(i);
                        int positionInS = mediaPlayer.getCurrentPosition() / 1000;
                        holder.currentTime.setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
                        holder.progress.setProgress(mediaPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        }, 100L, 100L);
    }

    private ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : sounds) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    private void a(int i) {
        if (E == i) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    timer.cancel();
                    mediaPlayer.pause();
                    sounds.get(E).curSoundPosition = mediaPlayer.getCurrentPosition();
                    adapter.notifyItemChanged(E);
                } else {
                    mediaPlayer.start();
                    b(i);
                    adapter.notifyDataSetChanged();
                }
            }
        } else {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                timer.cancel();
                mediaPlayer.pause();
                mediaPlayer.release();
            }
            if (D != -1) {
                sounds.get(D).curSoundPosition = 0;
                adapter.notifyItemChanged(D);
            }
            E = i;
            D = i;
            adapter.notifyItemChanged(E);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(3);
            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                b(i);
                adapter.notifyItemChanged(E);
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                timer.cancel();
                sounds.get(E).curSoundPosition = 0;
                adapter.notifyItemChanged(E);
                E = -1;
            });
            try {
                String src;
                if (sounds.get(E).isNew) {
                    src = sounds.get(E).resFullName;
                } else {
                    src = a(sounds.get(E));
                }
                mediaPlayer.setDataSource(src);
                mediaPlayer.prepare();
            } catch (Exception e) {
                E = -1;
                adapter.notifyItemChanged(E);
                e.printStackTrace();
            }
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
            if (c(next.resName)) {
                arrayList3.add(next.resName);
            } else {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName);
                projectResourceBean.isNew = true;
                arrayList2.add(projectResourceBean);
            }
        }
        if (arrayList3.size() > 0) {
            String str2 = "";
            for (String str3 : arrayList3) {
                if (str2.length() > 0) {
                    str2 = str2 + ", ";
                }
                str2 = str2 + str3;
            }
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.common_message_name_unavailable) + "\n[" + str2 + "]", 1).show();
        } else {
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_import_complete), 1).show();
            b(arrayList2);
            adapter.notifyDataSetChanged();
        }
        i();
    }

    private String a(String str) {
        return A + File.separator + str;
    }

    private String a(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return A + File.separator + projectResourceBean.resName + substring;
    }

    private void a(String str, String str2) {
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
