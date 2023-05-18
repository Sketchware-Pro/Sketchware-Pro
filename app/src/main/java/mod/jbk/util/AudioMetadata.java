package mod.jbk.util;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sketchware.remod.R;

import java.io.IOException;

import mod.SketchwareUtil;

public class AudioMetadata {
    public static final AudioAttributes MEDIA_PLAYER_AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();

    private final int durationInMs;
    private final byte[] embeddedPicture;

    public AudioMetadata(int durationInMs, byte[] embeddedPicture) {
        this.durationInMs = durationInMs;
        this.embeddedPicture = embeddedPicture;
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

    public static AudioMetadata fromPath(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(path);
            int durationInMs = (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            return new AudioMetadata(durationInMs, mediaMetadataRetriever.getEmbeddedPicture());
        } catch (IllegalArgumentException unused) {
            return new AudioMetadata(-1, null);
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                SketchwareUtil.toastError("Failed to release file " + path + ": " + e);
            }
        }
    }
}
