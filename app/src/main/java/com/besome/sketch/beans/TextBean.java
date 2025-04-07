package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import a.a.a.nA;

public class TextBean extends nA implements Parcelable {
    public static final Parcelable.Creator<TextBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public TextBean createFromParcel(Parcel source) {
            return new TextBean(source);
        }

        @Override
        public TextBean[] newArray(int size) {
            return new TextBean[size];
        }
    };

    public static int IME_OPTION_DONE = 6;
    public static int IME_OPTION_GO = 2;
    public static int IME_OPTION_NEXT = 5;
    public static int IME_OPTION_NONE = 1;
    public static int IME_OPTION_NORMAL = 0;
    public static int IME_OPTION_SEARCH = 3;
    public static int IME_OPTION_SEND = 4;
    public static int INPUT_TYPE_NUMBER_DECIMAL = 8194;
    public static int INPUT_TYPE_NUMBER_SIGNED = 4098;
    public static int INPUT_TYPE_NUMBER_SIGNED_DECIMAL = 12290;
    public static int INPUT_TYPE_PASSWORD = 129;
    public static int INPUT_TYPE_PHONE = 3;
    public static int INPUT_TYPE_TEXT = 1;
    public static String TEXT_FONT = "default_font";
    public static int TEXT_TYPE_BOLD = 1;
    public static int TEXT_TYPE_BOLDITALIC = 3;
    public static int TEXT_TYPE_ITALIC = 2;
    public static int TEXT_TYPE_NORMAL;
    @Expose
    public String hint;
    @Expose
    public int hintColor;
    @Expose
    public String resHintColor;
    @Expose
    public int imeOption;
    @Expose
    public int inputType;
    @Expose
    public int line;
    @Expose
    public int singleLine;
    @Expose
    public String text;
    @Expose
    public int textColor;
    @Expose
    public String resTextColor;
    @Expose
    public String textFont;
    @Expose
    public int textSize;
    @Expose
    public int textType;

    public TextBean() {
        text = "";
        textSize = 12;
        textType = TEXT_TYPE_NORMAL;
        textColor = 0xff000000;
        hint = "";
        hintColor = 0xff607d8b;
        singleLine = 0;
        line = 0;
        inputType = INPUT_TYPE_TEXT;
        imeOption = IME_OPTION_NORMAL;
        textFont = TEXT_FONT;
    }

    public TextBean(Parcel parcel) {
        text = parcel.readString();
        textSize = parcel.readInt();
        textColor = parcel.readInt();
        textType = parcel.readInt();
        textFont = parcel.readString();
        hint = parcel.readString();
        hintColor = parcel.readInt();
        singleLine = parcel.readInt();
        line = parcel.readInt();
        inputType = parcel.readInt();
        imeOption = parcel.readInt();
        resTextColor = parcel.readString();
        resHintColor = parcel.readString();
    }

    public static Parcelable.Creator<TextBean> getCreator() {
        return CREATOR;
    }

    public void copy(TextBean textBean) {
        text = textBean.text;
        textSize = textBean.textSize;
        textColor = textBean.textColor;
        textType = textBean.textType;
        textFont = textBean.textFont;
        hint = textBean.hint;
        hintColor = textBean.hintColor;
        singleLine = textBean.singleLine;
        line = textBean.line;
        inputType = textBean.inputType;
        imeOption = textBean.imeOption;
        resHintColor = textBean.resHintColor;
        resTextColor = textBean.resTextColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEqual(TextBean textBean) {
        String str = text;
        if (str != null) {
            String str2 = textBean.text;
            if (!str.equals(str2)) {
                return false;
            }
        } else if (textBean.text != null) {
            return false;
        }
        if (textSize != textBean.textSize || textColor != textBean.textColor || textType != textBean.textType || resTextColor != textBean.resTextColor || resHintColor != textBean.resTextColor) { //new
            return false;
        }
        String str3 = textFont;
        if (str3 != null) {
            String str4 = textBean.textFont;
            if (!str3.equals(str4)) {
                return false;
            }
        } else if (textBean.textFont != null) {
            return false;
        }
        String str5 = hint;
        if (str5 != null) {
            String str6 = textBean.hint;
            if (!str5.equals(str6)) {
                return false;
            }
        } else if (textBean.hint != null) {
            return false;
        }
        return hintColor == textBean.hintColor && singleLine == textBean.singleLine && line == textBean.line && inputType == textBean.inputType && imeOption == textBean.imeOption || resTextColor == textBean.resTextColor || resHintColor == textBean.resTextColor; //new
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(textSize);
        parcel.writeInt(textColor);
        parcel.writeInt(textType);
        parcel.writeString(textFont);
        parcel.writeString(hint);
        parcel.writeInt(hintColor);
        parcel.writeInt(singleLine);
        parcel.writeInt(line);
        parcel.writeInt(inputType);
        parcel.writeInt(imeOption);
        parcel.writeString(resTextColor);
        parcel.writeString(resHintColor);
    }
}
