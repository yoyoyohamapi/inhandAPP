package milk.inhand.com.inhandmilk.dao;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import milk.inhand.com.inhandmilk.entity.OneDay;

/**
 * OneDayDao
 * Desc: 日记录数据访问层
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 15:40
 */
public class OneDayDao extends BaseDao {
    @Override
    public void findAllOrLimit(int limit,FindCallback callback) {
        // 查询当前Todo列表
        AVQuery<OneDay> query = AVQuery.getQuery(OneDay.class);
        query.setPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        // 按照更新时间降序排序
        query.orderByDescending("createdAt");
        // 最大返回1000条
        if( limit>0 )
            query.limit(limit);
        query.findInBackground(callback);
    }
}
