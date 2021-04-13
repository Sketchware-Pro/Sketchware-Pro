package com.besome.sketch.beans;

public class CollectionBean {
    public String data;
    public String name;
    public String reserved1;

    public CollectionBean(String str, String str2) {
        this.name = str;
        this.data = str2;
    }

    public void print() {
    }

    public CollectionBean(String str, String str2, String str3) {
        this.name = str;
        this.data = str2;
        this.reserved1 = str3;
    }
}
