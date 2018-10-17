package cn.bry.yueke.base;

/**
 * 类描述：
 * 创建人： 张俊
 * 创建时间： 2018/10/17
 * 版权： 成都智慧一生约科技有限公司
 */

public class ReqMessageHead2 {
   private String appVersion;
   private String seqNo;
   private String opFlag;
   private String sign;

    public ReqMessageHead2() {
    }

    public ReqMessageHead2(String appVersion, String seqNo, String opFlag, String sign) {
        this.appVersion = appVersion;
        this.seqNo = seqNo;
        this.opFlag = opFlag;
        this.sign = sign;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOpFlag() {
        return opFlag;
    }

    public void setOpFlag(String opFlag) {
        this.opFlag = opFlag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
