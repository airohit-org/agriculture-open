package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 09:56
 */
@Data
public class UserLoginResponseVo {


    /**
     * loginSign : 9af7de50-a7ef-11eb-a725-9beef4c79665
     * currDate : 1660613095
     * expDate : 1660620295
     * token : 534791660613095301
     */
    /**
     * 登录标识
     */
    private String loginSign;
    /**
     * Token生成时间
     */
    private long currDate;
    /**
     * token过期时间
     */
    private long expDate;
    /**
     * token
     */
    private String token;

    public String getLoginSign() {
        return loginSign;
    }

    public void setLoginSign(String loginSign) {
        this.loginSign = loginSign;
    }

    public long getCurrDate() {
        return currDate;
    }

    public void setCurrDate(long currDate) {
        this.currDate = currDate;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
