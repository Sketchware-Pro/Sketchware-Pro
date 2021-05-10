package com.besome.sketch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import a.a.a.DB;


public class VideoGuideActivity extends YouTubeBaseActivity implements View.OnClickListener {

    public Button e;
    public Button f;
    public TextView g;
    public Boolean h;
    public DB i;

    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        /*
        if (i2 == 100 && i3 == -1) {
            this.i.a("P1I17", (Object) false);
            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();
        }*/
    }

    public void onClick(View view) {
        /*
        if (!mB.a()) {
            switch (view.getId()) {
                case 2131231075:
                    this.i.a("P1I17", (Object) false);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                case 2131231076:
                    Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent2, 100);
                    return;
                default:
                    return;
            }
        }
         */
    }

    @Override // com.google.android.youtube.player.YouTubeBaseActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(0x7f0b01bd);
        /*
         * Replaced with Intent to avoid crash on emulators.
         */
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        /*this.e = (Button) findViewById(0x7f080164);
        this.e.setOnClickListener(this);
        this.e.setText(xB.b().a(this, 2131625421));
        this.f = (Button) findViewById(0x7f080163);
        this.f.setOnClickListener(this);
        this.f.setText(xB.b().a(this, 2131625422));
        this.g = (TextView) findViewById(0x7f0804fd);
        this.i = new DB(getApplicationContext(), "P1");*/
    }

    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        /*
        if (bundle == null) {
            this.h = false;
        } else {
            this.h = Boolean.valueOf(bundle.getBoolean("changed_screen"));
        }
        if (GB.h(getApplicationContext())) {
            ((YouTubePlayerView) findViewById(0x7f080654)).a("AIzaSyBv696FDTPStkyYTGrW26Hb209wM2d6h_Q", new nM.a() {
                @Override
                public void a(nM.b b, lM lM) {

                }

                @Override
                public void a(nM.b b, nM nM, boolean b1) {
                    if (h.booleanValue()) {
                        nM.b("yKlj_0LNvqM");
                    } else {
                        nM.a("yKlj_0LNvqM");
                    }
                }
            });
            this.g.setText(xB.b().a(getApplicationContext(), 2131625423));
            return;
        }
        findViewById(0x7f080654).setVisibility(View.GONE);
        this.g.setText(xB.b().a(getApplicationContext(), 2131624932));
        */
    }

    @Override // com.google.android.youtube.player.YouTubeBaseActivity
    public void onResume() {
        super.onResume();
    }

    @Override // com.google.android.youtube.player.YouTubeBaseActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //bundle.putBoolean("changed_screen", true);
    }
}