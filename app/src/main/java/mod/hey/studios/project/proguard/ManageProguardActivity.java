package mod.hey.studios.project.proguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;
import mod.khaled.librarymanager.ExternalLibraryManager;

//changed in 6.3.0
public class ManageProguardActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ProguardHandler pg;
    private Switch sw_pg_enabled;
    private Switch sw_pg_debug;

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
        ExternalLibraryManager mll = new ExternalLibraryManager(getIntent().getStringExtra("sc_id"));

        final String[] libraryNames = new String[mll.getLibrariesInProjectHashes().size()];
        final String[] libraryHashes = new String[mll.getLibrariesInProjectHashes().size()];
        final boolean[] enabledLibraries = new boolean[mll.getLibrariesInProjectHashes().size()];

        for (int i = 0; i < mll.getLibrariesInProjectHashes().size(); i++) {
            String libraryHash = mll.getLibrariesInProjectHashes().get(i);
            libraryNames[i] = mll.getLibraryNameFromHash(libraryHash);
            libraryHashes[i] = libraryHash;
            enabledLibraries[i] = pg.libIsProguardFMEnabled(libraryHash);
        }

        //FIXME
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setTitle("Select Local libraries");
        bld.setMultiChoiceItems(libraryNames, enabledLibraries, (dialog, which, isChecked) -> enabledLibraries[which] = isChecked);
        bld.setPositiveButton(R.string.common_word_save, (dialog, which) -> {

            ArrayList<String> finalList = new ArrayList<>();

            for (int i = 0; i < libraryNames.length; i++) {
                if (enabledLibraries[i]) {
                    finalList.add(libraryHashes[i]);
                }
            }

            pg.setProguardFMLibs(finalList);
            dialog.dismiss();
        });
        bld.setNegativeButton(R.string.common_word_cancel, null);
        bld.create().show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.sw_pg_enabled) {
            pg.setProguardEnabled(isChecked);
        } else if (id == R.id.sw_pg_debug) {
            pg.setDebugEnabled(isChecked);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_proguard);

        initialize();
        initializeLogic();
    }

    private void initialize() {
        sw_pg_enabled = findViewById(R.id.sw_pg_enabled);

        LinearLayout ln_pg_rules = findViewById(R.id.ln_pg_rules);
        sw_pg_debug = findViewById(R.id.sw_pg_debug);

        LinearLayout ln_pg_fm = findViewById(R.id.ln_pg_fm);

        sw_pg_enabled.setOnCheckedChangeListener(this);
        ln_pg_rules.setOnClickListener(this);

        sw_pg_debug.setOnCheckedChangeListener(this);
        ln_pg_fm.setOnClickListener(this);
    }

    private void initializeLogic() {
        _initToolbar();
        pg = new ProguardHandler(getIntent().getStringExtra("sc_id"));
        sw_pg_enabled.setChecked(pg.isProguardEnabled());
        sw_pg_debug.setChecked(pg.isDebugFilesEnabled());
    }

    private void _initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("ProGuard Manager");

        ImageView back = findViewById(R.id.ig_toolbar_back);
        back.setOnClickListener(this);
        Helper.applyRippleToToolbarView(back);
    }
}
