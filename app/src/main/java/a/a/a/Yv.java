package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundImportActivity;
import com.sketchware.remod.R;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import mod.jbk.util.AudioMetadata;
import mod.jbk.util.SoundPlayingAdapter;

public class Yv extends qA implements View.OnClickListener {
    private RecyclerView soundsList;
    private String sc_id;
    private ArrayList<ProjectResourceBean> sounds;
    private TextView noSoundsText;
    private Button importSounds;
    private String h = "";
    private Adapter adapter = null;

    private ActivityResultLauncher<Intent> importSoundsHandler;

    private void updateImportSoundsText() {
        int selectedSounds = 0;
        for (ProjectResourceBean projectResourceBean : sounds) {
            if (projectResourceBean.isSelected) {
                selectedSounds++;
            }
        }
        if (selectedSounds > 0) {
            importSounds.setText(xB.b().a(getContext(), R.string.common_word_import_count, selectedSounds).toUpperCase());
            importSounds.setVisibility(View.VISIBLE);
        } else {
            importSounds.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        oB fileUtil = new oB();
        // create dirs if they don't exist
        fileUtil.f(h);
        if (savedInstanceState == null) {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
            h = requireActivity().getIntent().getStringExtra("dir_path");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            h = savedInstanceState.getString("dir_path");
        }
        e();

        importSoundsHandler = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                a(result.getData().getParcelableArrayListExtra("results"));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.btn_import) {
            importSounds.setVisibility(View.GONE);
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
                importSoundsHandler.launch(intent);
            }
            unselectAll();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup2 = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        soundsList = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        soundsList.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter();
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", h);
    }

    private class Adapter extends SoundPlayingAdapter<Adapter.ViewHolder> {
        public Adapter() {
            super(requireActivity());
        }

        @Override
        public ProjectResourceBean getData(int position) {
            return sounds.get(position);
        }

        @Override
        public Path getAudio(int position) {
            return Paths.get(wq.a(), "sound", "data", sounds.get(position).resFullName);
        }

        private class ViewHolder extends SoundPlayingAdapter.ViewHolder {
            public final CheckBox select;
            public final ImageView album;
            public final TextView name;
            public final ImageView play;
            public final TextView currentTime;
            public final ProgressBar progress;
            public final TextView endTime;

            private AudioMetadata audioMetadata;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                select = itemView.findViewById(R.id.chk_select);
                album = itemView.findViewById(R.id.img_album);
                name = itemView.findViewById(R.id.tv_sound_name);
                play = itemView.findViewById(R.id.img_play);
                currentTime = itemView.findViewById(R.id.tv_currenttime);
                progress = itemView.findViewById(R.id.prog_playtime);
                endTime = itemView.findViewById(R.id.tv_endtime);
                play.setOnClickListener(v -> {
                    if (!mB.a()) {
                        soundPlayer.onPlayPressed(getLayoutPosition());
                    }
                });
                select.setOnClickListener(v -> {
                    int position = getLayoutPosition();
                    sounds.get(position).isSelected = select.isChecked();
                    updateImportSoundsText();
                    notifyItemChanged(position);
                });
            }

            @Override
            protected TextView getCurrentPosition() {
                return currentTime;
            }

            @Override
            protected ProgressBar getPlaybackProgress() {
                return progress;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);
            holder.select.setVisibility(View.VISIBLE);

            var audioMetadata = holder.audioMetadata;
            var audio = getAudio(position);
            if (audioMetadata == null || !audioMetadata.getSource().equals(audio)) {
                audioMetadata = holder.audioMetadata = AudioMetadata.fromPath(audio);
                bean.totalSoundDuration = audioMetadata.getDurationInMs();
                audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.album);
            }

            int positionInS = bean.curSoundPosition / 1000;
            holder.currentTime.setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
            int durationInS = bean.totalSoundDuration / 1000;
            holder.endTime.setText(String.format("%d:%02d", durationInS / 60, durationInS % 60));
            holder.select.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            boolean playing = position == soundPlayer.getNowPlayingPosition() && soundPlayer.isPlaying();
            holder.play.setImageResource(playing ? R.drawable.ic_pause_blue_circle_48dp : R.drawable.circled_play_96_blue);
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

    public void d() {
        adapter.stopPlayback();
    }

    public void e() {
        sounds = Qp.g().f();
        adapter.notifyDataSetChanged();
        showOrHideNoSoundsText();
    }

    private void unselectAll() {
        for (ProjectResourceBean projectResourceBean : sounds) {
            projectResourceBean.isSelected = false;
        }
    }

    private void showOrHideNoSoundsText() {
        if (sounds.size() == 0) {
            noSoundsText.setVisibility(View.VISIBLE);
            soundsList.setVisibility(View.GONE);
        } else {
            soundsList.setVisibility(View.VISIBLE);
            noSoundsText.setVisibility(View.GONE);
        }
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
}
