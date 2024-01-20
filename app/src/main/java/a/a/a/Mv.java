package a.a.a;

import android.widget.ImageView;

import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class Mv extends SimpleTarget<GlideDrawable> {
    public final ImageView a;
    public final AddSoundCollectionActivity b;

    public Mv(AddSoundCollectionActivity addSoundCollectionActivity, ImageView imageView) {
        this.b = addSoundCollectionActivity;
        this.a = imageView;
    }

//    public void onResourceReady(Object obj, GlideAnimation glideAnimation) {
//        onResourceReady((GlideDrawable) obj, (GlideAnimation<? super GlideDrawable>) glideAnimation);
//    }

    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
        this.a.setImageDrawable(glideDrawable);
    }
}
