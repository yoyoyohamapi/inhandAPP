package com.inhand.milk.dao;

import android.content.ContentValues;
import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.App;
import com.inhand.milk.entity.Base;
import com.inhand.milk.entity.Device;
import com.inhand.milk.entity.OneDay;
import com.inhand.milk.entity.User;
import com.inhand.milk.helper.DBHelper;
import com.inhand.milk.helper.JSONHelper;

import java.util.List;

/**
 * DeviceDao
 * Desc: �豸���ʲ�
 * Date: 2015/6/3
 * Time: 7:23
 * Created by: Wooxxx
 */
public class DeviceDao
        extends BaseDao<Device>{

    public DeviceDao(Context ctx) {
        super(ctx);
    }

    /**
     * ͬ���ش��ƶ˻�ȡ��ǰ�û����豸
     * @return ��ǰ�û������
     */
    public Device findFromCloudByCurrentUser() {
        query = AVQuery.getQuery(Device.class);
        query.whereEqualTo(Device.USER_KEY, App.getCurrentUser());
        try {
            List<Device> devices = query.find();
            return devices.isEmpty()?null:devices.get(0);
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * �첽�ش��ƶ˻�ȡ��ǰ�û����豸��Ϣ
     * @param callback �ص��ӿ�
     */
    public void findFromCloudByCurrentUser(
            final FindCallback<Device> callback){
        query = AVQuery.getQuery(Device.class);
        query.whereEqualTo(Device.USER_KEY, App.getCurrentUser());
        query.findInBackground(callback);
    }

    /**
     * �첽�شӱ��ػ�ȡ��ǰ�û����豸��Ϣ
     * @param callback ��ѯ�ص��ӿ�
     */
    public void findFromDBByCurrentUser(
            final DBFindCallback<Device> callback
    ){

    }

    /**
     * ͬ���شӱ��ػ�ȡ��ǰ�û����豸��Ϣ
     *
     */
    public List<Device> findFromDBByCurrentUser(){
        return null;
    }

    /**
     * ͬ���شӱ��ظ���MAC��ַ����豸
     * @param MAC MAC��ַ
     * @return �豸����
     */
    public Device findFromDBByMAC(String MAC){

    }

    /**
     * �첽�ظ���MAC��ַ�ӱ��ػ�ȡ�豸
     * @param MAC mac��ַ
     * @param callback �ص��ӿ�
     */
    public void findFromDBByMAC(
            String MAC,
            final DBFindCallback<Device> callback){

    }

    /**
     * ͬ���شӱ��ظ���MAC��ַ����豸
     * @param MAC MAC��ַ
     * @return �豸����
     */
    public Device findFromCloudByMAC(String MAC){

    }

    /**
     * �첽�ظ���MAC��ַ�ӱ��ػ�ȡ�豸
     * @param MAC mac��ַ
     * @param callback �ص��ӿ�
     */
    public void findFromCloudByMAC(
            String MAC,
            final DBFindCallback<Device> callback){

    }



    @Override
    public void updateOrSaveInDB(Device device) {
        // �Ƚ��ֶ�Ϊ�豸��ַ
        String MAC = device.getMac();
        String whereClause = DBHelper.COLUMN_COMP + "=?";
        String[] whereArgs = {MAC};
        String saveJson = JSONHelper.getValidJSON(device.toJSONObject().toString());
        Device old = findFromDBByMAC(MAC);
        // ����Ѵ����Ұ汾��һ��,�����
        if (old != null) {
            refresh(device,old);
            String oldJSON = JSONHelper.getValidJSON(old.toJSONObject().toString());
            //������µ�ǰ�汾��ʶ
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_JSON, oldJSON);
            cv.put(DBHelper.COLUMN_VERSION, old.getVersion());
            db.update(Device.DEVICE_CLASS, cv, whereClause, whereArgs);
        } else {
            //����ֱ�Ӳ���
            dbHelper.insertToJson(
                    Device.DEVICE_CLASS,
                    saveJson,
                    device.getVersion(),
                    MAC
            );
        }
    }


    //ˢ���豸��Ϣ
    private void refresh(Device newOne,Device oldOne){

    }
}
