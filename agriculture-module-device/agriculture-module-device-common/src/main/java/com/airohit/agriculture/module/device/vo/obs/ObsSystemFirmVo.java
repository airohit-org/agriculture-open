package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Data
public class ObsSystemFirmVo {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("厂商名称")
    private String firmName;

    @ApiModelProperty("创建日期")
    private LocalDateTime createTime;
}
