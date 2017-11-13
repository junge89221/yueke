package com.yishengyue.seller.api;

import com.yishengyue.seller.base.ApiResult;
import com.yishengyue.seller.base.Order;
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
    Observable<ApiResult<User>> register(@Body Map<String, Object> params);

    /**
     * 获取短信验证码
     */
    @GET("verifycode/getVerifyCode")
    Observable<ApiResult<VerifyCodeBean>> getVerifyCode(@Query("mobile") String mobile,@Query("type") String type);

    /**
     * 登录
     * @return
     */
    @POST("oto/user/login/password")
    Observable<ApiResult<User>> login(@Query("loginName") String  loginName,@Query("password") String  password);
    /**
     * 快捷登录
     * @return
     */
    @POST("oto/user/login/verifyCode")
    Observable<ApiResult<User>> fastLogin(@Query("loginName") String  loginName,@Query("verifyCode") String  verifyCode);

    /**
     * （卖家端）通过消费验证码获取订单详情信息
     */
    @GET("oto/seller/order/get/consumeCode")
    Observable<ApiResult<Order>> getOrderDetail(@Query("userId") String userId, @Query("consumeVerifyCode") String consumeVerifyCode);

    /**
     * 核销验证码[扫描二维码或手动收
     * @return
     */
    @POST("oto/seller/order/scanQrCode")
    Observable<ApiResult<String>> activateOrder(@Query("userId") String  userId,@Query("consumeVerifyCode") String  consumeVerifyCode,@Query("orderDetailId") String  orderDetailId);

    /**
     * 忘记密码
     * @return
     */
    @POST("oto/user/phone/updatePwd")
    Observable<ApiResult<String>> forgetPassword(@Query("phone") String  phone,@Query("verifyCode") String  verifyCode,@Query("newPwd") String  newPwd);

}
