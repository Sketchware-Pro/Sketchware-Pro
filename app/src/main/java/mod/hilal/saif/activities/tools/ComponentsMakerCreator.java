package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hasrat.tools.ComponentHelper;
import mod.hey.studios.util.Helper;

public class ComponentsMakerCreator extends Activity implements View.OnClickListener {

    private MaterialButton cancel;
    private EditText coAddiVar;
    private EditText coBuildClass;
    private EditText coCustomImport;
    private EditText coDeAddiVar;
    private EditText coDesc;
    private EditText coIcon;
    private EditText coId;
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
        setContentView(2131427804);
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
        coName = findViewById(2131232489);
        coId = findViewById(2131232490);
        coTypeName = findViewById(2131232491);
        coIcon = findViewById(2131232492);
        selectIcon = findViewById(2131232493);
        selectIcon.setImageResource(2131165298);
        coDesc = findViewById(2131232494);
        coVarName = findViewById(2131232495);
        coTypeClass = findViewById(2131232496);
        coBuildClass = findViewById(2131232497);
        coUrl = findViewById(2131232498);
        coAddiVar = findViewById(2131232499);
        coDeAddiVar = findViewById(2131232500);
        coCustomImport = findViewById(2131232501);
        cancel = findViewById(2131232502);
        save = findViewById(2131232503);
    }

    private void initializeHelper() {
        this.coName.addTextChangedListener(new ComponentHelper(new EditText[]{this.coBuildClass, this.coVarName, this.coTypeName, this.coTypeClass}, this.coTypeClass));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 2131232457://back_icon
            case 2131232502://cancel
                finish();
                break;

            case 2131232503://save
                if (filledIn()) {
                    save();
                } else {
                    SketchwareUtil.toast("Some required fields are empty");
                }
                break;

            case 2131232493://selectIcon
                showIconSelectorDialog();
                break;
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
        if (isEdit) {
            ((TextView) findViewById(2131232458)).setText(na);
        } else {
            ((TextView) findViewById(2131232458)).setText("Create a new component");
        }
        ImageView back_icon = findViewById(2131232457);
        back_icon.setOnClickListener(this);
        Helper.applyRippleToToolbarView(back_icon);
    }
}