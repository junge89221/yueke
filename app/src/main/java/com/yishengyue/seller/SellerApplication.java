package com.yishengyue.seller;


import android.app.Application;

public class SellerApplication extends Application {

    private static SellerApplication application;

    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }

    public static SellerApplication getApplication() {
        return application;
    }
}
