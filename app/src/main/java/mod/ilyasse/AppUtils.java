package mod.ilyasse;

import android.view.View;
import android.view.ViewGroup;

import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class AppUtils {
    public static void animateLayoutChanges(View view) {
        var autoTransition = new AutoTransition();
        autoTransition.setDuration((short) 200);
        TransitionManager.beginDelayedTransition((ViewGroup) view, autoTransition);
    }
}
