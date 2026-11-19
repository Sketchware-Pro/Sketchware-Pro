package com.besome.sketch.editor.manage.common.preview;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.mB;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageSoundPreviewBinding;

/**
 * Reusable controller that manages audio playback, SeekBar synchronization,
 * and embedded album art extraction for both AddResourceActivity and ManageCollectionImportActivity.
 */
public class SoundPreviewController {

    private final Activity activity;
    private final ManageSoundPreviewBinding binding;
    private final boolean isImportMode;

    private MediaPlayer mediaPlayer;
    private boolean mediaPlayerIsPrepared = false;
    private Timer timer;
    private TimerTask progressUpdater;

    public SoundPreviewController(Activity activity, LayoutInflater inflater, ViewGroup container, boolean isImportMode) {
        this.activity = activity;
        this.isImportMode = isImportMode;
        this.binding = ManageSoundPreviewBinding.inflate(inflater, container, true);

        if (isImportMode) {
            binding.layoutGuide.setVisibility(View.GONE);
            binding.layoutControl.setVisibility(View.VISIBLE);
            binding.tvFileName.setVisibility(View.GONE);
        } else {
            binding.layoutGuide.setVisibility(View.VISIBLE);
            binding.layoutControl.setVisibility(View.GONE);
        }

        setupControls();
    }

    private void setupControls() {
        binding.imgPlay.setOnClickListener(v -> {
            if (!mB.a()) {
                togglePlayback();
            }
        });

        binding.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int seconds = progress / 10;
                    binding.tvCurrenttime.setText(
                            String.format(Locale.ROOT, "%d:%02d", seconds / 60, seconds % 60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopProgressUpdater();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayerIsPrepared) {
                    mediaPlayer.seekTo(seekBar.getProgress() * 100);
                    int seconds = seekBar.getProgress() / 10;
                    binding.tvCurrenttime.setText(
                            String.format(Locale.ROOT, "%d:%02d", seconds / 60, seconds % 60));
                    if (mediaPlayer.isPlaying()) {
                        startProgressUpdater();
                    }
                } else {
                    seekBar.setProgress(0);
                    binding.tvCurrenttime.setText("0:00");
                }
            }
        });
    }

    public void setOnAlbumCardClickListener(View.OnClickListener listener) {
        binding.albumCard.setOnClickListener(listener);
    }

    public void setAlbumCardEnabled(boolean enabled) {
        binding.albumCard.setEnabled(enabled);
    }

    /**
     * Loads and prepares an audio file from a local file path (used in Collection Import).
     */
    public boolean loadSoundFromFile(String filePath, boolean autoPlay) {
        return loadInternal(Uri.fromFile(new File(filePath)), null, true, autoPlay);
    }

    /**
     * Loads and prepares an audio file from a content or file Uri (used in Add/Edit Sound).
     */
    public boolean loadSoundFromUri(Uri uri, String displayFileName, boolean isFileUri, boolean autoPlay) {
        return loadInternal(uri, displayFileName, isFileUri, autoPlay);
    }

    private boolean loadInternal(Uri uri, String displayFileName, boolean isFileUri, boolean autoPlay) {
        stopProgressUpdater();
        releaseMediaPlayer();

        binding.seek.setProgress(0);
        binding.tvCurrenttime.setText("0:00");
        binding.tvFilelength.setText("0:00");
        binding.imgPlay.setIconResource(R.drawable.ic_mtrl_circle_play);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayerIsPrepared = true;
            binding.seek.setMax(mp.getDuration() / 100);
            binding.seek.setProgress(0);
            int duration = mp.getDuration() / 1000;
            binding.tvFilelength.setText(
                    String.format(Locale.ROOT, "%d:%02d", duration / 60, duration % 60));

            if ( displayFileName != null && !displayFileName.isEmpty()) {
                binding.tvFileName.setText(displayFileName);
                binding.tvFileName.setVisibility(View.VISIBLE);
            }

            if (autoPlay) {
                mp.start();
                binding.imgPlay.setIconResource(R.drawable.ic_mtrl_circle_pause);
                startProgressUpdater();
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            stopProgressUpdater();
            binding.imgPlay.setIconResource(R.drawable.ic_mtrl_circle_play);
            binding.seek.setProgress(0);
            binding.tvCurrenttime.setText("0:00");
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            mediaPlayerIsPrepared = false;
            stopProgressUpdater();
            releaseMediaPlayer();
            return true;
        });

        try {
            loadAlbumArtFromUri(activity, uri, isFileUri, binding.imgAlbum);
            mediaPlayer.setDataSource(activity, uri);
            mediaPlayer.prepare();
            binding.layoutControl.setVisibility(View.VISIBLE);
            binding.layoutGuide.setVisibility(View.GONE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mediaPlayerIsPrepared = false;
            if (!isImportMode) {
                binding.layoutControl.setVisibility(View.GONE);
                binding.layoutGuide.setVisibility(View.VISIBLE);
            }
            return false;
        }
    }

    public void togglePlayback() {
        if (mediaPlayer == null || !mediaPlayerIsPrepared) return;
        if (mediaPlayer.isPlaying()) {
            pausePlayback();
        } else {
            mediaPlayer.start();
            binding.imgPlay.setIconResource(R.drawable.ic_mtrl_circle_pause);
            startProgressUpdater();
        }
    }

    public void pausePlayback() {
        if (mediaPlayer != null && mediaPlayerIsPrepared && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            stopProgressUpdater();
            binding.imgPlay.setIconResource(R.drawable.ic_mtrl_circle_play);
        }
    }

    private void startProgressUpdater() {
        stopProgressUpdater();
        timer = new Timer();
        progressUpdater = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if (mediaPlayer == null || !mediaPlayerIsPrepared) {
                        stopProgressUpdater();
                        return;
                    }
                    try {
                        int current = mediaPlayer.getCurrentPosition() / 1000;
                        binding.tvCurrenttime.setText(
                                String.format(Locale.ROOT, "%d:%02d", current / 60, current % 60));
                        binding.seek.setProgress(mediaPlayer.getCurrentPosition() / 100);
                    } catch (IllegalStateException ignored) {
                    }
                });
            }
        };
        timer.schedule(progressUpdater, 100L, 100L);
    }

    public void stopProgressUpdater() {
        if (progressUpdater != null) {
            progressUpdater.cancel();
            progressUpdater = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void releaseMediaPlayer() {
        stopProgressUpdater();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException ignored) {
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayerIsPrepared = false;
    }

    public boolean isSoundPlayable() {
        return mediaPlayerIsPrepared;
    }

    public void playEmptySelectionAnimation() {
        binding.albumCard.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.ani_1));
    }

    /**
     * Static helper to load embedded album art into any ImageView (used for both main preview and RecyclerView items).
     */
    public static void loadEmbeddedAlbumArt(Context context, String filePath, ImageView target) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(filePath);
            byte[] art = mmr.getEmbeddedPicture();
            if (art != null) {
                Glide.with(context.getApplicationContext())
                        .load(art)
                        .centerCrop()
                        .into(target);
            } else {
                Glide.with(context.getApplicationContext()).clear(target);
                target.setImageResource(R.drawable.default_album_art_200dp);
            }
        } catch (Exception ignored) {
            Glide.with(context.getApplicationContext()).clear(target);
            target.setImageResource(R.drawable.default_album_art_200dp);
        } finally {
            try {
                mmr.release();
            } catch (IOException ignored) {
            }
        }
    }

    private static void loadAlbumArtFromUri(Context context, Uri uri, boolean isFileUri, ImageView target) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (isFileUri) {
                mmr.setDataSource(uri.getPath());
            } else {
                try (ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r")) {
                    if (pfd != null) {
                        mmr.setDataSource(pfd.getFileDescriptor());
                    }
                }
            }
            byte[] art = mmr.getEmbeddedPicture();
            if (art != null) {
                Glide.with(context.getApplicationContext())
                        .load(art)
                        .centerCrop()
                        .into(target);
            } else {
                Glide.with(context.getApplicationContext())
                        .load(R.drawable.default_album_art_200dp)
                        .centerCrop()
                        .into(target);
            }
        } catch (Exception ignored) {
            Glide.with(context.getApplicationContext())
                    .load(R.drawable.default_album_art_200dp)
                    .centerCrop()
                    .into(target);
        } finally {
            try {
                mmr.release();
            } catch (IOException ignored) {
            }
        }
    }
}
