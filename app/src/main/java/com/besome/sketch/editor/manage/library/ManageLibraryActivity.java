package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.admob.AdmobActivity;
import com.besome.sketch.editor.manage.library.admob.ManageAdmobActivity;
import com.besome.sketch.editor.manage.library.compat.ManageCompatActivity;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;
import com.besome.sketch.editor.manage.library.firebase.ManageFirebaseActivity;
import com.besome.sketch.editor.manage.library.googlemap.ManageGoogleMapActivity;
import com.besome.sketch.lib.base.BaseSessionAppCompatActivity;

import a.a.a.MA;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.qu;
import a.a.a.ru;
import mod.hey.studios.util.Helper;

public class ManageLibraryActivity extends BaseSessionAppCompatActivity implements View.OnClickListener {
    private String sc_id;
    private Toolbar toolbar;
    private LinearLayout libraryItemLayout;

    private ProjectLibraryBean firebaseLibraryBean;
    private ProjectLibraryBean compatLibraryBean;
    private ProjectLibraryBean admobLibraryBean;
    private ProjectLibraryBean googleMapLibraryBean;

    private String originalFirebaseUseYn = "N";
    private String originalCompatUseYn = "N";
    private String originalAdmobUseYn = "N";
    private String originalGoogleMapUseYn = "N";

    @Override
    public void a(int requestCode, String idk) {
        if (requestCode == 234) {
            toAdmobActivity(admobLibraryBean);
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

    private void addLibraryItem(ProjectLibraryBean libraryBean) {
        qu libraryItemView = new qu(this);
        libraryItemView.a(2131427538);
        libraryItemView.setTag(libraryBean.libType);
        libraryItemView.setData(libraryBean);
        libraryItemView.setOnClickListener(this);
        libraryItemLayout.addView(libraryItemView);
    }

    private void toCompatActivity(ProjectLibraryBean compatLibraryBean, ProjectLibraryBean firebaseLibraryBean) {
        Intent intent = new Intent(getApplicationContext(), ManageCompatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("compat", compatLibraryBean);
        intent.putExtra("firebase", firebaseLibraryBean);
        startActivityForResult(intent, 231);
    }

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        switch (libraryBean.libType) {
            case 0:
                firebaseLibraryBean = libraryBean;
                break;

            case 1:
                compatLibraryBean = libraryBean;
                break;

            case 2:
                admobLibraryBean = libraryBean;
                break;

            case 3:
                googleMapLibraryBean = libraryBean;
                break;
        }

        for (int index = 0; index < libraryItemLayout.getChildCount(); index++) {
            qu libraryItemView = (qu) libraryItemLayout.getChildAt(index);
            if (libraryBean.libType == (Integer) libraryItemView.getTag()) {
                libraryItemView.setData(libraryBean);
                if (libraryItemView instanceof ru) {
                    ((ru) libraryItemView).setData(libraryBean);
                }
            }
        }

    }

    private void toAdmobActivity(ProjectLibraryBean libraryBean) {
        Intent intent;
        if (!libraryBean.reserved1.isEmpty()) {
            intent = new Intent(getApplicationContext(), ManageAdmobActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), AdmobActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("admob", libraryBean);
        startActivityForResult(intent, 234);
    }

    private void toFirebaseActivity(ProjectLibraryBean libraryBean) {
        Intent intent;
        if (!libraryBean.data.isEmpty()) {
            intent = new Intent(getApplicationContext(), ManageFirebaseActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), FirebaseActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("firebase", libraryBean);
        startActivityForResult(intent, 230);
    }

    private void toGoogleMapActivity(ProjectLibraryBean libraryBean) {
        Intent intent = new Intent(getApplicationContext(), ManageGoogleMapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("google_map", libraryBean);
        startActivityForResult(intent, 241);
    }

    private void saveLibraryConfiguration() {
        jC.c(sc_id).b(compatLibraryBean);
        jC.c(sc_id).c(firebaseLibraryBean);
        jC.c(sc_id).a(admobLibraryBean);
        jC.c(sc_id).d(googleMapLibraryBean);
        jC.c(sc_id).k();
        jC.b(sc_id).a(jC.c(sc_id));
        jC.a(sc_id).a(jC.b(sc_id));
        jC.a(sc_id).a(firebaseLibraryBean);
        jC.a(sc_id).a(admobLibraryBean, jC.b(sc_id));
        jC.a(sc_id).b(googleMapLibraryBean, jC.b(sc_id));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 230:
                    ProjectLibraryBean libraryBean = (ProjectLibraryBean) data.getParcelableExtra("firebase");
                    initializeLibrary(libraryBean);
                    if (libraryBean.useYn.equals("Y") && !compatLibraryBean.useYn.equals("Y")) {
                        libraryBean = compatLibraryBean;
                        libraryBean.useYn = "Y";
                        initializeLibrary(libraryBean);
                        showFirebaseNeedComaptDialog();
                    }
                    break;

                case 231:
                    initializeLibrary((ProjectLibraryBean) data.getParcelableExtra("compat"));
                    break;

                case 234:
                    initializeLibrary((ProjectLibraryBean) data.getParcelableExtra("admob"));
                    break;

                case 241:
                    initializeLibrary((ProjectLibraryBean) data.getParcelableExtra("google_map"));
                    break;

                case 505:
                    toAdmobActivity(admobLibraryBean);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        k();
        try {
            new Handler().postDelayed(() -> {
                new SaveLibraryTask(getBaseContext()).execute(new Void[0]);
            }, 500L);
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int vTag = (Integer) v.getTag();
            switch (vTag) {
                case 0:
                    toFirebaseActivity(firebaseLibraryBean);
                    break;

                case 1:
                    toCompatActivity(compatLibraryBean, firebaseLibraryBean);
                    break;

                case 2:
                    // what? why?
                    admobLibraryBean.isEnabled();
                    toAdmobActivity(admobLibraryBean);
                    break;

                case 3:
                    toGoogleMapActivity(googleMapLibraryBean);
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        setContentView(2131427531);
        toolbar = (Toolbar) findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(8);
        d().a(Helper.getResString(2131625133));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        libraryItemLayout = (LinearLayout) findViewById(2131230934);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            compatLibraryBean = jC.c(sc_id).c();
            if (compatLibraryBean == null) {
                compatLibraryBean = new ProjectLibraryBean(1);
            }
            originalCompatUseYn = compatLibraryBean.useYn;

            firebaseLibraryBean = jC.c(sc_id).d();
            if (firebaseLibraryBean == null) {
                firebaseLibraryBean = new ProjectLibraryBean(0);
            }
            originalFirebaseUseYn = firebaseLibraryBean.useYn;

            admobLibraryBean = jC.c(sc_id).b();
            if (admobLibraryBean == null) {
                admobLibraryBean = new ProjectLibraryBean(2);
            }
            originalAdmobUseYn = admobLibraryBean.useYn;

            googleMapLibraryBean = jC.c(sc_id).e();
            if (googleMapLibraryBean == null) {
                googleMapLibraryBean = new ProjectLibraryBean(3);
            }
            originalGoogleMapUseYn = googleMapLibraryBean.useYn;
        } else {
            firebaseLibraryBean = (ProjectLibraryBean) savedInstanceState.getParcelable("firebase");
            originalFirebaseUseYn = savedInstanceState.getString("originalFirebaseUseYn");
            compatLibraryBean = (ProjectLibraryBean) savedInstanceState.getParcelable("compat");
            originalCompatUseYn = savedInstanceState.getString("originalCompatUseYn");
            admobLibraryBean = (ProjectLibraryBean) savedInstanceState.getParcelable("admob");
            originalAdmobUseYn = savedInstanceState.getString("originalAdmobUseYn");
            googleMapLibraryBean = (ProjectLibraryBean) savedInstanceState.getParcelable("google_map");
            originalGoogleMapUseYn = savedInstanceState.getString("originalGoogleMapUseYn");
        }

        addLibraryItem(compatLibraryBean);
        addLibraryItem(firebaseLibraryBean);
        addLibraryItem(admobLibraryBean);
        addLibraryItem(googleMapLibraryBean);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putParcelable("firebase", firebaseLibraryBean);
        outState.putParcelable("compat", compatLibraryBean);
        outState.putParcelable("admob", admobLibraryBean);
        outState.putParcelable("google_map", googleMapLibraryBean);
        outState.putString("originalFirebaseUseYn", originalFirebaseUseYn);
        outState.putString("originalCompatUseYn", originalCompatUseYn);
        outState.putString("originalAdmobUseYn", originalAdmobUseYn);
        outState.putString("originalGoogleMapUseYn", originalGoogleMapUseYn);
        super.onSaveInstanceState(outState);
    }

    private void showFirebaseNeedComaptDialog() {
        aB dialog = new aB(this);
        dialog.a(2131166245);
        dialog.b(Helper.getResString(2131625047));
        dialog.a(Helper.getResString(2131625223));
        dialog.b(Helper.getResString(2131625010), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public class SaveLibraryTask extends MA {

        public SaveLibraryTask(Context context) {
            super(context);
            ManageLibraryActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            Intent intent = new Intent();
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("firebase", firebaseLibraryBean);
            intent.putExtra("compat", compatLibraryBean);
            intent.putExtra("admob", admobLibraryBean);
            intent.putExtra("google_map", googleMapLibraryBean);
            setResult(-1, intent);
            finish();
        }

        @Override
        public void a(String idk) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override
        public void b() {
            try {
                publishProgress("Now processing..");
                saveLibraryConfiguration();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
