package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLocation {
    private int x=0,y=0;
    private Bitmap bitmap=null;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ImageLocation(int x, int y, Bitmap bitmap) {
        this.bitmap=bitmap;

        this.x = x;
        this.y = y;
    }
}
