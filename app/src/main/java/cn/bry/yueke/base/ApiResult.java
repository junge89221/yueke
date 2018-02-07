package cn.bry.yueke.base;



public class ApiResult<T> {

    public int code;
    public String msg;
    public T data;

    public boolean isSuccess(){
        return code==1000;
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
