package com.google.android.gms.analytics;

import android.content.Context;

public final class GoogleAnalytics {

    public GoogleAnalytics() {
    }

    public static GoogleAnalytics getInstance(Context context) {
        return new GoogleAnalytics();
    }

    public Tracker newTracker(String param1) {
        return new Tracker();
    }

}
