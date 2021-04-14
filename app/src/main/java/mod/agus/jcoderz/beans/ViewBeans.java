package mod.agus.jcoderz.beans;

public class ViewBeans {
    public static String buildClassInfo(int i) {
        switch (i) {
            case 19:
                return "RadioButton";
            case 20:
                return "RatingBar";
            case 21:
                return "VideoView";
            case 22:
                return "SearchView";
            case 23:
                return "AutoCompleteTextView";
            case 24:
                return "MultiAutoCompleteTextView";
            case 25:
                return "GridView";
            case 26:
                return "AnalogClock";
            case 27:
                return "DatePicker";
            case 28:
                return "TimePicker";
            case 29:
                return "DigitalClock";
            case 30:
                return "TabLayout";
            case 31:
                return "ViewPager";
            case 32:
                return "BottomNavigationView";
            case 33:
                return "BadgeView";
            case 34:
                return "PatternLockView";
            case 35:
                return "WaveSideBar";
            case 36:
                return "CardView";
            case 37:
                return "CollapsingToolbarLayout";
            case 38:
                return "TextInputLayout";
            case 39:
                return "SwipeRefreshLayout";
            case 40:
                return "RadioGroup";
            case 41:
                return "MaterialButton";
            case 42:
                return "SignInButton";
            case 43:
                return "CircleImageView";
            case 44:
                return "LottieAnimationView";
            case 45:
                return "YoutubePlayerView";
            case 46:
                return "OTPView";
            case 47:
                return "CodeView";
            case 48:
                return "RecyclerView";
            default:
                return "";
        }
    }

    public static int getViewTypeByTypeName(String str) {
        switch (str.hashCode()) {
            case -1946472170:
                if (str.equals("RatingBar")) {
                    return 20;
                }
                return -1;
            case -1865955844:
                if (!str.equals("DatePicker")) {
                    return 27;
                }
                break;
            case -1604708901:
                if (str.equals("TimePicker")) {
                    return 28;
                }
                break;
            case -1390941494:
                if (str.equals("LottieAnimationView")) {
                    return 44;
                }
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    return 24;
                }
                break;
            case -957993568:
                if (str.equals("VideoView")) {
                    return 21;
                }
                break;
            case -903281921:
                if (str.equals("TabLayout")) {
                    return 30;
                }
                break;
            case -855591024:
                if (str.equals("CircleImageView")) {
                    return 43;
                }
                break;
            case -803274158:
                if (str.equals("CodeView")) {
                    return 47;
                }
                break;
            case -463051541:
                if (str.equals("SwipeRefreshLayout")) {
                    return 39;
                }
                break;
            case -420256688:
                if (str.equals("OTPView")) {
                    return 46;
                }
                break;
            case -386865015:
                if (str.equals("YoutubePlayerView")) {
                    return 45;
                }
                break;
            case -344433152:
                if (str.equals("PatternLockView")) {
                    return 34;
                }
                break;
            case -237065308:
                if (str.equals("RecyclerView")) {
                    return 48;
                }
                break;
            case -178200376:
                if (str.equals("BadgeView")) {
                    return 33;
                }
                break;
            case 56460789:
                if (str.equals("CardView")) {
                    return 36;
                }
                break;
            case 269377782:
                if (str.equals("DigitalClock")) {
                    return 29;
                }
                break;
            case 382765867:
                if (str.equals("GridView")) {
                    return 25;
                }
                break;
            case 416531454:
                if (str.equals("ViewPager")) {
                    return 31;
                }
                break;
            case 647744519:
                if (str.equals("TextInputLayout")) {
                    return 38;
                }
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    return 19;
                }
                if (!str.equals("DatePicker")) {
                }
                break;
            case 1200917337:
                if (str.equals("MaterialButton")) {
                    return 41;
                }
                break;
            case 1283054733:
                if (str.equals("SearchView")) {
                    return 22;
                }
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    return 23;
                }
                break;
            case 1550886371:
                if (str.equals("WaveSideBar")) {
                    return 35;
                }
                break;
            case 1581407579:
                if (str.equals("CollapsingToolbarLayout")) {
                    return 37;
                }
                break;
            case 1623488948:
                if (str.equals("SignInButton")) {
                    return 42;
                }
                break;
            case 1657963012:
                if (str.equals("BottomNavigationView")) {
                    return 32;
                }
                break;
            case 1778827486:
                if (str.equals("AnalogClock")) {
                    return 26;
                }
                break;
            case 1969230692:
                if (str.equals("RadioGroup")) {
                    return 40;
                }
                break;
        }
        return 0;
    }

    public static String getViewTypeName(int i) {
        switch (i) {
            case 19:
                return "RadioButton";
            case 20:
                return "RatingBar";
            case 21:
                return "VideoView";
            case 22:
                return "SearchView";
            case 23:
                return "AutoCompleteTextView";
            case 24:
                return "MultiAutoCompleteTextView";
            case 25:
                return "GridView";
            case 26:
                return "AnalogClock";
            case 27:
                return "DatePicker";
            case 28:
                return "TimePicker";
            case 29:
                return "DigitalClock";
            case 30:
                return "TabLayout";
            case 31:
                return "ViewPager";
            case 32:
                return "BottomNavigationView";
            case 33:
                return "BadgeView";
            case 34:
                return "PatternLockView";
            case 35:
                return "WaveSideBar";
            case 36:
                return "CardView";
            case 37:
                return "CollapsingToolbarLayout";
            case 38:
                return "TextInputLayout";
            case 39:
                return "SwipeRefreshLayout";
            case 40:
                return "RadioGroup";
            case 41:
                return "MaterialButton";
            case 42:
                return "SignInButton";
            case 43:
                return "CircleImageView";
            case 44:
                return "LottieAnimationView";
            case 45:
                return "YoutubePlayerView";
            case 46:
                return "OTPView";
            case 47:
                return "CodeView";
            case 48:
                return "RecyclerView";
            default:
                return "";
        }
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
                return 2131165662;
            case 26:
                return 2131166276;
            case 27:
                return 2131165519;
            case 28:
                return 2131166276;
            case 29:
                return 2131166276;
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
            case 48:
                return 2131165662;
            default:
                return i;
        }
    }
}
