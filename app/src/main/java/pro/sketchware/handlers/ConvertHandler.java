package pro.sketchware.handlers;

import com.besome.sketch.beans.ViewBean;
import mod.agus.jcoderz.beans.ViewBeans;

public class ConvertHandler {
    public static int getTypeByConvert(ViewBean bean) {
        return switch (bean.convert) {
            case "LinearLayout" -> ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
            case "RelativeLayout" -> ViewBean.VIEW_TYPE_LAYOUT_RELATIVE;
            case "HorizontalScrollView" -> ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW;
            case "Button" -> ViewBean.VIEW_TYPE_WIDGET_BUTTON;
            case "TextView" -> ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW;
            case "EditText" -> ViewBean.VIEW_TYPE_WIDGET_EDITTEXT;
            case "ImageView" -> ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW;
            case "WebView" -> ViewBean.VIEW_TYPE_WIDGET_WEBVIEW;
            case "ProgressBar" -> ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR;
            case "ListView" -> ViewBean.VIEW_TYPE_WIDGET_LISTVIEW;
            case "Spinner" -> ViewBean.VIEW_TYPE_WIDGET_SPINNER;
            case "CheckBox" -> ViewBean.VIEW_TYPE_WIDGET_CHECKBOX;
            case "ScrollView" -> ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW;
            case "Switch" -> ViewBean.VIEW_TYPE_WIDGET_SWITCH;
            case "SeekBar" -> ViewBean.VIEW_TYPE_WIDGET_SEEKBAR;
            case "CalendarView" -> ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW;
            case "com.google.android.material.floatingactionbutton.FloatingActionButton" -> ViewBean.VIEW_TYPE_WIDGET_FAB;
            case "com.google.android.gms.ads.AdView" -> ViewBean.VIEW_TYPE_WIDGET_ADVIEW;
            case "com.google.android.gms.maps.MapView" -> ViewBean.VIEW_TYPE_WIDGET_MAPVIEW;
            case "RadioButton" -> ViewBeans.VIEW_TYPE_WIDGET_RADIOBUTTON;
            case "RatingBar" -> ViewBeans.VIEW_TYPE_WIDGET_RATINGBAR;
            case "VideoView" -> ViewBeans.VIEW_TYPE_WIDGET_VIDEOVIEW;
            case "SearchView" -> ViewBeans.VIEW_TYPE_WIDGET_SEARCHVIEW;
            case "AutoCompleteTextView" -> ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW;
            case "MultiAutoCompleteTextView" -> ViewBeans.VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW;
            case "GridView" -> ViewBeans.VIEW_TYPE_WIDGET_GRIDVIEW;
            case "AnalogClock" -> ViewBeans.VIEW_TYPE_WIDGET_ANALOGCLOCK;
            case "DatePicker" -> ViewBeans.VIEW_TYPE_WIDGET_DATEPICKER;
            case "TimePicker" -> ViewBeans.VIEW_TYPE_WIDGET_TIMEPICKER;
            case "DigitalClock" -> ViewBeans.VIEW_TYPE_WIDGET_DIGITALCLOCK;
            case "com.google.android.material.tabs.TabLayout" -> ViewBeans.VIEW_TYPE_LAYOUT_TABLAYOUT;
            case "androidx.viewpager.widget.ViewPager" -> ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER;
            case "com.google.android.material.bottomnavigation.BottomNavigationView" -> ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW;
            case "com.allenliu.badgeview.BadgeView" -> ViewBeans.VIEW_TYPE_WIDGET_BADGEVIEW;
            case "com.andrognito.patternlockview.PatternLockView" -> ViewBeans.VIEW_TYPE_WIDGET_PATTERNLOCKVIEW;
            case "com.sayuti.lib.WaveSideBar" -> ViewBeans.VIEW_TYPE_WIDGET_WAVESIDEBAR;
            case "androidx.cardview.widget.CardView" -> ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW;
            case "com.google.android.material.appbar.CollapsingToolbarLayout" -> ViewBeans.VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT;
            case "com.google.android.material.textfield.TextInputLayout" -> ViewBeans.VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT;
            case "androidx.swiperefreshlayout.widget.SwipeRefreshLayout" -> ViewBeans.VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT;
            case "RadioGroup" -> ViewBeans.VIEW_TYPE_LAYOUT_RADIOGROUP;
            case "com.google.android.material.button.MaterialButton" -> ViewBeans.VIEW_TYPE_WIDGET_MATERIALBUTTON;
            case "com.google.android.gms.common.SignInButton" -> ViewBeans.VIEW_TYPE_WIDGET_SIGNINBUTTON;
            case "de.hdodenhof.circleimageview.CircleImageView" -> ViewBeans.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW;
            case "com.airbnb.lottie.LottieAnimationView" -> ViewBeans.VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW;
            case "com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView" -> ViewBeans.VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW;
            case "affan.ahmad.otp.OTPView" -> ViewBeans.VIEW_TYPE_WIDGET_OTPVIEW;
            case "br.tiagohm.codeview.CodeView" -> ViewBeans.VIEW_TYPE_WIDGET_CODEVIEW;
            case "androidx.recyclerview.widget.RecyclerView" -> ViewBeans.VIEW_TYPE_WIDGET_RECYCLERVIEW;
            case "com.google.android.material.materialswitch.MaterialSwitch" -> ViewBeans.VIEW_TYPE_WIDGET_MATERIALSWITCH;
            default -> bean.type;
        };
    }
}