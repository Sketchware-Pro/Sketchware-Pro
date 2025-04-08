package mod.hey.studios.project.proguard;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageProguardBinding;

public class ManageProguardActivity extends BaseAppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ProguardHandler pg;

    private ManageProguardBinding binding;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ig_toolbar_back) {
            finish();
        } else if (id == R.id.ln_pg_rules) {
            new ProguardRulesDialog(this, pg).show();
        } else if (id == R.id.ln_pg_fm) {
            fmDialog();
        }
    }

    private void fmDialog() {
        ManageLocalLibrary mll = new ManageLocalLibrary(getIntent().getStringExtra("sc_id"));

        final String[] libraries = new String[mll.list.size()];
        final boolean[] enabledLibraries = new boolean[mll.list.size()];

        for (int i = 0; i < mll.list.size(); i++) {
            HashMap<String, Object> current = mll.list.get(i);

            Object name = current.get("name");
            if (name instanceof String) {
                libraries[i] = (String) name;
                enabledLibraries[i] = pg.libIsProguardFMEnabled(libraries[i]);
            } else {
                libraries[i] = "(broken library configuration)";
                enabledLibraries[i] = false;
            }
        }

        MaterialAlertDialogBuilder bld = new MaterialAlertDialogBuilder(this);
        bld.setTitle("Select Local libraries");
        bld.setMultiChoiceItems(
                libraries,
                enabledLibraries,
                (dialog, which, isChecked) -> enabledLibraries[which] = isChecked);
        bld.setPositiveButton(
                R.string.common_word_save,
                (dialog, which) -> {
                    ArrayList<String> finalList = new ArrayList<>();

                    for (int i = 0; i < libraries.length; i++) {
                        if (enabledLibraries[i]) {
                            finalList.add(libraries[i]);
                        }
                    }

                    pg.setProguardFMLibs(finalList);
                });
        bld.setNegativeButton(R.string.common_word_cancel, null);
        bld.create().show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == binding.swPgEnabled.getId()) {
            pg.setProguardEnabled(isChecked);
        } else if (id == binding.swPgDebug.getId()) {
            pg.setDebugEnabled(isChecked);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageProguardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
        initializeLogic();
    }

    private void initialize() {
        binding.swPgEnabled.setOnCheckedChangeListener(this);
        binding.lnPgRules.setOnClickListener(this);
        binding.swPgDebug.setOnCheckedChangeListener(this);
        binding.lnPgFm.setOnClickListener(this);
    }

    private void initializeLogic() {
        _initToolbar();
        pg = new ProguardHandler(getIntent().getStringExtra("sc_id"));
        binding.swPgEnabled.setChecked(pg.isShrinkingEnabled());
        binding.swPgDebug.setChecked(pg.isDebugFilesEnabled());
    }

    private void _initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Code Shrinking Manager");
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}
