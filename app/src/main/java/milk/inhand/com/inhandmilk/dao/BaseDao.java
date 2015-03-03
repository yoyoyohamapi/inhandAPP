package milk.inhand.com.inhandmilk.dao;

import android.content.Context;

import java.util.ArrayList;

import milk.inhand.com.inhandmilk.helper.DatabaseHelper;

/**
 * BaseDao
 * Desc:
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:11
 */
public abstract class BaseDao {
    protected Context ctx;
    protected DatabaseHelper dbHelper;

    public BaseDao(Context ctx) {
        this.ctx = ctx;
        this.dbHelper = new DatabaseHelper(ctx);
    }

    /**
     * 返回所有数据对象
     * @return 数据对象列表
     */
    public abstract ArrayList findAll();
}
