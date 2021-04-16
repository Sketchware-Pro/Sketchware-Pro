package com.besome.sketch;

import a.a.a.RM;
import a.a.a.TM;
import android.content.Context;
import androidx.multidex.MultiDexApplication;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class aaaaa extends MultiDexApplication {
    public Tracker a;

    /**
     * as far i think this is the old application class (?)
     * @return rayin merk por modir :sunglasses:
     */
    public synchronized Tracker a() {
        if (this.a == null) {
            this.a = GoogleAnalytics.getInstance(this).newTracker("UA-80718117-1");
        }
        return this.a;
    }

    public void onCreate() {
        aaaaa.super.onCreate();
        if (RM.a(this).isEmpty()) {
            RM.a(this, TM.a(this));
            MobileAds.initialize((Context) this, "ca-app-pub-7978947291427601~4871435882");
            MobileAds.setAppVolume(0.5f);
            MobileAds.setAppMuted(true);
        }
    }
}
