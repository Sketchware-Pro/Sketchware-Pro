package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.besome.sketch.editor.manage.font.ManageFontActivity;
import com.besome.sketch.editor.manage.sound.AddSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import pro.sketchware.R;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import pro.sketchware.databinding.FrManageSoundListBinding;
import pro.sketchware.databinding.ManageSoundBinding;
import pro.sketchware.databinding.ManageSoundListItemBinding;
import pro.sketchware.utility.FileUtil;
import mod.jbk.util.AudioMetadata;
import mod.jbk.util.SoundPlayingAdapter;

public class ow extends qA implements View.OnClickListener {
    private oB fileUtil;
    private ArrayList<ProjectResourceBean> sounds;
    private String sc_id;
    public boolean k = false;
    private Adapter adapter = null;
    private String A = "";

    private FrManageSoundListBinding binding;
    private ManageSoundBinding actBinding;

    private void i() {
        if (sounds.isEmpty()) {
            binding.tvGuide.setVisibility(View.VISIBLE);
            binding.soundList.setVisibility(View.GONE);
        } else {
            binding.soundList.setVisibility(View.VISIBLE);
            binding.tvGuide.setVisibility(View.GONE);
        }
    }

    private void addSound() {
        f();
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", A);
        intent.putExtra("sound_names", c());
        startActivityForResult(intent, 269);
    }

    private void editSound() {
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
                        adapter.stopPlayback();
                        a(false);
                        i();
                        bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.common_message_complete_delete), 1).show();
                        actBinding.fab.show();
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
        setHasOptionsMenu(true);

        actBinding = ((ManageSoundActivity) requireActivity()).binding;
        binding = FrManageSoundListBinding.inflate(inflater, container, false);

        actBinding.btnDelete.setOnClickListener(this);
        actBinding.btnCancel.setOnClickListener(this);
        binding.soundList.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter(binding.soundList);
        binding.soundList.setAdapter(adapter);
        actBinding.fab.setOnClickListener(v -> {
            if (!mB.a()) {
                a(false);
                addSound();
            }
        });
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_sound_add) {
            a(false);
            addSound();
        } else if (itemId == R.id.menu_sound_delete) {
            a(!k);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", A);
        outState.putParcelableArrayList("sounds", sounds);
        super.onSaveInstanceState(outState);
    }

    private class Adapter extends SoundPlayingAdapter<Adapter.ViewHolder> {
        private int lastSelectedSound = -1;
        private final LayoutInflater inflater;

        public Adapter(RecyclerView recyclerView) {
            super(requireActivity());
            this.inflater = LayoutInflater.from(requireActivity());
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2 && actBinding.fab.isEnabled()) {
                            actBinding.fab.hide();
                        } else if (dy < -2 && actBinding.fab.isEnabled()) {
                            actBinding.fab.show();
                        }
                    }
                });
            }
        }

        @Override
        public ProjectResourceBean getData(int position) {
            return sounds.get(position);
        }

        @Override
        public Path getAudio(int position) {
            var bean = sounds.get(position);
            return Paths.get(bean.isNew ? bean.resFullName : a(bean));
        }

        private class ViewHolder extends SoundPlayingAdapter.ViewHolder {
            private final ManageSoundListItemBinding binding;
            private AudioMetadata audioMetadata;

            public ViewHolder(@NonNull ManageSoundListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.imgPlay.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                        if (!k) {
                            soundPlayer.onPlayPressed(lastSelectedSound);
                        }
                    }
                });

                binding.layoutItem.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                    }
                    if (k) {
                        binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                        sounds.get(lastSelectedSound).isSelected = binding.chkSelect.isChecked();
                        notifyItemChanged(lastSelectedSound);
                    } else {
                        f();
                        editSound();
                    }
                });

                binding.layoutItem.setOnLongClickListener(v -> {
                    ow.this.a(true);
                    lastSelectedSound = getLayoutPosition();
                    binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                    sounds.get(lastSelectedSound).isSelected = binding.chkSelect.isChecked();
                    return true;
                });
            }

            @Override
            protected TextView getCurrentPosition() {
                return binding.tvCurrenttime;
            }

            @Override
            protected ProgressBar getPlaybackProgress() {
                return binding.progPlaytime;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);

            if (!k) {
                var audioMetadata = holder.audioMetadata;
                var audio = getAudio(position);
                if (audioMetadata == null || !audioMetadata.getSource().equals(audio)) {
                    audioMetadata = holder.audioMetadata = AudioMetadata.fromPath(audio);
                    bean.totalSoundDuration = audioMetadata.getDurationInMs();
                    audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.binding.imgAlbum);
                }
                holder.binding.imgAlbum.setVisibility(View.VISIBLE);
                holder.binding.deleteImgContainer.setVisibility(View.GONE);
            } else {
                holder.binding.imgAlbum.setVisibility(View.GONE);
                holder.binding.deleteImgContainer.setVisibility(View.VISIBLE);
            }

            holder.binding.imgDelete.setImageResource(bean.isSelected ? R.drawable.ic_checkmark_green_48dp : R.drawable.ic_trashcan_white_48dp);

            int positionInS = bean.curSoundPosition / 1000;
            int totalDurationInS = bean.totalSoundDuration / 1000;
            holder.binding.tvCurrenttime.setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
            holder.binding.tvEndtime.setText(String.format("%d:%02d", totalDurationInS / 60, totalDurationInS % 60));

            holder.binding.chkSelect.setChecked(bean.isSelected);
            holder.binding.tvSoundName.setText(bean.resName);

            boolean playing = position == soundPlayer.getNowPlayingPosition() && soundPlayer.isPlaying();
            holder.binding.imgPlay.setImageResource(playing ? R.drawable.ic_pause_blue_circle_48dp : R.drawable.circled_play_96_blue);
            holder.binding.progPlaytime.setMax(bean.totalSoundDuration / 100);
            holder.binding.progPlaytime.setProgress(bean.curSoundPosition / 100);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageSoundListItemBinding binding = ManageSoundListItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
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
        adapter.stopPlayback();
    }

    private void unselectAll() {
        for (ProjectResourceBean projectResourceBean : sounds) {
            projectResourceBean.isSelected = false;
        }
    }

    public void saveSounds() {
        for (ProjectResourceBean sound : sounds) {
            if (sound.isNew) {
                String temporarySoundPath = a(sound.resFullName);
                FileUtil.deleteFile(temporarySoundPath);

                String soundPath = a(sound);
                FileUtil.deleteFile(soundPath);
                FileUtil.copyFile(sound.resFullName, soundPath);
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
            actBinding.layoutBtnGroup.setVisibility(View.VISIBLE);
        } else {
            actBinding.layoutBtnGroup.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : sounds) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
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
        if (!arrayList3.isEmpty()) {
            String str2 = "";
            for (String str3 : arrayList3) {
                if (!str2.isEmpty()) {
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
}
