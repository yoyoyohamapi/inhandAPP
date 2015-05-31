package com.inhand.milk.fragment.bluetooth;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/5/30.
 */
public class OneDayMessageTest extends BaseBluetoothMessages {
    private byte[] bytes;
    private static final int MAXLEN = 10,AdviseMaxFlags = 0,AdviseMinFlags =1,MilkDensityFlags =2,UerserTimeFlags =3,
            AllFlags = 0x0f;
    private int flags =0;
    Context cnt;
    OneDayMessageTest(Context cnt){
        super();
        bytes = new byte[MAXLEN];
        flags = 0;
        setTitle();
        this.cnt =cnt;

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,1);

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,5);

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,9);

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,13);

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,17);

        floatToData(bytes,getOneFloat(),DataVaild);
        setByte(bytes,4,21);

        setCRC(25);

        setEnd(27);


    }
    public int message2Bytes(byte[] buffer){
        return getBytes(buffer);
    }
    private void setTitle(){
        bytes[0] = (byte)0x01;
        setByte(bytes,1,0);
    }
    private float getOneFloat(){
        float temp =(float)Math.random()*255;
        Toast.makeText(cnt,String.valueOf(temp),1000).show();
        return temp;
    }
}
