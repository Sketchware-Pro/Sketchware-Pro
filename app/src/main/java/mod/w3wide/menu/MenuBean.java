package mod.w3wide.menu;

import android.net.Uri;
import android.util.Pair;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.editor.LogicEditorActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.eC;
import a.a.a.Ss;
import a.a.a.jC;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class MenuBean {

    private static final String[] adSize = new String[]{"AUTO_HEIGHT", "BANNER", "FLUID", "FULL_BANNER", "FULL_WIDTH", "INVALID", "LARGE_BANNER", "LEADERBOARD", "MEDIUM_RECTANGLE", "SEARCH", "SMART_BANNER", "WIDE_SKYSCRAPER"};
    private static final String[] defaultColor = new String[]{"colorAccent", "colorControlHighlight", "colorControlNormal", "colorPrimary", "colorPrimaryDark"};
    private static final String[] intentKey = new String[]{"EXTRA_ALLOW_MULTIPLE", "EXTRA_EMAIL", "EXTRA_INDEX", "EXTRA_INTENT", "EXTRA_PHONE_NUMBER", "EXTRA_STREAM", "EXTRA_SUBJECT", "EXTRA_TEXT", "EXTRA_TITLE"};
    private static final String[] pixelFormat = new String[]{"OPAQUE", "RGBA_1010102", "RGBA_8888", "RGBA_F16", "RGBX_8888", "RGB_565", "RGB_888", "TRANSLUCENT", "TRANSPARENT", "UNKNOWN"};
    private static final String[] patternFlags = new String[]{"CANON_EQ", "CASE_INSENSITIVE", "COMMENTS", "DOTALL", "LITERAL", "MULTILINE", "UNICODE_CASE", "UNIX_LINES"};
    private static final String[] permission;

    static {
        ArrayList<String> permissions = new ArrayList<>();
        for (Field permissionField : android.Manifest.permission.class.getDeclaredFields()) {
            permissions.add(permissionField.getName());
        }
        permission = permissions.toArray(new String[0]);
    }

    private final eC projectDataManager;
    private final LogicEditorActivity logic;
    public String javaName;
    public String sc_id;

    public MenuBean(LogicEditorActivity activity) {
        javaName = activity.M.getJavaName();
        logic = activity;
        sc_id = activity.B;
        projectDataManager = jC.a(activity.B);
    }

    public static ArrayList<String> getProjectFiles(String path, String extension) {
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> projectFiles = new ArrayList<>();
        FileUtil.listDir(path, files);
        for (String file : files) {
            String lastPathSegment = Uri.parse(file).getLastPathSegment();
            if (file.endsWith(extension)) {
                projectFiles.add(lastPathSegment.substring(0, lastPathSegment.indexOf(extension)));
            }
        }
        return projectFiles;
    }

    public void projectMenu(Ss ss, AsdAll asdAll, ArrayList<String> selectableItems) {
        String menuName = ss.getMenuName();
        asdAll.b("Select " + menuName);
        switch (menuName) {
            case "SignButtonColor":
                asdAll.b("Select a SignInButton Color");
                selectableItems.add("COLOR_AUTO");
                selectableItems.add("COLOR_DARK");
                selectableItems.add("COLOR_LIGHT");
                break;

            case "SignButtonSize":
                asdAll.b("Select SignInButton Size");
                selectableItems.add("SIZE_ICON_ONLY");
                selectableItems.add("SIZE_STANDARD");
                selectableItems.add("SIZE_WIDE");
                break;

            case "ResString":
            case "ResStyle":
            case "ResColor":
            case "ResArray":
            case "ResDimen":
            case "ResBool":
            case "ResInteger":
            case "ResAttr":
            case "ResXml":
                asdAll.b("Deprecated");
                asdAll.a("This block menu has been deprecated because it used to read the file for the value which is very heavy on the I/O. But you still can use CodeEditor.");
                break;

            case "AdUnit":
                asdAll.a(2131166209);
                asdAll.b("Select an AdUnit");
                selectableItems.addAll(AdMobReader.getAdUnits(sc_id));
                break;

            case "TestDevice":
                asdAll.a(2131165866);
                asdAll.b("Select a test device");
                selectableItems.addAll(AdMobReader.getTestDevices(sc_id));
                break;

            case "IntentKey":
                asdAll.b("Select an Intent key");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(intentKey)));
                break;

            case "PatternFlag":
                asdAll.b("Select a Pattern Flags");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(patternFlags)));
                break;

            case "Permission":
                asdAll.b("Select a Permission");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(permission)));
                break;

            case "AdSize":
                asdAll.b("Select an Ad size");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(adSize)));
                break;

            case "PixelFormat":
                asdAll.b("Select a PixelFormat");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(pixelFormat)));
                break;

            case "Variable":
                asdAll.b("Select a Variable");
                for (Pair<Integer, String> integerStringPair : projectDataManager.k(javaName)) {
                    selectableItems.add(integerStringPair.second.replaceFirst("^\\w+[\\s]+(\\w+)", "$1"));
                }
                break;

            case "Component":
                asdAll.b("Select a Component");
                for (ComponentBean componentBean : projectDataManager.e(javaName)) {
                    selectableItems.add(componentBean.componentId);
                }
                break;

            case "CustomVar":
                asdAll.b("Select a Custom Variable");
                for (String s : projectDataManager.e(javaName, 5)) {
                    Matcher matcher = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(s);
                    while (matcher.find()) {
                        selectableItems.add(matcher.group(2));
                    }
                }
                break;
        }

        for (String s : projectDataManager.e(javaName, 5)) {
            Matcher matcher2 = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(s);
            while (matcher2.find()) {
                if (menuName.equals(matcher2.group(1))) {
                    asdAll.b("Select a " + matcher2.group(1) + " Variable");
                    selectableItems.add(matcher2.group(2));
                }
            }
        }
        for (ComponentBean componentBean : projectDataManager.e(javaName)) {
            if (componentBean.type > 36 && menuName.equals(ComponentBean.getComponentTypeName(componentBean.type))) {
                asdAll.b("Select a " + ComponentBean.getComponentTypeName(componentBean.type));
                selectableItems.add(componentBean.componentId);
            }
        }
    }
}
