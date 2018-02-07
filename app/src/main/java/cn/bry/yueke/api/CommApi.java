package cn.bry.yueke.api;

import cn.bry.yueke.base.Order;
import cn.bry.yueke.base.User;
import cn.bry.yueke.base.VerifyCodeBean;

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
     * @return
     */
    public Observable<String> register(String loginName, String password, int type) {
        return dispose(apiService.register(loginName,password,type));
    }


    /**
     * 获取验证码
     *
     * @param mobile 手机号码
     * @return
     */
    public Observable<VerifyCodeBean> getVerifyCode(String mobile) {
        return dispose(apiService.getVerifyCode(mobile, "OTO"));
    }


    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    public Observable<User> login(String loginName, String password) {
        return dispose(apiService.login(loginName, password, "USER"));
    }

    /**
     * 快捷登录
     *
     * @param loginName
     * @param verifyCode
     * @return
     */
    public Observable<User> fastLogin(String loginName, String verifyCode) {
        return dispose(apiService.fastLogin(loginName, verifyCode));
    }

    /**
     * （卖家端）通过消费验证码获取订单详情信息
     *
     * @return
     */
    public Observable<Order> getOrderDetail(String userId, String consumeVerifyCode) {
        return dispose(apiService.getOrderDetail(userId, consumeVerifyCode));
    }

    /**
     * 核销验证码[扫描二维码或手动收
     *
     * @return
     */
    public Observable<String> activateOrder(String userId, String consumeVerifyCode, String orderDatilId) {
        return dispose(apiService.activateOrder(userId, consumeVerifyCode, orderDatilId));
    }

    /**
     * 忘记密码
     *
     * @return
     */
    public Observable<String> forgetPassword(String phone, String verifyCode, String newPwd) {
        return dispose(apiService.forgetPassword(phone, verifyCode, newPwd));
    }
}
