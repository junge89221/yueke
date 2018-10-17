package cn.bry.yueke.api;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import cn.braing.pay.lib.bean.CommRequest;
import cn.braing.pay.lib.bean.LoginReq;
import cn.braing.pay.lib.bean.ReqMessageHead;
import cn.braing.pay.lib.bean.ServerLogEvent;
import cn.braing.pay.lib.util.DateUtil;
import cn.braing.pay.lib.util.MD5Util;
import cn.bry.yueke.base.CommRequest2;
import cn.bry.yueke.base.Order;
import cn.bry.yueke.base.RegisterReq;
import cn.bry.yueke.base.ReqMessageHead2;
import cn.bry.yueke.base.User;
import cn.bry.yueke.base.VerifyCodeBean;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.bry.yueke.base.loginResp;
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
    public Observable<loginResp> register(String loginName, String password) {
        return dispose(apiService.getData(getReqBean("REGISTER",new RegisterReq(DateUtil.getStringNow(),"1002",loginName,password))));
    }




    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    public Observable<loginResp> login(String loginName, String password) {
        return dispose(apiService.getData(getReqBean("LOGIN",new RegisterReq(DateUtil.getStringNow(),"1001",loginName,password))));
    }
    private CommRequest2 getReqBean(String method, Object bean) {
        ReqMessageHead2 reqMessageHead = new ReqMessageHead2();
         String lsh = UUID.randomUUID().toString();
        reqMessageHead.setSeqNo(lsh);
        reqMessageHead.setOpFlag(method);
        reqMessageHead.setAppVersion("v1.0");
        reqMessageHead.setSign(MD5Util.encodeMD5(lsh + method));
         return new CommRequest2(reqMessageHead, bean);

    }
}
