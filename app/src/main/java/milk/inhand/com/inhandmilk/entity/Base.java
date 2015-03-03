package milk.inhand.com.inhandmilk.entity;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.UUID;

import milk.inhand.com.inhandmilk.helper.DatabaseHelper;

/**
 * Base
 * Desc:实体基类，子类需要重载getTable()方法
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:06
 */
public class Base {
    protected DatabaseHelper dbHelper;
    protected String localUuid;

    public Base() {
    }

    public Base(Context ctx) {
        this.dbHelper = new DatabaseHelper(ctx);
        UUID uuid = UUID.randomUUID();
        this.localUuid = uuid.toString();
    }

    public String getLocalUuid() {
        return localUuid;
    }

    public String getTable(){
        return null;
    }

    /**
     * 存储数据对象至数据库
     */
    public void save(){
        //转换为json存入sqlite
        String obj = JSON.toJSONString(this);
        this.dbHelper.insert(this.getTable(),obj,getLocalUuid());
    }

}
