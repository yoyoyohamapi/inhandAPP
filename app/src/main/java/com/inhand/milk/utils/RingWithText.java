package com.inhand.milk.utils;

/**
 * Created by Administrator on 2015/5/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


public class RingWithText extends View {
    private float mR,paintWidth,circleR;
    private int ringBgColor = 0,ringColor=0,textColor= Color.WHITE;
    private float maxSweepAngle=0,sweepAngle=0;
    private String[] texts;
    private float[] textSizes;
    private int[] textColors;
    private int timeRing;
    private updateListener listener;
    private android.os.Handler handler;

    public RingWithText(Context context,float r) {
        super(context);
        mR = r;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;

    }
    public RingWithText(Context context) {
        super(context);
        mR = 0;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }
    public RingWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mR = 0;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }

    public RingWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mR = 0;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }

    public RingWithText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mR = 0;
        paintWidth = mR/15;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }

    public void setRingBgColor(int ringBgColor) {
        this.ringBgColor = ringBgColor;
    }
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    public void setPaintWidth(float paintWidth) {
        this.paintWidth = paintWidth;
        circleR = mR - paintWidth/2 >0? mR-paintWidth/2:0;
    }
    public void setMaxSweepAngle(float a){
        maxSweepAngle = a;
        sweepAngle = maxSweepAngle;
    }
    public void setTexts(String[] strings,float[] sizes){
        if (strings.length != sizes.length)
             return;
        texts = strings;
        textSizes = sizes;
    }
    public void setTexts(String[] strings,float[] sizes,int[] colors){
        if (strings.length != sizes.length)
            return;
        texts = strings;
        textSizes = sizes;
        textColors = colors;
    }
    public void setTextColor(int color){
        this.textColor = color;
    }

    public void setListener(updateListener listener) {
        this.listener = listener;
    }

    public void setTimeRing(int timeRing) {
        this.timeRing = timeRing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRingBg(canvas);
        drawRing(canvas);
        drawText(canvas);
    }
    private void drawRingBg(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(ringBgColor);
        canvas.drawCircle(mR,mR,circleR,paint);
    }
    private void drawRing(Canvas canvas){
        if(sweepAngle ==0)
            return;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(ringColor);
        float value = mR -circleR;
        RectF rectF = new RectF(value,value,2*mR-value,2*mR-value);
        canvas.drawArc(rectF,270,sweepAngle,false,paint);
    }
    private void drawText(Canvas canvas){
        float height=0,currentHeight,width;
        int i;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(paintWidth);

        for(i=0;i<texts.length;i++){
            height+= textSizes[i];
        }
        currentHeight = mR - height/2;
        for(i=0;i<texts.length;i++){
            if (textColors == null)
                paint.setColor(textColor);
            else
                paint.setColor(textColors[i]);
            paint.setTextSize(textSizes[i]);
            width = paint.measureText(texts[i]);
            currentHeight += textSizes[i];
            canvas.drawText(texts[i],mR - width/2,currentHeight,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(mR*2),(int)(mR*2));
    }

    public void startRing(){
        if (handler == null)
            handler = new Handler();
        sweepAngle = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                if (sweepAngle >= maxSweepAngle) {
                    handler.removeCallbacks(this);
                    sweepAngle = maxSweepAngle;
                }
                else{
                    sweepAngle += maxSweepAngle/(timeRing/2);
                    handler.postDelayed(this,2);
                }
                if(listener != null)
                    listener.updateSweepAngle(sweepAngle);
            }
        };
        handler.post(runnable);
    }
    public interface updateListener{
        void updateSweepAngle(float sweepAngle);
    }

}

