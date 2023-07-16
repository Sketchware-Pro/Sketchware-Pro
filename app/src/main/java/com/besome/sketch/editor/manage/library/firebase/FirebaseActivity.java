package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iC;
import a.a.a.kv;
import a.a.a.lC;
import a.a.a.lv;
import a.a.a.mB;
import a.a.a.mv;
import a.a.a.nv;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import mod.hey.studios.util.Helper;

public class FirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private static final int STEP_1 = 0;
    private static final int STEP_2 = 1;
    private static final int STEP_3 = 2;

    private TextView stepDescription;
    private LinearLayout stepContainer;
    private ImageView back;
    private Button openDocumentation;
    private Button importFromOtherProject;
    private String[] stepTitles;
    private String[] stepDescriptions;
    private nv step;
    private ProjectLibraryBean firebaseSettings;
    private b importFromOtherProjectAdapter;
    private String sc_id;
    private CardView openConsole;
    private TextView prev;
    private TextView title;
    private TextView next;
    private TextView stepTitle;
    private int stepNumber = STEP_1;
    private ArrayList<HashMap<String, Object>> otherProjects = new ArrayList<>();

    class a implements Comparator<HashMap<String, Object>> {
        public a() {
        }

        @Override
        public int compare(HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2) {
            return yB.c(hashMap, "sc_id").compareTo(yB.c(hashMap2, "sc_id")) * (-1);
        }
    }

    private void setStep(int stepNumber) {
        if (step != null) {
            step.a();
        }
        title.setText(stepNumber == STEP_3 ? xB.b().a(getApplicationContext(), R.string.common_word_review)
                : xB.b().a(getApplicationContext(), R.string.common_word_step, stepNumber + 1));
        next.setText(stepNumber == STEP_3 ? xB.b().a(getApplicationContext(), R.string.common_word_save)
                : xB.b().a(getApplicationContext(), R.string.common_word_next));
        back.setVisibility(stepNumber == STEP_1 ? View.VISIBLE : View.GONE);
        prev.setVisibility(stepNumber == STEP_1 ? View.GONE : View.VISIBLE);
        stepTitle.setText(stepTitles[stepNumber]);
        stepDescription.setText(stepDescriptions[stepNumber]);
        stepContainer.removeAllViews();
        if (stepNumber == STEP_1) {
            openConsole.setVisibility(View.VISIBLE);
            lv lvVar = new lv(this);
            stepContainer.addView(lvVar);
            lvVar.setData(firebaseSettings);
            step = lvVar;
        } else if (stepNumber == STEP_2) {
            openConsole.setVisibility(View.VISIBLE);
            mv mvVar = new mv(this);
            stepContainer.addView(mvVar);
            mvVar.setData(firebaseSettings);
            step = mvVar;
        } else if (stepNumber == STEP_3) {
            openConsole.setVisibility(View.GONE);
            kv kvVar = new kv(this);
            stepContainer.addView(kvVar);
            kvVar.setData(firebaseSettings);
            step = kvVar;
        }
        openDocumentation.setVisibility(step.getDocUrl().isEmpty() ? View.GONE : View.VISIBLE);
        importFromOtherProject.setVisibility(stepNumber > STEP_1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    private void importFromOtherProjectLoadProjects() {
        otherProjects = new ArrayList<>();
        for (HashMap<String, Object> project : lC.a()) {
            String projectSc_id = yB.c(project, "sc_id");
            if (!sc_id.equals(projectSc_id)) {
                iC iCVar = new iC(projectSc_id);
                iCVar.i();
                if (iCVar.d().useYn.equals("Y")) {
                    project.put("firebase_setting", iCVar.d().clone());
                    otherProjects.add(project);
                }
            }
        }
        if (otherProjects.size() > 0) {
            Collections.sort(otherProjects, new a());
        }
        importFromOtherProjectAdapter.notifyDataSetChanged();
    }

    private void onNextPressed() {
        if (step.isValid()) {
            step.a(firebaseSettings);
            if (stepNumber < STEP_3) {
                setStep(++stepNumber);
            } else {
                Intent intent = new Intent();
                intent.putExtra("firebase", firebaseSettings);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    private void openDocumentation() {
        String docUrl = step.getDocUrl();
        if (!docUrl.isEmpty()) {
            if (GB.h(getApplicationContext())) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("googlechrome://navigate?url=" + docUrl));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    showGetChromeDialog();
                }
            } else {
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
            }
        }
    }

    private void openFirebaseConsole() {
        if (GB.h(getApplicationContext())) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("googlechrome://navigate?url=https://console.firebase.google.com"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                showGetChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (stepNumber > STEP_1) {
            setStep(--stepNumber);
        } else {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_open_doc) {
                openDocumentation();
            } else if (id == R.id.cv_console) {
                openFirebaseConsole();
            } else if (id == R.id.img_backbtn || id == R.id.tv_prevbtn) {
                onBackPressed();
            } else if (id == R.id.tv_nextbtn) {
                onNextPressed();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        setContentView(R.layout.manage_library_firebase);
        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = getIntent().getStringExtra("sc_id");
        }
        String titleStep1 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_title);
        String titleStep2 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_title);
        String titleStep3 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_title);
        String descriptionStep1 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_desc);
        String descriptionStep2 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_desc);
        String descriptionStep3 = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_desc);
        stepTitles = new String[]{titleStep1, titleStep2, titleStep3};
        stepDescriptions = new String[]{descriptionStep1, descriptionStep2, descriptionStep3};
        openConsole = findViewById(R.id.cv_console);
        openConsole.setOnClickListener(this);
        TextView goToConsole = findViewById(R.id.tv_goto_console);
        goToConsole.setText(xB.b().a(getApplicationContext(), R.string.design_library_firebase_button_goto_firebase_console));
        prev = findViewById(R.id.tv_prevbtn);
        prev.setText(xB.b().a(getApplicationContext(), R.string.common_word_prev));
        prev.setOnClickListener(this);
        next = findViewById(R.id.tv_nextbtn);
        next.setText(xB.b().a(getApplicationContext(), R.string.common_word_next));
        next.setOnClickListener(this);
        title = findViewById(R.id.tv_toptitle);
        stepTitle = findViewById(R.id.tv_step_title);
        stepDescription = findViewById(R.id.tv_step_desc);
        ImageView icon = findViewById(R.id.icon);
        icon.setImageResource(R.drawable.widget_firebase);
        back = findViewById(R.id.img_backbtn);
        back.setOnClickListener(this);
        openDocumentation = findViewById(R.id.btn_open_doc);
        openDocumentation.setText(xB.b().a(getApplicationContext(), R.string.common_word_go_to_documentation));
        openDocumentation.setOnClickListener(this);
        importFromOtherProject = findViewById(R.id.btn_import);
        importFromOtherProject.setText(xB.b().a(getApplicationContext(), R.string.design_library_button_import_from_other_project));
        importFromOtherProject.setOnClickListener(v -> showImportFromOtherProjectDialog());
        stepContainer = findViewById(R.id.layout_container);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        firebaseSettings = getIntent().getParcelableExtra("firebase");
        setStep(stepNumber);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void showImportFromOtherProjectDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), R.string.design_library_title_select_project));
        dialog.a(R.drawable.widget_firebase);
        View a2 = wB.a(this, R.layout.manage_library_popup_project_selector);
        RecyclerView recyclerView = a2.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        importFromOtherProjectAdapter = new b();
        recyclerView.setAdapter(importFromOtherProjectAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        importFromOtherProjectLoadProjects();
        dialog.a(a2);
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            if (!mB.a()) {
                if (importFromOtherProjectAdapter.c >= 0) {
                    firebaseSettings = (ProjectLibraryBean) otherProjects.get(importFromOtherProjectAdapter.c).get("firebase_setting");
                    stepNumber = STEP_3;
                    setStep(stepNumber);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showGetChromeDialog() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(xB.b().a(getApplicationContext(), R.string.title_compatible_chrome_browser));
        dialog.a(xB.b().a(getApplicationContext(), R.string.message_compatible_chrome_brower));
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_ok), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public class b extends RecyclerView.Adapter<b.a> {
        public int c = -1;

        class a extends RecyclerView.ViewHolder {
            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            public a(@NonNull View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.project_layout);
                v = itemView.findViewById(R.id.project_name);
                u = itemView.findViewById(R.id.img_icon);
                w = itemView.findViewById(R.id.app_name);
                x = itemView.findViewById(R.id.package_name);
                y = itemView.findViewById(R.id.project_version);
                z = itemView.findViewById(R.id.img_selected);
                t.setOnClickListener(v -> {
                    if (!mB.a()) {
                        c = getLayoutPosition();
                        c(c);
                    }
                });
            }

            private void c(int i) {
                if (otherProjects.size() > 0) {
                    for (HashMap<String, Object> next : otherProjects) {
                        next.put("selected", false);
                    }
                    otherProjects.get(i).put("selected", true);
                    importFromOtherProjectAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull a holder, int position) {
            Uri fromFile;
            HashMap<String, Object> hashMap = otherProjects.get(position);
            String c = yB.c(hashMap, "sc_id");
            holder.u.setImageResource(R.drawable.default_icon);
            if (yB.a(hashMap, "custom_icon")) {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", new File(wq.e() + File.separator + c, "icon.png"));
                } else {
                    fromFile = Uri.fromFile(new File(wq.e() + File.separator + c, "icon.png"));
                }
                holder.u.setImageURI(fromFile);
            }
            holder.w.setText(yB.c(hashMap, "my_app_name"));
            holder.v.setText(yB.c(hashMap, "my_ws_name"));
            holder.x.setText(yB.c(hashMap, "my_sc_pkg_name"));
            holder.y.setText(String.format("%s(%s)", yB.c(hashMap, "sc_ver_name"), yB.c(hashMap, "sc_ver_code")));
            if (yB.a(hashMap, "selected")) {
                holder.z.setVisibility(View.VISIBLE);
            } else {
                holder.z.setVisibility(View.GONE);
            }
        }

        @Override
        @NonNull
        public a onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new a(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return otherProjects.size();
        }
    }
}
