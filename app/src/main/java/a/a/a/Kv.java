package a.a.a;

import android.media.MediaPlayer;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;

public class Kv implements MediaPlayer.OnPreparedListener {
    public final AddSoundCollectionActivity a;

    public Kv(AddSoundCollectionActivity addSoundCollectionActivity) {
        this.a = addSoundCollectionActivity;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        AddSoundCollectionActivity.d(this.a).setImageResource(0x7f07026d);
        AddSoundCollectionActivity.d(this.a).setEnabled(true);
        AddSoundCollectionActivity.e(this.a).setMax(mediaPlayer.getDuration() / 100);
        AddSoundCollectionActivity.e(this.a).setProgress(0);
        int duration = mediaPlayer.getDuration() / 1000;
        AddSoundCollectionActivity.f(this.a).setText(String.format("%d : %02d", Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)));
        AddSoundCollectionActivity.a(this.a).start();
        AddSoundCollectionActivity.c(this.a);
    }
}
