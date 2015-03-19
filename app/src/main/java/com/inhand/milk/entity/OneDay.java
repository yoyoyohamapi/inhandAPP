package com.inhand.milk.entity;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.inhand.milk.dao.OneDayDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OneDay
 * Desc: 某日的统计
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 09:17
 */
@AVClassName(OneDay.ONEDAY_CLASS)
public class OneDay extends AVObject {
    public static final String ONEDAY_CLASS = "Milk_OneDay";
    public static final String VOLUME_KEY = "volume";
    public static final String RECORDS_KEY = "records";
    public static final String DATE_KEY = "date";
    public static final String SCORE_KEY = "score";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String BABY_KEY = "baby";

    //日喝奶记录
    private List<Record> records;

    public OneDay() {

    }

    public int getVolume() {
        return this.getInt(VOLUME_KEY);
    }

    public void setVolume(int volume) {
        this.put(VOLUME_KEY, volume);
    }

    public List<Record> getRecords() {
        String recordsJSON = this.getString(RECORDS_KEY);
        records = JSON.parseArray(recordsJSON, Record.class);
        return records;
    }

    public void setRecords(List<Record> records) {
        this.put(RECORDS_KEY,
                JSON.toJSONString(records, true));
    }

    public Date getDate() {
        return this.getDate(DATE_KEY);
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = sdf.format(date);
        Date dstDate = null;
        try {
            dstDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.put(DATE_KEY, dstDate);
    }

    public int getScore() {
        return this.getInt(SCORE_KEY);
    }

    public void setScore(int score) {
        this.put(SCORE_KEY, score);
    }

    public void setBaby(Baby baby) {
        this.put(BABY_KEY, baby);
    }


    /**
     * 存储OneDay对象，若该“日”已存在，则为更新
     */
    public void save(final SaveCallback saveCallback) {
        final OneDay day = this;
        OneDayDao oneDayDao = new OneDayDao();
        oneDayDao.findOnedayByDay(day.getDate(), new FindCallback<OneDay>() {
            @Override
            public void done(List<OneDay> oneDays, AVException e) {
                //如果不存在，插入
                if (e != null) {
                    //day(App.getCurrentUser());
                    day.saveInBackground(saveCallback);
                } else {
                    //否则更新
                    OneDay oneDay = oneDays.get(0);
                    oneDay.setRecords(day.getRecords());
                    oneDay.setVolume(day.getVolume());
                    oneDay.setScore(day.getScore());
                    oneDay.saveInBackground(saveCallback);
                }
            }
        });
    }

}
