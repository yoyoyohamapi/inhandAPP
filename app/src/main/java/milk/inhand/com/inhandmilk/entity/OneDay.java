package milk.inhand.com.inhandmilk.entity;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

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
public class OneDay extends AVObject{
    static final String ONEDAY_CLASS = "OneDay";
    private static final String VOLUME_KEY = "volume";
    private static final String RECORDS_KEY = "records";
    private static final String DATE_KEY = "date";

    // 日奶量
    private int volume = 0;
    //日喝奶记录
    private List<Record> records;
    // 哪日
    private String date;

    public OneDay(){

    }

    public int getVolume() {
        return this.getInt(VOLUME_KEY);
    }

    public void setVolume(int volume) {
        this.put(VOLUME_KEY,volume);
    }

    public List<Record> getRecords() {
        String recordsJSON = this.getString(RECORDS_KEY);
        records = JSON.parseArray(recordsJSON,Record.class);
        return records;
    }

    public void setRecords(List<Record> records) {
        this.put(RECORDS_KEY,
                JSON.toJSONString(records,true));
    }

    public String getDate() {
        return this.getString(DATE_KEY);
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.put(DATE_KEY,sdf.format(date));
    }

    /**
     * 存储OneDay对象，若该“日”已存在，则为更新
     */
    public void save(final SaveCallback saveCallback){
        boolean success;
        final OneDay day = this;
        AVQuery<OneDay> query = AVQuery.getQuery(OneDay.class);
        query.setPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo(OneDay.DATE_KEY, this.getDate());
        query.findInBackground(new FindCallback<OneDay>() {
            @Override
            public void done(List<OneDay> oneDays, AVException e) {
                //如果不存在，则插入
                if (e!=null) {
                    day.saveInBackground(saveCallback);
                } else {
                    OneDay oneDay = oneDays.get(0);
                    oneDay.setRecords(day.getRecords());
                    oneDay.setVolume(day.getVolume());
                    oneDay.saveInBackground(saveCallback);
                }
            }
        });
    }

}
