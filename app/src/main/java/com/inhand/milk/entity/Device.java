package com.inhand.milk.entity;

import android.content.Context;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.inhand.milk.App;
import com.inhand.milk.dao.DeviceDao;
import com.inhand.milk.dao.OneDayDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Device
 * Desc: �豸ʵ��
 * Date: 2015/6/2
 * Time: 23:43
 * Created by: Wooxxx
 */
@AVClassName(Device.DEVICE_CLASS)
public class Device extends Base {
    public static final String DEVICE_CLASS = "Milk_Device";

    public static final String SOFTWARE_VERSION_KEY = "software_version";
    public static final String HARDWARE_VERSION_KEY="hardware_version";
    public static final String ADJUST_NUM_KEY= "adjust_num";
    public static final String ADJUST_DEVIATION_KEY="adjust_deviation";
    public static final String MAC_KEY = "mac";
    public static final String PRESSURE_ERROR_NUM_KEY = "pressure_error_num";
    public static final String TEMPERATURE_ERROR_NUM_KEY = "temperature_error_num";
    public static final String ACCELERATE_ERROR_NUM_KEY = "accelerate_error_num";
    public static final String USER_KEY = "user";
    public static final String VERSION_KEY = "version";

    public Device(){

    }

    /**
     * ����豸MAC��ַ
     * @return MAC��ַ
     */
    public String getMac(){
        return this.getString(MAC_KEY);
    }

    public void setMac(String mac){
        this.put(MAC_KEY,mac);
    }

    /**
     * ����豸����汾
     * @return ����汾
     */
    public String getSoftwareVersion(){
        return this.getString(SOFTWARE_VERSION_KEY);
    }

    public void setSoftwareVersion(String softwareVersion){
        this.put(SOFTWARE_VERSION_KEY,softwareVersion);
    }

    /**
     * ����豸Ӳ���汾
     * @return �豸Ӳ���汾
     */
    public String getHardwareVersion(){
        return this.getString(HARDWARE_VERSION_KEY);
    }

    public void setHardwareVersion(String hardwareVersion){
        this.put(HARDWARE_VERSION_KEY,hardwareVersion);
    }

    /**
     * ���У׼����
     * @return У׼����
     */
    public int getAdjustNum(){
        return this.getInt(ADJUST_NUM_KEY);
    }

    public void setAdjustNum(int adjustNum){
        this.put(ADJUST_NUM_KEY,adjustNum);
    }

    /**
     * ���У׼��
     * @return У׼��
     */
    public double getAdjustDeviation(){
        return this.getDouble(ADJUST_DEVIATION_KEY);
    }

    public void setAdjustNumDeviation(double adjustNumDeviation){
        this.put(ADJUST_DEVIATION_KEY,adjustNumDeviation);
    }

    /**
     * ���ѹ�����д������
     * @return ѹ�����д������
     */
    public int getPressureErrorNum(){
        return this.getInt(PRESSURE_ERROR_NUM_KEY);
    }

    public void setPressureErrorNum(int pressureErrorNum){
        this.put(PRESSURE_ERROR_NUM_KEY,pressureErrorNum);
    }

    /**
     * ����¶ȴ������
     * @return �¶ȴ������
     */
    public int getTemperatureErrorNum(){
        return this.getInt(TEMPERATURE_ERROR_NUM_KEY);
    }

    public void setTemperatureErrorNumKey(int temperatureErrorNum){
        this.put(TEMPERATURE_ERROR_NUM_KEY,temperatureErrorNum);
    }

    /**
     * ��ü��ٶ�ģ��������
     * @return ���ٶ�ģ��������
     */
    public int getAccelerateErrorNum(){
        return this.getInt(ACCELERATE_ERROR_NUM_KEY);
    }

    public void setAccelerateErrorNum(int accelerateErrorNum){
        this.put(ACCELERATE_ERROR_NUM_KEY,accelerateErrorNum);
    }

    /**
     * ��õ�ǰ���ݼ�¼�汾
     * @return �汾��
     */
    public String getVersion(){
        return this.getString(VERSION_KEY);
    }

    public void setVersion(String version){
        this.put(VERSION_KEY,version);
    }

    public User getUser(){
        return this.getAVUser(USER_KEY,User.class);
    }
    /**
     * �����豸��Ӧ�û�
     * @param user �û�
     */
    public void setUser(User user) {
        try {
            this.put(USER_KEY, AVObject.createWithoutData(User.class, user.getObjectId()));
        } catch (AVException e) {
            e.printStackTrace();
        }
    }
    /**
     * �����豸��Ϣ���ƶ�
     * @param callback �洢�ص��ӿ�
     * @param ctx �����Ļ���
     */
    public void saveInCloud(Context ctx,final SaveCallback callback){
        final Device  device = this;
        if( device.getUser() == null )
            device.setUser(App.getCurrentUser());
        //���°汾��ʾ
        SimpleDateFormat sdf = new SimpleDateFormat(VERSION_FORMAT);
        String version = sdf.format(new Date());
        device.setVersion(version);
        device.saveInBackground(callback);
    }

    /**
     * �����豸��Ϣ������
     * @param ctx �����Ļ���
     * @param callback �洢�ص��ӿ�
     */
    public void saveInDB(Context ctx,final DBSavingCallback callback){
        if( this.getUser() == null )
            this.setUser(App.getCurrentUser());
        //���°汾��ʾ
        SimpleDateFormat sdf = new SimpleDateFormat(VERSION_FORMAT);
        String version = sdf.format(new Date());
        this.setVersion(version);
        DBSavingTask task = new DBSavingTask(ctx, callback) {
            @Override
            protected Object doInBackground(Object[] params) {
                //���±������ݿ�
                DeviceDao deviceDao = new DeviceDao(ctx);
                deviceDao.updateOrSaveInDB(Device.this);
                return null;
            }
        };
        task.execute();
    }
}
