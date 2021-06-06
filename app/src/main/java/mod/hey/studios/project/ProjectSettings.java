package mod.hey.studios.project;

import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ProjectSettings {

    private final String path;
    public String sc_id;
    private HashMap<String, String> hashmap;

    public ProjectSettings(String id) {
        sc_id = id;
        path = getPath();

        if (FileUtil.isExistFile(path)) {
            try {
                hashmap = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_STRING_MAP);
            } catch (Exception var4) {
                hashmap = new HashMap<>();
                save();
            }
        } else {
            hashmap = new HashMap<>();
        }
    }

    private String getCheckedRbValue(RadioGroup radioGroup) {
        int counter = 0;
        String value;

        while (true) {
            if (counter >= radioGroup.getChildCount()) {
                value = "";
                break;
            }

            RadioButton var3 = (RadioButton) radioGroup.getChildAt(counter);
            if (var3.isChecked()) {
                value = var3.getText().toString();
                break;
            }

            ++counter;
        }

        return value;
    }

    private void save() {
        FileUtil.writeFile(path, (new Gson()).toJson(hashmap));
    }

    public String getPath() {
        return (new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/project_config")))).getAbsolutePath();
    }

    public String getValue(String key, String ifEmpty) {
        if (hashmap.containsKey(key)) {
            if (!hashmap.get(key).isEmpty()) {
                key = hashmap.get(key);
            } else {
                key = ifEmpty;
            }
        } else {
            key = ifEmpty;
        }

        return key;
    }

    public void setValues(View[] views) {
        for (View view : views) {
            if (view.getTag() != null) {
                String tag = (String) view.getTag();
                String value;

                if (view instanceof EditText) {
                    value = ((EditText) view).getText().toString();

                } else if (view instanceof CheckBox) {
                    if (((CheckBox) view).isChecked()) {
                        value = "true";
                    } else {
                        value = "false";
                    }

                } else {
                    if (!(view instanceof RadioGroup)) continue;

                    value = getCheckedRbValue((RadioGroup) view);
                }

                hashmap.put(tag, value);
            }
        }

        save();
    }
}
