package mod.jbk.util;

import android.media.MediaPlayer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

import mod.SketchwareUtil;

public class AudioPlayer {
    private final FragmentActivity context;
    private final RecyclerView.Adapter<?> adapter;
    private final SoundAdapter<ProjectResourceBean> soundAdapter;
    private MediaPlayer mediaPlayer;
    private Timer timer = new Timer();
    private int nowPlayingPosition = -1;

    public AudioPlayer(FragmentActivity context, RecyclerView.Adapter<?> adapter, SoundAdapter<ProjectResourceBean> soundAdapter) {
        this.context = context;
        this.adapter = adapter;
        this.soundAdapter = soundAdapter;

        this.context.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                stopPlayback();
            }
        });
    }

    public int getNowPlayingPosition() {
        return nowPlayingPosition;
    }

    public void onPlayPressed(int position) {
        if (nowPlayingPosition == position) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    timer.cancel();
                    mediaPlayer.pause();
                    soundAdapter.getData(position).curSoundPosition = mediaPlayer.getCurrentPosition();
                } else {
                    mediaPlayer.start();
                    reschedule(position);
                }
                adapter.notifyItemChanged(position);
            }
        } else {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                timer.cancel();
                mediaPlayer.pause();
                mediaPlayer.release();
                adapter.notifyItemChanged(nowPlayingPosition);
            }

            nowPlayingPosition = position;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(AudioMetadata.MEDIA_PLAYER_AUDIO_ATTRIBUTES);
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                reschedule(position);
                adapter.notifyItemChanged(position);
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                timer.cancel();
                soundAdapter.getData(position).curSoundPosition = 0;
                adapter.notifyItemChanged(position);
                nowPlayingPosition = -1;
            });

            var audio = soundAdapter.getAudio(position);
            try {
                mediaPlayer.setDataSource(audio.toString());
                mediaPlayer.prepare();
            } catch (IOException e) {
                adapter.notifyItemChanged(nowPlayingPosition);
                nowPlayingPosition = -1;

                LogUtil.e("AudioPlayer", "Failed to play Sound " + audio, e);
                SketchwareUtil.toastError("Failed to play Sound: " + e);
            }
        }
    }

    public void stopPlayback() {
        timer.cancel();
        if (nowPlayingPosition != -1) {
            soundAdapter.getData(nowPlayingPosition).curSoundPosition = 0;
            adapter.notifyItemChanged(nowPlayingPosition);
            nowPlayingPosition = -1;
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private void reschedule(int position) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(() -> {
                    if (mediaPlayer == null) {
                        timer.cancel();
                    } else {
                        int positionInS = mediaPlayer.getCurrentPosition() / 1000;
                        soundAdapter.getCurrentPosition(position).setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
                        soundAdapter.getPlaybackProgress(position).setProgress(mediaPlayer.getCurrentPosition() / 100);
                    }
                });
            }
        }, 100, 100);
    }

    public interface SoundAdapter<T> {
        T getData(int position);

        Path getAudio(int position);

        TextView getCurrentPosition(int position);

        ProgressBar getPlaybackProgress(int position);
    }
}
