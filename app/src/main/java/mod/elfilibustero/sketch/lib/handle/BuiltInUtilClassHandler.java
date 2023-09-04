package mod.elfilibustero.sketch.lib.handle;

import a.a.a.eC;
import a.a.a.hC;

import com.besome.sketch.beans.ComponentBean;

public class BuiltInUtilClassHandler {

    /**
     * Check if any SketchwareUtil block is used in the project.
     */
    public static boolean isSketchwareUtilBlockUsed(hC projectFileManager, eC projectDataManager) {
        for (var activity : projectFileManager.b()) {
            for (var entry : projectDataManager.b(activity.getJavaName()).entrySet()) {
                for (var blockBean : entry.getValue()) {
                    if (isSketchwareUtilPresent(blockBean.opCode)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Check if specific SketchwareUtil block is present.
     */
    public static boolean isSketchwareUtilPresent(String opCode) {
        String[] blocks = {
            "copyAssetFile", "customToast", "customToastWithIcon", "doToast", "getLocationX", "getLocationY",
            "hideKeyboard", "imageCrop", "interstitialAdShow", "isConnected", "listGetCheckedPositions", "mapGetAllKeys",
            "mathGetDip", "mathGetDisplayHeight", "mathGetDisplayWidth", "random", "showKeyboard", "sortListmap"
        };

        for (String block : blocks) {
            if (opCode.equals(block)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if any FileUtil block is used in the project.
     */
    public static boolean isFileUtilBlockUsed(hC projectFileManager, eC projectDataManager) {
        for (var activity : projectFileManager.b()) {
            for (var entry : projectDataManager.b(activity.getJavaName()).entrySet()) {
                for (var blockBean : entry.getValue()) {
                    if (isFileUtilPresent(blockBean.opCode)) {
                        return true;
                    }
                }
            }
        }

        for (var activity : projectFileManager.b()) {
            for (var component : projectDataManager.e(activity.getJavaName())) {
                if (component.type == ComponentBean.COMPONENT_TYPE_FILE_PICKER || component.type == ComponentBean.COMPONENT_TYPE_CAMERA) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if specific FileUtil block is present.
     */
    public static boolean isFileUtilPresent(String opCode) {
        String[] blocks = {
            "cropBitmapFileFromCenter", "fileutilcopy", "fileutilcopydir", "fileutildelete", "fileutilisdir",
            "fileutilisexist", "fileutilisfile", "fileutillength", "fileutilmakedir", "fileutilmove",
            "fileutilread", "fileutilwrite", "fileutillistdir", "getExternalStorageDir", "getJpegRotate",
            "getPackageDataDir", "getPublicDir", "resizeBitmapFileRetainRatio", "resizeBitmapFileToCircle",
            "resizeBitmapFileToSquare", "rotateBitmapFile", "scaleBitmapFile", "setBitmapFileBrightness",
            "setBitmapFileColorFilter", "setBitmapFileContrast", "setImageFilePath", "skewBitmapFile"
        };

        for (String name : blocks) {
            if (opCode.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
