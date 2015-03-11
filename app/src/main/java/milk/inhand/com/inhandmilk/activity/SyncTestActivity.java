package milk.inhand.com.inhandmilk.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import milk.inhand.com.inhandmilk.R;
import milk.inhand.com.inhandmilk.dao.OneDayDao;
import milk.inhand.com.inhandmilk.entity.OneDay;
import milk.inhand.com.inhandmilk.entity.Record;
import milk.inhand.com.inhandmilk.utils.ViewHolder;

/**
 * SyncTestActivity
 * Desc: 测试同步的Activity
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 09:44
 */
public class SyncTestActivity extends BaseActivity{
    private Button saveBtn;
    private Button syncBtn;
    private EditText durationEditor;
    private EditText maxTemperEditor;
    private EditText minTemperEditor;
    private EditText volumeEditor;
    private ListView listView;
    private OneDay today;
    private List<OneDay> oneDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_test);
        saveBtn = (Button)findViewById(R.id.save_btn);
        syncBtn = (Button)findViewById(R.id.sync_btn);
        durationEditor = (EditText)findViewById(R.id.duration_edit);
        minTemperEditor = (EditText)findViewById(R.id.min_temperature_edit);
        maxTemperEditor = (EditText)findViewById(R.id.max_temperature_edit);
        volumeEditor = (EditText)findViewById(R.id.volume_edit);
        listView = (ListView)findViewById(R.id.list);

        today = new OneDay();
        today.setDate(new Date());

        setListeners();
        initList();
    }

    private void initList(){
        OneDayDao oneDao = new OneDayDao();
        oneDao.findAllOrLimit(0, new FindCallback() {
            @Override
            public void done(List list, AVException e) {
                if( e == null ) {
                    oneDays = list;
                    List<Record> records = oneDays.get(0).getRecords();
                    System.out.println("Size:" + records.size());
                    RecordAdapter adapter = new RecordAdapter(
                            SyncTestActivity.this,
                            R.layout.item_sync_test,
                            records);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private void setListeners(){
        // 保存按钮
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //单击存储，存储一条饮奶记录
                int duration = Integer.valueOf(durationEditor.getText()
                        .toString());
                double maxTemperature = Double.valueOf(maxTemperEditor.getText()
                        .toString());
                double minTemperature = Double.valueOf(minTemperEditor.getText()
                        .toString());
                int volume = Integer.valueOf(volumeEditor.getText()
                        .toString());

                int sumVolume = 0;
                List<Record> records = new ArrayList<Record>();
                for(int i=0;i<10;i++){
                    Record record = new Record();
                    record.setVolume(duration + i);
                    record.setMaxTemperature(maxTemperature + i * 2.0);
                    record.setMinTemperature(minTemperature + i * 2.0);
                    record.setVolume(volume + i * 3);
                    sumVolume += record.getVolume();
                    records.add(record);
                }

                today.setVolume(sumVolume);
                today.setRecords(records);
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                today.save(null);
            }
        });
    }

    /**
     * 显示饮水记录的Adapter
     */
    public class RecordAdapter extends BaseAdapter{
        private int resId;
        private List<Record> records;
        private Context context;

        public RecordAdapter(Context context, int resource, List<Record> records) {
            this.resId = resource;
            this.records = records;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Record record = getItem(position);
            if( convertView==null ) {
                convertView = LayoutInflater.from(context)
                        .inflate(resId, parent, false);
            }
            TextView durationTxt = ViewHolder.get(convertView, R.id.duration_text);
            TextView minTmpTxt = ViewHolder.get(convertView,R.id.min_temperature_text);
            TextView maxTmpTxt = ViewHolder.get(convertView,R.id.max_temperature_text);
            TextView volumeTxt = ViewHolder.get(convertView,R.id.volume_text);
            durationTxt.setText(String.valueOf(record.getDuration()));
            minTmpTxt.setText(String.valueOf(record.getMinTemperature()));
            maxTmpTxt.setText(String.valueOf(record.getMaxTemperature()));
            volumeTxt.setText(String.valueOf(record.getVolume()));
            return convertView;
        }

        @Override
        public Record getItem(int position) {
            return records.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return records.size();
        }

    }

}
