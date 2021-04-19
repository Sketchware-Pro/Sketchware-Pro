package a.a.a;

import com.besome.sketch.MainActivity;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentStatus;

public class zn implements ConsentInfoUpdateListener {

    public final MainActivity a;

    public zn(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    @Override // com.google.ads.consent.ConsentInfoUpdateListener
    public void a(ConsentStatus consentStatus) {
        if (consentStatus.equals(ConsentStatus.UNKNOWN)) {
            this.a.p();
        }
    }

    @Override // com.google.ads.consent.ConsentInfoUpdateListener
    public void a(String str) {
    }
}