package cn.bry.yueke.base;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/27
 * desc：
 * <pre>
 */

public class QrCodeBean {

    /**
     * consumeVerifyCode : consumeVerifyCode
     * businessTypeCode : 1
     */
    private String consumeVerifyCode;
    private String businessTypeCode;

    public String getConsumeVerifyCode() {
        return consumeVerifyCode;
    }

    public void setConsumeVerifyCode(String consumeVerifyCode) {
        this.consumeVerifyCode = consumeVerifyCode;
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }
}
