package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.UtilKt;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public interface onSuccessLoadBitmap{
        void onSliceSuccess(List<Bitmap> bitmapList);
        void onSliceFailed(Throwable throwable);
    }
    public static void sliceTo4x4(final Context context, final onSuccessLoadBitmap onSuccessLoadBitmap, String imageUrl) {
        final ArrayList<Bitmap> bitmapList = new ArrayList<>();
        // TODO, download the image, crop, then sliced to 15 Bitmap (4x4 Bitmap). ignore the last Bitmap
        // below is stub, replace with your implementation!
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(new RequestOptions().override(400,400))
                .into(new Target<Bitmap>() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample11));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample12));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample13));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample14));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample21));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample22));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample23));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample24));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample31));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample32));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample33));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample34));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample41));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample42));
                bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample43));
                int w = 100, h = 100;

                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
                bitmapList.add(bmp);
                onSuccessLoadBitmap.onSliceSuccess(bitmapList);
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Bitmap resized = Bitmap.createScaledBitmap(resource, 400, 400, true);
                for(int x=0;x<resized.getWidth();x+=100){
                    for(int y=0;y<resized.getHeight();y+=100){
                        Bitmap cropeedBitmap = Bitmap.createBitmap(resized, x, y, 100, 100);
                        bitmapList.add(cropeedBitmap);
                    }
                }
                bitmapList.remove(bitmapList.size()-1);
                int w = 100, h = 100;

                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
                bitmapList.add(bmp);
                onSuccessLoadBitmap.onSliceSuccess(bitmapList);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void setRequest(@Nullable Request request) {

            }

            @Nullable
            @Override
            public Request getRequest() {
                return null;
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }
        });

    }
}
