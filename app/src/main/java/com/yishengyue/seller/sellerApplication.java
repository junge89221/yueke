package com.yishengyue.seller;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.yishengyue.seller.util.Utils;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/2
 * desc：
 * <pre>
 */

public class sellerApplication extends MultiDexApplication {
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
