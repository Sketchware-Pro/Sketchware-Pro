package mod.agus.jcoderz.beans;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class ViewBeans {

    public static final int VIEW_TYPE_WIDGET_RADIOBUTTON = 19;
    public static final int VIEW_TYPE_WIDGET_RATINGBAR = 20;
    public static final int VIEW_TYPE_WIDGET_VIDEOVIEW = 21;
    public static final int VIEW_TYPE_WIDGET_SEARCHVIEW = 22;
    public static final int VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW = 23;
    public static final int VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW = 24;
    public static final int VIEW_TYPE_WIDGET_GRIDVIEW = 25;
    public static final int VIEW_TYPE_WIDGET_ANALOGCLOCK = 26;
    public static final int VIEW_TYPE_WIDGET_DATEPICKER = 27;
    public static final int VIEW_TYPE_WIDGET_TIMEPICKER = 28;
    public static final int VIEW_TYPE_WIDGET_DIGITALCLOCK = 29;
    public static final int VIEW_TYPE_LAYOUT_TABLAYOUT = 30;
    public static final int VIEW_TYPE_LAYOUT_VIEWPAGER = 31;
    public static final int VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW = 32;
    public static final int VIEW_TYPE_WIDGET_BADGEVIEW = 33;
    public static final int VIEW_TYPE_WIDGET_PATTERNLOCKVIEW = 34;
    public static final int VIEW_TYPE_WIDGET_WAVESIDEBAR = 35;
    public static final int VIEW_TYPE_LAYOUT_CARDVIEW = 36;
    public static final int VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT = 37;
    public static final int VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT = 38;
    public static final int VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT = 39;
    public static final int VIEW_TYPE_LAYOUT_RADIOGROUP = 40;
    public static final int VIEW_TYPE_WIDGET_MATERIALBUTTON = 41;
    public static final int VIEW_TYPE_WIDGET_SIGNINBUTTON = 42;
    public static final int VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW = 43;
    public static final int VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW = 44;
    public static final int VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW = 45;
    public static final int VIEW_TYPE_WIDGET_OTPVIEW = 46;
    public static final int VIEW_TYPE_WIDGET_CODEVIEW = 47;
    public static final int VIEW_TYPE_WIDGET_RECYCLERVIEW = 48;

    /**
     * Map that stores both a view's type and type name.
     */
    static BiMap<Integer, String> views = new ImmutableBiMap.Builder<Integer, String>()
            .put(VIEW_TYPE_WIDGET_RADIOBUTTON, "RadioButton")
            .put(VIEW_TYPE_WIDGET_RATINGBAR, "RatingBar")
            .put(VIEW_TYPE_WIDGET_VIDEOVIEW, "VideoView")
            .put(VIEW_TYPE_WIDGET_SEARCHVIEW, "SearchView")
            .put(VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW, "AutoCompleteTextView")
            .put(VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW, "MultiAutoCompleteTextView")
            .put(VIEW_TYPE_WIDGET_GRIDVIEW, "GridView")
            .put(VIEW_TYPE_WIDGET_ANALOGCLOCK, "AnalogClock")
            .put(VIEW_TYPE_WIDGET_DATEPICKER, "DatePicker")
            .put(VIEW_TYPE_WIDGET_TIMEPICKER, "TimePicker")
            .put(VIEW_TYPE_WIDGET_DIGITALCLOCK, "DigitalClock")
            .put(VIEW_TYPE_LAYOUT_TABLAYOUT, "TabLayout")
            .put(VIEW_TYPE_LAYOUT_VIEWPAGER, "ViewPager")
            .put(VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW, "BottomNavigationView")
            .put(VIEW_TYPE_WIDGET_BADGEVIEW, "BadgeView")
            .put(VIEW_TYPE_WIDGET_PATTERNLOCKVIEW, "PatternLockView")
            .put(VIEW_TYPE_WIDGET_WAVESIDEBAR, "WaveSideBar")
            .put(VIEW_TYPE_LAYOUT_CARDVIEW, "CardView")
            .put(VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT, "CollapsingToolbarLayout")
            .put(VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT, "TextInputLayout")
            .put(VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT, "SwipeRefreshLayout")
            .put(VIEW_TYPE_LAYOUT_RADIOGROUP, "RadioGroup")
            .put(VIEW_TYPE_WIDGET_MATERIALBUTTON, "MaterialButton")
            .put(VIEW_TYPE_WIDGET_SIGNINBUTTON, "SignInButton")
            .put(VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW, "CircleImageView")
            .put(VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW, "LottieAnimationView")
            .put(VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW, "YoutubePlayerView")
            .put(VIEW_TYPE_WIDGET_OTPVIEW, "OTPView")
            .put(VIEW_TYPE_WIDGET_CODEVIEW, "CodeView")
            .put(VIEW_TYPE_WIDGET_RECYCLERVIEW, "RecyclerView")
            .build();

    public static String buildClassInfo(int id) {
        return getViewTypeName(id);
    }

    public static int getViewTypeByTypeName(String typeName) {
        return views.inverse().containsKey(typeName) ? views.inverse().get(typeName) : 0;
    }

    public static String getViewTypeName(int id) {
        return views.containsKey(id) ? views.get(id) : "";
    }

    public static int getViewTypeResId(int id) {
        switch (id) {
            case VIEW_TYPE_WIDGET_RADIOBUTTON:
                return 2131166264;

            case VIEW_TYPE_WIDGET_RATINGBAR:
                return 2131165475;

            case VIEW_TYPE_WIDGET_VIDEOVIEW:
                return 2131166259;

            case VIEW_TYPE_WIDGET_SEARCHVIEW:
                return 2131165849;

            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT:
                return 2131166242;

            case VIEW_TYPE_WIDGET_GRIDVIEW:
            case VIEW_TYPE_WIDGET_RECYCLERVIEW:
                return 2131165662;

            case VIEW_TYPE_WIDGET_ANALOGCLOCK:
            case VIEW_TYPE_WIDGET_TIMEPICKER:
            case VIEW_TYPE_WIDGET_DIGITALCLOCK:
                return 2131166276;

            case VIEW_TYPE_WIDGET_DATEPICKER:
                return 2131165519;

            case VIEW_TYPE_LAYOUT_TABLAYOUT:
                return 2131166303;

            case VIEW_TYPE_LAYOUT_VIEWPAGER:
                return 2131166352;

            case VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW:
                return 2131166314;

            case VIEW_TYPE_WIDGET_BADGEVIEW:
                return 2131166257;

            case VIEW_TYPE_WIDGET_PATTERNLOCKVIEW:
                return 2131166308;

            case VIEW_TYPE_WIDGET_WAVESIDEBAR:
                return 2131166312;

            case VIEW_TYPE_LAYOUT_CARDVIEW:
                return 2131166299;

            case VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT:
                return 2131166351;

            case VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT:
                return 2131166320;

            case VIEW_TYPE_LAYOUT_RADIOGROUP:
                return 2131166321;

            case VIEW_TYPE_WIDGET_MATERIALBUTTON:
                return 2131166353;

            case VIEW_TYPE_WIDGET_SIGNINBUTTON:
                return 2131165650;

            case VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW:
                return 2131166354;

            case VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW:
                return 2131166355;

            case VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW:
                return 2131166356;

            case VIEW_TYPE_WIDGET_OTPVIEW:
                return 2131166346;

            case VIEW_TYPE_WIDGET_CODEVIEW:
                return 2131166357;

            default:
                return id;
        }
    }
}
