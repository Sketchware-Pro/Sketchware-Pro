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
        if (str2.equals("DatePicker")) {
            return new IconDatePicker(context);
        }
        if (str2.equals("RatingBar")) {
            return new IconRatingBar(context);
        }
        if (str2.equals("SearchView")) {
            return new IconSearchView(context);
        }
        if (str2.equals("DigitalClock")) {
            return new IconDigitalClock(context);
        }
        if (str2.equals("RadioButton")) {
            return new IconRadioButton(context);
        }
        if (str2.equals("GridView")) {
            return new IconGridView(context);
        }
        if (str2.equals("AutoCompleteTextView")) {
            return new IconAutoCompleteTextView(context);
        }
        if (str2.equals("MultiAutoCompleteTextView")) {
            return new IconMultiAutoCompleteTextView(context);
        }
        if (str2.equals("VideoView")) {
            return new IconVideoView(context);
        }
        if (str2.equals("TimePicker")) {
            return new IconTimePicker(context);
        }
        if (str2.equals("AnalogClock")) {
            return new IconAnalogClock(context);
        }
        if (str2.equals("ViewPager")) {
            return new IconViewPager(context);
        }
        if (str2.equals("BadgeView")) {
            return new IconBadgeView(context);
        }
        if (str2.equals("PatternLockView")) {
            return new IconPatternLockView(context);
        }
        if (str2.equals("WaveSideBar")) {
            return new IconWaveSideBar(context);
        }
        if (str2.equals("SignInButton")) {
            return new IconGoogleSignInButton(context);
        }
        if (str2.equals("MaterialButton")) {
            return new IconMaterialButton(context);
        }
        if (str2.equals("CircleImageView")) {
            return new IconCircleImageView(context);
        }
        if (str2.equals("LottieAnimation")) {
            return new IconLottieAnimation(context);
        }
        if (str2.equals("YoutubePlayer")) {
            return new IconYoutubePlayer(context);
        }
        if (str2.equals("OTPView")) {
            return new IconOTPView(context);
        }
        if (str2.equals("CodeView")) {
            return new IconCodeView(context);
        }
        if (str2.equals("RecyclerView")) {
            return new IconRecyclerView(context);
        }
        return null;
    }

    public static View b(Context context, String str, String str2) {
        if (str2.equals("TabLayout")) {
            return new IconTabLayout(context);
        }
        if (str2.equals("BottomNavigationView")) {
            return new IconBottomNavigationView(context);
        }
        if (str2.equals("CollapsingToolbarLayout")) {
            return new IconCollapsingToolbar(context);
        }
        if (str2.equals("SwipeRefreshLayout")) {
            return new IconSwipeRefreshLayout(context);
        }
        if (str2.equals("RadioGroup")) {
            return new IconRadioGroup(context);
        }
        if (str2.equals("CardView")) {
            return new IconCardView(context);
        }
        if (str2.equals("TextInputLayout")) {
            return new IconTextInputLayout(context);
        }
        return null;
    }
}
