package mod.w3wide.menu;

import static com.besome.sketch.SketchApplication.getContext;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.ArrayList;

import a.a.a.Ss;
import a.a.a.wB;
import a.a.a.xB;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.asd.AsdDialog;
import mod.hilal.saif.asd.AsdHandler;
import mod.hilal.saif.asd.AsdHandlerCancel;
import mod.hilal.saif.asd.AsdOrigin;
import mod.hilal.saif.asd.old.AsdOldDialog;
import mod.w3wide.highlighter.SimpleHighlighter;

public class ExtraMenuBean {

    private final String ASSETS_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/assets/";
    private final String NATIVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/native_libs/";

    private final FilePickerDialog fpd;
    private final LogicEditorActivity logicEditor;
    private final DialogProperties mProperty = new DialogProperties();
    private final String sc_id;

    private String splitter;

    public ExtraMenuBean(LogicEditorActivity logicA) {
        fpd = new FilePickerDialog(logicA);
        logicEditor = logicA;
        sc_id = logicA.B;
    }

    private void codeMenu(Ss menu) {
        if (ConfigActivity.isLegacyCeEnabled()) {
            AsdOldDialog asdOldDialog = new AsdOldDialog(logicEditor);
            asdOldDialog.setCon(menu.getArgValue().toString());
            asdOldDialog.show();
            /* p2 as true is for number */
            asdOldDialog.saveLis(logicEditor, false, menu, asdOldDialog);
            asdOldDialog.cancelLis(logicEditor, asdOldDialog);
        } else {
            AsdDialog asdDialog = new AsdDialog(logicEditor);
            asdDialog.setCon(menu.getArgValue().toString());
            asdDialog.show();
            /* p2 as true is for number */
            asdDialog.saveLis(logicEditor, false, menu, asdDialog);
            asdDialog.cancelLis(logicEditor, asdDialog);
        }
    }

    public void defineMenuSelector(Ss ss) {
        String menuType = ss.b;
        String menuName = ss.getMenuName();

        switch (menuType) {
            case "d":
                logicEditor.a(ss, true);
                break;

            case "s":
                switch (menuName) {
                    case "intentData":
                        logicEditor.e(ss);
                        return;

                    case "url":
                        logicEditor.c(ss);
                        return;

                    case "inputCode":
                        codeMenu(ss);
                        return;

                    default:
                        cusA(ss, false);
                }
                break;

            case "m":
                switch (menuName) {
                    case "resource":
                        logicEditor.a(ss, "property_image");
                        return;

                    case "resource_bg":
                        logicEditor.a(ss, "property_background_resource");
                        return;

                    case "sound":
                        logicEditor.h(ss);
                        return;

                    case "font":
                        logicEditor.d(ss);
                        return;

                    case "typeface":
                        logicEditor.i(ss);
                        return;

                    case "color":
                        logicEditor.b(ss);
                        return;

                    case "view":
                    case "textview":
                    case "edittext":
                    case "imageview":
                    case "listview":
                    case "spinner":
                    case "listSpn":
                    case "webview":
                    case "checkbox":
                    case "switch":
                    case "seekbar":
                    case "calendarview":
                    case "compoundButton":
                    case "adview":
                    case "progressbar":
                    case "mapview":
                    case "radiobutton":
                    case "ratingbar":
                    case "searchview":
                    case "videoview":
                    case "gridview":
                    case "actv":
                    case "mactv":
                    case "tablayout":
                    case "viewpager":
                    case "bottomnavigation":
                    case "badgeview":
                    case "patternview":
                    case "sidebar":
                    case "recyclerview":
                    case "cardview":
                    case "collapsingtoolbar":
                    case "textinputlayout":
                    case "swiperefreshlayout":
                    case "radiogroup":
                    case "lottie":
                    case "otpview":
                    case "signinbutton":
                    case "youtubeview":
                    case "codeview":
                    case "datepicker":
                    case "timepicker":
                        logicEditor.f(ss);
                        return;

                    case "Assets":
                    case "NativeLib":
                        pathSelectorMenu(ss);
                        return;

                    default:
                        logicEditor.g(ss);
                }
                break;
        }
    }

    public void cusA(Ss ss, boolean isNum) {
        AsdOrigin asdOr = new AsdOrigin(logicEditor);
        if (isNum) {
            asdOr.b(Helper.getResString(Resources.string.logic_editor_title_enter_number_value));
        } else {
            asdOr.b(Helper.getResString(Resources.string.logic_editor_title_enter_string_value));
        }
        asdOr.a(Resources.drawable.rename_96_blue);

        View root = wB.a(logicEditor, Resources.layout.property_popup_input_text);
        EditText edittext = root.findViewById(Resources.id.ed_input);
        if (isNum) {
            edittext.setInputType(12290);
            edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
            edittext.setMaxLines(1);
        } else {
            edittext.setInputType(655361);
            edittext.setImeOptions(EditorInfo.IME_ACTION_NONE);
        }
        if (!isNum && ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_USE_ASD_HIGHLIGHTER)) {
            new SimpleHighlighter(edittext);
        }
        edittext.setText(ss.getArgValue().toString());
        asdOr.a(root);
        asdOr.carry(logicEditor, ss, isNum, edittext);

        asdOr.b(Helper.getResString(Resources.string.common_word_save), new AsdHandler(logicEditor, edittext, isNum, ss, asdOr));
        asdOr.a(Helper.getResString(Resources.string.common_word_cancel), new AsdHandlerCancel(logicEditor, edittext, asdOr));
        asdOr.show();
    }

    private void pathSelectorMenu(final Ss ss) {
        String menuName = ss.getMenuName();
        ArrayList<String> markedPath = new ArrayList<>();

        mProperty.selection_mode = DialogConfigs.SINGLE_MODE;
        mProperty.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        String path;
        if (menuName.equals("Assets")) {
            fpd.setTitle("Select an Asset");
            path = String.format(ASSETS_PATH, sc_id);
            markedPath.add(0, path + ss.getArgValue().toString());
            fpd.markFiles(markedPath);
            mProperty.root = new File(path);
            mProperty.error_dir = new File(path);
            String[] strArr = path.split("/");
            splitter = strArr[strArr.length - 1];
        } else if (menuName.equals("NativeLib")) {
            fpd.setTitle("Select a Native library");
            path = String.format(NATIVE_PATH, sc_id);
            markedPath.add(0, path + ss.getArgValue().toString());
            fpd.markFiles(markedPath);
            mProperty.selection_type = DialogConfigs.FILE_SELECT;
            mProperty.root = new File(path);
            mProperty.error_dir = new File(path);
            String[] strArr = path.split("/");
            splitter = strArr[strArr.length - 1];
        }
        fpd.setProperties(mProperty);
        fpd.setDialogSelectionListener(files -> logicEditor.a(ss, (Object) files[0].split(splitter)[1]));
        fpd.show();
    }
}
