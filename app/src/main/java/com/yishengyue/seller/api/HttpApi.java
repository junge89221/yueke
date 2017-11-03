package com.yishengyue.seller.api;




import com.yishengyue.seller.base.ApiResult;
import com.yishengyue.seller.http.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户模块网络请求Api
 */
public abstract class HttpApi<T> {

    protected T apiService = null;

    protected HttpApi() {
        apiService = RetrofitManager.getHttpsApiService(initService());
    }

    protected abstract Class<T> initService();

    /**
     * 线程、异常等处理
     * 返回对象为剥离了外层ApiResult的对象
     */
    protected <E> Observable<E> dispose(Observable<ApiResult<E>> ob){
        return ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new RxHelper.ServerResultFunc<E>())
                .onErrorResumeNext(new RxHelper.HttpResultFunc<E>());
    }

    /**
     * 线程、异常等处理
     * 返回对象为原始对象
     */
    protected <E> Observable<E> disposeOriginal(Observable<E> ob){
        return ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new RxHelper.HttpResultFunc<E>());
    }

}