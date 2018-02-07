package cn.bry.yueke.http.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 为每个请求添加固定的Header
 */
public class HttpsHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        Request requestNew = builder.build();
        Response response = chain.proceed(requestNew);

        return response;
    }

}
