package com.besome.sketch.beans;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

public class ShareCollectionBean {
    public ArrayList<String> arrAddedTags = new ArrayList<>();
    public ArrayList<String> arrAllTags = new ArrayList<>();
    public ArrayList<String> arrExistScreens = new ArrayList<>();
    public ArrayList<Bitmap> arrScreens = new ArrayList<>();
    public HashMap<String, Object> collectionData = new HashMap<>();
    public BlockCollectionBean selectedBlock;
    public ArrayList<ProjectResourceBean> selectedFonts = new ArrayList<>();
    public ArrayList<ProjectResourceBean> selectedImages = new ArrayList<>();
    public MoreBlockCollectionBean selectedMoreBlock;
    public ArrayList<ProjectResourceBean> selectedSounds = new ArrayList<>();
    public WidgetCollectionBean selectedView;
}
