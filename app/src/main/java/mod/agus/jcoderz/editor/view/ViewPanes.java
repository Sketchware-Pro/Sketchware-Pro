package mod.agus.jcoderz.editor.view;

import android.content.Context;
import android.view.View;

import com.besome.sketch.beans.ComponentBean;
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

    public static View a(int i, Context context) {
        switch (i) {
            case 19:
                return new ItemRadioButton(context);
            case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return new ItemRatingBar(context);
            case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                return new ItemVideoView(context);
            case 22:
                return new ItemSearchView(context);
            case 23:
                return new ItemAutoCompleteTextView(context);
            case 24:
                return new ItemMultiAutoCompleteTextView(context);
            case 25:
                return new ItemGridView(context);
            case 26:
                return new ItemAnalogClock(context);
            case 27:
                return new ItemDatePicker(context);
            case 28:
                return new ItemTimePicker(context);
            case 29:
                return new ItemDigitalClock(context);
            case 30:
                return new ItemTabLayout(context);
            case 31:
                return new ItemViewPager(context);
            case 32:
                return new ItemBottomNavigationView(context);
            case 33:
                return new ItemBadgeView(context);
            case 34:
                return new ItemPatternLockView(context);
            case 35:
                return new ItemWaveSideBar(context);
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
                return new ItemLinearLayout(context);
            case 41:
                return new ItemMaterialButton(context);
            case 42:
                return new ItemGoogleSignInButton(context);
            case 43:
                return new ItemCircleImageView(context);
            case 44:
                return new ItemLottieAnimation(context);
            case 45:
                return new ItemYoutubePlayer(context);
            case 46:
                return new ItemOTPView(context);
            case 47:
                return new ItemCodeView(context);
            case 48:
                return new ItemRecyclerView(context);
            default:
                return null;
        }
    }
}
