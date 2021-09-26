package com.besome.sketch.editor.manage.library.admob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseSessionAppCompatActivity;

import java.util.ArrayList;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class ManageAdmobActivity extends BaseSessionAppCompatActivity implements View.OnClickListener {

    public DB A = null;
    public TestDeviceAdapter testDeviceAdapter;
    public ArrayList<AdTestDeviceBean> testDeviceList = new ArrayList();
    public LinearLayout p;
    public LinearLayout switchLayout;
    public Switch libSwitch;
    public TextView tvBannerName;
    public TextView tvBannerId;
    public TextView tvInterName;
    public TextView tvInterId;
    public RecyclerView listTestDevice;
    public Toolbar toolbar;
    public Button btnConsole;
    public ProjectLibraryBean admobLibraryBean;
    private int n = 8001;
    private int o = 8002;

    @Override
    public void a(int rquestCode, String idk) {
        if (rquestCode == 8001 || rquestCode == 8002) {
            n(rquestCode);
        }

    }

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        admobLibraryBean = libraryBean;
        configure();
    }

    private void setBannerAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                tvBannerName.setText(adId.substring(0, indexOfSemicolon));
                tvBannerId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                tvBannerName.setText("");
                tvBannerId.setText(adId);
            }

        }
    }

    private void setInterAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                tvInterName.setText(adId.substring(0, indexOfSemicolon));
                tvInterId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                tvInterName.setText("");
                tvInterId.setText(adId);
            }

        }
    }

    @Override
    public void g(int idk) {
    }

    @Override
    public void h(int idk) {
    }

    @Override
    public void l() {
    }

    @Override
    public void m() {
    }

    private void n() {
        if (GB.h(this.getApplicationContext())) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setData(Uri.parse("https://apps.admob.com/v2/home"));
                intent.addFlags(1);
                intent.addFlags(2);
                intent.addFlags(64);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(this.getApplicationContext(), Helper.getResString(2131624932), 0).show();
        }

    }

    private void n(int requestCode) {
        if (requestCode == 8001) {
            libSwitch.setChecked(true);
            admobLibraryBean.useYn = "Y";
        } else {
            toAdmobActivity();
        }
    }

    private void o() {
        try {
            this.A.a("P1I16", true);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(Uri.parse("https://docs.sketchware.io/docs/admob-getting-started.html"));
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(64);
            startActivity(intent);
        } catch (Exception e) {
            downloadChromeDialog();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 236:
                    initializeLibrary((ProjectLibraryBean) data.getParcelableExtra("admob"));
                    break;
                case 8001:
                    libSwitch.setChecked(true);
                    admobLibraryBean.useYn = "Y";
                    break;
                case 8002:
                    toAdmobActivity();
                    break;
            }
        }
    }

    public void onBackPressed() {
        getIntent().putExtra("admob", admobLibraryBean);
        setResult(-1, getIntent());
        super.onBackPressed();
    }

    public void onClick(View v) {
        if (!mB.a()) {
            switch (v.getId()) {
                case 2131230815:
                    n();
                    break;

                case 2131231408:
                    if (!libSwitch.isChecked()) {
                        i(8001);
                    } else {
                        Switch enableAdmobSwitch = libSwitch;
                        enableAdmobSwitch.setChecked(enableAdmobSwitch.isChecked() ^ true);
                        if ("Y".equals(admobLibraryBean.useYn) && !libSwitch.isChecked()) {
                            configureLibrary();
                        } else {
                            admobLibraryBean.useYn = "Y";
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427545);
        p = (LinearLayout) findViewById(2131231332);
        toolbar = (Toolbar) findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(8);
        d().a(Helper.getResString(2131625195));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        A = new DB(this.getApplicationContext(), "P1");
        admobLibraryBean = (ProjectLibraryBean) getIntent().getParcelableExtra("admob");
        ((TextView) findViewById(2131231965)).setText(Helper.getResString(2131625249));
        ((TextView) findViewById(2131232201)).setText(Helper.getResString(2131625196));
        ((TextView) findViewById(2131232203)).setText(Helper.getResString(2131625192) + " : ");
        ((TextView) findViewById(2131232202)).setText(Helper.getResString(2131625193) + " : ");
        ((TextView) findViewById(2131232220)).setText(Helper.getResString(2131625197));
        ((TextView) findViewById(2131232222)).setText(Helper.getResString(2131625192) + " : ");
        ((TextView) findViewById(2131232221)).setText(Helper.getResString(2131625193) + " : ");
        ((TextView) findViewById(2131232246)).setText(Helper.getResString(2131625177));
        tvBannerId = (TextView) findViewById(2131231887);
        tvBannerName = (TextView) findViewById(2131231889);
        tvInterId = (TextView) findViewById(2131232012);
        tvInterName = (TextView) findViewById(2131232014);
        listTestDevice = (RecyclerView) findViewById(2131231468);
        listTestDevice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        testDeviceAdapter = new TestDeviceAdapter();
        listTestDevice.setAdapter(testDeviceAdapter);
        libSwitch = (Switch) findViewById(2131231429);
        switchLayout = (LinearLayout) findViewById(2131231408);
        switchLayout.setOnClickListener(this);
        btnConsole = (Button) findViewById(2131230815);
        btnConsole.setText(Helper.getResString(2131625160));
        btnConsole.setOnClickListener(this);
        configure();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492874, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131231497:
                o();
                break;
            case 2131231498:
                i(8002);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    private void configureLibrary() {
        final aB dialog = new aB(this);
        dialog.a(2131165524);
        dialog.b(Helper.getResString(2131625047));
        dialog.a(Helper.getResString(2131625174));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(2131624986), new View.OnClickListener() {
            public void onClick(View v) {
                if (!mB.a()) {
                    admobLibraryBean.useYn = "N";
                    libSwitch.setChecked(false);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(2131165415);
        dialog.b(Helper.getResString(2131626412));
        dialog.a(Helper.getResString(2131625629));
        dialog.b(Helper.getResString(2131625010), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void toAdmobActivity() {
        Intent intent = new Intent(getApplicationContext(), AdmobActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("admob", admobLibraryBean);
        startActivityForResult(intent, 236);
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(admobLibraryBean.useYn));
        setBannerAdUnit(admobLibraryBean.reserved1);
        setInterAdUnit(admobLibraryBean.reserved2);
        testDeviceList = admobLibraryBean.testDevices;
        testDeviceAdapter.c();
    }

    public class TestDeviceAdapter extends RecyclerView.a<TestDeviceAdapter.ViewHolder> {
        public int index = -1;

        public int a() {
            return testDeviceList.size();
        }

        public void b(ViewHolder viewHolder, int index) {
            AdTestDeviceBean adTestDeviceBean = (AdTestDeviceBean) testDeviceList.get(index);
            viewHolder.tvDeviceId.setText(adTestDeviceBean.deviceId);
        }

        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427555, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView tvDeviceId;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                tvDeviceId = (TextView) view.findViewById(2131231956);
                imageView = (ImageView) view.findViewById(2131231132);
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
