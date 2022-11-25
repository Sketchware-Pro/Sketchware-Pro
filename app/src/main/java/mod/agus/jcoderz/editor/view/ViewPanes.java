package mod.agus.jcoderz.editor.view;

import android.content.Context;
import android.view.View;

import com.besome.sketch.editor.view.item.ItemLinearLayout;

import dev.aldi.sayuti.editor.view.item.ItemBadgeView;
import dev.aldi.sayuti.editor.view.item.ItemBottomNavigationView;
import dev.aldi.sayuti.editor.view.item.ItemCircleImageView;
import dev.aldi.sayuti.editor.view.item.ItemCodeView;
import dev.aldi.sayuti.editor.view.item.ItemGoogleSignInButton;
import dev.aldi.sayuti.editor.view.item.ItemLottieAnimation;
import dev.aldi.sayuti.editor.view.item.ItemMaterialButton;
import dev.aldi.sayuti.editor.view.item.ItemOTPView;
import dev.aldi.sayuti.editor.view.item.ItemPatternLockView;
import dev.aldi.sayuti.editor.view.item.ItemRecyclerView;
import dev.aldi.sayuti.editor.view.item.ItemTabLayout;
import dev.aldi.sayuti.editor.view.item.ItemViewPager;
import dev.aldi.sayuti.editor.view.item.ItemWaveSideBar;
import dev.aldi.sayuti.editor.view.item.ItemYoutubePlayer;
import mod.agus.jcoderz.beans.ViewBeans;
import mod.agus.jcoderz.editor.view.item.ItemAnalogClock;
import mod.agus.jcoderz.editor.view.item.ItemAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.item.ItemDatePicker;
import mod.agus.jcoderz.editor.view.item.ItemDigitalClock;
import mod.agus.jcoderz.editor.view.item.ItemGridView;
import mod.agus.jcoderz.editor.view.item.ItemMultiAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.item.ItemRadioButton;
import mod.agus.jcoderz.editor.view.item.ItemRatingBar;
import mod.agus.jcoderz.editor.view.item.ItemSearchView;
import mod.agus.jcoderz.editor.view.item.ItemTimePicker;
import mod.agus.jcoderz.editor.view.item.ItemVideoView;

public class ViewPanes {
    public static View a(int type, Context context) {
        switch (type) {
            case ViewBeans.VIEW_TYPE_WIDGET_RADIOBUTTON:
                return new ItemRadioButton(context);

            case ViewBeans.VIEW_TYPE_WIDGET_RATINGBAR:
                return new ItemRatingBar(context);

            case ViewBeans.VIEW_TYPE_WIDGET_VIDEOVIEW:
                return new ItemVideoView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_SEARCHVIEW:
                return new ItemSearchView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
                return new ItemAutoCompleteTextView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
                return new ItemMultiAutoCompleteTextView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_GRIDVIEW:
                return new ItemGridView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_ANALOGCLOCK:
                return new ItemAnalogClock(context);

            case ViewBeans.VIEW_TYPE_WIDGET_DATEPICKER:
                return new ItemDatePicker(context);

            case ViewBeans.VIEW_TYPE_WIDGET_TIMEPICKER:
                return new ItemTimePicker(context);

            case ViewBeans.VIEW_TYPE_WIDGET_DIGITALCLOCK:
                return new ItemDigitalClock(context);

            case ViewBeans.VIEW_TYPE_LAYOUT_TABLAYOUT:
                return new ItemTabLayout(context);

            case ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER:
                return new ItemViewPager(context);

            case ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW:
                return new ItemBottomNavigationView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_BADGEVIEW:
                return new ItemBadgeView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_PATTERNLOCKVIEW:
                return new ItemPatternLockView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_WAVESIDEBAR:
                return new ItemWaveSideBar(context);

            case ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW:
            case ViewBeans.VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT:
            case ViewBeans.VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT:
            case ViewBeans.VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT:
            case ViewBeans.VIEW_TYPE_LAYOUT_RADIOGROUP:
                return new ItemLinearLayout(context);

            case ViewBeans.VIEW_TYPE_WIDGET_MATERIALBUTTON:
                return new ItemMaterialButton(context);

            case ViewBeans.VIEW_TYPE_WIDGET_SIGNINBUTTON:
                return new ItemGoogleSignInButton(context);

            case ViewBeans.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW:
                return new ItemCircleImageView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW:
                return new ItemLottieAnimation(context);

            case ViewBeans.VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW:
                return new ItemYoutubePlayer(context);

            case ViewBeans.VIEW_TYPE_WIDGET_OTPVIEW:
                return new ItemOTPView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_CODEVIEW:
                return new ItemCodeView(context);

            case ViewBeans.VIEW_TYPE_WIDGET_RECYCLERVIEW:
                return new ItemRecyclerView(context);

            default:
                return null;
        }
    }
}
