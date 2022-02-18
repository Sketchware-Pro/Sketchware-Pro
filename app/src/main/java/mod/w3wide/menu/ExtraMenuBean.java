package mod.w3wide.menu;

import static mod.SketchwareUtil.getDip;
import static android.text.TextUtils.isEmpty;

import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.eC;
import a.a.a.Ss;
import a.a.a.jC;
import a.a.a.uq;
import a.a.a.wB;
import dev.aldi.sayuti.block.ExtraMenuBlock;
import mod.agus.jcoderz.editor.manage.block.makeblock.BlockMenu;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.asd.AsdDialog;
import mod.hilal.saif.asd.AsdHandler;
import mod.hilal.saif.asd.AsdHandlerCancel;
import mod.hilal.saif.asd.AsdOrigin;
import mod.hilal.saif.asd.asdforall.AsdAll;
import mod.hilal.saif.asd.old.AsdOldDialog;
import mod.w3wide.highlighter.SimpleHighlighter;

public class ExtraMenuBean {

    public static final int VARIABLE_TYPE_BOOLEAN = 0;
    public static final int VARIABLE_TYPE_INTEGER = 1;
    public static final int VARIABLE_TYPE_MAP = 3;
    public static final int VARIABLE_TYPE_STRING = 2;

    public static final int LIST_TYPE_INTRGER = 1;
    public static final int LIST_TYPE_MAP = 3;
    public static final int LIST_TYPE_STRING = 2;

    private final String ASSETS_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/assets/";
    private final String NATIVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/%s/files/native_libs/";

    private final ExtraMenuBlock extraMenuBlock;
    private final FilePathUtil fpu;
    private final FilePickerDialog fpd;
    private final FileResConfig frc;
    private final LogicEditorActivity logicEditor;
    private final DialogProperties mProperty = new DialogProperties();
    private final eC projectDataManager;
    private final String sc_id;

    private String splitter;

    public ExtraMenuBean(LogicEditorActivity logicA) {
        fpd = new FilePickerDialog(logicA);
        logicEditor = logicA;
        sc_id = logicA.B;
        fpu = new FilePathUtil();
        frc = new FileResConfig(logicA.B);
        extraMenuBlock = new ExtraMenuBlock(logicA);
        projectDataManager = jC.a(logicA.B);
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

                    case "import":
                        asdDialog(ss, false, "Enter the path without import & semicolon");
                        return;

                    default:
                        asdDialog(ss, false, null);
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
                    case "materialButton":
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
                        defaultMenus(ss);
                }
                break;
        }
    }

    private void defaultMenus(Ss menu) {
        String menuName = menu.getMenuName();
        AsdAll asdAll = new AsdAll(logicEditor);
        View rootView = wB.a(logicEditor, 2131427643);
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(2131231668);
        ArrayList<String> menus = new ArrayList<>();
        switch (menuName) {
            case "varInt":
                asdAll.b(Helper.getResString(2131625572));
                menus = getVarMenus(VARIABLE_TYPE_INTEGER);
                break;

            case "varBool":
                asdAll.b(Helper.getResString(2131625570));
                menus = getVarMenus(VARIABLE_TYPE_BOOLEAN);
                break;

            case "varStr":
                asdAll.b(Helper.getResString(2131625573));
                menus = getVarMenus(VARIABLE_TYPE_STRING);
                break;

            case "varMap":
                asdAll.b(Helper.getResString(2131625571));
                menus = getVarMenus(VARIABLE_TYPE_MAP);
                break;

            case "listInt":
                asdAll.b(Helper.getResString(2131625566));
                menus = getListMenus(LIST_TYPE_INTRGER);
                break;

            case "listStr":
                asdAll.b(Helper.getResString(2131625567));
                menus = getListMenus(LIST_TYPE_STRING);
                break;

            case "listMap":
                asdAll.b(Helper.getResString(2131625565));
                menus = getListMenus(LIST_TYPE_MAP);
                break;

            case "list":
                asdAll.b(Helper.getResString(2131625563));
                menus = projectDataManager.c(logicEditor.M.getJavaName());
                break;

            case "intent":
                asdAll.b(Helper.getResString(2131625545));
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_INTENT);
                break;

            case "file":
                asdAll.b(Helper.getResString(2131625540));
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_SHAREDPREF);
                break;

            case "intentAction":
                asdAll.b(Helper.getResString(2131625546));
                menus = new ArrayList(Arrays.asList(uq.b()));
                break;

            case "intentFlags":
                asdAll.b(Helper.getResString(2131625547));
                menus = new ArrayList(Arrays.asList(uq.c()));
                break;

            case "calendar":
                asdAll.b(Helper.getResString(2131625536));
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_CALENDAR);
                break;

            case "calendarField":
                asdAll.b(Helper.getResString(2131625537));
                menus = new ArrayList(Arrays.asList(uq.e));
                break;

            case "vibrator":
                asdAll.b(Helper.getResString(2131625557));
                menus = getComponentMenus(4);
                break;

            case "timer":
                asdAll.b(Helper.getResString(2131625556));
                menus = getComponentMenus(5);
                break;

            case "firebase":
                asdAll.b(Helper.getResString(2131625542));
                menus = getComponentMenus(6);
                break;

            case "firebaseauth":
                asdAll.b(Helper.getResString(2131625489));
                menus = getComponentMenus(12);
                break;

            case "firebasestorage":
                asdAll.b(Helper.getResString(2131625543));
                menus = getComponentMenus(14);
                break;

            case "dialog":
                asdAll.b(Helper.getResString(2131625539));
                menus = getComponentMenus(7);
                break;

            case "mediaplayer":
                asdAll.b(Helper.getResString(2131625550));
                menus = getComponentMenus(8);
                break;

            case "soundpool":
                asdAll.b(Helper.getResString(2131625553));
                menus = getComponentMenus(9);
                break;

            case "objectanimator":
                asdAll.b(Helper.getResString(2131625551));
                menus = getComponentMenus(10);
                break;

            case "aniRepeatMode":
                asdAll.b(Helper.getResString(2131625532));
                menus = new ArrayList(Arrays.asList(uq.j));
                break;

            case "aniInterpolator":
                asdAll.b(Helper.getResString(2131625531));
                menus = new ArrayList(Arrays.asList(uq.k));
                break;

            case "visible":
                asdAll.b(Helper.getResString(2131625575));
                menus = new ArrayList(Arrays.asList(uq.g));
                break;

            case "cacheMode":
                asdAll.b(Helper.getResString(2131625534));
                menus = new ArrayList(Arrays.asList(uq.h));
                break;

            case "animatorproperty":
                asdAll.b(Helper.getResString(2131625533));
                menus = new ArrayList(Arrays.asList(uq.i));
                break;

            case "gyroscope":
                asdAll.b(Helper.getResString(2131625544));
                menus = getComponentMenus(11);
                break;

            case "interstitialad":
                asdAll.b(Helper.getResString(2131625548));
                menus = getComponentMenus(13);
                break;

            case "camera":
                asdAll.b(Helper.getResString(2131625538));
                menus = getComponentMenus(15);
                break;

            case "filepicker":
                asdAll.b(Helper.getResString(2131625541));
                menus = getComponentMenus(16);
                break;

            case "directoryType":
                asdAll.b(Helper.getResString(2131625558));
                menus = new ArrayList(Arrays.asList(uq.l));
                break;

            case "requestnetwork":
                asdAll.b(Helper.getResString(2131625552));
                menus = getComponentMenus(17);
                break;

            case "method":
                asdAll.b(Helper.getResString(2131625528));
                menus = new ArrayList(Arrays.asList(uq.n));
                break;

            case "requestType":
                asdAll.b(Helper.getResString(2131625529));
                menus = new ArrayList(Arrays.asList(uq.o));
                break;

            case "texttospeech":
                asdAll.b(Helper.getResString(2131625555));
                menus = getComponentMenus(18);
                break;

            case "speechtotext":
                asdAll.b(Helper.getResString(2131625554));
                menus = getComponentMenus(19);
                break;

            case "bluetoothconnect":
                asdAll.b(Helper.getResString(2131625535));
                menus = getComponentMenus(20);
                break;

            case "locationmanager":
                asdAll.b(Helper.getResString(2131625549));
                menus = getComponentMenus(21);
                break;

            case "videoad":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(22);
                break;

            case "progressdialog":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(23);
                break;

            case "datepickerdialog":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(24);
                break;

            case "asynctask":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(36);
                break;

            case "timepickerdialog":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(25);
                break;

            case "notification":
                asdAll.b(Helper.getResString(2131626499));
                menus = getComponentMenus(26);
                break;

            case "fragmentAdapter":
                asdAll.b("Select FragmentAdapter Component");
                menus = getComponentMenus(27);
                break;

            case "phoneauth":
                asdAll.b("Select FirebasePhone Component");
                menus = getComponentMenus(28);
                break;

            case "dynamiclink":
                asdAll.b("Select DynamicLink Component");
                menus = getComponentMenus(29);
                break;

            case "cloudmessage":
                asdAll.b("Select CloudMessage Component");
                menus = getComponentMenus(30);
                break;

            case "googlelogin":
                asdAll.b("Select FirebaseGoogle Component");
                menus = getComponentMenus(31);
                break;

            case "onesignal":
                asdAll.b("Select OneSignal Component");
                menus = getComponentMenus(32);
                break;

            case "fbadbanner":
                asdAll.b("Select FBAdsBanner Component ");
                menus = getComponentMenus(33);
                break;

            case "fbadinterstitial":
                asdAll.b("Select FBAdsInterstitial Component");
                menus = getComponentMenus(34);
                break;

            case "providerType":
                asdAll.b(Helper.getResString(2131625523));
                menus = new ArrayList(Arrays.asList(uq.p));
                break;

            case "mapType":
                asdAll.b(Helper.getResString(2131625524));
                menus = new ArrayList(Arrays.asList(uq.q));
                break;

            case "markerColor":
                asdAll.b(Helper.getResString(2131625525));
                menus = new ArrayList(Arrays.asList(uq.r));
                break;
            /**
             * Using {@link mod.agus.jcoderz.beans.ServiceBean}'s logic directly
             */
            case "service":
                asdAll.b("Select Background Service");
                if (FileUtil.isExistFile(fpu.getManifestService(sc_id))) {
                    menus = frc.getServiceManifestList();
                }
                break;
            /**
             * Using {@link mod.agus.jcoderz.beans.BroadcastBean}'s logic directly
             */
            case "broadcast":
                asdAll.b("Select Broadcast Receiver");
                if (FileUtil.isExistFile(fpu.getManifestBroadcast(sc_id))) {
                    menus = frc.getBroadcastManifestList();
                }
                break;

            case "activity":
                ArrayList<String> activityMenu = new ArrayList<>();
                asdAll.b(Helper.getResString(2131625530));
                for (ProjectFileBean projectFileBean : jC.b(sc_id).b()) {
                    activityMenu.add(projectFileBean.getActivityName());
                }
                for (String activity : activityMenu) {
                    viewGroup.addView(logicEditor.e(activity));
                }
                activityMenu = new ArrayList<>();
                if (FileUtil.isExistFile(fpu.getManifestJava(sc_id))) {
                    for (String activity : frc.getJavaManifestList()) {
                        if (activity.contains(".")) {
                            activityMenu.add(activity.substring(1 + activity.lastIndexOf(".")));
                        }
                    }
                    if (activityMenu.size() >= 1) {
                        TextView txt = new TextView(logicEditor);
                        txt.setText("Custom Activities");
                        txt.setPadding(
                                (int) getDip(2),
                                (int) getDip(4),
                                (int) getDip(4),
                                (int) getDip(4)
                        );
                        txt.setTextSize(14f);
                        viewGroup.addView(txt);
                    }
                    for (String activity : activityMenu) {
                        viewGroup.addView(logicEditor.e(activity));
                    }
                }
                break;

            case "customViews":
                asdAll.b("Select a custom view");
                for (ProjectFileBean projectFileBean : jC.b(sc_id).c()) {
                    menus.add(projectFileBean.fileName);
                }
                break;

            default:
                Pair<String, String[]> menuPair = BlockMenu.getMenu(menu.getMenuName());
                asdAll.b(menuPair.first);
                menus = new ArrayList(Arrays.asList(menuPair.second));
                extraMenuBlock.a(menu, asdAll, menus);
        }
        for (String menuArg : menus) {
            viewGroup.addView(logicEditor.e(menuArg));
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                RadioButton rb = (RadioButton) viewGroup.getChildAt(i);
                if (menu.getArgValue().toString().equals(rb.getText().toString())) {
                    rb.setChecked(true);
                    break;
                }
            }
        }
        asdAll.a(rootView);
        asdAll.b(Helper.getResString(2131625035), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    if (viewGroup.getChildAt(i) instanceof RadioButton) {
                        RadioButton rb = (RadioButton) viewGroup.getChildAt(i);
                        if (rb.isChecked()) {
                            logicEditor.a(menu, (Object) rb.getText().toString());
                        }
                    }
                }
                asdAll.dismiss();
            }
        });
        asdAll.carry(logicEditor, menu, viewGroup);
        asdAll.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(asdAll));
        asdAll.show();
    }

    private ArrayList<String> getVarMenus(int type) {
        return projectDataManager.e(logicEditor.M.getJavaName(), type);
    }

    private ArrayList<String> getListMenus(int type) {
        return projectDataManager.d(logicEditor.M.getJavaName(), type);
    }

    private ArrayList<String> getComponentMenus(int type) {
        return projectDataManager.b(logicEditor.M.getJavaName(), type);
    }

    private void asdDialog(Ss ss, boolean isNum, String message) {
        AsdOrigin asdOr = new AsdOrigin(logicEditor);
        if (isNum) {
            asdOr.b(Helper.getResString(Resources.string.logic_editor_title_enter_number_value));
        } else {
            asdOr.b(Helper.getResString(Resources.string.logic_editor_title_enter_string_value));
        }
        asdOr.a(Resources.drawable.rename_96_blue);

        if (!isEmpty(message)) asdOr.a(message);

        View root = wB.a(logicEditor, Resources.layout.property_popup_input_text);
        EditText edittext = root.findViewById(Resources.id.ed_input);
        if (isNum) {
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
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
