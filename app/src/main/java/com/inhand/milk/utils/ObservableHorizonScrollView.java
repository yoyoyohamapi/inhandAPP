package com.inhand.milk.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ObservableHorizonScrollView extends HorizontalScrollView {  
	  
    private ScrollViewListener scrollViewListener = null;  
  
    public ObservableHorizonScrollView(Context context) {  
        super(context);  
    }  
  
    public ObservableHorizonScrollView(Context context, AttributeSet attrs,  
            int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public ObservableHorizonScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
  
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }  
  

    public interface ScrollViewListener {  
    	  
        void onScrollChanged(ObservableHorizonScrollView scrollView, int x, int y, int oldx, int oldy);  
      
    }  
}
