package mod.w3wide.menu;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.editor.LogicEditorActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.Ss;
import a.a.a.jC;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class MenuBean {

    private static final String[] adSize = {"AUTO_HEIGHT", "BANNER", "FLUID", "FULL_BANNER", "FULL_WIDTH", "INVALID", "LARGE_BANNER", "LEADERBOARD", "MEDIUM_RECTANGLE", "SEARCH", "SMART_BANNER", "WIDE_SKYSCRAPER"};
    private static final String[] defaultColor = {"colorAccent", "colorControlHighlight", "colorControlNormal", "colorPrimary", "colorPrimaryDark"};
    private static final String[] intentKey = new String[]{"EXTRA_ALLOW_MULTIPLE","EXTRA_EMAIL","EXTRA_INDEX","EXTRA_INTENT","EXTRA_PHONE_NUMBER","EXTRA_STREAM","EXTRA_SUBJECT","EXTRA_TEXT","EXTRA_TITLE"};
    private static final String[] pixelFormat = {"OPAQUE", "RGBA_1010102", "RGBA_8888", "RGBA_F16", "RGBX_8888", "RGB_565", "RGB_888", "TRANSLUCENT", "TRANSPARENT", "UNKNOWN"};

    private final LogicEditorActivity logic;
    public String sc_id;

    public MenuBean(LogicEditorActivity activity) {
        logic = activity;
        sc_id = activity.B;
    }

    public static ArrayList<String> readResNames(String filePath, String pattern) {
        ArrayList<String> resNames = new ArrayList<>();
        if (FileUtil.isExistFile(filePath)) {
            Matcher matcher = Pattern.compile(pattern).matcher(FileUtil.readFile(filePath));
            while (matcher.find()) {
                resNames.add(matcher.group(1));
            }
        }
        return resNames;
    }

    public static ArrayList<String> getProjectFiles(String path, String extension) {
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> projectFiles = new ArrayList<>();
        FileUtil.listDir(path, files);
        for (String file : files) {
            if (file.endsWith(extension)) {
                projectFiles.add(Uri.parse(file).getLastPathSegment().substring(0, Uri.parse(file).getLastPathSegment().indexOf(extension)));
            }
        }
        return projectFiles;
    }

    public void projectMenu(Ss ss, AsdAll asdAll, ArrayList<String> selectableItems) {
        String menuName = ss.getMenuName();
        asdAll.b("Select " + menuName);
        switch (menuName) {
            case "ResString":
                asdAll.b("Select a resource String");
                selectableItems.add("app_name");
                selectableItems.addAll(readResNames(ResourcePath.getStringPath(sc_id), "string\\sname=\\\"(.*?)\\\""));
                break;

            case "ResStyle":
                asdAll.b("Select a resource Style");
                selectableItems.addAll(readResNames(ResourcePath.getStylePath(sc_id), "style\\sname=\\\"(.*?)\\\""));
                break;

            case "ResColor":
                asdAll.a(2131165472);
                asdAll.b("Select a resource Color");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(defaultColor)));
                selectableItems.addAll(readResNames(ResourcePath.getColorPath(sc_id), "color\\sname=\\\"(.*?)\\\""));
                break;

            case "ResArray":
                asdAll.b("Select a resource String array");
                selectableItems.addAll(readResNames(ResourcePath.getArrayPath(sc_id), "string-array\\sname=\\\"(.*?)\\\""));
                break;

            case "ResDimen":
                asdAll.b("Select a resource Dimension");
                selectableItems.addAll(readResNames(ResourcePath.getDimenPath(sc_id), "dimen\\sname=\\\"(.*?)\\\""));
                break;

            case "ResBool":
                asdAll.b("Select a resource Boolean");
                selectableItems.addAll(readResNames(ResourcePath.getBoolPath(sc_id), "bool\\sname=\\\"(.*?)\\\""));
                break;
            case "ResInteger":
                asdAll.b("Select a resource Integer");
                selectableItems.addAll(readResNames(ResourcePath.getIntegerPath(sc_id), "integer\\sname=\\\"(.*?)\\\""));
                break;

            case "ResAttr":
                asdAll.b("Select a resource Attribute");
                selectableItems.addAll(readResNames(ResourcePath.getAttrPath(sc_id), "integer\\sname=\\\"(.*?)\\\""));
                break;

            case "ResXml":
                asdAll.a(2131166280);
                asdAll.b("Select a resource XML file");
                selectableItems.addAll(getProjectFiles(ResourcePath.getXmlPath(sc_id), ".xml"));
                break;

            case "Assets":
                asdAll.b("Select an Asset file");
                selectableItems.addAll(getProjectFiles(ResourcePath.getAssetsPath(sc_id), ""));
                break;

            case "NativeLib":
                asdAll.a(2131166382);
                asdAll.b("Select a Native Library");
                selectableItems.addAll(getProjectFiles(ResourcePath.getNativePath(sc_id), ".so"));
                break;

            case "AdUnit":
                asdAll.a(2131166209);
                //asdAll.a("If no Ad Units were found, then add and save Project to get added Ad Units.");
                asdAll.b("Select an AdUnit");
                selectableItems.addAll(AdmobReader.getAdUnits(sc_id));
                break;

            case "TestDevice":
                asdAll.a(2131165866);
                //asdAll.a("If no Test Device Found !.\nthen add and save Project to get saved Test Devices.");
                asdAll.b("Select a test device");
                selectableItems.addAll(AdmobReader.getTestDevices(sc_id));
                break;

            case "IntentKey":
                asdAll.b("Select an Intent key");
                selectableItems.addAll(new ArrayList<>(Arrays.asList(intentKey)));
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
                for (Pair<Integer, String> integerStringPair : jC.a(sc_id).k(logic.M.getJavaName())) {
                    selectableItems.add(integerStringPair.second.replaceFirst("^\\w+[\\s]+(\\w+)", "$1"));
                }
                break;

            case "Component":
                asdAll.b("Select a Component");
                for (ComponentBean componentBean : jC.a(sc_id).e(logic.M.getJavaName())) {
                    selectableItems.add(componentBean.componentId);
                }
                break;

            case "CustomVar":
                asdAll.b("Select a Custom Variable");
                for (String s : jC.a(sc_id).e(logic.M.getJavaName(), 5)) {
                    Matcher matcher = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(s);
                    while (matcher.find()) {
                        selectableItems.add(matcher.group(2));
                    }
                }
                break;
        }

        for (String s : jC.a(sc_id).e(logic.M.getJavaName(), 5)) {
            Matcher matcher2 = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(s);
            while (matcher2.find()) {
                if (menuName.equals(matcher2.group(1))) {
                    asdAll.b("Select a " + matcher2.group(1) + " Variable");
                    selectableItems.add(matcher2.group(2));
                }
            }
        }
        for (ComponentBean componentBean : jC.a(sc_id).e(logic.M.getJavaName())) {
            if (componentBean.type > 36 && menuName.equals(ComponentBean.getComponentTypeName(componentBean.type))) {
                asdAll.b("Select a " + ComponentBean.getComponentTypeName(componentBean.type));
                selectableItems.add(componentBean.componentId);
            }
        }
    }
}
