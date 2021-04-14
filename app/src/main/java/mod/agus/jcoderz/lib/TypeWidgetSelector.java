package mod.agus.jcoderz.lib;

import com.besome.sketch.beans.ComponentBean;

public class TypeWidgetSelector {
    public static String a(int i) {
        switch (i) {
            case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return "ratingbar";
            case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                return "videoview";
            case 22:
                return "searchview";
            case 23:
                return "autocomplete";
            case 24:
                return "multiautocomplete";
            case 25:
                return "gridview";
            case 26:
                return "analogclock";
            case 27:
                return "datepicker";
            case 28:
                return "timepicker";
            case 29:
                return "digitalclock";
            case 30:
                return "tablayout";
            case 31:
                return "viewpager";
            case 32:
                return "bottomnavigation";
            case 33:
                return "badgeview";
            case 34:
                return "patternlockview";
            case 35:
                return "wavesidebar";
            case 36:
                return "cardview";
            case 37:
                return "collapsingtoolbar";
            case 38:
                return "textinputlayout";
            case 39:
                return "swiperefreshlayout";
            case 40:
                return "radiogroup";
            case 41:
                return "materialbutton";
            case 42:
                return "signinbutton";
            case 43:
                return "circleimageview";
            case 44:
                return "lottie";
            case 45:
                return "youtube";
            case 46:
                return "otpview";
            case 47:
                return "codeview";
            case 48:
                return "recyclerview";
            default:
                return "widget";
        }
    }
}
