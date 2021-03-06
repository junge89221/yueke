package cn.bry.yueke.http.okhttp;

import android.util.Log;

import cn.bry.yueke.base.Data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 为每个请求添加固定的Header
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        Request requestNew = builder.build();
        return chain.proceed(requestNew);
    }

}
