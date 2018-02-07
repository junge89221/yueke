package cn.bry.yueke.http.okhttp;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * OkHttp 日志拦截器
 */
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "okhttp";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.e(TAG,"\n");
        Log.e(TAG,"----------Start----------------");
        Log.e(TAG, "| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            Log.e(TAG,"请求数据"+getReqData(request.body()));
            Log.d(TAG, "| RequestParams:"+getReqData(request.body()));
//            StringBuilder sb = new StringBuilder();
//            if (request.body() instanceof FormBody) {
//                FormBody body = (FormBody) request.body();
//                for (int i = 0; i < body.size(); i++) {
//                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
//                }
//                sb.delete(sb.length() - 1, sb.length());
//                Log.d(TAG, "| RequestParams:{"+sb.toString()+"}");
//            }
        }
        Log.e(TAG, "| Response:" + content);
        Log.e(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();

    }

    private static final Charset UTF8 = Charset.forName("UTF-8");
    public static String getReqData(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readString(UTF8);
    }
}

