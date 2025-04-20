package mod.agus.jcoderz.beans;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import pro.sketchware.R;

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
        return switch (id) {
            case VIEW_TYPE_WIDGET_RADIOBUTTON -> R.drawable.ic_mtrl_radio_btn;
            case VIEW_TYPE_WIDGET_RATINGBAR -> R.drawable.ic_mtrl_star;
            case VIEW_TYPE_WIDGET_VIDEOVIEW -> R.drawable.ic_mtrl_video;
            case VIEW_TYPE_WIDGET_SEARCHVIEW -> R.drawable.ic_mtrl_search;
            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW, VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW,
                 VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_GRIDVIEW -> R.drawable.ic_mtrl_grid;
            case VIEW_TYPE_WIDGET_RECYCLERVIEW -> R.drawable.ic_mtrl_list;
            case VIEW_TYPE_WIDGET_ANALOGCLOCK, VIEW_TYPE_WIDGET_TIMEPICKER,
                 VIEW_TYPE_WIDGET_DIGITALCLOCK -> R.drawable.ic_mtrl_time;
            case VIEW_TYPE_WIDGET_DATEPICKER -> R.drawable.ic_mtrl_calendar;
            case VIEW_TYPE_LAYOUT_TABLAYOUT -> R.drawable.ic_mtrl_tabs;
            case VIEW_TYPE_LAYOUT_VIEWPAGER -> R.drawable.ic_mtrl_viewpager;
            case VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW -> R.drawable.ic_mtrl_bottom_navigation;
            case VIEW_TYPE_WIDGET_BADGEVIEW -> R.drawable.ic_mtrl_badge;
            case VIEW_TYPE_WIDGET_PATTERNLOCKVIEW -> R.drawable.ic_mtrl_pattern;
            case VIEW_TYPE_WIDGET_WAVESIDEBAR -> R.drawable.ic_mtrl_sidebar;
            case VIEW_TYPE_LAYOUT_CARDVIEW -> R.drawable.ic_mtrl_rectangle;
            case VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT -> R.drawable.ic_mtrl_collapsing_toolbar;
            case VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT -> R.drawable.ic_mtrl_refresh;
            case VIEW_TYPE_LAYOUT_RADIOGROUP -> R.drawable.ic_mtrl_radio_partitial;
            case VIEW_TYPE_WIDGET_MATERIALBUTTON -> R.drawable.ic_mtrl_button_click;
            case VIEW_TYPE_WIDGET_SIGNINBUTTON -> R.drawable.ic_mtrl_login;
            case VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW -> R.drawable.ic_mtrl_camera;
            case VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW -> R.drawable.ic_mtrl_animation;
            case VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW -> R.drawable.ic_mtrl_youtube;
            case VIEW_TYPE_WIDGET_OTPVIEW -> R.drawable.ic_mtrl_password;
            case VIEW_TYPE_WIDGET_CODEVIEW -> R.drawable.ic_mtrl_terminal;
            default -> id;
        };
    }
}
