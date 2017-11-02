package com.yishengyue.seller.api;

import com.yishengyue.seller.base.ApiResult;
import com.yishengyue.seller.base.VerifyCodeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * API
 */
public interface CommApiService {
    /**
     * 注册
     * @return
     */
    @POST("oto/user/register")
    Observable<ApiResult<String>> register(@Body Map<String, Object> params);

    /**
     * 获取短信验证码
     */
    @GET("verifycode/getVerifyCode")
    Observable<ApiResult<VerifyCodeBean>> getVerifyCode(@Query("mobile") String mobile);
}
