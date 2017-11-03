package com.yishengyue.seller.api;

import com.yishengyue.seller.base.ApiResult;
import com.yishengyue.seller.base.User;
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

    /**
     * 登录
     * @return
     */
    @POST("oto/user/login/password")
    Observable<ApiResult<User>> login(@Body Map<String, Object> params);
    /**
     * 快捷登录
     * @return
     */
    @POST("oto/user/login/verifyCode")
    Observable<ApiResult<User>> fastLogin(@Body Map<String, Object> params);
}
