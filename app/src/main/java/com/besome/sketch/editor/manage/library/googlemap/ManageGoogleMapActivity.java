package com.besome.sketch.editor.manage.library.googlemap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.iC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.util.Helper;

public class ManageGoogleMapActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String sc_id;
    private Switch libSwitch;
    private EditText editApiKey;
    private ProjectLibraryBean googleMapLibraryBean;
    private ArrayList<HashMap<String, Object>> projectsList = new ArrayList<>();
    private ProjectAdapter projectAdapter;

    private void initializeProjectList() {
        projectsList = new ArrayList<>();

        for (HashMap<String, Object> projectMap : lC.a()) {
            String projectScId = yB.c(projectMap, "sc_id");
            if (!sc_id.equals(projectScId)) {
                iC projectLibraryHandler = new iC(projectScId);
                projectLibraryHandler.i();
                if (projectLibraryHandler.e().useYn.equals("Y")) {
                    projectMap.put("google_map", projectLibraryHandler.e().clone());
                    projectsList.add(projectMap);
                }
            }
        }

        if (projectsList.size() > 0) {
            //noinspection Java8ListSort
            Collections.sort(projectsList, new ProjectComparator());
        }

        projectAdapter.c();
    }

    private void openDoc() {
        if (GB.h(getApplicationContext())) {
            try {
                Uri documentationUrl = Uri.parse("https://developers.google.com/maps/documentation/android-sdk/signup");
                Intent openDocIntent = new Intent(Intent.ACTION_VIEW);
                openDocIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openDocIntent.setData(documentationUrl);
                openDocIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openDocIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                openDocIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(openDocIntent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), 0).show();
        }
    }

    private void importLibrarySettings() {
        final aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.design_library_title_select_project));
        dialog.a(R.drawable.widget_google_map);
        View rootView = wB.a(this, R.layout.manage_library_popup_project_selector);
        RecyclerView projectRecyclerView = rootView.findViewById(R.id.list);
        projectRecyclerView.setHasFixedSize(true);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectAdapter = new ProjectAdapter();
        projectRecyclerView.setAdapter(projectAdapter);
        projectRecyclerView.setItemAnimator(new ci());
        initializeProjectList();
        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_select), view -> {
            if (!mB.a()) {
                if (projectAdapter.selectedProjectIndex >= 0) {
                    HashMap<String, Object> projectMap = projectsList.get(projectAdapter.selectedProjectIndex);
                    googleMapLibraryBean = (ProjectLibraryBean) projectMap.get("google_map");
                    configure();
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        googleMapLibraryBean.useYn = libSwitch.isChecked() ? "Y" : "N";
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
        int id = v.getId();

        if (id == R.id.btn_import) {
            importLibrarySettings();
        } else if (id == R.id.btn_open_doc) {
            openDoc();
        } else if (id == R.id.layout_switch) {
            libSwitch.setChecked(!libSwitch.isChecked());
            if ("Y".equals(googleMapLibraryBean.useYn) && !libSwitch.isChecked()) {
                configureLibrary();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_library_manage_googlemap);
        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
            googleMapLibraryBean = getIntent().getParcelableExtra("google_map");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            googleMapLibraryBean = savedInstanceState.getParcelable("google_map");
        }

        d().a("GoogleMap Settings");
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        LinearLayout switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(R.id.switch_lib);
        editApiKey = findViewById(R.id.ed_api_key);
        ((TextView) findViewById(R.id.tv_api_key)).setText(Helper.getResString(R.string.design_library_google_map_title_api_key));
        ((TextView) findViewById(R.id.tv_desc)).setText(Helper.getResString(R.string.design_library_google_maps_description_operate_normally));
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        Button btnImport = findViewById(R.id.btn_import);
        btnImport.setText(Helper.getResString(R.string.design_library_button_import_from_other_project));
        btnImport.setOnClickListener(this);
        Button btnOpenDoc = findViewById(R.id.btn_open_doc);
        btnOpenDoc.setText(Helper.getResString(R.string.design_library_google_map_button_open_doc));
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
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.delete_96);
        dialog.a(Helper.getResString(R.string.design_library_message_confirm_uncheck_google_map));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(R.string.common_word_delete), v -> {
            libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
            libSwitch.setChecked(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(googleMapLibraryBean.useYn));
        editApiKey.setText(googleMapLibraryBean.data);
    }

    private class ProjectAdapter extends RecyclerView.a<ProjectAdapter.ViewHolder> {
        private int selectedProjectIndex = -1;

        @Override
        public int a() {
            return projectsList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int index) {
            HashMap<String, Object> projectMap = projectsList.get(index);
            String sc_id = yB.c(projectMap, "sc_id");
            viewHolder.imgIcon.setImageResource(R.drawable.default_icon);
            if (yB.a(projectMap, "custom_icon")) {
                Uri iconUri;
                if (VERSION.SDK_INT >= 24) {
                    Context applicationContext =
                            getApplicationContext();
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
            viewHolder.imgSelected.setVisibility(yB.a(projectMap, "selected") ? View.VISIBLE : View.GONE);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int index) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        private class ViewHolder extends v implements View.OnClickListener {
            public final LinearLayout projectLayout;
            public final CircleImageView imgIcon;
            public final TextView projectName;
            public final TextView appName;
            public final TextView pkgName;
            public final TextView projectVersion;
            public final ImageView imgSelected;

            public ViewHolder(View itemView) {
                super(itemView);
                projectLayout = itemView.findViewById(R.id.project_layout);
                projectName = itemView.findViewById(R.id.project_name);
                imgIcon = itemView.findViewById(R.id.img_icon);
                appName = itemView.findViewById(R.id.app_name);
                pkgName = itemView.findViewById(R.id.package_name);
                projectVersion = itemView.findViewById(R.id.project_version);
                imgSelected = itemView.findViewById(R.id.img_selected);
                projectLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a() && v.getId() == R.id.project_layout) {
                    selectedProjectIndex = j();
                    selectProject(selectedProjectIndex);
                }
            }

            private void selectProject(int index) {
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
