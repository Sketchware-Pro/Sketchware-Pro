package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundImportActivity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import mod.jbk.util.AudioMetadata;
import mod.jbk.util.SoundPlayingAdapter;
import pro.sketchware.R;
import pro.sketchware.databinding.FrManageSoundListBinding;
import pro.sketchware.databinding.ManageSoundBinding;
import pro.sketchware.databinding.ManageSoundListItemBinding;

public class Yv extends qA {
    private String sc_id;
    private ArrayList<ProjectResourceBean> sounds;
    private String dirPath = "";
    private Adapter adapter;

    private FrManageSoundListBinding binding;
    private ManageSoundBinding actBinding;

    private ActivityResultLauncher<Intent> importSoundsHandler;

    private void updateImportSoundsText() {
        int selectedSounds = (int) sounds.stream().filter(projectResourceBean -> projectResourceBean.isSelected).count();
        if (selectedSounds > 0) {
            actBinding.btnImport.setText(xB.b().a(getContext(), R.string.common_word_import_count, selectedSounds));
            actBinding.layoutBtnImport.setVisibility(View.VISIBLE);
        } else {
            actBinding.layoutBtnImport.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        oB fileUtil = new oB();
        // create dirs if they don't exist
        fileUtil.f(dirPath);
        if (savedInstanceState == null) {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
            dirPath = requireActivity().getIntent().getStringExtra("dir_path");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            dirPath = savedInstanceState.getString("dir_path");
        }
        loadProjectSounds();

        importSoundsHandler = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                handleImportedSounds(result.getData().getParcelableArrayListExtra("results"));
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        actBinding = ((ManageSoundActivity) requireActivity()).binding;
        binding = FrManageSoundListBinding.inflate(inflater, container, false);

        binding.soundList.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter();
        binding.soundList.setAdapter(adapter);
        binding.tvGuide.setText(xB.b().a(requireActivity(), R.string.design_manager_sound_description_guide_add_sound));
        actBinding.btnImport.setOnClickListener(view -> {
            if (!mB.a()) {
                stopPlayback();
                ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
                for (ProjectResourceBean next : sounds) {
                    if (next.isSelected) {
                        arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + next.resFullName));
                    }
                }
                if (!arrayList.isEmpty()) {
                    ArrayList<ProjectResourceBean> d = ((ManageSoundActivity) requireActivity()).projectSounds.sounds;
                    Intent intent = new Intent(requireActivity(), ManageSoundImportActivity.class);
                    intent.putParcelableArrayListExtra("project_sounds", d);
                    intent.putParcelableArrayListExtra("selected_collections", arrayList);
                    importSoundsHandler.launch(intent);
                }
                resetSelection();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", dirPath);
    }

    public void stopPlayback() {
        adapter.stopPlayback();
    }

    public void loadProjectSounds() {
        sounds = Qp.g().f();
        adapter.notifyDataSetChanged();
        showOrHideNoSoundsText();
    }

    public void resetSelection() {
        sounds.forEach(projectResourceBean -> projectResourceBean.isSelected = false);
        adapter.notifyDataSetChanged();
        actBinding.layoutBtnImport.setVisibility(View.GONE);
    }

    public boolean isSelecting() {
        return sounds.stream().anyMatch(resource -> resource.isSelected);
    }

    private void showOrHideNoSoundsText() {
        if (sounds.isEmpty()) {
            binding.tvGuide.setVisibility(View.VISIBLE);
            binding.soundList.setVisibility(View.GONE);
        } else {
            binding.soundList.setVisibility(View.VISIBLE);
            binding.tvGuide.setVisibility(View.GONE);
        }
    }

    private void handleImportedSounds(ArrayList<ProjectResourceBean> resourceBeans) {
        ArrayList<ProjectResourceBean> beans = new ArrayList<>();
        for (ProjectResourceBean next : resourceBeans) {
            beans.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName));
        }
        if (!beans.isEmpty()) {
            ((ManageSoundActivity) requireActivity()).projectSounds.handleImportedResources(beans);
            ((ManageSoundActivity) requireActivity()).binding.viewPager.setCurrentItem(0);
        }
    }

    private class Adapter extends SoundPlayingAdapter<Adapter.ViewHolder> {
        private final LayoutInflater inflater;

        public Adapter() {
            super(requireActivity());
            this.inflater = LayoutInflater.from(requireActivity());
        }

        @Override
        public ProjectResourceBean getData(int position) {
            return sounds.get(position);
        }

        @Override
        public Path getAudio(int position) {
            return Paths.get(wq.a(), "sound", "data", sounds.get(position).resFullName);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);
            holder.binding.chkSelect.setVisibility(View.VISIBLE);

            var audioMetadata = holder.audioMetadata;
            var audio = getAudio(position);
            if (audioMetadata == null || !audioMetadata.getSource().equals(audio)) {
                audioMetadata = holder.audioMetadata = AudioMetadata.fromPath(audio);
                bean.totalSoundDuration = audioMetadata.getDurationInMs();
                audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.binding.imgAlbum);
            }

            int positionInS = bean.curSoundPosition / 1000;
            holder.binding.tvCurrenttime.setText(String.format(Locale.US, "%d:%02d", positionInS / 60, positionInS % 60));
            int durationInS = bean.totalSoundDuration / 1000;
            holder.binding.tvEndtime.setText(String.format(Locale.US, "%d:%02d", durationInS / 60, durationInS % 60));
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

                binding.imgPlay.setOnClickListener(v -> {
                    if (!mB.a()) {
                        soundPlayer.onPlayPressed(getLayoutPosition());
                    }
                });

                binding.getRoot().setOnClickListener(v -> binding.chkSelect.setChecked(!binding.chkSelect.isChecked()));

                binding.chkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    int position = getLayoutPosition();
                    sounds.get(position).isSelected = isChecked;
                    updateImportSoundsText();
                    new Handler().post(() -> notifyItemChanged(position));
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
