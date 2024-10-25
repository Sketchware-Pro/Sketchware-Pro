package mod.agus.jcoderz.beans;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.sketchware.remod.R;

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
                return R.drawable.widget_radio_button;

            case VIEW_TYPE_WIDGET_RATINGBAR:
                return R.drawable.color_star_24;

            case VIEW_TYPE_WIDGET_VIDEOVIEW:
                return R.drawable.widget_mediaplayer;

            case VIEW_TYPE_WIDGET_SEARCHVIEW:
                return R.drawable.ic_search_color_96dp;

            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT:
                return R.drawable.widget_edit_text;

            case VIEW_TYPE_WIDGET_GRIDVIEW:
            case VIEW_TYPE_WIDGET_RECYCLERVIEW:
                return R.drawable.grid_3_48;

            case VIEW_TYPE_WIDGET_ANALOGCLOCK:
            case VIEW_TYPE_WIDGET_TIMEPICKER:
            case VIEW_TYPE_WIDGET_DIGITALCLOCK:
                return R.drawable.widget_timer;

            case VIEW_TYPE_WIDGET_DATEPICKER:
                return R.drawable.date_span_96;

            case VIEW_TYPE_LAYOUT_TABLAYOUT:
                return R.drawable.widget_tab_layout;

            case VIEW_TYPE_LAYOUT_VIEWPAGER:
                return R.drawable.widget_view_pager;

            case VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW:
                return R.drawable.widget_bottom_view;

            case VIEW_TYPE_WIDGET_BADGEVIEW:
                return R.drawable.item_badge_view;

            case VIEW_TYPE_WIDGET_PATTERNLOCKVIEW:
                return R.drawable.widget_pattern_lock_view;

            case VIEW_TYPE_WIDGET_WAVESIDEBAR:
                return R.drawable.widget_wave_side_bar;

            case VIEW_TYPE_LAYOUT_CARDVIEW:
                return R.drawable.widget_cardview;

            case VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT:
                return R.drawable.widget_collapsing_toolbar;

            case VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT:
                return R.drawable.widget_swipe_refresh;

            case VIEW_TYPE_LAYOUT_RADIOGROUP:
                return R.drawable.widget_radiogroup;

            case VIEW_TYPE_WIDGET_MATERIALBUTTON:
                return R.drawable.widget_material_button;

            case VIEW_TYPE_WIDGET_SIGNINBUTTON:
                return R.drawable.google_48;

            case VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW:
                return R.drawable.widget_circle_image;

            case VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW:
                return R.drawable.widget_lottie;

            case VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW:
                return R.drawable.widget_youtube;

            case VIEW_TYPE_WIDGET_OTPVIEW:
                return R.drawable.event_google_signin;

            case VIEW_TYPE_WIDGET_CODEVIEW:
                return R.drawable.widget_code_view;

            default:
                return id;
        }
    }
}
