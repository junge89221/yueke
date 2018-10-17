package cn.bry.yueke.base;

/**
 * 类描述：
 * 创建人： 张俊
 * 创建时间： 2018/10/17
 * 版权： 成都智慧一生约科技有限公司
 */

public class loginResp {
    private String respCode;
    private String respDesc;
    private String userId;
    private String phone;
    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
