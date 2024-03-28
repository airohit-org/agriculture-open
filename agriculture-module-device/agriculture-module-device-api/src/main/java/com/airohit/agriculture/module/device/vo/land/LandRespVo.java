package com.airohit.agriculture.module.device.vo.land;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 地块信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LandRespVo extends LandBaseVo {

    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
