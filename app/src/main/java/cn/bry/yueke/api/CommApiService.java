package cn.bry.yueke.api;

import cn.braing.pay.lib.bean.CommRequest;
import cn.braing.pay.lib.bean.LoginReq;
import cn.bry.yueke.base.ApiResult;
import cn.bry.yueke.base.CommRequest2;
import cn.bry.yueke.base.Order;
import cn.bry.yueke.base.User;
import cn.bry.yueke.base.VerifyCodeBean;

import java.util.Map;

import cn.bry.yueke.base.loginResp;
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
     * 统一地址
     *
     * @return
     */
    @POST("yueki/excute.do")
    Observable<ApiResult<loginResp>> getData(@Body CommRequest2 params);


    /**
     * 注册
     *
     * @return
     */
    @POST("beforeuser/saveBeforeuser")
    Observable<ApiResult<String>> register(@Query("phonenumber") String loginName, @Query("pwd") String password, @Query("type") int type);

    /**
     * 获取短信验证码
     */
    @GET("verifycode/getVerifyCode")
    Observable<ApiResult<VerifyCodeBean>> getVerifyCode(@Query("mobile") String mobile, @Query("type") String type);

    /**
     * 登录
     *
     * @return
     */
    @POST("login")
    Observable<ApiResult<User>> login(@Query("aname") String aname, @Query("password") String password, @Query("type") String type);

    /**
     * 快捷登录
     *
     * @return
     */
    @POST("oto/user/login/verifyCode")
    Observable<ApiResult<User>> fastLogin(@Query("loginName") String loginName, @Query("verifyCode") String verifyCode);

    /**
     * （卖家端）通过消费验证码获取订单详情信息
     */
    @GET("oto/seller/order/get/consumeCode")
    Observable<ApiResult<Order>> getOrderDetail(@Query("userId") String userId, @Query("consumeVerifyCode") String consumeVerifyCode);

    /**
     * 核销验证码[扫描二维码或手动收
     *
     * @return
     */
    @POST("oto/seller/order/scanQrCode")
    Observable<ApiResult<String>> activateOrder(@Query("userId") String userId, @Query("consumeVerifyCode") String consumeVerifyCode, @Query("orderDetailId") String orderDetailId);

    /**
     * 忘记密码
     *
     * @return
     */
    @POST("oto/user/phone/updatePwd")
    Observable<ApiResult<String>> forgetPassword(@Query("phone") String phone, @Query("verifyCode") String verifyCode, @Query("newPwd") String newPwd);

}
