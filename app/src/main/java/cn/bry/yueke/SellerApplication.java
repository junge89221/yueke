package cn.bry.yueke;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import cn.braing.pay.lib.BraingSdk;
import cn.bry.yueke.util.Utils;

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
        BraingSdk.initSDK(this,"100000000000001","575262D9AAE3CA2BE0501A0AC27C0884","http://47.97.6.226:443/yuek/pay/testcallback.do","192.168.0.99");
    }
}
