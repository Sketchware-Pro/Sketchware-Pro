package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

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

import a.a.a.GB;
import a.a.a.Uu;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iC;
import a.a.a.mB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import mod.jbk.editor.manage.library.LibrarySettingsImporter;

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
    private String sc_id;
    private CardView goToConsole;
    private TextView previousStep;
    private TextView topTitle;

    private void showStep(int position) {
        if (position == 4) {
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
                AddAppIdStepView addAppIdStep = new AddAppIdStepView(this);
                stepContainer.addView(addAppIdStep);
                addAppIdStep.setData(adMobSettings);
                step = addAppIdStep;
                break;

            case 1:
                goToConsole.setVisibility(View.GONE);
                AddAdUnitStepView addAdUnitsStep = new AddAdUnitStepView(this);
                stepContainer.addView(addAdUnitsStep);
                addAdUnitsStep.setData(adMobSettings);
                step = addAdUnitsStep;
                break;

            case 2:
                goToConsole.setVisibility(View.GONE);
                AssignAdUnitStepView assignAdUnitsStep = new AssignAdUnitStepView(this);
                stepContainer.addView(assignAdUnitsStep);
                assignAdUnitsStep.setData(adMobSettings);
                step = assignAdUnitsStep;
                break;

            case 3:
                goToConsole.setVisibility(View.GONE);
                TestDevicesStepView testDevicesStep = new TestDevicesStepView(this);
                stepContainer.addView(testDevicesStep);
                testDevicesStep.setData(adMobSettings);
                step = testDevicesStep;
                break;

            case 4:
                goToConsole.setVisibility(View.GONE);
                ReviewStepView reviewStep = new ReviewStepView(this);
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

    private void nextStep() {
        if (step.isValid()) {
            step.a(adMobSettings);
            if (stepPosition < 4) {
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
        } else if (id == R.id.btn_import) {
            LibrarySettingsImporter importer = new LibrarySettingsImporter(sc_id, iC::b);
            importer.addOnProjectSelectedListener(settings -> {
                adMobSettings = settings;
                stepPosition = 0;
                String app_id;
                if (!(settings == null || (app_id = settings.appId) == null || isEmpty(app_id))) {
                    stepPosition = 4;
                }
                showStep(stepPosition);
            });
            importer.showDialog(this);
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
                Helper.getResString(R.string.design_library_admob_setting_step4_title),
                Helper.getResString(R.string.design_library_admob_setting_step5_title)
        };
        stepDescriptions = new String[]{
                Helper.getResString(R.string.design_library_admob_setting_step1_desc),
                Helper.getResString(R.string.design_library_admob_setting_step2_desc),
                Helper.getResString(R.string.design_library_admob_setting_step3_desc),
                Helper.getResString(R.string.design_library_admob_setting_step4_desc),
                Helper.getResString(R.string.design_library_admob_setting_step5_desc)
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
        importFromOtherProject.setOnClickListener(this);
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

    private void showGoogleChromeNotice() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), (d, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                d.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
    }
}
