package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import a.a.a.FB;
import a.a.a.nA;

public class TutorialStepBean extends nA implements Parcelable {
    public static final int ACTION_AUTO_SCROLL = 4;
    public static final int ACTION_AUTO_WAIT = 2;
    public static final int ACTION_INPUT_WAIT = 1;
    public static final int ACTION_MANUAL_SCROLL = 5;
    public static final int ACTION_MANUAL_WAIT = 3;
    public static final int ACTION_TOUCH_NEXT = 0;
    public static final Parcelable.Creator<TutorialStepBean> CREATOR = new Parcelable.Creator<TutorialStepBean>() {

        @Override
        public TutorialStepBean createFromParcel(Parcel parcel) {
            return new TutorialStepBean(parcel);
        }

        @Override
        public TutorialStepBean[] newArray(int i) {
            return new TutorialStepBean[i];
        }
    };
    public static final int PERMIT_OPTION_EQUAL = 1;
    public static final int PERMIT_OPTION_GREAT = 2;
    public static final int PERMIT_OPTION_LESS = 4;
    public static final int PERMIT_OPTION_NOT = 6;
    public static final int PERMIT_OPTION_SHIFT = 4;
    public static final String SCROLL_DIRECTION_BOTTOM = "bottom";
    public static final String SCROLL_DIRECTION_LEFT = "left";
    public static final String SCROLL_DIRECTION_RIGHT = "right";
    public static final String SCROLL_DIRECTION_TOP = "top";
    public static final int TARGET_ACTION_BACK_PRESS = 8;
    public static final int TARGET_ACTION_CLICK = 1;
    public static final int TARGET_ACTION_DRAG_CANCEL = 3;
    public static final int TARGET_ACTION_DRAG_START = 2;
    public static final int TARGET_ACTION_ITEM_ADD = 3;
    public static final int TARGET_ACTION_ITEM_MOVE = 4;
    public static final int TARGET_ACTION_ITEM_REMOVE = 5;
    public static final int TARGET_ACTION_NONE = 0;
    public static final int TARGET_ACTION_PARAM_ADD = 6;
    public static final int TARGET_ACTION_PARAM_CLICK = 7;
    public static final String TARGET_ID_CLOSE_PROPERTIES = "close_properties";
    public static final String TARGET_ID_COMPONENT_ADD = "component_add";
    public static final String TARGET_ID_COMPONENT_ITEM = "component_item";
    public static final String TARGET_ID_CONTEXT_MENU = "context_menu";
    public static final String TARGET_ID_EDIT_PROPERTIES = "edit_properties";
    public static final String TARGET_ID_EVENT_ADD = "event_add";
    public static final String TARGET_ID_EVENT_ITEM = "event_item";
    public static final String TARGET_ID_EXECUTE = "execute";
    public static final String TARGET_ID_FAB = "fab";
    public static final String TARGET_ID_FILE_SELECTOR = "file_selector";
    public static final String TARGET_ID_IMG_ORIENTATION = "img_orientation";
    public static final String TARGET_ID_LOGIC_BLOCK_COPY = "block_copy";
    public static final String TARGET_ID_LOGIC_BLOCK_PASTE = "block_paste";
    public static final String TARGET_ID_MNG_IMAGE_ADD = "add_image";
    public static final String TARGET_ID_MNG_IMAGE_IMPORT = "import_image";
    public static final String TARGET_ID_MNG_IMAGE_ITEM = "image_item";
    public static final String TARGET_ID_MNG_IMAGE_REMOVE = "remove_image";
    public static final String TARGET_ID_MNG_SCREEN_ADD = "add_screen";
    public static final String TARGET_ID_MNG_SCREEN_ITEM = "screen_item";
    public static final String TARGET_ID_MNG_SOUND_ADD = "add_sound";
    public static final String TARGET_ID_MNG_SOUND_ITEM = "sound_item";
    public static final String TARGET_ID_OPTION_MENU = "option_menu";
    public static final String TARGET_ID_OPTION_MENU_MNG_IMAGE = "mng_image";
    public static final String TARGET_ID_OPTION_MENU_MNG_SCREEN = "mng_screen";
    public static final String TARGET_ID_OPTION_MENU_MNG_SOUND = "mng_sound";
    public static final String TARGET_ID_OPTION_MENU_WIDGET_HELPER = "option_menu_widget_helper";
    public static final String TARGET_ID_PROPERTIES = "properties";
    public static final String TARGET_ID_SCROLL_BOTTOM = "scroll_bottom";
    public static final String TARGET_ID_SCROLL_LEFT = "scroll_left";
    public static final String TARGET_ID_SCROLL_RIGHT = "scroll_right";
    public static final String TARGET_ID_SCROLL_TOP = "scroll_top";
    public static final String TARGET_ID_SPN_WIDGET = "spn_widget";
    public static final String TARGET_ID_TAB_ITEM_1 = "tab_item_1";
    public static final String TARGET_ID_TAB_ITEM_2 = "tab_item_2";
    public static final int TARGET_OPTION_BLOCK_ALL_CONNECTED = 1;
    public static final int TARGET_OPTION_BLOCK_ITSELF = 0;
    public static final int TARGET_TYPE_ICON = 1;
    public static final int TARGET_TYPE_ITEM = 2;
    public static final int TARGET_TYPE_LOGIC_BLOCK = 7;
    public static final int TARGET_TYPE_LOGIC_BLOCK_INSERTION = 8;
    public static final int TARGET_TYPE_LOGIC_BLOCK_PARAM = 9;
    public static final int TARGET_TYPE_LOGIC_CATEGORY = 5;
    public static final int TARGET_TYPE_LOGIC_PALETTE = 6;
    public static final int TARGET_TYPE_NONE = 0;
    public static final int TARGET_TYPE_PROPERTY = 4;
    public static final int TARGET_TYPE_VIEW = 3;
    public int action;
    public int delayAfter;
    public String desc;
    public int descSize;
    public String failedMessage;
    public int failedTooltipTextColor;
    public int overlayColor;
    public int overlayPadding;
    public int permittedAction;
    public int permittedOption;
    public Object[] permittedValue;
    public int pointerColor;
    public int pointerGravity;
    public int pointerOffsetX;
    public int pointerOffsetY;
    public String targetId;
    public int targetOption;
    public int targetType;
    public String title;
    public int titleSize;
    public int tooltipColor;
    public int tooltipGravity;
    public int tooltipTextColor;

    public TutorialStepBean() {
        this.action = 0;
        this.targetType = 0;
        this.targetId = "";
        this.targetOption = 0;
        this.permittedAction = 0;
        this.permittedOption = 1;
        this.permittedValue = new Object[3];
        this.overlayColor = 0x99000000;
        this.overlayPadding = 0;
        this.pointerColor = 0x80f44336;
        this.pointerGravity = 17;
        this.pointerOffsetX = 0;
        this.pointerOffsetY = 0;
        this.tooltipColor = 0xfffff176;
        this.tooltipTextColor = 0xff212121;
        this.tooltipGravity = 51;
        this.title = "";
        this.titleSize = 30;
        this.desc = "";
        this.descSize = 16;
        this.failedMessage = "";
        this.failedTooltipTextColor = 0xffff0000;
        this.delayAfter = 0;
    }

    public TutorialStepBean(Parcel parcel) {
        this.action = parcel.readInt();
        this.targetType = parcel.readInt();
        this.targetId = parcel.readString();
    }

    public static Parcelable.Creator<TutorialStepBean> getCreator() {
        return CREATOR;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x007f A[RETURN] */
    private boolean isPermitted(int i, Object obj) {
        if (obj == null) {
            return false;
        }
        int permitOption = getPermitOption(i);
        Object obj2 = this.permittedValue[i];
        boolean z = true;
        if (FB.b(obj.toString()) && FB.b(obj2.toString())) {
            double parseDouble = Double.parseDouble(obj.toString());
            double parseDouble2 = Double.parseDouble(obj2.toString());
            if ((permitOption & 1) == 1 && parseDouble == parseDouble2) {
                return true;
            }
            if ((permitOption & 2) == 2 && parseDouble > parseDouble2) {
                return true;
            }
            return (permitOption & 4) == 4 && parseDouble < parseDouble2;
        } else if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int[] iArr2 = (int[]) obj2;
            if (iArr.length != iArr2.length) {
                return false;
            }
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (iArr[i2] != iArr2[i2]) {
                    z = false;
                }
            }
            return z;
        } else if ((permitOption & 1) == 1 && obj2.equals(obj)) {
            return true;
        } else {
            return (permitOption & 6) == 6 && !obj2.equals(obj);
        }
    }

    public void addPermitOption(int i, int i2, Object obj) {
        int i3 = this.permittedOption;
        int i4 = i3 % 16;
        int i5 = (i3 >> 4) % 16;
        int i6 = ((i3 >> 4) >> 4) % 16;
        if (i == 0) {
            i4 = i2;
        } else if (i == 1) {
            i5 = i2;
        } else if (i == 2) {
            i6 = i2;
        }
        this.permittedOption = (i5 << 4) | i4 | ((i6 << 4) << 4);
        this.permittedValue[i] = obj;
    }

    public void copy(TutorialStepBean tutorialStepBean) {
        this.action = tutorialStepBean.action;
        this.targetType = tutorialStepBean.targetType;
        this.targetId = tutorialStepBean.targetId;
    }

    public int describeContents() {
        return 0;
    }

    public int getPermitOption(int i) {
        return (this.permittedOption >> (i * 4)) % 16;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.action);
        parcel.writeInt(this.targetType);
        parcel.writeString(this.targetId);
    }

    public boolean isPermitted(Object obj) {
        return isPermitted(0, obj);
    }

    public boolean isPermitted(Object obj, Object obj2) {
        return isPermitted(0, obj) && isPermitted(1, obj2);
    }

    public boolean isPermitted(Object obj, Object obj2, Object obj3) {
        return isPermitted(0, obj) && isPermitted(1, obj2) && isPermitted(2, obj3);
    }
}
