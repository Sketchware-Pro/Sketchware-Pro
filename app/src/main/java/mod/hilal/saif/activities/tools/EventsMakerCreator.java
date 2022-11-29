package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.OldResourceIdMapper;

public class EventsMakerCreator extends Activity {

    private String _code;
    private String _desc;
    private String _icon;
    private String _name;
    private String _par;
    private String _spec;
    private String _var;
    private MaterialButton cancel;
    private EditText eventCode;
    private EditText eventDesc;
    private EditText eventIcon;
    private TextInputLayout eventIconTil;
    private EditText eventName;
    private EditText eventParams;
    private EditText eventSpec;
    private EditText eventVar;
    private String event_name = "";
    private boolean isActivityEvent = false;
    private boolean isEdit = false;
    private String lisName;
    private MaterialButton save;
    private ImageView selectIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_creator);
        if (getIntent().hasExtra("lis_name")) {
            lisName = getIntent().getStringExtra("lis_name");
            isActivityEvent = lisName.equals("");
        }
        if (getIntent().hasExtra("event")) {
            event_name = getIntent().getStringExtra("event");
            isEdit = true;
            _name = getIntent().getStringExtra("_name");
            _var = getIntent().getStringExtra("_var");
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
        if (isActivityEvent) {
            return !eventName.getText().toString().isEmpty()
                    && !eventSpec.getText().toString().isEmpty()
                    && !eventCode.getText().toString().isEmpty();
        } else {
            return !eventName.getText().toString().isEmpty()
                    && !eventVar.getText().toString().isEmpty()
                    && !eventIcon.getText().toString().isEmpty()
                    && !eventSpec.getText().toString().isEmpty()
                    && !eventCode.getText().toString().isEmpty();
        }
    }

    private void getViewsById() {
        eventName = findViewById(R.id.events_creator_eventname);
        eventVar = findViewById(R.id.events_creator_varname);
        EditText eventListener = findViewById(R.id.events_creator_listenername);
        ((View) eventListener.getParent().getParent()).setVisibility(View.GONE);
        eventIcon = findViewById(R.id.events_creator_icon);
        eventIconTil = findViewById(R.id.events_creator_icon_til);
        eventDesc = findViewById(R.id.events_creator_desc);
        eventParams = findViewById(R.id.events_creator_params);
        eventSpec = findViewById(R.id.events_creator_spec);
        eventCode = findViewById(R.id.events_creator_code);
        selectIcon = findViewById(R.id.events_creator_chooseicon);
        selectIcon.setImageResource(R.drawable.add_96_blue);
        CheckBox check = findViewById(R.id.events_creator_checkbox);
        check.setVisibility(View.GONE);
        cancel = findViewById(R.id.events_creator_cancel);
        save = findViewById(R.id.events_creator_save);
        if (isActivityEvent) {
            eventVar.setText("");
            ((View) eventVar.getParent().getParent()).setVisibility(View.GONE);
            eventIcon.setText("2131165298");
            ((View) eventIcon.getParent().getParent().getParent()).setVisibility(View.GONE);
        }
        Helper.addClearErrorOnTextChangeTextWatcher(eventIconTil);
    }

    private void setupViews() {
        cancel.setOnClickListener(Helper.getBackPressedClickListener(this));
        save.setOnClickListener(v -> save());
        selectIcon.setOnClickListener(v -> showIconSelectorDialog());
    }

    private void showIconSelectorDialog() {
        new IconSelectorDialog(this, eventIcon).show();
    }

    private void save() {
        if (!filledIn()) {
            SketchwareUtil.toast("Some required fields are empty!");
            return;
        }
        if (!OldResourceIdMapper.isValidIconId(eventIcon.getText().toString())) {
            eventIconTil.setError("Invalid icon ID");
            eventIcon.requestFocus();
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
                if (str.equals(arrayList.get(i).get("name"))) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void setToolbar() {
        TextView tx_toolbar_title = findViewById(R.id.tx_toolbar_title);
        if (isEdit) {
            tx_toolbar_title.setText(event_name);
        } else if (isActivityEvent) {
            tx_toolbar_title.setText("Create a new Activity event");
        } else {
            tx_toolbar_title.setText(lisName + "Create a new event");
        }
        ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);
    }
}
