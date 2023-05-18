package a.a.a;

import android.content.Intent;
import android.media.AudioManager;
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
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Yv extends qA implements View.OnClickListener {
    private RecyclerView soundsList;
    private String sc_id;
    private ArrayList<ProjectResourceBean> sounds;
    private TextView noSoundsText;
    private Button importSounds;
    private MediaPlayer mediaPlayer;
    private String h = "";
    private SoundAdapter adapter = null;
    private Timer timer = new Timer();
    private int q = -1;
    private int r = -1;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
            unselectAll();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup2 = (ViewGroup) inflater.inflate(R.layout.fr_manage_sound_list, container, false);
        soundsList = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        soundsList.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        adapter = new SoundAdapter();
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

    private class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
        private final Map<ProjectResourceBean, ManageSoundActivity.AudioMetadata> cachedMetadata = new HashMap<>();

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final CheckBox select;
            public final ImageView album;
            public final TextView name;
            public final ImageView play;
            public final TextView currentTime;
            public final ProgressBar progress;
            public final TextView endTime;

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
                        play(getLayoutPosition());
                    }
                });
                select.setOnClickListener(v -> {
                    int position = getLayoutPosition();
                    sounds.get(position).isSelected = select.isChecked();
                    updateImportSoundsText();
                    notifyItemChanged(position);
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean bean = sounds.get(position);
            String path = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            holder.select.setVisibility(View.VISIBLE);

            var audioMetadata = cachedMetadata.get(bean);
            if (audioMetadata != null) {
                audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.album);
            } else {
                audioMetadata = ManageSoundActivity.AudioMetadata.getFromPath(path);
                cachedMetadata.put(bean, audioMetadata);
                audioMetadata.setEmbeddedPictureAsAlbumCover(requireActivity(), holder.album);
                if (bean.totalSoundDuration == 0) {
                    bean.totalSoundDuration = audioMetadata.getDurationInMs();
                }
            }

            int positionInS = bean.curSoundPosition / 1000;
            String text = String.format("%d:%02d", positionInS / 60, positionInS % 60);
            holder.currentTime.setText(text);
            holder.endTime.setText(text);
            holder.select.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            if (r == position) {
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

    private void b(int position) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requireActivity().runOnUiThread(() -> {
                    if (mediaPlayer == null) {
                        timer.cancel();
                    } else {
                        SoundAdapter.ViewHolder viewHolder = (SoundAdapter.ViewHolder) soundsList.findViewHolderForLayoutPosition(position);
                        int seconds = mediaPlayer.getCurrentPosition() / 1000;
                        viewHolder.currentTime.setText(String.format("%d:%02d", seconds / 60, seconds % 60));
                        viewHolder.progress.setProgress(mediaPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        }, 100L, 100L);
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

    private void play(int position) {
        if (r == position) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    timer.cancel();
                    mediaPlayer.pause();
                    sounds.get(r).curSoundPosition = mediaPlayer.getCurrentPosition();
                    adapter.notifyItemChanged(r);
                } else {
                    mediaPlayer.start();
                    b(position);
                    adapter.notifyDataSetChanged();
                }
            }
        } else {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                timer.cancel();
                mediaPlayer.pause();
                mediaPlayer.release();
            }
            if (q != -1) {
                sounds.get(q).curSoundPosition = 0;
                adapter.notifyItemChanged(q);
            }
            r = position;
            q = position;
            adapter.notifyItemChanged(r);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                b(position);
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
    }
}
