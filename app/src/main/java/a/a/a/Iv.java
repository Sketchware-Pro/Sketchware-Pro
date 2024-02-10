package a.a.a;

import android.media.MediaPlayer;

import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;

public class Iv implements MediaPlayer.OnPreparedListener {
    public final String a;
    public final AddSoundCollectionActivity b;

    public Iv(AddSoundCollectionActivity addSoundCollectionActivity, String str) {
        this.b = addSoundCollectionActivity;
        this.a = str;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (this.a == null) {
            return;
        }
        AddSoundCollectionActivity.d(this.b).setImageResource(0x7f07026d);
        AddSoundCollectionActivity.d(this.b).setEnabled(true);
        AddSoundCollectionActivity.e(this.b).setMax(mediaPlayer.getDuration() / 100);
        AddSoundCollectionActivity.e(this.b).setProgress(0);
        int duration = mediaPlayer.getDuration() / 1000;
        AddSoundCollectionActivity.f(this.b).setText(String.format("%d : %02d", Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)));
        AddSoundCollectionActivity.g(this.b).setText(this.a.substring(this.a.lastIndexOf("/") + 1));
        mediaPlayer.start();
        AddSoundCollectionActivity.c(this.b);
    }
}

