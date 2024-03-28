package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:20
 */
@Data
public class UserGroupResponseVo {

    /**
     * groupId : 2F79166C3AC1FCCBBB72B024446C5976
     * groupName : 灌溉演示区域
     * remark :
     * parentId : 0
     * emailQuantity : 4
     * smsQuantity : 8
     * createTime : 2021-09-25 09:04:51
     * deviceList : null
     * deviceDOS : null
     * authority : 1
     * select : false
     * dataCrud : 1
     */
    /**
     * 区域id
     */
    private String groupId;
    /**
     * 区域名称
     */
    private String groupName;
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
    /**
     * 操作权限 1为可操作，其他不可操作
     */
    private int authority;
    /**
     * 蒸发量和降雨量数据操作权限 1 为可操作，其他不可操作
     */
    private int dataCrud;


}
