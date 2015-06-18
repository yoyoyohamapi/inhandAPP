package com.inhand.milk.fragment.bluetooth;

import android.util.Log;

import com.inhand.milk.entity.OneDay;
import com.inhand.milk.entity.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2015/5/24.
 */
public class BluetoothData {
    private static byte[] buf;
    private static int  location;
    private static  final int MAXLEN = 1024;
    private static SimpleDateFormat simpleDateFormat;
    private static enum DATASTATUS  {ERROR,DATAERROR,DATAVAILD,DATAINVAILD};
    private static BluetoothData bluetoothData;
    private BluetoothData(){
        this.buf = new byte[MAXLEN];
        this.location =  0;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
    }
    private static synchronized  void init(){
        if(bluetoothData == null)
            bluetoothData = new BluetoothData();
    }
     public static BluetoothData getInstance(){
        if (bluetoothData == null)
            init();
         return bluetoothData;
    }

    //这个没有进行多线程保护的。。。但是我觉得应该不会，也没有必要的。。除非你开两个蓝牙的接收线程。
    public boolean saveData(byte[] buf, int len){
        if (len +location > MAXLEN)
            return  false;
        for(int i=0;i<len;i++){
            this.buf[i+location] = buf[i];
        }
        location = len +location;
        handleMessage();
        return true;
    }
    public void handleMessage(){
        if (location <=0)
            return;
        switch (buf[0]){
            case 1:
                if ( type01() == false)
                    return;
                else {
                    handleMessage();
                    break;
                }

            default:
                if (deleteHeadByte(1) == true){
                      handleMessage();
                }
        }
    }
    private boolean deleteHeadByte(int len){
        if(len >location )
            return false;
        int i;
        for (i = len;i<location;i++){
            buf[i - len] = buf[i];
        }
        location = location -len;
        return true;
    }
//暂时处理成，数据只有全部有效的时候存储，不存储错误信息,false 代表不是这个报文，true 代表是这类报文，并处理了。
    private boolean type01(){
        float temp,continuTime,interval;
        Record record ;
        List<Record> records  = new ArrayList<>();
        OneDay oneDay  = new OneDay();
        if (location <28)
            return false;
        if (buf[0] != 1)
            return false;
        if(type01Check() == false) {
            deleteHeadByte(28);
            return true;
        }
        record = new Record();
        temp = getDataValue(1);
        record.setBeginTemperature(temp);

        temp = getDataValue(5);
        record.setEndTemperature(temp);

        temp = getDataValue(9);
        record.setVolume((int)temp);

        continuTime = getDataValue(13);
        interval = getDataValue(17);
        setRecordTime(record,continuTime,interval);

        /*temp = getDataValue(21);
        * 建议量存储没有写
        */
        records.add(record);
        oneDay.setRecords(records);

        deleteHeadByte(28);
        return true;
    }

    private void setRecordTime(Record record,float continuTime,float interva ){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -(int) interva);
        calendar.add(Calendar.MINUTE, -(int)continuTime);
        record.setBeginTime(simpleDateFormat.format(calendar.getTime()));
    }
    //false 代表报文有错误，true 代表正确.
    private boolean type01Check(){
        if (checkData(1) != DATASTATUS.DATAVAILD)
            return false;
        if (checkData(5) != DATASTATUS.DATAVAILD)
            return false;
        if (checkData(9) != DATASTATUS.DATAVAILD)
            return false;
        if (checkData(13) != DATASTATUS.DATAVAILD)
            return false;
        if (checkData(17) != DATASTATUS.DATAVAILD)
            return false;
        if (checkData(21) != DATASTATUS.DATAVAILD)
            return false;
        if(checkCRC(25) == false)
            return false;
        if(buf[27] != (byte)0xff)
            return false;
        return true;
    }


    private float getDataValue(int start){
        byte high,low,decimal;
        float value;
        high = buf[start+1];
        low = buf[start+2];
        decimal =  buf[start+3];
        value = (high&0xff)*256 + (low&0xff) + (decimal&0xff)*(2>>8);
        Log.i("Bluetooth",String.valueOf(value));
        return value;
    }
    private DATASTATUS checkData(int start){
        if ( ( buf[start]&(byte)0x02 ) != (byte)0 ) {
            if (( buf[start] & (byte)0x01) == (byte)0)
                return DATASTATUS.ERROR;
            if (  buf[start+1] != (byte)0xff)
                return DATASTATUS.ERROR;
            if ( buf[start+2] != (byte)0xff)
                return DATASTATUS.ERROR;
            if ( buf[start+3] != (byte)0xff)
                return DATASTATUS.ERROR;
            return DATASTATUS.DATAINVAILD;
        }
        if( ( buf[start]&(byte)0x01 ) != (byte)0)
            return DATASTATUS.DATAERROR;
        return DATASTATUS.DATAVAILD;
    }
    private boolean checkCRC(int start){
        int index;
        byte crcl,crch;
        crcl = (byte)0xff;
        crch = (byte)0xff;
        for(int i=0;i<start;i++)
        {
            index = crcl ^ buf[i];
            crcl = (byte) (crch ^ CRC.crc_lo[index]);
            crch = CRC.crc_hi[index] ;
        }
        if ( crch == buf[start]  && crcl == buf[start +1]){
            return true;
        }
        return false;
    }
}
