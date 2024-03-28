package com.airohit.agriculture.module.system.api.slave.vo.farm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("管理后台 - 农场 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FarmRespVO extends FarmBaseVO {

    @ApiModelProperty(value = "Id", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("经纬度列表")
    private List<PosVo> posVoList;

}
