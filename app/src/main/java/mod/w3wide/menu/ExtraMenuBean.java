package mod.w3wide.menu;

import a.a.a.Ss;

import com.besome.sketch.editor.LogicEditorActivity;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.asd.AsdDialog;
import mod.hilal.saif.asd.old.AsdOldDialog;

public class ExtraMenuBean {
    
    private static final DialogProperties mProperty = new DialogProperties();
    private static final String ASSETS_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/assets/";
    private static final String NATIVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/native_libs/";
    
    private static FilePickerDialog fpd;
    private static LogicEditorActivity logicEditor;
    
    private static String path;
    private static String sc_id;
    private static String splitter;
    
    public ExtraMenuBean(LogicEditorActivity logicA, String sc_id) {
        this.fpd = new FilePickerDialog(logicA);
        this.logicEditor = logicA;
        this.sc_id = sc_id;
    }
    
    //Note: This will only be applied on the {s} type menu. for example %s.menu
    public static void menu(Ss menu) {
        String menuName = menu.getMenuName();
        if (menuName.equals("inputCode")) {
            if (ConfigActivity.isLegacyCeEnabled()) {
                AsdOldDialog asdOldDialog = new AsdOldDialog(logicEditor);
                asdOldDialog.setCon(menu.getArgValue().toString());
                asdOldDialog.show();
                asdOldDialog.saveLis(logicEditor, false/*'true' is for number.*/, menu, asdOldDialog);
                asdOldDialog.cancelLis(logicEditor, asdOldDialog);
            } else {
                AsdDialog asdDialog = new AsdDialog(logicEditor);
                asdDialog.setCon(menu.getArgValue().toString());
                asdDialog.show();
                asdDialog.saveLis(logicEditor, false/*'true' is for number.*/, menu, asdDialog);
                asdDialog.cancelLis(logicEditor, asdDialog);
            }
        } else {
            logicEditor.cusA(menu, false/*'true' is for number.*/);
        }
    }
    
    public static void pathSelectorMenu(final Ss ss) {
        String menuName = ss.getMenuName();
        ArrayList<String> markedPath = new ArrayList<>();
        
        mProperty.selection_mode = DialogConfigs.SINGLE_MODE;
        mProperty.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        if (menuName.equals("Assets")) {
            fpd.setTitle("Select Assets File");
            path = String.format(ASSETS_PATH, sc_id);
            markedPath.add(0, path + ss.getArgValue().toString());
            fpd.markFiles(markedPath);
            mProperty.root = new File(path);
            mProperty.error_dir = new File(path);
            String[] strArr = path.split("/");
            splitter = strArr[strArr.length - 1];
        } else if(menuName.equals("NativeLib")) {
            fpd.setTitle("Select Native Libraries File");
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
        fpd.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                logicEditor.a(ss, (Object) files[0].split(splitter)[1]);
            }
        });
        fpd.show();
    }
    
}
