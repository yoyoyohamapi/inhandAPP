package com.inhand.milk.dao;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;

/**
 * BaseDao
 * Desc:基础Dao类
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-04
 * Time: 21:24
 */
public class BaseDao {

    /**
     * @return
     */
    public AVObject findByObjectId(String objectId) {
        return null;
    }

    /**
     * @param limit        最大数量，如果为0，代表传入所有
     * @param findCallback 查询完后的回调函数
     */
    public void findAllOrLimit(int limit, FindCallback findCallback) {

    }
}
