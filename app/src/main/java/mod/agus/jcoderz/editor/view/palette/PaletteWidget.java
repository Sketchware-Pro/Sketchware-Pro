package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;
import android.view.View;

import dev.aldi.sayuti.editor.view.palette.IconBadgeView;
import dev.aldi.sayuti.editor.view.palette.IconBottomNavigationView;
import dev.aldi.sayuti.editor.view.palette.IconCardView;
import dev.aldi.sayuti.editor.view.palette.IconCircleImageView;
import dev.aldi.sayuti.editor.view.palette.IconCodeView;
import dev.aldi.sayuti.editor.view.palette.IconCollapsingToolbar;
import dev.aldi.sayuti.editor.view.palette.IconGoogleSignInButton;
import dev.aldi.sayuti.editor.view.palette.IconLottieAnimation;
import dev.aldi.sayuti.editor.view.palette.IconMaterialButton;
import dev.aldi.sayuti.editor.view.palette.IconOTPView;
import dev.aldi.sayuti.editor.view.palette.IconPatternLockView;
import dev.aldi.sayuti.editor.view.palette.IconRadioGroup;
import dev.aldi.sayuti.editor.view.palette.IconRecyclerView;
import dev.aldi.sayuti.editor.view.palette.IconSwipeRefreshLayout;
import dev.aldi.sayuti.editor.view.palette.IconTabLayout;
import dev.aldi.sayuti.editor.view.palette.IconTextInputLayout;
import dev.aldi.sayuti.editor.view.palette.IconViewPager;
import dev.aldi.sayuti.editor.view.palette.IconWaveSideBar;
import dev.aldi.sayuti.editor.view.palette.IconYoutubePlayer;

public class PaletteWidget {

    public static View a(String str, String str2, String str3, Context context) {
        switch (str2) {
            case "DatePicker":
                return new IconDatePicker(context);

            case "RatingBar":
                return new IconRatingBar(context);

            case "SearchView":
                return new IconSearchView(context);

            case "DigitalClock":
                return new IconDigitalClock(context);

            case "RadioButton":
                return new IconRadioButton(context);

            case "GridView":
                return new IconGridView(context);

            case "AutoCompleteTextView":
                return new IconAutoCompleteTextView(context);

            case "MultiAutoCompleteTextView":
                return new IconMultiAutoCompleteTextView(context);

            case "VideoView":
                return new IconVideoView(context);

            case "TimePicker":
                return new IconTimePicker(context);

            case "AnalogClock":
                return new IconAnalogClock(context);

            case "ViewPager":
                return new IconViewPager(context);

            case "BadgeView":
                return new IconBadgeView(context);

            case "PatternLockView":
                return new IconPatternLockView(context);

            case "WaveSideBar":
                return new IconWaveSideBar(context);

            case "SignInButton":
                return new IconGoogleSignInButton(context);

            case "MaterialButton":
                return new IconMaterialButton(context);

            case "CircleImageView":
                return new IconCircleImageView(context);

            case "LottieAnimation":
                return new IconLottieAnimation(context);

            case "YoutubePlayer":
                return new IconYoutubePlayer(context);

            case "OTPView":
                return new IconOTPView(context);

            case "CodeView":
                return new IconCodeView(context);

            case "RecyclerView":
                return new IconRecyclerView(context);

            default:
                return null;
        }
    }

    public static View b(Context context, String str, String str2) {
        switch (str2) {
            case "TabLayout":
                return new IconTabLayout(context);

            case "BottomNavigationView":
                return new IconBottomNavigationView(context);

            case "CollapsingToolbarLayout":
                return new IconCollapsingToolbar(context);

            case "SwipeRefreshLayout":
                return new IconSwipeRefreshLayout(context);

            case "RadioGroup":
                return new IconRadioGroup(context);

            case "CardView":
                return new IconCardView(context);

            case "TextInputLayout":
                return new IconTextInputLayout(context);

            default:
                return null;
        }
    }
}
