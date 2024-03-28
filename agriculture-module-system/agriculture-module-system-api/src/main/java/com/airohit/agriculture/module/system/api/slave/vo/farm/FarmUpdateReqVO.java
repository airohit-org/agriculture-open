package com.airohit.agriculture.module.system.api.slave.vo.farm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("管理后台 - 农场更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FarmUpdateReqVO extends FarmBaseVO {

    @ApiModelProperty(value = "Id", required = true)
    @NotNull(message = "Id不能为空")
    private Integer id;

    @ApiModelProperty(value = "经纬度列表", required = true)
    private List<PosVo> posVoList;

}
