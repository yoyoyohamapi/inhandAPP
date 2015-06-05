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
 * Desc: 设备访问层
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
     * 同步地从云端获取当前用户的设备
     * @return 当前用户的设别
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
     * 异步地从云端获取当前用户的设备信息
     * @param callback 回调接口
     */
    public void findFromCloudByCurrentUser(
            final FindCallback<Device> callback){
        query = AVQuery.getQuery(Device.class);
        query.whereEqualTo(Device.USER_KEY, App.getCurrentUser());
        query.findInBackground(callback);
    }

    /**
     * 异步地从本地获取当前用户的设备信息
     * @param callback 查询回调接口
     */
    public void findFromDBByCurrentUser(
            final DBFindCallback<Device> callback
    ){

    }

    /**
     * 同步地从本地获取当前用户的设备信息
     *
     */
    public List<Device> findFromDBByCurrentUser(){
        return null;
    }

    /**
     * 同步地从本地根据MAC地址获得设备
     * @param MAC MAC地址
     * @return 设备对象
     */
    public Device findFromDBByMAC(String MAC){

    }

    /**
     * 异步地根据MAC地址从本地获取设备
     * @param MAC mac地址
     * @param callback 回调接口
     */
    public void findFromDBByMAC(
            String MAC,
            final DBFindCallback<Device> callback){

    }

    /**
     * 同步地从本地根据MAC地址获得设备
     * @param MAC MAC地址
     * @return 设备对象
     */
    public Device findFromCloudByMAC(String MAC){

    }

    /**
     * 异步地根据MAC地址从本地获取设备
     * @param MAC mac地址
     * @param callback 回调接口
     */
    public void findFromCloudByMAC(
            String MAC,
            final DBFindCallback<Device> callback){

    }



    @Override
    public void updateOrSaveInDB(Device device) {
        // 比较字段为设备地址
        String MAC = device.getMac();
        String whereClause = DBHelper.COLUMN_COMP + "=?";
        String[] whereArgs = {MAC};
        String saveJson = JSONHelper.getValidJSON(device.toJSONObject().toString());
        Device old = findFromDBByMAC(MAC);
        // 如果已存在且版本不一致,则更新
        if (old != null) {
            refresh(device,old);
            String oldJSON = JSONHelper.getValidJSON(old.toJSONObject().toString());
            //保存更新当前版本标识
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_JSON, oldJSON);
            cv.put(DBHelper.COLUMN_VERSION, old.getVersion());
            db.update(Device.DEVICE_CLASS, cv, whereClause, whereArgs);
        } else {
            //否则直接插入
            dbHelper.insertToJson(
                    Device.DEVICE_CLASS,
                    saveJson,
                    device.getVersion(),
                    MAC
            );
        }
    }


    //刷新设备信息
    private void refresh(Device newOne,Device oldOne){

    }
}
