package milk.inhand.com.inhandmilk.helper;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * JSONHelper
 * Desc:JSON处理帮助类
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:01
 */
public class JSONHelper {

    /**
     * 从文件解析JSON数组为对象列表
     * @param ctx 上下文环境
     * @param file 文件名
     * @param clazz 返回对象类型
     * @return 对象列表
     */
    public static  <T> List<T> parseJSONArray(Context ctx,String file,Class<T> clazz){
        InputStream is = null;
        try {
            is = ctx.getResources().getAssets().open(file);
            byte [] buffer = new byte[is.available()] ;
            is.read(buffer);
            String json = new String(buffer,"utf-8");
            is.close();
            return JSON.parseArray(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
