package mod.jbk.util;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;

import java.nio.file.Path;

public abstract class SoundPlayingAdapter<VH extends SoundPlayingAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final AudioPlayer audioPlayer;
    private RecyclerView recyclerView;

    public SoundPlayingAdapter(FragmentActivity activity) {
        super();
        audioPlayer = new AudioPlayer(activity, this);
    }

    protected abstract ProjectResourceBean getData(int position);

    protected abstract Path getAudio(int position);

    protected VH getViewHolder(int position) {
        //noinspection unchecked
        return (VH) recyclerView.findViewHolderForLayoutPosition(position);
    }

    public void stopPlayback() {
        audioPlayer.stopPlayback();
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
}
