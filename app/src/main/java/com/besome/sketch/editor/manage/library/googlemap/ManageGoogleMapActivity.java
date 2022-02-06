package com.besome.sketch.editor.manage.library.googlemap;

import a.a.a.*;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mod.hey.studios.util.Helper;

import com.besome.sketch.editor.manage.library.ProjectComparator;

public class ManageGoogleMapActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public Toolbar toolbar;
    public String sc_id;
    public LinearLayout switchLayout;
    public Switch libSwitch;
    public EditText editApiKey;
    public Button btnImport;
    public Button btnOpenDoc;
    public ProjectLibraryBean googleMapLibraryBean;
    public ArrayList<HashMap<String, Object>> projectsList = new ArrayList<>();
    public ProjectAdapter projectAdapter;

    private void initializeProjectList() {
        projectsList = new ArrayList<>();

        for (HashMap<String, Object> projectMap : lC.a()) {
            String projectScId = yB.c(projectMap, "sc_id");
            if (!sc_id.equals(projectScId)) {
                iC projectLibraryhandler = new iC(projectScId);
                projectLibraryhandler.i();
                if (projectLibraryhandler.e().useYn.equals("Y")) {
                    projectMap.put("google_map", projectLibraryhandler.e().clone());
                    projectsList.add(projectMap);
                }
            }
        }

        if (projectsList.size() > 0) {
            Collections.sort(projectsList, new ProjectComparator());
        }

        projectAdapter.c();
    }

    private void openDoc() {
        if (GB.h(getApplicationContext())) {
            try {
                Uri documantationUrl = Uri.parse("https://developers.google.com/maps/documentation/android-sdk/signup");
                Intent openDocIntent = new Intent("android.intent.action.VIEW");
                openDocIntent.addFlags(268435456);
                openDocIntent.setData(documantationUrl);
                openDocIntent.addFlags(1);
                openDocIntent.addFlags(2);
                openDocIntent.addFlags(64);
                startActivity(openDocIntent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), Helper.getResString(2131624932), 0).show();
        }
    }

    private void importLibrarySettings() {
        final aB dialog = new aB(this);
        dialog.b(Helper.getResString(2131625252));
        dialog.a(2131166247);
        View rootView = wB.a(this, 2131427550);
        RecyclerView projectRecyclerView = rootView.findViewById(2131231440);
        projectRecyclerView.setHasFixedSize(true);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectAdapter = new ProjectAdapter();
        projectRecyclerView.setAdapter(projectAdapter);
        projectRecyclerView.setItemAnimator(new ci());
        initializeProjectList();
        dialog.a(rootView);
        dialog.b(Helper.getResString(2131625035), new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mB.a()) {
                    if (projectAdapter.c >= 0) {
                        HashMap<String, Object> projectMap = projectsList.get(projectAdapter.c);
                        googleMapLibraryBean = (ProjectLibraryBean) projectMap.get("google_map");
                        configure();
                        dialog.dismiss();
                    }
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        String useYn = libSwitch.isChecked() ? "Y" : "N";
        googleMapLibraryBean.useYn = useYn;
        googleMapLibraryBean.data = editApiKey.getText().toString();
        intent.putExtra("google_map", googleMapLibraryBean);
        setResult(RESULT_OK, intent);
        if (editApiKey.getText().toString().isEmpty() && libSwitch.isChecked()) {
            bB.a(getApplicationContext(), "Api key can't be empty!", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 2131230832:
                importLibrarySettings();
                break;

            case 2131230841:
                openDoc();
                break;

            case 2131231408:
                libSwitch.setChecked(libSwitch.isChecked() ^ true);
                if ("Y".equals(googleMapLibraryBean.useYn) && !libSwitch.isChecked()) {
                    configureLibrary();
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle svedInstanceState) {
        super.onCreate(svedInstanceState);
        setContentView(2131427548);
        toolbar = findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(8);
        if (svedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
            googleMapLibraryBean = (ProjectLibraryBean) getIntent().getParcelableExtra("google_map");
        } else {
            sc_id = svedInstanceState.getString("sc_id");
            googleMapLibraryBean = (ProjectLibraryBean) svedInstanceState.getParcelable("google_map");
        }

        d().a("GoogleMap Settings");
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        switchLayout = findViewById(2131231408);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(2131231775);
        editApiKey = findViewById(2131230986);
        ((TextView) findViewById(2131231875)).setText(Helper.getResString(2131625242));
        ((TextView) findViewById(2131231944)).setText(Helper.getResString(2131625243));
        ((TextView) findViewById(2131231965)).setText(Helper.getResString(2131625249));
        btnImport = findViewById(2131230832);
        btnImport.setText(Helper.getResString(2131625201));
        btnImport.setOnClickListener(this);
        btnOpenDoc = findViewById(2131230841);
        btnOpenDoc.setText(Helper.getResString(2131625240));
        btnOpenDoc.setOnClickListener(this);
        configure();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putParcelable("google_map", googleMapLibraryBean);
        super.onSaveInstanceState(outState);
    }

    private void configureLibrary() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(2131625047));
        dialog.a(2131165524);
        dialog.a(Helper.getResString(2131625246));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(2131624986), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libSwitch.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(2131624974), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libSwitch.setChecked(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(googleMapLibraryBean.useYn));
        editApiKey.setText(googleMapLibraryBean.data);
    }

    public class ProjectAdapter extends RecyclerView.a<ProjectAdapter.ViewHolder> {
        public int c = -1;

        @Override
        public int a() {
            return projectsList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int index) {
            HashMap projectMap = (HashMap) projectsList.get(index);
            String sc_id = yB.c(projectMap, "sc_id");
            viewHolder.imgIcon.setImageResource(2131165521);
            if (yB.a(projectMap, "custom_icon")) {
                Uri iconUri;
                if (VERSION.SDK_INT >= 24) {
                    Context applicationContext =
                            ManageGoogleMapActivity.this.getApplicationContext();
                    String providerPath = getPackageName() + ".provider";
                    String iconPath = wq.e() + File.separator + sc_id;
                    iconUri =
                            FileProvider.a(
                                    applicationContext,
                                    providerPath,
                                    new File(iconPath, "icon.png"));
                } else {
                    String iconPath = wq.e() + File.separator + sc_id;
                    iconUri = Uri.fromFile(new File(iconPath, "icon.png"));
                }

                viewHolder.imgIcon.setImageURI(iconUri);
            }

            viewHolder.appName.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.projectName.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.pkgName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String version =
                    String.format(
                            "%s(%s)",
                            yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code"));
            viewHolder.projectVersion.setText(version);
            viewHolder.imgSelected.setVisibility(yB.a(projectMap, "selected") ? 0 : 8);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int index) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427549, parent, false));
        }

        public class ViewHolder extends v implements View.OnClickListener {
            public LinearLayout projectLayout;
            public CircleImageView imgIcon;
            public TextView projectName;
            public TextView appName;
            public TextView pkgName;
            public TextView projectVersion;
            public ImageView imgSelected;

            public ViewHolder(View view) {
                super(view);
                projectLayout = view.findViewById(2131231613);
                projectName = view.findViewById(2131231614);
                imgIcon = view.findViewById(2131231151);
                appName = view.findViewById(2131230780);
                pkgName = view.findViewById(2131231579);
                projectVersion = view.findViewById(2131231618);
                imgSelected = view.findViewById(2131231181);
                projectLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a() && v.getId() == 2131231613) {
                    ProjectAdapter.this.c = ViewHolder.this.j();
                    c(ProjectAdapter.this.c);
                }
            }

            private void c(int index) {
                if (projectsList.size() > 0) {
                    for (HashMap<String, Object> projectMap : projectsList) {
                        projectMap.put("selected", false);
                    }

                    projectsList.get(index).put("selected", true);
                    ProjectAdapter.this.c();
                }
            }
        }
    }
}
