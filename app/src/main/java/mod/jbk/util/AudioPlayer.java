package mod.jbk.util;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mod.SketchwareUtil;

public class AudioPlayer {
    private final FragmentActivity context;
    private final SoundPlayingAdapter<?> adapter;
    private MediaPlayer mediaPlayer;
    private Timer timer = new Timer();
    private int nowPlayingPosition = -1;

    public AudioPlayer(FragmentActivity context, SoundPlayingAdapter<?> adapter) {
        this.context = context;
        this.adapter = adapter;

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
                    adapter.getData(position).curSoundPosition = mediaPlayer.getCurrentPosition();
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
                adapter.getData(position).curSoundPosition = 0;
                adapter.notifyItemChanged(position);
                nowPlayingPosition = -1;
            });

            var audio = adapter.getAudio(position);
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
            adapter.getData(nowPlayingPosition).curSoundPosition = 0;
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
                    var holder = adapter.getViewHolder(position);
                    int positionInS = mediaPlayer.getCurrentPosition() / 1000;
                    holder.getCurrentPosition().setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
                    holder.getPlaybackProgress().setProgress(mediaPlayer.getCurrentPosition() / 100);
                });
            }
        }, 100, 100);
    }
}
