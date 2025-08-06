package mod.jbk.util;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.nio.file.Path;

import pro.sketchware.R;
import pro.sketchware.utility.SketchwareUtil;

public class AudioMetadata {
    public static final AudioAttributes MEDIA_PLAYER_AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();

    private final Path source;
    private final int durationInMs;
    private final byte[] embeddedPicture;

    public AudioMetadata(Path source, int durationInMs, byte[] embeddedPicture) {
        this.source = source;
        this.durationInMs = durationInMs;
        this.embeddedPicture = embeddedPicture;
    }

    public static AudioMetadata fromPath(Path audio) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(audio.toString());
            int durationInMs = (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            return new AudioMetadata(audio, durationInMs, mediaMetadataRetriever.getEmbeddedPicture());
        } catch (IllegalArgumentException unused) {
            return new AudioMetadata(audio, -1, null);
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                SketchwareUtil.toastError("Failed to release file " + audio + ": " + e);
            }
        }
    }

    public int getDurationInMs() {
        return durationInMs;
    }

    public boolean hasEmbeddedPicture() {
        return embeddedPicture != null;
    }

    public byte[] getEmbeddedPicture() {
        return embeddedPicture;
    }

    public Path getSource() {
        return source;
    }

    public void setEmbeddedPictureAsAlbumCover(Activity activity, ImageView imageView) {
        if (hasEmbeddedPicture()) {
            Glide.with(activity)
                    .load(getEmbeddedPicture())
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.default_album_art_200dp);
            imageView.setBackgroundResource(R.drawable.bg_outline_album);
        }
    }
}
