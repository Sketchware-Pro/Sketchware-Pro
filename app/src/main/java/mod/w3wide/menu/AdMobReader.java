package mod.w3wide.menu;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.AdUnitBean;

import java.util.ArrayList;

import a.a.a.jC;
import mod.agus.jcoderz.lib.FileUtil;

public class AdMobReader {

    private static final String libPath = (FileUtil.getExternalStorageDir() + "/.sketchware/data/");

    public static ArrayList<String> getAdUnits(String sc_id) {
        ArrayList<String> adUnits = new ArrayList<>();
        for (AdUnitBean bean : jC.c(sc_id).e.adUnits) {
            adUnits.add(bean.id);
        }
        return adUnits;
    }

    public static ArrayList<String> getTestDevices(String sc_id) {
        ArrayList<String> testDevices = new ArrayList<>();
        for (AdTestDeviceBean testDevice : jC.c(sc_id).e.testDevices) {
            testDevices.add(testDevice.deviceId);
        }
        return testDevices;
    }
}