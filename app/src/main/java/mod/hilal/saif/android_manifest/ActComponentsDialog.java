package mod.hilal.saif.android_manifest;

import static pro.sketchware.utility.GsonUtils.getGson;
import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ActComponentsDialog extends Dialog {

    private static String ACTIVITIES_COMPONENTS_FILE_PATH;
    private final Timer _timer = new Timer();
    private final Activity activity;
    private final String activityName;
    private ViewGroup base;
    private TextView cancel;
    private CodeEditorLayout codeEditor;
    private CodeEditorEditText editor;
    private TextView save;

    public ActComponentsDialog(Activity activity, String sc_id, String activityName) {
        super(activity);
        ACTIVITIES_COMPONENTS_FILE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/Injection/androidmanifest/activities_components.json";
        this.activity = activity;
        this.activityName = activityName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_code);
        codeEditor = findViewById(R.id.text_content);
        TextView zoom_in = findViewById(R.id.code_editor_zoomin);
        zoom_in.setOnClickListener(v -> codeEditor.increaseTextSize());
        TextView zoom_out = findViewById(R.id.code_editor_zoomout);
        zoom_out.setOnClickListener(v -> codeEditor.decreaseTextSize());
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        base = (ViewGroup) codeEditor.getParent();
        base.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4), 0, Color.WHITE, Color.WHITE));
        TextView title = findViewById(R.id.text_title);
        title.setText(activityName + " Components");
        addControl();
        setListeners();
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        codeEditor.start(ColorScheme.XML());
        setCodeEditorText();
        editor = codeEditor.getEditText();
        codeEditor.onCreateOptionsMenu(findViewById(R.id.codeeditor_more_options));
        editor.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editor.setImeOptions(EditorInfo.IME_ACTION_NONE);
    }

    private void addControl() {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.0f));
        linearLayout.setPadding(
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (codeEditor.dark_theme) {
            linearLayout.setBackgroundColor(0xff292929);
        } else {
            linearLayout.setBackgroundColor(Color.WHITE);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        cancel.setText(R.string.common_word_cancel);
        cancel.setTextColor(Color.WHITE);
        cancel.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        cancel.setGravity(17);
        if (codeEditor.dark_theme) {
            cancel.setBackgroundColor(0xff333333);
        } else {
            cancel.setBackgroundColor(0xff008dcd);
        }
        cancel.setTextSize(15.0f);
        linearLayout.addView(cancel);
        save = new TextView(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        save.setText(R.string.common_word_save);
        save.setTextColor(Color.WHITE);
        save.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        save.setGravity(17);
        if (codeEditor.dark_theme) {
            save.setBackgroundColor(0xff333333);
        } else {
            save.setBackgroundColor(0xff008dcd);
        }
        save.setTextSize(15.0f);
        linearLayout.addView(save);
        if (codeEditor.dark_theme) {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
        } else {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
        }
        save.setElevation(getDip(1));
        cancel.setElevation(getDip(1));
        base.addView(linearLayout);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if (codeEditor.dark_theme) {
                        linearLayout.setBackgroundColor(0xff292929);
                        save.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                        cancel.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                        return;
                    }
                    linearLayout.setBackgroundColor(Color.WHITE);
                    save.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                    cancel.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                });
            }
        };
        _timer.scheduleAtFixedRate(timerTask, 500, 500);
    }

    private void setListeners() {
        save.setOnClickListener(v -> {
            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
            if (FileUtil.isExistFile(ACTIVITIES_COMPONENTS_FILE_PATH)) {
                ArrayList<HashMap<String, Object>> activitiesComponents = getGson()
                        .fromJson(FileUtil.readFile(ACTIVITIES_COMPONENTS_FILE_PATH), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < activitiesComponents.size(); i++) {
                    if (activitiesComponents.get(i).get("name").equals(activityName)) {
                        activitiesComponents.get(i).put("value", codeEditor.getText());
                        FileUtil.writeFile(ACTIVITIES_COMPONENTS_FILE_PATH, getGson()
                                .toJson(activitiesComponents));
                        SketchwareUtil.toast("Saved");
                        dismiss();
                        return;
                    }
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", activityName);
                map.put("value", codeEditor.getText());
                activitiesComponents.add(map);
                FileUtil.writeFile(ACTIVITIES_COMPONENTS_FILE_PATH, getGson()
                        .toJson(activitiesComponents));
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", activityName);
                map.put("value", codeEditor.getText());
                arrayList.add(map);
                FileUtil.writeFile(ACTIVITIES_COMPONENTS_FILE_PATH, getGson()
                        .toJson(arrayList));
            }
            dismiss();
        });
        cancel.setOnClickListener(Helper.getDialogDismissListener(this));
    }

    private void setCodeEditorText() {
        if (FileUtil.isExistFile(ACTIVITIES_COMPONENTS_FILE_PATH)) {
            ArrayList<HashMap<String, Object>> arrayList = getGson()
                    .fromJson(FileUtil.readFile(ACTIVITIES_COMPONENTS_FILE_PATH), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get("name").equals(activityName)) {
                    codeEditor.setText((String) arrayList.get(i).get("value"));
                    return;
                }
            }
        } else {
            codeEditor.setText("");
        }
    }
}
