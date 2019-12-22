package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tokopedia.testproject.R;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;

public class SlidingImageGameActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    public static final String X_IMAGE_URL = "https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
    public static final int GRID_NO = 4;
    ImageView[][] imageViews = null, originalViews = null;
    private String imageUrl;
    private int blankX = Integer.MIN_VALUE, blankY = Integer.MIN_VALUE;
    private int lastX = Integer.MIN_VALUE, lastY = Integer.MIN_VALUE;
    private GridLayout gridLayout;

    public static Intent getIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, SlidingImageGameActivity.class);
        intent.putExtra(X_IMAGE_URL, imageUrl);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i=0;i<imageViews.length;i++){
            for(int j=0;j<imageViews[0].length;j++){
                GameMemory.imageViews.add(imageViews[i][j]);
                GameMemory.originalViews.add(originalViews[i][j]);
            }
        }
        GameMemory.blankX=blankX;
        GameMemory.blankY=blankY;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getIntent().getStringExtra(X_IMAGE_URL);
        setContentView(R.layout.activity_sliding_image_game);

        gridLayout = findViewById(R.id.gridLayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        if(GameMemory.originalViews.isEmpty() || GameMemory.imageViews.isEmpty()) {
            originalViews = new ImageView[4][4];
            imageViews = new ImageView[4][4];
            for (int i = 0; i < GRID_NO; i++) {
                for (int j = 0; j < GRID_NO; j++) {
                    ImageView view = (ImageView) inflater.inflate(R.layout.item_image_sliding_image,
                            gridLayout, false);
                    gridLayout.addView(view);
                    imageViews[i][j] = view;
                    originalViews[i][j] = view;
                    view.setOnTouchListener(this);
                }
            }
            Solution.sliceTo4x4(this, new Solution.onSuccessLoadBitmap() {
                @Override
                public void onSliceSuccess(List<Bitmap> bitmapList) {
                    //TODO will randomize placement to grid. Note: the game must be solvable.
                    //replace below implementation to your implementation.
                    int counter = 0;
                    int bitmapSize = bitmapList.size();
                    Random random = new Random();
                    boolean[][] alreadyFilled = new boolean[4][4];
                    for (int i = 0; i < alreadyFilled.length; i++) {
                        for (int j = 0; j < alreadyFilled[0].length; j++) {
                            alreadyFilled[i][j] = false;
                        }
                    }
                    int x = 0, y = 0;
                    for (int i = 0; i < GRID_NO; i++) {
                        for (int j = 0; j < GRID_NO; j++) {
                            if (counter >= bitmapSize) break;
                            do {
                                x = random.nextInt(4);
                                y = random.nextInt(4);
                            }
                            while (alreadyFilled[x][y] != false);
                            imageViews[x][y].setImageBitmap(bitmapList.get(counter));
                            alreadyFilled[x][y] = true;
                            counter++;
                        }
                        if (counter >= bitmapSize) break;
                    }
                    blankX = x;
                    blankY = y;
                }

                @Override
                public void onSliceFailed(Throwable throwable) {
                    Toast.makeText(SlidingImageGameActivity.this,
                            throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
            }, imageUrl);
        } else {
            blankX=GameMemory.blankX;
            blankY=GameMemory.blankY;
            imageViews=new ImageView[4][4];
            originalViews=new ImageView[4][4];
            int counter=0;
            for(int i=0;i<imageViews.length;i++) {
                for (int j = 0; j < imageViews[0].length; j++) {
                    imageViews[i][j]=GameMemory.imageViews.get(counter);
                    originalViews[i][j]=GameMemory.originalViews.get(counter);
                    counter++;
                    ((ViewGroup)imageViews[i][j].getParent()).removeView(imageViews[i][j]);
                    System.out.println(imageViews[i][j].getParent());
                }
            }
            GameMemory.imageViews.clear();
            GameMemory.originalViews.clear();
            for (int i = 0; i < GRID_NO; i++) {
                for (int j = 0; j < GRID_NO; j++) {
                    try {
                        gridLayout.addView(imageViews[i][j]);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }


        // TODO add implementation of the game.
        // There is image adjacent to blank space (either horizontal or vertical).
        // If that image is clicked, it will swap to the blank space
        // if the puzzle is solved (the image in the view is aligned with the original image), then show a "success" dialog

        // TODO add handling for rotation to save the user input.
        // If the device is rotated, it should retain user's input, so user can continue the game.
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < GRID_NO; i++) {
                    for (int j = 0; j < GRID_NO; j++) {
                        if (v.equals(imageViews[i][j])) {
                            if (lastX == i && lastY == j) {
                                return false;
                            }
                            if ((Math.abs(1 - blankX) != i) && (Math.abs(1 - blankY) != j)
                                    && (Math.abs(1 + blankY) != i) && (Math.abs(1 + blankX) != j)) {
                                return false;
                            }
                            imageViews[i][j] = imageViews[blankX][blankY];
                            imageViews[blankX][blankY] = (ImageView) v;
                            lastX = blankX;
                            lastY = blankY;
                            blankX = i;
                            blankY = j;
                            break;
                        }
                    }
                }
                gridLayout.removeAllViews();
                for (int i = 0; i < GRID_NO; i++) {
                    for (int j = 0; j < GRID_NO; j++) {
                        //((ViewGroup)imageViews[i][j].getParent()).removeView(imageViews[i][j]);
                        try {
                            gridLayout.addView(imageViews[i][j]);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            default:
                break;
        }
        boolean gameFinished = true;
        for (int i = 0; i < GRID_NO; i++) {
            for (int j = 0; j < GRID_NO; j++) {
                if (!imageViews[i][j].equals(originalViews[i][j])) {
                    gameFinished = false;
                    break;
                }
            }
        }
        if (gameFinished == true) {
            new LovelyInfoDialog(this)
                    //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                    .setTitle("Game Finished!")
                    .setMessage("Congratulations! you win!")
                    .show();
        }
        return true;
    }

    public int convertFrom2DTo1D(int row, int col) {
        return row * 4 + col;
    }
}
