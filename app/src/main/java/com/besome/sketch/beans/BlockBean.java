package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.mq;

public class BlockBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<BlockBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public BlockBean createFromParcel(Parcel source) {
            return new BlockBean(source);
        }

        @Override
        public BlockBean[] newArray(int size) {
            return new BlockBean[size];
        }
    };

    public Gx classInfo;
    @Expose
    public int color;
    @Expose
    public String id;
    @Expose
    public int nextBlock;
    @Expose
    public String opCode;
    public ArrayList<Gx> paramClassInfo;
    @Expose
    public ArrayList<String> parameters;
    @Expose
    public String spec;
    @Expose
    public int subStack1;
    @Expose
    public int subStack2;
    @Expose
    public String type;
    @Expose
    public String typeName;

    public BlockBean() {
        parameters = new ArrayList<>();
        subStack1 = -1;
        subStack2 = -1;
        nextBlock = -1;
    }

    /**
     * Constructor without <code>typeName</code>.
     */
    public BlockBean(String id, String spec, String type, String opCode) {
        this(id, spec, type, "", opCode);
    }

    public BlockBean(String id, String spec, String type, String typeName, String opCode) {
        this.id = id;
        this.spec = spec;
        this.type = type;
        this.typeName = typeName;
        this.opCode = opCode;
        parameters = new ArrayList<>();
        subStack1 = -1;
        subStack2 = -1;
        nextBlock = -1;
        buildClassInfo();
    }

    public BlockBean(Parcel parcel) {
        id = parcel.readString();
        spec = parcel.readString();
        type = parcel.readString();
        typeName = parcel.readString();
        opCode = parcel.readString();
        color = parcel.readInt();
        parameters = (ArrayList<String>) parcel.readSerializable();
        subStack1 = parcel.readInt();
        subStack2 = parcel.readInt();
        nextBlock = parcel.readInt();
        buildClassInfo();
    }

    public static Parcelable.Creator<BlockBean> getCreator() {
        return CREATOR;
    }

    private void buildClassInfo() {
        classInfo = mq.a(type, typeName);
        paramClassInfo = mq.a(spec);
    }

    public void copy(BlockBean other) {
        id = other.id;
        spec = other.spec;
        type = other.type;
        typeName = other.typeName;
        opCode = other.opCode;
        color = other.color;
        parameters = new ArrayList<>(other.parameters);
        subStack1 = other.subStack1;
        subStack2 = other.subStack2;
        nextBlock = other.nextBlock;
        buildClassInfo();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (classInfo == null) {
            buildClassInfo();
        }
        return classInfo;
    }

    public ArrayList<Gx> getParamClassInfo() {
        if (paramClassInfo == null) {
            buildClassInfo();
        }
        return paramClassInfo;
    }

    public boolean isEqual(BlockBean other) {
        if (other == null) {
            return false;
        }
        String id = this.id;
        if (!(id == null || id.equals(other.id))) {
            return false;
        }
        String spec = this.spec;
        if (!((spec == null || spec.equals(other.spec)) && type.equals(other.type))) {
            return false;
        }
        String typeName = this.typeName;
        if (!((typeName == null || typeName.equals(other.typeName)) && opCode.equals(other.opCode)
                && color == other.color && subStack1 == other.subStack1
                && subStack2 == other.subStack2 && nextBlock == other.nextBlock)) {
            return false;
        }
        ArrayList<String> parameters = this.parameters;
        if (!(parameters == null || parameters.size() == other.parameters.size())) {
            return false;
        }
        for (int i = 0; i < parameters.size(); i++) {
            String str4 = parameters.get(i);
            String str5 = other.parameters.get(i);
            if (!(str4 == null || str4.equals(str5))) {
                return false;
            }
        }
        return true;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(spec);
        dest.writeString(type);
        dest.writeString(typeName);
        dest.writeString(opCode);
        dest.writeInt(color);
        dest.writeSerializable(parameters);
        dest.writeInt(subStack1);
        dest.writeInt(subStack2);
        dest.writeInt(nextBlock);
    }

    @Override
    public BlockBean clone() {
        BlockBean blockBean = new BlockBean();
        blockBean.copy(this);
        return blockBean;
    }
}
