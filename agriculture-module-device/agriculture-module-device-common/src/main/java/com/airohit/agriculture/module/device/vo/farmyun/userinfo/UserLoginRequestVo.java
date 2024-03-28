package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 09:55
 */
@Data
public class UserLoginRequestVo {
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String loginPwd;

}
