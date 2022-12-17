package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
import mod.hasrat.tools.ComponentHelper;
import mod.hey.studios.util.Helper;
import mod.jbk.util.OldResourceIdMapper;

public class ComponentsMakerCreator extends Activity implements View.OnClickListener {

    private MaterialButton cancel;
    private EditText coAddiVar;
    private EditText coBuildClass;
    private EditText coCustomImport;
    private EditText coDeAddiVar;
    private EditText coDesc;
    private EditText coIcon;
    private EditText coId;
    private TextInputLayout coIconTil;
    private EditText coName;
    private EditText coTypeClass;
    private EditText coTypeName;
    private EditText coUrl;
    private EditText coVarName;
    private boolean isEdit = false;
    private String na = "";
    private int pos = 0;
    private MaterialButton save;
    private ImageView selectIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.components_creator);
        if (getIntent().hasExtra("pos")) {
            isEdit = true;
            pos = Integer.parseInt(getIntent().getStringExtra("pos"));
            na = getIntent().getStringExtra("name");
        }
        setToolbar();
        getViewsById();
        setupViews();
        if (isEdit) {
            fillUp();
        } else {
            initializeHelper();
        }
    }

    private void fillUp() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/component.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            coName.setText((String) arrayList.get(pos).get("name"));
            coId.setText((String) arrayList.get(pos).get("id"));
            coTypeName.setText((String) arrayList.get(pos).get("typeName"));
            coIcon.setText((String) arrayList.get(pos).get("icon"));
            coDesc.setText((String) arrayList.get(pos).get("description"));
            coVarName.setText((String) arrayList.get(pos).get("varName"));
            coTypeClass.setText((String) arrayList.get(pos).get("class"));
            coBuildClass.setText((String) arrayList.get(pos).get("buildClass"));
            coUrl.setText((String) arrayList.get(pos).get("url"));
            coAddiVar.setText((String) arrayList.get(pos).get("additionalVar"));
            coDeAddiVar.setText((String) arrayList.get(pos).get("defineAdditionalVar"));
            coCustomImport.setText((String) arrayList.get(pos).get("imports"));
        }
    }

    private void getViewsById() {
        coName = findViewById(R.id.components_creator_name);
        coId = findViewById(R.id.components_creator_id);
        coIconTil = findViewById(R.id.components_creator_icon_til);
        coTypeName = findViewById(R.id.components_creator_typename);
        coIcon = findViewById(R.id.components_creator_icon);
        selectIcon = findViewById(R.id.components_creator_iconselector);
        selectIcon.setImageResource(R.drawable.add_96_blue);
        coDesc = findViewById(R.id.components_creator_description);
        coVarName = findViewById(R.id.components_creator_varname);
        coTypeClass = findViewById(R.id.components_creator_typeclass);
        coBuildClass = findViewById(R.id.components_creator_buildclass);
        coUrl = findViewById(R.id.components_creator_url);
        coAddiVar = findViewById(R.id.components_creator_addirional);
        coDeAddiVar = findViewById(R.id.components_creator_deadditional);
        coCustomImport = findViewById(R.id.components_creator_custom);
        cancel = findViewById(R.id.components_creator_cancel);
        save = findViewById(R.id.components_creator_save);
        Helper.addClearErrorOnTextChangeTextWatcher(coIconTil);
    }

    private void initializeHelper() {
        coName.addTextChangedListener(new ComponentHelper(new EditText[]{coBuildClass, coVarName, coTypeName, coTypeClass}, coTypeClass));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ig_toolbar_back || id == R.id.components_creator_cancel) {
            finish();
        } else if (id == R.id.components_creator_save) {
            if (filledIn()) {
                if (OldResourceIdMapper.isValidIconId(coIcon.getText().toString())) {
                    save();
                } else {
                    coIconTil.setError("Invalid icon ID");
                    coIcon.requestFocus();
                }
            } else {
                SketchwareUtil.toast("Some required fields are empty");
            }
        } else if (id == R.id.components_creator_iconselector) {
            showIconSelectorDialog();
        }
    }

    private void setupViews() {
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        selectIcon.setOnClickListener(this);
    }

    private void showIconSelectorDialog() {
        new IconSelectorDialog(this, coIcon).show();
    }

    private boolean filledIn() {
        return !coName.getText().toString().equals("")
                && !coId.getText().toString().equals("")
                && !coTypeName.getText().toString().equals("")
                && !coIcon.getText().toString().equals("")
                && !coVarName.getText().toString().equals("")
                && !coTypeClass.getText().toString().equals("")
                && !coBuildClass.getText().toString().equals("");
    }

    private void save() {
        ArrayList<HashMap<String, Object>> arrayList;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/component.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
        } else {
            arrayList = new ArrayList<>();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isEdit) {
            hashMap = arrayList.get(pos);
        }
        hashMap.put("name", coName.getText().toString());
        hashMap.put("id", coId.getText().toString());
        hashMap.put("typeName", coTypeName.getText().toString());
        hashMap.put("icon", coIcon.getText().toString());
        hashMap.put("description", coDesc.getText().toString());
        hashMap.put("varName", coVarName.getText().toString());
        hashMap.put("class", coTypeClass.getText().toString());
        hashMap.put("buildClass", coBuildClass.getText().toString());
        hashMap.put("url", coUrl.getText().toString());
        hashMap.put("additionalVar", coAddiVar.getText().toString());
        hashMap.put("defineAdditionalVar", coDeAddiVar.getText().toString());
        hashMap.put("imports", coCustomImport.getText().toString());
        if (!isEdit) {
            arrayList.add(hashMap);
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void setToolbar() {
        TextView title = findViewById(R.id.tx_toolbar_title);
        if (isEdit) {
            title.setText(na);
        } else {
            title.setText("Create a new component");
        }
        ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        back_icon.setOnClickListener(this);
        Helper.applyRippleToToolbarView(back_icon);
    }
}