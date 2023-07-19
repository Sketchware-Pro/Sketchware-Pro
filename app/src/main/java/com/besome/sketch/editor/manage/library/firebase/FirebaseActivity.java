package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import java.util.Comparator;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iC;
import a.a.a.kv;
import a.a.a.lv;
import a.a.a.mB;
import a.a.a.mv;
import a.a.a.nv;
import a.a.a.xB;
import a.a.a.yB;
import mod.hey.studios.util.Helper;
import mod.jbk.editor.manage.library.LibrarySettingsImporter;

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
    private String sc_id;
    private CardView openConsole;
    private TextView prev;
    private TextView title;
    private TextView next;
    private TextView stepTitle;
    private int stepNumber = STEP_1;

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
            } else if (id == R.id.btn_import) {
                LibrarySettingsImporter importer = new LibrarySettingsImporter(sc_id, iC::d);
                importer.addOnProjectSelectedListener(settings -> {
                    firebaseSettings = settings;
                    stepNumber = STEP_3;
                    setStep(stepNumber);
                });
                importer.showDialog(this);
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
        importFromOtherProject.setOnClickListener(this);
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
}
