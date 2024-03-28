package com.airohit.agriculture.module.peasant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 农户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PeasantRespVO extends PeasantBaseVO {

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
