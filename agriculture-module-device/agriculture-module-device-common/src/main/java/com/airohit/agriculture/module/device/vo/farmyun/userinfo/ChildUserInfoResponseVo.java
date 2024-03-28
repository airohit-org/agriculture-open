package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:10
 */
@Data
public class ChildUserInfoResponseVo {


    /**
     * userId : 9af7de50-a7ef-11eb-a725-9beef4c79665
     * userName : jnrstest管理员
     * loginName : jnrstest
     * loginPwd : 8e909623292346bb9684e16c22828287
     * userType : 1
     * parentId : 0
     * remark : 勿删
     * emailQuantity : 109
     * smsQuantity : 78
     * createTime : 2021-04-28 15:01:50
     * correlationDeviceAddr : null
     * exp : null
     * iat : null
     */
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String loginPwd;
    /**
     * 用户类型 1普通账号 2子账号
     */
    private String userType;
    /**
     * 父用户id
     */
    private String parentId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 邮件可用发送条数
     */
    private int emailQuantity;
    /**
     * 短信可用发送条数
     */
    private int smsQuantity;
    /**
     * 创建时间
     */
    private String createTime;
}
