package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.GB;
import a.a.a.bB;
import a.a.a.iC;
import a.a.a.kv;
import a.a.a.lv;
import a.a.a.mB;
import a.a.a.mv;
import a.a.a.nv;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import mod.jbk.editor.manage.library.LibrarySettingsImporter;
import pro.sketchware.R;

public class FirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private static final int STEP_1 = 0;
    private static final int STEP_2 = 1;
    private static final int STEP_3 = 2;
    private String[] stepTitles;
    private String[] stepDescriptions;
    private nv step;
    private ProjectLibraryBean firebaseSettings;
    private String sc_id;
    private int stepNumber = STEP_1;

    private Toolbar toolbar;
    private ImageView icon;
    private Button btn_import;
    private Button btn_open_doc;
    private TextView tv_step_desc;
    private TextView tv_step_title;
    private TextView tv_goto_console;
    private LinearLayout ll_goto_console;
    private LinearLayout layout_container;
    private LinearLayout layout_step_guide;
    private com.google.android.material.card.MaterialCardView cv_console;

    private void setStep(int stepNumber) {
        if (step != null) {
            step.a();
        }

        getSupportActionBar().setSubtitle(stepNumber == STEP_3 ? getTranslatedString(R.string.common_word_review) : xB.b().a(getApplicationContext(), R.string.common_word_step, stepNumber + 1));
        tv_step_title.setText(stepTitles[stepNumber]);
        tv_step_desc.setText(stepDescriptions[stepNumber]);

        layout_container.removeAllViews();
        if (stepNumber == STEP_1) {
            cv_console.setVisibility(View.VISIBLE);
            lv lvVar = new lv(this);
            layout_container.addView(lvVar);
            lvVar.setData(firebaseSettings);
            step = lvVar;
        } else if (stepNumber == STEP_2) {
            cv_console.setVisibility(View.VISIBLE);
            mv mvVar = new mv(this);
            layout_container.addView(mvVar);
            mvVar.setData(firebaseSettings);
            step = mvVar;
        } else if (stepNumber == STEP_3) {
            cv_console.setVisibility(View.GONE);
            kv kvVar = new kv(this);
            layout_container.addView(kvVar);
            kvVar.setData(firebaseSettings);
            step = kvVar;
        }
        cv_console.setVisibility(step.getDocUrl().isEmpty() ? View.GONE : View.VISIBLE);
        btn_import.setVisibility(stepNumber > STEP_1 ? View.GONE : View.VISIBLE);
        onCreateOptionsMenu(toolbar.getMenu());
    }

    @Override
    public void finish() {
        super.finish();
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
                bB.a(getApplicationContext(), getTranslatedString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
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
            bB.a(getApplicationContext(), getTranslatedString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
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
        setContentView(R.layout.manage_library_firebase);

        icon = findViewById(R.id.icon);
        cv_console = findViewById(R.id.cv_console);
        btn_import = findViewById(R.id.btn_import);
        tv_step_desc = findViewById(R.id.tv_step_desc);
        btn_open_doc = findViewById(R.id.btn_open_doc);
        tv_step_title = findViewById(R.id.tv_step_title);
        ll_goto_console = findViewById(R.id.ll_goto_console);
        tv_goto_console = findViewById(R.id.tv_goto_console);
        layout_container = findViewById(R.id.layout_container);
        layout_step_guide = findViewById(R.id.layout_step_guide);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.change_firebase_config_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = getIntent().getStringExtra("sc_id");
        }
        String titleStep1 = getTranslatedString(R.string.design_library_firebase_setting_step1_title);
        String titleStep2 = getTranslatedString(R.string.design_library_firebase_setting_step2_title);
        String titleStep3 = getTranslatedString(R.string.design_library_firebase_setting_step3_title);
        String descriptionStep1 = getTranslatedString(R.string.design_library_firebase_setting_step1_desc);
        String descriptionStep2 = getTranslatedString(R.string.design_library_firebase_setting_step2_desc);
        String descriptionStep3 = getTranslatedString(R.string.design_library_firebase_setting_step3_desc);
        stepTitles = new String[]{titleStep1, titleStep2, titleStep3};
        stepDescriptions = new String[]{descriptionStep1, descriptionStep2, descriptionStep3};

        cv_console.setOnClickListener(this);
        tv_goto_console.setText(getTranslatedString(R.string.design_library_firebase_button_goto_firebase_console));
        icon.setImageResource(R.drawable.widget_firebase);

        btn_open_doc.setText(getTranslatedString(R.string.common_word_go_to_documentation));
        btn_open_doc.setOnClickListener(this);

        btn_import.setText(getTranslatedString(R.string.design_library_button_import_from_other_project));
        btn_import.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (stepNumber == STEP_3) {
            menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Save").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_save)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Next").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_next)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        switch (title) {
            case "Save", "Next":
                onNextPressed();
                break;

            default:
                return false;
        }
        return super.onOptionsItemSelected(menuItem);
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
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setTitle(getTranslatedString(R.string.title_compatible_chrome_browser));
        dialog.setMessage(getTranslatedString(R.string.message_compatible_chrome_brower));
        dialog.setPositiveButton(getTranslatedString(R.string.common_word_ok), (v, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(getTranslatedString(R.string.common_word_cancel), null);
        dialog.show();
    }
}
