package com.inhand.milk.dao;

import android.content.Context;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.entity.Baby;

/**
 * BabyDao
 * Desc:
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-17
 * Time: 08:44
 */
public class BabyDao extends BaseDao {
    AVQuery<Baby> query = AVQuery.getQuery(Baby.class);

    public BabyDao(Context ctx) {
        super(ctx);
    }

    public void findBabiesByUser(AVUser user, FindCallback<Baby> callback) {
        query.whereEqualTo(Baby.USER_KEY, user);
        query.findInBackground(callback);
    }
}
