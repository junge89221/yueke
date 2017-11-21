package com.yishengyue.seller.base;

import android.content.Context;

import com.yishengyue.seller.util.Constant;
import com.yishengyue.seller.util.Utils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : 初始化内存相关数据
 * </pre>
 */
public class Data {


     public static void setUser(User mUser) {
        Data.User = mUser;
    }


    public static User getUser() {
        return User;
    }

    private static User User;


    public static String getPhone(){
        return Utils.getSpUtils().getString(Constant.PHONE);
    }


    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        Data.isLogin = isLogin;
    }

    private static boolean isLogin= false;
    private Data() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {

     }




}