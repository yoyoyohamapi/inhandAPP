package com.inhand.milk.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Circle extends View {

	int mColor = Color.WHITE,r=0;
	public Circle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public Circle(Context context,AttributeSet attri){
		super(context,attri);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if ( r != 0){
			setMeasuredDimension(2*r, 2*r);
			return;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	public void setColor(int color){
		mColor = color;
	}
	public void setR(int rr){
		r = rr;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		float rr;
		if (r != 0)
			rr = r;
		else 
			rr = getMeasuredHeight()/2;
		
		Paint paint = new Paint();
		paint.setAlpha(255);
		paint.setAntiAlias(true);
		paint.setColor(mColor);
		canvas.drawCircle(rr,rr, rr, paint);
	}
	
	
	
}
