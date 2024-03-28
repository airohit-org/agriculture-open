package com.airohit.agriculture.module.system.api.slave.vo.farm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@ApiModel("管理后台 - 农场创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FarmCreateReqVO extends FarmBaseVO {

    @ApiModelProperty(value = "经纬度列表", required = true)
    private List<PosVo> posVoList;
}
