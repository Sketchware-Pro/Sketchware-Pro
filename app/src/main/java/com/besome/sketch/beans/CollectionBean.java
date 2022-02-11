package com.besome.sketch.beans;

public class CollectionBean {
    public String data;
    public String name;
    public String reserved1;

    public CollectionBean(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public CollectionBean(String name, String data, String reserved1) {
        this.name = name;
        this.data = data;
        this.reserved1 = reserved1;
    }

    public void print() {
    }
}
