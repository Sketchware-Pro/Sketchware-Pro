package mod.hey.studios.project.stringfog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class ManageStringfogActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private StringfogHandler stringfogHandler;
    private Switch sw_pg_enabled;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        stringfogHandler.setStringfogEnabled(isChecked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	mod.tsd.ui.AppThemeApply.setUpTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_stringfog);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        sw_pg_enabled = findViewById(R.id.sw_pg_enabled);
        sw_pg_enabled.setOnCheckedChangeListener(this);
    }

    private void initializeLogic() {
        initToolbar();

        stringfogHandler = new StringfogHandler(getIntent().getStringExtra("sc_id"));
        sw_pg_enabled.setChecked(stringfogHandler.isStringfogEnabled());
    }

    private void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("StringFog Manager");

        ImageView ig_toolbar_back = findViewById(R.id.ig_toolbar_back);
        ig_toolbar_back.setOnClickListener(Helper.getBackPressedClickListener(this));

        Helper.applyRippleToToolbarView(ig_toolbar_back,this);
    }
}