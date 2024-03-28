package com.airohit.agriculture.module.device.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备基本信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ObsDeviceFirmBaseDto {
    @ApiModelProperty("厂商id")
    private Integer firmId;
    /**
     * 农场id
     */
    @ApiModelProperty("农场id")
    private Integer farmId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String loginName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String loginPwd;
}
