package cn.bry.yueke.base;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/2
 * desc：
 * <pre>
 */

public class User {

    /**
     * token : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0b2tlbiIsImlhdCI6MTUxNzk5NzA1OCwic3ViIjoie1wicGVybWlzc2lvblwiOlwiXCIsXCJpZFwiOjMxNn0iLCJleHAiOjE1MTgwMDA2NTh9.I81Vo6VlSQvCYW34GXDJMHUdSSAmoxdhZJhjh8yROGQ
     * refreshToken : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0b2tlbiIsImlhdCI6MTUxNzk5NzA1OCwic3ViIjoie1wicGVybWlzc2lvblwiOlwiXCIsXCJpZFwiOjMxNn0iLCJleHAiOjE1MTgwNDAyNTh9.ZOqTsoeEe6u7mULXH7MWPmugdrOXydgnrh2nGtDE6tE
     */
    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
