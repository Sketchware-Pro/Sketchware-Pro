package mod.w3wide.menu;

import static android.text.TextUtils.isEmpty;
import static mod.SketchwareUtil.getDip;

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

import a.a.a.Ss;
import a.a.a.eC;
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

    public static final int LIST_TYPE_INTEGER = 1;
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
                        asdDialog(ss, "Enter the path without import & semicolon");
                        return;

                    default:
                        asdDialog(ss, null);
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
        View rootView = wB.a(logicEditor, Resources.layout.property_popup_selector_single);
        ViewGroup viewGroup = rootView.findViewById(Resources.id.rg_content);
        ArrayList<String> menus = new ArrayList<>();
        String title;
        switch (menuName) {
            case "varInt":
                title = Helper.getResString(Resources.string.logic_editor_title_select_variable_number);
                menus = getVarMenus(VARIABLE_TYPE_INTEGER);
                break;

            case "varBool":
                title = Helper.getResString(Resources.string.logic_editor_title_select_variable_boolean);
                menus = getVarMenus(VARIABLE_TYPE_BOOLEAN);
                break;

            case "varStr":
                title = Helper.getResString(Resources.string.logic_editor_title_select_variable_string);
                menus = getVarMenus(VARIABLE_TYPE_STRING);
                break;

            case "varMap":
                title = Helper.getResString(Resources.string.logic_editor_title_select_variable_map);
                menus = getVarMenus(VARIABLE_TYPE_MAP);
                break;

            case "listInt":
                title = Helper.getResString(Resources.string.logic_editor_title_select_list_number);
                menus = getListMenus(LIST_TYPE_INTEGER);
                break;

            case "listStr":
                title = Helper.getResString(Resources.string.logic_editor_title_select_list_string);
                menus = getListMenus(LIST_TYPE_STRING);
                break;

            case "listMap":
                title = Helper.getResString(Resources.string.logic_editor_title_select_list_map);
                menus = getListMenus(LIST_TYPE_MAP);
                break;

            case "list":
                title = Helper.getResString(Resources.string.logic_editor_title_select_list);
                menus = projectDataManager.c(logicEditor.M.getJavaName());
                break;

            case "intent":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_intent);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_INTENT);
                break;

            case "file":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_file);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_SHAREDPREF);
                break;

            case "intentAction":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_intent_action);
                menus = new ArrayList<>(Arrays.asList(uq.b()));
                break;

            case "intentFlags":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_intent_flags);
                menus = new ArrayList<>(Arrays.asList(uq.c()));
                break;

            case "calendar":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_calendar);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_CALENDAR);
                break;

            case "calendarField":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_calendar_field);
                menus = new ArrayList<>(Arrays.asList(uq.e));
                break;

            case "vibrator":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_vibrator);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_VIBRATOR);
                break;

            case "timer":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_timer);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_TIMERTASK);
                break;

            case "firebase":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_firebase);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_FIREBASE);
                break;

            case "firebaseauth":
                title = Helper.getResString(Resources.string.logic_editor_component_firebaseauth_title_select_firebase_auth);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH);
                break;

            case "firebasestorage":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_firebasestorage);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE);
                break;

            case "dialog":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_dialog);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_DIALOG);
                break;

            case "mediaplayer":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_mediaplayer);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_MEDIAPLAYER);
                break;

            case "soundpool":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_soundpool);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_SOUNDPOOL);
                break;

            case "objectanimator":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_objectanimator);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR);
                break;

            case "aniRepeatMode":
                title = Helper.getResString(Resources.string.logic_editor_title_select_animator_repeat_mode);
                menus = new ArrayList<>(Arrays.asList(uq.j));
                break;

            case "aniInterpolator":
                title = Helper.getResString(Resources.string.logic_editor_title_select_animator_interpolator);
                menus = new ArrayList<>(Arrays.asList(uq.k));
                break;

            case "visible":
                title = Helper.getResString(Resources.string.logic_editor_title_select_visibility);
                menus = new ArrayList<>(Arrays.asList(uq.g));
                break;

            case "cacheMode":
                title = Helper.getResString(Resources.string.logic_editor_title_select_cache_mode);
                menus = new ArrayList<>(Arrays.asList(uq.h));
                break;

            case "animatorproperty":
                title = Helper.getResString(Resources.string.logic_editor_title_select_animator_target_property);
                menus = new ArrayList<>(Arrays.asList(uq.i));
                break;

            case "gyroscope":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_gyroscope);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_GYROSCOPE);
                break;

            case "interstitialad":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_interstitialad);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD);
                break;

            case "camera":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_camera);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_CAMERA);
                break;

            case "filepicker":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_filepicker);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_FILE_PICKER);
                break;

            case "directoryType":
                title = Helper.getResString(Resources.string.logic_editor_title_select_directory_type);
                menus = new ArrayList<>(Arrays.asList(uq.l));
                break;

            case "requestnetwork":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_request_network);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK);
                break;

            case "method":
                title = Helper.getResString(Resources.string.logic_editor_title_request_network_method);
                menus = new ArrayList<>(Arrays.asList(uq.n));
                break;

            case "requestType":
                title = Helper.getResString(Resources.string.logic_editor_title_request_network_request_type);
                menus = new ArrayList<>(Arrays.asList(uq.o));
                break;

            case "texttospeech":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_text_to_speech);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH);
                break;

            case "speechtotext":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_speech_to_text);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT);
                break;

            case "bluetoothconnect":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_bluetooth_connect);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT);
                break;

            case "locationmanager":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component_location_manager);
                menus = getComponentMenus(ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER);
                break;

            case "videoad":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(22);
                break;

            case "progressdialog":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(23);
                break;

            case "datepickerdialog":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(24);
                break;

            case "asynctask":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(36);
                break;

            case "timepickerdialog":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(25);
                break;

            case "notification":
                title = Helper.getResString(Resources.string.logic_editor_title_select_component);
                menus = getComponentMenus(26);
                break;

            case "fragmentAdapter":
                title = "Select a FragmentAdapter Component";
                menus = getComponentMenus(27);
                break;

            case "phoneauth":
                title = "Select a FirebasePhone Component";
                menus = getComponentMenus(28);
                break;

            case "dynamiclink":
                title = "Select a DynamicLink Component";
                menus = getComponentMenus(29);
                break;

            case "cloudmessage":
                title = "Select a CloudMessage Component";
                menus = getComponentMenus(30);
                break;

            case "googlelogin":
                title = "Select a FirebaseGoogle Component";
                menus = getComponentMenus(31);
                break;

            case "onesignal":
                title = "Select a OneSignal Component";
                menus = getComponentMenus(32);
                break;

            case "fbadbanner":
                title = "Select an FBAdsBanner Component";
                menus = getComponentMenus(33);
                break;

            case "fbadinterstitial":
                title = "Select an FBAdsInterstitial Component";
                menus = getComponentMenus(34);
                break;

            case "providerType":
                title = Helper.getResString(Resources.string.logic_editor_title_location_manager_provider_type);
                menus = new ArrayList<>(Arrays.asList(uq.p));
                break;

            case "mapType":
                title = Helper.getResString(Resources.string.logic_editor_title_mapview_map_type);
                menus = new ArrayList<>(Arrays.asList(uq.q));
                break;

            case "markerColor":
                title = Helper.getResString(Resources.string.logic_editor_title_mapview_marker_color);
                menus = new ArrayList<>(Arrays.asList(uq.r));
                break;
            case "service":
                title = "Select a Background Service";
                if (FileUtil.isExistFile(fpu.getManifestService(sc_id))) {
                    menus = frc.getServiceManifestList();
                }
                break;
            case "broadcast":
                title = "Select a Broadcast Receiver";
                if (FileUtil.isExistFile(fpu.getManifestBroadcast(sc_id))) {
                    menus = frc.getBroadcastManifestList();
                }
                break;

            case "activity":
                ArrayList<String> activityMenu = new ArrayList<>();
                title = Helper.getResString(Resources.string.logic_editor_title_select_activity);
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
                title = "Select a Custom View";
                for (ProjectFileBean projectFileBean : jC.b(sc_id).c()) {
                    menus.add(projectFileBean.fileName);
                }
                break;

            default:
                Pair<String, String[]> menuPair = BlockMenu.getMenu(menu.getMenuName());
                title = menuPair.first;
                menus = new ArrayList<>(Arrays.asList(menuPair.second));
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

        asdAll.b(title);
        asdAll.a(rootView);
        asdAll.b(Helper.getResString(Resources.string.common_word_select), view -> {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof RadioButton) {
                    RadioButton rb = (RadioButton) viewGroup.getChildAt(i);
                    if (rb.isChecked()) {
                        logicEditor.a(menu, (Object) rb.getText().toString());
                    }
                }
            }
            asdAll.dismiss();
        });
        asdAll.carry(logicEditor, menu, viewGroup);
        asdAll.a(Helper.getResString(Resources.string.common_word_cancel), Helper.getDialogDismissListener(asdAll));
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

    private void asdDialog(Ss ss, String message) {
        AsdOrigin asdOr = new AsdOrigin(logicEditor);
        asdOr.b(Helper.getResString(Resources.string.logic_editor_title_enter_string_value));
        asdOr.a(Resources.drawable.rename_96_blue);

        if (!isEmpty(message)) asdOr.a(message);

        View root = wB.a(logicEditor, Resources.layout.property_popup_input_text);
        EditText edittext = root.findViewById(Resources.id.ed_input);
        edittext.setInputType(655361);
        edittext.setImeOptions(EditorInfo.IME_ACTION_NONE);

        if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_USE_ASD_HIGHLIGHTER)) {
            new SimpleHighlighter(edittext);
        }
        edittext.setText(ss.getArgValue().toString());
        asdOr.a(root);
        asdOr.carry(logicEditor, ss, false, edittext);

        asdOr.b(Helper.getResString(Resources.string.common_word_save), new AsdHandler(logicEditor, edittext, false, ss, asdOr));
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
