package a.a.a;

import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import java.util.TimerTask;

import a.a.a.Nv;

public class Ov extends TimerTask {
    public final AddSoundCollectionActivity a;

    public Ov(AddSoundCollectionActivity addSoundCollectionActivity) {
        this.a = addSoundCollectionActivity;
    }

    @Override
    public void run() {
        this.a.runOnUiThread(new Nv(this));
    }
}
