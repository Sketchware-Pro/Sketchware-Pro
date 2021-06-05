package mod.agus.jcoderz.beans;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class ViewBeans {

    // Id : ViewName
    static BiMap<Integer, String> views;

    static {
        // Initialize the views variable
        views = new ImmutableBiMap.Builder<Integer, String>()
                .put(19, "RadioButton")
                .put(20, "RatingBar")
                .put(21, "VideoView")
                .put(22, "SearchView")
                .put(23, "AutoCompleteTextView")
                .put(24, "MultiAutoCompleteTextView")
                .put(25, "GridView")
                .put(26, "AnalogClock")
                .put(27, "DatePicker")
                .put(28, "TimePicker")
                .put(29, "DigitalClock")
                .put(30, "TabLayout")
                .put(31, "ViewPager")
                .put(32, "BottomNavigationView")
                .put(33, "BadgeView")
                .put(34, "PatternLockView")
                .put(35, "WaveSideBar")
                .put(36, "CardView")
                .put(37, "CollapsingToolbarLayout")
                .put(38, "TextInputLayout")
                .put(39, "SwipeRefreshLayout")
                .put(40, "RadioGroup")
                .put(41, "MaterialButton")
                .put(42, "SignInButton")
                .put(43, "CircleImageView")
                .put(44, "LottieAnimationView")
                .put(45, "YoutubePlayerView")
                .put(46, "OTPView")
                .put(47, "CodeView")
                .put(48, "RecyclerView")
                .build();
    }

    public static String buildClassInfo(int i) {
        return getViewTypeName(i);
    }

    public static int getViewTypeByTypeName(String str) {
        return views.inverse().containsKey(str) ? views.inverse().get(str) : 0;
    }

    public static String getViewTypeName(int i) {
        return views.containsKey(i) ? views.get(i) : "";
    }

    public static int getViewTypeResId(int i) {
        switch (i) {
            case 19:
                return 2131166264;
            case 20:
                return 2131165475;
            case 21:
                return 2131166259;
            case 22:
                return 2131165849;
            case 23:
            case 24:
            case 38:
                return 2131166242;
            case 25:
            case 48:
                return 2131165662;
            case 26:
            case 28:
            case 29:
                return 2131166276;
            case 27:
                return 2131165519;
            case 30:
                return 2131166303;
            case 31:
                return 2131166352;
            case 32:
                return 2131166314;
            case 33:
                return 2131166257;
            case 34:
                return 2131166308;
            case 35:
                return 2131166312;
            case 36:
                return 2131166299;
            case 37:
                return 2131166351;
            case 39:
                return 2131166320;
            case 40:
                return 2131166321;
            case 41:
                return 2131166353;
            case 42:
                return 2131165650;
            case 43:
                return 2131166354;
            case 44:
                return 2131166355;
            case 45:
                return 2131166356;
            case 46:
                return 2131166346;
            case 47:
                return 2131166357;
            default:
                return i;
        }
    }
}
