package com.inhand.milk.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/5/16.
 */
public class HeadlistView extends ListView {
    private View headView;
    private int mMeasureWidth, mMeasureHeight;
    private HeadListViewAdpter myAdapter;
    private boolean drawHead =false;
    public HeadlistView(Context context) {
        super(context);
    }

    public HeadlistView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadlistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeadlistView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setHeadView(View view){
        this.headView = view;
    }
    public void setHeadView(int resource){
        this.headView = LayoutInflater.from(getContext()).inflate(resource,this,false);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if ( headView != null){
            measureChild(headView, widthMeasureSpec, heightMeasureSpec);
            mMeasureHeight = headView.getMeasuredHeight();
            mMeasureWidth  = headView.getMeasuredWidth();
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // TODO Auto-generated method stub
        super.setAdapter(adapter);
        myAdapter =	(HeadListViewAdpter) adapter;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        if (headView != null){
            controlHeadView( getFirstVisiblePosition() );
        }
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);
        if (headView != null && drawHead){
            drawChild(canvas, headView, getDrawingTime());
            controlHeadView( getFirstVisiblePosition() );
        }
    }
    private void controlHeadView(int position){
        if (headView == null){
            drawHead = false;
            return ;
        }
        int state = myAdapter.getHeadViewState(getChildAt(0),headView.getMeasuredHeight(),getChildAt(0).getMeasuredHeight(),position);

        switch (state) {
            case HeadViewManager.HEAD_VIEW_GONE:
                drawHead = false;
                break;
            case HeadViewManager.HEAD_VIEW_VISIBLE:
                myAdapter.configureHeadView(headView, position);
                headView.layout(0, 0, mMeasureWidth, mMeasureHeight);
                drawHead = true;
                break;
            case HeadViewManager.HEAD_VIEW_MOVE:
                myAdapter.configureHeadView(headView, position);
                drawHead = true;
                View child =  getChildAt(0);
                if (child != null){
                    int bottom = child.getBottom();
                    int height = headView.getHeight();//����Ҫ�� ���ⳤ��   С��   ���ݳ���
                    int y = bottom - height;
                    if ( y > 0 )
                        y = 0;
                    headView.layout(0, y, mMeasureWidth, mMeasureHeight + y);
                }
                break;
        }
    }
    public interface HeadViewManager{
        public static final int HEAD_VIEW_GONE = 0;
        public static final int HEAD_VIEW_VISIBLE = 1;
        public static final int HEAD_VIEW_MOVE = 2;
        int getHeadViewState(View v,int headHeight,int itemHeight,int position);
        void configureHeadView(View view, int position);
    }
}
