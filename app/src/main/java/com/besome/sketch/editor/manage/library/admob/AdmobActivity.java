package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManageLibraryAdmobBinding;

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
    private String[] stepTitles;
    private String[] stepDescriptions;
    private int stepPosition = 0;
    private Uu step;
    private ProjectLibraryBean adMobSettings;
    private String sc_id;
    private ManageLibraryAdmobBinding binding;

    private void showStep(int position) {
        if (position == 4) {
            binding.tvToptitle.setText(Helper.getResString(R.string.common_word_review));
            binding.tvNextbtn.setText(Helper.getResString(R.string.common_word_save));
        } else {
            binding.tvToptitle.setText(xB.b().a(this, R.string.common_word_step, position + 1));
            binding.tvNextbtn.setText(Helper.getResString(R.string.common_word_next));
        }

        if (position == 0) {
            binding.imgBackbtn.setVisibility(View.VISIBLE);
            binding.tvPrevbtn.setVisibility(View.GONE);
        } else {
            binding.imgBackbtn.setVisibility(View.GONE);
            binding.tvPrevbtn.setVisibility(View.VISIBLE);
        }

        binding.tvStepTitle.setText(stepTitles[position]);
        binding.tvStepDesc.setText(stepDescriptions[position]);
        binding.layoutContainer.removeAllViews();
        switch (position) {
            case 0:
                AddAppIdStepView addAppIdStep = new AddAppIdStepView(this);
                binding.layoutContainer.addView(addAppIdStep);
                addAppIdStep.setData(adMobSettings);
                step = addAppIdStep;
                break;

            case 1:
                binding.cvConsole.setVisibility(View.GONE);
                AddAdUnitStepView addAdUnitsStep = new AddAdUnitStepView(this);
                binding.layoutContainer.addView(addAdUnitsStep);
                addAdUnitsStep.setData(adMobSettings);
                step = addAdUnitsStep;
                break;

            case 2:
                binding.cvConsole.setVisibility(View.GONE);
                AssignAdUnitStepView assignAdUnitsStep = new AssignAdUnitStepView(this);
                binding.layoutContainer.addView(assignAdUnitsStep);
                assignAdUnitsStep.setData(adMobSettings);
                step = assignAdUnitsStep;
                break;

            case 3:
                binding.cvConsole.setVisibility(View.GONE);
                TestDevicesStepView testDevicesStep = new TestDevicesStepView(this);
                binding.layoutContainer.addView(testDevicesStep);
                testDevicesStep.setData(adMobSettings);
                step = testDevicesStep;
                break;

            case 4:
                binding.cvConsole.setVisibility(View.GONE);
                ReviewStepView reviewStep = new ReviewStepView(this);
                binding.layoutContainer.addView(reviewStep);
                reviewStep.setData(adMobSettings);
                step = reviewStep;
                break;

            default:
        }

        if (step.getDocUrl().isEmpty()) {
            binding.btnOpenDoc.setVisibility(View.GONE);
        } else {
            binding.btnOpenDoc.setVisibility(View.VISIBLE);
        }

        if (position > 0) {
            binding.btnImport.setVisibility(View.GONE);
        } else {
            binding.btnImport.setVisibility(View.VISIBLE);
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

        if (id == binding.btnOpenDoc.getId()) {
            goToDocumentation();
        } else if (id == binding.cvConsole.getId()) {
            goToConsole();
        } else if (id == binding.tvNextbtn.getId()) {
            nextStep();
        } else if (id == binding.tvPrevbtn.getId()) {
            onBackPressed();
        } else if (id == binding.btnImport.getId()) {
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
        binding = ManageLibraryAdmobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        binding.cvConsole.setOnClickListener(this);
        binding.tvGotoConsole.setText(Helper.getResString(R.string.design_library_admob_button_goto_setting));
        binding.tvPrevbtn.setText(Helper.getResString(R.string.common_word_prev));
        binding.tvPrevbtn.setOnClickListener(this);
        binding.icon.setImageResource(R.drawable.widget_admob);
        binding.tvNextbtn.setText(Helper.getResString(R.string.common_word_next));
        binding.tvNextbtn.setOnClickListener(this);
        binding.imgBackbtn.setOnClickListener(Helper.getBackPressedClickListener(this));
        binding.btnOpenDoc.setText(Helper.getResString(R.string.common_word_go_to_documentation));
        binding.btnOpenDoc.setOnClickListener(this);
        binding.btnImport.setText(Helper.getResString(R.string.design_library_button_import_from_other_project));
        binding.btnImport.setOnClickListener(this);
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
}
