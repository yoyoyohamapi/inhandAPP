package com.inhand.milk.fragment.milk_amount;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inhand.milk.R;
import com.inhand.milk.fragment.TitleFragment;
import com.inhand.milk.utils.HeadListViewAdpter;
import com.inhand.milk.utils.HeadlistView;
import com.inhand.milk.utils.MultiLayerCircle;
import com.inhand.milk.utils.ProgressBar;
import com.inhand.milk.utils.RingWithText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/15.
 */
public class MilkAmountFragment extends TitleFragment {
    private TextView adviseNum,drinkNum;
    private float drinkAmount,adviseAmount;
    private RingWithText ringWithText;
    private final static int timeRing = 200,dataLoadAmount =4;
    private List<Map<String,Object>> listItems;
    private int[] multiLayerCircleColors,multiLayerCircleWeights;
    private List<ProgressBar> progressBarList;
    private HeadlistView headlistView;
    private int warningHighColor,warningLowColor,normalColor,progressBgColor;
    private float[] mTemperature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.fragment_milk_amount,null);
        //setTitleview(getResources().getString(R.string.milk_amount_title),1,null,null);
        initVarables();
        initViews();
        startAnimator();
        return mView;
    }
    private void initVarables(){
        warningHighColor = getResources().getColor(R.color.milk_amount_listview_list_warining_high_tempreature_color);
        warningLowColor = getResources().getColor(R.color.milk_amount_listview_list_warining_low_tempreature_color);
        normalColor = getResources().getColor(R.color.milk_amount_listview_list_normal_tempreature_color);
        progressBgColor  = getResources().getColor(R.color.milk_amount_listview_item_progress_bar_background_color);
    }
    private void initViews(){

        drinkNum = (TextView)mView.findViewById(R.id.milk_amount_drink_num);
        drinkNum.setText(getResources().getString(R.string.milk_amount_drink_num_doc)+ getOneDayDrinkAmount());
        adviseNum = (TextView)mView.findViewById(R.id.milk_amount_advise_num);
        adviseNum.setText(getResources().getString(R.string.milk_amount_advise_num_doc) + getOneDayAdviseAmount());
        initRingWithText();
        initListViews();

    }
    private void initRingWithText(){
        LinearLayout linearLayout = (LinearLayout)mView.findViewById(R.id.milk_amount_advise_ring_container);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.milk_amount_advise_ring_size_D),
                getResources().getDimensionPixelOffset(R.dimen.milk_amount_advise_ring_size_D));
        ringWithText  = new RingWithText(getActivity().getApplicationContext(),
                getResources().getDimension(R.dimen.milk_amount_advise_ring_size_D)/2);
        linearLayout.addView(ringWithText,lp);
        ringWithText.setRingBgColor(getResources().getColor(R.color.milk_amount_ring_bg_color));
        ringWithText.setRingColor(getResources().getColor(R.color.milk_amount_ring_color));
        ringWithText.setTextColor(getResources().getColor(R.color.milk_amount_ring_text_color));
        ringWithText.setMaxSweepAngle(drinkAmount/adviseAmount *360);
        final String[] strings = new String[2];
        strings[0]= getResources().getString(R.string.milk_amount_advise_ring_up_doc);
        DecimalFormat format = new DecimalFormat("###");
        strings[1]= String.valueOf(format.format(drinkAmount/adviseAmount*100))+"%";
        float[] sizes = {getResources().getDimension(R.dimen.milk_amount_advise_ring_text_up_size),
                getResources().getDimension(R.dimen.milk_amount_advise_ring_text_down_size)};
        ringWithText.setTexts(strings,sizes);
        RingWithText.updateListener listener  = new RingWithText.updateListener() {
            @Override
            public void updateSweepAngle(float sweepAngle) {
                DecimalFormat format = new DecimalFormat("###");
                strings[1]= String.valueOf(format.format(sweepAngle/360*100))+"%";
            }
        };
        ringWithText.setListener(listener);
        ringWithText.setTimeRing(timeRing);
    }

    private void initListViews(){
        headlistView = (HeadlistView)mView.findViewById(R.id.milk_amount_listview);
        headlistView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        headlistView.setDividerHeight(0);
        initData();
        HeadListViewAdpter adpter = new HeadListViewAdpter(getActivity().getApplicationContext(),listItems,
                R.layout.fragment_milk_amount_listview_item,
                new String[]{"date","totalNum","onceTime","onceAmount","onceTemperature","mulCircle","progressBar","ringWithText","color"},
                new int[]{R.id.milk_amount_time_text,R.id.milk_amount_total_num_text,R.id.milk_amount_listview_item_time_text,
                R.id.milk_amount_listview_item_amount_text,R.id.milk_amount_listview_item_temperature_amount_text,
                R.id.milk_amount_listview_item_title_circle_container,R.id.milk_amount_listview_item_progress_bar,
                R.id.milk_amount_listview_item_circle_container,R.id.milk_amount_listview_item_divide_temperature_amount},

                "date",R.id.milk_amount_time_text,R.id.milk_amount_listview_item_title,
                new String[]{"date","totalNum","titlemulCircle"},
                new int[]{R.id.milk_amount_time_text,R.id.milk_amount_total_num_text,R.id.milk_amount_listview_item_title_circle_container});
        headlistView.setAdapter(adpter);
        headlistView.setHeadView(R.layout.fragment_milk_amount_listview_item_title);
    }
    private void initData( ){
        if (listItems ==null)
            listItems = new ArrayList<Map<String, Object>>();
        multiLayerCircleColors = getResources().getIntArray(R.array.milk_amount_listview_list_item_circle_colors);
        multiLayerCircleWeights = getResources().getIntArray(R.array.milk_amount_listview_item_title_circle_weights);
        MultiLayerCircle titleMultiLayerCircle = new MultiLayerCircle( getActivity().getApplicationContext(),
                getResources().getDimension(R.dimen.milk_amount_circle_D)/2,multiLayerCircleColors,multiLayerCircleWeights);
        for(int i=0;i<dataLoadAmount;i++){
            int count = getTotalDrinkNum();
            String tempString;
            float tempNum;
            int color;
            if (count == 0)
                continue;
            for(int j=0;j<count;j++) {
                Map<String, Object> map = new HashMap<>();
                tempString = getOnceTime();
                if (tempString == null)
                    continue;
                initDataTitle(map,i,count,tempString);

                tempNum = getOnceAmount();
                if(tempNum == -1)
                    continue;
                mTemperature = getOnceTemperature();
                if(mTemperature == null)
                    continue;
                initDataTpAmount(map,mTemperature,tempNum);
                tempNum = getRecord(getAdviseAmount(),tempNum,mTemperature[0],mTemperature[1],getDrinkTime());
                if(tempNum == -1)
                    continue;
               color = selectColor(mTemperature[0],mTemperature[1]);
               initDataProgressBar(map,tempNum,color);
               initDataCircleText(map,tempNum,color);
               initDataMultiLayerCircle(map);
               map.put("titlemulCircle",titleMultiLayerCircle);
               map.put("color",color);
               listItems.add(map);
            }
        }
    }
    private void initDataTitle(Map<String,Object> map,int days,int count ,String tempString){
        map.put("date", getCalenderBefore(days));
        map.put("totalNum", getResources().getString(R.string.milk_amount_drink_total_num_doc) + String.valueOf(count) + "次");
        map.put("onceTime", tempString);
    }
    private void initDataTpAmount(Map<String,Object>map,float[] tp,float amount){
        float temperature ;
        temperature = tp[1];
        DecimalFormat format = new DecimalFormat("###");
        map.put("onceTemperature",String.valueOf(format.format(temperature))+"ml");
        map.put("onceAmount",String.valueOf(amount)+"°C");
    }
    private void initDataMultiLayerCircle(Map<String,Object> map){
        if (multiLayerCircleColors == null)
            multiLayerCircleColors = getResources().getIntArray(R.array.milk_amount_listview_list_item_circle_colors);
        if (multiLayerCircleWeights == null)
             multiLayerCircleWeights = getResources().getIntArray(R.array.milk_amount_listview_item_title_circle_weights);
        MultiLayerCircle multiLayerCircle = new MultiLayerCircle( getActivity().getApplicationContext(),
                getResources().getDimension(R.dimen.milk_amount_circle_D)/2,multiLayerCircleColors,multiLayerCircleWeights);
        map.put("mulCircle",multiLayerCircle);
    }
    private void initDataProgressBar(Map<String,Object> map,float tempNum,int color){
        if (progressBarList == null)
            progressBarList = new ArrayList<>();
        ProgressBar progressBar = new ProgressBar(getActivity().getApplicationContext(),
                getResources().getDimension(R.dimen.milk_amount_listview_item_progress_bar_width),
                getResources().getDimension(R.dimen.milk_amount_listview_item_progress_bar_height));
        progressBar.setColor(color);
        progressBar.setBgColor(progressBgColor);
        progressBar.setMaxNum(tempNum);
        progressBar.setTimeAnimator(timeRing);
        map.put("progressBar",progressBar);
        progressBarList.add(progressBar);
    }
    private void initDataCircleText(Map<String,Object> map,float tempNum,int color){
        float r = getResources().getDimension(R.dimen.milk_amount_listview_item_circle_D) /2;
        RingWithText ring = new RingWithText(getActivity().getApplicationContext(),r);
        ring.setRingBgColor(color);
        ring.setPaintWidth(r);
        DecimalFormat format = new DecimalFormat("###");
        ring.setTexts(new String[]{String.valueOf(format.format(tempNum)),"分"},
                new float[]{getResources().getDimension(R.dimen.milk_amount_listview_item_circle_up_text_size),
                        getResources().getDimension(R.dimen.milk_amount_listview_item_circle_down_text_size)});
        map.put("ringWithText",ring);
    }

    private void startAnimator(){
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startProgressBar();
                ringWithText.startRing();
            }
        },300);

    }
    private void startProgressBar(){
        int count = headlistView.getChildCount();
        Log.i("listview child count",String.valueOf(count));
        if (count == 0)
            return;
        ProgressBar progressBar ;
        for(int i = 0;i<count;i++){
                progressBar = progressBarList.get(i);
                progressBar.startAnimator();
        }
    }
    private String getCalenderBefore(int days){
        if (days == 0)
            return "今天";
        if (days == 1)
            return "昨天";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-days);
        int year = calendar.get(Calendar.YEAR);
        int month  =calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(day)+"日";

    }
    private int selectColor(float temperatureHigh,float temperatureLow){
        float ratio1=0,ratio2=0;
        if (temperatureHigh > TEMPREATUREHIGH)
            ratio1 = (temperatureHigh - TEMPREATUREHIGH)/TEMPREATUREHIGH;
        if (temperatureLow < TEMPREATURELOW)
            ratio2 = (TEMPREATURELOW  -temperatureLow)/TEMPREATURELOW;
        if (ratio1 > ratio2 && ratio1 >0)
            return warningHighColor;
        if (ratio1 < ratio2 && ratio2 >0)
            return warningLowColor;
        if (ratio1 == ratio2 && ratio1 == 0)
            return normalColor;
        return  warningLowColor;
    }
    private String getOneDayDrinkAmount(){
        drinkAmount = getDrinkAmount();
        return  String.valueOf(drinkAmount)+"ml";
    }
    private String getOneDayAdviseAmount(){
        adviseAmount = getAdviseAmount();
        return String.valueOf(adviseAmount)+"ml";
    }
    private String getOnceTime(){
        return "08:36";
    }
    private float getAdviseAmount(){
        return (float)Math.random()*50+200;
    }
    private float getDrinkAmount(){return (float)Math.random()*50+200; }
    private int  getTotalDrinkNum(){
        return 6;
    }
    private int getDrinkTime() {return 10;}
    private float[] getOnceTemperature(){
        float [] tempreture = new float[2];
        float temp;
        tempreture[0] = (float)(Math.random()*30+10);
        tempreture[1] = (float)(Math.random()*30+10);
        if (tempreture[0] < tempreture[1]){
            temp = tempreture[0];
            tempreture[0] = tempreture[1];
            tempreture[1] = temp;
        }

        return tempreture;
    }
    private float getOnceAmount(){
        return 300;
    }

    final static float AMOUNTSCORE = 55;
    final static float TEMPERATURESCORE = 35;
    final static float TIMESCORE = 10,TEMPREATUREHIGH = 40,TEMPREATURELOW = 37,STANDARTIME = 30;
    private float getRecord(float advise ,float amount ,float temperatureHigh,float temperatureLow,float time){
        float ratio,sum = 0;
        if (advise > amount)
            ratio = amount/advise;
        else
            ratio = advise /amount;
        sum += AMOUNTSCORE * ratio;
        Log.i("amount " ,String.valueOf(sum));
        ratio = 0;
        if (temperatureHigh > TEMPREATUREHIGH)
            ratio += (temperatureHigh - TEMPREATUREHIGH)/TEMPREATUREHIGH;
        if (temperatureLow < TEMPREATURELOW)
            ratio += (TEMPREATURELOW  -temperatureLow)/TEMPREATURELOW;
        ratio = ratio> 1?1:ratio;
        sum += TEMPERATURESCORE *(1-ratio);
        Log.i("amount tempreature " ,String.valueOf(sum));
        if (STANDARTIME > time)
            ratio = time/STANDARTIME;
        else
            ratio = STANDARTIME/time;
        sum += TIMESCORE*ratio;
        Log.i("amount tempreature time" ,String.valueOf(sum));
        return sum;
    }


}
