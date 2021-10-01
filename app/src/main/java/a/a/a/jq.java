package a.a.a;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;

import java.util.ArrayList;
import java.util.HashMap;

import mod.agus.jcoderz.handle.component.ConstVarComponent;

public class jq {

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#CALL_PHONE
     */
    public static final int PERMISSION_CALL_PHONE = 1;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#INTERNET
     */
    public static final int PERMISSION_INTERNET = 2;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#VIBRATE
     */
    public static final int PERMISSION_VIBRATE = 4;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#ACCESS_NETWORK_STATE
     */
    public static final int PERMISSION_ACCESS_NETWORK_STATE = 8;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#CAMERA
     */
    public static final int PERMISSION_CAMERA = 16;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#READ_EXTERNAL_STORAGE
     */
    public static final int PERMISSION_READ_EXTERNAL_STORAGE = 32;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#WRITE_EXTERNAL_STORAGE
     */
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 64;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#RECORD_AUDIO
     */
    public static final int PERMISSION_RECORD_AUDIO = 128;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#BLUETOOTH
     */
    public static final int PERMISSION_BLUETOOTH = 256;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#BLUETOOTH_ADMIN
     */
    public static final int PERMISSION_BLUETOOTH_ADMIN = 512;

    /**
     * A permission type for {@link jq#a(String, int)}, {@link a#a(int)} and {@link a#b(int)}.
     *
     * @see android.Manifest.permission#ACCESS_FINE_LOCATION
     */
    public static final int PERMISSION_ACCESS_FINE_LOCATION = 1024;

    /**
     * Project's package name,
     * e.g. com.jbk.internal.demo.
     */
    public String a = "";

    /**
     * Application's name of project,
     * e.g. Sketchware Pro Remod Remod Demo.
     */
    public String b;

    /**
     * Project version code,
     * e.g. 1
     */
    public String c;

    /**
     * Project's version name,
     * e.g. 1.0
     */
    public String d;

    public boolean e = false;

    /**
     * If we're building a debug APK, meaning that e.g. test ads should be used.
     */
    public boolean f = false;

    /**
     * If the built-in library AppCompat (contains the Jetpack AppCompat artefacts and
     * Material Design Components) is enabled.
     */
    public boolean g = false;

    /**
     * If the built-in library Firebase is enabled.
     */
    public boolean h = false;

    /**
     * If there's at least 1 Firebase Auth Component in the project.
     */
    public boolean i = false;

    /**
     * If there's at least 1 Firebase Database Component in the project.
     */
    public boolean j = false;

    /**
     * If there's at least 1 Firebase Storage Component in the project.
     */
    public boolean k = false;

    /**
     * If the built-in library AdMob is enabled.
     */
    public boolean l = false;

    /**
     * If the built-in library Google Map is enabled.
     */
    public boolean m = false;

    /**
     * If at least 1 block in the project uses the built-in library Glide.
     */
    public boolean n = false;

    /**
     * If at least 1 block in the project uses the built-in library Gson.
     */
    public boolean o = false;

    /**
     * If at least 1 component in the project uses the built-in library OkHttp3.
     */
    public boolean p = false;

    /**
     * Permissions of the whole project, stored as bitfield.
     */
    public int q = 0;

    /**
     * Configured ad unit ID for banner ads. Set to demo ad unit provided by Google by default.
     */
    public String r = "ca-app-pub-3940256099942544/6300978111";

    /**
     * Configured ad unit ID for interstitial ads. Set to demo ad unit provided by Google by default.
     */
    public String s = "ca-app-pub-3940256099942544/1033173712";

    public String appId = "";

    public String sc_id = "";

    /**
     * Device IDs registered as Test Devices for the current project.
     */
    public ArrayList<String> t = new ArrayList<>();
    public boolean u = false;

    /**
     * (Most likely) the Google Maps API key associated with the project.
     */
    public String v = "";

    /**
     * Map containing permissions of Activities. Activity name = {@link String},
     * Permissions container = {@link a}.
     */
    public HashMap<String, a> w = new HashMap<>();
    public ConstVarComponent x = new ConstVarComponent();

    public boolean a() {
        return q == 0;
    }

    public void b() {
    }

    /**
     * @return True if the permission was registered on the project.
     */
    public boolean b(int permission) {
        return (q & permission) == permission;
    }

    /**
     * Registers a permission for the entire project.
     */
    public void a(int permission) {
        q = permission | q;
    }

    /**
     * (Most likely) sets the Google Maps API key.
     */
    public void b(ProjectLibraryBean googleMapsLibrary) {
        String data;
        if (googleMapsLibrary != null && (data = googleMapsLibrary.data) != null) {
            v = data;
        }
    }

    /**
     * Registers a permission for an Activity.
     */
    public void a(String activityName, int permission) {
        if (!w.containsKey(activityName)) {
            w.put(activityName, new a());
        }
        w.get(activityName).a(permission);
        a(permission);
    }

    /**
     * @return The permissions object for the Activity
     */
    public a a(String activityName) {
        if (!w.containsKey(activityName)) {
            w.put(activityName, new a());
        }
        return w.get(activityName);
    }

    public void a(ProjectLibraryBean projectLibraryBean) {
        ArrayList<AdTestDeviceBean> testDevices;
        if (!(projectLibraryBean == null || (testDevices = projectLibraryBean.testDevices) == null || testDevices.size() <= 0)) {
            for (AdTestDeviceBean adTestDeviceBean : projectLibraryBean.testDevices) {
                t.add(adTestDeviceBean.deviceId);
            }
        }
        String reserved1;
        if (!(projectLibraryBean == null || (reserved1 = projectLibraryBean.reserved1) == null || reserved1.isEmpty())) {
            r = reserved1.substring(reserved1.lastIndexOf(" : ") + 3);
        }
        String reserved2;
        if (projectLibraryBean != null && (reserved2 = projectLibraryBean.reserved2) != null && !reserved2.isEmpty()) {
            this.s = reserved2.substring(reserved2.lastIndexOf(" : ") + 3);
        }
    }

    public static class a {
        /**
         * True if Activity contains a Drawer.
         */
        public boolean a = false;

        /**
         * True if Activity uses Firebase Auth.
         */
        public boolean b = false;

        /**
         * The permissions bitfield.
         */
        public int c = 0;

        public a() {
        }

        public void a(int i) {
            c = i | c;
        }

        public boolean b(int i) {
            return (this.c & i) == i;
        }

        /**
         * @return (Most likely) true if the Activity associated with this {@link a} object
         * will request any runtime permissions
         */
        public boolean a() {
            return b(PERMISSION_CALL_PHONE)
                    || b(PERMISSION_READ_EXTERNAL_STORAGE) || b(PERMISSION_WRITE_EXTERNAL_STORAGE)
                    || b(PERMISSION_CAMERA)
                    || b(PERMISSION_RECORD_AUDIO)
                    || b(PERMISSION_ACCESS_FINE_LOCATION);
        }
    }
}
