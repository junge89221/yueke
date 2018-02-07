package cn.bry.yueke.api.exception;

public class ApiException extends Exception {

    private int code;
    private int errorCode;
    private String msg;

    public ApiException(int code){
        super();
        this.code = code;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
