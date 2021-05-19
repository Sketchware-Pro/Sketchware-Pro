package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.FileUtil;

public class EventsMakerCreator extends Activity {

    private String _code;
    private String _desc;
    private String _icon;
    private String _lis;
    private String _name;
    private String _par;
    private int _pos;
    private String _spec;
    private String _var;
    private MaterialButton cancel;
    private CheckBox check;
    private EditText eventCode;
    private EditText eventDesc;
    private EditText eventIcon;
    private EditText eventListener;
    private EditText eventName;
    private EditText eventParams;
    private EditText eventSpec;
    private EditText eventVar;
    private String event_name = "";
    private LinearLayout hide;
    private boolean isActivityEvent = false;
    private boolean isEdit = false;
    private String lisName;
    private EditText listenerCode;
    private EditText listenerImport;
    private EditText listenerName;
    private MaterialButton save;
    private ScrollView scroll;
    private ImageView selectIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427803);
        if (getIntent().hasExtra("lis_name")) {
            lisName = getIntent().getStringExtra("lis_name");
            isActivityEvent = lisName.equals("");
        }
        if (getIntent().hasExtra("event")) {
            event_name = getIntent().getStringExtra("event");
            isEdit = true;
            _pos = Integer.parseInt(getIntent().getStringExtra("_pos"));
            _name = getIntent().getStringExtra("_name");
            _var = getIntent().getStringExtra("_var");
            _lis = getIntent().getStringExtra("_lis");
            _icon = getIntent().getStringExtra("_icon");
            _desc = getIntent().getStringExtra("_desc");
            _par = getIntent().getStringExtra("_par");
            _spec = getIntent().getStringExtra("_spec");
            _code = getIntent().getStringExtra("_code");
        }
        setToolbar();
        getViewsById();
        setupViews();
        if (isEdit) {
            fillUp();
        }
    }

    private void fillUp() {
        eventName.setText(_name);
        eventVar.setText(_var);
        eventIcon.setText(_icon);
        eventDesc.setText(_desc);
        eventParams.setText(_par);
        eventSpec.setText(_spec);
        eventCode.setText(_code);
    }

    private boolean filledIn() {
        return !eventName.getText().toString().equals("")
                && !eventVar.getText().toString().equals("")
                && !eventIcon.getText().toString().equals("")
                && !eventSpec.getText().toString().equals("")
                && !eventCode.getText().toString().equals("");
    }

    private void getViewsById() {
        scroll = findViewById(2131232472);
        eventName = findViewById(2131232473);
        eventVar = findViewById(2131232474);
        eventListener = findViewById(2131232475);
        ((View) eventListener.getParent().getParent()).setVisibility(View.GONE);
        eventIcon = findViewById(2131232476);
        eventDesc = findViewById(2131232478);
        eventParams = findViewById(2131232479);
        eventSpec = findViewById(2131232480);
        eventCode = findViewById(2131232481);
        selectIcon = findViewById(2131232477);
        selectIcon.setImageResource(2131165298);
        check = findViewById(2131232482);
        check.setVisibility(View.GONE);
        hide = findViewById(2131232483);
        listenerName = findViewById(2131232484);
        listenerCode = findViewById(2131232485);
        listenerImport = findViewById(2131232486);
        cancel = findViewById(2131232487);
        save = findViewById(2131232488);
        if (isActivityEvent) {
            eventVar.setText("");
            ((View) eventVar.getParent().getParent()).setVisibility(View.GONE);
            eventIcon.setText("2131165298");
            ((View) eventIcon.getParent().getParent().getParent()).setVisibility(View.GONE);
        }
    }

    private void setupViews() {
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        selectIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showIconSelectorDialog();
            }
        });
    }

    private void showIconSelectorDialog() {
        new IconSelectorDialog(this, eventIcon).show();
    }

    private void save() {
        if (!filledIn()) {
            SketchwareUtil.toast("Some required fields are empty!");
            return;
        }
        ArrayList<HashMap<String, Object>> arrayList;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
        } else {
            arrayList = new ArrayList<>();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isEdit) {
            hashMap = arrayList.get(figureP(_name));
        }
        hashMap.put("name", eventName.getText().toString());
        hashMap.put("var", eventVar.getText().toString());
        if (isActivityEvent) {
            hashMap.put("listener", "");
        } else {
            hashMap.put("listener", lisName);
        }
        hashMap.put("icon", eventIcon.getText().toString());
        hashMap.put("description", eventDesc.getText().toString());
        hashMap.put("parameters", eventParams.getText().toString());
        hashMap.put("code", eventCode.getText().toString());
        hashMap.put("headerSpec", eventSpec.getText().toString());
        if (!isEdit) {
            arrayList.add(hashMap);
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private int figureP(String str) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get("name").equals(str)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void setToolbar() {
        if (isEdit) {
            ((TextView) findViewById(2131232458)).setText(event_name);
        } else if (isActivityEvent) {
            ((TextView) findViewById(2131232458)).setText("Create a new Activity event");
        } else {
            ((TextView) findViewById(2131232458)).setText(lisName + "Create a new event");
        }
        ImageView back_icon = findViewById(2131232457);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
    }
}