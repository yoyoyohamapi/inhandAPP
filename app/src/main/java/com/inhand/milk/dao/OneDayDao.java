package com.inhand.milk.dao;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.entity.OneDay;

import java.util.Date;

/**
 * OneDayDao
 * Desc: 日记录数据访问层
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 15:40
 */
public class OneDayDao extends BaseDao {
    private static final String SORT_BY = "createdAt";
    AVQuery<OneDay> query = AVQuery.getQuery(OneDay.class);

    public OneDayDao() {
        query.setPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
    }

    @Override
    public void findAllOrLimit(int limit, FindCallback callback) {
        // 按照更新时间降序排序
        query.orderByDescending(SORT_BY);
        // 最大返回1000条
        if (limit > 0)
            query.limit(limit);
        query.findInBackground(callback);
    }

    public void findOnedayByDay(Date date, FindCallback callback) {
        query.whereEqualTo(OneDay.DATE_KEY, date);
        query.findInBackground(callback);
    }
}
