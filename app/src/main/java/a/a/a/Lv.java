package a.a.a;

import android.media.MediaPlayer;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.sketchware.remod.R;

public class Lv implements MediaPlayer.OnCompletionListener {
    public final AddSoundCollectionActivity a;

    public Lv(AddSoundCollectionActivity addSoundCollectionActivity) {
        this.a = addSoundCollectionActivity;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AddSoundCollectionActivity.b(this.a).cancel();
        AddSoundCollectionActivity.d(this.a).setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        AddSoundCollectionActivity.e(this.a).setProgress(0);
        AddSoundCollectionActivity.h(this.a).setText("0 : 00");
    }
}
