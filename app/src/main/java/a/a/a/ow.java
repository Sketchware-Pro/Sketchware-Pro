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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.AddSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import mod.hey.studios.util.Helper;
import mod.jbk.util.AudioMetadata;
import mod.jbk.util.SoundPlayingAdapter;
import pro.sketchware.R;
import pro.sketchware.databinding.FrManageSoundListBinding;
import pro.sketchware.databinding.ManageSoundBinding;
import pro.sketchware.databinding.ManageSoundListItemBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ow extends qA {

    public ArrayList<ProjectResourceBean> sounds;
    public boolean isSelecting = false;
    private String sc_id;
    private Adapter adapter;
    private String dirPath = "";

    private FrManageSoundListBinding binding;
    private ManageSoundBinding actBinding;

    private void updateNoSoundsTextVisibility() {
        if (sounds.isEmpty()) {
            binding.tvGuide.setVisibility(View.VISIBLE);
            binding.soundList.setVisibility(View.GONE);
        } else {
            binding.soundList.setVisibility(View.VISIBLE);
            binding.tvGuide.setVisibility(View.GONE);
        }
    }

    private void addSound() {
        stopPlayback();
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", dirPath);
        intent.putExtra("sound_names", getSoundsNames());
        startActivityForResult(intent, 269);
    }

    private void editSound() {
        Intent intent = new Intent(getContext(), AddSoundActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", dirPath);
        intent.putExtra("sound_names", getSoundsNames());
        intent.putExtra("request_code", 270);
        intent.putExtra("project_resource", sounds.get(adapter.lastSelectedSound));
        startActivityForResult(intent, 270);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new oB().f(dirPath);
        sounds = new ArrayList<>();
        if (savedInstanceState == null) {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
            dirPath = jC.d(sc_id).o();
            ArrayList<ProjectResourceBean> arrayList = jC.d(sc_id).c;
            if (arrayList != null) {
                for (ProjectResourceBean projectResourceBean : arrayList) {
                    sounds.add(projectResourceBean.clone());
                }
            }
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            dirPath = savedInstanceState.getString("dir_path");
            sounds = savedInstanceState.getParcelableArrayList("sounds");
        }
        adapter.notifyDataSetChanged();
        updateNoSoundsTextVisibility();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 269 && resultCode == Activity.RESULT_OK) {
            sounds.add(data.getParcelableExtra("project_resource"));
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_add_complete));

            adapter.notifyDataSetChanged();
            updateNoSoundsTextVisibility();
            ((ManageSoundActivity) requireActivity()).collectionSounds.loadProjectSounds();
        } else if (requestCode == 270 && resultCode == Activity.RESULT_OK) {
            sounds.set(adapter.lastSelectedSound, data.getParcelableExtra("project_resource"));
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_edit_complete));
            adapter.notifyDataSetChanged();
            updateNoSoundsTextVisibility();
            ((ManageSoundActivity) requireActivity()).collectionSounds.loadProjectSounds();
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
        menu.findItem(R.id.menu_sound_delete).setVisible(!isSelecting);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        actBinding = ((ManageSoundActivity) requireActivity()).binding;
        binding = FrManageSoundListBinding.inflate(inflater, container, false);

        actBinding.btnDelete.setOnClickListener(view -> {
            if (sounds.isEmpty()) {
                SketchwareUtil.toast(Helper.getResString(R.string.common_message_no_item_delete));
            } else if (!mB.a() && isSelecting) {
                for (int i = sounds.size() - 1; i >= 0; i--) {
                    ProjectResourceBean projectResourceBean = sounds.get(i);
                    projectResourceBean.curSoundPosition = 0;
                    if (projectResourceBean.isSelected) {
                        sounds.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                adapter.stopPlayback();
                SketchwareUtil.toast(Helper.getResString(R.string.common_message_complete_delete));
            }
            setSelecting(false);
            updateNoSoundsTextVisibility();
        });

        actBinding.btnCancel.setOnClickListener(view -> setSelecting(false));
        adapter = new Adapter(binding.soundList);
        binding.soundList.setAdapter(adapter);
        actBinding.fab.setOnClickListener(v -> {
            if (!mB.a()) {
                setSelecting(false);
                addSound();
            }
        });
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_sound_add) {
            setSelecting(false);
            addSound();
        } else if (itemId == R.id.menu_sound_delete) {
            setSelecting(!isSelecting);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", dirPath);
        outState.putParcelableArrayList("sounds", sounds);
        super.onSaveInstanceState(outState);
    }

    public void stopPlayback() {
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
                String temporarySoundPath = getFilePathFromName(sound.resFullName);
                FileUtil.deleteFile(temporarySoundPath);

                String soundPath = getFilePathFromResource(sound);
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

    private boolean isResourceUnavailable(String str) {
        for (ProjectResourceBean projectResourceBean : sounds) {
            if (projectResourceBean.resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void setSelecting(boolean state) {
        isSelecting = state;
        requireActivity().invalidateOptionsMenu();
        unselectAll();
        if (isSelecting) {
            stopPlayback();
            actBinding.fab.hide();
            actBinding.layoutBtnGroup.setVisibility(View.VISIBLE);
        } else {
            actBinding.fab.show();
            actBinding.layoutBtnGroup.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> getSoundsNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : sounds) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    public void handleImportedResources(ArrayList<ProjectResourceBean> resourceList) {
        ArrayList<ProjectResourceBean> newResources = new ArrayList<>();
        ArrayList<String> unavailableResourceNames = new ArrayList<>();

        for (ProjectResourceBean resource : resourceList) {
            if (isResourceUnavailable(resource.resName)) {
                unavailableResourceNames.add(resource.resName);
            } else {
                ProjectResourceBean newResource = new ProjectResourceBean(
                        ProjectResourceBean.PROJECT_RES_TYPE_FILE, resource.resName, resource.resFullName
                );
                newResource.isNew = true;
                newResources.add(newResource);
            }
        }

        if (!unavailableResourceNames.isEmpty()) {
            StringBuilder unavailableNames = new StringBuilder();
            for (String name : unavailableResourceNames) {
                if (unavailableNames.length() > 0) {
                    unavailableNames.append(", ");
                }
                unavailableNames.append(name);
            }
            SketchwareUtil.toast(Helper.getResString(R.string.common_message_name_unavailable) + "\n[" + unavailableNames + "]");
        } else {
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_import_complete));
            sounds.addAll(newResources);
            adapter.notifyDataSetChanged();
        }

        updateNoSoundsTextVisibility();
    }

    private String getFilePathFromName(String str) {
        return dirPath + File.separator + str;
    }

    private String getFilePathFromResource(ProjectResourceBean projectResourceBean) {
        String str = projectResourceBean.resFullName;
        String substring = str.substring(str.lastIndexOf("."));
        return dirPath + File.separator + projectResourceBean.resName + substring;
    }

    private class Adapter extends SoundPlayingAdapter<Adapter.ViewHolder> {
        private final LayoutInflater inflater;
        private int lastSelectedSound = -1;

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
            return Paths.get(bean.isNew ? bean.resFullName : getFilePathFromResource(bean));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);

            if (!isSelecting) {
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
            holder.binding.tvCurrenttime.setText(String.format(Locale.US, "%d:%02d", positionInS / 60, positionInS % 60));
            holder.binding.tvEndtime.setText(String.format(Locale.US, "%d:%02d", totalDurationInS / 60, totalDurationInS % 60));

            holder.binding.chkSelect.setChecked(bean.isSelected);
            holder.binding.tvSoundName.setText(bean.resName);

            boolean playing = position == soundPlayer.getNowPlayingPosition() && soundPlayer.isPlaying();
            holder.binding.imgPlay.setImageResource(playing ? R.drawable.ic_mtrl_circle_pause : R.drawable.ic_mtrl_circle_play);
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

        private class ViewHolder extends SoundPlayingAdapter.ViewHolder {
            private final ManageSoundListItemBinding binding;
            private AudioMetadata audioMetadata;

            public ViewHolder(@NonNull ManageSoundListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.chkSelect.setVisibility(View.GONE);

                binding.imgPlay.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                        if (!isSelecting) {
                            soundPlayer.onPlayPressed(lastSelectedSound);
                        }
                    }
                });

                binding.layoutItem.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedSound = getLayoutPosition();
                    }
                    if (isSelecting) {
                        binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                        sounds.get(lastSelectedSound).isSelected = binding.chkSelect.isChecked();
                        notifyItemChanged(lastSelectedSound);
                    } else {
                        stopPlayback();
                        editSound();
                    }
                });

                binding.layoutItem.setOnLongClickListener(v -> {
                    setSelecting(true);
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
    }
}
