package cn.bry.yueke.api.exception;

/**
 * 服务器异常
 * Created on 2017/2/24
 * Author：qzj
 */
public class ServerException extends RuntimeException {

    private int code;
    private String msg;

    public ServerException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ServerException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
