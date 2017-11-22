package com.yishengyue.seller;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.yishengyue.seller.util.Utils;

/**
 * Created by zhangli on 2017/11/22
 */

public class SellerApplication extends MultiDexApplication {
    private static Context sApplication;

    public static Context getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication =this;
        Utils.init(this);
    }
}
