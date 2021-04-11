package mod.w3wide.menu;

import mod.agus.jcoderz.lib.FileUtil;

public class ResourcePath {

    public static String getArrayPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/arrays.xml";
    }

    public static String getColorPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/colors.xml";
    }

    public static String getStringPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/strings.xml";
    }

    public static String getStylePath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/styles.xml";
    }

    public static String getDimenPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/dimens.xml";
    }

    public static String getBoolPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/bools.xml";
    }

    public static String getIntegerPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/integers.xml";
    }

    public static String getAttrPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/attrs.xml";
    }

    public static String getValuesPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/values/values.xml";
    }

    public static String getXmlPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/resource/xml/";
    }

    public static String getNativePath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/native_libs/";
    }

    public static String getAssetsPath(String sc_id) {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/assets/";
    }
}
