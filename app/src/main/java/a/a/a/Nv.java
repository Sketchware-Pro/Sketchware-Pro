package a.a.a;

import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;

public class Nv implements Runnable {
    public final Ov a;

    public Nv(Ov ov) {
        this.a = ov;
    }

    @Override
    public void run() {
        if (AddSoundCollectionActivity.a(this.a.a) == null) {
            AddSoundCollectionActivity.b(this.a.a).cancel();
            return;
        }
        int currentPosition = AddSoundCollectionActivity.a(this.a.a).getCurrentPosition() / 1000;
        AddSoundCollectionActivity.h(this.a.a).setText(String.format("%d : %02d", Integer.valueOf(currentPosition / 60), Integer.valueOf(currentPosition % 60)));
        AddSoundCollectionActivity.e(this.a.a).setProgress(AddSoundCollectionActivity.a(this.a.a).getCurrentPosition() / 100);
    }
}
