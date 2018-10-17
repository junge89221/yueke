package cn.bry.yueke.base;

/**
 * 类描述：
 * 创建人： 张俊
 * 创建时间： 2018/10/17
 * 版权： 成都智慧一生约科技有限公司
 */

public class RegisterReq {
    private String dateTime;
    private String yuekeType ;
    private String phone;
    private String pwd;

    public RegisterReq(String dateTime, String yuekeType, String phone, String pwd) {
        this.dateTime = dateTime;
        this.yuekeType = yuekeType;
        this.phone = phone;
        this.pwd = pwd;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
