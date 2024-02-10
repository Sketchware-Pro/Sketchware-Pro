package a.a.a;

import android.widget.SeekBar;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;

public class Hv implements SeekBar.OnSeekBarChangeListener {
    public final AddSoundCollectionActivity a;

    public Hv(AddSoundCollectionActivity addSoundCollectionActivity) {
        this.a = addSoundCollectionActivity;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (AddSoundCollectionActivity.a(this.a) == null || !AddSoundCollectionActivity.a(this.a).isPlaying() || AddSoundCollectionActivity.b(this.a) == null) {
            return;
        }
        AddSoundCollectionActivity.b(this.a).cancel();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (AddSoundCollectionActivity.a(this.a) != null) {
            AddSoundCollectionActivity.a(this.a).seekTo(seekBar.getProgress() * 100);
            if (AddSoundCollectionActivity.a(this.a).isPlaying()) {
                AddSoundCollectionActivity.c(this.a);
                return;
            }
            return;
        }
        seekBar.setProgress(0);
    }
}

