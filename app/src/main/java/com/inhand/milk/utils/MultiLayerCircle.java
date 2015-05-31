package com.inhand.milk.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SimpleAdapter;

/**
 * Created by Administrator on 2015/5/17.
 */
public class MultiLayerCircle extends View {
    private float mR=0;
    private int[] mColors;
    private int[] mWeights;
    public MultiLayerCircle(Context context) {
        super(context);
    }
    public MultiLayerCircle(Context context,float r,int[] colors,int[] weights) {
        super(context);
        this.mR = r;
        mColors = colors;
        mWeights = weights;
    }

    public MultiLayerCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLayerCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiLayerCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setR(float r){
        mR = r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint =new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        int count = mColors.length < mWeights.length?mColors.length:mWeights.length;
        int weight = 0;
        float r=0,tempR;
        for(int i =0;i<count;i++){
               weight+= mWeights[i];
        }
        for(int i=0;i<count;i++){
            paint.setColor(mColors[i]);
            tempR = mR*mWeights[i]/weight;
            paint.setStrokeWidth(tempR);
            canvas.drawCircle(mR,mR,r+ tempR/2,paint);
            r += mR*mWeights[i]/weight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)( 2*mR+1),(int)(2*mR+1));
    }
}
