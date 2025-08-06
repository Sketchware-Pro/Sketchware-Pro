package com.besome.sketch.beans;

import java.util.ArrayList;

public class BlockCollectionBean extends SelectableBean {
    public ArrayList<BlockBean> blocks;
    public String name;

    public BlockCollectionBean(String str, ArrayList<BlockBean> arrayList) {
        name = str;
        blocks = arrayList;
    }
}
