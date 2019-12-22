package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.graphics.Bitmap;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMemory {
    public static List<ImageView> imageViews = new ArrayList<>();
    public static List<ImageView> originalViews= new ArrayList<>();
    public static GridLayout gridLayout=null;
    public static int blankX=0,blankY=0;
}
