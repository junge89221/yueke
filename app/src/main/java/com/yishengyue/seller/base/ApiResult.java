package com.yishengyue.seller.base;



public class ApiResult<T> {

    public int code;
    public String msg;
    public T data;

    public boolean isSuccess(){
        return code==0;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
