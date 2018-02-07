package cn.bry.yueke.api;



import cn.bry.yueke.base.ApiResult;
import cn.bry.yueke.api.exception.ExceptionEngine;
import cn.bry.yueke.api.exception.ServerException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RxHelper {


    public static class ServerResultFunc<T> implements Function<ApiResult<T>, T> {
        @Override
        public T apply(ApiResult<T> httpResult) {

            if (!httpResult.isSuccess()) {
                throw new ServerException(httpResult.code,httpResult.msg);
            }
//            if(httpResult.data==null){
//                throw new NullPointerException();
//            }
//            return httpResult.data;

            return httpResult.data==null? (T) "" :httpResult.data;
        }
    }

    public static class HttpResultFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) {
            //ExceptionEngine为处理异常的驱动器
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }

}
