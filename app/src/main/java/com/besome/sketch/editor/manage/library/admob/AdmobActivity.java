package com.besome.sketch.editor.manage.library.admob;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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
import a.a.a.Iu;
import a.a.a.Ku;
import a.a.a.Nu;
import a.a.a.Tu;
import a.a.a.Uu;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.iC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import mod.hey.studios.util.Helper;

public class AdmobActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private TextView nextStep;
    private ImageView back;
    private TextView stepTitle;
    private TextView stepDescription;
    private LinearLayout stepContainer;
    private String[] stepTitles;
    private String[] stepDescriptions;
    private int stepPosition = 0;
    private Uu step;
    private ProjectLibraryBean adMobSettings;
    private Button goToDocumentation;
    private Button importFromOtherProject;
    private ArrayList<HashMap<String, Object>> projects = new ArrayList<>();
    private ProjectsAdapter adapter;
    private String sc_id;
    private CardView goToConsole;
    private TextView previousStep;
    private TextView topTitle;

    private void showStep(int position) {
        if (position == 3) {
            topTitle.setText(Helper.getResString(R.string.common_word_review));
            nextStep.setText(Helper.getResString(R.string.common_word_save));
        } else {
            topTitle.setText(xB.b().a(this, R.string.common_word_step, position + 1));
            nextStep.setText(Helper.getResString(R.string.common_word_next));
        }

        if (position == 0) {
            back.setVisibility(View.VISIBLE);
            previousStep.setVisibility(View.GONE);
        } else {
            back.setVisibility(View.GONE);
            previousStep.setVisibility(View.VISIBLE);
        }

        stepTitle.setText(stepTitles[position]);
        stepDescription.setText(stepDescriptions[position]);
        stepContainer.removeAllViews();
        switch (position) {
            case 0:
                Iu addAdUnitsStep = new Iu(this);
                stepContainer.addView(addAdUnitsStep);
                addAdUnitsStep.setData(adMobSettings);
                step = addAdUnitsStep;
                break;

            case 1:
                goToConsole.setVisibility(View.GONE);
                Nu assignAdUnitsStep = new Nu(this);
                stepContainer.addView(assignAdUnitsStep);
                assignAdUnitsStep.setData(adMobSettings);
                step = assignAdUnitsStep;
                break;

            case 2:
                goToConsole.setVisibility(View.GONE);
                Tu testDevicesStep = new Tu(this);
                stepContainer.addView(testDevicesStep);
                testDevicesStep.setData(adMobSettings);
                step = testDevicesStep;
                break;

            case 3:
                goToConsole.setVisibility(View.GONE);
                Ku reviewStep = new Ku(this);
                stepContainer.addView(reviewStep);
                reviewStep.setData(adMobSettings);
                step = reviewStep;
                break;

            default:
        }

        if (step.getDocUrl().isEmpty()) {
            goToDocumentation.setVisibility(View.GONE);
        } else {
            goToDocumentation.setVisibility(View.VISIBLE);
        }

        if (position > 0) {
            importFromOtherProject.setVisibility(View.GONE);
        } else {
            importFromOtherProject.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    private void loadProjects() {
        projects = new ArrayList<>();

        for (HashMap<String, Object> stringObjectHashMap : lC.a()) {
            String projectSc_id = yB.c(stringObjectHashMap, "sc_id");
            if (!sc_id.equals(projectSc_id)) {
                iC iC = new iC(projectSc_id);
                iC.i();
                if (iC.b().useYn.equals("Y")) {
                    stringObjectHashMap.put("admob_setting", iC.b().clone());
                    projects.add(stringObjectHashMap);
                }
            }
        }

        if (projects.size() > 0) {
            //noinspection Java8ListSort
            Collections.sort(projects, new ProjectComparator());
        }

        adapter.c();
    }

    private void nextStep() {
        if (step.isValid()) {
            step.a(adMobSettings);
            if (stepPosition < 3) {
                showStep(++stepPosition);
            } else {
                Intent intent = new Intent();
                intent.putExtra("admob", adMobSettings);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (stepPosition > 0) {
            showStep(--stepPosition);
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_open_doc) {
            goToDocumentation();
        } else if (id == R.id.cv_console) {
            goToConsole();
        } else if (id == R.id.tv_nextbtn) {
            nextStep();
        } else if (id == R.id.tv_prevbtn) {
            onBackPressed();
        }
    }

    private void goToConsole() {
        if (GB.h(this)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("https://apps.admob.com/v2/home"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                showGoogleChromeNotice();
            }
        } else {
            bB.a(this, Helper.getResString(R.string.common_message_check_network), 0).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        setContentView(R.layout.manage_library_admob);
        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = getIntent().getStringExtra("sc_id");
        }

        stepTitles = new String[]{
                Helper.getResString(R.string.design_library_admob_setting_step1_title),
                Helper.getResString(R.string.design_library_admob_setting_step2_title),
                Helper.getResString(R.string.design_library_admob_setting_step3_title),
                Helper.getResString(R.string.design_library_admob_setting_step4_title)
        };
        stepDescriptions = new String[]{
                Helper.getResString(R.string.design_library_admob_setting_step1_desc),
                Helper.getResString(R.string.design_library_admob_setting_step2_desc),
                Helper.getResString(R.string.design_library_admob_setting_step3_desc),
                Helper.getResString(R.string.design_library_admob_setting_step4_desc)
        };
        goToConsole = findViewById(R.id.cv_console);
        goToConsole.setOnClickListener(this);
        TextView goToConsole = findViewById(R.id.tv_goto_console);
        goToConsole.setText(Helper.getResString(R.string.design_library_admob_button_goto_setting));
        previousStep = findViewById(R.id.tv_prevbtn);
        previousStep.setText(Helper.getResString(R.string.common_word_prev));
        previousStep.setOnClickListener(this);
        ImageView icon = findViewById(R.id.icon);
        icon.setImageResource(R.drawable.widget_admob);
        topTitle = findViewById(R.id.tv_toptitle);
        nextStep = findViewById(R.id.tv_nextbtn);
        nextStep.setText(Helper.getResString(R.string.common_word_next));
        nextStep.setOnClickListener(this);
        back = findViewById(R.id.img_backbtn);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        stepTitle = findViewById(R.id.tv_step_title);
        stepDescription = findViewById(R.id.tv_step_desc);
        goToDocumentation = findViewById(R.id.btn_open_doc);
        goToDocumentation.setText(Helper.getResString(R.string.common_word_go_to_documentation));
        goToDocumentation.setOnClickListener(this);
        importFromOtherProject = findViewById(R.id.btn_import);
        importFromOtherProject.setText(Helper.getResString(R.string.design_library_button_import_from_other_project));
        importFromOtherProject.setOnClickListener(view -> showImportFromOtherProjectDialog());
        stepContainer = findViewById(R.id.layout_container);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adMobSettings = getIntent().getParcelableExtra("admob");
        showStep(stepPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void goToDocumentation() {
        if (!step.getDocUrl().isEmpty()) {
            if (GB.h(this)) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(step.getDocUrl()));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    showGoogleChromeNotice();
                }
            } else {
                bB.a(this, Helper.getResString(R.string.common_message_check_network), 0).show();
            }
        }
    }

    private void showImportFromOtherProjectDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.design_library_title_select_project));
        dialog.a(R.drawable.widget_admob);
        View rootView = wB.a(this, R.layout.manage_library_popup_project_selector);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new ci());
        loadProjects();
        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_select), view -> {
            if (!mB.a()) {
                if (adapter.selectedProjectIndex >= 0) {
                    HashMap<String, Object> projectMap = projects.get(adapter.selectedProjectIndex);
                    adMobSettings = (ProjectLibraryBean) projectMap.get("admob_setting");
                    stepPosition = 3;
                    showStep(stepPosition);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showGoogleChromeNotice() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), view -> {
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

    private class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {

        private int selectedProjectIndex = -1;

        @Override
        public int a() {
            return projects.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = projects.get(position);
            String projectSc_id = yB.c(projectMap, "sc_id");
            String iconDir = wq.e() + File.separator + projectSc_id;
            viewHolder.icon.setImageResource(R.drawable.default_icon);
            if (yB.a(projectMap, "custom_icon")) {
                Uri iconUri;
                if (VERSION.SDK_INT >= 24) {
                    iconUri = FileProvider.a(getApplicationContext(), getPackageName() + ".provider", new File(iconDir, "icon.png"));
                } else {
                    iconUri = Uri.fromFile(new File(iconDir, "icon.png"));
                }

                viewHolder.icon.setImageURI(iconUri);
            }

            viewHolder.appName.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.projectName.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            viewHolder.version.setText(String.format("%s(%s)", yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code")));

            viewHolder.checkmark.setVisibility(yB.a(projectMap, "selected") ? View.VISIBLE : View.GONE);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        private class ViewHolder extends v implements View.OnClickListener {

            private final CircleImageView icon;
            private final TextView projectName;
            private final TextView appName;
            private final TextView packageName;
            private final TextView version;
            private final ImageView checkmark;

            public ViewHolder(View itemView) {
                super(itemView);
                LinearLayout projectLayout = itemView.findViewById(R.id.project_layout);
                projectName = itemView.findViewById(R.id.project_name);
                icon = itemView.findViewById(R.id.img_icon);
                appName = itemView.findViewById(R.id.app_name);
                packageName = itemView.findViewById(R.id.package_name);
                version = itemView.findViewById(R.id.project_version);
                checkmark = itemView.findViewById(R.id.img_selected);
                projectLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a() && v.getId() == R.id.project_layout) {
                    selectedProjectIndex = j();
                    selectProject(selectedProjectIndex);
                }
            }

            private void selectProject(int i) {
                if (projects.size() > 0) {
                    for (HashMap<String, Object> stringObjectHashMap : projects) {
                        stringObjectHashMap.put("selected", false);
                    }

                    projects.get(i).put("selected", true);
                    adapter.c();
                }
            }
        }
    }
}
