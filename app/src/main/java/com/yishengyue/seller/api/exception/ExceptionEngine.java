package com.yishengyue.seller.api.exception;




import com.yishengyue.seller.util.NetWorkUtils;
import com.yishengyue.seller.util.Utils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created on 2017/2/24
 * Author：qzj
 */
public class ExceptionEngine {

    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, Error.HTTP_ERROR);
            ex.setErrorCode(httpException.code());
            ex.setMsg("服务器连接异常");
            return ex;
        } else if (e instanceof ServerException){
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, Error.SERVER_ERROR);
            ex.setErrorCode(resultException.getCode());
            ex.setMsg(resultException.getMsg());
            return ex;
        } else if(e instanceof ConnectException || e instanceof SocketException || e instanceof SocketTimeoutException){
            if (!NetWorkUtils.isNetworkConnected(Utils.getContext())){
                ex = new ApiException(e, Error.NETWORK_ERROR);
                ex.setMsg("连接失败");
            }else{
                ex = new ApiException(e, Error.HTTP_ERROR);
                    ex.setMsg("服务器连接异常");
            }

            return ex;
        }
        else if(e instanceof UnknownHostException){
            if (!NetWorkUtils.isNetworkConnected(Utils.getContext())){
                ex = new ApiException(e, Error.NETWORK_ERROR);
                ex.setMsg("连接失败");
            }else{
                ex = new ApiException(e, Error.HTTP_ERROR);
                ex.setMsg("服务器连接异常");
            }
            return ex;
        }
        else if(e instanceof NullPointerException){
            ex = new ApiException(e, Error.EMPTY);
            ex.setMsg("空指针");
            return ex;
        }
        else {
            ex = new ApiException(e, Error.UNKNOWN);
            ex.setMsg("未知错误");
            return ex;
        }
    }

}
