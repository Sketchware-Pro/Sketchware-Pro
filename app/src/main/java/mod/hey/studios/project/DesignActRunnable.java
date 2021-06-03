package mod.hey.studios.project;

import a.a.a.Ep;

public class DesignActRunnable implements Runnable {

    /* Note by Hey! Studios DEV */
    //i created this to fix the ProGuard bug in Android 7.

    private final Ep ep;
    private final String string;

    public DesignActRunnable(Ep ep, String string) {
        this.ep = ep;
        this.string = string;
    }

    @Override
    public void run() {
        if (ep.isShowing()) {
            ep.a(string);
        }
    }
}