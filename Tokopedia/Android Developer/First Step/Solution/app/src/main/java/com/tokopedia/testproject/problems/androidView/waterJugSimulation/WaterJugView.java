package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class WaterJugView extends View {

    private int maxWater = 0;
    private int waterFill = 0;

    public WaterJugView(Context context) {
        super(context);
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMaxWater(int maxWater) {
        this.maxWater = maxWater;
    }

    public void setWaterFill(int waterFill) {
        this.waterFill = waterFill;
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Paint blackPaint=new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(1);

        Paint waterPaint=new Paint();
        waterPaint.setColor(Color.BLUE);
        waterPaint.setStrokeWidth(12);
        waterPaint.setStyle(Paint.Style.FILL);

        canvas.drawLine(0,0,0,getMeasuredHeight()/2,blackPaint);
        canvas.drawLine(0,getMeasuredHeight()/2,getMeasuredWidth()/2,getMeasuredHeight()/2,blackPaint);
        canvas.drawLine(getMeasuredWidth()/2,0,getMeasuredWidth()/2,getMeasuredHeight()/2,blackPaint);
        int jarWidth=getMeasuredWidth()/2;
        int jarHeight=getMeasuredHeight()/2;
        float waterHeight=jarHeight;
        if(waterFill!=0){
            if(waterFill!=maxWater) {
                waterHeight = jarHeight-((waterFill * jarHeight) / maxWater );
            } else {
                waterHeight = 0;
            }
        }
        System.out.println(waterFill);
        System.out.println(maxWater);
        System.out.println((waterFill/maxWater));
        System.out.println((jarHeight));
        System.out.println(waterHeight);
        System.out.println("-------");
        canvas.drawRect(0,waterHeight,jarWidth,jarHeight,waterPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
//TODO
    /*
    Based on these variables: maxWater and waterFill, draw the jug with the water

    Example a:
    maxWater = 10
    waterFill = 0

    Result,
    View will draw like below
    |        |
    |        |
    |        |
    |        |
    `--------'

    Example b:
    maxWater = 10
    waterFill = 5

    Result,
    View will draw like below
    |        |
    |        |
    |--------|
    |        |
    `--------'

    Example c:
    maxWater = 10
    waterFill = 8

    Result,
    View will draw like below
    |        |
    |--------|
    |        |
    |        |
    `--------'

    Example d:
    maxWater = 10
    waterFill = 10

    Result,
    View will draw like below
     ________
    |        |
    |        |
    |        |
    |        |
    `--------'
    */

}
