package com.airohit.agriculture.module.device.vo.obs.beijingTH;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 09:55
 */
@Data
public class UserLoginBeijingTHRequestVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    private String client_id;

    private String client_secret;

    private String grant_type;


}
