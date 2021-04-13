package com.besome.sketch.beans;

import java.util.ArrayList;

public class MoreBlockCollectionBean extends SelectableBean {
    public ArrayList<BlockBean> blocks;
    public String name;
    public String spec;

    public MoreBlockCollectionBean(String str, String str2, ArrayList<BlockBean> arrayList) {
        this.name = str;
        this.spec = str2;
        this.blocks = arrayList;
    }
}
