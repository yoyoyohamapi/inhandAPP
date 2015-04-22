package com.inhand.milk.utils;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class HealthNutritionDisk extends ViewGroup {
	
	private int[] colors = {Color.rgb(0, 255, 0),Color.rgb(255, 0, 0),Color.rgb(0, 0, 255)};
	private float[][]  radians = {{0,100},{100,200},{200,360}};
	private List<View> views = new ArrayList<View>();
	private int innerCircleColor = Color.rgb(255,255,255);
	float outerCircleR ;
	float innerCircleR ;
	float sweepAngleOffset = 0;
	int lastIndex = -1;
	int currentIndex = -1;
	float distanceMoveDown = 20;
    String[] centerText = {"总摄入","368克","查看详情"};
    String[] centerText2 = {"总摄入","390克","查看详情"};
	public HealthNutritionDisk(Context context ,float r) {
		// TODO Auto-generated constructor stub
		super(context);
		outerCircleR = r;
		innerCircleR = r / 2;
		Sector view;
		int length = colors.length;
		for (int i = 0 ;i< length ;i++){
			view = new Sector(context, outerCircleR, colors[i], radians[i][0], radians[i][1]);
			this.addView(view);
			view.setTag(String.valueOf(i));
			views.add(view);
		}
		this.addView(new MyCircle(context, innerCircleR, innerCircleColor));
		this.addView(new MyText(getContext(), innerCircleR, centerText[0],
				centerText[1], centerText[2]) );
		setClickEvent();
		setObjectAnimation();
		
		currentIndex = 0;
		sweepAngleOffset = 90 - ( radians[0][0] + radians[0][1] )/2;
		this.setRotation(  sweepAngleOffset);
		startMoveDown(0, 0);
		addMyText();
	}
	
	private void removeMyText(){
		int count  = getChildCount();
		View child =getChildAt(count -1);
		if (child instanceof MyText)
			child.setVisibility(View.GONE);
	}
	private void addMyText(){
		int count = getChildCount();
		View child =getChildAt(count -1);
		if(  child instanceof MyText ){
			child.setVisibility(View.VISIBLE);
			((MyText) child).changeStrings(centerText2[0], centerText2[1], centerText2[2]);
			child.setRotation(-sweepAngleOffset);
		}
	}
 	private float radianToAngle(float radian){
 		return (float) (radian * 180 /Math.PI);
 	}
 	private int whoClicked(float x, float y){
 		float absX = x - outerCircleR;
 		float absY = - ( y - outerCircleR );
 		Log.i("evet.get x y",String.valueOf(absX) + " "+String.valueOf(absY));
 		float distance =  (float) Math.sqrt( absX * absX + absY *absY);
 		if (distance < innerCircleR || distance > outerCircleR)
 			return -1;
 		float atan = (float) (Math.atan( absY /absX));
 		atan = radianToAngle(atan);
 		atan = Math.abs(atan);
 		if (absX >= 0 && absY <= 0)
 			atan = atan;
 		else if (absX <0 && absY <=0)
 			atan = 180 - atan;
 		else if (absX < 0 && absY >0)
 			atan = 180 +atan;
 		else if (absX >=0 && absY >0)
 			atan = 360 - atan;
 		else 
 			return -1;
 	    //atan = atan - sweepAngleOffset;
 		if (atan < 0 )
 			atan  = atan +360;
 		else if (atan > 360)
 			atan = atan -360;
 		int count = radians.length;
 		Log.i("length of 2dimen", String.valueOf(count));
 		for (int i= 0; i<count;i++){
 			if (atan >= radians[i][0] && atan <= radians[i][1]){
 				currentIndex = i;
 				return i;
 			}
 		}
 		currentIndex = -1;
 		return -1;
 	}
	private void setClickEvent(){
		OnTouchListener onTouchListener = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP){
					//Log.i("evet.get x y",String.valueOf(event.getX()) + " "+String.valueOf(event.getY()));
					int index = whoClicked(event.getX(), event.getY());
					Log.i("index",String.valueOf(index) );
					if (index == -1)
						return true;
					if (index == lastIndex)
						return true;
					removeMyText();
					startMyRotateAnimation(index);
					return true;
				}
				return true;	
			}
		};
		this.setOnTouchListener(onTouchListener);	
	}
	
	
	private void startMoveDown(int index,int time){
		if (radians[index][1] - radians[index][0] >180)
			return;
		
		//RotateAnimation rAnimation = new RotateAnimation(this.getRotation(), toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue)
		View view = views.get(index);
		float angle = (radians[index][0] + radians[index][1] )/2;
		//angle = 360 -angle;
		angle = (float) (angle /180 *Math.PI);
		float x = (float)  (distanceMoveDown *Math.cos( angle) );
		float y = (float)  (distanceMoveDown *Math.sin( angle) );
		Log.i("x  or y ", String.valueOf(x )+" "+String.valueOf(y));
		TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, y);
		translateAnimation.setFillAfter(true);
		translateAnimation.setDuration(time);
		view.startAnimation(translateAnimation);
		addMyText();
		lastIndex = index;	
	}
	
	private ObjectAnimator oAnimator ;
	private void setObjectAnimation(){
		oAnimator  = new ObjectAnimator();
		oAnimator.setTarget(this);
		oAnimator.setPropertyName("rotation");
	}
	
	private void startMyRotateAnimation(int index){
		if (lastIndex != -1)
			views.get(lastIndex).clearAnimation();
		float centerAngle = (radians[index][0] + radians[index][1])/2 ;
		float time = 90 - centerAngle;
		sweepAngleOffset = time;
		float rotate = this.getRotation();
		if (time < this.getRotation()) 
			time = (int)( (rotate - time)/360+1 )*360 + time;
		oAnimator.setFloatValues(time);
		oAnimator.setInterpolator(new AccelerateDecelerateInterpolator() );
		oAnimator.setDuration( (int)(time - rotate) * 10);
		AnimatorListener listener = new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				startMoveDown(currentIndex,300);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		};
		oAnimator.addListener(listener);
		oAnimator.start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		View child ;
		for(int i = 0 ;i<count ; i++){
			child = getChildAt(i);
			child.measure(widthMeasureSpec, heightMeasureSpec);
		}
		setMeasuredDimension((int)(outerCircleR *2 +1), (int)(outerCircleR * 2 + 1 + distanceMoveDown * 2 ));
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int count = views.size();
		int offset = (int) (distanceMoveDown);
		View child ;
		for(int i = 0 ;i < count; i++){
			child = getChildAt(i);
			child.layout(0, offset, child.getMeasuredWidth(), 
					child.getMeasuredHeight() + offset );
		//	Log.i("health_nutrition", String.valueOf(i)+":"+String.valueOf(child.getMeasuredWidth()));
		}
		
		child = getChildAt(count);
		int leftTop =  (int) (outerCircleR - innerCircleR);
		int rightBottome = (int)(outerCircleR + innerCircleR);
		child.layout( leftTop,leftTop + offset,rightBottome,rightBottome +offset );
		
		if (getChildCount() == count +2){
			child = getChildAt(count + 1);
			child.layout( leftTop,leftTop + offset,rightBottome,rightBottome +offset );
		}
	}
	
	private class Sector extends View{
		private int mColor;
		private float mStartRadian;
		private float mEndRadian;
		private float mR;
		public Sector(Context context,float r,  int color,float startRadian, float endRadian ) {
			// TODO Auto-generated constructor stub
			super(context);
			mColor = color;
			mStartRadian = startRadian;
			mEndRadian = endRadian;
			mR = r;
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			int width;
			width = (int)(mR * 2 + 1);
			setMeasuredDimension(width, width);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			Paint  paint = new Paint();
			RectF   rectf = new RectF(0, 0,(int)(2 * mR), (int)(2 * mR));
			paint.setAntiAlias(true);
			paint.setColor(mColor);
			
			paint.setAlpha(255);
			canvas.drawArc(rectf, mStartRadian, mEndRadian -mStartRadian,true, paint);
			
			paint.setAlpha(0);
			canvas.drawArc(rectf, 0, mStartRadian, true, paint);
			canvas.drawArc(rectf, mEndRadian,360 - mEndRadian, true, paint);
			//Log.i("sector", String.valueOf(this.getHeight()));
			
		}
		
	}

	private class MyCircle extends View{
		float mR;
		int mColor;
		public MyCircle(Context context,float r,int color) {
			// TODO Auto-generated constructor stub
			super(context);
			mR = r;
			mColor = color;
		}
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			int width;
			width = (int)(mR * 2 + 1);
			setMeasuredDimension(width, width);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			Paint  paint = new Paint();
			RectF   rectf = new RectF(0, 0,(int)(2 * mR), (int)(2 * mR));
			paint.setAntiAlias(true);
			paint.setColor(mColor);
			
			paint.setAlpha(255);
			canvas.drawCircle(mR, mR, mR, paint);
		}
	}


	private class MyText extends View{
		private float uperTextSize , middleTextSize , bottomTextSize;
		private String uperText,middleText,bottomText;
		private int width;
		
		public MyText(Context context,float r,String string1 , String string2, String string3) {
			// TODO Auto-generated constructor stub
			super(context);
			width = (int) (r *2) ;
			uperTextSize = r * 0.25f;
			middleTextSize = r * 0.4f;
			bottomTextSize = r * 0.2f;
			uperText = string1;
			middleText = string2;
			bottomText = string3;
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			setMeasuredDimension(width, width);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			float  y,r;
			r = width/2;
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			
			y = 0.35f * width;
			paint.setTextSize(uperTextSize);
			canvas.drawText(uperText, r - paint.measureText(uperText)/2, y, paint);
			
			y = 0.55f * width;
			paint.setTextSize(middleTextSize);
			canvas.drawText(middleText, r - paint.measureText(middleText)/2, y, paint);
			
			y = 0.7f * width;
			paint.setTextSize(bottomTextSize);
			canvas.drawText(bottomText, r - paint.measureText(bottomText)/2, y, paint);
		}
		
		public void changeStrings(String string1 ,String string2 ,String string3){
			uperText = string1;
			middleText = string2;
			bottomText = string3;
		}
		
	}




}
