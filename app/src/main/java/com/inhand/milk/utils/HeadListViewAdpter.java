package com.inhand.milk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/16.
 */
public class HeadListViewAdpter extends BaseAdapter implements HeadlistView.HeadViewManager {
    private Context context;
    private List<? extends Map<String,?>> listItems;
    private int resource;
    private int needTitleto,titleTo;
    private String needTitleFrom;
    private String[] from,headviewfrom;
    private int[] to,headViewto;


    public HeadListViewAdpter(Context context,List<? extends Map<String,?>> Items,int layoutId,
                              String[] strings,int[] Ids,String needTitleFrom,int needTitleto,int titleTo,
                                String[] headViewfrom,int[] headViewto) {
        this.context  = context;
        listItems = Items;
        this.resource = layoutId;
        this.needTitleto= needTitleto;
        this.needTitleFrom = needTitleFrom;
        this.to = Ids;
        this.from = strings;
        this.titleTo = titleTo;
        this.headviewfrom = headViewfrom;
        this.headViewto = headViewto;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView ==null)
            view = LayoutInflater.from(context).inflate(resource,null);
        else
            view = convertView;
        initView(view,position);
        if (needTitle(view,position))
            ViewHolder.get(view,titleTo).setVisibility(View.VISIBLE);
        else
            ViewHolder.get(view,titleTo).setVisibility(View.GONE);
        return view;
    }
    private void initView(View view,int position){
        final Map data = listItems.get(position);
        final int count = from.length;
        for(int i=0;i<count;i++){
            final View child = ViewHolder.get(view,to[i]);
            final Object  obj = data.get(from[i]);
            bindView(child, obj);
        }
    }
    private boolean bindView(View v, Object obj){
        if(v instanceof TextView){
            String text;
            if(obj == null) {
                text = "";
            }
            else {
                text = obj.toString();
            }
            ((TextView) v).setText(text);

        }
        else if (v instanceof ImageView){
            if (obj == null)
                throw new NullPointerException("obj is null");
            if (obj instanceof Integer)
                ((ImageView)v).setImageResource((Integer)obj);
            else if (obj instanceof Drawable)
                ((ImageView)v).setImageDrawable((Drawable)obj);
            else {
                throw new IllegalStateException(obj.getClass().getName() + " is not a " +
                        " picture id or drawable");
            }
        }
        else if (v instanceof LinearLayout){
            if (obj == null){
                     return false;
                    //throw new NullPointerException("obj is null");
            }
            if(obj instanceof View){
                ((LinearLayout)v).removeAllViews();
                  LinearLayout parent = (LinearLayout)((View) obj).getParent();
                  if (parent == null){
                   //   Log.i("parent","null");
                      ((LinearLayout) v).addView((View) obj);
                  }
                  else{
                 //      parent.removeView((View)obj);
                   //  ((LinearLayout) v).addView((View) obj);
                    //   Log.i("parent","has");
                  }
            }
            else if (obj instanceof  Integer){
                int value = (Integer)obj;
                ((LinearLayout)v).setBackgroundColor(value);
            }
            else {
                throw new IllegalStateException(obj.getClass().getName() + " is not a " +
                        " view");
            }
        }
        else {
            throw new IllegalStateException(v.getClass().getName() + " is not a " +
                    " imageview or textview or linearlayout");
        }
        return true;
    }
    private boolean needTitle(View v,int positon){
        if(positon == 0)
            return true;
        final Map currentItem;
        final Map previousItem;
        currentItem = (Map) getItem(positon);
        previousItem = (Map)getItem(positon-1);
        if (currentItem == null || previousItem == null)
            return false;
        final Object currentObject;
        final Object previousObject;
        currentObject = currentItem.get(needTitleFrom);
        previousObject = previousItem.get(needTitleFrom);
        if(currentObject == null || previousObject ==null)
            return  false;
        final View child  = ViewHolder.get(v,needTitleto);
        if(child instanceof  TextView){
            if(currentObject instanceof String && previousObject instanceof  String){
                if ( currentObject.toString().equals(previousObject.toString()))
                    return false;
                else
                    return true;
            }
            else {
                throw new IllegalStateException(currentObject.getClass().getName() + " is not a " +
                        " string");
            }
        }
        //......................
        return false;
    }
    private boolean isMove(View v,int headHeight,int itemHeight,int position){
        if(position == listItems.size()-1)
            return false;
        final Map currentItem;
        final Map nextItem;
        currentItem = (Map) getItem(position);
        nextItem = (Map)getItem(position+1);
        if (currentItem == null || nextItem == null)
            return false;
        final Object currentObject;
        final Object nextObject;
        currentObject = currentItem.get(needTitleFrom);
        nextObject = nextItem.get(needTitleFrom);
        if(currentObject == null || nextObject ==null)
            return  false;
        final View child  = ViewHolder.get(v,needTitleto);
        if(child instanceof  TextView){
            if(currentObject instanceof String && nextObject instanceof  String){
                if ( currentObject.toString().equals(nextObject.toString()))
                    return false;
                else {
                    if (headHeight < itemHeight){
                        if (v.getBottom() <= headHeight) {
                            return true;
                        }
                        return false;
                    }
                   //......................
                    return false;
                }
            }
            else {
                throw new IllegalStateException(currentObject.getClass().getName() + " is not a " +
                        " string");
            }
        }
        //......................
        return false;
    }
    @Override
    public int getHeadViewState(View v,int headHeight,int itemHeight,int position) {
        if (listItems.size() <=0)
            return HeadlistView.HeadViewManager.HEAD_VIEW_GONE;

        if ( isMove(v,headHeight,itemHeight,position))
            return HeadlistView.HeadViewManager.HEAD_VIEW_MOVE;

        return HeadlistView.HeadViewManager.HEAD_VIEW_VISIBLE;
    }

    @Override
    public void configureHeadView(View view, int position) {
        final Map data = listItems.get(position);
        final int count = headviewfrom.length;
        for(int i=0;i<count;i++){
            final View child = ViewHolder.get(view,headViewto[i]);
            final Object  obj = data.get(headviewfrom[i]);
            bindView(child, obj);
        }
    }
}
