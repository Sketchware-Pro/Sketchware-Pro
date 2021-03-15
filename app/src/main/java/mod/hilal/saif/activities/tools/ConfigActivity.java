package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.bB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.FileUtil;

public class ConfigActivity extends Activity {

    LinearLayout opt_legacy_ce;
    Switch sw_legacy_ce;
    private TextView a1_sub;
    private Switch a1_sw;
    private Switch a2_sw;
    private ImageView back_icon;
    private LinearLayout linear1;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private LinearLayout option1;
    private LinearLayout option2;
    private TextView page_title;
    private HashMap<String, Object> setting_map = new HashMap<>();
    private ArrayList<String> settings = new ArrayList<>();
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private LinearLayout toolbar;

    public static String getBackupPath() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json");
        if (FileUtil.isExistFile(concat)) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP);
            if (hashMap.containsKey("backup-dir")) {
                return (String) hashMap.get("backup-dir");
            }
        }
        return "/.sketchware/backups/";
    }

    public static String getFullBackupPath() {
        String externalStorageDir = FileUtil.getExternalStorageDir();
        String concat = externalStorageDir.concat("/.sketchware/data/settings.json");
        if (FileUtil.isExistFile(concat)) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP);
            if (hashMap.containsKey("backup-dir")) {
                return externalStorageDir.concat((String) hashMap.get("backup-dir"));
            }
        }
        return externalStorageDir.concat("/.sketchware/backups/");
    }

    public static boolean isLegacyCeEnabled() {
        if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"))) {
            return false;
        }
        HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
        if (hashMap.containsKey("legacy-ce")) {
            return (Boolean) hashMap.get("legacy-ce");
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427815);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        opt_legacy_ce = (LinearLayout) findViewById(2131231912);
        sw_legacy_ce = (Switch) findViewById(2131232306);
        opt_legacy_ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw_legacy_ce.setChecked(!sw_legacy_ce.isChecked());
            }
        });
        sw_legacy_ce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting_map.put("legacy-ce", isChecked);
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
            }
        });
        toolbar = (LinearLayout) findViewById(2131231847);
        option1 = (LinearLayout) findViewById(2131232583);
        option2 = (LinearLayout) findViewById(2131232584);
        back_icon = (ImageView) findViewById(2131232546);
        page_title = (TextView) findViewById(2131232547);
        a1_sw = (Switch) findViewById(2131232587);
        a2_sw = (Switch) findViewById(2131232588);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1_sw.setChecked(!a1_sw.isChecked());
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a2_sw.setChecked(!a2_sw.isChecked());
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        a1_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting_map.put("built-in-blocks", isChecked);
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
            }
        });
        a2_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting_map.put("always-show-blocks", isChecked);
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
            }
        });
        View findViewById = findViewById(2131232622);
        applyDesign(findViewById);
        findViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConfigActivity configActivity = ConfigActivity.this;
                final EditText editText = new EditText(configActivity);
                editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setTextSize(14.0f);
                editText.setTextColor(-16777216);
                editText.setText(ConfigActivity.getBackupPath());
                AlertDialog create = new AlertDialog.Builder(configActivity)
                        .setTitle("Backup directory inside Internal storage, e.g sketchware/backups")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setting_map.put("backup-dir", editText.getText().toString());
                                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
                                bB.a(configActivity, "Saved", 0).show();
                            }
                        }).create();
                create.setView(editText);
                create.show();
            }
        });
    }

    private void initializeLogic() {
        applyDesign(opt_legacy_ce);
        applyDesign(option1);
        applyDesign(option2);
        if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"))) {
            setting_map = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json")), Helper.TYPE_MAP);
            if (!setting_map.containsKey("built-in-blocks") || !setting_map.containsKey("always-show-blocks")) {
                setting_map.put("backup-dir", "");
                setting_map.put("always-show-blocks", Boolean.FALSE);
                setting_map.put("built-in-blocks", Boolean.FALSE);
                setting_map.put("legacy-ce", Boolean.FALSE);
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
                return;
            }
            a1_sw.setChecked((Boolean) setting_map.get("built-in-blocks"));
            a2_sw.setChecked((Boolean) setting_map.get("always-show-blocks"));
            if (setting_map.containsKey("legacy-ce")) {
                sw_legacy_ce.setChecked((Boolean) setting_map.get("legacy-ce"));
                return;
            }
            return;
        }
        setting_map.put("backup-dir", "");
        setting_map.put("always-show-blocks", Boolean.FALSE);
        setting_map.put("built-in-blocks", Boolean.FALSE);
        setting_map.put("legacy-ce", Boolean.FALSE);
        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/settings.json"), new Gson().toJson(setting_map));
    }

    private void applyDesign(View view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#20ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#008dcd")}), gradientDrawable, null);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }
}