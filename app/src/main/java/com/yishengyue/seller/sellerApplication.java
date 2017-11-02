package com.yishengyue.seller;

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
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
