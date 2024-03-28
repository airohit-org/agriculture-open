package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Data
public class ObsDeviceFirmVo {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("厂商id")
    private Integer firmId;

    @ApiModelProperty("厂商名称")
    private String firmName;

    @ApiModelProperty("农场id")
    private Integer farmId;

    @ApiModelProperty("用户名")
    private String loginName;

    @ApiModelProperty("密码")
    private String loginPwd;

    @ApiModelProperty("创建日期")
    private LocalDateTime createTime;
}
