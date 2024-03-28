package com.airohit.agriculture.module.land.vo;

import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("管理后台 - 地块信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LandUpdateReqVO extends LandBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private Integer id;

    @ApiModelProperty(value = "经纬度列表", required = true)
    private List<PosVo> posVoList;

    @ApiModelProperty(value = "作物及品种", required = true)
    private List<CropsCreateReqVO> cropsCreateReqVOList;

    @ApiModelProperty(value = "种植计划编号")
    private Integer planId;
}
