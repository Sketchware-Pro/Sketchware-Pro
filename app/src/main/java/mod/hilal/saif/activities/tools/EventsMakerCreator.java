package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.EventsCreatorBinding;

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
    private String event_name = "";
    private boolean isActivityEvent = false;
    private boolean isEdit = false;
    private String lisName;

    private EventsCreatorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EventsCreatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        binding.eventsCreatorEventname.setText(_name);
        binding.eventsCreatorVarname.setText(_var);
        binding.eventsCreatorIcon.setText(_icon);
        binding.eventsCreatorDesc.setText(_desc);
        binding.eventsCreatorParams.setText(_par);
        binding.eventsCreatorSpec.setText(_spec);
        binding.eventsCreatorCode.setText(_code);
    }

    private boolean filledIn() {
        if (isActivityEvent) {
            return !binding.eventsCreatorEventname.getText().toString().isEmpty()
                    && !binding.eventsCreatorSpec.getText().toString().isEmpty()
                    && !binding.eventsCreatorCode.getText().toString().isEmpty();
        } else {
            return !binding.eventsCreatorEventname.getText().toString().isEmpty()
                    && !binding.eventsCreatorVarname.getText().toString().isEmpty()
                    && !binding.eventsCreatorIcon.getText().toString().isEmpty()
                    && !binding.eventsCreatorSpec.getText().toString().isEmpty()
                    && !binding.eventsCreatorCode.getText().toString().isEmpty();
        }
    }

    private void getViewsById() {
        ((View) binding.eventsCreatorListenercode.getParent().getParent()).setVisibility(View.GONE);
        binding.eventsCreatorChooseicon.setImageResource(R.drawable.add_96_blue);
        binding.eventsCreatorCheckbox.setVisibility(View.GONE);
        if (isActivityEvent) {
            binding.eventsCreatorVarname.setText("");
            ((View) binding.eventsCreatorVarname.getParent().getParent()).setVisibility(View.GONE);
            binding.eventsCreatorIcon.setText("2131165298");
            ((View) binding.eventsCreatorIcon.getParent().getParent().getParent()).setVisibility(View.GONE);
        }
        Helper.addClearErrorOnTextChangeTextWatcher(binding.eventsCreatorIconTil);
    }

    private void setupViews() {
        binding.eventsCreatorCancel.setOnClickListener(Helper.getBackPressedClickListener(this));
        binding.eventsCreatorSave.setOnClickListener(v -> save());
        binding.eventsCreatorChooseicon.setOnClickListener(v -> showIconSelectorDialog());
    }

    private void showIconSelectorDialog() {
        new IconSelectorDialog(this, binding.eventsCreatorIcon).show();
    }

    private void save() {
        if (!filledIn()) {
            SketchwareUtil.toast("Some required fields are empty!");
            return;
        }
        if (!OldResourceIdMapper.isValidIconId(binding.eventsCreatorIcon.getText().toString())) {
            binding.eventsCreatorIconTil.setError("Invalid icon ID");
            binding.eventsCreatorIcon.requestFocus();
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
        hashMap.put("name", binding.eventsCreatorEventname.getText().toString());
        hashMap.put("var", binding.eventsCreatorVarname.getText().toString());
        if (isActivityEvent) {
            hashMap.put("listener", "");
        } else {
            hashMap.put("listener", lisName);
        }
        hashMap.put("icon", binding.eventsCreatorIcon.getText().toString());
        hashMap.put("description", binding.eventsCreatorDesc.getText().toString());
        hashMap.put("parameters", binding.eventsCreatorParams.getText().toString());
        hashMap.put("code", binding.eventsCreatorCode.getText().toString());
        hashMap.put("headerSpec", binding.eventsCreatorSpec.getText().toString());
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
        if (isEdit) {
            binding.txToolbarTitle.setText(event_name);
        } else if (isActivityEvent) {
            binding.txToolbarTitle.setText("Create a new Activity event");
        } else {
            binding.txToolbarTitle.setText(lisName + "Create a new event");
        }
        binding.igToolbarBack.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(binding.igToolbarBack);
    }
}
