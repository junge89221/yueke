package com.yishengyue.seller.base;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/2
 * desc：
 * <pre>
 */

public class User {

    /**
     * userId : 636f713b134c4cfd9ed37961d300f0f6
     * storeId : 636f713b134c4cfd9ed37961d300f0f6
     * applyId : 636f713b134c4cfd9ed37961d300f0f6
     */

    private String userId;
    private String storeId;
    private String applyId;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
