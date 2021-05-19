package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.mq;

public class BlockBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<BlockBean> CREATOR = new Parcelable.Creator<BlockBean>() {
        /* class com.besome.sketch.beans.BlockBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public BlockBean createFromParcel(Parcel parcel) {
            return new BlockBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public BlockBean[] newArray(int i) {
            return new BlockBean[i];
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
        this.parameters = new ArrayList<>();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
    }

    public BlockBean(String str, String str2, String str3, String str4) {
        this(str, str2, str3, "", str4);
    }

    public BlockBean(String str, String str2, String str3, String str4, String str5) {
        this.id = str;
        this.spec = str2;
        this.type = str3;
        this.typeName = str4;
        this.opCode = str5;
        this.parameters = new ArrayList<>();
        this.subStack1 = -1;
        this.subStack2 = -1;
        this.nextBlock = -1;
        buildClassInfo();
    }

    public BlockBean(Parcel parcel) {
        this.id = parcel.readString();
        this.spec = parcel.readString();
        this.type = parcel.readString();
        this.typeName = parcel.readString();
        this.opCode = parcel.readString();
        this.color = parcel.readInt();
        this.parameters = (ArrayList) parcel.readSerializable();
        this.subStack1 = parcel.readInt();
        this.subStack2 = parcel.readInt();
        this.nextBlock = parcel.readInt();
        buildClassInfo();
    }

    public static Parcelable.Creator<BlockBean> getCreator() {
        return CREATOR;
    }

    private void buildClassInfo() {
        this.classInfo = mq.a(this.type, this.typeName);
        this.paramClassInfo = mq.a(this.spec);
    }

    public void copy(BlockBean blockBean) {
        this.id = blockBean.id;
        this.spec = blockBean.spec;
        this.type = blockBean.type;
        this.typeName = blockBean.typeName;
        this.opCode = blockBean.opCode;
        this.color = blockBean.color;
        this.parameters = new ArrayList<>(blockBean.parameters);
        this.subStack1 = blockBean.subStack1;
        this.subStack2 = blockBean.subStack2;
        this.nextBlock = blockBean.nextBlock;
        buildClassInfo();
    }

    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (this.classInfo == null) {
            buildClassInfo();
        }
        return this.classInfo;
    }

    public ArrayList<Gx> getParamClassInfo() {
        if (this.paramClassInfo == null) {
            buildClassInfo();
        }
        return this.paramClassInfo;
    }

    public boolean isEqual(BlockBean blockBean) {
        if (blockBean == null) {
            return false;
        }
        String str = this.id;
        if (!(str == null || str.equals(blockBean.id))) {
            return false;
        }
        String str2 = this.spec;
        if (!((str2 == null || str2.equals(blockBean.spec)) && this.type.equals(blockBean.type))) {
            return false;
        }
        String str3 = this.typeName;
        if (!((str3 == null || str3.equals(blockBean.typeName)) && this.opCode.equals(blockBean.opCode) && this.color == blockBean.color && this.subStack1 == blockBean.subStack1 && this.subStack2 == blockBean.subStack2 && this.nextBlock == blockBean.nextBlock)) {
            return false;
        }
        ArrayList<String> arrayList = this.parameters;
        if (!(arrayList == null || arrayList.size() == blockBean.parameters.size())) {
            return false;
        }
        for (int i = 0; i < this.parameters.size(); i++) {
            String str4 = this.parameters.get(i);
            String str5 = blockBean.parameters.get(i);
            if (!(str4 == null || str4.equals(str5))) {
                return false;
            }
        }
        return true;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.spec);
        parcel.writeString(this.type);
        parcel.writeString(this.typeName);
        parcel.writeString(this.opCode);
        parcel.writeInt(this.color);
        parcel.writeSerializable(this.parameters);
        parcel.writeInt(this.subStack1);
        parcel.writeInt(this.subStack2);
        parcel.writeInt(this.nextBlock);
    }

    @Override // java.lang.Object
    public BlockBean clone() {
        BlockBean blockBean = new BlockBean();
        blockBean.copy(this);
        return blockBean;
    }
}
