package mod.jbk.util;

import android.media.MediaPlayer;
import android.view.View;
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

public abstract class SoundPlayingAdapter<VH extends SoundPlayingAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final SoundPlayer soundPlayer;
    private RecyclerView recyclerView;

    public SoundPlayingAdapter(FragmentActivity activity) {
        super();
        soundPlayer = new SoundPlayer(activity, this);
    }

    protected abstract ProjectResourceBean getData(int position);

    protected abstract Path getAudio(int position);

    protected VH getViewHolder(int position) {
        //noinspection unchecked
        return (VH) recyclerView.findViewHolderForLayoutPosition(position);
    }

    public void stopPlayback() {
        soundPlayer.stopPlayback();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (recyclerView.equals(this.recyclerView)) {
            this.recyclerView = null;
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        if (soundPlayer.isNowPlayingItemOffscreen) {
            soundPlayer.isNowPlayingItemOffscreen = false;
            soundPlayer.reschedule(holder.getLayoutPosition());
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected abstract TextView getCurrentPosition();

        protected abstract ProgressBar getPlaybackProgress();
    }

    public static class SoundlessViewHolder extends ViewHolder {
        public SoundlessViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected TextView getCurrentPosition() {
            return null;
        }

        @Override
        protected ProgressBar getPlaybackProgress() {
            return null;
        }
    }

    public static class SoundPlayer {
        private final FragmentActivity context;
        private final SoundPlayingAdapter<?> adapter;
        private MediaPlayer mediaPlayer;
        private Timer timer = new Timer();
        private int nowPlayingPosition = -1;
        private boolean isNowPlayingItemOffscreen;

        public SoundPlayer(FragmentActivity context, SoundPlayingAdapter<?> adapter) {
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

                    LogUtil.e("SoundPlayer", "Failed to play Sound " + audio, e);
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
                        if (holder != null) {
                            int positionInS = mediaPlayer.getCurrentPosition() / 1000;
                            holder.getCurrentPosition().setText(String.format("%d:%02d", positionInS / 60, positionInS % 60));
                            holder.getPlaybackProgress().setProgress(mediaPlayer.getCurrentPosition() / 100);
                        } else {
                            timer.cancel();
                            isNowPlayingItemOffscreen = true;
                        }
                    });
                }
            }, 100, 100);
        }
    }
}
