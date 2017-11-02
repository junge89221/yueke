package com.yishengyue.seller.api.subscriber;


import com.yishengyue.seller.api.exception.ApiException;

import io.reactivex.Observer;

/**
 * Created on 2017/2/24
 * Author：qzj
 */
abstract class BaseSubscriber<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if (e != null) e.printStackTrace();
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,-1));
        }

        onComplete();
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);

}