package com.yishengyue.seller.api;

import com.yishengyue.seller.base.User;
import com.yishengyue.seller.base.VerifyCodeBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 用户模块网络请求Api
 */
public class CommApi extends HttpApi<CommApiService> {

    private static CommApi api = null;

    @Override
    public Class<CommApiService> initService() {
        return CommApiService.class;
    }

    public static CommApi instance() {
        if (api == null) {
            synchronized (CommApi.class) {
                if (api == null) {
                    api = new CommApi();
                }
            }
        }

        return api;
    }

    /**
     * 注册
     *
     * @param loginName
     * @param password
     * @param verifyCode
     * @return
     */
    public Observable<String> register(String loginName, String password, String verifyCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("password", password);
        params.put("verifyCode", verifyCode);
        return dispose(apiService.register(params));
    }


    /**
     * 获取验证码
     *
     * @param mobile 手机号码
     * @return
     */
    public Observable<VerifyCodeBean> getVerifyCode(String mobile) {
        return dispose(apiService.getVerifyCode(mobile));
    }


    /**
     * 登录
     *
     * @param loginName
     * @param password
      * @return
     */
    public Observable<User> login(String loginName, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("password", password);
         return dispose(apiService.login(params));
    }

    /**
     * 快捷登录
     *
     * @param loginName
     * @param verifyCode
     * @return
     */
    public Observable<User> fastLogin(String loginName,  String verifyCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
         params.put("verifyCode", verifyCode);
        return dispose(apiService.fastLogin(params));
    }
}
