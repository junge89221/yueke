package cn.bry.yueke.base;

/**
 * <pre>
 * author：张俊
 * date： 2017/3/17
 * desc： 短信验证码
 * <pre>
 */

public class VerifyCodeBean {
    private String verifyCode;
    private String isReg;

    public String getIsReg() {
        return isReg;
    }

    public void setIsReg(String isReg) {
        this.isReg = isReg;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
